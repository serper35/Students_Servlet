package org.example.dto.mapper;

import org.example.dto.GroupDto;
import org.example.dto.StudentDto;
import org.example.entity.Groups;
import org.example.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentDtoMapperImplTest {
    private StudentDtoMapper studentDtoMapper;

    @BeforeEach
    void setUp() {
        studentDtoMapper = new StudentDtoMapperImpl();
    }

    @Test
    void mapToStudent() {
        StudentDto studentDto = new StudentDto();
        studentDto.setId(1);
        studentDto.setName("John Doe");
        studentDto.setAge(25);
        GroupDto group = new GroupDto();
        group.setId(10);
        studentDto.setGroups(group);


        Student student = studentDtoMapper.mapToStudent(studentDto);

        assertEquals(studentDto.getId(), student.getId());
        assertEquals(studentDto.getName(), student.getName());
        assertEquals(studentDto.getAge(), student.getAge());
        assertEquals(studentDto.getGroups().getId(), student.getGroup().getId());
    }

    @Test
    void mapToStudentDto() {
        Student student = new Student();
        student.setId(1);
        student.setName("John Doe");
        student.setAge(25);

        Groups group = new Groups();
        group.setId(10);
        student.setGroup(group);

        StudentDto studentDto = studentDtoMapper.mapToStudentDto(student);

        assertEquals(student.getId(), studentDto.getId());
        assertEquals(student.getName(), studentDto.getName());
        assertEquals(student.getAge(), studentDto.getAge());
        assertEquals(student.getGroup().getId(), studentDto.getGroups().getId());
    }

}