package ru.pharus.socnetwork.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pharus.socnetwork.core.Security;
import ru.pharus.socnetwork.dao.exception.DAOException;
import ru.pharus.socnetwork.entity.User;
import ru.pharus.socnetwork.entity.enums.Role;
import ru.pharus.socnetwork.service.UsersService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ResourceBundle;

@WebServlet({"", "/login", "/logout", "/registration"})
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
        request.setCharacterEncoding("utf-8");

        HttpSession session = request.getSession();
        session.removeAttribute("errLogin");
        session.removeAttribute("errRegister");
        setLocale(request, session);

        ResourceBundle bundle = ResourceBundle.getBundle("localization");

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
            User tmpUser = new User();
            tmpUser.setLogin(request.getParameter("login_email"));
            tmpUser.setPassword(request.getParameter("password"));
            log.debug(String.format("Try to login with login: %s", tmpUser.getLogin()));

            try {
                User logUser = service.getUserByLogin(tmpUser.getLogin());
                if (logUser != null) {
                    if (Security.checkHash(tmpUser.getPassword(), "123", logUser.getPassword())) {
                        log.info(String.format("User %s logged and going to user page", logUser.getLogin()));
                        session.setAttribute("user_id", logUser.getId());
                        session.setAttribute("login", logUser.getLogin());
                        response.sendRedirect("/user");
                        return;
                    }
                }
                request.setAttribute("user", tmpUser);
                session.setAttribute("errLogin", bundle.getString("errLogin"));
            } catch (DAOException e) {
                e.printStackTrace();
            }
        }

        if ("/registration".equalsIgnoreCase(request.getServletPath())) {
            User user = new User();
            user.setLogin(request.getParameter("regLoginEmail"));
            user.setPassword(Security.generateHash(request.getParameter("regPassword"), Security.SOME_SALT));
            user.setFullName(request.getParameter("regFullName"));
            user.setRole(Role.USER);

            String err = service.validate(user);

            if (err.isEmpty()) {
                try {
                    new UsersService().register(user);
                    session.setAttribute("user_id", user.getId());
                    session.setAttribute("login", user.getLogin());
                    response.sendRedirect("/edit");
                    return;
                } catch (DAOException e) {
                    log.error("Registration error ", e);
                }
            }

            session.setAttribute("errRegister", err);
            request.setAttribute("user", user);
        }
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
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
