package day18;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhang on 2019/8/6.
 */
public class Page {
    public static void main(String[] args) {
        List<Student> list = findByUserNameLikeOrderLimit("王" , 2 ,1);
        list.forEach(student -> System.out.println(student.toString()));
    }
    public static List findByUserNameLikeOrderLimit(String userName,int currPage, int pageSize)  {
        List list = new ArrayList();
        Connection connection = null;
        PreparedStatement preparedStatement;
        ResultSet resultSet =null;
        try {
            connection =getconn();
            int start = (currPage-1) * pageSize;

            String sql = "select * from student WHERE sname LIKE  ? order by sname  limit ? ,? ";
            preparedStatement =connection.prepareStatement(sql);
            preparedStatement.setString(1 ,"%"+userName+"%");
            preparedStatement.setInt(2 ,start);
            preparedStatement.setInt(3 ,pageSize);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Student student = new Student();
                student.setSname(resultSet.getString("sno"));
                student.setSname(resultSet.getString("sname"));
                student.setSbirthday(resultSet.getString("sbirthday"));
                student.setSsex(resultSet.getString("ssex"));
                student.setStuclass(resultSet.getString("class"));
                list.add(student);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeall(connection,null,resultSet);
        return list;
    }
    private static Connection getconn() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/homework?useUnicode=true&characterEncoding=utf8", "root", "123456");
        System.out.println(connection);
        return connection;
    }

    private static void closeall(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        if (resultSet != null) {
            try {
                if (connection != null) {
                    connection.close();
                } else if (preparedStatement != null) {
                    preparedStatement.close();
                } else if (resultSet != null) {
                    resultSet.close();
                }
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
