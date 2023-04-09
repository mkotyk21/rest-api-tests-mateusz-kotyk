import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import io.restassured.specification.*;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.post;

import org.testng.Assert;

public class InvalidPeselTest {
    String url = "https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=";

    @DataProvider
    public Object[][] testShortPesel() {
        return new Object[][]{
                {"8"},
                {"56"},
                {"321"},
                {"4585"},
                {"69061"},
                {"771114"},
                {"9902019"},
                {"18250582"},
                {"082930273"},
                {"6719302731"}
        };}
    @DataProvider
        public Object[][] testLongPesel() {
            return new Object[][]{
                    {"888454523212"},
                    {"5654585256321"},
                    {"1258789565256985412"},
                    {"4848912941849814149141414149814141414914914149149814914914891489"}
            };
        }

    @Test(dataProvider = "testShortPesel")
    public void invalidShortPesel(String stringFromDataProvider) {
        //Providing the short length of pesel should to shown an error
        Response response = get(url+stringFromDataProvider);
        System.out.println(response.body().asString());
        boolean isValid = response.path("isValid");
        Assert.assertFalse(isValid);
        String responseBody= response.getBody().asString();
       Assert.assertTrue(responseBody.contains("Invalid length. Pesel should have exactly 11 digits."));
    }
    @Test(dataProvider = "testLongPesel")
    public void invalidLongPesel(String stringFromDataProvider) {
        //Providing the short length of pesel should to shown an error
        Response response = get(url+stringFromDataProvider);
        System.out.println(response.body().asString());
        boolean isValid = response.path("isValid");
        Assert.assertFalse(isValid);
        String responseBody= response.getBody().asString();
        Assert.assertTrue(responseBody.contains("Invalid length. Pesel should have exactly 11 digits."));
    }
}
