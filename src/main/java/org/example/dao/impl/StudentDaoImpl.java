package org.example.dao.impl;

import org.example.dao.GroupDao;
import org.example.dao.StudentDao;
import org.example.db.ConnectionManager;
import org.example.entity.Student;
import org.example.entity.Groups;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements StudentDao {

    private GroupDao groupDao = new GroupDaoImpl();


    @Override
    public void save(Student student) {

        String saveToDb = """
                INSERT INTO student (name, age, groups_id)
                VALUES (?, ?, ?);
                """;

        try (Connection connection = ConnectionManager.getConnection();
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
                UPDATE student
                SET name = ?,
                age = ?,
                groups_id = ?
                where id = ?
                """;

        try (Connection connection = ConnectionManager.getConnection();
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
                DELETE FROM student
                where id = ?;
                """;

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(delete)) {

            statement.setLong(1, id);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении студента в базу данных", e);
        }

    }

    @Override
    public Student get(long studetnId) {
        String get = """
                SELECT * 
                FROM student
                WHERE ID = ?
                """;

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(get)) {

            statement.setLong(1, studetnId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                int groupId = resultSet.getInt("groups_id");

                return new Student(id, name, age, groupId);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении студента из базы данных", e);
        }

        return null;
    }

    @Override
    public List<Student> getAll() {
        List<Student> students = new ArrayList<>();

        String getAllSQL = """
                SELECT id, name, age, groups_id
                FROM student
                """;

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(getAllSQL)) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                int groupId = resultSet.getInt("groups_id");

                students.add(new Student(id, name, age, groupId));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении студентов из базы данных", e);
        }
        return students;
    }
}
