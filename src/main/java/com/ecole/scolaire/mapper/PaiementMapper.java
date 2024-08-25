package com.ecole.scolaire.mapper;

import com.ecole.scolaire.dtos.PaiementDto;
import com.ecole.scolaire.entity.Inscription;
import com.ecole.scolaire.entity.Paiement;
import com.ecole.scolaire.entity.TypePaiement;
import org.springframework.stereotype.Component;

@Component
public class PaiementMapper {
    public PaiementDto entityToDto(Paiement paiement) {
        return PaiementDto.builder()
                .idDto(paiement.getId())
                .montantDto(paiement.getMontant())
                .datePaiementDto(paiement.getDatePaiement())
                .typePaiementDto(paiement.getTypePaiement().name())
                .inscriptionIdDto(paiement.getInscription().getId())
                .etatDto(paiement.isEtat())
                .build();
    }

    public Paiement dtoToEntity(PaiementDto paiementDto, Inscription inscription) {
        return Paiement.builder()
                .montant(paiementDto.getMontantDto())
                .datePaiement(paiementDto.getDatePaiementDto())
                .typePaiement(TypePaiement.valueOf(paiementDto.getTypePaiementDto()))
                .inscription(inscription)
                .etat(paiementDto.isEtatDto())
                .build();
    }
}
