package ru.pharus.socnetwork.jstlTags;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pharus.socnetwork.entity.Model;
import ru.pharus.socnetwork.entity.User;
import ru.pharus.socnetwork.service.CarService;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;


public class AddCarForm extends TagSupport{
    private static final Logger log = LoggerFactory.getLogger(AddCarForm.class);

    @Override
    public int doStartTag() throws JspException {
        Locale locale = pageContext.findAttribute("lang") == null ? new Locale("ru") : new Locale((String)pageContext.findAttribute("lang"));
        ResourceBundle bundle = ResourceBundle.getBundle("localization", locale);
        List<Model> models = (List<Model>) pageContext.getRequest().getAttribute("carModels");

        StringBuilder html = new StringBuilder();
        html.append("<div class='slide'> <label for='slide1'><ul> ---==");
        html.append(bundle.getString("car.add"));
        html.append("==---</label><input type='checkbox' id='slide1'/>");
        html.append("<div class='content'><form action='/user' method='get'/><li>");
        html.append("<select name='carmodel' required><option disabled selected>...</option>");
        for(Model model:models){
            html.append(String.format("<option value='%d'> %s </option>",model.getId(),model.getName()));
        }
        html.append("</select><br><p>");
        html.append(String.format(" %s <input type='text' name='carnumber' placeholder='o777o178' pattern='.{4,10}' required style='width: 90px;'/>",bundle.getString("car.number")));
        html.append(String.format(" %s<input type='text' name='caryear' placeholder='YYYY' required pattern='^[0-9]{4,4}' style='width: 60px;'/></li>",bundle.getString("car.number")));
        html.append("</p><li><input type='submit'/></li></div></ul></div></form>");

        try{
            pageContext.getOut().print(html);
        }catch (IOException e){
            log.error("Error taglib out", e);
        }

        return SKIP_BODY;
    }
}
