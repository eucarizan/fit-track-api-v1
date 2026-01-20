package dev.nj.fta.developer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeveloperServiceTest {

    @Mock
    private DeveloperRepository developerRepository;

    @InjectMocks
    private DeveloperServiceImpl developerService;

    @Test
    void createDeveloper_validRequest_savesDataAndReturnsId() {
        Developer developer = new Developer("johndoe@gmail.com", "qwerty");
        Developer savedDeveloper = new Developer("johndoe@gmail.com",  "qwerty");
        ReflectionTestUtils.setField(savedDeveloper, "id", 9062L);

        when(developerRepository.save(developer)).thenReturn(savedDeveloper);

        Long id = developerService.createDeveloper(developer);

        verify(developerRepository).save(developer);
        assertEquals(9062L, id);
    }
}
