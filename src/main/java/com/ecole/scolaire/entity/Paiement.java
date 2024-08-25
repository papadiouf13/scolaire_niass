package com.ecole.scolaire.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Paiement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double montant;

    @Temporal(TemporalType.DATE)
    private Date datePaiement;

    @Enumerated(EnumType.STRING)
    private TypePaiement typePaiement;

    @ManyToOne
    @JoinColumn(name = "inscription_id", nullable = false)
    @JsonBackReference
    private Inscription inscription;

    @Column(columnDefinition = "boolean default false")
    private boolean etat;
}


