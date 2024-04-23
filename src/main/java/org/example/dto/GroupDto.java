package org.example.dto;

import org.example.entity.Professor;
import org.example.entity.Student;

import java.util.List;
import java.util.Objects;

public class GroupDto {
    private long id;

    private String faculty;

    private int numberOfStudents;

    private List<Professor> professors;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public List<Professor> getProfessors() {
        return professors;
    }

    public void setProfessors(List<Professor> professors) {
        this.professors = professors;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        GroupDto groupDto = (GroupDto) object;
        return id == groupDto.id && numberOfStudents == groupDto.numberOfStudents && Objects.equals(faculty, groupDto.faculty);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, faculty, numberOfStudents);
    }
}
