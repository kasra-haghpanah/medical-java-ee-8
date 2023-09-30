package com.kasra.javaee.schedule;

import com.kasra.javaee.controller.DownloadController;
import com.kasra.javaee.interfaces.repository.ISickRepository;
import com.kasra.javaee.jaxb.Sicks;
import com.kasra.javaee.model.Sick;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RunAs;
import javax.ejb.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.Date;

/**
 * @author kasra.haghpanah
 *         Date: 8/15/16
 *         Time: 11:41 AM
 */
@Singleton
@Startup
@Local
public class Schedule {

    @Resource
    private SessionContext context;

    @EJB
    ISickRepository sickRepository;


    @PostConstruct
    public void init() {
        System.out.println("Schedule!");
        startTimer();
    }

    public void startTimer() {

        TimerConfig config = new TimerConfig();
        config.setInfo("This is a demo timer");
        config.setPersistent(true);

        TimerService theTimerService = context.getTimerService();
        Timer theTimer = theTimerService.createCalendarTimer(new ScheduleExpression()
                        //.timezone("Asia/Tehran")
                        //.month("Jan-Mar, Jun")
                        //.dayOfMonth("Last Fri")
                        //.dayOfMonth(4)
                        //.hour("12-23")
                        //.minute("1-38")
                        //.second("0,2,4,6,8,10,12,14,16,18,20,22,24,26,28,30,32,34,36,38,40,42,44,46,48,50,52,54,56,58")
                        //.second("*/2")
                        .hour("0")
                        .minute("0")
                        .second("0")

                , config
        );

    }

    @Timeout
    @PermitAll
    public void performance(Timer timer) {

        InputStream inputStream = DownloadController.class.getResourceAsStream("/register.xml");

        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        StringBuffer result = new StringBuffer();
        try {
            while ((line = br.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String xml = result.toString();


        JAXBContext context = null;
        Unmarshaller unmarshaller = null;


        try {
            context = JAXBContext.newInstance(Sicks.class);
            unmarshaller = context.createUnmarshaller();

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        StringReader reader = new StringReader(xml);
        try {
            Sicks sicks = (Sicks) unmarshaller.unmarshal(reader);
            if (sicks.getSicks() != null) {
                for (Sick sick : sicks.getSicks()) {
                    sickRepository.save(sick);
                }
            }

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        //info(timer);

    }


    private void info(Timer timer) {
        System.out.println("____________________________________________");
        System.out.println("Timer Service : " + timer.getInfo());
        System.out.println("Current Time : " + new Date());
        System.out.println("Next Timeout : " + timer.getNextTimeout());
        System.out.println("Time Remaining : " + timer.getTimeRemaining());
        System.out.println("____________________________________________");

    }
}
