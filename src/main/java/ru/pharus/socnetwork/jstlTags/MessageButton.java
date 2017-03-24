package ru.pharus.socnetwork.jstlTags;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pharus.socnetwork.entity.User;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;


public class MessageButton extends TagSupport {
    private static final Logger log = LoggerFactory.getLogger(SubscribeButton.class);
    private String caption;

    public void setCaption(String caption){
        this.caption = caption;
    }

    @Override
    public int doStartTag() throws JspException {
        User infoUser = (User) pageContext.getRequest().getAttribute("infoUser");

        StringBuilder html = new StringBuilder();
        html.append(String.format("<a href='/user?message=%d'>",infoUser.getId()));
        html.append(String.format("<div class='flatbutton'>%s</div></a>",caption));

        try {
            pageContext.getOut().print(html);
        }catch (IOException e){
            log.error("Error taglib out");
        }
        return SKIP_BODY;
    }
}
