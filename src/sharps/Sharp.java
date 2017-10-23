package sharps;

import javafx.scene.canvas.GraphicsContext;

import java.io.Serializable;

public abstract class Sharp implements Serializable{

    public abstract void draw(GraphicsContext gc);

}
