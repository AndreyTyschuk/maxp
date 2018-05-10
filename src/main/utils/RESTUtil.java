package main.utils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;

public class RESTUtil {

    public RESTUtil() {
        RestAssured.baseURI = PropertyLoader.getInstance().getAPIUrl();
    }

    public Response getRequest(String basePath) {

        RestAssured.basePath = basePath;
        System.out.println(baseURI);
        System.out.println(RestAssured.basePath);
        RequestSpecification httpRequest = RestAssured.given()
                .when()
                .contentType(ContentType.JSON);
        Response response = httpRequest.request(Method.GET);
        if (!(response.getStatusCode() == 200))
            return null;
        return response;
    }
}