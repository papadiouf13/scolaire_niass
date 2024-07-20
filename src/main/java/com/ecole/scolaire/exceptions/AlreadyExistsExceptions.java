package com.ecole.scolaire.exceptions;

public class AlreadyExistsExceptions extends RuntimeException{
    public AlreadyExistsExceptions(String message) {
        super(message);
    }

    public static class EtudiantAlreadyExistsException extends AlreadyExistsExceptions {
        public EtudiantAlreadyExistsException(String message) {
            super(message);
        }
    }

    public static class ClasseAlreadyExistsException extends AlreadyExistsExceptions {
        public ClasseAlreadyExistsException(String message) {
            super(message);
        }
    }

    public static class FiliereAlreadyExistsException extends AlreadyExistsExceptions {
        public FiliereAlreadyExistsException(String message) {
            super(message);
        }
    }
}
