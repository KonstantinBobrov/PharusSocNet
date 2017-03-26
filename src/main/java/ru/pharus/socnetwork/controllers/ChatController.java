package ru.pharus.socnetwork.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pharus.socnetwork.dao.exception.DAOException;
import ru.pharus.socnetwork.entity.Message;
import ru.pharus.socnetwork.entity.User;
import ru.pharus.socnetwork.service.UsersService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;


@WebServlet({"/im", "/messages"})
public class ChatController extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(ChatController.class);
    private UsersService userService;

    @Override
    public void init() throws ServletException {
        // TODO: 20.03.2017 проверить как праивльно ссылаться на слой сервисов из сервлета
        //??? True dao layer injection?
        //service = (UsersService) config.getServletContext().getAttribute("UsersService()");
        userService = new UsersService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();

        try {
            User loginUser = userService.getUserById((int) session.getAttribute("user_id"));
            if (loginUser == null) {
                log.trace(String.format("User with id %s not found. Redirect to error page", session.getAttribute("user_id")));
                response.sendRedirect("/error404");
                return;
            }

            if (request.getParameter("id") != null) {
                int id = Integer.parseInt(request.getParameter("id"));
                String send = request.getParameter("send");

                User infoUser = userService.getUserById(id);
                request.setAttribute("logUser", loginUser);
                request.setAttribute("infoUser", infoUser);

                if (send != null) {
                    Message message = new Message();
                    message.setFromUserId(loginUser.getId());
                    message.setToUserId(infoUser.getId());
                    message.setMessage(send);
                    message.setPostTime(LocalDateTime.now());

                    String err = userService.validate(message);
                    if (err.isEmpty()) {
                        userService.sendMessage(message);
                    }
                }
                request.setAttribute("logUser", loginUser);
                request.setAttribute("infoUser", infoUser);
                request.setAttribute("listMessages", userService.getUserMessages(loginUser, infoUser));
                request.getRequestDispatcher("/WEB-INF/jsp/chat.jsp").forward(request, response);
                return;

            }

            request.setAttribute("logUser", loginUser);
            request.setAttribute("listMessages", userService.getUserMessages(loginUser));
            request.getRequestDispatcher("/WEB-INF/jsp/im.jsp").forward(request, response);
        } catch (DAOException e) {
            log.error("DAO error in chat controller", e);
        }
    }


}
