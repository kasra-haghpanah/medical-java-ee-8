package com.kasra.javaee.websocket.message;

/**
 * Created by kasra.haghpanah on 19/12/2016.
 */

public class SketchMessage {

    private Double x;
    private Double y;
    private Double size;
    private String color;

    public SketchMessage() {
    }

    public SketchMessage(Double x, Double y, Double size, String color) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }


    @Override
    public String toString() {
        return "{"
                + "\"x\":\"" + x + "\""
                + ",\"y\":\"" + y + "\""
                + ",\"size\":\"" + size + "\""
                + ",\"color\":\"" + color + "\""
                + "}";
    }
}
