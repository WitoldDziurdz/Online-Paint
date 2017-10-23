package sharps;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.Serializable;

public abstract class Sharp implements Serializable{
    protected double sizeLine;
    protected double red;
    protected double green;
    protected double blue;
    protected double opacity;

    public Sharp(Color color, double sizeLine) {
        this.sizeLine = sizeLine;
        this.red = color.getRed();
        this.green = color.getGreen();
        this.blue = color.getBlue();
        this.opacity = color.getOpacity();
    }

    public abstract void draw(GraphicsContext gc);

    protected Color getColor(){
        return  new Color(red,green,blue,opacity);
    }
}
