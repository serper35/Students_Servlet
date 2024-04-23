package org.example.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Professor {
    private long id;
    private String name;
    private List<Groups> groups = new ArrayList<>();

    public Professor(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Professor() {
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Groups> getGroups() {
        return groups;
    }

    public void setGroups(List<Groups> groups) {
        this.groups = groups;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Professor professor = (Professor) object;
        return id == professor.id && Objects.equals(name, professor.name) && Objects.equals(groups, professor.groups);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, groups);
    }

    @Override
    public String toString() {
        return "Professor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", groups=" + groups +
                '}';
    }
}
