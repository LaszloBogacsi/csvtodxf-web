package com.laszlobogacsi.csvtodxfwebservice.converter.dxf;

public class StringTemplate {

    public static String getHeader() {
        return " 0\nSECTION\n 2\nENTITIES\n";
    }

    public static String getFooter() {
        return "\n 0\nENDSEC\n 0\nEOF";
    }

    public static String getPointStringTemplate() {
        return  " 0\nPOINT\n 8\n{layerName}\n 10\n{positionE}\n 20\n{positionN}\n 30\n{positionH}";
    }

    public static String getSingleTextStringTemplate() {
        return " 0\nTEXT\n 8\n{layerName}\n 10\n{positionE}\n 20\n{positionN}\n 30\n{positionH}\n 40\n{textHeight}\n 1\n{text}";
    }
}