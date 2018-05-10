package tests.java;

import main.pages.LoginPage;
import main.utils.APIRequests.APIRequestsSearchFilm;
import main.utils.APIRequests.APIRequestsSearchPeople;
import main.utils.APIRequests.APIRequestsSearchPlanets;
import main.utils.Film;
import main.utils.People;
import main.utils.Planet;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;

import java.lang.reflect.Method;

public class APITest extends BaseTest {

    @DataProvider()
    public Object[][] dataForApiTest(ITestContext context) {
        return new Object[][]{
            {1, "Luke Skywalker", "Tatooine", 200000, "Attack of the Clones"},
        };
    }
    @Test(dataProvider = "dataForApiTest")
    public void apiTest(int peopleId, String peopleName, String planetName, int planetPopulation, String title){
        People people = new APIRequestsSearchPeople().searchPeople(peopleId);
        hardAssert.assertEquals(peopleName, people.getName());
        Planet planet = new APIRequestsSearchPlanets().searchPlanets(people);
        hardAssert.assertEquals(planet.getName(), planetName);
        hardAssert.assertEquals(planet.getPopulation(), planetPopulation);
        Film film = new APIRequestsSearchFilm().searchFilms(planet);
        hardAssert.assertEquals(film.getTitle(), title);
        hardAssert.assertTrue(film.getCharacters().contains("https://swapi.co/api/films/"+peopleId+"/"));
        hardAssert.assertTrue(film.getPlanets().contains(people.getHomeWorldId()));
    }
}
