package main.utils.APIRequests;
import io.restassured.response.Response;
import main.utils.Film;
import main.utils.People;
import main.utils.Planet;
import main.utils.RESTUtil;


public class APIRequestsSearchFilm {
    public Film searchFilms(Planet planet) {
        Film film = new Film();
        String basePath = planet.getFilms().get(0).substring(21);
        Response response = new RESTUtil().getRequest(basePath);
        film.setTitle(response.jsonPath().getString("title"));
        film.setCharacters(response.jsonPath().getList("characters"));
        film.setPlanets(response.jsonPath().getList("planets"));
        return film;
    }
}
