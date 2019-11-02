import javafx.scene.shape.Circle;

public class GameObject {


	protected Circle circle;
	private Vector2D position;
	private Vector2D velocity;
    private Vector2D acceleration;
    private boolean stop = false;
	
	public GameObject(Vector2D position, Vector2D velocity, Vector2D acceleration, double radius)
    {
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration; 
        
        circle = new Circle(0, 0, radius);
        circle.setCenterX(position.getX());
        circle.setCenterY(position.getY());
    }
	
	public Circle getCricle() {  
		return circle;
		}
	
	
	public void removeFromScreen(int opacity) {
		circle.setOpacity(opacity);
		stop = true;
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
	
	
	
	
	
	
	
}
