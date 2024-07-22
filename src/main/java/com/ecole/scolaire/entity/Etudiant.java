package com.ecole.scolaire.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Etudiant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String matricule;
    private String nom;
    private String prenom;
    private String telephone;
    @Column(unique = true)
    private String email;
    private String dateNaissance;
    private String adresse;
    @JsonBackReference
    @OneToMany(mappedBy = "etudiant")
    private List<Inscription> inscriptions;
    @Column(columnDefinition = "boolean default false")
    private boolean etat;
}
