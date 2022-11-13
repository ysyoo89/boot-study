package com.example.bootstudy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

/**
 * MySQL을 pom.xml에 dependency 만을 추가한 뒤에 아래에 명령어를 그대로 사용한다면
 * H2를 사용했을 때와 똑같이 실행하게 된다.
 * 즉, 어떤 DB를 연결하여 사용하느냐에 따라 다른 코드를 짤 필요가 없이
 * dependency를 연결하여 어떻게 사용할 것인지를 알고 있으면 좋을 것 같다.
 */
@Component
public class MySQLRunner implements ApplicationRunner {

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
            System.out.println(dataSource.getClass() );
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
