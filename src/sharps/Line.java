package sharps;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Line extends Point{
    protected double finishX;
    protected double finishY;

    public Line(Color color, double sizeLine, double startX, double startY,double finishX, double finishY) {
        super(color,sizeLine,startX,startY);
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
