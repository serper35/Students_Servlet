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

    @BeforeEach
    public void setup() {
        professorDao = new ProfessorDaoImpl(dbProp);

        try {
            connectionManager = new ConnectionManager();

            Connection connection = connectionManager.getConnection(dbProp);
            Statement statement = connection.createStatement();

            statement.executeUpdate("CREATE TABLE groups (id BIGSERIAL PRIMARY KEY, faculty VARCHAR(255), numberofstudents INT)");
            statement.executeUpdate("CREATE TABLE professors (id BIGSERIAL NOT NULL, name varchar(100) NOT NULL, CONSTRAINT professors_pkey PRIMARY KEY (id))");
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

    @Test
    void saveShouldAddProfessorToH2Database() {
        Professor professor = new Professor();
        professor.setName("Cole");

        professorDao.save(professor);

        Professor test = professorDao.get(1L);

        assertNotNull(test);
        assertEquals(professor.getName(), test.getName());

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
    void updateShouldUpdateProfessor() {
        Professor professor = new Professor();
        professor.setName("Cole");

        professorDao.save(professor);

        Professor updProfessor = new Professor(1, "Colle");
        professorDao.update(updProfessor);

        Professor test = professorDao.get(1L);

        assertEquals(updProfessor.getName(), test.getName());
        assertNotEquals(professor.getName(), test.getName());
    }

    @Test
    void deleteShouldDeleteProfessor() {
        Professor professor = new Professor();
        professor.setName("Cole");

        professorDao.save(professor);

        Professor test = professorDao.get(1L);

        professorDao.delete(1L);

        assertNull(professorDao.get(1));
    }

    @Test
    void getShouldReturnProfessor() {
        Professor professor = new Professor();
        professor.setName("Cole");

        professorDao.save(professor);
        Professor actual = professorDao.get(1L);

        assertEquals(professor.getName(), actual.getName());
    }

    @Test
    void deleteShouldThrowExceptionWhenConnectionIsClosed() throws SQLException {
        assertThrows(SQLException.class, () -> connectionManager.getConnection("fail.properties"));
    }

    @AfterEach
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