package ru.pharus.socnetwork.jstlTags;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pharus.socnetwork.dao.exception.DAOException;
import ru.pharus.socnetwork.entity.Car;
import ru.pharus.socnetwork.entity.User;
import ru.pharus.socnetwork.entity.enums.Role;
import ru.pharus.socnetwork.service.CarService;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;


public class ShowCars extends TagSupport {
    private static final Logger log = LoggerFactory.getLogger(ShowCars.class);
    CarService carService;
    private List<Car> list;
    private User infoUser;
    private User logUser;
    private int max;
    private boolean adminMode = false;

    public void setList(List list) {
        //Collections.list(pageContext.getRequest().getAttributeNames()).forEach(System.out::println);
        carService = new CarService();
        infoUser = (User) pageContext.getRequest().getAttribute("infoUser");
        logUser = (User) pageContext.getRequest().getAttribute("logUser");
        if(logUser.getRole() == Role.ADMIN) adminMode = true;
        else  adminMode = false;
        this.list = list;
    }

    public void setMax(int max){
        this.max = max;
    }

    @Override
    public int doStartTag() throws JspException {
        Locale locale = pageContext.findAttribute("lang") == null ? new Locale("ru") : new Locale((String)pageContext.findAttribute("lang"));
        ResourceBundle bundle = ResourceBundle.getBundle("localization", locale);

        if (max == 0 || max > list.size()) max = list.size();

        StringBuilder html = new StringBuilder();
        try{
        for(int i = 0; i < max; i++){
            html.append("<p><table border=1 width=100% class = 'tablistcars'>");
            html.append("<tr><td colspan=2><p><b>");
            Car car = list.get(i);
            html.append(String.format("%s: %s", bundle.getString("car.modelname"),carService.getModelById(car.getModelId()).getName()));
            html.append("</b>");
            html.append("</td></tr><tr><td>");
            html.append(String.format("%s: %s ", bundle.getString("car.number"),car.getCarNumber()));
            html.append("</td><td>");
            html.append(String.format("%s: %s ", bundle.getString("car.year"), car.getYear()));
            html.append("</td></tr>");
            if (logUser.getId() == infoUser.getId() || adminMode) {
                html.append("<tr><td colspan=2>");
                html.append(String.format(": <a href='?cardel=%d'>%s</a> : ", car.getId(),bundle.getString("delete")));
                html.append("</td></tr>");
            }
            html.append("</table>");
        }

        if(list.size() > max){
            //html.append(String.format("<p><a href='/user?id=%s&allcars=%d'>  ---== %s ==--- </a>", infoUser.getId(), increment, bundle.getString("showmore")));
        }


            pageContext.getOut().print(html);
        }catch (IOException | DAOException e){
            log.error("Error taglib out", e);
        }

        return SKIP_BODY;
    }
}
