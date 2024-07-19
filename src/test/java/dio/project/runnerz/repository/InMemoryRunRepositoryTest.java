package dio.project.runnerz.repository;

import dio.project.runnerz.entity.Location;
import dio.project.runnerz.entity.Run;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryRunRepositoryTest {

    InMemoryRunRepository repository;

    @BeforeEach
    void setUp() {
        repository= new InMemoryRunRepository();
        repository.create(new Run(1,
                "Monday run",
                LocalDateTime.now(),
                LocalDateTime.now().plus(30, ChronoUnit.MINUTES),
                3,
                Location.INDOOR,
                null));

        repository.create(new Run(2,
                "Friday Jog",
                LocalDateTime.now(),
                LocalDateTime.now().plus(40, ChronoUnit.MINUTES),
                4,
                Location.INDOOR,
                null));
    }

    @Test
    void shouldFindAllRuns() {
        List<Run> runs = repository.findAll();
        assertEquals(2, runs.size(), "Should return two runs");
    }

    @Test
    void shouldFindRunWithValidId() throws Exception {
        var run = repository.findById(1).get();
        assertEquals("Monday run", run.getTitle());
        assertEquals(3, run.getKilometers());
    }

    @Test
    void shouldCreateNewRun() {
        repository.create(new Run(3,
                "Saturday Run",
                LocalDateTime.now(),
                LocalDateTime.now().plus(20,ChronoUnit.MINUTES),
                2,
                Location.INDOOR,
                0));

        List<Run> runs = repository.findAll();
        assertEquals(3, runs.size());
    }

    @Test
    void shouldUpdateRun() throws Exception {
        repository.update(new Run(1,
                "Morning",
                LocalDateTime.now(),
                LocalDateTime.now().plus(20,ChronoUnit.MINUTES),
                2,
                Location.INDOOR,
                0), 1);

        var run = repository.findById(1).get();
        assertEquals("Morning", run.getTitle());
        assertEquals(2, run.getKilometers());
        assertEquals(Location.INDOOR, run.getLocation());
    }

    @Test
    void shouldDeleteRun() {
        repository.delete(1);
        List<Run> runs = repository.findAll();
        assertEquals(1, runs.size());
    }
}