package com.ecole.scolaire.exceptions;

public class GeneralExceptions extends RuntimeException{
    public GeneralExceptions(String message) {
        super(message);
    }

    public static class InvalidClasseDataException extends GeneralExceptions {
        public InvalidClasseDataException(String message) {
            super(message);
        }
    }

    public static class InvalidInscriptionDataException extends GeneralExceptions {
        public InvalidInscriptionDataException(String message) {
            super(message);
        }
    }


}
