package com.SFManagementAntTask.common;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.SFManagementAntTask.tooling.dao.model.CoverageClass;
import com.SFManagementAntTask.tooling.dao.model.Line;

public class CalendarServiceTest {
	/**
	 * AuthSubController
	 */
	@Test
	public void testOne() {
		CoverageClass cobj = new CoverageClass();
		cobj.setName("CalendarServiceTest");
		ApexClassParser parser = new ApexClassParser(cobj);
		for (String line : createTestData()) {
			parser.execute(line);
		}

		System.out.println(">>> Assert.");
		Integer[] assertrow = { 3, 4, 10, 12, 15, 16, 19, 21, 22, 25, 26, 29, 30, 31, 33, 34, 37, 41, 42, 43, 44, 45, 46, 64, 65, 66, 67, 68, 69, 71, 72, 73, 74, 77, 78, 79, 80, 81, 85, 86, 87, 88,
		        89, 92, 93, 94, 95, 96, 98, 100, 101, 105, 106, 107, 110, 111, 121, 122, 123, 124, 127, 128, 129, 130, 132, 133, 134, 135, 139, 140, 141, 148, 149, 150, 151, 154, 157 };
		for (int i = 0; i < cobj.getCoveredLines().size(); i++) {
			Line line = cobj.getCoveredLines().get(i);

			System.out.println(line.getNum() + ":" + assertrow[i]);
		}
		Assert.assertEquals("" + cobj.getCoveredLines().get(0).getNum(), "3");
		Assert.assertEquals("" + cobj.getCoveredLines().get(1).getNum(), "4");
		Assert.assertEquals("" + cobj.getCoveredLines().get(2).getNum(), "10");
		Assert.assertEquals("" + cobj.getCoveredLines().get(3).getNum(), "12");
		Assert.assertEquals("" + cobj.getCoveredLines().get(4).getNum(), "15");
		Assert.assertEquals("" + cobj.getCoveredLines().get(5).getNum(), "16");
		Assert.assertEquals("" + cobj.getCoveredLines().get(6).getNum(), "19");
		Assert.assertEquals("" + cobj.getCoveredLines().get(7).getNum(), "21");
		Assert.assertEquals("" + cobj.getCoveredLines().get(8).getNum(), "22");
		Assert.assertEquals("" + cobj.getCoveredLines().get(9).getNum(), "25");
		Assert.assertEquals("" + cobj.getCoveredLines().get(10).getNum(), "26");
		Assert.assertEquals("" + cobj.getCoveredLines().get(11).getNum(), "29");
		Assert.assertEquals("" + cobj.getCoveredLines().get(12).getNum(), "30");
		Assert.assertEquals("" + cobj.getCoveredLines().get(13).getNum(), "31");
		Assert.assertEquals("" + cobj.getCoveredLines().get(14).getNum(), "33");
		Assert.assertEquals("" + cobj.getCoveredLines().get(15).getNum(), "34");
		Assert.assertEquals("" + cobj.getCoveredLines().get(16).getNum(), "37");
		Assert.assertEquals("" + cobj.getCoveredLines().get(17).getNum(), "41");
		Assert.assertEquals("" + cobj.getCoveredLines().get(18).getNum(), "42");
		Assert.assertEquals("" + cobj.getCoveredLines().get(19).getNum(), "43");
		Assert.assertEquals("" + cobj.getCoveredLines().get(20).getNum(), "44");
		Assert.assertEquals("" + cobj.getCoveredLines().get(21).getNum(), "45");
		Assert.assertEquals("" + cobj.getCoveredLines().get(22).getNum(), "46");
		Assert.assertEquals("" + cobj.getCoveredLines().get(23).getNum(), "64");
		Assert.assertEquals("" + cobj.getCoveredLines().get(24).getNum(), "65");
		Assert.assertEquals("" + cobj.getCoveredLines().get(25).getNum(), "66");
		Assert.assertEquals("" + cobj.getCoveredLines().get(26).getNum(), "67");
		Assert.assertEquals("" + cobj.getCoveredLines().get(27).getNum(), "68");
		Assert.assertEquals("" + cobj.getCoveredLines().get(28).getNum(), "69");
		Assert.assertEquals("" + cobj.getCoveredLines().get(29).getNum(), "71");
		Assert.assertEquals("" + cobj.getCoveredLines().get(30).getNum(), "72");
		Assert.assertEquals("" + cobj.getCoveredLines().get(31).getNum(), "73");
		Assert.assertEquals("" + cobj.getCoveredLines().get(32).getNum(), "74");
		Assert.assertEquals("" + cobj.getCoveredLines().get(33).getNum(), "77");
		Assert.assertEquals("" + cobj.getCoveredLines().get(34).getNum(), "78");
		Assert.assertEquals("" + cobj.getCoveredLines().get(35).getNum(), "79");
		Assert.assertEquals("" + cobj.getCoveredLines().get(36).getNum(), "80");
		Assert.assertEquals("" + cobj.getCoveredLines().get(37).getNum(), "81");
		Assert.assertEquals("" + cobj.getCoveredLines().get(38).getNum(), "85");
		Assert.assertEquals("" + cobj.getCoveredLines().get(39).getNum(), "86");
		Assert.assertEquals("" + cobj.getCoveredLines().get(40).getNum(), "87");
		Assert.assertEquals("" + cobj.getCoveredLines().get(41).getNum(), "88");
		Assert.assertEquals("" + cobj.getCoveredLines().get(42).getNum(), "89");
		Assert.assertEquals("" + cobj.getCoveredLines().get(43).getNum(), "92");
		Assert.assertEquals("" + cobj.getCoveredLines().get(44).getNum(), "93");
		Assert.assertEquals("" + cobj.getCoveredLines().get(45).getNum(), "94");
		Assert.assertEquals("" + cobj.getCoveredLines().get(46).getNum(), "95");
		Assert.assertEquals("" + cobj.getCoveredLines().get(47).getNum(), "96");
		Assert.assertEquals("" + cobj.getCoveredLines().get(48).getNum(), "98");
		Assert.assertEquals("" + cobj.getCoveredLines().get(49).getNum(), "100");
		Assert.assertEquals("" + cobj.getCoveredLines().get(50).getNum(), "101");
		Assert.assertEquals("" + cobj.getCoveredLines().get(51).getNum(), "105");
		Assert.assertEquals("" + cobj.getCoveredLines().get(52).getNum(), "106");
		Assert.assertEquals("" + cobj.getCoveredLines().get(53).getNum(), "107");
		Assert.assertEquals("" + cobj.getCoveredLines().get(54).getNum(), "110");
		Assert.assertEquals("" + cobj.getCoveredLines().get(55).getNum(), "111");
		Assert.assertEquals("" + cobj.getCoveredLines().get(56).getNum(), "121");
		Assert.assertEquals("" + cobj.getCoveredLines().get(57).getNum(), "122");
		Assert.assertEquals("" + cobj.getCoveredLines().get(58).getNum(), "123");
		Assert.assertEquals("" + cobj.getCoveredLines().get(59).getNum(), "124");
		Assert.assertEquals("" + cobj.getCoveredLines().get(60).getNum(), "127");
		Assert.assertEquals("" + cobj.getCoveredLines().get(61).getNum(), "128");
		Assert.assertEquals("" + cobj.getCoveredLines().get(62).getNum(), "129");
		Assert.assertEquals("" + cobj.getCoveredLines().get(63).getNum(), "130");
		Assert.assertEquals("" + cobj.getCoveredLines().get(64).getNum(), "132");
		Assert.assertEquals("" + cobj.getCoveredLines().get(65).getNum(), "133");
		Assert.assertEquals("" + cobj.getCoveredLines().get(66).getNum(), "134");
		Assert.assertEquals("" + cobj.getCoveredLines().get(67).getNum(), "135");
		Assert.assertEquals("" + cobj.getCoveredLines().get(68).getNum(), "139");
		Assert.assertEquals("" + cobj.getCoveredLines().get(69).getNum(), "140");
		Assert.assertEquals("" + cobj.getCoveredLines().get(70).getNum(), "141");
		Assert.assertEquals("" + cobj.getCoveredLines().get(71).getNum(), "148");
		Assert.assertEquals("" + cobj.getCoveredLines().get(72).getNum(), "149");
		Assert.assertEquals("" + cobj.getCoveredLines().get(73).getNum(), "150");
		Assert.assertEquals("" + cobj.getCoveredLines().get(74).getNum(), "151");
		Assert.assertEquals("" + cobj.getCoveredLines().get(75).getNum(), "154");
		Assert.assertEquals("" + cobj.getCoveredLines().get(76).getNum(), "157");
	}

