package com.SFManagementAntTask.common;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.SFManagementAntTask.tooling.dao.model.CoverageClass;
import com.SFManagementAntTask.tooling.dao.model.Line;

public class AuthSubUtilTest {

	/**
	 * AuthSubController
	 */
	@Test
	public void testOne() {
		CoverageClass cobj = new CoverageClass();
		cobj.setName("AuthSubUtilTest");
		ApexClassParser parser = new ApexClassParser(cobj);
		for (String line : createTestData()) {
			parser.execute(line);
		}
		System.out.println(">>> Assert.");
		Integer[] assertrow = { 38, 39, 40, 41, 43, 44, 46, 47, 48, 50, 52, 53, 54, 55, 57, 61, 65, 69, 70, 71, 74, 78, 82, 86, 87, 88, 95, 100, 101, 102, 103, 104, 108, 109, 110, 112, 115, 118, 120,
		        121, 122 };
		System.out.println(cobj.getCoveredLines().size() + ":" + assertrow.length);
		for (int i = 0; i < cobj.getCoveredLines().size(); i++) {
			Line line = cobj.getCoveredLines().get(i);
			if (i < assertrow.length) {
				System.out.println(line.getNum() + ":" + assertrow[i]);
			} else {
				System.out.println(line.getNum() + ": null");
			}
			Assert.assertTrue(line.getNum() == assertrow[i]);
		}
	}

