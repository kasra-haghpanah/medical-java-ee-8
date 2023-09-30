package com.kasra.javaee.batch;

import java.io.Serializable;

/**
 * Created by kasra.haghpanah on 13/02/2017.
 */

public class LineCheckpoint implements Serializable{
    private int lineNumber =0;

    public void increase(){
        lineNumber++;
    }

    public int getLineNumber(){
        return lineNumber;
    }
}
