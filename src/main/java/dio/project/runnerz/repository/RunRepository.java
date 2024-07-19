package dio.project.runnerz.repository;

import dio.project.runnerz.entity.Location;
import dio.project.runnerz.entity.Run;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RunRepository extends ListCrudRepository<Run, Integer> {

    List<Run> findAllByLocation(Location location);
}
