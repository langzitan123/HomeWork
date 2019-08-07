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
 * 根据昨天练习的表，完成根据某个字段模糊查找并排序（升序），
 * 然后分页获取第二页数据的操作（每页显示2条）
 *
 *userName:用户名
 * currPage:当前页
 * pageSize:每页显示的数量
 */
public class HomeWork4 {
    public List<Student> findByUserNameLikeOrderLimit(String userName,int currPage,int pageSize)  {
        Connection connection = JdbcUtil.getInstance().getConnection();
        ResultSet rs = null;
        String sql = "select * from student where sname like ? order by sname limit ?,?";
        PreparedStatement preparedStatement = null;
        List<Student> list = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1,"%" + userName + "%");
            preparedStatement.setObject(2,currPage - 1);
            preparedStatement.setObject(3,pageSize);
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
        List<Student> list = findByUserNameLikeOrderLimit("1",1,2);//姓为：1 当前页为：1 每页显示：2条
        list.forEach(n -> System.out.println(n));
    }
}

