package dio.project.runnerz.repository;

import dio.project.runnerz.entity.Run;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcClientRunRepository {

/*    private List<Run> runs = new ArrayList<>();

    public List<Run> findAll() {
        return runs;
    }

    public Optional<Run> findById(Integer id) {
        return runs.stream()
                .filter(run -> run.getId() == id)
                .findFirst();
    }

    public void create(Run run) {
        runs.add(run);
    }

    public void update(Run run, Integer id) {
        Optional<Run> existingRun = findById(id);
        if (existingRun.isPresent()) {
            runs.set(runs.indexOf(existingRun.get()), run);
        }
    }

    public void delete(Integer id) {
        runs.removeIf(run -> run.getId().equals(id));
    }

    @PostConstruct
    private void init() {
        runs.add(new Run(1,
                "Monday Morning Run",
                LocalDateTime.now(),
                LocalDateTime.now().plus(30, ChronoUnit.MINUTES),
                3,
                Location.INDOOR));

        runs.add(new Run(2,
                "Wednesday Morning Run",
                LocalDateTime.now(),
                LocalDateTime.now().plus(60, ChronoUnit.MINUTES),
                4,
                Location.INDOOR));
    }*/

    private static final Logger log = LoggerFactory.getLogger(JdbcClientRunRepository.class);
    private final JdbcClient jdbcClient;

    public JdbcClientRunRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Run> findAll() {
        return jdbcClient.sql("select * from run")
                .query(Run.class)
                .list();
    }

    public Optional<Run> findById (Integer id) {
        return jdbcClient.sql("select * from Run where id = :id")
                .param("id", id)
                .query(Run.class)
                .optional();
    }

    public void create(Run run) {
        var updater = jdbcClient.sql("insert into Run (id, title, started_on, completed_on, kilometers, location) values(:id, :title, :startedOn, :completedOn, :kilometers, :location)")
                .param("id", run.getId())
                .param("title", run.getTitle())
                .param("startedOn", run.getStartedOn())
                .param("completedOn", run.getCompletedOn())
                .param("kilometers", run.getKilometers())
                .param("location", run.getLocation().toString())
                .update();

        Assert.state(updater == 1, "Failed to create run" + run.getTitle());
    }

    public void update(Run run, Integer id) {
        var updater = jdbcClient.sql("update Run set title = :title, started_on = :startedOn, completed_on = :completedOn, kilometers = :kilometers, location = :location where id = :id")
                .param("title", run.getTitle())
                .param("startedOn", run.getStartedOn())
                .param("completedOn", run.getCompletedOn())
                .param("kilometers", run.getKilometers())
                .param("location", run.getLocation().toString())
                .param("id", id)
                .update();

        Assert.state(updater == 1, "Failed to create run" + run.getTitle());
    }

    public void delete(Integer id) {
        var updater = jdbcClient.sql("delete from run where id = :id")
                .param("id", id)
                .update();

        Assert.state(updater == 1, "Failed to create run" + id);
    }

    public int count() {
        return jdbcClient.sql("select * from Run")
                .query()
                .listOfRows()
                .size();
    }

    public void saveAll(List<Run> runs) {
        runs.forEach(this::create);
    }

    public List<Run> findByLocation(String location) {
        return jdbcClient.sql("select * from Run where location = :location")
                .param("location", location)
                .query(Run.class)
                .list();
    }
}
