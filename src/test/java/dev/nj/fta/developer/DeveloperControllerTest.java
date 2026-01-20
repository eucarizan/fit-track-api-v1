package dev.nj.fta.developer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static dev.nj.fta.TestUtils.asJsonString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DeveloperController.class)
public class DeveloperControllerTest {

    private static final String URL = "/api/developers/signup";

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    private DeveloperService developerService;

    @Test
    void createDeveloper_validRequest_returns201AndLocationHeader() throws Exception {
        Developer developer = new Developer("johndoe@gmail.com", "qwerty");

        when(developerService.createDeveloper(developer)).thenReturn(9062L);

        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(developer)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "api/developers/9062"));
    }
}
