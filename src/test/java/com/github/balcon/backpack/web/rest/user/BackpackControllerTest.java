package com.github.balcon.backpack.web.rest.user;

import com.github.balcon.backpack.service.BackpackService;
import com.github.balcon.backpack.web.rest.TestData;
import jakarta.persistence.EntityManager;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static com.github.balcon.backpack.web.rest.user.BackpackController.BASE_URL;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class BackpackControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BackpackService service;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        service.save(TestData.backpack1);
        service.save(TestData.backpack2);
        entityManager.flush();
    }

    @Test
    void getById() throws Exception {
        mockMvc.perform(get(BASE_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$", Matchers.hasSize(2)));
    }
}
