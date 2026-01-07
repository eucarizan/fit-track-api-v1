package dev.nj.fta.fitnessdata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FitnessDataServiceImpl implements FitnessDataService {

    private static final Logger logger = LoggerFactory.getLogger(FitnessDataServiceImpl.class);
    private final FitnessDataRepository fitnessDataRepository;

    public FitnessDataServiceImpl(FitnessDataRepository fitnessDataRepository) {
        this.fitnessDataRepository = fitnessDataRepository;
    }

    @Override
    public void createFitnessData(FitnessData fitnessData) {
        fitnessDataRepository.save(fitnessData);
    }
}
