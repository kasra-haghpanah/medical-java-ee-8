package test.arqulian.javaee;

import com.kasra.javaee.model.Sick;

import com.kasra.javaee.repository.SickRepository;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.List;
import java.util.Properties;

/**
 * Created by kasra.haghpanah on 29/11/2016.
 */
//@RunWith(JUnit4.class)
public class SickTest {

    private static SickRepository sickRepository;

    private static Sick sick = null;


    //@Before
    public void setUp() {



        Properties prop = new Properties();
        prop.put(Context.PROVIDER_URL,"192.168.22.97:3700");
        prop.put("org.omg.CORBA.ORBInitialHost","192.168.22.97");
        prop.put("org.omg.CORBA.ORBInitialPort","3700");
        prop.put("java.naming.factory.url.pkgs","com.sun.enterprise.naming");
        prop.put("java.naming.factory.initial","com.sun.enterprise.naming.impl.SerialInitContextFactory");
        prop.put("java.naming.factory.state","com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");

       Context ctx = null;
        try {
            ctx = new InitialContext(prop);
        } catch (NamingException e) {
            e.printStackTrace();
        }


        try {
            ctx.lookup("java:global/medical/SickRepository!com.kasra.javaee.interfaces.repository.ISickRepositoryRemote");
        } catch (NamingException e) {
            e.printStackTrace();
        }


    }


    //@Test
    public void getAll() {
        List<Sick> getAll = sickRepository.getAll();
        System.out.println(getAll);
    }


}
