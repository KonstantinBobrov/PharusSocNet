package ru.pharus.socnetwork.jstlTags;

import ru.pharus.socnetwork.dao.DaoFactory;
import ru.pharus.socnetwork.dao.exception.DAOException;
import ru.pharus.socnetwork.entity.Message;
import ru.pharus.socnetwork.entity.User;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspTag;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

public class ChatMessages extends TagSupport {
    private List<Message> list;

    public void setList(List<Message> list) {
        this.list = list;
    }

    @Override
    public int doStartTag() throws JspException {
        try {

            StringBuilder html = new StringBuilder();
            for (Message message : list) {
                User fromuser = DaoFactory.getInstance().getUserDao().getById(message.getFromUserId());

                html.append("<p><div class=\"lastchat\">");
                html.append(String.format("<a href=/messages?id=%d>%s</a>", fromuser.getId(), fromuser.getFullName()));
                html.append("<br>").append(message.getMessage());
                html.append("</div>");
            }
            pageContext.getOut().print(html);
        } catch (DAOException | IOException e) {

        }
        return SKIP_BODY;
    }
}