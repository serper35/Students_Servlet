package org.example.dao.impl;

import org.example.db.ConnectionManager;
import org.example.entity.Groups;
import org.example.entity.Professor;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProfessorDaoImplTestH2 {

    private String dbProp = "h2.properties";
    private ConnectionManager connectionManager;
    private ProfessorDaoImpl professorDao;
    private static String name;

    @BeforeAll
    public void setupDatabase() {
        name = "Cole";
        try {
            connectionManager = new ConnectionManager();

            Connection connection = connectionManager.getConnection(dbProp);
            Statement statement = connection.createStatement();

            statement.executeUpdate("CREATE TABLE groups (id INT PRIMARY KEY AUTO_INCREMENT, faculty VARCHAR(255), numberofstudents INT)");
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
    }

    @BeforeEach
    public void setup() {
        professorDao = new ProfessorDaoImpl(dbProp);

        Professor professor = new Professor();
        professor.setName(name);

        professorDao.save(professor);
    }

    @Test
    void saveShouldAddProfessorToH2Database() {
        Professor test = professorDao.get(1L);

        assertNotNull(test);
        assertEquals(name, test.getName());

        try {
            Connection connection = connectionManager.getConnection(dbProp);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM professors WHERE id = ?");
            statement.setLong(1, 1L);

            ResultSet resultSet = statement.executeQuery();

            assertTrue(resultSet.next());

            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при проверке сохранения профессора в базу данных", e);
        }
    }

    @Test
    void updateShouldUpdateGroup() {

        Professor updProfessor = new Professor(1, "Colle");
        professorDao.update(updProfessor);

        Professor test = professorDao.get(1L);

        assertEquals(updProfessor.getName(), test.getName());
        assertNotEquals(name, test.getName());
    }

    @Test
    void deleteShouldDeleteGroup() {
        professorDao.delete(1L);

        assertNull(professorDao.get(1));
    }

    @Test
    void getShouldReturnGroup() {
        Professor actual = professorDao.get(1L);

        assertEquals(name, actual.getName());
    }

    @Test
    void getAllShouldReturnAllGroups() {
        List<Professor> professors = new ArrayList<>();
        Professor professor = new Professor();
        professor.setName("Cole");
        professors.add(professor);

        Professor professor1 = new Professor();
        professor1.setName("Dole");
        professors.add(professor);
        professorDao.save(professor);
        professorDao.save(professor1);

        List<Professor> actual = professorDao.getAll();

        assertEquals(professors.size(), actual.size());
    }

    @Test
    void deleteShouldThrowExceptionWhenConnectionIsClosed() throws SQLException {
        assertThrows(SQLException.class, () -> connectionManager.getConnection("fail.properties"));
    }

    @AfterAll
    public void teardownDatabase() {
        try {
            Connection connection = connectionManager.getConnection(dbProp);
            Statement statement = connection.createStatement();

            statement.executeUpdate("DROP TABLE groups_professors");
            statement.executeUpdate("DROP TABLE groups");
            statement.executeUpdate("DROP TABLE professors");

            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении таблиц", e);
        }
    }
}