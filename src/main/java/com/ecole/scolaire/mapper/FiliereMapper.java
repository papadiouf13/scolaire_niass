package com.ecole.scolaire.mapper;

import com.ecole.scolaire.dtos.FiliereDto;
import com.ecole.scolaire.entity.Filiere;
import org.springframework.stereotype.Component;

@Component
public class FiliereMapper {
    public Filiere toEntity(FiliereDto filiereDto) {
        return Filiere.builder()
                .id(filiereDto.getIdDto())
                .libelle(filiereDto.getLibelleDto())
                .etat(filiereDto.isEtatDto())
                .build();
    }

    public FiliereDto toDto(Filiere filiere) {
        return FiliereDto.builder()
                .idDto(filiere.getId())
                .libelleDto(filiere.getLibelle())
                .etatDto(filiere.isEtat())
                .build();
    }
}
