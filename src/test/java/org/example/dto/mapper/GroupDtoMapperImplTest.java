package org.example.dto.mapper;

import org.example.dto.GroupDto;
import org.example.entity.Groups;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

class GroupDtoMapperImplTest {
    private GroupDtoMapperImpl groupDtoMapper;

    @BeforeEach
    public void setUp() {
        groupDtoMapper = new GroupDtoMapperImpl();
    }

    @Test
    public void testMapToGroup() {
        GroupDto groupDto = new GroupDto();
        groupDto.setId(1);
        groupDto.setFaculty("Computer Science");
        groupDto.setNumberOfStudents(30);

        Groups group = groupDtoMapper.mapToGroup(groupDto);

        assertEquals(groupDto.getId(), group.getId());
        assertEquals(groupDto.getFaculty(), group.getFaculty());
        assertEquals(groupDto.getNumberOfStudents(), group.getNumberOfStudents());
    }

    @Test
    public void testMapToGroupDto() {
        Groups group = new Groups();
        group.setId(1);
        group.setFaculty("Computer Science");
        group.setNumberOfStudents(30);

        GroupDto groupDto = groupDtoMapper.mapToGroupDto(group);

        assertEquals(group.getId(), groupDto.getId());
        assertEquals(group.getFaculty(), groupDto.getFaculty());
        assertEquals(group.getNumberOfStudents(), groupDto.getNumberOfStudents());
    }
}