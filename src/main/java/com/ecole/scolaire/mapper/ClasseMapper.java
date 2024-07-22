package com.ecole.scolaire.mapper;

import com.ecole.scolaire.dtos.ClasseDto;
import com.ecole.scolaire.entity.Classe;
import com.ecole.scolaire.entity.Filiere;
import org.springframework.stereotype.Component;

@Component
public class ClasseMapper {
    public Classe toEntity(ClasseDto classeDto) {
        return Classe.builder()
                .id(classeDto.getIdDto())
                .code(classeDto.getCodeDto())
                .libelle(classeDto.getLibelleDto())
                .fraisInscription(classeDto.getFraisInscriptionDto())
                .mensualite(classeDto.getMensualiteDto())
                .autresFrais(classeDto.getAutresFraisDto())
                .filiere(classeDto.getFiliereIdDto() != null ? Filiere.builder().id(classeDto.getFiliereIdDto()).build() : null)
                .etat(classeDto.isEtatDto())
                .build();
    }

    public ClasseDto toDto(Classe classe) {
        return ClasseDto.builder()
                .idDto(classe.getId())
                .codeDto(classe.getCode())
                .libelleDto(classe.getLibelle())
                .fraisInscriptionDto(classe.getFraisInscription())
                .mensualiteDto(classe.getMensualite())
                .autresFraisDto(classe.getAutresFrais())
                .filiereIdDto(classe.getFiliere() != null ? classe.getFiliere().getId() : null)
                .etatDto(classe.isEtat())
                .build();
    }
}
