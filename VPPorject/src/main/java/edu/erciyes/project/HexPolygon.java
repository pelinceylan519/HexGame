package edu.erciyes.project;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class HexPolygon extends Polygon {
    public HexPolygon(double radius) {

        super();
        for (int i = 0; i < 6; i++) {
            double angle = Math.toRadians(60 * i);
            double x = radius * Math.cos(angle) +250;
            double y = radius * Math.sin(angle)+100;
            getPoints().addAll(x, y);
        }
        setFill(Color.LIGHTGRAY);
        setStroke(Color.BLACK);
        setStrokeWidth(5);
    }

}