	private List<String> createTestData() {
		List<String> data = new ArrayList<String>();
		data.add("/*");
		data.add("    Copyright (c) 2008 salesforce.com, inc.");
		data.add("    All rights reserved.");
		data.add("    ");
		data.add("    Redistribution and use in source and binary forms, with or without");
		data.add("    modification, are permitted provided that the following conditions");
		data.add("    are met:");
		data.add("    ");
		data.add("    1. Redistributions of source code must retain the above copyright");
		data.add("       notice, this list of conditions and the following disclaimer.");
		data.add("    2. Redistributions in binary form must reproduce the above copyright");
		data.add("       notice, this list of conditions and the following disclaimer in the");
		data.add("       documentation and/or other materials provided with the distribution.");
		data.add("    3. The name of the author may not be used to endorse or promote products");
		data.add("       derived from this software without specific prior written permission.");
		data.add("    ");
		data.add("    THIS SOFTWARE IS PROVIDED BY THE AUTHOR \"AS IS\" AND ANY EXPRESS OR");
		data.add("    IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES");
		data.add("    OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.");
		data.add("    IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT, ");
		data.add("    INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT");
		data.add("    NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,");
		data.add("    DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY");
		data.add("    THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT");
		data.add("    (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF");
		data.add("    THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.");
		data.add("    ");
		data.add("    Utility class for collecting a google auth sub session token for use with all ");
		data.add("    Google Data API's");
		data.add("");
		data.add("*/");
		data.add("/*  ");
		data.add("  * for inspiration an insight read");
		data.add("  http://code.google.com/apis/gdata/javadoc/com/google/gdata/client/http/AuthSubUtil.html");
		data.add("  */");
		data.add(" ");
		data.add("public class AuthSubUtil {");
		data.add("    static string tokenInfo = 'https://www.google.com/accounts/AuthSubTokenInfo';");
		data.add("    static string authRequest = 'https://www.google.com/accounts/AuthSubRequest';");
		data.add("    static string subSession = 'https://www.google.com/accounts/AuthSubSessionToken';");
		data.add("    static string revokeUrl = 'https://www.google.com/accounts/AuthSubRevokeToken';");
		data.add("    ");
		data.add("    public static Map<String, String> getTokenInfo(string token) {");
		data.add("        Map<String, String> ret = new Map<String, String> ();");
		data.add("");
		data.add("        GoogleService service = new GoogleService('auth'); ");
		data.add("        service.AuthSubToken = token; ");
		data.add("        service.getFeedMethod('GET',tokenInfo, null, null );");
		data.add("        ");
		data.add("        string[] lines =  service.response.getBody().split('\n');");
		data.add("        ");
		data.add("        try { ");
		data.add("            for (string s: lines) {");
		data.add("                string[] nv = s.split('='); ");
		data.add("                ret.put(nv[0],nv[1]);");
		data.add("            } ");
		data.add("        } catch (exception e) {");
		data.add("            // an error or invalid token...");
		data.add("            system.debug( service.response.getBody() );");
		data.add("        } ");
		data.add("        return ret;");
		data.add("    }");
		data.add("    ");
		data.add("    //  Creates the request URL to be used to retrieve an AuthSub token.");
		data.add("    public static String    getRequestUrl(string proto, string host, ");
		data.add("                                            String nextUrl, String scope) {");
		data.add("        // first we need to go thru the Salesforce GoogleAuthSub callback servlet");
		data.add("        // and that (next destination) needs to be encoded");
		data.add("        string encodedNext = proto + '://' + host +     ");
		data.add("            '/_ui/core/google/GoogleAuthSubCallback?url=' + ");
		data.add("            EncodingUtil.urlEncode( nextUrl, 'UTF-8' ); ");
		data.add("        ");
		data.add("        // then we need to encode again to allow it to pass thru to the google authsub sevlet");
		data.add("        string twiceEncodedNext =  EncodingUtil.urlEncode( encodedNext, 'UTF-8' ) ; ");
		data.add("        ");
		data.add("        // finaly construct the first step in the redirect process, send your users to this url");
		data.add("        // must be done by the end user, in a browser...");
		data.add("        return authRequest + '?next='+ twiceEncodedNext + '&scope='+ scope + '&session=1&secure=0'; ");
		data.add("    }");
		data.add("    ");
		data.add("    //  Creates the pagereference to be used to retrieve an AuthSub token.");
		data.add("    public static pagereference    getRequestPageReference(string proto, string host, ");
		data.add("                                            String nextUrl, String scope) {");
		data.add("        // first we need to go thru the Salesforce GoogleAuthSub callback servlet");
		data.add("        // and that (next destination) needs to be encoded");
		data.add("        string encodedNext = proto + '://' + host +     ");
		data.add("            '/_ui/core/google/GoogleAuthSubCallback?url=' + ");
		data.add("            EncodingUtil.urlEncode( nextUrl, 'UTF-8' ); ");
		data.add("        ");
		data.add("        // then we need to encode again to allow it to pass thru to the google authsub sevlet");
		data.add("        //string twiceEncodedNext =  EncodingUtil.urlEncode( encodedNext, 'UTF-8' ) ; ");
		data.add("        ");
		data.add("        // finaly construct the first step in the redirect process, send your users to this url");
		data.add("        // must be done by the end user, in a browser...");
		data.add("        return  new PageReference( authRequest + '?next='+ encodedNext + '&scope='+ scope + '&session=1&secure=0'); ");
		data.add("    }");
		data.add("");
		data.add("         ");
		data.add("    //    Exchanges the one time use token returned in the URL for a session token.");
		data.add("    public static String    exchangeForSessionToken(String onetimeUseToken ) { ");
		data.add("        GoogleService service = new GoogleService('auth'); ");
		data.add("        service.AuthSubToken = onetimeUseToken;");
		data.add("        service.getFeedMethod('GET', subSession, null, GoogleService.CONTENT_TYPE_URL);");
		data.add("        return getTokenFromReply( service.response.getbody());");
		data.add("    }     ");
		data.add("    ");
		data.add("    // Parses and returns the AuthSub token returned by Google on a successful AuthSub login request.");
		data.add("    public static String    getTokenFromReply(String bodyOrUrl) {");
		data.add("        string[] atoken = bodyOrUrl.split('=');");
		data.add("        if ( atoken.size() != 2) { ");
		data.add("            system.debug( 'invalid token, or response from AuthSubSessionToken, no token');");
		data.add("            return null;");
		data.add("        }");
		data.add("        system.debug('session token is: '+atoken[1].trim());");
		data.add("        return atoken[1].trim();");
		data.add("    }");
		data.add("");
		data.add("    public  static void     revokeToken(String token) {");
		data.add("         // Revokes the specified token.");
		data.add("        GoogleService service = new GoogleService('auth'); ");
		data.add("        service.AuthSubToken = token; ");
		data.add("        service.getFeed( revokeUrl ); ");
		data.add("    }");
		data.add("    ");
		data.add("    /*  ");
		data.add("    static String   getPrivateKeyFromKeystore(String keystore, String keystorePass, String keyAlias, String keyPass)");
		data.add("              Retrieves the private key from the specified keystore.          ");
		data.add("    */");
		data.add("    ");
		data.add("    /* ");
		data.add("     * test methods below here ");
		data.add("     */");
		data.add("    static final string sessionAuthToken = 'CJ3pqczuBBCpgI2pBw';");
		data.add("    ");
		data.add("    public static testMethod void testGetTokenInfo() { ");
		data.add("        system.debug ( AuthSubUtil.getTokenInfo( sessionAuthToken) );");
		data.add("        system.debug ( AuthSubUtil.getTokenInfo( 'badtoken' ) );");
		data.add("    }");
		data.add("    ");
		data.add("    public static testMethod void testexchangeForSessionToken() { ");
		data.add("        CalendarService service = new CalendarService();  ");
		data.add("        AuthSubUtil.exchangeForSessionToken( 'teststtoken' );");
		data.add("        ");
		data.add("    }");
		data.add("    ");
		data.add("    public static testMethod void testgetTokenFromReply() { ");
		data.add("        CalendarService service = new CalendarService();  ");
		data.add("        AuthSubUtil.getTokenFromReply( 'url=teststtoken' );");
		data.add("        AuthSubUtil.getTokenFromReply( 'urlbadteststtoken' );");
		data.add("    }");
		data.add("    ");
		data.add("    public static testMethod void testrevokeToken() { ");
		data.add("        CalendarService service = new CalendarService();  ");
		data.add("        AuthSubUtil.revokeToken( 'teststtoken' );");
		data.add("     ");
		data.add("    }");
		data.add("    ");
		data.add("    public static testMethod void testgetRequestUrl() { ");
		data.add(
		        "        string expected = 'https://www.google.com/accounts/AuthSubRequest?next=https%3A%2F%2Ftapp0.salesforce.com%2F_ui%2Fcore%2Fgoogle%2FGoogleAuthSubCallback%3Furl%3D%252Fapex%252Fgsession%253Fid%253Da0AT0000000FO1QMAW&scope=http://www.google.com/calendar/feeds/&session=1&secure=0';");
		data.add("        CalendarService service = new CalendarService();  ");
		data.add("        ");
		data.add("        string checkUrl =  AuthSubUtil.getRequestUrl( 'https', 'tapp0.salesforce.com', ");
		data.add("            '/apex/gsession?id=a0AT0000000FO1QMAW',  // next  ");
		data.add("            'https://www.google.com/calendar/feeds/' );   // scope ");
		data.add("            ");
		data.add("        system.debug ( expected ) ; ");
		data.add("        system.debug ( checkUrl );   ");
		data.add("        system.assert(  expected == checkUrl , ' mis match request url '); ");
		data.add("    }");
		data.add("    ");
		data.add("");
		data.add("	public static testMethod void  testgetRequestPageReference(){");
		data.add("    ");
		data.add("        String strProto = 'https';");
		data.add("        String strHost = 'na2.salesforce.com';");
		data.add("        String strNextUrl = '/apex/authsub';");
		data.add("        String strScopeUrl = 'https://spreadsheets.google.com/feeds/';");
		data.add("              ");
		data.add("       	PageReference p = AuthSubUtil.getRequestPageReference( strProto, strHost, strNextUrl, strScopeUrl);");
		data.add("      	system.debug (p); ");
		data.add("    	");
		data.add(
		        "    	STRING expected = 'https://www.google.com/accounts/AuthSubRequest?next=https%3A%2F%2Fna2.salesforce.com%2F_ui%2Fcore%2Fgoogle%2FGoogleAuthSubCallback%3Furl%3D%252Fapex%252Fauthsub&scope=http%3A%2F%2Fspreadsheets.google.com%2Ffeeds%2F&secure=0&session=1';");
		data.add("    	system.assert( expected == p.getUrl() , 'page reference genreated is bad'); ");
		data.add("    }");
		data.add("        ");
		data.add("}");
		return data;
	}
}
