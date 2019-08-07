package com.guo.test.day19.transaction;

import com.guo.test.day17.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 场景:张三给李四转200元钱
 */
public class HomeWork1 {
    public static void main(String[] args) {
        Connection connection = JdbcUtil.getInstance().getConnection();
        PreparedStatement preparedStatement1 = null;
        try {
            connection.setAutoCommit(false);
            String sql = "update account set balance = balance - ? where name like ? and balance - ? >=0";// 张三
            preparedStatement1 = connection.prepareStatement(sql);
            preparedStatement1.setObject(1, 200);
            preparedStatement1.setObject(2, "张%");
            preparedStatement1.setObject(3, 200);
            int count1 = preparedStatement1.executeUpdate();
            sql = "select balance from account where name = ?";
            sql = "update account set balance = balance + ? where name = ?";
            preparedStatement1 = connection.prepareStatement(sql);
            preparedStatement1.setObject(1, 200);
            preparedStatement1.setObject(2, "lisi");
            int count2 = preparedStatement1.executeUpdate();
            System.out.println(count1 + " " + count2);
            if (count1 == 1 && count2 == 1) {
                connection.commit();
            } else {
                connection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtil.getInstance().closeResource(preparedStatement1);
            JdbcUtil.getInstance().closeResource(connection);
        }

    }
}
