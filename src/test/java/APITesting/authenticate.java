package APITesting;

import com.fasterxml.jackson.databind.ObjectMapper;
import controllers.dataLoad.AuthenticationController;
import controllers.utils.reRunFailedCases;
import io.restassured.http.Method;
import io.restassured.response.Response;
import listeners.reportListener;
import lombok.SneakyThrows;

import static org.skyscreamer.jsonassert.JSONCompareMode.*;

import org.testng.Assert;
import org.testng.annotations.*;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static controllers.utils.assertion.assertEquals;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;


import java.util.HashMap;

import static controllers.dataLoad.AuthenticationController.changeValueWithJsonPath;
import static org.apache.http.HttpStatus.*;

import static controllers.utils.JsonParser.*;

@Listeners(reportListener.class)
public class authenticate {
    private final AuthenticationController authenticationController = new AuthenticationController();

    /**
     * SUCCESSFULLY
     */
    @Test(description = "Case verify authenticating users successfully when entering valid credentials")
    @SneakyThrows
    public void authenticateSuccessfullyWithValidCredentials() {
        //Arrange
        var body = createBody("testData/authenticate/authenSuccessfully.json");

        //Act
        Response authenticateResponse = authenticationController.authenticate(body.toPrettyString());
        String createdAt = authenticateResponse.then().extract().path("createdAt");

        //Assert
        assertThat(authenticateResponse.statusCode(), is(SC_OK));
        assertThat(authenticateResponse.asString(), allOf(
                //common
                hasJsonPath("$.createdAt", is(createdAt))
        ));
    }

    @Test(description = "Case verify authenticating users successfully when entering required fields")
    @SneakyThrows
    public void authenticateSuccessfullyWithRequiredFields() { //same authenticate successfully with valid credentials
        //Arrange

        //Act

        //Assert
    }

    /**
     * UNSUCCESSFULLY
     */
    @DataProvider(name = "requiredFieldBlank")
    private static Object[][] requiredFieldBlank() {
        return new Object[][]{{"0164685417", ""}, {"", "password"}};
    }

    @Test(description = "Case verify authenticating users unsuccessfully when required fields are blank", dataProvider = "requiredFieldBlank")
    @SneakyThrows
    public void authenticateUnSuccessfullyWithRequiredFieldBlank(String username, String password) {
        //Arrange
        var bodyEg = createBody("testData/authenticate/authenSuccessfully.json");
        bodyEg.removeAll();
        HashMap<String, String> credentials = new HashMap<>();
        credentials.put("tel", username);
        credentials.put("password", password);

        String authen = new ObjectMapper().writeValueAsString(credentials);

        //Act
        Response authenticateResponse = authenticationController.authenticate(authen);
        var expectedResult = asString("errors/authenticate/400_BAD_REQUEST.json");

        for(String key: credentials.keySet()){
            if(key == "tel" && credentials.get(key) == ""){
                //Assert
                assertThat(authenticateResponse.statusCode(), is(SC_BAD_REQUEST));
                assertEquals(authenticateResponse.asString(), expectedResult, STRICT);
            }
            else if (key == "password" && credentials.get(key) == ""){
                //Assert
                assertThat(authenticateResponse.statusCode(), is(SC_BAD_REQUEST));
                assertEquals(expectedResult.replace("tel", "password"), authenticateResponse.asString(),  STRICT);
            }
        }
    }

    @DataProvider(name = "requiredFieldMissing")
    private static Object[][] requiredFieldMissing() {
        return new Object[][]{{"tel"}, {"password"}};
    }

    @Test(description = "Case verify authenticating users unsuccessfully when lacking of required fields", dataProvider = "requiredFieldMissing")
    @SneakyThrows
    public void authenticateUnSuccessfullyWithoutRequiredFields(String fieldName) {
        //Arrange
        var body = createBody("testData/authenticate/authenSuccessfully.json");

        //Act
        body.remove(fieldName);
        Response authenticateResponse = authenticationController.authenticate(body.toPrettyString());
        var expectedResult = asString("errors/authenticate/400_PARAM_REQUIRED.json");

        if(fieldName == "tel"){
            //Assert
            assertThat(authenticateResponse.statusCode(), is(SC_BAD_REQUEST));
            assertEquals(expectedResult.replace("password", "tel"), authenticateResponse.asString(), STRICT);
        }
        else{
            //Assert
            assertThat(authenticateResponse.statusCode(), is(SC_BAD_REQUEST));
            assertEquals(authenticateResponse.asString(), expectedResult, STRICT);
        }
    }

