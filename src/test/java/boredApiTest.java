import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import static io.restassured.RestAssured.get;
import org.testng.Assert;

public class boredApiTest {
    String url = "http://www.boredapi.com/api/activity?";

    @DataProvider
    public Object[][] keyCheck() {
        return new Object[][]{
                {"3943506"},
                {"5881028"},
                {"5808228"},
                {"3136036"}
        };
    }

    @DataProvider
    public Object[][] typeCheck() {
        return new Object[][]{
                {"education"},
                {"recreational"},
                {"social"},
                {"relaxation"}
        };
    }

    @Test(dataProvider = "keyCheck")
    public void testByKeyActivity(String fromDataProvider) {
        String keyType = "key=";
        Response response = get(url + keyType + fromDataProvider);
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.path("key"), fromDataProvider);
    }

    @Test(dataProvider = "typeCheck")
    public void testByTypeActivity(String fromDataProvider) {
        String keyType = "type=";
        Response response = get(url + keyType + fromDataProvider);
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.path("type"), fromDataProvider);
    }

    @Test
    public void invalidtest() {
        String keyType = "key=";
        String keyValue = "6Lk98uNN8";
        Response response = get(url + keyType + keyValue);
        Assert.assertTrue(response.body().asString().contains("No activity found with the specified parameters"));
    }
}
