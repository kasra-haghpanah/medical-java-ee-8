package com.kasra.javaee.batch;

import javax.batch.api.AbstractBatchlet;
import javax.batch.runtime.BatchStatus;
import javax.inject.Named;

/**
 * Created by kasra.haghpanah on 13/02/2017.
 */
@Named
public class FirstBatchlet extends AbstractBatchlet {
    public String process() throws Exception {
        return BatchStatus.COMPLETED.toString();
    }
}
