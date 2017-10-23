package sharps;

import javafx.scene.paint.Color;

public abstract class Point extends Sharp {
    protected double red;
    protected double green;
    protected double blue;
    protected double opacity;
    protected double sizeLine;
    protected double startX;
    protected double startY;


    public Point(Color color, double sizeLine, double startX, double startY){
        this.sizeLine = sizeLine;
        this.red = color.getRed();
        this.green = color.getGreen();
        this.blue = color.getBlue();
        this.opacity = color.getOpacity();
        this.startX = startX;
        this.startY = startY;
    }
}
