package com.SFManagementAntTask.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.tools.ant.BuildException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.SFManagementAntTask.ant.task.SFAntTaskAbs;
import com.sforce.soap.metadata.MetadataConnection;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.tooling.ToolingConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

public class ConnectionUtil {

	private final static Logger log = LoggerFactory.getLogger(ConnectionUtil.class);

	private static ConnectorConfig CONFIG;

	public static MetadataConnection login(SFAntTaskAbs ins) {
		MetadataConnection con = ins.getMetadataConnection();
		JSONObject json = new JSONObject(con.getSessionHeader());
		Const.SESSION_ID = (String) json.get("sessionId");
		Const.MAX_POLL = ins.getMaxpoll();
		Const.POLL_WAIT_MILLIS = ins.getPollWaitMillis();
		Const.REPORT_ROOT = ins.getReportRoot();
		Const.SRC_ROOT = ins.getSrcRoot();
		CONFIG = con.getConfig();
		return con;
	}

	public static MetadataConnection connectMetadata() {

		MetadataConnection con = null;
		ConnectorConfig config = CONFIG;
		config.setAuthEndpoint(config.getAuthEndpoint().replace("/T/", "/m/"));
		config.setServiceEndpoint(config.getServiceEndpoint().replace("/T/", "/m/"));
		config.setAuthEndpoint(config.getAuthEndpoint().replace("/u/", "/m/"));
		config.setServiceEndpoint(config.getServiceEndpoint().replace("/u/", "/m/"));

		try {
			con = new MetadataConnection(config);
		} catch (ConnectionException e) {
			log.error("createPartnerCon", e);
			new BuildException(e);
		}
		return con;
	}

	public static ToolingConnection connectTooling() {

		ToolingConnection con = null;
		ConnectorConfig config = CONFIG;
		config.setAuthEndpoint(config.getAuthEndpoint().replace("/m/", "/T/"));
		config.setServiceEndpoint(config.getServiceEndpoint().replace("/m/", "/T/"));
		config.setAuthEndpoint(config.getAuthEndpoint().replace("/u/", "/T/"));
		config.setServiceEndpoint(config.getServiceEndpoint().replace("/u/", "/T/"));

		try {
			con = new ToolingConnection(config);
		} catch (ConnectionException e) {
			log.error("createPartnerCon", e);
			new BuildException(e);
		}
		return con;
	}

	public static PartnerConnection connectPartner() {

		PartnerConnection con = null;
		ConnectorConfig config = CONFIG;
		config.setAuthEndpoint(config.getAuthEndpoint().replace("/m/", "/u/"));
		config.setServiceEndpoint(config.getServiceEndpoint().replace("/m/", "/u/"));
		config.setAuthEndpoint(config.getAuthEndpoint().replace("/T/", "/u/"));
		config.setServiceEndpoint(config.getServiceEndpoint().replace("/T/", "/u/"));
		try {
			con = new PartnerConnection(config);
		} catch (ConnectionException e) {
			log.error("createPartnerCon", e);
			new BuildException(e);
		}
		return con;
	}

	private static Map<String, String> resultmap = new HashMap<String, String>();

	public static String queryforGetNocash(String url) {

		Content content = null;
		log.trace("[RestApi] tooling/" + url);
		try {
			content = Request
			        .Get("https://ap.salesforce.com/services/data/v33.0/tooling/" + url)
			        .addHeader("Content-Type", "application/json")
			        .addHeader("charset", "utf-8")
			        .addHeader("X-PrettyPrint", "1")
			        .addHeader("Authorization", "Bearer " + Const.SESSION_ID)
			        .execute().returnContent();
		} catch (ClientProtocolException e) {
			log.error("[queryforGetNocash]", e);
			throw new BuildException(e);
		} catch (IOException e) {
			log.error("[queryforGetNocash]", e);
			throw new BuildException(e);
		}
		log.trace("[RestApi] content : " + content);
		if (content == null) {
			return null;
		}
		String res = content.asString();
		return res;
	}

	public static String queryforGet(String url) {
		if (resultmap.containsKey(url)) {
			return resultmap.get(url);
		}
		String res = queryforGetNocash(url);
		resultmap.put(url, res);
		return res;
	}

	public static String queryforGet(String url, Map<String, String> param) {
		Iterator<String> keyIte = param.keySet().iterator();
		String cond = "+Where";
		Boolean flg = true;
		while (keyIte.hasNext()) {
			String key = keyIte.next();
			String val = param.get(key);
			if (flg) {
				cond = cond + "+" + key + "='" + val + "'";
				flg = false;
			}
			if (!flg) {
				cond = cond + "+AND+" + key + "='" + val + "'";
			}
		}
		return queryforGet(url);
	}
}
