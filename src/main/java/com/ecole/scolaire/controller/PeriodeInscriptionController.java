package com.ecole.scolaire.controller;

import com.ecole.scolaire.dtos.PeriodeInscriptionDto;
import com.ecole.scolaire.entity.PeriodeInscription;
import com.ecole.scolaire.services.PeriodeInscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/periodesIns")
public class PeriodeInscriptionController {
    @Autowired
    private PeriodeInscriptionService periodeInscriptionService;

    @PostMapping("/add")
    public ResponseEntity<PeriodeInscription> addPeriodeInscription(@RequestBody PeriodeInscriptionDto periodeInscriptionDto) {
        PeriodeInscription periodeInscription = periodeInscriptionService.addPeriodeInscription(periodeInscriptionDto);
        return ResponseEntity.status(201).body(periodeInscription);
    }
}