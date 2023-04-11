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

    @DataProvider
    public Object[][] testGender() {
        return new Object[][]{
                {"36471551596", "Male"}, {"61521954511", "Male"},
                {"57861282737", "Male"}, {"18251515954", "Male"},
                {"89100941752", "Male"}, {"40890115738", "Male"},
                {"69440997055", "Male"}, {"72920158150", "Male"},
                {"33072631371", "Male"}, {"08293027384", "Female"},
                {"85251401693", "Male"}, {"07430829371", "Male"},
                {"12251944613", "Male"}, {"37522139242", "Female"},
                {"99032779646", "Female"}, {"40922368451", "Male"},
                {"23522236423", "Female"}, {"74442358825", "Female"},
                {"19240615088", "Female"}, {"52452245722", "Female"},
                {"29280917862", "Female"}, {"59441031188", "Female"},
                {"05310242584", "Female"}, {"11462926601", "Female"},
                {"66321875589", "Female"}, {"77472673661", "Female"}
        };
    }


    @Test(dataProvider = "testValidPesel")
    public void validPesel(String fromDataProvider) {
//providing the valid pesel from generator and checking if response is valid
        Response response = get(url + fromDataProvider);
        System.out.println(response.body().asString());
        boolean isValid = response.path("isValid");
        Assert.assertTrue(isValid);

    }

    @Test(dataProvider = "testGender")
    public void genderTest(String fromDataProvider, String gender) {
        Response response = get(url + fromDataProvider);
        String genderValue = response.path("gender");
        Assert.assertEquals(genderValue, gender);
    }

}
