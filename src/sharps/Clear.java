package sharps;


import javafx.scene.canvas.GraphicsContext;

public class Clear extends Sharp {
    @Override
    public void draw(GraphicsContext gc) {
         double height =  gc.getCanvas().getHeight();
         double weight = gc.getCanvas().getWidth();
         gc.clearRect(0,0,weight,height);
    }
}
