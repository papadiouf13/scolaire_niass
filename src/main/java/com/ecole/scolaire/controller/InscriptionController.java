package com.ecole.scolaire.controller;

import com.ecole.scolaire.dtos.InscriptionDto;
import com.ecole.scolaire.entity.Inscription;
import com.ecole.scolaire.exceptions.GeneralExceptions;
import com.ecole.scolaire.exceptions.ValidationExceptions;
import com.ecole.scolaire.services.InscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/inscriptions")
@CrossOrigin("*")
public class InscriptionController {
    @Autowired
    private InscriptionService inscriptionService;

    @PostMapping("/add")
    public ResponseEntity<Object> createInscription(@RequestBody InscriptionDto inscriptionDto) {
        try {
            Inscription inscription = inscriptionService.saveInscription(inscriptionDto);
            return new ResponseEntity<>(inscriptionService.getMapper().entityInscriptionToInscriptionDto(inscription), HttpStatus.CREATED);
        } catch (ValidationExceptions.MissingFieldException | GeneralExceptions.InvalidInscriptionDataException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Une erreur est survenue. Veuillez réessayer plus tard.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<InscriptionDto>> getAllInscriptions() {
        List<InscriptionDto> inscriptions = inscriptionService.getAllInscriptions();
        return new ResponseEntity<>(inscriptions, HttpStatus.OK);
    }

    @ExceptionHandler(ValidationExceptions.MissingFieldException.class)
    public ResponseEntity<String> handleMissingFieldException(ValidationExceptions.MissingFieldException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(GeneralExceptions.InvalidInscriptionDataException.class)
    public ResponseEntity<String> handleInvalidInscriptionDataException(GeneralExceptions.InvalidInscriptionDataException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur est survenue. Veuillez réessayer plus tard.");
    }
}
