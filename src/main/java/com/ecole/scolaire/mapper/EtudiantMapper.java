package com.ecole.scolaire.mapper;

import com.ecole.scolaire.dtos.EtudiantDto;
import com.ecole.scolaire.entity.Etudiant;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
@Component
public class EtudiantMapper {
    public Etudiant toEntity(EtudiantDto etudiantDto) {
        return Etudiant.builder()
                .id(etudiantDto.getIdDto())
                .matricule(etudiantDto.getMatriculeDto())
                .nom(etudiantDto.getNomDto())
                .prenom(etudiantDto.getPrenomDto())
                .telephone(etudiantDto.getTelephoneDto())
                .email(etudiantDto.getEmailDto())
                .dateNaissance(String.valueOf(LocalDate.parse(etudiantDto.getDateNaissanceDto())))
                .adresse(etudiantDto.getAdresseDto())
                .inscriptions(etudiantDto.getInscriptionsDto())
                .etat(etudiantDto.isEtatDto())
                .build();
    }

    public EtudiantDto toDto(Etudiant etudiant) {
        return EtudiantDto.builder()
                .idDto(etudiant.getId())
                .matriculeDto(etudiant.getMatricule())
                .nomDto(etudiant.getNom())
                .prenomDto(etudiant.getPrenom())
                .telephoneDto(etudiant.getTelephone())
                .emailDto(etudiant.getEmail())
                .dateNaissanceDto(etudiant.getDateNaissance())
                .adresseDto(etudiant.getAdresse())
                .inscriptionsDto(etudiant.getInscriptions())
                .etatDto(etudiant.isEtat())
                .build();
    }
}
