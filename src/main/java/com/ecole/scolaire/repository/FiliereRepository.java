package com.ecole.scolaire.repository;

import com.ecole.scolaire.entity.Filiere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FiliereRepository extends JpaRepository<Filiere, Long> {
    boolean existsByLibelle(String libelle);
    List<Filiere> findByEtatFalse();
}