	private List<String> createTestData() {
		List<String> data = new ArrayList<String>();
		data.add("public class CalendarService {");
		data.add("  ");
		data.add("    GoogleService service = new GoogleService('Calendar');");
		data.add("    public void setAuthSubToken(string t) { service.AuthSubToken = t;   }");
		data.add("");
		data.add("    public static final string defaultFeed = 'https://www.google.com/calendar/feeds/default';"); // 6
		data.add("    public static final string allCalendars = 'https://www.google.com/calendar/feeds/default/allcalendars/full';");
		data.add("    public static final string ownCalendars = 'https://www.google.com/calendar/feeds/default/owncalendars/full';");
		data.add("    ");
		data.add("    public Boolean useClientLogin(String username, String password)"); // 10
		data.add("	{");
		data.add("		return service.authenticateWithClientLogin(username, password, 'cl');	");
		data.add("	}");
		data.add("	");
		data.add("    public void makePutRequest( string editUrl, string body) {");
		data.add("        service.makePutRequest( editUrl, body);");
		data.add("    }");
		data.add("    ");
		data.add("    public GoogleData getFeed(string url) { return service.getFeed(url); }");
		data.add("    ");
		data.add("    public GoogleData getAllCalendarsFeed() {");
		data.add("        return service.getFeed(CalendarService.allCalendars); ");
		data.add("    }");
		data.add("    ");
		data.add("    public GoogleData getOwnCalendarsFeed() {");
		data.add("        return service.getFeed(CalendarService.ownCalendars); ");
		data.add("    } ");
		data.add("     ");
		data.add("    public GoogleData.Calendar getCalendarByTitle( string title ) {  ");
		data.add("        GoogleData feed = service.getFeed(CalendarService.allCalendars);");
		data.add("        for (xmldom.element ee: feed.entries ) { ");
		data.add("            // system.debug ( GoogleData.getTitle(ee) );");
		data.add("            if ( ee.getValue('title').startsWith (title) ) {  ");
		data.add("                return new GoogleData.Calendar(ee); ");
		data.add("            }");
		data.add("        } ");
		data.add("        return null; ");
		data.add("    } ");
		data.add("    ");
		data.add("    // helper to get events in a range, important if the calendar is large ( many events)");
		data.add("    public GoogleData getEventsRange( GoogleData.Calendar c, datetime min, datetime max) { ");
		data.add("        GoogleData evs_range = service.getFeed( c.alternate + ");
		data.add("             '?start-min='+ GoogleData.dateTimeToString( min ) +");
		data.add("             '&start-max='+  GoogleData.dateTimeToString( max ) + ");
		data.add("             '&orderby=starttime' ); ");
		data.add("         return evs_range;          ");
		data.add("    }");
		data.add("    ");
		data.add("    //  batch insert");
		data.add("    /* ");
		data.add("    <feed xmlns='http://www.w3.org/2005/Atom' ");
		data.add("      xmlns:batch='http://schemas.google.com/gdata/batch'");
		data.add("      xmlns:gCal='http://schemas.google.com/gCal/2005'>");
		data.add("      <category scheme='http://schemas.google.com/g/2005#kind' term='http://schemas.google.com/g/2005#event' />");
		data.add("      <entry>");
		data.add("        <batch:id>1</batch:id>");
		data.add("        <batch:operation type='insert' />");
		data.add("        <category scheme='http://schemas.google.com/g/2005#kind' term='http://schemas.google.com/g/2005#event' />");
		data.add("        <title type='text'>Event inserted via batch</title>");
		data.add("      </entry>"); //
		data.add("      <entry>");
		data.add("    */ ");
		data.add("     ");
		data.add("    public xmldom.element insertEvents( GoogleData.Calendar c, list<Event> evtList) {"); // 64
		data.add("        xmldom.element feed = new xmldom.element('feed'); "); // 65
		data.add("        feed.attributes.put( 'xmlns','http://www.w3.org/2005/Atom');");
		data.add("        feed.attributes.put( 'xmlns:batch','http://schemas.google.com/gdata/batch');");
		data.add("        feed.attributes.put( 'xmlns:gCal','http://schemas.google.com/gCal/2005');");
		data.add("        feed.attributes.put( 'xmlns:gd','http://schemas.google.com/g/2005');");
		data.add("        // <category scheme='http://schemas.google.com/g/2005#kind' term='http://schemas.google.com/g/2005#event' />");
		data.add("        xmldom.element cat =  new xmldom.element('category');");
		data.add("        cat.attributes.put( 'scheme','http://schemas.google.com/g/2005#kind');");
		data.add("        cat.attributes.put( 'term','http://schemas.google.com/g/2005#event');");
		data.add("        feed.appendChild(cat);");
		data.add("");
		data.add("        // build an entry for each event in the list");
		data.add("        integer batchid = 1;");
		data.add("        for ( Event e: evtList) {");
		data.add("            xmldom.element entry =new xmldom.element('entry'); ");
		data.add("            entry.appendChild( Googledata.createTextNode ( 'title',e.subject) );");
		data.add("            entry.appendChild( Googledata.createTextNode ( 'content',e.description) );");
		data.add("            // TODO support for recurring events");
		data.add("            ");
		data.add("            // construct start and end times");
		data.add("            xmldom.element ewhen = new xmldom.element('gd:when');");
		data.add("            ewhen.attributes.put('startTime',GoogleData.dateTimeToString(e.activityDateTime));");
		data.add("            datetime endtime = e.activityDateTime.addMinutes(e.DurationInMinutes);");
		data.add("            ewhen.attributes.put('endTime',GoogleData.dateTimeToString(endtime));");
		data.add("            entry.appendChild(ewhen); ");
		data.add("            ");
		data.add("            // add batch info to the element ");
		data.add("            xmldom.element bid = GoogleData.makeElement( 'batch:id',string.valueof( batchid ) );");
		data.add("            entry.appendchild( bid ); ");
		data.add("            xmldom.element bop =  new xmldom.element('batch:operation');");
		data.add("            bop.attributes.put('type','insert');");
		data.add("            entry.appendChild( bop );");
		data.add("                    ");
		data.add("            batchid++; // increment the batch counter");
		data.add("            ");
		data.add("            entry.dumpall();");
		data.add("            feed.appendChild( entry ); ");
		data.add("        }");
		data.add("        ");
		data.add("        // take the entries list, wrap in a feed");
		data.add("        string body = feed.toXmlString(); ");
		data.add("        service.makePostRequest( c.alternate + '/batch', body );");
		data.add("        return service.responseXml.root; ");
		data.add("    } ");
		data.add("    ");
		data.add("    public xmldom.element insertEvent( GoogleData.Calendar c, Event evt) {");
		data.add("        return insertEvents( c, new list<Event>{evt}  );");
		data.add("    }");
		data.add("    ");
		data.add("    /*    ");
		data.add("    public xmldom.element insertEvent( GoogleData.Calendar c, string body) {");
		data.add("        service.makePostRequest( c.alternate, body );");
		data.add("        return  service.responseXml.root ; // response is an entry, not feed");
		data.add("    } ");
		data.add("    */");
		data.add("    ");
		data.add("    public xmldom.element insertCalendar( GoogleData.Calendar c) {");
		data.add("        string body = c.toXmlString(); ");
		data.add("        service.makePostRequest( CalendarService.ownCalendars, c.toXmlString() );");
		data.add("        return  service.responseXml.root ; // response is an entry, not feed");
		data.add("    } ");
		data.add("    ");
		data.add("    public void removeEvent( xmldom.element ev ) {");
		data.add("        string editUrl  = GoogleData.getRelLink(ev,'edit');");
		data.add("        if ( editUrl == null ) { system.debug( 'ERROR missing edit url'); return ; }");
		data.add("        service.makeDeleteRequest( editUrl );");
		data.add("    }");
		data.add("    public void remove( xmldom.element obj) {");
		data.add("        string editUrl  = GoogleData.getRelLink(obj,'edit');");
		data.add("        if ( editUrl == null ) { system.debug( 'ERROR missing edit url'); return ; }");
		data.add("        service.makeDeleteRequest( editUrl );");
		data.add("    }");
		data.add("    ");
		data.add("    // updates use PUT");
		data.add("    public void updateEvent( xmldom.element ev ) {");
		data.add("        string editUrl  = GoogleData.getRelLink(ev,'edit');");
		data.add("        if ( editUrl == null ) { system.debug( 'ERROR missing edit url'); return ; }");
		data.add("        /*");
		data.add("         * for events, we need to construct the required namespaces on the entry passed in");
		data.add("         * these were on the feed, but here we have only an entry to update, add the ");
		data.add("         * attributes we need looks like this: ");
		data.add("         * <entry xmlns='http://www.w3.org/2005/Atom' xmlns:gd='http://schemas.google.com/g/2005'...");
		data.add("         */");
		data.add("        xmldom.element node = ev.getElementByTagName('entry');");
		data.add("        GoogleData.addNameSpace(node); ");
		data.add("        GoogleData.addAPINameSpace(node, 'xmlns:gCal','http://schemas.google.com/gCal/2005');");
		data.add("        string body = ev.toXmlString();");
		data.add("        system.debug( body); ");
		data.add("         ");
		data.add("        service.makePutRequest( editUrl, body);");
		data.add("    }");
		data.add("    ");
		data.add("    public HttpResponse response { get { return service.response; } } ");
		data.add("         ");
		data.add("}");
		return data;
	}
}
