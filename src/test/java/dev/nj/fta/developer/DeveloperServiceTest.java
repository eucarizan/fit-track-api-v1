package dev.nj.fta.developer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeveloperServiceTest {

    @Mock
    private DeveloperRepository developerRepository;

    @Mock
    private DeveloperMapper developerMapper;

    @InjectMocks
    private DeveloperServiceImpl developerService;

    @Test
    void createDeveloper_validRequest_savesDataAndReturnsId() {
        Developer developer = new Developer("johndoe@gmail.com", "qwerty");
        DeveloperRequest request = new DeveloperRequest("johndoe@gmail.com", "qwerty");
        Developer savedDeveloper = new Developer("johndoe@gmail.com",  "qwerty");
        ReflectionTestUtils.setField(savedDeveloper, "id", 9062L);

        when(developerRepository.save(developer)).thenReturn(savedDeveloper);
        when(developerMapper.toEntity(request)).thenReturn(developer);

        Long id = developerService.createDeveloper(request);

        verify(developerRepository).save(developer);
        assertEquals(9062L, id);
    }

    @Test
    void getDeveloper_byId_returnsDeveloper() {
        Developer developer = new Developer("johndoe@gmail.com", "qwerty");
        DeveloperResponse developerResponse = new DeveloperResponse(9062L, "johndoe@gmail.com");

        when(developerRepository.findById(9062L)).thenReturn(Optional.of(developer));
        when(developerMapper.toResponse(developer)).thenReturn(developerResponse);

        DeveloperResponse response = developerService.getDeveloperById(9062L);

        assertEquals(9062L, response.id());
        assertEquals("johndoe@gmail.com", response.email());
    }
}
