package com.ecole.scolaire.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FiliereDto {
    private Long idDto;
    private String libelleDto;
    private boolean etatDto;
}
