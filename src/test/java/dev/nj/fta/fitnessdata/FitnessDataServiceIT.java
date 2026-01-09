package dev.nj.fta.fitnessdata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Testcontainers
public class FitnessDataServiceIT {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @Autowired
    private FitnessDataService fitnessDataService;

    @Autowired
    private FitnessDataRepository fitnessDataRepository;

    @BeforeEach
    void setUp() {
        fitnessDataRepository.deleteAll();
    }

    @Test
    void it_createFitnessData_validRequest_savesData() {
        FitnessData data = new FitnessData("user-12", "swimming", 950, 320);

        fitnessDataService.createFitnessData(data);

        List<FitnessData> allData = fitnessDataRepository.findAll();
        assertEquals(1, allData.size());
        assertEquals(data.getUsername(), allData.get(0).getUsername());
        assertEquals(data.getActivity(), allData.get(0).getActivity());
        assertEquals(data.getDuration(), allData.get(0).getDuration());
        assertEquals(data.getCalories(), allData.get(0).getCalories());
    }

    @Test
    void it_getAllFitnessData_returnsListOfFitnessData() {
        FitnessData data1 = new FitnessData("user-12", "swimming", 950, 320);
        fitnessDataRepository.save(data1);
        FitnessData data2 = new FitnessData("user-12", "hiking", 4800, 740);
        fitnessDataRepository.save(data2);

        List<FitnessDataResponse> allData = fitnessDataService.getAllData();

        assertEquals(2, allData.size());
        assertEquals(2L, allData.get(0).id());
        assertEquals(1L, allData.get(1).id());
    }
}
