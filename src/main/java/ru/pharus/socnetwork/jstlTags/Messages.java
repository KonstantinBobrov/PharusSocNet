package ru.pharus.socnetwork.jstlTags;

import ru.pharus.socnetwork.dao.DaoFactory;
import ru.pharus.socnetwork.dao.exception.DAOException;
import ru.pharus.socnetwork.entity.Message;
import ru.pharus.socnetwork.entity.User;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

public class Messages extends TagSupport {
    private List<Message> list;

    public void setList(List<Message> list) {
        this.list = list;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            User logUser = (User)pageContext.getRequest().getAttribute("logUser");
            StringBuilder html = new StringBuilder();
            for (Message message : list) {
                int id = logUser.getId() == message.getFromUserId() ? message.getToUserId() : message.getFromUserId();
                User user = DaoFactory.getInstance().getUserDao().getById(id);
                html.append("<p><div class=\"lastchat\">");
                html.append(String.format("<a href=/messages?id=%d>%s</a>", user.getId(), user.getFullName()));
                html.append("<br>").append(message.getMessage());
                html.append("</div>");
            }
            pageContext.getOut().print(html);
        } catch (DAOException | IOException e) {

        }
        return SKIP_BODY;
    }
}
