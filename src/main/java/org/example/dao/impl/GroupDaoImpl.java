package org.example.dao.impl;

import org.example.dao.GroupDao;
import org.example.dao.mapper.GroupResultSetMapper;
import org.example.dao.mapper.impl.GroupResultSetMapperImpl;
import org.example.db.ConnectionManager;
import org.example.entity.Groups;
import org.example.entity.Professor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupDaoImpl implements GroupDao {

    private String dbProp;

    private GroupResultSetMapper groupResultSetMapper = new GroupResultSetMapperImpl();

    private ConnectionManager connectionManager = new ConnectionManager();

    public GroupDaoImpl(String dbProp) {
        this.dbProp = dbProp;
    }

    @Override
    public void save(Groups group) {
        String saveSQL = """
                INSERT INTO groups (faculty, numberofstudents)
                values (?, ?)
                """;

        try (Connection connection = connectionManager.getConnection(dbProp);
             PreparedStatement statement = connection.prepareStatement(saveSQL)) {

            statement.setString(1, group.getFaculty());
            statement.setInt(2, group.getNumberOfStudents());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при сохранении группы в базу данных", e);
        }
    }

    @Override
    public void update(Groups group) {
        String updateSQL = """
                UPDATE groups 
                SET faculty = ?, 
                numberofstudents = ?
                WHERE ID = ?
                """;

        try (Connection connection = connectionManager.getConnection(dbProp);
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setString(1, group.getFaculty());
            preparedStatement.setInt(2, group.getNumberOfStudents());
            preparedStatement.setLong(3, group.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при обновлении группы в базу данных", e);
        }
    }

    @Override
    public void delete(long id) {
        String deleteSQL = """
                DELETE FROM GROUPS
                WHERE ID = ?
                """;

        try (Connection connection = connectionManager.getConnection(dbProp);
             PreparedStatement statement = connection.prepareStatement(deleteSQL)) {

            statement.setLong(1, id);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении группы из базы данных", e);
        }

    }

    @Override
    public Groups get(long id) {
        Groups group = null;

        String getSQL = """
                SELECT id, faculty, numberofstudents
                FROM GROUPS
                WHERE id = ?
                """;

        try (Connection connection = connectionManager.getConnection(dbProp);
             PreparedStatement preparedStatement = connection.prepareStatement(getSQL)) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                group = groupResultSetMapper.map(resultSet);

                List<Professor> professors = getProfessorsByGroupId(id);
                group.setProfessors(professors);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении группы из базы данных", e);
        }
        return group;
    }

    @Override
    public List<Groups> getAll() {
        List<Groups> groups = new ArrayList<>();

        String getAllSQL = """
                SELECT id, faculty, numberofstudents
                FROM GROUPS
                """;

        try (Connection connection = connectionManager.getConnection(dbProp);
             PreparedStatement statement = connection.prepareStatement(getAllSQL)) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Groups group = groupResultSetMapper.map(resultSet);

                List<Professor> professors = getProfessorsByGroupId(group.getId());
                group.setProfessors(professors);

                groups.add(group);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении групп из базы данных", e);
        }
        return groups;
    }

    private List<Professor> getProfessorsByGroupId(long groupId) throws SQLException {
        List<Professor> professorsList = new ArrayList<>();
        String query = """
                SELECT Professors.id, Professors.name FROM Professors
                JOIN Groups_Professors ON Professors.id = Groups_Professors.professor_id
                WHERE Groups_Professors.group_id = ?
                """;
        try (Connection connection = connectionManager.getConnection(dbProp);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, groupId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Professor professor = new Professor();
                    professor.setId(resultSet.getLong("id"));
                    professor.setName(resultSet.getString("name"));
                    professorsList.add(professor);
                }
            }
        }
        return professorsList;
    }
}
