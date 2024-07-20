package com.ecole.scolaire.exceptions;

public class ValidationExceptions extends RuntimeException{
    public ValidationExceptions(String message) {
        super(message);
    }

    public static class EtudiantValidationException extends ValidationExceptions {
        public EtudiantValidationException(String message) {
            super(message);
        }
    }

    public static class InvalidDateException extends ValidationExceptions {
        public InvalidDateException(String message) {
            super(message);
        }
    }

    public static class InvalidAnneeScolaireException extends ValidationExceptions {
        public InvalidAnneeScolaireException(String message) {
            super(message);
        }
    }

    public static class MissingFieldException extends ValidationExceptions {
        public MissingFieldException(String message) {
            super(message);
        }
    }

    public static class NotFoundException extends ValidationExceptions {
        public NotFoundException(String message) {
            super(message);
        }
    }
}
