package dev.nj.fta.fitnessdata;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class FitnessDataServiceTest {

    @Mock
    private FitnessDataRepository fitnessDataRepository;

    @InjectMocks
    private FitnessDataService fitnessDataService;

    @Test
    void createFitnessData_validRequest_savesData() {
        FitnessData data = new FitnessData("user-12", "swimming", 950, 320);

        fitnessDataService.createFitnessData(data);

        verify(fitnessDataRepository).save(data);
    }
}
