package com.ecole.scolaire.services;

import com.ecole.scolaire.dtos.PeriodeInscriptionDto;
import com.ecole.scolaire.entity.PeriodeInscription;
import com.ecole.scolaire.exceptions.ValidationExceptions;
import com.ecole.scolaire.mapper.PeriodeInscriptionMapper;
import com.ecole.scolaire.repository.PeriodeInscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;


@Service
public class PeriodeInscriptionServiceImpl implements PeriodeInscriptionService{
    @Autowired
    private PeriodeInscriptionRepository periodeInscriptionRepository;

    @Autowired
    private PeriodeInscriptionMapper periodeInscriptionMapper;

    @Override
    public PeriodeInscription addPeriodeInscription(PeriodeInscriptionDto periodeInscriptionDto) {
        String anneeScolaire = periodeInscriptionDto.getAnneeScolaire();
        Date dateDebut = periodeInscriptionDto.getDateDebut();
        Date dateFin = periodeInscriptionDto.getDateFin();
        Boolean statut = periodeInscriptionDto.getStatut();

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

        // Valider que l'année scolaire est comprise entre dateDebut et dateFin
        if (anneeDebutScolaire < yearDebut || anneeFinScolaire < yearFin) {
            throw new ValidationExceptions.InvalidAnneeScolaireException("Revoyez l'année scolaire pour cette période. Elle doit être comprise entre les dates de début et de fin.");
        }

        // Vérifier qu'il n'existe pas déjà une période d'inscription avec statut true
        if (statut && periodeInscriptionRepository.existsByStatutTrue()) {
            throw new ValidationExceptions.InvalidDateException("Une autre période d'inscription est déjà active.");
        }

        PeriodeInscription periodeInscription = periodeInscriptionMapper.toEntity(periodeInscriptionDto);
        return periodeInscriptionRepository.save(periodeInscription);
    }
}
