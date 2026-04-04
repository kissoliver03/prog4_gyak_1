package hu.pte.mik.prog4.zh1.servlet;

import hu.pte.mik.prog4.zh1.model.Food;
import hu.pte.mik.prog4.zh1.service.FoodService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FoodServlet extends HttpServlet {
    private final FoodService foodService = new FoodService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String insertCookie = req.getParameter("cookieid");
        if (insertCookie != null) {
            Cookie cookie = new Cookie("favourite", URLEncoder.encode(insertCookie, StandardCharsets.UTF_8));
            resp.addCookie(cookie);
        }

        String XMLid = req.getParameter("xmlid");
        if (XMLid != null) {
            Long LongXMLid = Long.parseLong(XMLid);
            Food foundFood = foodService.findById(LongXMLid);
            foodService.convertToXml(foundFood);
        }
        List<Food> model = new ArrayList<Food>();
        model = foodService.findAll();

        req.setAttribute("model", model);
        req.getRequestDispatcher("/food.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String deleteId = req.getParameter("deleteid");

        if (deleteId != null) {
            Long LongDeleteId = Long.parseLong(deleteId);
            foodService.deleteById(LongDeleteId);

            List<Food> foodList = new ArrayList<>();
            foodList = foodService.findAll();
            req.setAttribute("foodList", foodList);

            req.getRequestDispatcher("/food.jsp").forward(req, resp);
        }
    }
}
