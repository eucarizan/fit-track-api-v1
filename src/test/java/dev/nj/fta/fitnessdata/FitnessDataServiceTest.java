package dev.nj.fta.fitnessdata;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FitnessDataServiceTest {

    @Mock
    private FitnessDataRepository fitnessDataRepository;

    @Mock
    private FitnessDataMapper fitnessDataMapper;

    @InjectMocks
    private FitnessDataServiceImpl fitnessDataService;

    @Test
    void createFitnessData_validRequest_savesData() {
        FitnessData data = new FitnessData("user-12", "swimming", 950, 320);
        when(fitnessDataRepository.save(data)).thenReturn(data);

        fitnessDataService.createFitnessData(data);

        verify(fitnessDataRepository).save(data);
    }

    @Test
    void getAllFitnessData_returnsListOfFitnessData() {
        FitnessData data1 = new FitnessData("user-12", "swimming", 950, 320);
        FitnessData data2 = new FitnessData("user-12", "hiking", 4800, 740);

        when(fitnessDataRepository.findAllByOrderByCreatedDesc()).thenReturn(List.of(data2, data1));
        when(fitnessDataMapper.toResponse(data2)).thenReturn(
                new FitnessDataResponse(2L, "user-12", "hiking", 4800, 740));
        when(fitnessDataMapper.toResponse(data1)).thenReturn(
                new FitnessDataResponse(1L, "user-12", "swimming", 950, 320));

        List<FitnessDataResponse> responseList = fitnessDataService.getAllData();

        assertEquals(2, responseList.size());
        assertEquals(2L, responseList.get(0).id());
        assertEquals(1L, responseList.get(1).id());
    }
}
