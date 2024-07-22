package com.ecole.scolaire.mapper;

import com.ecole.scolaire.dtos.PeriodeInscriptionDto;
import com.ecole.scolaire.entity.PeriodeInscription;
import org.springframework.stereotype.Component;

@Component
public class PeriodeInscriptionMapper {
    public PeriodeInscriptionDto toDto(PeriodeInscription periodeInscription) {
        return PeriodeInscriptionDto.builder()
                .idDto(periodeInscription.getId())
                .anneeScolaireDto(periodeInscription.getAnneeScolaire())
                .dateDebutDto(periodeInscription.getDateDebut())
                .dateFinDto(periodeInscription.getDateFin())
                .statutDto(periodeInscription.getStatut())
                .inscriptionsDto(periodeInscription.getInscriptions())
                .etatDto(periodeInscription.isEtat())
                .build();
    }

    public PeriodeInscription toEntity(PeriodeInscriptionDto periodeInscriptionDto) {
        return PeriodeInscription.builder()
                .id(periodeInscriptionDto.getIdDto())
                .anneeScolaire(periodeInscriptionDto.getAnneeScolaireDto())
                .dateDebut(periodeInscriptionDto.getDateDebutDto())
                .dateFin(periodeInscriptionDto.getDateFinDto())
                .statut(periodeInscriptionDto.getStatutDto())
                .inscriptions(periodeInscriptionDto.getInscriptionsDto())
                .etat(periodeInscriptionDto.isEtatDto())
                .build();
    }
}
