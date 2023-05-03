package APITesting;

import com.beust.ah.A;
import controllers.dataLoad.AuthenticationController;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import listeners.reportListener;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.annotations.*;

import java.util.HashMap;

import static controllers.dataLoad.AuthenticationController.changeValueWithJsonPath;
import static org.apache.http.HttpStatus.*;

import static controllers.utils.JsonParser.*;
import static org.hamcrest.Matchers.*;

@Listeners(reportListener.class)
public class authenticate {
    private final AuthenticationController authenticationController = new AuthenticationController();
    /**
     * SUCCESSFULLY
     * */
    @Test(description= "Case verify authenticating users successfully when entering valid credentials")
    @SneakyThrows
    public void authenticateSuccessfullyWithValidCredentials(){
        //Arrange
        var body = createBody("testData/authenSuccessfully.json");
        System.out.println(body);
        //Act
        Response authenticateResponse = authenticationController.create(body);
        System.out.println(authenticateResponse.asString());
        //Assert
        Assert.assertThat(authenticateResponse.statusCode(), is(SC_OK));
        Assert.assertEquals(body.toPrettyString(), authenticateResponse.asString());
    }

    @Test(description= "Case verify authenticating users successfully when entering required fields")
    @SneakyThrows
    public void authenticateSuccessfullyWithRequiredFields(){ //same authenticate successfully with valid credentials
        //Arrange

        //Act

        //Assert
    }

    /**
    * UNSUCCESSFULLY
    * */
    @DataProvider(name = "requiredFieldBlank")
    private static Object[][] requiredFieldBlank(){
        return new Object[][] {{"0666523191", ""}, {"", "password"}};
    }
    @Test(description= "Case verify authenticating users unsuccessfully when required fields are blank", dataProvider = "requiredFieldBlank")
    @SneakyThrows
    public void authenticateUnSuccessfullyWithRequiredFieldBlank(String username, String password){
        //Arrange
        var bodyEg = createBody("testData/authenSuccessfully.json");
        bodyEg.removeAll();
        HashMap<String, String> credentials = new HashMap<>();
        credentials.put("tel", username);
        credentials.put("password", password);

        for (String field: credentials.keySet()){
            String body = changeValueWithJsonPath(bodyEg.toPrettyString(), "$" + field, credentials.get(field));
            //Act
            Response authenticateResponse = authenticationController.create(body);
            var expectedResult = asString("errors/400_BAD_REQUEST.json");

            //Assert
            Assert.assertThat(authenticateResponse.statusCode(), is(SC_BAD_REQUEST));
            Assert.assertEquals(expectedResult, authenticateResponse.asString());
        }
    }

    @DataProvider(name="requiredFieldMissing")
    private static Object[][] requiredFieldMissing(){
        return new Object[][] {{"tel"}, {"password"}};
    }

    @Test(description= "Case verify authenticating users unsuccessfully when lacking of required fields", dataProvider = "requiredFieldMissing")
    @SneakyThrows
    public void authenticateUnSuccessfullyWithoutRequiredFields(String fieldName){
        //Arrange
        var body = createBody("testData/authenSuccessfully.json");

        //Act
        body.remove(fieldName);
        Response authenticateResponse = authenticationController.create(body);
        var expectedResult = asString("errors/404_NOT_FOUND.json");


        //Assert
        Assert.assertThat(authenticateResponse.statusCode(), is(SC_BAD_REQUEST));
        Assert.assertEquals(expectedResult, authenticateResponse.asString());
    }

    @Test(description= "Case verify authenticating users unsuccessfully when entering wrong format of username")
    @SneakyThrows
    public void authenticateUnSuccessfullyWithWrongFormatOfUsername(){
        //Arrange
        var body = createBody("testData/authenWithWrongUsername.json");

        //Act
        Response authenticateResponse = authenticationController.create(body);
        var expectedResult = asString("errors/404_NOT_FOUND.json");

        //Assert
        Assert.assertThat(authenticateResponse.statusCode(), is(SC_BAD_REQUEST));
//        Assert.assertEquals(expectedResult, authenticateResponse.asString());
        JSONAssert.assertEquals(expectedResult, authenticateResponse.asString(), JSONCompareMode.STRICT);
    }

    @Test(description= "Case verify authenticating users unsuccessfully when entering invalid password")
    @SneakyThrows
    public void authenticateUnSuccessfullyWithInvalidPassword(){
        //Arrange
        var body = createBody("testData/authenWithInvalidPassword.json");

        //Act
        Response authenticateResponse = authenticationController.create(body);
        var expectedResult = asString("errors/401_AUTH_REQUIRED.json");

        //Assert
        Assert.assertThat(authenticateResponse.statusCode(), is(SC_UNAUTHORIZED));
        Assert.assertEquals(expectedResult, authenticateResponse.asString());

    }

    @Test(description= "Case verify authenticating users unsuccessfully when username has not been recorded in the system")
    @SneakyThrows
    public void authenticateUnSuccessfullyWithNonExistedUsername(){
        //Arrange
        var body = createBody("testData/authenWithNonExistedUser.json");

        //Act
        Response authenticateResponse = authenticationController.create(body);
        var expectedResult = asString("errors/404_NOT_FOUND.json");

        //Assert
        Assert.assertThat(authenticateResponse.statusCode(), is(SC_NOT_FOUND));
        Assert.assertEquals(expectedResult, authenticateResponse.asString());
    }

    @Test(description= "Case verify authenticating users unsuccessfully when using wrong HTTP method")
    @SneakyThrows
    public void authenWith405Error(){
        //Arrange
        var requestBody = createBody("testData/authenSuccessfully.json");

        //Act
        var authenticateResponse = authenticationController.check405(requestBody.toPrettyString(), Method.PUT);
        var expectedResult = asString("errors/405_OP_NOT_ALLOWED.json");

        //Assert
        Assert.assertThat(authenticateResponse.statusCode(), is(SC_METHOD_NOT_ALLOWED));
        Assert.assertEquals(expectedResult, authenticateResponse.asString());
    }

    @Test(description= "Case verify authenticating users unsuccessfully when having wrong Accept header")
    @SneakyThrows
    public void authenWith406Error(){
        //Arrange
        var requestBody = createBody("testData/authenSuccessfully.json");

        //Act
        var authenticateResponse = authenticationController.check406(requestBody.toPrettyString(), Method.POST);
        var expectedResult = asString("errors/406_NOT_ACCEPTABLE.json");

        //Assert
        Assert.assertThat(authenticateResponse.statusCode(), is(SC_NOT_ACCEPTABLE));
        Assert.assertEquals(expectedResult, authenticateResponse.asString());
    }

    @Test(description= "Case verify authenticating users unsuccessfully when having wrong media type")
    @SneakyThrows
    public void authenWith415Error(){
        //Arrange
        var requestBody = createBody("testData/authenSuccessfully.json");

        //Act
        var authenticateResponse = authenticationController.check415(Method.POST, requestBody.toPrettyString());
        var expectedResult = asString("errors/415_UNSUPPORTED_MEDIA_TYPE.json");

        //Assert
        Assert.assertThat(authenticateResponse.statusCode(), is(SC_UNSUPPORTED_MEDIA_TYPE));
        Assert.assertEquals(expectedResult, authenticateResponse.asString());
    }
}
