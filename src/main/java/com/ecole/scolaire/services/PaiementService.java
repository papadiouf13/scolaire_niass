package com.ecole.scolaire.services;

import com.ecole.scolaire.dtos.PaiementDto;
import com.ecole.scolaire.entity.Paiement;

import java.util.List;

public interface PaiementService {
    PaiementDto createPaiement(PaiementDto paiementDto);
    List<PaiementDto> getAllPaiementsByInscription(Long inscriptionId);
    double calculateTotalPaid(Long inscriptionId);
    List<PaiementDto> getAllPaiements();
    List<Paiement> getPaiementsByEtudiantId(Long etudiantId);

}
