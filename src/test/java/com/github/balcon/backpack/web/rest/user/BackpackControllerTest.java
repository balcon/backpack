package com.github.balcon.backpack.web.rest.user;

import com.github.balcon.backpack.repository.BackpackRepository;
import com.github.balcon.backpack.web.rest.BaseIntegrationTest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class BackpackControllerTest extends BaseIntegrationTest {

    private final MockMvc mockMvc;

    private final BackpackRepository repository;

}
