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
    public Object[][] incorrectMonth() {
        return new Object[][]{
                {"87132354586"},
                {"74341123658"},
                {"08591123258"},
                {"18541923852"},
                {"65800125872"}
           };
    }
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
        };
    }
    @DataProvider
    public Object[][] invalidCheckSum() {
        return new Object[][]{
                {"41472743151"},{"48021884252"},{"47920189843"},{"29232171338"},{"55060642600"},{"59460581301"},
                {"26122244715"},{"00882637362"},{"31920169887"},{"34230570809"},{"79292899890"},{"41100868682"},
                {"11461206261"},{"41480389575"},{"86080532597"},{"14452600594"},{"75441964641"},{"94321955678"},
                {"71102951043"},{"00210142553"},{"11522676461"},{"96850497597"},{"00832305799"},{"09813041535"},
                {"72822054480"},{"63911936350"},{"15291864241"},{"60320677813"},{"60052643588"},{"26111589871"}
                        };
    }

    @DataProvider
    public Object[][] testLongPesel() {
        return new Object[][]{
                {"888454523212"},
                {"5654585256321"},
                {"1258789565256985412"},
                {"484891294184981414914141414981414141491491414911489"},
                {"4848912941849814149141414149814141414914914149149814914914891489"},
                {"4848912941849814149141414149814141414914914149149814914914891486575675756886578567546765467567567659"}
        };
    }

    @Test(dataProvider = "testShortPesel")
    public void invalidShortPesel(String stringFromDataProvider) {
        //Providing the short length of pesel should to shown an error
        Response response = get(url + stringFromDataProvider);
        boolean isValid = response.path("isValid");
        Assert.assertFalse(isValid);
        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains("Invalid length. Pesel should have exactly 11 digits."));
    }

    @Test(dataProvider = "testLongPesel")
    public void invalidLongPesel(String stringFromDataProvider) {
        //Providing the short length of pesel should to shown an error
        Response response = get(url + stringFromDataProvider);
        boolean isValid = response.path("isValid");
        Assert.assertFalse(isValid);
        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains("Invalid length. Pesel should have exactly 11 digits.") || responseBody.contains("Pesel should be a number"));
    }

    @Test
    public void emptyField() {
        Response response = get(url);
        Assert.assertEquals(response.statusCode(), 400);
    }

    @Test(dataProvider = "incorrectMonth")
    public void testIncorrectMonth(String fromDataProvider){
        Response response=get(url+fromDataProvider);
        boolean isValid = response.path("isValid");
        Assert.assertFalse(isValid);
        String responseBody=response.getBody().asString();
        Assert.assertTrue(responseBody.contains("Invalid month"));
    }
    @Test(dataProvider = "invalidCheckSum")
    public void testIncorrectCheckSum(String fromDataProvider){
        Response response=get(url+fromDataProvider);
        boolean isValid = response.path("isValid");
        Assert.assertFalse(isValid);
        String responseBody=response.getBody().asString();
        Assert.assertTrue(responseBody.contains("Check sum is invalid. Check last digit"));
    }
}
