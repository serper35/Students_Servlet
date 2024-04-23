package org.example.dto.mapper;

import org.example.dto.ProfessorDto;
import org.example.dto.mapper.ProfessorDtoMapper;
import org.example.entity.Professor;

public class ProfessorDtoMapperImpl implements ProfessorDtoMapper {
    @Override
    public Professor mapToProfessor(ProfessorDto professorDto) {
        Professor professor = new Professor();
        professor.setId(professorDto.getId());
        professor.setName(professorDto.getName());

        return professor;
    }

    @Override
    public ProfessorDto mapToProfessorDto(Professor professor) {
        ProfessorDto professorDto = new ProfessorDto();
        professorDto.setId(professor.getId());
        professorDto.setName(professor.getName());
        professorDto.setGroups(professor.getGroups());
        return professorDto;
    }
}
