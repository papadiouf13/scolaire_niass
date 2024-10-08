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
    private Long idDto;
    private String anneeScolaireDto;
    private Date dateDebutDto;
    private Date dateFinDto;
    private Boolean statutDto;
    private List<Inscription> inscriptionsDto;
    private boolean etatDto;
}
