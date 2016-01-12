package com.SFManagementAntTask.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tools.ant.BuildException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.SFManagementAntTask.tooling.dao.model.CoverageClass;
import com.SFManagementAntTask.tooling.dao.model.Line;

/**
 * Apexカバレッジ分析クラス
 * @author mkawaguchi
 */
public class ApexClassParser {

	private final static Logger log = LoggerFactory.getLogger(ApexClassParser.class);

	/**
	 * デフォルトコンストラクタ
	 * @param aobj Apex情報
	 */
	public ApexClassParser(CoverageClass aobj) {
		this.aobj = aobj;
		linecnt = 0;
	}

	/** Apex情報 */
	private CoverageClass aobj;

	/** 行カウント */
	private int linecnt;

	/** クラスフラグ */
	private boolean classflg;

	/** テストクラスフラグ */
	private boolean testclassflg;

	/** インナークラスフラグ */
	private boolean innerclassflg = false;

	/** ブロックカウント */
	private int blockcnt;

	/** テストメソッドフラグ */
	private boolean testmethodflg;

	/** テストメソッドカウント */
	private int testmethodcnt;

	/** テストメソッドカウント */
	private int nextskipcnt = 0;

	/** テストメソッドカウント */
	private boolean nextskipflg = false;

	/** SOQLカウント */
	private int soqlcnt = 0;

	/** SOQLフラグ */
	private boolean soqlflg = false;

	/** コメントフラグ */
	private boolean commentflg;

	/**
	 * 行毎分析メソッド
	 * @param body 行情報
	 */
	public void execute(String body) {

		++linecnt;
		if (isComment(body) || isEmptyRow(body)) {
			return;
		}
		if (isStartCommentBlock(body)) {
			commentflg = true;
		}
		if (existEndCommentBlock(body)) {
			commentflg = false;
		}
		if (isTest(body) && !classflg) {
			testclassflg = true;
		}
		if (isTest(body) && classflg) {
			testmethodflg = true;
		}
		if (isClass(body) && classflg) {
			innerclassflg = true;
		}
		if (isClass(body) && !classflg) {
			classflg = true;
		}
		if (isContinueDeclearMethod(body)) {
			++nextskipcnt;
		}
		if (existStartBracket(body)) {
			++soqlcnt;
		}
		if (soqlcnt > 0 && isContinueDeclearMethod(body)) {
			++soqlcnt;
		}
		if (existEndBracket(body)) {
			soqlcnt = 0;
		}
		if (nextskipcnt == 0 && nextskipflg) {
			nextskipflg = false;
		}
		if (isStartBlock(body)) {
			++blockcnt;
			if (nextskipcnt > 0) {
				nextskipcnt = 0;
				nextskipflg = true;
			}
			if (testmethodflg) {
				++testmethodcnt;
			}
		}
		if (isEndBlock(body)) {
			--blockcnt;
			if (testmethodflg) {
				--testmethodcnt;
			}
			if (testmethodcnt == 0) {
				testmethodflg = false;
			}
			if (blockcnt == 0) {
				classflg = false;
				testclassflg = false;
			}
			if (blockcnt == 1) {
				innerclassflg = false;
			}
		}
		/////////////////////
		// 次へ
		if (isSkepRow(body)) {
			return;
		}
		Line line = new Line();
		line.setNum(linecnt);
		line.setHits(1);
		line.setBranch(existIf(body));
		aobj.addCoveredLines(line);
	}

	/**
	 * 行スキップ判定メソッド
	 * @param body 行情報
	 * @return 有り true | 無し false
	 */
	private Boolean isSkepRow(String body) {
		log.debug("[line] " + aobj.getName() + " (" + linecnt + ") : "
		        + testmethodflg + " , " + testclassflg + " , " + commentflg
		        + " , " + existEndCommentBlock(body) + " , " + isEmptyBlock(body) + " , " + isClass(body)
		        + " , " + isSkepMethod(body)
		        + " , " + existDebuglog(body)
		        + " , " + (isAnnotation(body) && blockcnt <= 1)
		        + " , " + (isSkippingField(body) && (blockcnt == 1 || innerclassflg))
		        //+ " , " + (nextskipcnt > 1 || nextskipflg)
		        + " , (" + nextskipcnt + " , " + nextskipflg + ")"
		        + " , " + (soqlcnt > 1 || soqlflg));
		return testmethodflg || testclassflg || commentflg
		        || existEndCommentBlock(body) || isEmptyBlock(body) || isClass(body) || isSkepMethod(body)
		        || existDebuglog(body)
		        || (isAnnotation(body) && blockcnt <= 1)
		        || (isSkippingField(body) && (blockcnt == 1 || innerclassflg))
		        || (nextskipcnt > 1 || nextskipflg)
		        || (soqlcnt > 1 || soqlflg);
	}

