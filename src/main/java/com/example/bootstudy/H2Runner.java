package com.example.bootstudy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

@Component
public class H2Runner implements ApplicationRunner {

    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        /**
         * 명시적으로 finally 블럭을 생성하여 사용하지 않을 수 있다.
         */
        // Connection connection = dataSource.getConnection();
        try(Connection connection = dataSource.getConnection()) {
            System.out.println(connection.getMetaData().getURL());
            System.out.println(connection.getMetaData().getUserName());

            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE TEST_USER (ID INTEGER NOT NULL, name VARCHAR(255), PRIMARY KEY (id))";
            statement.executeUpdate(sql);
        }

        /**
         * JdbcTemplate
         * Connection보다 좀 더 안전하게 사용가능하다.
         * 예외를 던질때 좀 더 가독성이 좋다.
         */
        jdbcTemplate.execute("INSERT INTO TEST_USER VALUES (1, 'yulseon')");


    }
}
