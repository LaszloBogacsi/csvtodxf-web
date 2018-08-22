package com.laszlobogacsi.csvtodxfwebservice.resources;

import org.junit.Test;

public class JobResponseTest {

    @Test
    public void name() {
        JobResponse response = new JobResponse("123345");
        System.out.println("response = " + response);
    }
}