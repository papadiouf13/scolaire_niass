package com.ecole.scolaire.mapper;

import com.ecole.scolaire.dtos.InscriptionDto;
import com.ecole.scolaire.entity.Classe;
import com.ecole.scolaire.entity.Etudiant;
import com.ecole.scolaire.entity.Inscription;
import com.ecole.scolaire.entity.PeriodeInscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InscriptionMapper {
    private final ClasseMapper classeMapper;
    private final EtudiantMapper etudiantMapper;
    private final PeriodeInscriptionMapper periodeInscriptionMapper;

    @Autowired
    public InscriptionMapper(ClasseMapper classeMapper, EtudiantMapper etudiantMapper, PeriodeInscriptionMapper periodeInscriptionMapper) {
        this.classeMapper = classeMapper;
        this.etudiantMapper = etudiantMapper;
        this.periodeInscriptionMapper = periodeInscriptionMapper;
    }

    public InscriptionDto entityInscriptionToInscriptionDto(Inscription inscription) {
        return InscriptionDto.builder()
                .idDto(inscription.getId())
                .etudiantIdDto(inscription.getEtudiant().getId())
                .classeIdDto(inscription.getClasse().getId())
                .periodeInscriptionIdDto(inscription.getPeriodeInscription().getId())
                .dateDto(inscription.getDate())
                .etatDto(inscription.isEtat())
                .build();
    }

    public Inscription inscriptionDtoToEntityInscription(InscriptionDto inscriptionDto) {
        Inscription inscription = new Inscription();
        inscription.setId(inscriptionDto.getIdDto());
        inscription.setDate(inscriptionDto.getDateDto());
        inscription.setEtat(inscriptionDto.isEtatDto());
        if (inscriptionDto.getClasseIdDto() != null) {
            Classe classe = new Classe();
            classe.setId(inscriptionDto.getClasseIdDto());
            inscription.setClasse(classe);
        }
        if (inscriptionDto.getEtudiantIdDto() != null) {
            Etudiant etudiant = new Etudiant();
            etudiant.setId(inscriptionDto.getEtudiantIdDto());
            inscription.setEtudiant(etudiant);
        }
        if (inscriptionDto.getPeriodeInscriptionIdDto() != null) {
            PeriodeInscription periodeInscription = new PeriodeInscription();
            periodeInscription.setId(inscriptionDto.getPeriodeInscriptionIdDto());
            inscription.setPeriodeInscription(periodeInscription);
        }
        return inscription;
    }
}
