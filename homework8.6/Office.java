package day18;

import java.sql.*;

/**
 * Created by zhang on 2019/8/6.
 */
public class Office {
    public static void main(String[] args) {
        insert();
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

    public static void insert() {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = getconn();
            //开启事务
            connection.setAutoCommit(false);
            //添加学生
            String sql = "INSERT INTO student VALUES('115','张博文1','M','1997-06-03 00:00:00','52')";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("添加成功");
            //添加父母
            String sql2 = "INSERT INTO parent VALUES('117','张**','M','1997-06-03 00:00:00','52')";
            preparedStatement = connection.prepareStatement(sql2);
            preparedStatement.execute();
            //关闭事务
            connection.commit();
            closeall(connection, null, null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
