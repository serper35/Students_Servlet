package org.example.dao.impl;

import org.example.dao.StudentDao;
import org.example.dao.mapper.StudentResultSetMapper;
import org.example.dao.mapper.impl.StudentResultSetMapperImpl;
import org.example.db.ConnectionManager;
import org.example.entity.Groups;
import org.example.entity.Professor;
import org.example.entity.Student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements StudentDao {
    StudentResultSetMapper studentResultSetMapper = new StudentResultSetMapperImpl();
    private ConnectionManager connectionManager = new ConnectionManager();
    String dbProp = "db.properties";

    Groups group = new Groups();

    public StudentDaoImpl(String dbProp) {
        this.dbProp = dbProp;
    }

    @Override
    public void save(Student student) {


        String saveToDb = """
                INSERT INTO students (name, age, group_id)
                VALUES (?, ?, ?);
                """;


        try (Connection connection = connectionManager.getConnection(dbProp);
             PreparedStatement statement = connection.prepareStatement(saveToDb)) {

            statement.setString(1, student.getName());
            statement.setLong(2, student.getAge());
            statement.setLong(3, student.getGroup().getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при сохранении студента в базу данных", e);
        } catch (NullPointerException e) {
            throw new RuntimeException("Указанный номер группы отсутствует", e);
        }
    }

    @Override
    public void update(Student student) {


        String updateDb = """
                UPDATE students
                SET name = ?,
                age = ?,
                group_id = ?
                where id = ?
                """;

        try (Connection connection = connectionManager.getConnection(dbProp);
            PreparedStatement statement = connection.prepareStatement(updateDb)) {

            statement.setString(1, student.getName());
            statement.setLong(2, student.getAge());
            statement.setLong(3, student.getGroup().getId());
            statement.setLong(4, student.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при редактировании студента в базу данных", e);
        }

    }

    @Override
    public void delete(long id) {
        String delete = """
                DELETE FROM students
                where id = ?;
                """;

        try (Connection connection = connectionManager.getConnection(dbProp);
             PreparedStatement statement = connection.prepareStatement(delete)) {

            statement.setLong(1, id);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении студента в базу данных", e);
        }

    }

    @Override
    public Student get(long studentId) {
        Student student = null;
        String get = """
                SELECT id, age, name 
                FROM students
                WHERE ID = ?
                """;

        try (Connection connection = connectionManager.getConnection(dbProp);
             PreparedStatement statement = connection.prepareStatement(get)) {

            statement.setLong(1, studentId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                student = studentResultSetMapper.map(resultSet);
                student.setGroup(getGroupByStudentId(studentId));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении студента из базы данных", e);
        }

        return student;
    }

    @Override
    public List<Student> getAll() {
        List<Student> students = new ArrayList<>();

        String getAllSQL = """
                SELECT id, name, age, group_id
                FROM STUDENTS
                """;

        try (Connection connection = connectionManager.getConnection(dbProp);
             PreparedStatement statement = connection.prepareStatement(getAllSQL);
            ResultSet resultSet = statement.executeQuery()){

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                Student student = studentResultSetMapper.map(resultSet);
                student.setGroup(getGroupByStudentId(student.getId()));
                students.add(student);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении студентов из базы данных", e);
        }
        return students;
    }

    private Groups getGroupByStudentId(long groupId) throws SQLException {
        Groups group = new Groups();
        String query = """
                SELECT Groups.id, Groups.faculty, Groups.numberofstudents FROM GROUPS
                JOIN Students ON Groups.id = Students.group_id
                WHERE Students.group_id = ?
                """;
        try (Connection connection = connectionManager.getConnection(dbProp);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, groupId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    group.setId(resultSet.getLong("id"));
                    group.setFaculty(resultSet.getString("faculty"));
                    group.setNumberOfStudents(resultSet.getInt("numberofstudents"));
                }
            }
        }
        return group;
    }
}
