package org.example.dao;

import org.example.entity.Professor;
import org.example.entity.Student;

import java.util.List;

public interface ProfessorDao {
    void save(Professor professor);
    void update(Professor professor);
    void delete(long id);
    Professor get(long id);
    List<Professor> getAll();
}
