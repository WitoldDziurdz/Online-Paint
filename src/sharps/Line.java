package sharps;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Line extends Sharp{
    private double startX;
    private double startY;
    private double finishX;
    private double finishY;

    public Line(Color color, double sizeLine, double startX, double startY,double finishX, double finishY) {
        super(color, sizeLine);
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
}
