package com.guo.test.day19.transaction;

import com.guo.test.day17.JdbcUtil;
import com.guo.test.day17.Student;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 3.根据昨天练习的表，完成根据某个字段进行排序（降序）的操作
 *
 * 方法名，如：findOrderByUserName()
 */
public class HomeWork3 {
    public List<Student> findOrderByUserName(){
        Connection connection = JdbcUtil.getInstance().getConnection();
        ResultSet rs = null;
        String sql = "select * from student order by sname desc";
        PreparedStatement preparedStatement = null;
        List<Student> list = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Student student = new Student(rs.getString(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getString(5));
                list.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtil.getInstance().closeResource(rs);
            JdbcUtil.getInstance().closeResource(preparedStatement);
            JdbcUtil.getInstance().closeResource(connection);
        }
        return list;
    }
    @Test
    public void test(){
        List<Student> list = findOrderByUserName();
        list.forEach(n -> System.out.println(n));
    }
}

