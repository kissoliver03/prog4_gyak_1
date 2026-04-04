package hu.pte.mik.prog4.zh1.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

public class filter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        Optional<String> name = Optional.ofNullable(req.getParameterMap().get("name"))
                .stream()
                .flatMap(Arrays::stream)
                .findFirst();

        if(name.isEmpty() && req.getMethod().equals("POST")){
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        else{
            super.doFilter(req, res, chain);
        }
    }
}
