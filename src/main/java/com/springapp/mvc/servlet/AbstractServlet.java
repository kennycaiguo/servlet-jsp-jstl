package com.springapp.mvc.servlet;

import com.springapp.mvc.db.ConnectionFactory;
import com.springapp.mvc.db.ConnectionManager;
import com.springapp.mvc.service.Result;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by zhangjie on 2015/4/22.
 */
public abstract class AbstractServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * 执行具体业务方法
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public abstract Result execute(HttpServletRequest request, HttpServletResponse response) throws Exception;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ConnectionManager manager = ConnectionFactory.getConnectionManager();
        try {
            Result result = null;
            try {
                //实行具体业务方法
                result = execute(req, resp);
                //提交事务
                manager.getConnection().commit();
            } catch (SQLException e){
                //回滚事务
                manager.getConnection().rollback();
                throw  e;
            }
            result.forward(req, resp);
        } catch (Exception e){
            e.printStackTrace();
            throw new ServletException(e);
        }
    }
}
