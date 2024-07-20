package com.ecole.scolaire.mapper;

import com.ecole.scolaire.dtos.PeriodeInscriptionDto;
import com.ecole.scolaire.entity.PeriodeInscription;
import org.springframework.stereotype.Component;

@Component
public class PeriodeInscriptionMapper {
    public PeriodeInscriptionDto toDto(PeriodeInscription periodeInscription) {
        return PeriodeInscriptionDto.builder()
                .id(periodeInscription.getId())
                .anneeScolaire(periodeInscription.getAnneeScolaire())
                .dateDebut(periodeInscription.getDateDebut())
                .dateFin(periodeInscription.getDateFin())
                .statut(periodeInscription.getStatut())
                .inscriptions(periodeInscription.getInscriptions())
                .build();
    }

    public PeriodeInscription toEntity(PeriodeInscriptionDto periodeInscriptionDto) {
        return PeriodeInscription.builder()
                .id(periodeInscriptionDto.getId())
                .anneeScolaire(periodeInscriptionDto.getAnneeScolaire())
                .dateDebut(periodeInscriptionDto.getDateDebut())
                .dateFin(periodeInscriptionDto.getDateFin())
                .statut(periodeInscriptionDto.getStatut())
                .inscriptions(periodeInscriptionDto.getInscriptions())
                .build();
    }
}
