package org.example.service.impl;

import org.example.dao.ProfessorDao;
import org.example.dao.impl.ProfessorDaoImpl;
import org.example.dto.ProfessorDto;
import org.example.dto.mapper.GroupDtoMapper;
import org.example.dto.mapper.ProfessorDtoMapper;
import org.example.dto.mapper.ProfessorDtoMapperImpl;
import org.example.entity.Professor;
import org.example.service.ProfessorService;
import org.mapstruct.factory.Mappers;

import java.util.List;

public class ProfessorServiceImpl implements ProfessorService {
    private String dbProp = "db.properties";

    private ProfessorDao professorDao = new ProfessorDaoImpl(dbProp);
    private ProfessorDtoMapper dtoMapper = Mappers.getMapper(ProfessorDtoMapper.class);

    @Override
    public void saveProfessor(ProfessorDto professorDto) {
        professorDao.save(dtoMapper.mapToProfessor(professorDto));
    }

    @Override
    public void updateProfessor(ProfessorDto professorDto) {
        professorDao.update(dtoMapper.mapToProfessor(professorDto));

    }

    @Override
    public void deleteProfessor(long id) {
        professorDao.delete(id);
    }

    @Override
    public ProfessorDto getProfessor(long id) {
        Professor professor = professorDao.get(id);

        return dtoMapper.mapToProfessorDto(professor);
    }

    @Override
    public List<ProfessorDto> getAllProfessors() {
        List<Professor> professors = professorDao.getAll();

        List<ProfessorDto> professorDtosList = professors.stream()
                .map(professor -> dtoMapper.mapToProfessorDto(professor))
                .toList();

        return professorDtosList;
    }
}
