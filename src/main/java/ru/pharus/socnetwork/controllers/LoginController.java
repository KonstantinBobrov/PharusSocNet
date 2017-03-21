package ru.pharus.socnetwork.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pharus.socnetwork.dao.exception.DAOException;
import ru.pharus.socnetwork.entity.User;
import ru.pharus.socnetwork.service.UsersService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    // TODO: 20.03.2017 проверить как праивльно делать ссылку на слой сервисов
    private UsersService service;

    @Override
    public void init(ServletConfig config) throws ServletException {
        //service = (UsersService) config.getServletContext().getAttribute("UsersService()");
        service = new UsersService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login_email");
        String password = request.getParameter("password");

        log.debug(String.format("User: %s Password: %s \n", login, password));
        try {
            User currUser = service.getUserByLogin(login);
            if (currUser == null ) request.getRequestDispatcher("/index.jsp").forward(request,response);
            else {
                request.setAttribute("User", currUser);
                request.setAttribute("Posts", service.getUserPosts(currUser));
                request.getRequestDispatcher("/WEB-INF/jsp/user.jsp").forward(request,response);
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
