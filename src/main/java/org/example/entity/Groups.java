package org.example.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Groups {

    private long id;

    private String faculty;

    private int numberOfStudents;
    private List<Professor> professors = new ArrayList<>();


    public Groups(long id, String faculty, int numberOfStudents) {
        this.id = id;
        this.faculty = faculty;
        this.numberOfStudents = numberOfStudents;
    }

    public Groups(String faculty, int numberOfStudents) {
        this.faculty = faculty;
        this.numberOfStudents = numberOfStudents;
    }

    public Groups() {
    }

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
        Groups groups = (Groups) object;
        return id == groups.id && numberOfStudents == groups.numberOfStudents && Objects.equals(faculty, groups.faculty) && Objects.equals(professors, groups.professors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, faculty, numberOfStudents, professors);
    }

    @Override
    public String toString() {
        return "Groups{" +
                "id=" + id +
                ", faculty='" + faculty + '\'' +
                ", numberOfStudents=" + numberOfStudents +
                ", professors=" + professors +
                '}';
    }
}