	/**
	 * 空行判別メソッド
	 * @param body 行情報
	 * @return 有り true | 無し false
	 */
	private Boolean isEmptyRow(String body) {
		return isMatch("^[ \t]*$", body);
	}

	/**
	 * 空ブロック判別メソッド
	 * @param body 行情報
	 * @return 有り true | 無し false
	 */
	private Boolean isEmptyBlock(String body) {
		return isMatch("^[ \t]*\\{[ \t]*$", body) || isMatch("^[ \t]*\\}[ \t]*$", body);
	}

	/**
	 * カバレッジ対象外メソッド判別メソッド
	 * @param body 行情報
	 * @return 有り true | 無し false
	 */
	private Boolean isSkepMethod(String body) {
		return (isMatch(".*[ \t\\}]else[ \t\\{].*$", body) && !isMatch(".* if[ \t\\{].*$", body));
	}

	/**
	 * コメント判別メソッド
	 * @param body 行情報
	 * @return 有り true | 無し false
	 */
	private Boolean isComment(String body) {
		return isMatch("^[ \t]*\\/\\/.*$", body);
	}

	/**
	 * コメントブロック開始判別メソッド
	 * @param body 行情報
	 * @return 有り true | 無し false
	 */
	private Boolean isStartCommentBlock(String body) {

		return isMatch("^[ \t]*\\/\\*.*$", body);
	}

	/**
	 * コメントブロック終了判別メソッド
	 * @param body 行情報
	 * @return 有り true | 無し false
	 */
	private Boolean existEndCommentBlock(String body) {

		return isMatch("^.* \\**\\*\\/.*$", body);
	}

	/**
	 * 鍵括弧開始判別メソッド
	 * @param body 行情報
	 * @return 有り true | 無し false
	 */
	private Boolean existStartBracket(String body) {

		return isMatch("^.*\\[.*$", body);
	}

	/**
	 * 鍵括弧終了判別メソッド
	 * @param body 行情報
	 * @return 有り true | 無し false
	 */
	private Boolean existEndBracket(String body) {

		return isMatch("^.*\\].*$", body);
	}

	/**
	 * ブロック開始判別メソッド
	 * @param body 行情報
	 * @return 有り true | 無し false
	 */
	private Boolean isStartBlock(String body) {

		return isMatch("^.*\\{.*$", body);
	}

	/**
	 * ブロック終了判別メソッド
	 * @param body 行情報
	 * @return 有り true | 無し false
	 */
	private Boolean isEndBlock(String body) {

		return isMatch("^.*\\}.*$", body);
	}

	/**
	 * 句終了存在判別メソッド
	 * @param body 行情報
	 * @return 有り true | 無し false
	 */
	private Boolean isEndLine(String body) {

		return isMatch("^.*;.*$", body);
	}

	/**
	 * アノテーション判別メソッド
	 * @param body 行情報
	 * @return 有り true | 無し false
	 */
	private Boolean isAnnotation(String body) {

		return isMatch("^[ \t]*@.*$", body);
	}

	/**
	 * クラス判別メソッド
	 * @param body 行情報
	 * @return 有り true | 無し false
	 */
	private Boolean isClass(String body) {

		return isMatch("^.* class .*$", body.toLowerCase()) || isMatch("^.* trigger .*$", body.toLowerCase());
	}

	/**
	 * テストクラス、メソッド判別メソッド
	 * @param body 行情報
	 * @return 有り true | 無し false
	 */
	private Boolean isTest(String body) {

		return isMatch("^.* testmethod .*$", body.toLowerCase()) || isMatch("^.* @istest .*$", body.toLowerCase());
	}

