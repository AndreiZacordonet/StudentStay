package dev.studentstay.Documente.exceptions;

public class DocumentNotFoundException extends RuntimeException{
    public DocumentNotFoundException(String msg) {
        super(msg);
    }
}
