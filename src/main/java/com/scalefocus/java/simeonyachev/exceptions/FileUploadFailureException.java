package com.scalefocus.java.simeonyachev.exceptions;

public class FileUploadFailureException extends RuntimeException {

    public FileUploadFailureException() {
        super("Failed to upload the file.");
    }
}
