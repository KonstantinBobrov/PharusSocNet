package ru.pharus.socnetwork.jstlTags;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pharus.socnetwork.entity.User;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class SubscribeButton extends TagSupport{
    private static final Logger log = LoggerFactory.getLogger(SubscribeButton.class);
    private String caption;
    private String uncaption;

    public void setCaption(String caption){
        this.caption = caption;
    }

    public void setUncaption(String caption){
        this.uncaption = caption;
    }

    @Override
    public int doStartTag() throws JspException {
        User infoUser = (User) pageContext.getRequest().getAttribute("infoUser");
        Boolean isSubscribed = (Boolean) pageContext.getRequest().getAttribute("isSubscribed");

        StringBuilder html = new StringBuilder();
        if(isSubscribed){
				html.append(String.format("<a href='/user?id=%d&unsubscribe='>",infoUser.getId()));
				html.append(String.format("<div class='flatbutton'>%s</div></a>",uncaption));
        }else{
            html.append(String.format("<a href='/user?id=%d&subscribe='>",infoUser.getId()));
            html.append(String.format("<div class='flatbutton'>%s</div></a>",caption));
        }

        try {
            pageContext.getOut().print(html);
        }catch (IOException e){
            log.error("Error taglib out");
        }
        return SKIP_BODY;
    }
}
