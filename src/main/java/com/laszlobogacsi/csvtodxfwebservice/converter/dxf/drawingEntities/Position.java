package com.laszlobogacsi.csvtodxfwebservice.converter.dxf.drawingEntities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Position {
    private double e;
    private double n;
    private double h;
}