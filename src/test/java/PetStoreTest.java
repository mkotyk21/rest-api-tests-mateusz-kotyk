import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;
import io.restassured.response.Response;

import static io.restassured.RestAssured.get;

import org.testng.Assert;

public class PetStoreTest {
    String url = "https://petstore.swagger.io/v2/user/";

    @Test
    public void postUserByUsername() {
        String userName = "Monia";
        RequestSpecification request = RestAssured.given();
        request.given().log().all();
        request.contentType(ContentType.JSON);
        request.body("{\"id\":0,\"username\":\""+userName+"\",\"firstName\":\"string\",\"lastName\":\"string\",\"email\":\"string\",\"password\":\"string\",\"phone\":\"string\",\"userStatus\":0}");
        Response response = request.post(url);
        System.out.println(response.getBody().asString());
        String actualUsername = response.jsonPath().get("username");
        System.out.println("Actual username: " + actualUsername);
        System.out.println("Expected username: " + userName);
        Assert.assertEquals(actualUsername, userName);
       // Assert.assertEquals(response.jsonPath().get("username"), userName);
      // Assert.assertEquals(response.path("username"),userName);
       // Assert.assertEquals(response.path("username"),userName);
    }
    @Test
    public void postfromHim() {
        String userName = "Dareczek";
        RequestSpecification request = RestAssured.given();
        request.contentType(ContentType.JSON);
        request.body("{\"id\":464610,\"username\":\""+userName+"\",\"firstName\":\"string\",\"lastName\":\"string\",\"email\":\"string\",\"password\":\"string\",\"phone\":\"string\",\"userStatus\":0}");
        Response response = request.post(url);
        System.out.println(response.getBody().asString());
        String responseUsername = response.path("username");
        System.out.println("Response username: " + responseUsername);
        Assert.assertEquals(responseUsername, userName);
    }


    @Test
    public void testWord() {
        String userName = "nikita";
        Response response = get(url + userName);
        System.out.println(response.getBody().asString());
        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(userName,response.path("username"));
            }

}
