package ru.pharus.socnetwork.jstlTags;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pharus.socnetwork.entity.Post;
import ru.pharus.socnetwork.entity.User;
import ru.pharus.socnetwork.entity.enums.Role;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class ShowPosts extends TagSupport {
    private static final Logger log = LoggerFactory.getLogger(ShowPosts.class);
    private List<Post> list;
    private int max;

    public void setList(List<Post> list) {
        this.list = list;
    }
    public void setMax(int max){
        this.max = max;
    }

    @Override
    public int doStartTag() throws JspException {
        Locale locale = pageContext.findAttribute("lang") == null ? new Locale("ru") : new Locale((String)pageContext.findAttribute("lang"));
        ResourceBundle bundle = ResourceBundle.getBundle("localization", locale);

        //Collections.list(pageContext.getRequest().getAttributeNames()).forEach(System.out::println);
        User logUser = (User) pageContext.getRequest().getAttribute("logUser");
        User infoUser = (User) pageContext.getRequest().getAttribute("infoUser");
        Post editPost = (Post) pageContext.getRequest().getAttribute("editPost");
        int increment = pageContext.getRequest().getParameter("allposts") == null ?
                0 : Integer.valueOf(pageContext.getRequest().getParameter("allposts"));
        increment++;

        boolean adminMode = false;
        adminMode = logUser.getRole() == Role.ADMIN;
        int localMax = max * increment;
        if (localMax == 0 || localMax > list.size()) localMax = list.size();

        StringBuilder html = new StringBuilder();

        if (logUser.getId() == infoUser.getId()){
            html.append("<br> <form action='/user' method='get'>");
            html.append(String.format("<input type='hidden' name='postid' value='%s'>", editPost != null ? editPost.getId() : ""));
            html.append("<textarea name='addpost' rows=5 cols=68 minlength=5 maxlength=2000 style='resize: vertical;' required>");
            html.append( editPost != null ? editPost.getText() : "");
            html.append("</textarea><input type='submit' size=25></form>");
        }

        for(int i = 0; i < localMax; i++){
            html.append("<p><table border=1 width=100% class = 'tablistpost'>");
            html.append("<tr><td><p><b>");
            html.append(String.format("<a href='/user?id=%s'>", infoUser.getId()));
            html.append(String.format("<div class='mainfriends' style=\"background-image:url('img/ava/%s')\"></div>",infoUser.getAvatar()));
            Post post = list.get(i);
            html.append(String.format("<a href='/user?id=%s&view=%d'>%s %s</a>",infoUser.getId(), post.getId(),bundle.getString("userpost"), infoUser.getFullName()));
            html.append("</b>");
            html.append("</td></tr><tr><td><p>");
            html.append(list.get(i).getText());
            html.append("</td></tr>");
            if (logUser.getId() == infoUser.getId() || adminMode) {
                html.append("<tr><td>");
                html.append(String.format(": <a href='/user?id=%s&delpost=%d'>%s</a>", infoUser.getId(), post.getId(), bundle.getString("delete") ));
                html.append(String.format(": <a href='/user?id=%s&editpost=%d'>%s</a> :", infoUser.getId(), post.getId(), bundle.getString("edit") ));
                html.append("</td></tr>");
            }
            html.append("</table>");
        }

        if(list.size() > localMax){
            html.append(String.format("<p><a href='/user?id=%s&allposts=%d'>  ---== %s ==--- </a>", infoUser.getId(), increment, bundle.getString("showmore")));
        }

        try{
            pageContext.getOut().print(html);
        }catch (IOException e){
            log.error("Error taglib out", e);
        }

        return SKIP_BODY;
    }

}
