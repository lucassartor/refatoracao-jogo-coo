package entities.shooters.enemies;

import entities.State;
import entities.projectiles.Projectile;
import entities.shooters.Shooter;
import game.*;

import java.awt.*;

public class Enemy extends Shooter {
    private double angle;
    private double RV;
    private long nextSpawnTime;

    public Enemy(Color color, double radius, long nextSpawnTime) {
        super(color, radius);
        this.RV = 0.0;
        this.nextSpawnTime = nextSpawnTime;
    }

    public double getAngle() {
        return this.angle;
    }

    public double getRV() {
        return RV;
    }

    public long getNextSpawnTime() {
        return nextSpawnTime;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void setRV(double RV) {
        this.RV = RV;
    }

    public void setNextSpawnTime(long nextSpawnTime) {
        this.nextSpawnTime = nextSpawnTime;
    }

    public void draw() {
    }

    public void addProjectile() {
    }

    @Override
    public void drawProjectiles() {
        for (Projectile projectile : this.projectiles) {
            if(projectile.getState().isActive()) {
                GameLib.setColor(Color.RED);
                GameLib.drawCircle(projectile.getX(), projectile.getY(), projectile.getRadius());
            }
        }
    }

    @Override
    public void checkProjectilesState() {
        for (Projectile projectile : this.projectiles) {
            if (projectile.getState().isActive()) {
                if (projectile.getY() > GameLib.HEIGHT) {
                    projectile.setState(State.INACTIVE);
                } else {
                    projectile.setX(projectile.getX() + (projectile.getVX() * Game.getDelta()));
                    projectile.setY(projectile.getY() + (projectile.getVY() * Game.getDelta()));
                }
            }
        }
        this.projectiles.removeIf(projectile -> !projectile.getState().isActive());
    }

    @Override
    public void checkState(double x) {}

    public boolean shouldBeCreated() {
        return Game.getCurrentTime() > this.nextSpawnTime;
    }
}
