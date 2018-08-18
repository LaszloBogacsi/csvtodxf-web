package com.laszlobogacsi.csvtodxfwebservice.converter;

import com.laszlobogacsi.csvtodxfwebservice.DrawingConfig;

public interface Converter {
    void convert(DrawingConfig config) throws Exception;

}
