package dio.project.runnerz.repository;

import dio.project.runnerz.entity.Location;
import dio.project.runnerz.entity.Run;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class InMemoryRunRepository {

    private static final Logger log = LoggerFactory.getLogger(InMemoryRunRepository.class);
    private final List<Run> runs = new ArrayList<>();


    public List<Run> findAll() {
        return runs;
    }

    public Optional<Run> findById (Integer id) throws Exception {
        return Optional.ofNullable(runs.stream()
                .filter(run -> run.getId() == id)
                .findFirst()
                .orElseThrow(Exception::new));
    }

    public void create(Run run) {
        Run newRun = new Run(run.getId(),
                run.getTitle(),
                run.getStartedOn(),
                run.getCompletedOn(),
                run.getKilometers(),
                run.getLocation(),
                null);

        runs.add(newRun);
    }

    public void update(Run run, Integer id) throws Exception {
        Optional<Run> existingRun = findById(id);
        if (existingRun.isPresent()) {
            var r = existingRun.get();
            log.info("Updating run: " + existingRun.get());
            runs.set(runs.indexOf(r), run);
        }
    }

    public void delete(Integer id) {
        log.info("Deleting run: " + id);
        runs.removeIf(run -> run.getId().equals(id));
    }

    public int count() {
        return runs.size();
    }

    public void saveAll(List<Run> runs) {
        runs.stream().forEach(run -> create(run));
    }

    public List<Run> findByLocation(String location) {
        return runs.stream()
                .filter(run -> Objects.equals(run.getLocation(), location))
                .toList();
    }

    @PostConstruct
    private void init() {
        runs.add(new Run(1,
                "Monday run",
                LocalDateTime.now(),
                LocalDateTime.now().plus(30, ChronoUnit.MINUTES),
                3,
                Location.INDOOR,
                null));

        runs.add(new Run(2,
                "Friday run",
                LocalDateTime.now(),
                LocalDateTime.now().plus(50, ChronoUnit.MINUTES),
                4,
                Location.INDOOR, null));
    }
}
