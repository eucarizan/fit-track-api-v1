package dev.nj.fta.fitnessdata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static dev.nj.fta.TestUtils.asJsonString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class FitnessDataControllerIT {

    private static final String URL = "/api/tracker";

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:12-alpine");

    @Autowired
    MockMvc mockMvc;

    @Autowired
    FitnessDataRepository fitnessDataRepository;

    @BeforeEach
    void setup() {
        fitnessDataRepository.deleteAll();
    }

    @Test
    void it_createFitnessData_validRequest_returns201() throws Exception {
        FitnessData data = new FitnessData("user-12", "swimming", 950, 320);

        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(data)))
                .andExpect(status().isCreated());

        var allData = fitnessDataRepository.findAll();
        assertEquals(1, allData.size());
        assertEquals("user-12", allData.get(0).getUsername());
    }

    @Test
    void it_getFitnessData_returns200() throws Exception {
        FitnessData data1 = new FitnessData("user-12", "swimming", 950, 320);
        fitnessDataRepository.save(data1);
        Thread.sleep(10);
        FitnessData data2 = new FitnessData("user-12", "hiking", 4800, 740);
        fitnessDataRepository.save(data2);

        mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].activity").value("hiking"))
                .andExpect(jsonPath("$[1].activity").value("swimming"));
    }
}
