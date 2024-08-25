package com.ecole.scolaire.services;

import com.ecole.scolaire.dtos.PeriodeInscriptionDto;
import com.ecole.scolaire.entity.PeriodeInscription;
import com.ecole.scolaire.exceptions.GeneralExceptions;
import com.ecole.scolaire.exceptions.ValidationExceptions;
import com.ecole.scolaire.mapper.PeriodeInscriptionMapper;
import com.ecole.scolaire.repository.PeriodeInscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PeriodeInscriptionServiceImpl implements PeriodeInscriptionService{
    @Autowired
    private PeriodeInscriptionRepository periodeInscriptionRepository;

    @Autowired
    private PeriodeInscriptionMapper periodeInscriptionMapper;


    @Override
    public List<PeriodeInscriptionDto> getAllPeriodesInscription() {
        return periodeInscriptionRepository.findAll().stream()
                .map(periodeInscriptionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PeriodeInscriptionDto findById(Long id) {
        PeriodeInscription periodeInscription = periodeInscriptionRepository.findById(id)
                .orElseThrow(() -> new GeneralExceptions("Période d'inscription non trouvée avec l'id " + id));
        return periodeInscriptionMapper.toDto(periodeInscription);
    }
    @Override
    public PeriodeInscription addPeriodeInscription(PeriodeInscriptionDto periodeInscriptionDto) {
        String anneeScolaire = periodeInscriptionDto.getAnneeScolaireDto();
        Date dateDebut = periodeInscriptionDto.getDateDebutDto();
        Date dateFin = periodeInscriptionDto.getDateFinDto();
        Boolean statut = periodeInscriptionDto.getStatutDto();

        if (anneeScolaire == null || anneeScolaire.isEmpty()) {
            throw new ValidationExceptions.MissingFieldException("L'année scolaire est obligatoire");
        }
        if (dateDebut == null) {
            throw new ValidationExceptions.MissingFieldException("La date de début est obligatoire");
        }
        if (dateFin == null) {
            throw new ValidationExceptions.MissingFieldException("La date de fin est obligatoire");
        }
        if (statut == null) {
            throw new ValidationExceptions.MissingFieldException("Le statut est obligatoire");
        }

        if (dateDebut.after(dateFin)) {
            throw new ValidationExceptions.InvalidDateException("La date de début ne doit pas être supérieure à la date de fin.");
        }

        if (dateFin.before(dateDebut)) {
            throw new ValidationExceptions.InvalidDateException("La date de fin ne doit pas être inférieure à la date de début.");
        }

        // Extraire les années de dateDebut et dateFin
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateDebut);
        int yearDebut = cal.get(Calendar.YEAR);
        cal.setTime(dateFin);
        int yearFin = cal.get(Calendar.YEAR);

        // Extraire les années de l'année scolaire
        String[] annees = anneeScolaire.split("-");
        int anneeDebutScolaire;
        int anneeFinScolaire;
        try {
            anneeDebutScolaire = Integer.parseInt(annees[0]);
            anneeFinScolaire = Integer.parseInt(annees[1]);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new ValidationExceptions.InvalidAnneeScolaireException("Format d'année scolaire invalide. Utilisez le format 'YYYY-YYYY'.");
        }

        if (anneeDebutScolaire < yearDebut || anneeFinScolaire < yearFin) {
            throw new ValidationExceptions.InvalidAnneeScolaireException("Revoyez l'année scolaire pour cette période. Elle doit être comprise entre les dates de début et de fin.");
        }

        if (statut && periodeInscriptionRepository.existsByStatutTrue()) {
            throw new ValidationExceptions.InvalidDateException("Une autre période d'inscription est déjà active.");
        }

        PeriodeInscription periodeInscription = periodeInscriptionMapper.toEntity(periodeInscriptionDto);
        return periodeInscriptionRepository.save(periodeInscription);
    }
}
