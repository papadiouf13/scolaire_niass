package com.ecole.scolaire.services;

import com.ecole.scolaire.dtos.ClasseDto;
import com.ecole.scolaire.entity.Classe;
import com.ecole.scolaire.entity.Filiere;
import com.ecole.scolaire.entity.Inscription;
import com.ecole.scolaire.exceptions.AlreadyExistsExceptions;
import com.ecole.scolaire.exceptions.GeneralExceptions;
import com.ecole.scolaire.mapper.ClasseMapper;
import com.ecole.scolaire.repository.ClasseRepository;
import com.ecole.scolaire.repository.FiliereRepository;
import com.ecole.scolaire.repository.InscriptionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClasseServiceImpl implements ClasseService{
    @Autowired
    private ClasseRepository classeRepository;

    @Autowired
    private InscriptionRepository inscriptionRepository;
    @Autowired
    private FiliereRepository filiereRepository;

    @Autowired
    private ClasseMapper classeMapper;

    @Override
    public Classe saveClasse(ClasseDto classeDto) {
        validateClasseData(classeDto);

        if (classeRepository.existsByLibelle(classeDto.getLibelleDto())) {
            throw new AlreadyExistsExceptions.ClasseAlreadyExistsException("Une classe avec ce libellé existe déjà.");
        }

        if (classeRepository.existsByCode(classeDto.getCodeDto())) {
            throw new AlreadyExistsExceptions.ClasseAlreadyExistsException("Une classe avec ce code existe déjà.");
        }

        Filiere filiere = filiereRepository.findById(classeDto.getFiliereIdDto())
                .orElseThrow(() -> new GeneralExceptions.InvalidClasseDataException("Filiere ID invalide."));

        Classe classe = classeMapper.toEntity(classeDto);
        classe.setFiliere(filiere);

        return classeRepository.save(classe);
    }

    private void validateClasseData(ClasseDto classeDto) {
        if (classeDto.getFraisInscriptionDto() < 5000) {
            throw new GeneralExceptions.InvalidClasseDataException("Les frais d'inscription doivent être au moins de 5000.");
        }

        if (classeDto.getMensualiteDto() < 5000) {
            throw new GeneralExceptions.InvalidClasseDataException("La mensualité doit être au moins de 5000.");
        }

        if (classeDto.getAutresFraisDto() < 5000) {
            throw new GeneralExceptions.InvalidClasseDataException("Les autres frais doivent être au moins de 5000.");
        }

        if (classeDto.getFiliereIdDto() == null) {
            throw new GeneralExceptions.InvalidClasseDataException("Filiere ID est obligatoire.");
        }
    }


    @Override
    public Classe updateClasse(Long id, ClasseDto classeDto) {
        validateClasseData(classeDto);

        Classe existingClasse = classeRepository.findById(id)
                .orElseThrow(() -> new GeneralExceptions.InvalidClasseDataException("Classe non trouvée."));

        if (!existingClasse.getLibelle().equals(classeDto.getLibelleDto()) && classeRepository.existsByLibelle(classeDto.getLibelleDto())) {
            throw new AlreadyExistsExceptions.ClasseAlreadyExistsException("Une classe avec ce libellé existe déjà.");
        }

        if (!existingClasse.getCode().equals(classeDto.getCodeDto()) && classeRepository.existsByCode(classeDto.getCodeDto())) {
            throw new AlreadyExistsExceptions.ClasseAlreadyExistsException("Une classe avec ce code existe déjà.");
        }

        Filiere filiere = filiereRepository.findById(classeDto.getFiliereIdDto())
                .orElseThrow(() -> new GeneralExceptions.InvalidClasseDataException("Filiere ID invalide."));

        Classe updatedClasse = classeMapper.toEntity(classeDto);
        updatedClasse.setId(id);
        updatedClasse.setFiliere(filiere);

        return classeRepository.save(updatedClasse);
    }

    @Override
    public void deleteClasse(Long id) {
        Classe classe = classeRepository.findById(id)
                .orElseThrow(() -> new GeneralExceptions.InvalidClasseDataException("Classe non trouvée."));

        classe.setEtat(true);
        classeRepository.save(classe);
        List<Inscription> inscriptions = inscriptionRepository.findByClasseId(classe.getId());
        for (Inscription inscription : inscriptions) {
            inscription.setEtat(true);
            inscriptionRepository.save(inscription);
        }
    }

    @Override
    public List<ClasseDto> getAllClasses() {
        List<Classe> classes = classeRepository.findByEtatFalse();
        return classes.stream()
                .map(classeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClasseDto> getClassesByFiliere(Long filiereId) {
        List<Classe> classes = classeRepository.findByFiliereId(filiereId);
        return classes.stream().map(classeMapper::toDto).collect(Collectors.toList());
    }
}
