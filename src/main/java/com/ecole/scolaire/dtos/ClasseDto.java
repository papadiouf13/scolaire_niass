package com.ecole.scolaire.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClasseDto {
    private Long idDto;
    private String codeDto;
    private String libelleDto;
    private Double fraisInscriptionDto;
    private Double mensualiteDto;
    private Double autresFraisDto;
    private Long filiereIdDto;
}
