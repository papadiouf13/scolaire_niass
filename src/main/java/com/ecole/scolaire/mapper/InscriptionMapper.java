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
                .etudiantId(inscription.getEtudiant().getId())
                .classeId(inscription.getClasse().getId())
                .periodeInscriptionId(inscription.getPeriodeInscription().getId())
                .date(inscription.getDate())
                .build();
    }

    public Inscription inscriptionDtoToEntityInscription(InscriptionDto inscriptionDto) {
        Inscription inscription = new Inscription();
        inscription.setId(inscriptionDto.getIdDto());
        inscription.setDate(inscriptionDto.getDate());
        // Handle mapping for related entities
        if (inscriptionDto.getClasseId() != null) {
            Classe classe = new Classe();
            classe.setId(inscriptionDto.getClasseId());
            inscription.setClasse(classe);
        }
        if (inscriptionDto.getEtudiantId() != null) {
            Etudiant etudiant = new Etudiant();
            etudiant.setId(inscriptionDto.getEtudiantId());
            inscription.setEtudiant(etudiant);
        }
        if (inscriptionDto.getPeriodeInscriptionId() != null) {
            PeriodeInscription periodeInscription = new PeriodeInscription();
            periodeInscription.setId(inscriptionDto.getPeriodeInscriptionId());
            inscription.setPeriodeInscription(periodeInscription);
        }
        return inscription;
    }
}
