package com.ecole.scolaire.controller;

import com.ecole.scolaire.dtos.ClasseDto;
import com.ecole.scolaire.entity.Classe;
import com.ecole.scolaire.exceptions.AlreadyExistsExceptions;
import com.ecole.scolaire.exceptions.GeneralExceptions;
import com.ecole.scolaire.services.ClasseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("/api/classes")
public class ClasseController {
    @Autowired
    private ClasseService classeService;

    @PostMapping("add")
    public ResponseEntity<?> createClasse(@RequestBody ClasseDto classeDto) {
        try {
            Classe savedClasse = classeService.saveClasse(classeDto);
            return new ResponseEntity<>(savedClasse, HttpStatus.CREATED);
        } catch (AlreadyExistsExceptions.ClasseAlreadyExistsException | GeneralExceptions.InvalidClasseDataException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping
    public ResponseEntity<List<ClasseDto>> getAllClasses() {
        List<ClasseDto> classeDtos = classeService.getAllClasses();
        return new ResponseEntity<>(classeDtos, HttpStatus.OK);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> updateClasse(@PathVariable Long id, @RequestBody ClasseDto classeDto) {
        try {
            Classe updatedClasse = classeService.updateClasse(id, classeDto);
            return new ResponseEntity<>(updatedClasse, HttpStatus.OK);
        } catch (GeneralExceptions.InvalidClasseDataException | AlreadyExistsExceptions.ClasseAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteClasse(@PathVariable Long id) {
        try {
            classeService.deleteClasse(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (GeneralExceptions.InvalidClasseDataException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
