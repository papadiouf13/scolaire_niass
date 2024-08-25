package com.ecole.scolaire.services;

import com.ecole.scolaire.dtos.PeriodeInscriptionDto;
import com.ecole.scolaire.entity.PeriodeInscription;

import java.util.List;

public interface PeriodeInscriptionService {
    PeriodeInscription addPeriodeInscription(PeriodeInscriptionDto periodeInscriptionDto);
    List<PeriodeInscriptionDto> getAllPeriodesInscription();
    PeriodeInscriptionDto findById(Long id);
}
