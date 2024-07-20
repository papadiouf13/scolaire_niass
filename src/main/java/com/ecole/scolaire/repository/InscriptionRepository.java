package com.ecole.scolaire.repository;

import com.ecole.scolaire.entity.Etudiant;
import com.ecole.scolaire.entity.Inscription;
import com.ecole.scolaire.entity.PeriodeInscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, Long> {
    Optional<Inscription> findByEtudiantAndPeriodeInscription(Etudiant etudiant, PeriodeInscription periodeInscription);
    void deleteByClasseId(Long classeId);
}