    @Test(description = "Case verify authenticating users unsuccessfully when entering wrong format of username")
    @SneakyThrows
    public void authenticateUnSuccessfullyWithWrongFormatOfUsername() {
        //Arrange
        var body = createBody("testData/authenticate/authenWithWrongUsername.json");

        //Act
        Response authenticateResponse = authenticationController.authenticate(body.toPrettyString());
        String value = authenticateResponse.then().extract().path("errors[0].value");
        var expectedResult = asString("errors/authenticate/400_BAD_REQUEST.json");
        String expectedResultFinal = changeValueWithJsonPath(expectedResult, "errors[0].value", value);

        //Assert
        assertThat(authenticateResponse.statusCode(), is(SC_BAD_REQUEST));
        assertEquals(expectedResultFinal.replace("required", "invalid"), authenticateResponse.asString(),  STRICT);
    }

    @Test(description = "Case verify authenticating users unsuccessfully when entering invalid password")
    @SneakyThrows
    public void authenticateUnSuccessfullyWithInvalidPassword() {
        //Arrange
        var body = createBody("testData/authenticate/authenWithInvalidPassword.json");

        //Act
        Response authenticateResponse = authenticationController.authenticate(body.toPrettyString());
        var expectedResult = asString("errors/authenticate/401_AUTH_REQUIRED.json");

        //Assert
        assertThat(authenticateResponse.statusCode(), is(SC_UNAUTHORIZED));
        assertEquals(authenticateResponse.asString(), expectedResult, STRICT);

    }

    @Test(description = "Case verify authenticating users unsuccessfully when username has not been recorded in the system")
    @SneakyThrows
    public void authenticateUnSuccessfullyWithNonExistedUsername() {
        //Arrange
        var body = createBody("testData/authenticate/authenWithNonExistedUser.json");

        //Act
        Response authenticateResponse = authenticationController.authenticate(body.toPrettyString());
        var expectedResult = asString("errors/authenticate/404_NOT_FOUND.json");

        //Assert
        assertThat(authenticateResponse.statusCode(), is(SC_NOT_FOUND));
        assertEquals(authenticateResponse.asString(), expectedResult, STRICT);
    }

    @Test(description = "Case verify authenticating users unsuccessfully when using wrong HTTP method")
    @SneakyThrows
    public void authenWith405Error() {
        //Arrange
        var requestBody = createBody("testData/authenticate/authenSuccessfully.json");

        //Act
        var authenticateResponse = authenticationController.check405(requestBody.toPrettyString(), Method.PUT);
        var expectedResult = asString("errors/authenticate/405_OP_NOT_ALLOWED.json");

        //Assert
        assertThat(authenticateResponse.statusCode(), is(SC_METHOD_NOT_ALLOWED));
        assertEquals(authenticateResponse.asString(), expectedResult, STRICT);
    }

    @Test(description = "Case verify authenticating users unsuccessfully when having wrong Accept header")
    @SneakyThrows
    public void authenWith406Error() {
        //Arrange
        var requestBody = createBody("testData/authenticate/authenSuccessfully.json");

        //Act
        var authenticateResponse = authenticationController.check406(requestBody.toPrettyString(), Method.POST);
        var expectedResult = asString("errors/authenticate/406_NOT_ACCEPTABLE.json");

        //Assert
        assertThat(authenticateResponse.statusCode(), is(SC_NOT_ACCEPTABLE));
        assertEquals(authenticateResponse.asString(), expectedResult, STRICT);
    }

    @Test(description = "Case verify authenticating users unsuccessfully when having wrong media type")
    @SneakyThrows
    public void authenWith415Error() {
        //Arrange
        var requestBody = createBody("testData/authenticate/authenSuccessfully.json");

        //Act
        var authenticateResponse = authenticationController.check415(Method.POST, requestBody.asText());
        var expectedResult = asString("errors/authenticate/415_UNSUPPORTED_MEDIA_TYPE.json");

        //Assert
        assertThat(authenticateResponse.statusCode(), is(SC_UNSUPPORTED_MEDIA_TYPE));
        assertEquals(authenticateResponse.asString(), expectedResult, STRICT);
    }
//
//    /**
//     * Re-run failed cases
//     */
//    @SneakyThrows
//    @Test(description = "Re-run failed cases", retryAnalyzer = reRunFailedCases.class)
//    public void reRunFailed(){
//        Assert.fail();
//    }
}
