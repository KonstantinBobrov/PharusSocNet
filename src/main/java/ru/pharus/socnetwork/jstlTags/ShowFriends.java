package ru.pharus.socnetwork.jstlTags;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pharus.socnetwork.entity.User;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

public class ShowFriends extends TagSupport {
    private static final Logger log = LoggerFactory.getLogger(ShowFriends.class);
    private List<User> list;
    private User infoUser;
    private String title = "Info";
    private int cols = 3;
    private int max;

    public void setList(List list) {
        //Collections.list(pageContext.getRequest().getAttributeNames()).forEach(System.out::println);
        infoUser = (User) pageContext.getRequest().getAttribute("infoUser");
        this.list = list;
    }

    public void setCols(int cols){
        this.cols = cols;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setMax(int max){
        this.max = max;
    }

    @Override
    public int doStartTag() throws JspException {
        if (max == 0 || max > list.size()) max = list.size();

        StringBuilder html = new StringBuilder();

        html.append("<table class = 'tablistfr'><tr><td colspan=2>");
        html.append(String.format("<a href='/friends?id=%s&selection=all'> %s </td><td> %s </td>", infoUser.getId(), title, list.size()));
        for(int i = 0; i < max; ){
            html.append("</tr><tr>");
            for(int j = 0; j < cols; j++){
                User user = list.get(i);
                html.append(String.format("<td><div><a href='/user?id=%s'><div class='mainfriends' style=\"background-image:url('img/ava/%s')\"></div>", user.getId(),user.getAvatar()));
                html.append(user.getFullName());
                html.append("</a></div></td>");
                i++;
                if (i == max) break;
            }
            if (i == max) break;
        }
        html.append("</tr></table>");

        try{
            pageContext.getOut().print(html);
        }catch (IOException e){
            log.error("Error taglib out", e);
        }

        return SKIP_BODY;
    }
}
