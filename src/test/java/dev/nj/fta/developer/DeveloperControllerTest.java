package dev.nj.fta.developer;

import dev.nj.fta.config.SecurityConfig;
import dev.nj.fta.config.TestSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static dev.nj.fta.TestUtils.asJsonString;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DeveloperController.class)
@Import({TestSecurityConfig.class, DeveloperUserDetailsService.class})
public class DeveloperControllerTest {

    private static final String DEVELOPERS_SIGNUP = "/api/developers/signup";
    private static final String GET_DEVELOPER = "/api/developers/{id}";

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    private DeveloperService developerService;

    @MockitoBean
    private DeveloperRepository developerRepository;

    @Test
    void createDeveloper_validRequest_returns201AndLocationHeader() throws Exception {
        DeveloperRequest request = new DeveloperRequest("johndoe@gmail.com", "qwerty");

        when(developerService.createDeveloper(any(DeveloperRequest.class))).thenReturn(9062L);

        mockMvc.perform(post(DEVELOPERS_SIGNUP)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/developers/9062"));
    }

    @Test
    void createDeveloper_nullEmail_returns400() throws Exception {
        DeveloperRequest request = new DeveloperRequest(null, "qwerty");

        mockMvc.perform(post(DEVELOPERS_SIGNUP)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.messages").isArray())
                .andExpect(jsonPath("$.messages").value(hasItem("Email is required")));
    }

    @Test
    void createDeveloper_emptyEmail_returns400() throws Exception {
        DeveloperRequest request = new DeveloperRequest("", "qwerty");

        mockMvc.perform(post(DEVELOPERS_SIGNUP)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.messages").isArray())
                .andExpect(jsonPath("$.messages").value(hasItem("Email is required")));
    }

    @Test
    void createDeveloper_invalidEmail_returns400() throws Exception {
        DeveloperRequest request = new DeveloperRequest("not-an-email", "qwerty");

        mockMvc.perform(post(DEVELOPERS_SIGNUP)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.messages").isArray())
                .andExpect(jsonPath("$.messages").value(hasItem("Incorrect email format")));
    }

    @Test
    void createDeveloper_nullPassword_returns400() throws Exception {
        DeveloperRequest request = new DeveloperRequest("johndoe@gmail.com", null);

        mockMvc.perform(post(DEVELOPERS_SIGNUP)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.messages").isArray())
                .andExpect(jsonPath("$.messages").value(hasItem("Password is required")));
    }

    @Test
    void createDeveloper_emptyPassword_returns400() throws Exception {
        DeveloperRequest request = new DeveloperRequest("johndoe@gmail.com", "");

        mockMvc.perform(post(DEVELOPERS_SIGNUP)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.messages").isArray())
                .andExpect(jsonPath("$.messages").value(hasItem("Password is required")));
    }

    @Test
    void getDeveloper_authenticatedOwner_returns200AndJson() throws Exception {
        Developer developer = new Developer("johndoe@gmail.com", new BCryptPasswordEncoder().encode("qwerty"));
        ReflectionTestUtils.setField(developer, "id", 9062L);
        DeveloperResponse response = new DeveloperResponse(9062L, "johndoe@gmail.com");

        when(developerRepository.findByEmail("johndoe@gmail.com")).thenReturn(Optional.of(developer));
        when(developerService.getDeveloperById(9062L)).thenReturn(response);

        mockMvc.perform(get(GET_DEVELOPER, 9062L)
                        .with(httpBasic("johndoe@gmail.com", "qwerty")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(9062L))
                .andExpect(jsonPath("$.email").value("johndoe@gmail.com"));
    }

    @Test
    void getDeveloper_unauthenticated_returns401()  throws Exception {
        mockMvc.perform(get(GET_DEVELOPER, 9999L))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getDeveloper_authenticatedNonOwner_returns403() throws Exception {
        Developer developer = new Developer("johndoe@gmail.com", new BCryptPasswordEncoder().encode("qwerty"));
        ReflectionTestUtils.setField(developer, "id", 9062L);

        when(developerRepository.findByEmail("johndoe@gmail.com")).thenReturn(Optional.of(developer));

        mockMvc.perform(get(GET_DEVELOPER, 9999L)
                .with(httpBasic("johndoe@gmail.com", "qwerty")))
                .andExpect(status().isForbidden());
    }
}
