package org.example.dao.mapper.impl;

import org.example.dao.mapper.ProfessorResultSetMapper;
import org.example.entity.Professor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProfessorResultSetMapperImplTest {
    @Test
    void mapResultSetToProfessor() throws SQLException, SQLException {
        ResultSet resultSet = mock(ResultSet.class);

        long expectedId = 1;
        String expectedName = "Ivanov";
        when(resultSet.getLong("id")).thenReturn(expectedId);
        when(resultSet.getString("name")).thenReturn(expectedName);

        ProfessorResultSetMapper mapper = new ProfessorResultSetMapperImpl();

        Professor professor = mapper.map(resultSet);

        assertEquals(expectedId, professor.getId());
        assertEquals(expectedName, professor.getName());
    }
}