package org.example.dao.impl;

import org.example.dao.ProfessorDao;
import org.example.dao.mapper.ProfessorResultSetMapper;
import org.example.dao.mapper.impl.ProfessorResultSetMapperImpl;
import org.example.db.ConnectionManager;
import org.example.entity.Groups;
import org.example.entity.Professor;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfessorDaoImpl implements ProfessorDao {

    private ConnectionManager connectionManager = new ConnectionManager();

    private ProfessorResultSetMapper professorResultSetMapper = new ProfessorResultSetMapperImpl();

    private String dbProp;

    public ProfessorDaoImpl(String dbProp) {
        this.dbProp = dbProp;
    }


    @Override
    public void save(Professor professor) {
        String sql = """
                INSERT INTO professors (name)
                values (?)
                """;

        try (Connection connection = connectionManager.getConnection(dbProp);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, professor.getName());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при сохранении профессора в базу данных", e);
        }
    }

    @Override
    public void update(Professor professor) {
        String sql = """
                UPDATE professors
                set name = ?
                WHERE id = ?
                """;

        try (Connection connection = connectionManager.getConnection(dbProp);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, professor.getName());
            preparedStatement.setLong(2, professor.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при обновлении профессора в базу данных", e);
        }

    }

    @Override
    public void delete(long id) {
        String sql = """
                DELETE from professors
                WHERE id = ?
                """;

        try (Connection connection = connectionManager.getConnection(dbProp);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении профессора из базу данных", e);
        }

    }

    @Override
    public Professor get(long id) {
        Professor professor = null;

        String sql = """
                SELECT * FROM professors
                WHERE id = ?
                """;

        try (Connection connection = connectionManager.getConnection(dbProp);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                professor = professorResultSetMapper.map(resultSet);
                professor.setGroups(getGroupsByProfessorId(id));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при обновлении профессора в базу данных", e);
        }

        return professor;
    }

    @Override
    public List<Professor> getAll() {
        List<Professor> professors = new ArrayList<>();

        String sql = """
                SELECT * FROM professors
                """;

        try (Connection connection = connectionManager.getConnection(dbProp);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                Professor professor = professorResultSetMapper.map(resultSet);
                professor.setGroups(getGroupsByProfessorId(professor.getId()));
                professors.add(professor);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при обновлении профессора в базу данных", e);
        }

        return professors;
    }

    private List<Groups> getGroupsByProfessorId(long professorId) {
        List<Groups> groups = new ArrayList<>();

        String sql = """
                SELECT Groups.id, Groups.faculty, Groups.numberofstudents FROM Groups
                JOIN Groups_Professors ON Groups.id = Groups_Professors.group_id
                WHERE Groups_Professors.professor_id = ?
                """;

        try (Connection connection = connectionManager.getConnection(dbProp);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, professorId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Groups group = new Groups();

                group.setId(resultSet.getLong("id"));
                group.setFaculty(resultSet.getString("faculty"));
                group.setNumberOfStudents(resultSet.getInt("numberofstudents"));

                groups.add(group);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return groups;
    }
}
