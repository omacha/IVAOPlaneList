package ch.robinweiskopf.ivao.file;

import java.io.File;

public class Main {

	public static void main(String[] args) {
		
		if(args.length < 2) {
			System.out.println(Helper.message());
			return;
		}
		
		String inputFname = args[0];
		String outputFname = args[1];
		String errorFname;
		
		if(args.length < 3) {
			errorFname = "ERROR.log";
		} else {
			errorFname = args[2];
		}
		
		File input = new File(inputFname);
		File output = new File(outputFname);
		File error = new File(errorFname);
		
		FileParser parser = new FileParser(input, output, error);
		
		try {
			int numErrors = parser.execute();
			
			if(numErrors == 0) {
				System.out.println("No errors during execution.");
				if(error.exists()) {
					error.delete();
				}
			} else {
				System.out.println("There were " + numErrors + " errors during execution, see " + error.getName() + ".");
			}
		} catch(FileParserException e) {
			System.out.println("  Fatal error during execution:");
			System.out.println("    " + e.getMessage());
		}
		
	}

}
