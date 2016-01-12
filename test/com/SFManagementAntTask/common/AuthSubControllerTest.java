package com.SFManagementAntTask.common;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.SFManagementAntTask.tooling.dao.model.CoverageClass;

public class AuthSubControllerTest {

	@Before
	public void doBefore() {
		System.out.println("doBeforeしています。");
	}

	/**
	 * AuthSubController
	 */
	@Test
	public void testOne() {
		CoverageClass cobj = new CoverageClass();
		cobj.setName("AuthSubControllerTest");
		ApexClassParser parser = new ApexClassParser(cobj);
		for (String line : createTestData()) {
			parser.execute(line);
		}
		Assert.assertEquals(cobj.getCoveredLines().get(0).getNum(), "3");
		Assert.assertEquals(cobj.getCoveredLines().get(1).getNum(), "4");
		Assert.assertEquals(cobj.getCoveredLines().get(2).getNum(), "5");
		Assert.assertEquals(cobj.getCoveredLines().get(3).getNum(), "6");
		Assert.assertEquals(cobj.getCoveredLines().get(4).getNum(), "7");
		Assert.assertEquals(cobj.getCoveredLines().get(5).getNum(), "10");
		Assert.assertEquals(cobj.getCoveredLines().get(6).getNum(), "11");
		Assert.assertEquals(cobj.getCoveredLines().get(7).getNum(), "12");
		Assert.assertEquals(cobj.getCoveredLines().get(8).getNum(), "14");
		Assert.assertEquals(cobj.getCoveredLines().get(9).getNum(), "16");
		Assert.assertEquals(cobj.getCoveredLines().get(10).getNum(), "19");
		Assert.assertEquals(cobj.getCoveredLines().get(11).getNum(), "20");
		Assert.assertEquals(cobj.getCoveredLines().get(12).getNum(), "21");
		Assert.assertEquals(cobj.getCoveredLines().size(), 13);
	}

	private List<String> createTestData() {
		List<String> data = new ArrayList<String>();
		data.add("public class AuthSubController {");
		data.add("    ");
		data.add("    public pagereference exchangeRequestToken() {");
		data.add("        if ( ApexPages.currentPage().getParameters().get('token') != null) { ");
		data.add("            string sessToken = ");
		data.add("             AuthSubUtil.exchangeForSessionToken( ");
		data.add("                ApexPages.currentPage().getParameters().get('token'));");
		data.add("            // store the token ");
		data.add("            // this assumes a you store tokens in a custom object");
		data.add("            GoogSession__c session = new googSession__c(id=");
		data.add("                ApexPages.currentPage().getParameters().get('id'),"); // 11
		data.add("                 AuthSubSessionToken__c = sessToken );"); // 12
		data.add("            ");
		data.add("            update session; "); // 14
		data.add("        }");
		data.add("        return null;"); // 16
		data.add("    }");
		data.add("    ");
		data.add("    public boolean getRequestToken() {");
		data.add("        return (ApexPages.currentPage().getParameters().get('token') == null"); // 20
		data.add("        && ApexPages.currentPage().getParameters().get('id') != null);");
		data.add("    }");
		data.add("    ");
		data.add("    public static testMethod void t1() {   ");
		data.add("   ");
		data.add("        PageReference pageRef = Page.authsub;");
		data.add("        Test.setCurrentPage(pageRef);");
		data.add("  ");
		data.add("        AuthSubController stc = new AuthSubController( );");
		data.add("        ApexPages.currentPage().getParameters().put('token', 'yyyy');");
		data.add("        ApexPages.currentPage().getParameters().put('id', 'yyyy');");
		data.add("        stc.exchangeRequestToken();");
		data.add("        stc.getRequestToken();");
		data.add("    }");
		data.add("     public static testMethod void t2() {   ");
		data.add("   ");
		data.add("        PageReference pageRef = Page.authsub;");
		data.add("        Test.setCurrentPage(pageRef);");
		data.add("  ");
		data.add("        AuthSubController stc = new AuthSubController( );");
		data.add("        stc.getRequestToken();");
		data.add("        stc.exchangeRequestToken();");
		data.add("        ");
		data.add("    }");
		data.add("}");
		return data;
	}
}
