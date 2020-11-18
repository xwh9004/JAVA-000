package com.example.springboot.service.impl;

import com.example.springboot.entity.User;
import com.example.springboot.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

/**
 * <p><b>Description:</b>
 * TODO
 * <p><b>Company:</b>
 *
 * @author created by Jesse Hsu at 16:57 on 2020/11/17
 * @version V0.1
 * @classNmae UserServiceImp
 */
@Slf4j
@Service
public class UserServiceImpl implements IUserService {
//    @Autowired
//    private DataSource dataSource;

    @Autowired
    private Connection connection;

    final String INSERT_SQL = "INSERT INTO user (id,first_name,last_name) VALUES(?,?,?)";

    final String UPDATE_SQL = "UPDATE user  SET user.first_name = ?,  user.last_name = ? WHERE user.id=?";

    final String DELETE_SQL = "DELETE FROM user WHERE id =?";

    final   String QUERY_SQL = "SELECT id,first_name as firstName,last_name as lastName from user where id=?";

//    @PostConstruct
//    void init() throws SQLException {
//        connection = dataSource.getConnection();
//    }

    public void insert(User user) {
        log.info("insert user ={}",user);
        try {
            PreparedStatement prepareStatement = connection.prepareStatement(INSERT_SQL);
            prepareStatement.setInt(1,user.getId());
            prepareStatement.setString(2,user.getFirstName());
            prepareStatement.setString(3,user.getLastName());
            prepareStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User select(int id) {
        log.info("insert user id ={}",id);

        try( PreparedStatement prepareStatement = connection.prepareStatement(QUERY_SQL);) {

            prepareStatement.setInt(1,id);
            ResultSet resultSet = prepareStatement.executeQuery();

            if(resultSet.first()){
                int id1 = resultSet.getInt("id");
                String  firstName = resultSet.getString("firstName");
                String  lastName = resultSet.getString("lastName");
                return new User(id1,firstName,lastName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(int id) {

        try(PreparedStatement prepareStatement = connection.prepareStatement(DELETE_SQL);){
            prepareStatement.setInt(1,id);
            prepareStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(User user) {
        try (PreparedStatement prepareStatement = connection.prepareStatement(UPDATE_SQL);){
            prepareStatement.setInt(3,user.getId());
            prepareStatement.setString(1,user.getFirstName());
            prepareStatement.setString(2,user.getLastName());
            prepareStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void batchInsert(List<User> users) {

        try (PreparedStatement prepareStatement = connection.prepareStatement(INSERT_SQL)){
            connection.setAutoCommit(false); //关闭自动提交
            users.forEach(user->{
                try {
                    prepareStatement.setInt(1,user.getId());
                    prepareStatement.setString(2,user.getFirstName());
                    prepareStatement.setString(3,user.getLastName());
                    prepareStatement.addBatch();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            prepareStatement.executeBatch(); //批量执行
            connection.commit();  //提交事务
            connection.setAutoCommit(true);  //开启自动提交

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
