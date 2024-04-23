package org.example.dao.mapper;

import org.example.entity.Groups;
import org.example.entity.Professor;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ProfessorResultSetMapper {
    Professor map (ResultSet resultSet) throws SQLException;
}
