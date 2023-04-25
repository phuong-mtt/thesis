package controllers.dataLoad;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.spi.json.JacksonJsonNodeJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import controllers.utils.constants;
import lombok.SneakyThrows;
import io.restassured.RestAssured;
import io.restassured.config.RedirectConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHeaders;
import org.openqa.selenium.json.Json;

public class AuthenticationController extends BaseController {
    @SneakyThrows
//    public AuthenticationController() {
//    }

    protected String getBaseURL() {
        return constants.BASE_URL + constants.AUTH_URL;
    }


    public Response authenticate(String body) {
        return this
                .withDefaultHeaders()
                .withBody(body)
                .withMethod(Method.POST)
                .withUrl(getBaseURL())
                .doRequest();
    }

    public static String changeValueWithJsonPath(String parent, String fieldPath, Object newValue) {
        return JsonPath.using(configuration).parse(parent).set(fieldPath, newValue).jsonString();
    }

    public static String removeValueWithJsonPath(String parent, String fieldPath) {
        return JsonPath.using(configuration).parse(parent).delete(fieldPath).jsonString();
    }

    public Response check405(String body, Method method) {
        if (body != null) {
            this.withBody(body);
        }
        return this.withDefaultHeaders().withMethod(method).withUrl(this.getBaseURL()).doRequest();
    }

    public Response check406(String body, Method method) {
        this.withHeader("Accept", "a/a");
        return check405(body, method);
    }

    public Response check415(Method method, String body) {
        return this
                .withDefaultHeaders()
                .withUrl(this.getBaseURL())
                .withBody(body)
                .withMethod(method)
                .doRequest();
    }

    public static final Configuration configuration = Configuration.builder()
            .jsonProvider(new JacksonJsonNodeJsonProvider())
            .mappingProvider(new JacksonMappingProvider())
            .build();

}
