package ru.pharus.socnetwork.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pharus.socnetwork.dao.exception.DAOException;
import ru.pharus.socnetwork.entity.User;
import ru.pharus.socnetwork.service.CarService;
import ru.pharus.socnetwork.service.UsersService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet({"/user", "/id*", "/friends"})
public class UserController extends javax.servlet.http.HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private UsersService userService;
    private CarService carService;
    private User loginedUser;
    private User showUser;

    @Override
    public void init(ServletConfig config) throws ServletException {
        // TODO: 20.03.2017 проверить как праивльно ссылаться на слой сервисов из сервлета
        //??? True dao layer injection?
        //service = (UsersService) config.getServletContext().getAttribute("UsersService()");
        userService = new UsersService();
        carService = new CarService();
    }


    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        HttpSession session = request.getSession();
        String url = request.getServletPath();

        try {
            loginedUser = userService.getUserById((int) session.getAttribute("user_id"));
            if (loginedUser == null) {
                response.sendRedirect("/error404");
            }
            if (request.getParameter("id") != null) {
                showUser = userService.getUserById(Integer.parseInt(request.getParameter("id")));
                if (showUser == null) {
                    response.sendRedirect("/error404");
                    return;
                }
            } else {
                showUser = loginedUser;
            }
        } catch (DAOException e) {
            log.error("Error in user controller service ", e);
            response.sendRedirect("/error404");
            return;
        } catch (NumberFormatException e) {
            log.warn("Some problem in friends controller ", e);
        }

        if (url.contains("/user"))
            goToUserController(request, response);
        if (url.contains("/friends"))
            goToFriendsController(request, response);
    }

    private void goToUserController(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        try {
            request.setAttribute("logUser", loginedUser);
            request.setAttribute("showUser", showUser);
            request.setAttribute("listPosts", userService.getUserPosts(showUser));
            request.setAttribute("listFriends", userService.getUserFriends(showUser));
            request.setAttribute("listCars", carService.getUserCars(showUser));
            request.getRequestDispatcher("/WEB-INF/jsp/user.jsp").forward(request, response);
        } catch (DAOException e) {
            log.error("Error in user controller database service ", e);
            response.sendRedirect("/error404");
        }
    }

    private void goToFriendsController(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        try {
            request.setAttribute("logUser", loginedUser);
            request.setAttribute("showUser", showUser);
            request.setAttribute("listFriends", userService.getUserFriends(showUser));
            request.getRequestDispatcher("/WEB-INF/jsp/friends.jsp").forward(request, response);
        } catch (DAOException e) {
            log.error("Error in friends controller database service ", e);
            response.sendRedirect("/error404");
        }
    }

}
