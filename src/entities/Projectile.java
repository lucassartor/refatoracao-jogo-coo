package entities;

public class Projectile extends Entity {
    public Projectile(double X, double Y, double VX, double VY, double radius) {
        super(State.ACTIVE, radius);
        this.setX(X);
        this.setY(Y);
        this.setVX(VX);
        this.setVY(VY);
    }
}
