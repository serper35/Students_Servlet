package org.example.dto.mapper;

import org.example.dto.GroupDto;
import org.example.dto.ProfessorDto;
import org.example.entity.Groups;
import org.example.entity.Professor;

public interface ProfessorDtoMapper {
    Professor mapToProfessor(ProfessorDto professorDto);

    ProfessorDto mapToProfessorDto(Professor professor);
}
