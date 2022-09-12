package de.th.obis.restapi;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

@RestController
@RequestMapping("/routes")
public class RouteController {

    ArrayList<Route> routes = new ArrayList<>();

    @GetMapping
    public ArrayList<Route> getRoutes() {
        return routes;
    }

}
