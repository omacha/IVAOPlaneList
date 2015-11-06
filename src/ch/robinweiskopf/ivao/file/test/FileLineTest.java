package ch.robinweiskopf.ivao.file.test;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;

import org.junit.Test;

import ch.robinweiskopf.ivao.file.FileLine;

public class FileLineTest {
	
	@Test
	public void workingLineParser() 
		throws ParseException
	{
		String line = "A306AAW,Afriqiyah Airways (AAW),16.72,-1.02,1";
		int lineNumber = 252;
		
		FileLine fline = new FileLine(line, lineNumber);
		
		assertEquals("A306AAW", fline.getModelName());
		assertEquals("A306", fline.getTypeCode());
		assertEquals(true, fline.hasCallSign());
		assertEquals("AAW", fline.getCallSign());
		assertEquals(lineNumber, fline.getLineNumber());
	}
	
	@Test
	public void workingLineParser2() 
		throws ParseException
	{
		String line = "A306CSNCRG,China Southern Airlines (cargo) (CSN),16.72,-1.02,0";
		int lineNumber = 2232;
		
		FileLine fline = new FileLine(line, lineNumber);
		
		assertEquals("A306CSNCRG", fline.getModelName());
		assertEquals("A306", fline.getTypeCode());
		assertEquals(true, fline.hasCallSign());
		assertEquals("CSN", fline.getCallSign());
		assertEquals(lineNumber, fline.getLineNumber());
	}
	
	@Test
	public void workingLineParserPrivate() 
		throws ParseException
	{
		String line = "E135ZZZ,Privately owned (Intel Corp.),4.4,-1,0";
		int lineNumber = 2349;
		
		FileLine fline = new FileLine(line, lineNumber);
		
		assertEquals("E135ZZZ", fline.getModelName());
		assertEquals("E135", fline.getTypeCode());
		assertEquals(false, fline.hasCallSign());
		assertEquals(lineNumber, fline.getLineNumber());
	}
	
	@Test
	public void workingLineParserPrivate2() 
		throws ParseException
	{
		String line = "E135,Privately owned (Intel Corp.),4.4,-1,0";
		int lineNumber = 2350;
		
		FileLine fline = new FileLine(line, lineNumber);
		
		assertEquals("E135", fline.getModelName());
		assertEquals("E135", fline.getTypeCode());
		assertEquals(false, fline.hasCallSign());
		assertEquals(lineNumber, fline.getLineNumber());
	}
	
	@Test
	public void workingLineParserPrivate3() 
		throws ParseException
	{
		String line = "A10,Default,5.68,-0.68,0";
		int lineNumber = 2351;
		
		FileLine fline = new FileLine(line, lineNumber);
		
		assertEquals("A10", fline.getModelName());
		assertEquals("A10", fline.getTypeCode());
		assertEquals(lineNumber, fline.getLineNumber());
	}
	
	@Test
	public void workingLineParserPrivate4() 
		throws ParseException
	{
		String line = "A319@RT,Royal Thai Air Force,7.95,-0.4,0";
		int lineNumber = 2351;
		
		FileLine fline = new FileLine(line, lineNumber);
		
		assertEquals("A319@RT", fline.getModelName());
		assertEquals("A319", fline.getTypeCode());
		assertEquals(false, fline.hasCallSign());
		assertEquals(lineNumber, fline.getLineNumber());
	}
	
	@Test(expected=ParseException.class)
	public void invalidFileLine()
		throws ParseException
	{
		String line = "E135adc,fawnfawfaf,4.4,-1";
		int lineNumber = 1;
		
		@SuppressWarnings("unused")
		FileLine fline = new FileLine(line, lineNumber);
	}
	
	@Test(expected=ParseException.class)
	public void invalidFileLine2()
		throws ParseException
	{
		String line = "E135ab,Intel (xyz),4.4,-1,0";
		int lineNumber = 1;
		
		@SuppressWarnings("unused")
		FileLine fline = new FileLine(line, lineNumber);
	}
	
}
