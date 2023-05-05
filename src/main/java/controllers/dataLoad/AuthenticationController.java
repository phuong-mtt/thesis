package controllers.dataLoad;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.spi.json.JacksonJsonNodeJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import controllers.utils.constants;
import io.restassured.http.Method;

public class AuthenticationController extends BaseController {
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
