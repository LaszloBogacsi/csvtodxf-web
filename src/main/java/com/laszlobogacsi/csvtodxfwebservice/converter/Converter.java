package com.laszlobogacsi.csvtodxfwebservice.converter;

import com.laszlobogacsi.csvtodxfwebservice.DrawingConfig;
import com.laszlobogacsi.csvtodxfwebservice.report.ConversionReport;

public interface Converter {
    ConversionReport convert(DrawingConfig config) throws Exception;

}
