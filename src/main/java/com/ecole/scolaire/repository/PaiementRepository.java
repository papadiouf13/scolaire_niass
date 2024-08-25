package com.ecole.scolaire.repository;

import com.ecole.scolaire.entity.Inscription;
import com.ecole.scolaire.entity.Paiement;
import com.ecole.scolaire.entity.TypePaiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {
    List<Paiement> findByInscriptionId(Long inscriptionId);
    int countByInscriptionId(Long inscriptionId);

    boolean existsByInscriptionIdAndTypePaiement(Long inscriptionId, TypePaiement typePaiement);

    @Query(value = "SELECT p.* FROM paiement p JOIN inscription i ON i.id = p.inscription_id WHERE i.etudiant_id = :etudiantId", nativeQuery = true)
    List<Paiement> findPaiementsByEtudiantId(@Param("etudiantId") Long etudiantId);

}
