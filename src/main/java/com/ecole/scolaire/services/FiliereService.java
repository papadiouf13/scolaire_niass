package com.ecole.scolaire.services;

import com.ecole.scolaire.dtos.FiliereDto;
import com.ecole.scolaire.entity.Filiere;
import org.springframework.stereotype.Service;

import java.util.List;


public interface FiliereService {
    Filiere saveFiliere(Filiere filiere);
    List<FiliereDto> getAllFilieres();

    Filiere updateFiliere(Long id, FiliereDto filiereDto);
    void deleteFiliere(Long id);
}
