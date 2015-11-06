package ch.robinweiskopf.ivao.file;

public class Helper {
    
    private Helper() {
        
    }
    
    public static String message() {
        String separator = System.getProperty("line.separator");
        StringBuilder msg = new StringBuilder();
        msg.append("Usage: java -jar FileRewriter.jar READFILE.dat OUTPUTFILE.mvr [ERRORFILE.dat]");
        msg.append(separator);
        msg.append("  INPUTFILE.dat:   File to be parsed and rewritten.");
        msg.append(separator);
        msg.append("  OUTPUTFILE.mvr: Output");
        msg.append(separator);
        msg.append("                  If this file already exists, it will be overwritten.");
        msg.append(separator);
        msg.append("  ERROR.log:      File which logs all parsing errors" + 
        		   "                  (optionnal: default \"ERROR.log\")");
        msg.append(separator);
        return msg.toString();
    }
    
}
