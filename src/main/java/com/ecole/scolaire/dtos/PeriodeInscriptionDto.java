package com.ecole.scolaire.dtos;

import com.ecole.scolaire.entity.Inscription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PeriodeInscriptionDto {
    private Long id;
    private String anneeScolaire;
    private Date dateDebut;
    private Date dateFin;
    private Boolean statut;
    private List<Inscription> inscriptions;
}
