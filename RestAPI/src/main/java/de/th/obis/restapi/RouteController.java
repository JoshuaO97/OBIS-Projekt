package de.th.obis.restapi;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

@RestController
@RequestMapping("/routes")
public class RouteController {

    private ArrayList<Route> routes = new ArrayList<>();

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
