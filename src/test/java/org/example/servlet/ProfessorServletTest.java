package org.example.servlet;

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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfessorServletTest {
    @InjectMocks
    private ProfessorServlet professorServlet;

    @Mock
    private ProfessorService professorService;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpServletRequest request;

    @Test
    void doGetTest() throws IOException, ServletException {
        Long id = 1L;

        when(request.getParameter("id")).thenReturn(String.valueOf(id));
        when(professorService.getProfessor(id)).thenReturn(new ProfessorDto());

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        professorServlet.doGet(request, response);

        verify(response).setContentType("application/json");
        verify(professorService, times(1)).getProfessor(id);
    }

    @Test
    void doPost() throws ServletException, IOException {
        String name = "Jason";

        when(request.getParameter("name")).thenReturn(name);

        ProfessorDto professorDto = new ProfessorDto();
        professorDto.setName(name);

        professorServlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_CREATED);
        verify(professorService, times(1)).saveProfessor(professorDto);
    }

    @Test
    void doPut() throws ServletException, IOException {
        String name = "Jason";
        Long id = 1L;

        when(request.getParameter("name")).thenReturn(name);
        when(request.getParameter("id")).thenReturn(String.valueOf(id));

        ProfessorDto professorDto = new ProfessorDto();
        professorDto.setId(id);
        professorDto.setName(name);

        professorServlet.doPut(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(professorService, times(1)).updateProfessor(professorDto);
    }

    @Test
    void doDelete() throws ServletException, IOException {
        Long id = 1L;

        when(request.getParameter("id")).thenReturn(String.valueOf(id));

        professorServlet.doDelete(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(professorService, times(1)).deleteProfessor(id);
    }
}