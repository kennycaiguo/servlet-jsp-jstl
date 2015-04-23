package com.springapp.mvc.dao;

import com.springapp.mvc.db.ConnectionFactory;
import com.springapp.mvc.db.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;

/**
 * Created by zhangjie on 2015/4/23.
 */
public class RecordDAOImpl {
    private static  final ConnectionManager manager = ConnectionFactory.getConnectionManager();

    public int getCount() throws SQLException{
        final String sql = "select count(*) from demo";

        ResultSet rs = null;
        PreparedStatement stm = null;
        try {
            stm = manager.getConnection().prepareStatement(sql);
            rs = stm.executeQuery();
            rs.next();
            return rs.getInt(1);
        } finally {
            manager.release(rs, stm);
        }
    }
}
