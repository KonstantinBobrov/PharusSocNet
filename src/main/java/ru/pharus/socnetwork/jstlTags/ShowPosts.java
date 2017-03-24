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
        //Collections.list(pageContext.getRequest().getAttributeNames()).forEach(System.out::println);
        User logUser = (User) pageContext.getRequest().getAttribute("logUser");
        User infoUser = (User) pageContext.getRequest().getAttribute("infoUser");
        Post editPost = (Post) pageContext.getRequest().getAttribute("editPost");

        boolean adminMode = false;
        adminMode = logUser.getRole() == Role.ADMIN;

        int localMax = max;
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
            html.append(String.format("<a href='/user?id=%s&view=%d'>user post</a>",infoUser.getId(), post.getId()));
            html.append("</b>");
            html.append("</td></tr><tr><td><p>");
            html.append(list.get(i).getText());
            html.append("</td></tr>");
            if (logUser.getId() == infoUser.getId() || adminMode) {
                html.append("<tr><td>");
                html.append(String.format(": <a href='/user?id=%s&delpost=%d'>Delete</a>", infoUser.getId(), post.getId() ));
                html.append(String.format(": <a href='/user?id=%s&editpost=%d'>Edit</a> :", infoUser.getId(), post.getId() ));
                html.append("</td></tr>");
            }
            html.append("</table>");
        }

        if(list.size() > localMax){
            html.append(String.format("<p><a href='/user?id=%s&allposts=1'>  ---== Show more =--- </a>", infoUser.getId()));
        }

        try{
            pageContext.getOut().print(html);
        }catch (IOException e){
            log.error("Error taglib out", e);
        }

        return SKIP_BODY;
    }

}
