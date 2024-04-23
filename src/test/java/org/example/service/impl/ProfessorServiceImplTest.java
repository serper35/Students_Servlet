package org.example.service.impl;

import org.example.dao.impl.ProfessorDaoImpl;
import org.example.dao.mapper.impl.ProfessorResultSetMapperImpl;
import org.example.dto.ProfessorDto;
import org.example.dto.mapper.ProfessorDtoMapperImpl;
import org.example.entity.Professor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ProfessorServiceImplTest {

    @InjectMocks
    ProfessorServiceImpl professorService;

    @Mock
    ProfessorDtoMapperImpl dtoMapper;

    @Mock
    ProfessorDaoImpl professorDao;

    @Mock
    ProfessorResultSetMapperImpl resultSetMapper;

    private ProfessorDto professordto = new ProfessorDto();

    private Professor professor = new Professor();

    @Test
    void saveProfessorTest() {
        Mockito.when(dtoMapper.mapToProfessor(professordto)).thenReturn(professor);

        professorService.saveProfessor(professordto);

        Mockito.verify(professorDao, Mockito.times(1)).save(professor);
    }

    @Test
    void updateProfessorTest() {
        Mockito.when(dtoMapper.mapToProfessor(professordto)).thenReturn(professor);

        professorService.updateProfessor(professordto);

        Mockito.verify(professorDao, Mockito.times(1)).update(professor);
    }

    @Test
    void deleteProfessorTest() {
        professorService.deleteProfessor(1);

        Mockito.verify(professorDao, Mockito.times(1)).delete(1);
    }

    @Test
    void getProfessorTest() {
        Mockito.when(dtoMapper.mapToProfessorDto(professor)).thenReturn(professordto);
        Mockito.when(professorDao.get(1)).thenReturn(professor);

        ProfessorDto actual = professorService.getProfessor(1);

        Assertions.assertEquals(professordto, actual);
    }

    @Test
    void getAllProfessorsTest() {
        List<Professor> professors = new ArrayList<>();
        Professor professor1 = new Professor();
        professors.add(professor);
        professors.add(professor1);

        List<ProfessorDto> professorsDto = new ArrayList<>();
        ProfessorDto professor1Dto = new ProfessorDto();
        professorsDto.add(professordto);
        professorsDto.add(professor1Dto);

        Mockito.when(professorDao.getAll()).thenReturn(professors);
        Mockito.when(dtoMapper.mapToProfessorDto(professor)).thenReturn(professordto);
        Mockito.when(dtoMapper.mapToProfessorDto(professor1)).thenReturn(professor1Dto);

        List<ProfessorDto> actual = professorService.getAllProfessors();

        Assertions.assertEquals(professorsDto,actual);
    }
}