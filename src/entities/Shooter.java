package entities;

import game.Game;
import game.GameLib;

import java.awt.*;
import java.util.ArrayList;

public class Shooter extends Entity implements Animations {

    private double explosionStart;
    private double explosionEnd;
    public ArrayList<Projectile> projectiles = new ArrayList<>();
    private final Color color;
    private long nextShoot;

    public Shooter(Color color, double radius) {
        super(State.ACTIVE, radius);
        this.color = color;
    }

    @Override
    public double getExplosionStart() {
        return explosionStart;
    }

    @Override
    public double getExplosionEnd() {
        return explosionEnd;
    }

    public Color getColor() {
        return color;
    }

    public long getNextShot() {
        return this.nextShoot;
    }

    @Override
    public void setExplosionStart(double explosionStart) {
        this.explosionStart = explosionStart;
    }

    @Override
    public void setExplosionEnd(double explosionEnd) {
        this.explosionEnd = explosionEnd;
    }

    public void setNextShot(long nextShoot) {
        this.nextShoot = nextShoot;
    }

    @Override
    public void draw() {
        //Animations.super.draw();
    }

    @Override
    public void explode() {
        double alpha = (Game.getCurrentTime() - this.getExplosionStart()) / (this.getExplosionEnd() - this.getExplosionStart());
        GameLib.drawExplosion(this.getX(), this.getY(), alpha);
    }

    public void addProjectile() {}

    public void drawProjectiles() {}

    public void checkColissions(Entity attacker) {
        double dx = attacker.getX() - this.getX();
        double dy = attacker.getY() - this.getY();
        double dist = Math.sqrt(dx * dx + dy * dy);

        if(dist < (this.getRadius() + attacker.getRadius()) * 0.8) {
            this.setState(State.EXPLODING);
            this.setExplosionStart(Game.getCurrentTime());
            this.setExplosionEnd(Game.getCurrentTime() + 2000);
        }
    }

    public void checkProjectilesState() {}

    public void checkState(double x) {}
}
