package com.kasra.javaee.batch;

import javax.batch.api.partition.PartitionMapper;
import javax.batch.api.partition.PartitionPlan;
import javax.batch.api.partition.PartitionPlanImpl;
import javax.inject.Named;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by kasra.haghpanah on 13/02/2017.
 */
// just for mapper partition
@Named
public class FilePartitionMapper implements PartitionMapper {

    static String directory;

    public PartitionPlan mapPartitions() throws Exception {
        return new PartitionPlanImpl() {
            @Override
            public int getPartitions() {
                try {
                    return getCsvFileCount();
                } catch (Exception e) {
                    return 0;
                }
            }

            public int getCsvFileCount() throws Exception {

                File file = new File(getDirectory());
                File[] files = file.listFiles(new FileFilter() {
                    public boolean accept(File pathname) {
                        if (pathname.getName().endsWith(".csv")) {
                            return true;
                        }
                        return false;
                    }
                });
                return files.length;
            }

            public String getCsvFile(int index) throws Exception {

                File file = new File(getDirectory());
                File[] files = file.listFiles(new FileFilter() {
                    public boolean accept(File pathname) {
                        if (pathname.getName().endsWith(".csv")) {
                            return true;
                        }
                        return false;
                    }
                });
                return files[index].getAbsolutePath();
            }

            @Override
            public Properties[] getPartitionProperties() {


                /* Populate a Properties array. Each Properties element
                 * in the array corresponds to a partition. */
                Properties[] props = new Properties[getPartitions()];

                for (int i = 0; i < getPartitions(); i++) {
                    props[i] = new Properties();
                    try {
                        props[i].put("input", getCsvFile(i));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return props;
            }
        };
    }


    public String getDirectory(){
        if (directory == null) {
            directory = loadConfig().getProperty("context-root");
            while (directory.indexOf('\\') > -1) {
                directory = directory.replace('\\', '/');
            }
            directory = directory + "/src/main/resources/META-INF";
        }

        return directory;
    }

    private Properties loadConfig() {

        InputStream inputStream = this.getClass().getResourceAsStream("/config.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties;
    }
}
