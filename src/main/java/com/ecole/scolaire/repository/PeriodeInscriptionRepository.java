package com.ecole.scolaire.repository;

import com.ecole.scolaire.entity.PeriodeInscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PeriodeInscriptionRepository extends JpaRepository<PeriodeInscription , Long> {
    @Query("SELECT COUNT(pi) > 0 FROM PeriodeInscription pi WHERE pi.statut = true")
    boolean existsByStatutTrue();
}
