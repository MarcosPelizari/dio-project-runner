package dio.project.runnerz.entity;

public record Address(
        String street,
        String suite,
        String city,
        String zipcod,
        Geo geo
) {
}
