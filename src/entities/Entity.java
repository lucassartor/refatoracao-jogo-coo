package entities;

public class Entity implements Coordinate {
    public double X;
    private double Y;
    private double VX;
    private double VY;
    private State state;
    private double radius;

    public Entity(State state, double radius) {
        this.state = state;
        this.radius = radius;
    }

    @Override
    public double getX() {
        return X;
    }

    public void setX(double X) {
        this.X = X;
    }

    @Override
    public double getY() {
        return Y;
    }

    public void setY(double Y) {
        this.Y = Y;
    }

    @Override
    public double getVX() {
        return VX;
    }

    @Override
    public void setVX(double VX) {
        this.VX = VX;
    }

    @Override
    public double getVY() {
        return VY;
    }

    @Override
    public void setVY(double VY) {
        this.VY = VY;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public double getRadius() {
        return radius;
    }

    protected void setRadius(double radius) {
        this.radius = radius;
    }
}
