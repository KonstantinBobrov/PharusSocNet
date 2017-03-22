package ru.pharus.socnetwork.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pharus.socnetwork.dao.exception.DAOException;
import ru.pharus.socnetwork.entity.User;
import ru.pharus.socnetwork.service.UsersService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet({"/user","/id*"})
public class UserController extends javax.servlet.http.HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private UsersService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        // TODO: 20.03.2017 проверить как праивльно ссылаться на слой сервисов из сервлета
        //??? True dao layer injection?
        //service = (UsersService) config.getServletContext().getAttribute("UsersService()");
        userService = new UsersService();
    }


    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
            doGet(request,response);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        HttpSession session = request.getSession();
        try {
            User currUser = userService.getUserById((int) request.getSession().getAttribute("user_id"));
            request.setAttribute("currentUser", currUser);
            request.setAttribute("Posts", userService.getUserPosts(currUser));

            request.getRequestDispatcher("/WEB-INF/jsp/user.jsp").forward(request,response);
        }catch (DAOException e){
            // TODO: 22.03.2017 перенаправить на error page jsp 
           response.sendRedirect("/");
        }



    }
}
