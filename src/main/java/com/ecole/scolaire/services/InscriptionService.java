package com.ecole.scolaire.services;

import com.ecole.scolaire.dtos.InscriptionDto;
import com.ecole.scolaire.entity.Inscription;
import com.ecole.scolaire.mapper.InscriptionMapper;

import java.util.List;

public interface InscriptionService {
    Inscription saveInscription(InscriptionDto inscriptionDto);
    List<InscriptionDto> getAllInscriptions();
    InscriptionMapper getMapper();
    List<InscriptionDto> getAllInscriptionsByClasse(Long classeId);
    //InscriptionDto getInscriptionByEtudiant(String matricule);
    List<InscriptionDto> getInscriptionByEtudiant(String matricule);

}
