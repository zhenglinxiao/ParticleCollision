package particlecollision;

import javafx.scene.shape.Circle;

public class GameObject {

    protected Circle circle;
    private Vector2D position;
    private Vector2D velocity;
    private Vector2D acceleration;

    public GameObject(Vector2D position, Vector2D velocity, Vector2D acceleration, double radius) {
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;

        circle = new Circle(0, 0, radius);
        circle.setCenterX(position.getX());
        circle.setCenterY(position.getY());
    }

    public Circle getCircle() {
        return circle;
    }

    public void removeFromScreen(int opacity) {
        circle.setOpacity(opacity);
    }

    public Vector2D getPosition() {
        return position;
    }

    public Vector2D getVelocity() {
        return velocity;
    }

    public Vector2D getAcceleration() {
        return acceleration;
    }

    public void setPosition(double x, double y) {
        this.position = new Vector2D(x, y);
        circle.setCenterX(position.getX());
        circle.setCenterY(position.getY());
    }

    public void setPosition(Vector2D vect) {
        this.position = vect;
        circle.setCenterX(position.getX());
        circle.setCenterY(position.getY());
    }

    public void setVelocity(double x, double y) {
        this.velocity = new Vector2D(x, y);
    }

    public void setVelocity(Vector2D vect) {
        this.velocity = vect;
    }

    public void setAcceleration(double x, double y) {
        this.acceleration = new Vector2D(x, y);
    }
    
    public void setAcceleration(Vector2D vect){
        this.acceleration = vect;
    }

}
