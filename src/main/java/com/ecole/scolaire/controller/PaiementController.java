package com.ecole.scolaire.controller;

import com.ecole.scolaire.dtos.PaiementDto;
import com.ecole.scolaire.entity.Paiement;
import com.ecole.scolaire.services.PaiementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/paiements")
@CrossOrigin("*")
public class PaiementController {
    @Autowired
    private PaiementService paiementService;

    @PostMapping("/add")
    public ResponseEntity<Object> createPaiement(@RequestBody PaiementDto paiementDto) {
        try {
            PaiementDto paiement = paiementService.createPaiement(paiementDto);
            return new ResponseEntity<>(paiement, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("FI BAXOUL WAY...", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/inscription/{inscriptionId}")
    public ResponseEntity<List<PaiementDto>> getAllPaiementsByInscription(@PathVariable Long inscriptionId) {
        List<PaiementDto> paiements = paiementService.getAllPaiementsByInscription(inscriptionId);
        return new ResponseEntity<>(paiements, HttpStatus.OK);
    }

    @GetMapping
    public List<PaiementDto> getAllPaiements() {
        return paiementService.getAllPaiements();
    }

    @GetMapping("/etudiant/{etudiantId}")
    public ResponseEntity<List<Paiement>> getPaiementsByEtudiantId(@PathVariable Long etudiantId) {
        List<Paiement> paiements = paiementService.getPaiementsByEtudiantId(etudiantId);
        if (paiements.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(paiements);
    }
}
