package test.arqulian.javaee;

import com.kasra.javaee.framework.web.mvc.statics.Method;
import com.kasra.javaee.framework.web.mvc.utility.BrowserUtility;
import com.kasra.javaee.model.Sick;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kasra.haghpanah on 02/12/2016.
 */
@RunWith(JUnit4.class)
public class DownloadTest {

    private static Sick sick = null;

    private static List<String> result = new ArrayList<String>();


    @Before
    public void setUp() {


        System.out.println(BrowserUtility.authentication(false, "localhost", 8084, Method.POST, true, false, "kasra", "123"));


        String url = "http://localhost:8084/kasra/start";
        byte[] bytes = (byte[]) BrowserUtility.send(url, Method.GET, null, "application/x-www-form-urlencoded", "*/*", false, false, null, null).get(0);


        if (bytes != null) {
            sick = (Sick) BrowserUtility.deserialize(bytes);
        }
    }


    @Test
    public void getAll() {
        System.out.println(sick.toString());
    }

}
