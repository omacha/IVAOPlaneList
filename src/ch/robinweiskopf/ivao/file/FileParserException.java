package ch.robinweiskopf.ivao.file;

public class FileParserException extends Exception {

    /**
     * Serial Version ID.
     */
    private static final long serialVersionUID = -8224005427629389747L;

    public FileParserException() {
        super();
    }
    
    public FileParserException(String string) {
        super(string);
    }
    
    public FileParserException(Exception e) {
        super(e);
    }

}
