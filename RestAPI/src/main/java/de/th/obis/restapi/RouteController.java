package de.th.obis.restapi;

import de.th.obis.restapi.point.Point;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Arrays;

@RestController
@RequestMapping("/routes")
public class RouteController {

    private final Route route1 = new Route(1, "Burg Hunolstein", 1764, 79, 82, new ArrayList<>(Arrays.asList(new Point(49.79902337229739, 7.045904398096021, 6946), new Point(49.80374204785129, 7.043684365510524, 6486), new Point(49.81101479063034, 7.047369853566575, 1743))));
    private final Route route2 = new Route(2, "Eremitenpfad", 1121, 3, 79, new ArrayList<>(Arrays.asList(new Point(49.88725337891083, 7.870928926186527, 2584), new Point(49.88544048334817, 7.879936540611833, 4451))));
    private final Route route3 = new Route(3, "Sonnenberghütte", 1981, 55, 18, new ArrayList<>(Arrays.asList(new Point(49.897002220658976, 7.842485473574225, 1265), new Point(49.895138684854146, 7.863365895921416, 1362))));

    private ArrayList<Route> routes = new ArrayList<>(Arrays.asList(route1, route2, route3));

    @GetMapping
    public ArrayList<Route> getAllRoutes() {
        return routes;
    }

    @GetMapping("/{nr}")
    public Route getRoute(@PathVariable int nr) {
        if (nr - 1 < 0 || nr - 1 > routes.size() - 1) {
            throw new ArrayIndexOutOfBoundsException("Route ID does not exist");
        }
        return routes.get(nr - 1);
    }
}
