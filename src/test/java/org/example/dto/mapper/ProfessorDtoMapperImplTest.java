package org.example.dto.mapper;

import org.example.dto.ProfessorDto;
import org.example.entity.Professor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProfessorDtoMapperImplTest {
    private ProfessorDtoMapperImpl dtoMapper;

    @BeforeEach
    public void setUp() {
        dtoMapper = new ProfessorDtoMapperImpl();
    }

    @Test
    void testMapToProfessor() {
        ProfessorDto professorDto = new ProfessorDto();
        professorDto.setId(1);
        professorDto.setName("Com");

        Professor professor = dtoMapper.mapToProfessor(professorDto);

        assertEquals(professorDto.getId(), professor.getId());
        assertEquals(professorDto.getName(), professor.getName());
    }

    @Test
    void testMapToGroupDto() {
        Professor professor = new Professor();
        professor.setId(1);
        professor.setName("Com");

        ProfessorDto professorDto = dtoMapper.mapToProfessorDto(professor);

        assertEquals(professorDto.getId(), professor.getId());
        assertEquals(professorDto.getName(), professor.getName());
    }
}