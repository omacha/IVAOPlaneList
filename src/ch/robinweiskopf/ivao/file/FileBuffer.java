package ch.robinweiskopf.ivao.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

public class FileBuffer {
	private final String SEP = System.getProperty("line.separator");
	
	private final BufferedWriter output;
	
	private FileLine last = null;
	private HashMap<String, List<FileLine>> buffer = null;
	
	public FileBuffer(File output)
		throws IOException
	{
		if(output == null) {
			throw new NullPointerException("File was null");
		}
		this.output = new BufferedWriter(new FileWriter(output));
		
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
		Date now = new Date();
		
		String currentDatetime = df.format(now);
		
		this.output.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + SEP);
		this.output.write("<ModelMatchRuleSet Folder=\"\" UpdatedOn=\"" + currentDatetime + "\">" + SEP);
	}
	
	public void add(FileLine line)
		throws IOException
	{
		if(last == null || !last.getTypeCode().equals(line.getTypeCode())) {
			if(last != null) {
				writeOut();
			}
			buffer = new HashMap<String, List<FileLine>>();
		}
		
		if(buffer.containsKey(line.getCallSign())) {
			buffer.get(line.getCallSign()).add(line);
		} else {
			List<FileLine> list = new LinkedList<>();
			list.add(line);
			buffer.put(line.getCallSign(), list);
		}
		
		last = line;
	}
	
	private void writeOut()
		throws IOException
	{
		if(buffer != null) {
			for(Entry<String, List<FileLine>> x : buffer.entrySet()) {
				String line;
				if(x.getKey().equals("")) {
					line = "  <ModelMatchRule TypeCode=\"" + x.getValue().get(0).getTypeCode() + "\" ModelName=\"" + concatModelNames(x.getValue()) + "\" />" + SEP;
				} else {
					line = "  <ModelMatchRule CallsignPrefix=\"" + x.getKey() + "\" TypeCode=\"" + x.getValue().get(0).getTypeCode() + "\" ModelName=\"" + concatModelNames(x.getValue()) + "\" />" + SEP;
				}
				output.write(line);
			}
		}
	}
	
	public void close()
		throws IOException
	{
		output.write("</ModelMatchRuleSet>");
		output.close();
	}
	
	private String concatModelNames(List<FileLine> list) {
		StringBuilder result = new StringBuilder();
		
		boolean first = true;
		for(FileLine fl : list) {
			if(first) {
				first = false;
			} else {
				result.append("//");
			}
			result.append(fl.getModelName());
		}
		return result.toString();
	}
	
}
