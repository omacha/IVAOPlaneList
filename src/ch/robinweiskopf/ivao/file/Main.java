package ch.robinweiskopf.ivao.file;

import java.io.File;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		if(args.length < 2) {
			System.out.println(Helper.message());
			return;
		}
		
		Scanner sc = new Scanner(System.in);
		
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
		
		if(!getFileType(input).toLowerCase().equals("dat")) {
		    System.out.print(" Input file type is not DAT. Do you want to continue (Y/N) ? ");
		    String answer = sc.nextLine();
		    if(!answer.toLowerCase().equals("y")) {
		        System.out.println(" Aborting...");
		        sc.close();
		        return;
		    }
		}
		
		if(!getFileType(output).toLowerCase().equals("vmr")) {
		    System.out.print(" Output file type is not VMR. Do you want to continue (Y/N) ? ");
		    String answer = sc.nextLine();
		    if(!answer.toLowerCase().equals("y")) {
		        System.out.println(" Aborting...");
		        sc.close();
		        return;
		    }
		}
		
		if(!getFileType(error).toLowerCase().equals("log")) {
            System.out.print(" Output file type is not LOG. Do you want to continue (Y/N) ? ");
            String answer = sc.nextLine();
            if(!answer.toLowerCase().equals("y")) {
                System.out.println(" Aborting...");
                sc.close();
                return;
            }
        }
		
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
			System.out.println("	" + e.getMessage());
		}
		
		sc.close();
		
	}
	
	private static String getFileType(File file) {
	    String ftype = file.getName();
	    int dotPos = ftype.lastIndexOf(".");
	    if(dotPos == -1 || dotPos == ftype.length()-1) {
	        return "";
	    } else {
	        return ftype.substring(dotPos+1);
	    }
	}

}
