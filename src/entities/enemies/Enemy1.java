package entities.enemies;

import entities.Projectile;
import entities.State;
import game.Game;
import game.GameLib;

import java.awt.*;

public class Enemy1 extends Enemy {

    public Enemy1() {
        super(Color.CYAN, 9.0,Game.getCurrentTime() + 2000);
        this.setX(Math.random() * GameLib.WIDTH);
        this.setY(Math.random() * GameLib.HEIGHT);
        this.setVX(0.25);
        this.setVY(0.25);
        this.setNextShot(Game.getCurrentTime());
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
                //enemy1List.remove(enemy);
            }
        }
        if (this.getState().isActive()) {
            /* verificando se inimigo saiu da tela */
            if (this.getY() > GameLib.HEIGHT + 10) {
                this.setState(State.INACTIVE);
                //enemy1List.remove(enemy);
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
