package com.ecole.scolaire.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
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
    @JsonBackReference
    private List<Inscription> inscriptions;
    @Column(columnDefinition = "boolean default false")
    private boolean etat;

    public int getDureeEnMois() {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(dateDebut);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(dateFin);

        int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
        int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);

        return diffMonth + 1; // +1 si vous voulez inclure les deux mois limites
    }
}
