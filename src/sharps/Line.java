package sharps;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Line extends Sharp{
    protected double red;
    protected double green;
    protected double blue;
    protected double opacity;
    private double startX;
    private double startY;
    private double finishX;
    private double finishY;
    protected double sizeLine;


    public Line(Color color, double sizeLine, double startX, double startY,double finishX, double finishY) {
        this.sizeLine = sizeLine;
        this.red = color.getRed();
        this.green = color.getGreen();
        this.blue = color.getBlue();
        this.opacity = color.getOpacity();
        this.startX = startX;
        this.startY = startY;
        this.finishY = finishY;
        this.finishX = finishX;
    }

    @Override
    public void draw(GraphicsContext gc) {
        Color color = getColor();
        gc.setStroke(color);
        gc.setLineWidth(sizeLine);
        gc.strokeLine(startX, startY, finishX,finishY);
    }
    protected Color getColor(){
        return  new Color(red,green,blue,opacity);
    }
}
