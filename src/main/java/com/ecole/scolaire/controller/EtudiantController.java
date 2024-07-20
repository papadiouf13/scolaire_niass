package com.ecole.scolaire.controller;

import com.ecole.scolaire.dtos.EtudiantDto;
import com.ecole.scolaire.services.EtudiantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("/api/etudiants")
public class EtudiantController {
    @Autowired
    private EtudiantService etudiantService;

    @PostMapping("/add")
    public ResponseEntity<EtudiantDto> addEtudiant(@RequestBody EtudiantDto etudiantDto) {
        EtudiantDto addedEtudiant = etudiantService.addEtudiant(etudiantDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedEtudiant);
    }

    @GetMapping
    public ResponseEntity<List<EtudiantDto>> getAllEtudiants() {
        List<EtudiantDto> allEtudiants = etudiantService.getAllEtudiants();
        return ResponseEntity.ok().body(allEtudiants);
    }

    @GetMapping("/matricule/{matricule}")
    public ResponseEntity<List<EtudiantDto>> getEtudiantsByMatricule(@PathVariable String matricule) {
        List<EtudiantDto> etudiants = etudiantService.getEtudiantsByMatricule(matricule);
        if (etudiants.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(etudiants, HttpStatus.OK);
    }
}
