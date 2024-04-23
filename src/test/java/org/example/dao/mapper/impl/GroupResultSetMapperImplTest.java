package org.example.dao.mapper.impl;

import org.example.dao.mapper.GroupResultSetMapper;
import org.example.entity.Groups;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GroupResultSetMapperImplTest {
    @Test
    void mapResultSetToGroup() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);

        int expectedGroupId = 1;
        String expectedFaculty = "CS";
        int expectedNumberOfStudents = 20;
        when(resultSet.getInt("id")).thenReturn(expectedGroupId);
        when(resultSet.getString("faculty")).thenReturn(expectedFaculty);
        when(resultSet.getInt("numberofstudents")).thenReturn(expectedNumberOfStudents);

        GroupResultSetMapper mapper = new GroupResultSetMapperImpl();

        Groups group = mapper.map(resultSet);

        assertEquals(expectedGroupId, group.getId());
        assertEquals(expectedFaculty, group.getFaculty());
        assertEquals(expectedNumberOfStudents, group.getNumberOfStudents());
    }
}