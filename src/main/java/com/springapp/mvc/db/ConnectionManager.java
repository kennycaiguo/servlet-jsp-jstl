package com.springapp.mvc.db;



import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by zhangjie on 2015/4/23.
 */
public class ConnectionManager {

    /**
     * 空的事务对象
     */
    private static final Transaction EMPTY_TRANSACTION = new Transaction() {
        public void rollback() throws SQLException {
        }
        public void commit() throws SQLException {
        }
    };

    /**
     * 负责提交和回滚的事务对象
     */
    private static final Transaction TRANSACTION = new Transaction() {
        public void rollback() throws SQLException {
            Connection connection = connectionHolder.get();
            if (connection != null) {
                if (connection.getAutoCommit() == false) {
                    connection.rollback();
                }
                connection.close();
                connectionHolder.remove();
            }
        }

        public void commit() throws SQLException {
            Connection connection = connectionHolder.get();
            if (connection != null) {
                if (connection.getAutoCommit() == false) {
                    connection.commit();
                }
                connection.close();
                connectionHolder.remove();
            }
        }
    };

    //线程本地对象管理器
    //对于连接管理，主要使用了Java的线程本地存储(ThreadLocal)，这样可以保证为每一个线程存储单个不同的连接对象
    private static final ThreadLocal<Connection> connectionHolder = new ThreadLocal<Connection>();

    //数据源
    private DataSource dataSource;

    public ConnectionManager(DataSource dataSource){
        this.dataSource = dataSource;
    }

    /**
     * 获取数据库连接
     * @return 数据库连接
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException{
        Connection connection = connectionHolder.get();
        if (connection == null){
            connection = dataSource.getConnection();
            connectionHolder.set(connection);
        }
        return connection;
    }

    /**
     * 启动事务
     * @return 事务管理对象
     * @throws SQLException
     */
    public Transaction beginTransaction() throws SQLException{
        Connection connection = getConnection();
        if (connection.getAutoCommit()){
            connection.setAutoCommit(false);
        }
        return TRANSACTION;
    }

    /**
     * 获取事务
     * 在获取事务对象时，如果数据库连接存在，则返回可以操作事务的Transaction对象，否则返回伪实现对象，以保证返回的结果上可以正确调用rollback和commit方法。
     * @return
     * @throws SQLException
     */
    public Transaction getTransaction() throws SQLException{
        return connectionHolder.get() == null ? EMPTY_TRANSACTION : TRANSACTION;
    }

    /**
     * 关闭连接库
     * @throws SQLException
     */
    public void close() throws SQLException{
        Connection connection = connectionHolder.get();
        if (connection != null){
            connection.close();
            connectionHolder.remove();
        }
    }

    /**
     * 释放资源
     * @param rs 结果集对象
     * @param stm 命令集对象
     * @throws SQLException
     */
    public void release(ResultSet rs, Statement stm) throws SQLException{
        if (rs != null){
            rs.close();
        }
        if (stm != null){
            stm.close();
        }
    }


}
