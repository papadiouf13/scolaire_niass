package com.ecole.scolaire.repository;

import com.ecole.scolaire.entity.Etudiant;
import com.ecole.scolaire.entity.Inscription;
import com.ecole.scolaire.entity.PeriodeInscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, Long> {
    Optional<Inscription> findByEtudiantAndPeriodeInscription(Etudiant etudiant, PeriodeInscription periodeInscription);
    //void deleteByClasseId(Long classeId);
    List<Inscription> findByClasseId(Long classeId);
    @Query(value = "SELECT i FROM Inscription i WHERE i.etudiant.id = (SELECT e.id FROM Etudiant e WHERE e.matricule = :matricule)")
    List<Inscription> findByEtudiantMatricule(@Param("matricule") String matricule);

    List<Inscription> findByEtudiantId(Long etudiantId);
}
