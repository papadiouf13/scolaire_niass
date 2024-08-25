package com.ecole.scolaire.services;

import com.ecole.scolaire.dtos.InscriptionDto;
import com.ecole.scolaire.entity.Classe;
import com.ecole.scolaire.entity.Etudiant;
import com.ecole.scolaire.entity.Inscription;
import com.ecole.scolaire.entity.PeriodeInscription;
import com.ecole.scolaire.exceptions.GeneralExceptions;
import com.ecole.scolaire.exceptions.ValidationExceptions;
import com.ecole.scolaire.mapper.InscriptionMapper;
import com.ecole.scolaire.repository.ClasseRepository;
import com.ecole.scolaire.repository.EtudiantRepository;
import com.ecole.scolaire.repository.InscriptionRepository;
import com.ecole.scolaire.repository.PeriodeInscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InscriptionServiceImpl implements InscriptionService {

    @Autowired
    private InscriptionRepository inscriptionRepository;

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private ClasseRepository classeRepository;

    @Autowired
    private PeriodeInscriptionRepository periodeInscriptionRepository;

    @Autowired
    private InscriptionMapper inscriptionMapper;

    @Override
    public Inscription saveInscription(InscriptionDto inscriptionDto) {
        validateInscriptionData(inscriptionDto);

        Etudiant etudiant = etudiantRepository.findById(inscriptionDto.getEtudiantIdDto())
                .orElseThrow(() -> new GeneralExceptions.InvalidInscriptionDataException("Etudiant ID invalide."));

        Classe classe = classeRepository.findById(inscriptionDto.getClasseIdDto())
                .orElseThrow(() -> new GeneralExceptions.InvalidInscriptionDataException("Classe ID invalide."));

        PeriodeInscription periodeInscription = periodeInscriptionRepository.findById(inscriptionDto.getPeriodeInscriptionIdDto())
                .orElseThrow(() -> new GeneralExceptions.InvalidInscriptionDataException("Période d'inscription ID invalide."));

        // Validate the date is within the period interval
        if (inscriptionDto.getDateDto().before(periodeInscription.getDateDebut()) ||
                inscriptionDto.getDateDto().after(periodeInscription.getDateFin())) {
            throw new GeneralExceptions.InvalidInscriptionDataException("La date d'inscription n'est pas dans l'intervalle de la période d'inscription.");
        }

        Optional<Inscription> existingInscription = inscriptionRepository
                .findByEtudiantAndPeriodeInscription(etudiant, periodeInscription);
        if (existingInscription.isPresent()) {
            throw new GeneralExceptions.InvalidInscriptionDataException("L'étudiant est déjà inscrit pour cette période d'inscription.");
        }

        Inscription inscription = inscriptionMapper.inscriptionDtoToEntityInscription(inscriptionDto);
        inscription.setEtudiant(etudiant);
        inscription.setClasse(classe);
        inscription.setPeriodeInscription(periodeInscription);

        return inscriptionRepository.save(inscription);
    }

    private void validateInscriptionData(InscriptionDto inscriptionDto) {
        if (inscriptionDto.getDateDto() == null) {
            throw new ValidationExceptions.MissingFieldException("La date d'inscription est obligatoire.");
        }
        if (inscriptionDto.getEtudiantIdDto() == null) {
            throw new ValidationExceptions.MissingFieldException("L'ID de l'étudiant est obligatoire.");
        }
        if (inscriptionDto.getClasseIdDto() == null) {
            throw new ValidationExceptions.MissingFieldException("L'ID de la classe est obligatoire.");
        }
        if (inscriptionDto.getPeriodeInscriptionIdDto() == null) {
            throw new ValidationExceptions.MissingFieldException("L'ID de la période d'inscription est obligatoire.");
        }
    }

    @Override
    public List<InscriptionDto> getAllInscriptions() {
        List<Inscription> inscriptions = inscriptionRepository.findAll();
        return inscriptions.stream()
                .map(inscriptionMapper::entityInscriptionToInscriptionDto)
                .collect(Collectors.toList());
    }

    @Override
    public InscriptionMapper getMapper() {
        return inscriptionMapper;
    }

    @Override
    public List<InscriptionDto> getAllInscriptionsByClasse(Long classeId) {
        return inscriptionRepository.findByClasseId(classeId).stream()
                .map(inscriptionMapper::entityInscriptionToInscriptionDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<InscriptionDto> getInscriptionByEtudiant(String matricule) {
        List<Inscription> inscriptions = inscriptionRepository.findByEtudiantMatricule(matricule);
        if (inscriptions.isEmpty()) {
            throw new GeneralExceptions("Inscription non trouvée pour l'étudiant avec matricule " + matricule);
        }
        return inscriptions.stream()
                .map(inscriptionMapper::entityInscriptionToInscriptionDto)
                .collect(Collectors.toList());
    }
}
