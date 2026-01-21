package dev.nj.fta.developer;

import dev.nj.fta.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static dev.nj.fta.TestUtils.asJsonString;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DeveloperController.class)
@Import(SecurityConfig.class)
public class DeveloperControllerTest {

    private static final String URL = "/api/developers/signup";

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    private DeveloperService developerService;

    @Test
    void createDeveloper_validRequest_returns201AndLocationHeader() throws Exception {
        Developer developer = new Developer("johndoe@gmail.com", "qwerty");

        when(developerService.createDeveloper(any(DeveloperRequest.class))).thenReturn(9062L);

        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(developer)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/developers/9062"));
    }

    @Test
    void createDeveloper_nullEmail_returns400() throws Exception {
        Developer developer = new Developer(null, "qwerty");

        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(developer)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.messages").isArray())
                .andExpect(jsonPath("$.messages").value(hasItem("Email is required")));
    }
}
