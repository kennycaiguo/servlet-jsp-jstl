package com.springapp.mvc.db;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;

/**
 * 数据连接工厂类
 * Created by zhangjie on 2015/4/23.
 */
public class ConnectionFactory {

    //单列工厂类对象
    private static ConnectionManager connectionManager = null;

    /**
     * 私有构造函数
     */
    private ConnectionFactory(){}

    /**
     * 创建连接管理对象
     * @return
     */
    public static ConnectionManager getConnectionManager(){
        if (connectionManager == null){
            try {
                MysqlDataSource dataSource = new MysqlDataSource();
                dataSource.setURL("jdbc:mysql://127.0.0.1:3306/?useUnicode=true&characterEncoding=UTF-8");
                connectionManager = new ConnectionManager(dataSource);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return connectionManager;
    }

    /**
     * 为测试初始化连接环境
     */
    public static void initializeForTesting(String driverClassName, String url, String username, String password) {
        if (connectionManager == null) {
//            BasicDataSource dataSource = new BasicDataSource();
//            dataSource.setDriverClassName(driverClassName);
//            dataSource.setUrl(url);
//            dataSource.setUsername(username);
//            dataSource.setPassword(password);
//            connectionManager = new ConnectionManager(dataSource);
        }
    }


}
