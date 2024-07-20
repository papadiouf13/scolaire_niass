package com.ecole.scolaire.services;

import com.ecole.scolaire.dtos.PeriodeInscriptionDto;
import com.ecole.scolaire.entity.PeriodeInscription;

public interface PeriodeInscriptionService {
    PeriodeInscription addPeriodeInscription(PeriodeInscriptionDto periodeInscriptionDto);
}
