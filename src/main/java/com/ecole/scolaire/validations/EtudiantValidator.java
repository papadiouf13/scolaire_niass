package com.ecole.scolaire.validations;

import com.ecole.scolaire.dtos.EtudiantDto;
import com.ecole.scolaire.exceptions.ValidationExceptions;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

@Component
public class EtudiantValidator {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+221\\d{9}$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z]+$");

    public void validate(EtudiantDto etudiantDto) {
        validateEmail(etudiantDto.getEmailDto());
        validatePhone(etudiantDto.getTelephoneDto());
        validateAge(etudiantDto.getDateNaissanceDto());
        validateName(etudiantDto.getNomDto());
        validateName(etudiantDto.getPrenomDto());
    }

    private void validateEmail(String email) {
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new ValidationExceptions.EtudiantValidationException("Email invalide");
        }
    }

    private void validatePhone(String phone) {
        if (!PHONE_PATTERN.matcher(phone).matches()) {
            throw new ValidationExceptions.EtudiantValidationException("Le téléphone doit être au format +221 suivi de 9 chiffres");
        }
    }

    private void validateAge(String dateNaissance) {
        LocalDate birthDate = LocalDate.parse(dateNaissance);
        int age = Period.between(birthDate, LocalDate.now()).getYears();
        if (age < 18) {
            throw new ValidationExceptions.EtudiantValidationException("L'étudiant doit avoir au moins 18 ans");
        }
    }

    private void validateName(String name) {
        if (!NAME_PATTERN.matcher(name).matches()) {
            throw new ValidationExceptions.EtudiantValidationException("Le nom et le prénom doivent contenir uniquement des lettres");
        }
    }
}
