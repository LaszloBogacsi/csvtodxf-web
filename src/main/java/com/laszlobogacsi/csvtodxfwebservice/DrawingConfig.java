package com.laszlobogacsi.csvtodxfwebservice;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class DrawingConfig {
    private UUID drawingId;
    private String separator;
    private double textHeight;
    private boolean doPrintId;
    private boolean doPrintCoords;
    private boolean doPrintCode;
    private boolean doPrintHeight;
    private boolean is3D;
    private boolean isLayerByCode;

}
