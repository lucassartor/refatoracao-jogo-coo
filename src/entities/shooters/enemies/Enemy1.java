package entities.shooters.enemies;

import entities.State;
import entities.projectiles.Projectile;
import game.*;

import java.awt.*;

public class Enemy1 extends Enemy {

    public Enemy1() {
        super(Color.CYAN, 9.0,Game.getCurrentTime() + 2000);

        this.setX(Math.random() * (GameLib.WIDTH - 20.0) + 10.0);
        this.setY(-10.0);
        this.setVX(0.20 + Math.random() * 0.15);
        this.setVY(0.20 + Math.random() * 0.15);
        this.setAngle((3 * Math.PI) / 2);
        this.setNextShot(Game.getCurrentTime() + 500);
        this.setNextSpawnTime(Game.getCurrentTime() + 500);
    }

    @Override
    public void draw() {
        if(this.getState().isExploding())
            this.explode();
        else {
            GameLib.setColor(this.getColor());
            GameLib.drawCircle(this.getX(), this.getY(), this.getRadius());
        }
    }

    @Override
    public void addProjectile() {
        this.projectiles.add(new Projectile(this.getX(), this.getY(), Math.cos(this.getAngle()), Math.sin(this.getAngle() * 0.45), 2.0));
        this.setNextShot((long) (Game.getCurrentTime() + 200 + Math.random() * 500));
    }

    @Override
    public void checkState(double playerX) {
        if (this.getState().isExploding()) {
            if (Game.getCurrentTime() > this.getExplosionEnd()) {
                this.setState(State.INACTIVE);
            }
        }
        if (this.getState().isActive()) {
            /* verificando se inimigo saiu da tela */
            if (this.getY() > GameLib.HEIGHT + 10) {
                this.setState(State.INACTIVE);
            } else {
                this.setX(this.getX() + this.getVX() * Math.cos(this.getAngle()) * Game.getDelta());
                this.setY(this.getY() + this.getVY() * Math.sin(this.getAngle()) * Game.getDelta() * (-1.0));
                this.setAngle(this.getAngle() + this.getRV() * Game.getDelta());

                if (Game.getCurrentTime() > this.getNextShot() && this.getY() < playerX) {
                    this.addProjectile();
                }
            }
        }
    }
}
