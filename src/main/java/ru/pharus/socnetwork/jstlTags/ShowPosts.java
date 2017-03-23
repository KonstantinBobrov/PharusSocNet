package ru.pharus.socnetwork.jstlTags;

import lombok.SneakyThrows;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class ShowPosts extends BodyTagSupport {
    @Override
    @SneakyThrows
    public int doStartTag() throws JspException {
        pageContext.getOut().print("<b> Тег с телом начало</b>");
        return EVAL_BODY_INCLUDE;
    }


    @Override
    @SneakyThrows
    public int doAfterBody() throws JspException {
        pageContext.getOut().print("<br><b> Тут само тело<b>");
        return SKIP_BODY;
    }

    @Override
    @SneakyThrows
    public int doEndTag() throws JspException {
        pageContext.getOut().print("<br><b> тут конец</b>");
        return SKIP_BODY;
    }


}
