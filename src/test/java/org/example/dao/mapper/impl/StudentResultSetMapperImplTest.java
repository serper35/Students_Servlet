package org.example.dao.mapper.impl;

import org.example.dao.mapper.StudentResultSetMapper;
import org.example.entity.Student;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentResultSetMapperImplTest {
    @Test
    void mapResultSetToStudent() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);

        long expectedId = 1;
        String expectedName = "Johny";
        int expectedAge = 25;
        int expectedGroupId = 1;
        when(resultSet.getLong("id")).thenReturn(expectedId);
        when(resultSet.getString("name")).thenReturn(expectedName);
        when(resultSet.getInt("age")).thenReturn(expectedAge);
        when(resultSet.getInt("group_id")).thenReturn(expectedGroupId);

        StudentResultSetMapper mapper = new StudentResultSetMapperImpl();

        Student student = mapper.map(resultSet);

        assertEquals(expectedId, student.getId());
        assertEquals(expectedName, student.getName());
        assertEquals(expectedAge, student.getAge());
        assertEquals(expectedGroupId, student.getGroup().getId());
    }

}