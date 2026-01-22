package dev.nj.fta.developer;

public interface DeveloperService {
    Long createDeveloper(DeveloperRequest request);

    DeveloperResponse getDeveloperById(Long id);
}