	/**
	 * 句途中改行判別メソッド
	 * @param body 行情報
	 * @return 有り true | 無し false
	 */
	private Boolean isContinueDeclearMethod(String body) {
		return isMatch("^.*[/w]*.*$", body.toLowerCase())
		        && !isStartBlock(body)
		        && !isEndLine(body)
		        && !isEndBlock(body)
		        && !isAnnotation(body)
		        && !existStartBracket(body)
		        && !existEqual(body)
		        && !existEndCommentBlock(body)
		        && soqlcnt == 0 && !soqlflg && !commentflg
		        && (blockcnt == 1 && classflg)
		        && (blockcnt == 2 && innerclassflg);
	}

	/**
	 * フィールド判別メソッド
	 * @param body 行情報
	 * @return 有り true | 無し false
	 */
	private Boolean isSkippingField(String body) {
		return isMatch("^.*;.*$", body)
		        && !isStartBlock(body)
		        && !(existEqual(body) && !existStatic(body))
		        && !isDML(body);
	}

	private Boolean isDML(String body) {
		return isMatch("^[ \t]*return .*$", body.toLowerCase())
		        || isMatch("^[ \t]*update .*$", body.toLowerCase())
		        || isMatch("^[ \t]*insert .*$", body.toLowerCase())
		        || isMatch("^[ \t]*delete .*$", body.toLowerCase())
		        || isMatch("^[ \t]*upsert .*$", body.toLowerCase());
	}

	/**
	 * イコール存在判別メソッド
	 * @param body 行情報
	 * @return 有り true | 無し false
	 */
	private Boolean existEqual(String body) {
		return isMatch("^.*=.*$", body.toLowerCase());
	}

	/**
	 * イコール存在判別メソッド
	 * @param body 行情報
	 * @return 有り true | 無し false
	 */
	private Boolean existStatic(String body) {
		return isMatch("^.* static .*$", body.toLowerCase());
	}

	/**
	 * If存在判定メソッド
	 * @param body 行情報
	 * @return 有り true | 無し false
	 */
	private Boolean existIf(String body) {

		return isMatch("^.* if .*$", body.toLowerCase()) || isMatch("^.* if\\(.*$", body.toLowerCase());
	}

	/**
	 * テストクラス、メソッド判別メソッド
	 * @param body 行情報
	 * @return 有り true | 無し false
	 */
	private Boolean existDebuglog(String body) {

		return isMatch("^[ \t]*system.debug.*$", body.toLowerCase());
	}

	/**
	 * 正規表現判定クラス
	 * @param regex 正規表現
	 * @param body 対象情報
	 * @return 有り true | 無し false
	 */
	public static Boolean isMatch(String regex, String body) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(body);
		return m.matches();
	}

	/**
	 * パッケージ指定全ファイル解析処理
	 * @param dirname ソースルート
	 * @param packagename パッケージフォルダ
	 * @param amap Apex情報マップ
	 * @return Apex情報マップ
	 */
	public static Map<String, CoverageClass> parseClasses(String dirname, String packagename, Map<String, CoverageClass> amap) {
		File dir = new File(dirname + "/" + packagename);
		if (dir == null || dir.list() == null) {
			log.error("[search directory] not existing directory " + dirname + "/" + packagename);
			return amap;
		}
		for (String filename : dir.list()) {
			if (isMatch(".*\\.cls", filename) || isMatch(".*\\.trigger", filename)) {
				CoverageClass aobj = parseClass(dirname, packagename, filename);
				amap.put(aobj.getName(), aobj);
			}
		}
		return amap;
	}

	/**
	 * ファイル指定分析処理
	 * @param dirname ソースルート
	 * @param packagename パッケージフォルダ
	 * @param filename ファイル名
	 * @return Apex情報
	 */
	private static CoverageClass parseClass(String dirname, String packagename, String filename) {

		CoverageClass aobj = new CoverageClass();
		aobj.setName(filename.split("\\.")[0]);
		File file = new File(dirname + "/" + packagename + "/" + filename);
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
		} catch (FileNotFoundException e) {
			log.error("[parseApexClass]", e);
			new BuildException(e);
		}

		String line;
		int count = 0;
		ApexClassParser parser = new ApexClassParser(aobj);
		try {
			while ((line = br.readLine()) != null) {
				log.trace(++count + "行：" + line);
				parser.execute(line);
			}
		} catch (IOException e) {
			log.error("[parseApexClass]", e);
			new BuildException(e);
		}

		//終了処理
		try {
			br.close();
			fr.close();
		} catch (IOException e) {
			log.error("[parseApexClass]", e);
			new BuildException(e);
		}

		return aobj;
	}
}
