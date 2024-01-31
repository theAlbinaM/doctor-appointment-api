package ru.almyal.hospital;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
class TimeslotServiceControllerTests {
    @Autowired
    MockMvc mockMvc;

    @Test
    void getAllTimeslots() throws Exception {
        this.mockMvc.perform(get("/timeslots"))
                .andExpect(status().isOk());
    }
}
