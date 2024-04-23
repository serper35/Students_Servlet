package org.example.dao.mapper.impl;

import org.example.dao.mapper.ProfessorResultSetMapper;
import org.example.entity.Professor;
import org.example.entity.Student;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfessorResultSetMapperImpl implements ProfessorResultSetMapper {
    @Override
    public Professor map(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");

        return new Professor(id, name);
    }
}
