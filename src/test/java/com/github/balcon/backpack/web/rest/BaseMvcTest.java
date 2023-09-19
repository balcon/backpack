package com.github.balcon.backpack.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@ActiveProfiles("test")
@Sql("classpath:data.sql")
public abstract class BaseMvcTest {

    private static final String VERSION = "15.4";

    private static final PostgreSQLContainer<?> container =
            new PostgreSQLContainer<>("postgres:" + VERSION);

    @Autowired
    private ObjectMapper jsonMapper;

    @Autowired
    protected MockMvc mockMvc;

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

    protected String toJson(Object object) throws JsonProcessingException {
        return jsonMapper.writeValueAsString(object);
    }
}
