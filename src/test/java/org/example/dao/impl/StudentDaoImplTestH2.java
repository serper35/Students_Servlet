package org.example.dao.impl;


import org.example.db.ConnectionManager;
import org.example.entity.Groups;
import org.example.entity.Student;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StudentDaoImplTestH2 {
    private String dbProp = "h2.properties";

    private ConnectionManager connectionManager;

    private StudentDaoImpl studentDao;
    private GroupDaoImpl groupDao;

    @BeforeEach
    public void setupDatabase() {
        try {
            connectionManager = new ConnectionManager();

            Connection connection = connectionManager.getConnection(dbProp);
            Statement statement = connection.createStatement();

            statement.executeUpdate("CREATE TABLE GROUPS (id INT PRIMARY KEY AUTO_INCREMENT, faculty VARCHAR(255), numberofstudents INT)");
            statement.executeUpdate("CREATE TABLE STUDENTS (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), age INT, group_id INT, FOREIGN KEY (group_id) REFERENCES groups(id))");
            statement.executeUpdate("CREATE TABLE professors (id bigserial NOT NULL, name varchar(100) NOT NULL, CONSTRAINT professors_pkey PRIMARY KEY (id))");
            statement.executeUpdate("""
                    CREATE TABLE groups_professors (group_id int8 NOT NULL,professor_id int8 NOT NULL,
                    CONSTRAINT groups_professors_pkey PRIMARY KEY (group_id, professor_id),
                    CONSTRAINT groups_professors_group_id_fkey FOREIGN KEY (group_id) REFERENCES groups(id) ON DELETE CASCADE,
                    CONSTRAINT groups_professors_professor_id_fkey FOREIGN KEY (professor_id) REFERENCES public.professors(id) ON DELETE CASCADE)
                    """);

            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при создании таблицы групп", e);
        }

        groupDao = new GroupDaoImpl(dbProp);
        studentDao = new StudentDaoImpl(dbProp);

        Groups group = new Groups();
        group.setFaculty("IT");
        group.setNumberOfStudents(20);
        groupDao.save(group);
    }

    @AfterEach
    public void teardownDatabase() {
        try {
            Connection connection = connectionManager.getConnection(dbProp);
            Statement statement = connection.createStatement();

            statement.executeUpdate("DROP TABLE students");
            statement.executeUpdate("DROP TABLE groups_professors");
            statement.executeUpdate("DROP TABLE groups");
            statement.executeUpdate("DROP TABLE professors");

            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении таблицы групп", e);
        }
    }

    @Test
    void saveShouldAddStudentToH2Database() {
        Groups savedGroup = new Groups(1, "hhh", 5);

        Student student = new Student();
        student.setName("vova");
        student.setAge(10);
        student.setGroup(savedGroup);

        studentDao.save(student);

        Student test = studentDao.get(1L);

        assertNotNull(test);
        assertEquals(student.getName(), test.getName());

        try {
            Connection connection = connectionManager.getConnection(dbProp);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM students WHERE id = ?");
            statement.setLong(1, 1L);

            ResultSet resultSet = statement.executeQuery();

            assertTrue(resultSet.next());

            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при проверке сохранения группы в базу данных", e);
        }
    }

    @Test
    void updateShouldUpdateStudent() {
        Groups savedGroup = new Groups(1, "hhh", 5);

        Student student = new Student();
        student.setName("vova");
        student.setAge(10);
        student.setGroup(savedGroup);

        studentDao.save(student);

        Student updStudent = new Student();
        updStudent.setId(1L);
        updStudent.setName("vova");
        updStudent.setAge(20);
        updStudent.setGroup(savedGroup);

        studentDao.update(updStudent);

        Student test = studentDao.get(1L);

        assertEquals(updStudent.getAge(), test.getAge());
        assertNotEquals(student.getAge(), test.getAge());
    }

    @Test
    void deleteShouldDeleteStudent() {
        Groups savedGroup = new Groups(1, "hhh", 5);

        Student student = new Student();
        student.setName("vova");
        student.setAge(10);
        student.setGroup(savedGroup);

        studentDao.save(student);

        studentDao.delete(1L);

        assertNull(studentDao.get(1L));
    }

    @Test
    void getShouldReturnStudent() {
        Groups savedGroup = new Groups(1, "hhh", 5);

        Student student = new Student();
        student.setName("vova");
        student.setAge(10);
        student.setGroup(savedGroup);

        studentDao.save(student);

        Student actual = studentDao.get(1L);

        assertEquals(student.getName(), actual.getName());
    }

    @Test
    void deleteShouldThrowExceptionWhenConnectionIsClosed() throws SQLException {
        assertThrows(SQLException.class, () -> connectionManager.getConnection("fail.properties"));
    }
}