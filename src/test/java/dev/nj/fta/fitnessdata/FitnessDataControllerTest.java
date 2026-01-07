package dev.nj.fta.fitnessdata;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static dev.nj.fta.TestUtils.asJsonString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FitnessDataController.class)
public class FitnessDataControllerTest {

    private static final String URL = "/api/tracker";

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    FitnessDataService fitnessDataService;

    @Test
    void createFitnessData_validRequest_returns201() throws Exception {
        FitnessData fitnessData = new FitnessData("user-12", "swimming", 950, 320);

        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(fitnessData)))
                .andExpect(status().isCreated());
    }

    @Test
    void getFitnessData_returns200() throws Exception {
        FitnessDataResponse response1 = new FitnessDataResponse(1L, "user-12", "swimming", 950, 320);
        FitnessDataResponse response2 = new FitnessDataResponse(2L, "user-12", "hiking", 4800, 740);
        List<FitnessDataResponse> responses = List.of(response2, response1);

        when(fitnessDataService.getAllData()).thenReturn(responses);

        mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(2))
                .andExpect(jsonPath("$[1].id").value(1));
    }
}
