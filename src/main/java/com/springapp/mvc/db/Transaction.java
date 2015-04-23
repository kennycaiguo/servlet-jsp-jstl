package com.springapp.mvc.db;

import java.sql.SQLException;

/**
 * Created by zhangjie on 2015/4/23.
 */
public interface Transaction {
    void commit() throws SQLException;
    void rollback() throws SQLException;
}
