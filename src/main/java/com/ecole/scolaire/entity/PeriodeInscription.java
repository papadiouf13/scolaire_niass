package com.ecole.scolaire.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PeriodeInscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String anneeScolaire;
    private Date dateDebut;
    private Date dateFin;
    private Boolean statut;
    @OneToMany(mappedBy = "periodeInscription")
    private List<Inscription> inscriptions;
    @Column(columnDefinition = "boolean default false")
    private boolean etat;
}
