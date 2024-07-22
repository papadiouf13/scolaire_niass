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
        List<Etudiant> etudiants = etudiantRepository.findByEtatFalse();
        return etudiants.stream()
                .map(etudiantMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EtudiantDto> getEtudiantsByMatricule(String matricule) {
        List<Etudiant> etudiants = etudiantRepository.findByMatricule(matricule);
        return etudiants.stream()
                .map(etudiantMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteEtudiant(Long id) {
        Etudiant etudiant = etudiantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Etudiant n'existe pas avec l'id : " + id));
        etudiant.setEtat(true);
        etudiantRepository.save(etudiant);
    }

    @Override
    public void updateEtudiant(Long id, EtudiantDto etudiantDto) {
        Etudiant etudiant = etudiantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Etudiant not found with id: " + id));
        etudiant.setPrenom(etudiantDto.getPrenomDto());
        etudiant.setNom(etudiantDto.getNomDto());
        etudiant.setTelephone(etudiantDto.getTelephoneDto());
        etudiant.setEmail(etudiantDto.getEmailDto());
        etudiant.setAdresse(etudiantDto.getAdresseDto());
        etudiant.setDateNaissance(etudiantDto.getDateNaissanceDto());
        etudiant.setEtat(etudiantDto.isEtatDto());

        etudiantRepository.save(etudiant);
    }
}
