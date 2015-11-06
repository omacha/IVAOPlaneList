package ch.robinweiskopf.ivao.file;

import java.text.ParseException;
import java.util.regex.Pattern;

public class FileLine {
	private final Pattern linePattern = Pattern.compile("[a-zA-Z0-9@ ]+,.+,[0-9\\-\\.]+,[0-9\\-\\.]+,[0-9]+");
	private final Pattern airlineSpecSplitPattern = Pattern.compile("( \\()|(\\) \\(|\\)\\(|\\))");
	
	private final String modelName;
	private final String callSign;
	private final String typeCode;
	private final int lineNumber;
	
	public FileLine(String inputLine, int lineNumber)
		throws ParseException
	{
		this.lineNumber = lineNumber;
		
		if(!linePattern.matcher(inputLine).matches()) {
			throw new ParseException("Invalid input line", lineNumber);
		}
		
		String[] mainParts = inputLine.split(",");
		if(mainParts.length != 5) {
			throw new ParseException("Invalid input line", lineNumber);
		}
		
		String[] airlineSpecParts = airlineSpecSplitPattern.split(mainParts[1]);
		
		modelName = mainParts[0];
		
		// callsign calculation
		if(modelName.length() <= 4) {
			callSign = "";
			typeCode = modelName;
		} else {
			typeCode = modelName.substring(0, 4).trim();
			if(modelName.length() < 7) {
				throw new ParseException("Invalid model name: len >= 4 -> len >= 7", lineNumber);
			}
			String callSignTemp = modelName.substring(4,7);
			String callSignFromSpec;
			if(callSignTemp.startsWith("@")) {
				callSign = "";
			} else if(isCallSign(callSignFromSpec = airlineSpecParts[airlineSpecParts.length-1]) && !callSignFromSpec.startsWith("@")) {
				callSign = callSignFromSpec;
			} else {
				callSign = "";
			}
		}
		
	}
	
	public boolean hasCallSign() {
		return !callSign.equals("");
	}
	
	public String getModelName() {
		return modelName;
	}

	public String getTypeCode() {
		return typeCode;
	}
	
	public String getCallSign() {
		return callSign;
	}

	public int getLineNumber() {
		return lineNumber;
	}
	
	private boolean isCallSign(String callSign) {
		return (callSign.length()==3) && (callSign.toUpperCase().equals(callSign));
	}
	
}