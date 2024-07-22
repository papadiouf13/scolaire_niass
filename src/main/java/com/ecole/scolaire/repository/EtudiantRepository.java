package com.ecole.scolaire.repository;

import com.ecole.scolaire.entity.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EtudiantRepository extends JpaRepository<Etudiant,Long> {
    boolean existsByEmail(String email);

    long countByEmail(String email);
    List<Etudiant> findByMatricule(String matricule);
    List<Etudiant> findByEtatFalse();
}
