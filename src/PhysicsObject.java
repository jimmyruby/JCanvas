import java.util.ArrayList;


public class PhysicsObject extends JCanvas{
	public double xAcceleration, yAcceleration, xVelocity, yVelocity, xPosition, yPosition, mass, width, height;
	public double xAccelerationNext, yAccelerationNext, xVelocityNext, yVelocityNext, xPositionNext, yPositionNext;
	public ArrayList<Force> forces = new ArrayList<Force>();
	public JImage image;
	public PhysicsObject(double xacceleration, double yacceleration, double xvelocity, double yvelocity, double xposition, double yposition){
		this.xAcceleration = xacceleration;
		this.yAcceleration = yacceleration;
		this.xVelocity = xvelocity;
		this.yVelocity = yvelocity;
		this.xPosition = xposition;
		this.yPosition = yposition;
		
		this.xAccelerationNext = xacceleration;
		this.yAccelerationNext = yacceleration;
		this.xVelocityNext = xvelocity;
		this.yVelocityNext = yvelocity;
		this.xPositionNext = xposition;
		this.yPositionNext = yposition;
		
		this.mass = 1;
	}
	public PhysicsObject(double xvelocity, double yvelocity, double xposition, double yposition){
		this.xAcceleration = 0;
		this.yAcceleration = 0;
		this.xVelocity = xvelocity;
		this.yVelocity = yvelocity;
		this.xPosition = xposition;
		this.yPosition = yposition;
		
		this.xAccelerationNext = 0;
		this.yAccelerationNext = 0;
		this.xVelocityNext = xvelocity;
		this.yVelocityNext = yvelocity;
		this.xPositionNext = xposition;
		this.yPositionNext = yposition;
		
		this.mass = 1;
	}
	public PhysicsObject(JImage image, double xacceleration, double yacceleration, double xvelocity, double yvelocity, double xposition, double yposition){
		this.xAcceleration = xacceleration;
		this.yAcceleration = yacceleration;
		this.xVelocity = xvelocity;
		this.yVelocity = yvelocity;
		this.xPosition = xposition;
		this.yPosition = yposition;
		
		this.xAccelerationNext = xacceleration;
		this.yAccelerationNext = yacceleration;
		this.xVelocityNext = xvelocity;
		this.yVelocityNext = yvelocity;
		this.xPositionNext = xposition;
		this.yPositionNext = yposition;
		
		this.mass = 1;
		this.image = image;
	}
	public PhysicsObject(JImage image, double xvelocity, double yvelocity, double xposition, double yposition){
		this.xAcceleration = 0;
		this.yAcceleration = 0;
		this.xVelocity = xvelocity;
		this.yVelocity = yvelocity;
		this.xPosition = xposition;
		this.yPosition = yposition;
		
		this.xAccelerationNext = 0;
		this.yAccelerationNext = 0;
		this.xVelocityNext = xvelocity;
		this.yVelocityNext = yvelocity;
		this.xPositionNext = xposition;
		this.yPositionNext = yposition;
		
		this.mass = 1;
		this.image = image;
	}
	public void setImage(JImage i){
		this.image = i;
		width = image.width;
		height = image.height;
	}
	public void setMass(double m){
		this.mass = m;
	}
	public void setGravity(double g){
		forces.add(new Force(g, 90));
	}
	public void addForce(double f, double t){
		forces.add(new Force(f, t));
	}
	public void addForceXY(double x, double y){
		forces.add(new Force(x, y, ""));
	}
	public void clearForces(){
		forces.clear();
	}
	public void loadNext(){
		//System.out.println(forces.size());
		yAccelerationNext = 0.0;
		xAccelerationNext = 0.0;
		for(Force f : forces){
			//System.out.println(f.y);
			if(Double.isNaN(f.y)){
				f.y = 0;
			}
			if(Double.isNaN(f.x)){
				f.x = 0;
			}
			if(mass > 0){
				yAccelerationNext += f.y/mass;
				xAccelerationNext += f.x/mass;
			}
		}
		//System.out.println(yAcceleration + " + " + yVelocity +" = " + (yAcceleration + yVelocity));
		yVelocityNext += yAccelerationNext;
		xVelocityNext += xAccelerationNext;
		yPositionNext += yVelocityNext;
		xPositionNext += xVelocityNext;
	}
	public void updateFrame(){
		xAcceleration = xAccelerationNext;
		yAcceleration = yAccelerationNext;
		xVelocity = xVelocityNext;
		yVelocity = yVelocityNext;
		xPosition = xPositionNext;
		yPosition = yPositionNext;
	}
	public void fullUpdate(){
		loadNext();
		updateFrame();
	}
	public void displayImage(){
		image(image, (int)xPosition, (int)yPosition);
	}
	public boolean contact(PhysicsObject o){
		if(((xPosition <= o.xPosition + o.image.width && xPosition >= o.xPosition) || (xPosition + image.width >= o.xPosition && xPosition + image.width <= o.xPosition + o.image.width)) 
				&& ((yPosition <= o.yPosition + o.image.height && yPosition >= o.yPosition) || (yPosition + image.height >= o.yPosition && yPosition + image.height <= o.yPosition + o.image.height))){
			return true;
		}
		return false;
	}
	class Force {
		double magnitude, theta, x, y;
		Force(double magnitude, double theta){
			this.magnitude = magnitude;
			this.theta = theta;
			this.x = Math.cos(Math.toRadians(theta)) * magnitude;
			this.y = Math.sin(Math.toRadians(theta)) * magnitude;
		}
		Force(double x, double y, String s){
			this.x = x;
			this.y = y;
			this.magnitude = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		}
	}
}
