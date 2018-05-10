package main.utils.APIRequests;

import io.restassured.response.Response;
import main.utils.People;
import main.utils.RESTUtil;

public class APIRequestsSearchPeople {

    public People searchPeople(int peopleId) {
        People people = new People();
        String basePath = String.format("people/%d/", peopleId);

        Response response = new RESTUtil().getRequest(basePath);
        people.setName(response.jsonPath().getString("name"));
        people.setHomeWorldId(response.jsonPath().getString("homeworld"));
        return people;
    }
}
