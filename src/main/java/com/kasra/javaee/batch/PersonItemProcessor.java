package com.kasra.javaee.batch;

import com.kasra.javaee.model.Person;

import javax.batch.api.chunk.ItemProcessor;
import javax.inject.Named;
import java.util.StringTokenizer;

/**
 * Created by kasra.haghpanah on 13/02/2017.
 */
@Named
public class PersonItemProcessor implements ItemProcessor {
    public Object processItem(Object object) throws Exception {
        String line = (String) object;
        StringTokenizer tokenizer = new StringTokenizer(line, ",");
        Long id = Long.parseLong(tokenizer.nextToken());
        String firstName = tokenizer.nextToken();
        String lastName = tokenizer.nextToken();
        return new Person(id, firstName, lastName);
    }
}
