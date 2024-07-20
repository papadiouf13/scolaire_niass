package com.ecole.scolaire.services;

import com.ecole.scolaire.dtos.EtudiantDto;
import com.ecole.scolaire.entity.Etudiant;
import com.ecole.scolaire.exceptions.AlreadyExistsExceptions;
import com.ecole.scolaire.mapper.EtudiantMapper;
import com.ecole.scolaire.repository.EtudiantRepository;
import com.ecole.scolaire.validations.EtudiantValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EtudiantServiceImpl implements EtudiantService{
    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private MatriculeGeneratorService matriculeGeneratorService;

    @Autowired
    private EtudiantValidator etudiantValidator;

    @Autowired
    private EtudiantMapper etudiantMapper; // Injecter votre mapper ici

    @Override
    public EtudiantDto addEtudiant(EtudiantDto etudiantDto) {
        etudiantValidator.validate(etudiantDto);

        // Utilisation du service de génération de matricule
        String matricule = matriculeGeneratorService.generateMatricule();

        Etudiant etudiant = etudiantMapper.toEntity(etudiantDto);
        etudiant.setMatricule(matricule);

        // Vérifier si un étudiant avec cet email existe déjà
        if (etudiantRepository.existsByEmail(etudiantDto.getEmailDto())) {
            throw new AlreadyExistsExceptions.EtudiantAlreadyExistsException("Un étudiant avec cet email existe déjà.");
        }

        Etudiant savedEtudiant = etudiantRepository.save(etudiant);
        return etudiantMapper.toDto(savedEtudiant);
    }

    @Override
    public List<EtudiantDto> getAllEtudiants() {
        List<Etudiant> etudiants = etudiantRepository.findAll();
        return etudiants.stream()
                .map(etudiantMapper::toDto) // Utilisation de method reference pour la conversion
                .collect(Collectors.toList());
    }

    @Override
    public List<EtudiantDto> getEtudiantsByMatricule(String matricule) {
        List<Etudiant> etudiants = etudiantRepository.findByMatricule(matricule);
        return etudiants.stream()
                .map(etudiantMapper::toDto)
                .collect(Collectors.toList());
    }
}
