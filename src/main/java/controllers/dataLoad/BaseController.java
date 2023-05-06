package controllers.dataLoad;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.RestAssured;

import java.util.HashMap;

public abstract class BaseController<T> {
    protected RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
    private String requestUrl;
    private Method requestMethod;
    public BaseController withDefaultHeaders(){
        this.requestSpecBuilder.addHeader("Content-Type", "application/json");
        return this;
    }

    public BaseController withHeader(String headerKey, String headerValue){
        this.requestSpecBuilder.addHeader(headerKey, headerValue);
        return this;
    }

    public BaseController withBody (String requestBody){
        this.requestSpecBuilder.setBody(requestBody);
        return this;
    }

    public BaseController withBody (ObjectNode requestBody){
        this.requestSpecBuilder.setBody(requestBody);
        return this;
    }

    public BaseController withMethod(Method method){
        this.requestMethod = method;
        return this;
    }

    public BaseController withUrl(String url){
        this.requestUrl = url;
        return this;
    }

    public BaseController withQueryParam(String paramName, String paramValue){
        this.requestSpecBuilder.addQueryParam(paramName, new Object[]{paramValue});
        return this;
    }

    public BaseController withQueryParams(HashMap<String, String> params){
        this.requestSpecBuilder.addQueryParams(params);
        return this;
    }

    public Response doRequest(){
        Response response = (Response) RestAssured.given(this.requestSpecBuilder.build()).request(this.requestMethod, this.requestUrl, new Object[0]);
        this.init();
        return response;
    }

//    public Response create(String bodyPath, String url){
//        Response response = this.withDefaultHeaders().withBody(bodyPath).withMethod(Method.POST).withUrl(url).doRequest();
//        return response;
//    }

    public Response create(ObjectNode body){
        Response response = this.withDefaultHeaders().withBody(body).withMethod(Method.POST).withUrl(this.getBaseURL()).doRequest();
        return response;
    }

    public Response create(String bodyPath){
        Response response = this.withDefaultHeaders().withBody(bodyPath).withMethod(Method.POST).withUrl(this.getBaseURL()).doRequest();
        return response;
    }

    public Response get (String resourceId){
        BaseController var10000 = this.withDefaultHeaders().withMethod(Method.GET);
        String var10001 = this.getBaseURL();
        Response response = var10000.withUrl(var10001 + resourceId).doRequest();
        return response;
    }

    public Response getList(){
        Response response = this.withDefaultHeaders().withMethod(Method.GET).withUrl(this.getBaseURL()).doRequest();
        return response;
    }

    public Response patch (String resourceId, String bodyPath){
        BaseController var10000 = this.withDefaultHeaders().withBody(bodyPath).withMethod(Method.PATCH);
        String var10001 = this.getBaseURL();
        Response response = var10000.withUrl(var10001 + resourceId).doRequest();
        return response;
    }

    public Response patch (String resourceId, ObjectNode body){
        BaseController var10000 = this.withDefaultHeaders().withBody(body).withMethod(Method.PATCH);
        String var10001 = this.getBaseURL();
        Response response = var10000.withUrl(var10001 + resourceId).doRequest();
        return response;
    }

    public Response put (String resourceId, String bodyPath){
        BaseController var10000 = this.withDefaultHeaders().withBody(bodyPath).withMethod(Method.PUT);
        String var10001 = this.getBaseURL();
        Response response = var10000.withUrl(var10001 + resourceId).doRequest();
        return response;
    }

    public Response put (String resourceId, ObjectNode body){
        BaseController var10000 = this.withDefaultHeaders().withBody(body).withMethod(Method.PUT);
        String var10001 = this.getBaseURL();
        Response response = var10000.withUrl(var10001 + resourceId).doRequest();
        return response;
    }

    public Response delete (String resourceId){
        BaseController var10000 = this.withDefaultHeaders().withMethod(Method.DELETE);
        Response response = var10000.withUrl(this.getBaseURL() + resourceId).doRequest();
        return response;
    }

    public void init(){
        this.requestSpecBuilder = new RequestSpecBuilder();
        this.requestUrl = null;
        this.requestMethod = null;
    }

    protected abstract String getBaseURL();
}
