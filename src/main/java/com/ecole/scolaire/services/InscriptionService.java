package com.ecole.scolaire.services;

import com.ecole.scolaire.dtos.InscriptionDto;
import com.ecole.scolaire.entity.Inscription;
import com.ecole.scolaire.mapper.InscriptionMapper;

import java.util.List;

public interface InscriptionService {
    Inscription saveInscription(InscriptionDto inscriptionDto);
    List<InscriptionDto> getAllInscriptions();
    InscriptionMapper getMapper(); // Add this method declaration
}
