package com.ecole.scolaire.dtos;

import com.ecole.scolaire.entity.Inscription;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class EtudiantDto {
    private Long idDto;
    private String matriculeDto;
    private String nomDto;
    private String prenomDto;
    private String telephoneDto;
    private String emailDto;
    private String dateNaissanceDto;
    private String adresseDto;
    @JsonBackReference
    private List<Inscription> inscriptionsDto;
}
