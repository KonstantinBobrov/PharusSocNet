package ru.pharus.socnetwork.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pharus.socnetwork.dao.exception.DAOException;
import ru.pharus.socnetwork.entity.Post;
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
        User loginUser;
        User infoUser;

        try {
            loginUser = userService.getUserById((int) session.getAttribute("user_id"));
            if (loginUser == null) {
                response.sendRedirect("/error404");
            }
            request.setAttribute("logUser", loginUser);
            request.setAttribute("infoUser", loginUser);

            if (request.getParameter("id") != null) {
                infoUser = userService.getUserById(Integer.parseInt(request.getParameter("id")));
                if (infoUser == null) {
                    response.sendRedirect("/error404");
                    return;
                }
                request.setAttribute("infoUser", infoUser);
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
        User logUser = (User) request.getAttribute("logUser");
        User infoUser = (User) request.getAttribute("infoUser");

        String cardel = request.getParameter("cardel");
        String delpost = request.getParameter("delpost");
        String addpost = request.getParameter("addpost");
        String editpost = request.getParameter("editpost");
        String subscribe = request.getParameter("subscribe");
        String unsubscribe = request.getParameter("unsubscribe");

        try {
            // запрос на довбавление поста
            if (null != addpost && !addpost.isEmpty()) {
                Post post = new Post();
                post.setText(addpost);
                post.setUserId(logUser.getId());
                String postid = request.getParameter("postid");
                if (postid != null && !addpost.isEmpty()) {
                    post.setId(Integer.parseInt(postid));
                    userService.editPost(post);
                }else {
                    userService.addPost(post);
                }
            }
            // запрос на удаление поста
            if (null != delpost && !delpost.isEmpty()) {
                // TODO: 24.03.2017 добавть проверку на невозможность удаления чужого поста
                userService.deletePost(Integer.parseInt(delpost));
            }
            // запрос на редактирование поста
            if (null != editpost) {
                Post post = userService.getPostById(Integer.parseInt(editpost));
                if (null != post)
                    request.setAttribute("editPost", post);
            }
            // запрос на удаление авто
            if (null != cardel && !cardel.isEmpty()) {
                // TODO: 24.03.2017 добавть проверку на невозможность удаления чужого авто
                carService.deleteCar(Integer.parseInt(cardel));
            }
            // запрос на подписку
            if (null != subscribe) {
                userService.subscribe(logUser, infoUser);
            }
            // запрос на отписку
            if (null != unsubscribe) {
                userService.unsubscribe(logUser, infoUser);
            }

            // Занесение атрибутов в ответ
            request.setAttribute("listPosts", userService.getUserPosts(infoUser));
            request.setAttribute("listFriends", userService.getUserFriends(infoUser));
            request.setAttribute("listCars", carService.getUserCars(infoUser));
            request.setAttribute("isSubscribed", userService.isSubcribed(logUser, infoUser));
            request.getRequestDispatcher("/WEB-INF/jsp/user.jsp").forward(request, response);
        } catch (DAOException e) {
            log.error("Error in user controller", e);
        }
    }

    private void goToFriendsController(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User logUser = (User) request.getAttribute("logUser");
        User infoUser = (User) request.getAttribute("infoUser");

        try {
            request.setAttribute("listFriends", userService.getUserFriends(infoUser));
            request.getRequestDispatcher("/WEB-INF/jsp/friends.jsp").forward(request, response);
        } catch (DAOException e) {
            log.error("Error in friends controller database service ", e);
            response.sendRedirect("/error404");
        }
    }

}
