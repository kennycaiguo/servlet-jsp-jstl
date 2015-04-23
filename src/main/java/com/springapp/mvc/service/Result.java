package com.springapp.mvc.service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zhangjie on 2015/4/23.
 */
public class Result {

    private String URL = "";

    public Result(){

    }

    public void forward(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
            request.getRequestDispatcher(this.getURL()).forward(request, response);
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}
