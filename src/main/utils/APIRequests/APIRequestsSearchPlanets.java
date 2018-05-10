package main.utils.APIRequests;

import io.restassured.response.Response;
import main.utils.People;
import main.utils.Planet;
import main.utils.RESTUtil;

public class APIRequestsSearchPlanets {

    public Planet searchPlanets(People people) {
        Planet planet = new Planet();
        String basePath = people.getHomeWorldId().substring(21);
        Response response = new RESTUtil().getRequest(basePath);
        planet.setName(response.jsonPath().getString("name"));
        planet.setPopulation(response.jsonPath().getInt("population"));
        planet.setFilms(response.jsonPath().getList("films"));
        return planet;
    }
}
