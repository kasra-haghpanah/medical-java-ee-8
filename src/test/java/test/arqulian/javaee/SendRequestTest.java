package test.arqulian.javaee;

import com.kasra.javaee.framework.web.mvc.statics.Method;
import com.kasra.javaee.framework.web.mvc.utility.BrowserUtility;
import com.kasra.javaee.model.Sick;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by kasra.haghpanah on 02/12/2016.
 */
@RunWith(JUnit4.class)
public class SendRequestTest {

    private static Sick sick = null;
    private static List<String> result = new ArrayList<String>();


    @Before
    public void setUp() {


        //CookieHandler.setDefault(new CookieManager());
        //ClientWSForm.authentication("http://localhost:8084/j_security_check", "POST", "kasra", "123", true);
        String username = "kasra";
        String password = "123";

        System.out.println(BrowserUtility.authentication(false, "localhost", 8084, Method.POST, true, false, username, password));


        List<Sick> sickList = new ArrayList<Sick>();

        Sick sick = new Sick();
        sick.setId(1);
        sick.setSex((short) 1);
        sick.setFirstName("kasra");
        sick.setLastName("haghpanah");

        sickList.add(sick);

        sick = new Sick();
        sick.setId(2);
        sick.setSex((short) 1);
        sick.setFirstName("kambiz");
        sick.setLastName("rad");

        sickList.add(sick);

        String url = "http://localhost:8084/kasra/medical/record/getBySickId";
        url = "http://localhost:8084/resources/MyRestService/json/getSicks";
        result.add((String) BrowserUtility.send(url, Method.POST, sickList.toString(), "application/json", "application/json", false, false, null, null).get(0));

        //url = "http://localhost:8084/kasra/medical/record/sickId?id=1";
        url = "http://localhost:8084/resources/MyRestService/user/firstname/kasra/lastname/haghpanah";
        String contentType = "application/x-www-form-urlencoded";
        String acceptType = "text/html";

        result.add((String) BrowserUtility.send(url, Method.GET, null, contentType, acceptType, false, false, null, null).get(0));


        url = "http://localhost:8084/resources/MyRestService/xml/getSicks";
        //url = "http://localhost:8084/kasra/xml/read";
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><sicks><sick id=\"0\"><firstName>kasra1</firstName><lastName>haghpanah1</lastName><sex>1</sex></sick><sick id=\"0\"><firstName>farah1</firstName><lastName>diba1</lastName><sex>0</sex></sick></sicks>";
        result.add((String) BrowserUtility.send(url, Method.POST, xml, "application/xml", "application/xml", false, false, null, null).get(0));


        url = "http://localhost:8084/resources/download//02%20-%20Ghorbat.MP3";
        List<Object> content = BrowserUtility.send(url, Method.POST, null, "application/x-www-form-urlencoded", "*/*", false, false, null, null);

        BrowserUtility.writeBinaryFile("E:/test/" + (String) content.get(1), (byte[]) content.get(0));


        // for upload
        url = "http://localhost:8084/kasra/host/upload";
        //url = "http://localhost:8084/kasra/sick/upload";
        List<HashMap<String, Object>> mapList = BrowserUtility.addFormFieldInMap(null, "name", "kasra");
        mapList = BrowserUtility.addFormFieldInMap(mapList, "port", "8084");
        mapList = BrowserUtility.addPartFieldInMap(mapList, "video.png", "E:/test/video.png");
        String response1 = (String) BrowserUtility.upload(url, Method.POST, "UTF-8", mapList).get(0);

        System.out.println(response1);
    }


    @Test
    public void getAll() {

        for (String value : result) {
            System.out.println();
            System.out.println();
            System.out.println(value);
        }

    }

}
