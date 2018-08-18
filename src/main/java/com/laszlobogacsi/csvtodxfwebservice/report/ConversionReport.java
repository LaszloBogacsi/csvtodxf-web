package com.laszlobogacsi.csvtodxfwebservice.report;

public class ConversionReport {
    private int numberOfLinesConverted;
    private long durationInMillies;
    private double fileSize;

    public ConversionReport() { }

    public long getNumberOfLinesConverted() {
        return numberOfLinesConverted;
    }

    public void setNumberOfLinesConverted(int numberOfLinesConverted) {
        this.numberOfLinesConverted = numberOfLinesConverted;
    }

    public long getDurationInMillies() {
        return durationInMillies;
    }

    public void setDurationInMillies(long durationInMillies) {
        this.durationInMillies = durationInMillies;
    }

    public double getFileSize() {
        return fileSize;
    }

    public void setFileSize(double fileSize) {
        this.fileSize = fileSize;
    }
}
