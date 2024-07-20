package com.ecole.scolaire.repository;

import com.ecole.scolaire.entity.SequenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SequenceEntityRepository extends JpaRepository<SequenceEntity, Long> {
}
