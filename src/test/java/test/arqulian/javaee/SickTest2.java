package test.arqulian.javaee;

import com.kasra.javaee.interfaces.repository.ISickRepository;
import com.kasra.javaee.model.Sick;
import com.kasra.javaee.repository.SickRepository;
//import org.jboss.arquillian.container.test.api.Deployment;
//import org.jboss.arquillian.junit.Arquillian;
//import org.jboss.shrinkwrap.api.ShrinkWrap;
//import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

/**
 * Created by kasra.haghpanah on 29/11/2016.
 */
//@RunWith(Arquillian.class)
@RunWith(JUnit4.class)
public class SickTest2 {

    //@EJB
    //ISickRepository sickRepository;


/*
    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addClass(ISickRepository.class).addClass(SickRepository.class)
                .addPackage("com.kasra.javaee.interfaces.repository")
                .addPackage("com.kasra.javaee.repository")
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF", "beans.xml"));
    }
*/


   @Test
    public void getAll() {
        HashMap<String , Integer> map = new HashMap<String , Integer>();
        map.put("kasra" , 20);
        map.get("kasra");

        //List<Sick> getAll = sickRepository.getAll();
        //System.out.println(getAll);
    }


}
