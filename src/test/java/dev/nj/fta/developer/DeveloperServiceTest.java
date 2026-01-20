package dev.nj.fta.developer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
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
        DeveloperResponse response = new DeveloperResponse(9062L, "johndoe@gmail.com");

        when(developerRepository.save(developer)).thenReturn(developer);
        when(developerMapper.toResponse(developer)).thenReturn(response);

        Long id = developerService.createDeveloper(developer);

        verify(developerRepository).save(developer);
        assertEquals(9062L, id);
    }
}
