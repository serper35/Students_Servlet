package org.example.service;

import org.example.dto.GroupDto;
import org.example.dto.ProfessorDto;

import java.util.List;

public interface ProfessorService {
    void saveProfessor(ProfessorDto professorDto);

    void updateProfessor(ProfessorDto professorDto);

    void deleteProfessor(long id);

    ProfessorDto getProfessor(long id);

    List<ProfessorDto> getAllProfessors();
}
