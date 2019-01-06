package com.laszlobogacsi.csvtodxfwebservice.resources;

public enum JobResult {
    SUCCESS("success"),
    IN_PROGRESS("in progress"),
    CONVERSION_ERROR("conversion error");


    JobResult(String result) { }
}
