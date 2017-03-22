package ru.pharus.socnetwork.jstlTags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Created by PharuS on 22.03.2017.
 */
public class SetLocale extends TagSupport {
    @Override
    public int doStartTag() throws JspException {
       // pageContext.getOut().write("<fmt:setLocale value=\"${not empty sessionScope['lang'] ? sessionScope['lang'] : 'Ru'}\" />");

        return SKIP_BODY;
    }
}
