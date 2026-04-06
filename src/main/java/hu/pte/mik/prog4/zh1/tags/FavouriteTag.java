package hu.pte.mik.prog4.zh1.tags;

import hu.pte.mik.prog4.zh1.model.Food;
import hu.pte.mik.prog4.zh1.repository.FoodRepository;
import hu.pte.mik.prog4.zh1.service.FoodService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.PageContext;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.stream.Stream;

public class FavouriteTag extends SimpleTagSupport {
   private final FoodService foodService = new FoodService();

    @Override
    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();
        var context = (PageContext) getJspContext();
        var request = (HttpServletRequest) context.getRequest();
        var favourite = Optional.ofNullable(request.getCookies())
                .map(Stream::of)
                .orElse(Stream.empty())
                .filter(cookie -> cookie.getName().equals("favouriteid"))
                .findFirst()
                .map(cookie -> URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8))
                .orElse(null);

        if (favourite != null) {
            Long longId = Long.parseLong(favourite);
            Food favouriteFood = foodService.findById(longId);
            out.println("A kedvenc ételed a következő: " + favouriteFood.getFoodName() + "(" +favouriteFood.getRestaurantName() + ")");
        }
        else{
            out.println("Még nincs kedvenc ételed!");
        }
    }
}
