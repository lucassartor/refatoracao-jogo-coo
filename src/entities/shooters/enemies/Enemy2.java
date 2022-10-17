package entities.shooters.enemies;

import entities.State;
import entities.projectiles.Projectile;
import game.*;

import java.awt.*;

public class Enemy2 extends Enemy {
    private double spawnX;

    public Enemy2() {
        super(Color.MAGENTA,12.0,Game.getCurrentTime() + 7000);
        this.spawnX = GameLib.WIDTH * 0.20;

        this.setX(this.getSpawnX());
        this.setY(-10);
        this.setVX(0.42);
        this.setVY(0.42);
        this.setAngle((3 * Math.PI) / 2);
    }

    public double getSpawnX() {
        return this.spawnX;
    }

    public void setSpawnX(double spawnX) {
        this.spawnX = spawnX;
    }

    @Override
    public void draw() {
        if(this.getState().isExploding())
            this.explode();
        else {
            GameLib.setColor(this.getColor());
            GameLib.drawDiamond(this.getX(), this.getY(), this.getRadius());
        }
    }

    @Override
    public void addProjectile() {
        double [] angles = { Math.PI/2 + Math.PI/8, Math.PI/2, Math.PI/2 - Math.PI/8 };
        for (double angle : angles) {
            double a = angle + Math.random() * Math.PI / 6 - Math.PI / 12;
            double vx = Math.cos(a);
            double vy = Math.sin(a);
            this.projectiles.add(new Projectile(this.getX(), this.getY(), vx * 1.1, vy * 1.1, 2.0));
        }
    }

    @Override
    public void checkState(double playerX) {
        if (this.getState().isExploding()) {
            if (Game.getCurrentTime() > this.getExplosionEnd()) {
                this.setState(State.INACTIVE);
            }
        }
        if (this.getState().isActive()) {
            if(this.getX() < -10 || this.getX() > GameLib.WIDTH + 10) {
                this.setState(State.INACTIVE);
            } else {
                boolean shootNow = false;
                double previousY = this.getY();

                this.setX(this.getX() + (this.getVX() * Math.cos(this.getAngle()) * Game.getDelta()));
                this.setY(this.getY() + (this.getVY() * Math.sin(this.getAngle()) * Game.getDelta() * (-1.0)));
                this.setAngle(this.getAngle() + (this.getRV() * Game.getDelta()));

                double threshold = GameLib.HEIGHT * 0.30;

                if(previousY < threshold && this.getY() >= threshold) {
                    if(this.getX() < GameLib.WIDTH / 2)
                        this.setRV(0.003);
                    else
                        this.setRV(-0.003);
                }

                if(this.getRV() > 0 && Math.abs(this.getAngle() - 3 * Math.PI) < 0.05) {
                    this.setRV(0.0);
                    this.setAngle(3 * Math.PI);
                    shootNow = true;
                }

                if(this.getRV() < 0 && Math.abs(this.getAngle()) < 0.05) {
                    this.setRV(0.0);
                    this.setAngle(0);
                    shootNow = true;
                }

                if(shootNow){
                    this.addProjectile();
                }
            }
        }
    }
}
