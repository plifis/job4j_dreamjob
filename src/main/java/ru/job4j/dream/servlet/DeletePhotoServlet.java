package ru.job4j.dream.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class DeletePhotoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String path = "C:\\images\\";
        for (File file : new File(path).listFiles()) {
            if (id.equals(file.getName().substring(0, file.getName().lastIndexOf(".")))) {
                file.delete();
                 break;
            }
        }
        resp.sendRedirect(req.getContextPath() + "/candidates.do");
    }
}
