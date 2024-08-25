package com.ecole.scolaire.services;

import com.ecole.scolaire.dtos.PaiementDto;
import com.ecole.scolaire.entity.Inscription;
import com.ecole.scolaire.entity.Paiement;
import com.ecole.scolaire.entity.TypePaiement;
import com.ecole.scolaire.mapper.PaiementMapper;
import com.ecole.scolaire.repository.InscriptionRepository;
import com.ecole.scolaire.repository.PaiementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
@Service
public class PaiementServiceImpl implements PaiementService{
    @Autowired
    private PaiementRepository paiementRepository;

    @Autowired
    private InscriptionRepository inscriptionRepository;

    @Autowired
    private PaiementMapper paiementMapper;

    @Override
    public PaiementDto createPaiement(PaiementDto paiementDto) {
        Inscription inscription = inscriptionRepository.findById(paiementDto.getInscriptionIdDto())
                .orElseThrow(() -> new RuntimeException("Inscription non trouvée"));

        double montantInscription = inscription.getClasse().getFraisInscription()
                + inscription.getClasse().getAutresFrais()
                + inscription.getClasse().getMensualite();

        int moisTotalInscription = calculateMonthsBetween(inscription.getPeriodeInscription().getDateDebut(), inscription.getPeriodeInscription().getDateFin());

        int moisPayes = paiementRepository.countByInscriptionId(inscription.getId());

        if (moisPayes >= moisTotalInscription) {
            throw new RuntimeException("Vous ne pouvez plus payer pour cette période.");
        }

        if (paiementDto.getTypePaiementDto().equals(TypePaiement.INSCRIPTION.name())) {
            boolean inscriptionAlreadyPaid = paiementRepository.existsByInscriptionIdAndTypePaiement(inscription.getId(), TypePaiement.valueOf(TypePaiement.INSCRIPTION.name()));

            if (inscriptionAlreadyPaid) {
                throw new RuntimeException("L'inscription a déjà été payée pour cette période.");
            }

            double montantPaye = paiementDto.getMontantDto();

            if (montantPaye < montantInscription) {
                throw new RuntimeException("Le montant de l'inscription est incorrect. Montant requis: " + montantInscription);
            }

            Paiement paiement = paiementMapper.dtoToEntity(paiementDto, inscription);
            paiement = paiementRepository.save(paiement);

            double avance = montantPaye - montantInscription;
            if (avance > 0) {
                paiementDto.setMontantDto(avance);
            } else {
                paiementDto.setMontantDto(0.0);
            }

            PaiementDto paiementReponseDto = paiementMapper.entityToDto(paiement);
            paiementReponseDto.setMessage(String.format("Inscription payée avec succès. Montant restant: %.2f CFA", moisTotalInscription - moisPayes, montantInscription - montantPaye));

            return paiementReponseDto;
        } else if (paiementDto.getTypePaiementDto().equals(TypePaiement.MENSUALITE.name())) {
            boolean inscriptionPaid = paiementRepository.existsByInscriptionIdAndTypePaiement(inscription.getId(), TypePaiement.valueOf(TypePaiement.INSCRIPTION.name()));

            if (!inscriptionPaid) {
                throw new RuntimeException("Vous devez payer l'inscription avant de payer la mensualité.");
            }

            double montantMensualite = inscription.getClasse().getMensualite();
            double montantPaye = paiementDto.getMontantDto();

            if (montantPaye < montantMensualite) {
                throw new RuntimeException("Le montant de la mensualité est insuffisant. Montant requis: " + montantMensualite);
            }

            int moisRestants = moisTotalInscription - moisPayes - 1;
            double montantTotalRestant = moisRestants * montantMensualite;

            if (montantPaye > montantTotalRestant) {
                throw new RuntimeException("Vous avez dépassé le montant total restant à payer pour cette période. Montant restant: " + montantTotalRestant);
            }

            Paiement paiement = paiementMapper.dtoToEntity(paiementDto, inscription);
            paiement = paiementRepository.save(paiement);

            PaiementDto paiementReponseDto = paiementMapper.entityToDto(paiement);
            paiementReponseDto.setMessage(String.format("Paiement mensuel reçu. Il reste %d mois à payer. Montant restant: %.2f CFA", moisRestants, montantTotalRestant - montantPaye));

            return paiementReponseDto;
        } else {
            throw new RuntimeException("Type de paiement non reconnu.");
        }
    }


    @Override
    public double calculateTotalPaid(Long inscriptionId) {
        List<Paiement> paiements = paiementRepository.findByInscriptionId(inscriptionId);
        return paiements.stream().mapToDouble(Paiement::getMontant).sum();
    }


    private int calculateMonthsBetween(Date startDate, Date endDate) {
        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        Calendar end = Calendar.getInstance();
        end.setTime(endDate);
        int monthsBetween = 0;
        while (start.before(end)) {
            start.add(Calendar.MONTH, 1);
            monthsBetween++;
        }
        return monthsBetween;
    }

    @Override
    public List<Paiement> getPaiementsByEtudiantId(Long etudiantId) {
        return paiementRepository.findPaiementsByEtudiantId(etudiantId);
    }

    @Override
    public List<PaiementDto> getAllPaiementsByInscription(Long inscriptionId) {
        List<Paiement> paiements = paiementRepository.findByInscriptionId(inscriptionId);
        return paiements.stream()
                .map(paiementMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaiementDto> getAllPaiements() {
        List<Paiement> paiements = paiementRepository.findAll();
        return paiements.stream()
                .map(paiementMapper::entityToDto)
                .collect(Collectors.toList());
    }
}
