package com.ecole.scolaire.repository;

import com.ecole.scolaire.entity.Classe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClasseRepository extends JpaRepository<Classe, Long> {
    boolean existsByLibelle(String libelle);
    boolean existsByCode(String code);
    List<Classe> findByFiliereId(Long filiereId);
    List<Classe> findByEtatFalse();
}
