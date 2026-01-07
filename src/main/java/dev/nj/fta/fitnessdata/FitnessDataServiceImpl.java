package dev.nj.fta.fitnessdata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FitnessDataServiceImpl implements FitnessDataService {

    private static final Logger logger = LoggerFactory.getLogger(FitnessDataServiceImpl.class);
    private final FitnessDataRepository fitnessDataRepository;
    private final FitnessDataMapper fitnessDataMapper;

    public FitnessDataServiceImpl(FitnessDataRepository fitnessDataRepository, FitnessDataMapper fitnessDataMapper) {
        this.fitnessDataRepository = fitnessDataRepository;
        this.fitnessDataMapper = fitnessDataMapper;
    }

    @Override
    public void createFitnessData(FitnessData fitnessData) {
        logger.debug("Creating fitnessData: username={}, activity={}, duration={}, calories={}",
                fitnessData.getUsername(), fitnessData.getActivity(), fitnessData.getDuration(), fitnessData.getCalories());
        FitnessData savedData = fitnessDataRepository.save(fitnessData);
        logger.debug("FitnessData persisted: id={}", savedData.getId());
    }

    @Override
    public List<FitnessDataResponse> getAllData() {
        logger.debug("Getting all fitnessData");
        List<FitnessDataResponse> responseList = fitnessDataRepository.findAllByOrderByCreatedDesc()
                .stream()
                .map(fitnessDataMapper::toResponse)
                .toList();
        logger.debug("Successfully retrieved {} fitnessData", responseList.size());
        return responseList;
    }
}
