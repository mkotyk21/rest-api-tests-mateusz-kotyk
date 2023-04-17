import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.restassured.response.Response;

import static io.restassured.RestAssured.get;

import org.testng.Assert;

public class ValidPeselTest {
    private String url = "https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=";

    @DataProvider
    public Object[][] testDOB() {
        return new Object[][]{
                {"00811641375", "1800-01-16"}, {"20821655791", "1820-02-16"},
                {"05830298704", "1805-03-02"}, {"81871279935", "1881-07-12"},
                {"89901187405", "1889-10-11"}, {"50922142324", "1850-12-21"},
                {"00022855143", "1900-02-28"}, {"25052204233", "1925-05-22"},
                {"35061648542", "1935-06-16"}, {"80091066493", "1980-09-10"},
                {"92103059528", "1992-10-30"}, {"99123170004", "1999-12-31"},
                {"00210120116", "2000-01-01"}, {"09230825269", "2009-03-08"},
                {"48261574096", "2048-06-15"}, {"56282171565", "2056-08-21"},
                {"87292818612", "2087-09-28"}, {"99323157889", "2099-12-31"},
                {"00410172010", "2100-01-01"}, {"15430111253", "2115-03-01"},
                {"21451324064", "2121-05-13"}, {"54483113256", "2154-08-31"},
                {"59502084528", "2159-10-20"}, {"99523181895", "2199-12-31"},
                {"00610135130", "2200-01-01"}, {"02640746225", "2202-04-07"},
                {"33663016466", "2233-06-30"}, {"61692008615", "2261-09-20"},
                {"77711112531", "2277-11-11"}, {"99723136303", "2299-12-31"}
        };
    }

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
                {"08293027384"},
                {"00222987727"}
        };
    }
    @DataProvider
    public Object[][] testGender() {
        return new Object[][]{
                {"36471551596", "Male"}, {"61521954511", "Male"},
                {"57861282737", "Male"}, {"18251515954", "Male"},
                {"33072631371", "Male"}, {"08293027384", "Female"},
                {"37522139242", "Female"}, {"23522236423", "Female"},
                {"29280917862", "Female"}, {"11462926601", "Female"}
        };
    }
    @Test(dataProvider = "testValidPesel")
    public void validPesel(String fromDataProvider) {
//providing the valid pesel from generator and checking if response is valid
        Response response = get(url + fromDataProvider);
        boolean isValid = response.path("isValid");
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertTrue(isValid);
    }
    @Test(dataProvider = "testGender")
    public void genderTest(String fromDataProvider, String gender) {
        Response response = get(url + fromDataProvider);
        String genderValue = response.path("gender");
        Assert.assertEquals(genderValue, gender);
    }
    @Test(dataProvider = "testDOB")
    public void DOBtest(String fromDataProvider, String dob) {
        Response response = get(url + fromDataProvider);
        String dobValue = response.path("dateOfBirth");
        Assert.assertTrue(dobValue.contains(dob));
    }
}
