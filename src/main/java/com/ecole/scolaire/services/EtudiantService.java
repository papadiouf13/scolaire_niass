package com.ecole.scolaire.services;

import com.ecole.scolaire.dtos.EtudiantDto;

import java.util.List;

public interface EtudiantService {
    EtudiantDto addEtudiant(EtudiantDto etudiantDto);
    void deleteEtudiant(Long id);
    List<EtudiantDto> getAllEtudiants();
    void updateEtudiant(Long id, EtudiantDto etudiantDto);
    List<EtudiantDto> getEtudiantsByMatricule(String matricule);
}
