package com.ecole.scolaire.services;

import com.ecole.scolaire.entity.SequenceEntity;
import com.ecole.scolaire.repository.SequenceEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.Optional;

@Service
public class MatriculeGeneratorService {
    private static final long DEFAULT_SEQUENCE_VALUE = 1L;

    @Autowired
    private SequenceEntityRepository sequenceEntityRepository;

    public String generateMatricule() {
        int currentYear = Year.now().getValue();
        long nextValue = getNextSequenceValue();
        return String.format("M-%04d-%d", nextValue, currentYear);
    }

    private synchronized long getNextSequenceValue() {
        Optional<SequenceEntity> sequenceEntityOptional = sequenceEntityRepository.findById(1L);
        SequenceEntity sequenceEntity;
        if (sequenceEntityOptional.isPresent()) {
            sequenceEntity = sequenceEntityOptional.get();
            sequenceEntity.setValue(sequenceEntity.getValue() + 1);
        } else {
            sequenceEntity = new SequenceEntity();
            sequenceEntity.setId(1L);
            sequenceEntity.setValue(DEFAULT_SEQUENCE_VALUE);
        }
        sequenceEntityRepository.save(sequenceEntity);
        return sequenceEntity.getValue();
    }
}
