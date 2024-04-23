package org.example.dao.mapper.impl;

import org.example.dao.mapper.GroupResultSetMapper;
import org.example.entity.Groups;
import org.example.entity.Professor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupResultSetMapperImpl implements GroupResultSetMapper {


    @Override
    public Groups map(ResultSet resultSet) throws SQLException {
        int groupId = resultSet.getInt("id");
        String faculty = resultSet.getString("faculty");
        int numberOfStudents = resultSet.getInt("numberofstudents");

        return new Groups(groupId, faculty, numberOfStudents);
    }
}
