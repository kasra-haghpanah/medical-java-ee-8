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
public class PersonItemReader implements ItemReader {

    BufferedReader bufferedReader;
    LineCheckpoint lineCheckpoint;

    @Inject
    JobContext jobContext;

    @Inject
    @BatchProperty
    String inputFile;
    //relate the property in reader  , FirstJobChunkWithPlanPartition.xml


    public void open(Serializable ckpt) throws Exception {

        if (ckpt == null) {
            lineCheckpoint = new LineCheckpoint();
        } else {
            lineCheckpoint = (LineCheckpoint) ckpt;
        }

        //String fileName = jobContext.getProperties().getProperty(inputFile);


        String fileName = inputFile;

        if (inputFile.trim().indexOf("person") == 0) {
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
            fileName = directory + "/src/main/resources/META-INF/" + inputFile;
        }

        bufferedReader = new BufferedReader(new FileReader(fileName));
        // bufferedReader = new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(inputFile)));


        for (long i = 0; i < lineCheckpoint.getLineNumber(); i++) {
            bufferedReader.readLine();
        }

        //bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));


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
