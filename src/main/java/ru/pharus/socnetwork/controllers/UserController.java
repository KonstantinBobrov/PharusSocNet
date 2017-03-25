package ru.pharus.socnetwork.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pharus.socnetwork.core.Security;
import ru.pharus.socnetwork.dao.exception.DAOException;
import ru.pharus.socnetwork.entity.Car;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@WebServlet({"/user", "/id*", "/friends", "/edit", "/feed"})
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
        request.setCharacterEncoding("utf-8");
        doGet(request, response);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        // Deleting oldest error messages
        request.removeAttribute("errEditProfile");
        request.removeAttribute("errEditPost");
        request.removeAttribute("errEditCars");

        HttpSession session = request.getSession();
        String url = request.getServletPath();

        try {
            // Getting information about logged user
            User loginUser = userService.getUserById((int) session.getAttribute("user_id"));
            if (loginUser == null) {
                log.trace(String.format("User with id %s not found. Redirect to error page", session.getAttribute("user_id")));
                response.sendRedirect("/error404");
            }
            request.setAttribute("logUser", loginUser);
            request.setAttribute("infoUser", loginUser);

            // Getting information about required user
            if (request.getParameter("id") != null) {
                User infoUser = userService.getUserById(Integer.parseInt(request.getParameter("id")));
                if (infoUser == null) {
                    log.trace(String.format("User with id %s not found. Redirect to error page", session.getAttribute("user_id")));
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
            log.warn("Some problem in user controller ", e);
        }

        if (url.contains("/edit"))
            goToEditController(request, response);
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
        String carmodel = request.getParameter("carmodel");

        try {
            // запрос на довбавление поста
            if (null != addpost && !addpost.equals("")) {
                Post post = new Post();
                post.setText(addpost);
                post.setUserId(logUser.getId());
                String postid = request.getParameter("postid");
                String err = userService.validatePost(post);
                if (err.isEmpty()) {
                    try {
                        if (postid != null && !postid.equals("")) {
                            post.setId(Integer.parseInt(postid));
                            userService.editPost(post);
                        } else {
                            userService.addPost(post);
                        }
                    } catch (DAOException e) {
                        log.error(String.format("Error update user post %s", logUser.getLogin()));
                    }
                }
                request.getSession().setAttribute("errEditPost", err);
            }
            // запрос на удаление поста
            if (null != delpost && !delpost.equals("")) {
                // TODO: 24.03.2017 добавть проверку на невозможность удаления чужого поста
                userService.deletePost(Integer.parseInt(delpost));
            }
            // запрос на редактирование поста
            if (null != editpost) {
                Post post = userService.getPostById(Integer.parseInt(editpost));
                if (null != post)
                    request.setAttribute("editPost", post);
            }

            if (null != carmodel) {
                Car car = new Car();
                car.setUserId(logUser.getId());
                car.setModelId(Integer.parseInt(request.getParameter("carmodel")));
                car.setCarNumber(request.getParameter("carnumber"));
                car.setYear(Integer.parseInt(request.getParameter("caryear")));
                String err = carService.validate(car);
                if (err.isEmpty()) {
                    try {
                        carService.addCar(car);
                    } catch (DAOException e) {
                        log.error(String.format("Error update user post %s", logUser.getLogin()));
                    }
                }
                request.getSession().setAttribute("errEditCars", err);
            }

            // запрос на удаление авто
            if (null != cardel && !cardel.equals("")) {
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
            request.setAttribute("carModels", carService.getModels());
            request.setAttribute("listPosts", userService.getUserPosts(infoUser));
            request.setAttribute("listFriends", userService.getUserFriends(infoUser));
            request.setAttribute("listCars", carService.getUserCars(infoUser));
            request.setAttribute("isSubscribed", userService.isSubcribed(logUser, infoUser));
            request.getRequestDispatcher("/WEB-INF/jsp/user.jsp").forward(request, response);
        } catch (DAOException e) {
            log.error("Error in user controller", e);
        }
    }

    private void goToEditController(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User editUser = (User) request.getAttribute("logUser");
        String err;
        if (request.getParameter("regLoginEmail") != null) {
            editUser.setPassword(request.getParameter("regPassword"));
            editUser.setFullName(request.getParameter("regFullName"));
            String birthDate = request.getParameter("birthDate");
            if (birthDate != null && !birthDate.isEmpty()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd");
                LocalDate date = LocalDate.parse(request.getParameter("birthDate"), formatter);
                editUser.setBirthDate(date);
            }
            //Validation user in Hibernate Validator
            err = userService.validate(editUser);
            if (err.isEmpty()) {
                try {
                    editUser.setPassword(Security.generateHash(editUser.getPassword(), Security.SOME_SALT));
                    userService.updateUser(editUser);
                    response.sendRedirect("/user");
                    return;
                } catch (DAOException e) {
                    log.error(String.format("Error update user %s", editUser.getLogin()));
                }
            }
            // if contain errors pass to JSP
            request.getSession().setAttribute("errEditProfile", err);
        }
        request.setAttribute("user", editUser);
        request.getRequestDispatcher("/WEB-INF/jsp/edit.jsp").forward(request, response);
    }

    private void goToFriendsController(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
