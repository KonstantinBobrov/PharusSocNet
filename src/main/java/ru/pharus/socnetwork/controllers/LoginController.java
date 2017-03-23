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
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet({"", "/login", "/logout"})
public class LoginController extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    private UsersService service;

    @Override
    public void init(ServletConfig config) throws ServletException {
        // TODO: 20.03.2017 проверить как праивльно ссылаться на слой сервисов из сервлета
        //??? True dao layer injection?
        //service = (UsersService) config.getServletContext().getAttribute("UsersService()");
        service = new UsersService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        setLocale(request, session);

        if ("/logout".equalsIgnoreCase(request.getServletPath())) {
            log.debug("Logout user session");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Cache-Control", "no-store");
            response.setDateHeader("Expires", 0);
            session.invalidate();
            response.sendRedirect("/");
            return;
        }

        if ("/login".equalsIgnoreCase(request.getServletPath())) {
            String login = request.getParameter("login_email");
            String password = request.getParameter("password");
            log.debug(String.format("Try to login with login: %s", login));


            //UsersService.validateLogin(login);


            try {
                User currUser = service.getUserByLogin(login);
                if (currUser == null) request.getRequestDispatcher("/index.jsp").forward(request, response);
                else {
                    log.info(String.format("User %s logged and going to user page", currUser.getFullName()));
                    session.setAttribute("user_id", currUser.getId());
                    response.sendRedirect("/user");
                    return;
                }
            } catch (DAOException e) {
                e.printStackTrace();
            }
        }

        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    private void setLocale(HttpServletRequest request, HttpSession session) {
        if (request.getParameter("lang") != null)
            switch (request.getParameter("lang")) {
                case "en": {
                    log.debug("Set default locale to english");
                    session.setAttribute("lang", "en");
                    break;
                }
                case "ru": {
                    log.debug("Set default locale to russian");
                    session.setAttribute("lang", "ru");
                    break;
                }
                default: {
                    session.setAttribute("lang", "ru");
                }
            }
    }
}
