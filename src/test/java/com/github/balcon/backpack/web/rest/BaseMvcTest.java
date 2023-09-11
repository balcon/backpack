package com.github.balcon.backpack.web.rest;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Sql("classpath:data.sql")
public abstract class BaseMvcTest {

    private static final String VERSION = "15.4";

    private static final PostgreSQLContainer<?> container =
            new PostgreSQLContainer<>("postgres:" + VERSION);

    // startup Postgres docker container
    @BeforeAll
    static void beforeAll() {
        container.start();
    }

    // configure random Postgres container port
    @DynamicPropertySource
    static void configureDatasource(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
    }
}
