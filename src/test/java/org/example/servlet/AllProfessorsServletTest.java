package org.example.servlet;

import org.example.dto.GroupDto;
import org.example.dto.ProfessorDto;
import org.example.service.ProfessorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AllProfessorsServletTest {
    @InjectMocks
    private AllProfessorsServlet professorServlet;

    @Mock
    private ProfessorService professorService;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpServletRequest request;

    @Test
    void doGetTest() throws IOException, ServletException {
        List<ProfessorDto> professors = new ArrayList<>();
        professors.add(new ProfessorDto());

        when(professorService.getAllProfessors()).thenReturn(professors);

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(printWriter);

        professorServlet.doGet(request, response);

        verify(response).setContentType("application/json");
        verify(professorService, times(1)).getAllProfessors();
    }
}