package com.ecole.scolaire.controller;

import com.ecole.scolaire.dtos.FiliereDto;
import com.ecole.scolaire.entity.Filiere;
import com.ecole.scolaire.exceptions.AlreadyExistsExceptions;
import com.ecole.scolaire.exceptions.ValidationExceptions;
import com.ecole.scolaire.services.FiliereService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
@Tag(name = "Filiere", description = "Filiere API")
@RequestMapping("/api/filieres")
public class FiliereController {
    @Autowired
    private FiliereService filiereService;

    @GetMapping
    public ResponseEntity<List<FiliereDto>> getAllFilieres() {
        List<FiliereDto> filiereDtos = filiereService.getAllFilieres();
        return new ResponseEntity<>(filiereDtos, HttpStatus.OK);
    }
    @PostMapping("add")
    public ResponseEntity<?> createFiliere(@RequestBody FiliereDto filiereDto) {
        try {
            Filiere filiere = new Filiere();
            filiere.setLibelle(filiereDto.getLibelleDto());
            Filiere savedFiliere = filiereService.saveFiliere(filiere);
            return new ResponseEntity<>(savedFiliere, HttpStatus.CREATED);
        } catch (AlreadyExistsExceptions.FiliereAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Filiere> updateFiliere(@PathVariable Long id, @RequestBody FiliereDto filiereDto) {
        Filiere updatedFiliere = filiereService.updateFiliere(id, filiereDto);
        return ResponseEntity.ok(updatedFiliere);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFiliere(@PathVariable Long id) {
        try {
            filiereService.deleteFiliere(id);
            return ResponseEntity.ok("Filiere supprimée avec succès.");
        } catch (ValidationExceptions.NotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
