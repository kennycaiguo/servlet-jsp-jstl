package com.springapp.mvc.servlet;

import com.springapp.mvc.service.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zhangjie on 2015/4/22.
 */
@WebServlet("/servletDemo1.html")
public class servletDemo1 extends AbstractServlet {

    @Override
    public Result execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Result result = new Result();
        request.setAttribute("name", "servlet-jsp-jstl");
        result.setURL("/WEB-INF/pages/hello.jsp");
        return result;
    }
}
