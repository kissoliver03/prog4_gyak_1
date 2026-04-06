package hu.pte.mik.prog4.zh1.servlet;

import hu.pte.mik.prog4.zh1.service.FoodService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class DeleteFood extends HttpServlet {
    private final FoodService foodService = new FoodService();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long foodId = Long.parseLong(req.getParameter("deleteid"));
        try{
            this.foodService.deleteById(foodId);
        }
        catch(Exception e){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

        resp.sendRedirect(req.getContextPath() + "/Food");
    }
}
