package controllers.utils;

import lombok.SneakyThrows;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

public class assertion extends Assert {
    @SneakyThrows
    public static <T> void assertEquals(String actualResult, String expectedResult, JSONCompareMode compareMode){
        processAssertion(actualResult, expectedResult, compareMode);
    }

    @SneakyThrows
    public static <T> void assertThat(T actualResult, Matcher<? super T> matcher ){
        MatcherAssert.assertThat(actualResult, matcher);
    }

    @SneakyThrows
    public static void processAssertion(Object actualResult, Object expectedResult, JSONCompareMode compareMode) {
        JSONAssert.assertEquals(actualResult.toString(), expectedResult.toString(), compareMode);
    }
}
