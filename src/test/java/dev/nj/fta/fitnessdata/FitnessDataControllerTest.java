package dev.nj.fta.fitnessdata;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static dev.nj.fta.TestUtils.asJsonString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FitnessDataController.class)
public class FitnessDataControllerTest {

    private static final String CREATE_FITNESS_DATA = "/api/tracker";

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    FitnessDataService fitnessDataService;

    @Test
    void createFitnessData_validRequest_returns201() throws Exception {
        FitnessData fitnessData = new FitnessData("user-12", "swimming", 950, 320);

        mockMvc.perform(post(CREATE_FITNESS_DATA)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(fitnessData)))
                .andExpect(status().isCreated());
    }
}
