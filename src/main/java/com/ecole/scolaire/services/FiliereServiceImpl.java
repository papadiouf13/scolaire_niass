package com.ecole.scolaire.services;

import com.ecole.scolaire.dtos.FiliereDto;
import com.ecole.scolaire.entity.Filiere;
import com.ecole.scolaire.exceptions.AlreadyExistsExceptions;
import com.ecole.scolaire.exceptions.ValidationExceptions;
import com.ecole.scolaire.mapper.FiliereMapper;
import com.ecole.scolaire.repository.FiliereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FiliereServiceImpl implements FiliereService{
    @Autowired
    private FiliereRepository filiereRepository;

    @Autowired
    private FiliereMapper filiereMapper;

    @Override
    public Filiere saveFiliere(Filiere filiere) {
        if (filiereRepository.existsByLibelle(filiere.getLibelle())) {
            throw new AlreadyExistsExceptions.FiliereAlreadyExistsException("Une filière avec ce libellé existe déjà.");
        }
        return filiereRepository.save(filiere);
    }

    @Override
    public List<FiliereDto> getAllFilieres() {
        List<Filiere> filieres = filiereRepository.findByEtatFalse();
        return filieres.stream()
                .map(filiereMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Filiere updateFiliere(Long id, FiliereDto filiereDto) {
        Filiere existingFiliere = filiereRepository.findById(id)
                .orElseThrow(() -> new ValidationExceptions.NotFoundException("Filiere not found with id: " + id));
        if (!existingFiliere.getLibelle().equals(filiereDto.getLibelleDto()) &&
                filiereRepository.existsByLibelle(filiereDto.getLibelleDto())) {
            throw new AlreadyExistsExceptions.FiliereAlreadyExistsException("Une filière avec ce libellé existe déjà.");
        }
        existingFiliere.setLibelle(filiereDto.getLibelleDto());
        // Update other fields if necessary
        return filiereRepository.save(existingFiliere);
    }

    @Override
    public void deleteFiliere(Long id) {
        Filiere filiere = filiereRepository.findById(id).orElseThrow(() -> new ValidationExceptions.NotFoundException("Filiere not found with id: " + id));
        filiere.setEtat(true);
        filiereRepository.save(filiere);
    }
}
