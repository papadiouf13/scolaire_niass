package com.ecole.scolaire.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InscriptionDto {
    private Long idDto;
    private Long etudiantId;
    private Long classeId;
    private Long periodeInscriptionId;
    private Date date;
}
