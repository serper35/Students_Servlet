package org.example.servlet;

import com.google.gson.Gson;
import org.example.dto.ProfessorDto;
import org.example.service.ProfessorService;
import org.example.service.impl.ProfessorServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "professors")
public class AllProfessorsServlet extends HttpServlet {
    private ProfessorService professorService;

    private final Gson gson = new Gson();

    @Override
    public void init() throws ServletException {
        professorService = new ProfessorServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ProfessorDto> professors = professorService.getAllProfessors();
        String json = gson.toJson(professors);

        resp.setContentType("application/json");
        resp.getWriter().write(json);
    }
}
