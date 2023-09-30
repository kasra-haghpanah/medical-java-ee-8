package com.kasra.javaee.batch;

import javax.batch.api.BatchProperty;
import javax.batch.api.chunk.ItemReader;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.*;
import java.util.Properties;

/**
 * Created by kasra.haghpanah on 13/02/2017.
 */
@Named
public class PersonItemReaderSimple implements ItemReader {

    BufferedReader bufferedReader;
    LineCheckpoint lineCheckpoint;

    @Inject//get detail job
            JobContext jobContext;

    @Inject
    @BatchProperty
    String inputFile;

    public void open(Serializable chpt) throws Exception {
        if (chpt == null) {
            lineCheckpoint = new LineCheckpoint();
            chpt = lineCheckpoint;
        } else {
            lineCheckpoint = (LineCheckpoint) chpt;
        }

        //bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));
        String fileName = "";

        InputStream inputStream = this.getClass().getResourceAsStream("/config.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }


        String directory = properties.getProperty("context-root");
        while (directory.indexOf('\\') > -1) {
            directory = directory.replace('\\', '/');
        }
        fileName = directory + "/src/main/resources/META-INF/person.csv";


        bufferedReader = new BufferedReader(new FileReader(fileName));
        //bufferedReader = new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("person.csv")));

        for (int i = 0; i < lineCheckpoint.getLineNumber(); i++) {
            bufferedReader.readLine();
        }
    }

    public void close() throws Exception {
        bufferedReader.close();
    }

    public Object readItem() throws Exception {
        return bufferedReader.readLine();
    }

    public Serializable checkpointInfo() throws Exception {
        return lineCheckpoint;
    }
}
