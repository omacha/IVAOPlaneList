package ch.robinweiskopf.ivao.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.io.Writer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileParser {
	
	private File input;
	private File output;
	private File error;
	
	private boolean executed = false;
	
	public FileParser(File input, File output, File error) {
		this.input = input;
		this.output = output;
		this.error = error;
	}
	
	public int execute()
		throws FileParserException
	{
		if(executed){
			throw new FileParserException("Already executed this instance.");
		}
		
		if(!input.exists()) {
			throw new FileParserException("Input file does not exist.");
		} else if(!input.canRead()) {
			throw new FileParserException("Can't read input file!");
		}
		
		if(output.exists()) {
			try {
				output.delete();
			} catch(SecurityException e) {
				throw new FileParserException("Can't overwrite output file.");
			}
		}
		try {
			output.createNewFile();
		} catch(IOException e) {
			throw new FileParserException("Can't not create output file.");
		}
		if(!output.canWrite()) {
			try {
				output.setWritable(true);
			} catch(SecurityException e) {
				throw new FileParserException("Can't write to output file!");
			}
		}
		
		int errorCount = 0;
		
		final int TOTAL_LINES = countLines(input);
		errorCount = TOTAL_LINES - TOTAL_LINES;
		
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(input));
		} catch (FileNotFoundException e) {
			throw new FileParserException("Could not read file.");
		}
		
		
		FileBuffer fbuff;
		try {
			fbuff = new FileBuffer(output);
		} catch (IOException e1) {
			closeStream(reader);
			throw new FileParserException("Could not open output file to write.");
		}
		
		BufferedWriter errorWriter;
		try {
			errorWriter = new BufferedWriter(new FileWriter(error));
		} catch (IOException e2) {
			closeStream(reader);
			closeStream(fbuff);
			throw new FileParserException("Could not open error file to write.");
		}
		
		final String SEP = System.getProperty("line.separator");
		
		try {
			String line;
			int lineNumber = 1;
			while((line = reader.readLine()) != null) {
				try {
					FileLine fline = new FileLine(line, lineNumber);
					fbuff.add(fline);
				} catch(ParseException e) {
					String date = getCurrentTime();
					errorWriter.write(date + " / in " + input.getName() + " / line " + lineNumber + ": " + e.getMessage() + SEP);
					++errorCount;
				} catch(IOException e) {
					String date = getCurrentTime();
					errorWriter.write(date + " / in " + input.getName() + " / line " + lineNumber + ": " + e.getMessage() + SEP);
					++errorCount;
				}
				++lineNumber;
			}
		} catch (IOException e) {
			closeStream(reader);
			closeStream(fbuff);
			closeStream(errorWriter);
			throw new FileParserException("An unexpected IO error occured during parsing!");
		}
		
		closeStream(reader);
		closeStream(fbuff);
		closeStream(errorWriter);
		
		executed = true;
		return errorCount;
		
	}
	
	private String getCurrentTime() {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date now = new Date();
		
		return df.format(now);
	}
	
	private void closeStream(Reader stream) {
		try {
			if(stream != null) {
				stream.close();
			}
		} catch(Exception e) {
			
		}
	}
	
	private void closeStream(Writer stream) {
		try {
			if(stream != null) {
				stream.close();
			}
		} catch(Exception e) {
			
		}
	}
	
	private void closeStream(FileBuffer fb) {
		try {
			if(fb != null) {
				fb.close();
			}
		} catch(Exception e) {
			
		}
	}
	
	private int countLines(File file) {
		LineNumberReader lnr = null;
		try {
			lnr = new LineNumberReader(new FileReader(file));
			while(lnr.readLine() != null);
			return lnr.getLineNumber();
		} catch(IOException e) {
			return -1;
		} finally {
			if(lnr != null) {
				try {
					lnr.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
}
