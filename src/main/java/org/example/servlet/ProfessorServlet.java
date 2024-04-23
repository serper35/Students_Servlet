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

@WebServlet(value = "professor")
public class ProfessorServlet extends HttpServlet {

    private ProfessorService professorService;

    private final Gson gson = new Gson();


    @Override
    public void init() throws ServletException {
        professorService = new ProfessorServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));

        ProfessorDto professor = professorService.getProfessor(id);
        String json = gson.toJson(professor);

        resp.setContentType("application/json");
        resp.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");

        ProfessorDto professor = new ProfessorDto();
        professor.setName(name);

        professorService.saveProfessor(professor);

        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        String name = req.getParameter("name");

        ProfessorDto professor = new ProfessorDto();
        professor.setId(id);
        professor.setName(name);

        professorService.updateProfessor(professor);

        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));

        professorService.deleteProfessor(id);

        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
