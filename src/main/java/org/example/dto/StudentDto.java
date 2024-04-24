package org.example.dto;

import org.example.entity.Groups;

import java.util.Objects;

public class StudentDto {
    private long id;
    private String name;
    private int age;
    private GroupDto groups;

    public StudentDto() {
    }

    public StudentDto(long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public GroupDto getGroups() {
        return groups;
    }

    public void setGroups(GroupDto groups) {
        this.groups = groups;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        StudentDto that = (StudentDto) object;
        return id == that.id && age == that.age && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age);
    }
}
