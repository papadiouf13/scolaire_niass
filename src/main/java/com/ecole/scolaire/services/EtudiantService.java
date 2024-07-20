package com.ecole.scolaire.services;

import com.ecole.scolaire.dtos.EtudiantDto;

import java.util.List;

public interface EtudiantService {
    EtudiantDto addEtudiant(EtudiantDto etudiantDto);

    List<EtudiantDto> getAllEtudiants();
    List<EtudiantDto> getEtudiantsByMatricule(String matricule);
}
