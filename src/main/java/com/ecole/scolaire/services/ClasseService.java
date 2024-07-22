package com.ecole.scolaire.services;

import com.ecole.scolaire.dtos.ClasseDto;
import com.ecole.scolaire.entity.Classe;

import java.util.List;

public interface ClasseService {
    Classe saveClasse(ClasseDto classeDto);
    List<ClasseDto> getAllClasses();
    Classe updateClasse(Long id, ClasseDto classeDto);
    void deleteClasse(Long id);
    List<ClasseDto> getClassesByFiliere(Long filiereId);
}
