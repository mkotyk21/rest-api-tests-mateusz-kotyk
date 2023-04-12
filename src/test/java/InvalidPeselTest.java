import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.restassured.response.Response;

import static io.restassured.RestAssured.get;

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
    public Object[][] shortPesel() {
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
                {"41472743151"}, {"48021884252"}, {"47920189843"}, {"29232171338"}, {"55060642600"}, {"59460581301"},
                {"26122244715"}, {"00882637362"}, {"31920169887"}, {"34230570809"}, {"79292899890"}, {"41100868682"},
                {"11461206261"}, {"41480389575"}, {"86080532597"}, {"14452600594"}, {"75441964641"}, {"94321955678"},
                {"71102951043"}, {"00210142553"}, {"11522676461"}, {"96850497597"}, {"00832305799"}, {"09813041535"},
                {"72822054480"}, {"63911936350"}, {"15291864241"}, {"60320677813"}, {"60052643588"}, {"26111589871"}
        };
    }

    @DataProvider
    public Object[][] longPesel() {
        return new Object[][]{
                {"888454523212"},
                {"5654585256321"},
                {"1258789565256985412"},
                {"484891294184981414914141414981414141491491414911489"},
                {"4848912941849814149141414149814141414914914149149814914914891489"},
                {"4848912941849814149141414149814141414914914149149814914914891486575675756886578567546765467567567659"}
        };
    }

    @DataProvider
    public Object[][] invalidCharacters() {
        return new Object[][]{
                {"acd34554340"},
                {"5654?>oo321"},
                {"!2587565256"},
                {"4848n*41848"},
                {"49814ikm489"},
                {"_484891181_"},
                {"WeccOYTpidx"}
        };
    }

    @DataProvider
    public Object[][] invalidDay() {
        return new Object[][]{
                {"91243318236"}, {"05460070904"}, {"65328123746"}, {"00829907357"}, {"56815105331"}, {"76483282408"},
                {"69063112131"}, {"33239932032"}, {"13528340152"}, {"53924140126"}, {"18473335947"}, {"99022938385"},
                {"61033280243"}, {"82884650523"}, {"31463523432"}, {"82433896612"}, {"88023168593"}, {"97827850761"}
        };
    }

    @Test(dataProvider = "shortPesel")
    public void testInvalidShortPesel(String stringFromDataProvider) {
        //Providing the short length of pesel should to shown an error
        Response response = get(url + stringFromDataProvider);
        boolean isValid = response.path("isValid");
        Assert.assertFalse(isValid);
        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains("Invalid length. Pesel should have exactly 11 digits."));

    }

    @Test(dataProvider = "longPesel")
    public void testInvalidLongPesel(String stringFromDataProvider) {
        //Providing the short length of pesel should to shown an error
        Response response = get(url + stringFromDataProvider);
        boolean isValid = response.path("isValid");
        Assert.assertFalse(isValid);
        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains("Invalid length. Pesel should have exactly 11 digits.") || responseBody.contains("Pesel should be a number"));
    }

    @Test
    public void testEmptyField() {
        Response response = get(url);
        Assert.assertEquals(response.statusCode(), 400);
    }

    @Test(dataProvider = "incorrectMonth")
    public void testIncorrectMonth(String fromDataProvider) {
        Response response = get(url + fromDataProvider);
        boolean isValid = response.path("isValid");
        Assert.assertFalse(isValid);
        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains("Invalid month"));
    }

    @Test(dataProvider = "invalidCheckSum")
    public void testIncorrectCheckSum(String fromDataProvider) {
        Response response = get(url + fromDataProvider);
        boolean isValid = response.path("isValid");
        Assert.assertFalse(isValid);
        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains("Check sum is invalid. Check last digit"));
    }

    @Test(dataProvider = "invalidCharacters")
    public void testInvalidCharacters(String fromDataProvider) {
        Response response = get(url + fromDataProvider);
        boolean isValid = response.path("isValid");
        Assert.assertFalse(isValid);
        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains("NBRQ"));

    }

    @Test(dataProvider = "invalidDay")
    public void testInvalidDay(String fromDataProvider) {
        Response response = get(url + fromDataProvider);
        boolean isValid = response.path("isValid");
        Assert.assertFalse(isValid);
        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains("INVD"));

    }
}
