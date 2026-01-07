package dev.nj.fta.fitnessdata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tracker")
public class FitnessDataController {

    private static final Logger logger = LoggerFactory.getLogger(FitnessDataController.class);
    private final FitnessDataService fitnessDataService;

    public FitnessDataController(FitnessDataService fitnessDataService) {
        this.fitnessDataService = fitnessDataService;
    }

    @PostMapping
    public ResponseEntity<Void> createFitnessData(@RequestBody FitnessData fitnessData) {
        logger.info("POST /api/tracker: username={}, activity={}, duration={}, calories={}",
                fitnessData.getId(), fitnessData.getActivity(), fitnessData.getDuration(), fitnessData.getCalories());
        fitnessDataService.createFitnessData(fitnessData);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<FitnessDataResponse>> getAllFitnessData() {
        logger.info("GET /api/tracker");
        List<FitnessDataResponse> allData = fitnessDataService.getAllData();
        return ResponseEntity.status(HttpStatus.OK).body(allData);
    }
}
