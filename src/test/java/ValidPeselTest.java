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

public class ValidPeselTest {
    String url = "https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=";

    @DataProvider
    public Object[][] testValidPesel() {
        return new Object[][]{
                {"97071042673"},
                {"80030936652"},
                {"95101214511"},
                {"00211233439"},
                {"48082582658"},
                {"69061339629"},
                {"77111427808"},
                {"99020194615"},
                {"18250582755"},
                {"08293027384"}
        };
    }

    @Test(dataProvider = "testValidPesel")
    public void valifPesel(String fromDataProvider) {
//providing the valid pesel from generator and checking if response is valid
        Response response = get(url + fromDataProvider);
        System.out.println(response.body().asString());
        boolean isValid = response.path("isValid");
        Assert.assertTrue(isValid);

    }


}
