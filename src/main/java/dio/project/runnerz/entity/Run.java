package dio.project.runnerz.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Run {

    @Id
    private Integer id;
    @NotEmpty
    private String title;
    private LocalDateTime startedOn;
    private LocalDateTime completedOn;
    @Positive
    private Integer kilometers;
    @Enumerated(EnumType.STRING)
    private Location location;
    @Version
    Integer version;

    public Run() {
    }

    public Run(Integer id, String title, LocalDateTime startedOn, LocalDateTime completedOn, Integer kilometers, Location location, Integer version) {
        this.id = id;
        this.title = title;
        this.startedOn = startedOn;
        this.completedOn = completedOn;
        this.kilometers = kilometers;
        this.location = location;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getStartedOn() {
        return startedOn;
    }

    public void setStartedOn(LocalDateTime startedOn) {
        this.startedOn = startedOn;
    }

    public LocalDateTime getCompletedOn() {
        return completedOn;
    }

    public void setCompletedOn(LocalDateTime completedOn) {
        this.completedOn = completedOn;
    }

    public Integer getKilometers() {
        return kilometers;
    }

    public void setKilometers(Integer kilometers) {
        this.kilometers = kilometers;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Run run = (Run) o;
        return Objects.equals(id, run.id) && Objects.equals(title, run.title) && Objects.equals(startedOn, run.startedOn) && Objects.equals(completedOn, run.completedOn) && Objects.equals(kilometers, run.kilometers) && Objects.equals(location, run.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, startedOn, completedOn, kilometers, location);
    }

    @Override
    public String toString() {
        return "Run{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", startedOn=" + startedOn +
                ", completedOn=" + completedOn +
                ", kilometers=" + kilometers +
                ", location=" + location +
                '}';
    }
}
