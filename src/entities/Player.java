package entities;

import game.Game;
import game.GameLib;

import java.awt.*;

public class Player extends Shooter{

    public Player() {
        super(Color.BLUE, 12.0);
        this.setX((double) GameLib.WIDTH / 2);
        this.setY(GameLib.HEIGHT * 0.90);
        this.setVX(0.25);
        this.setVY(0.25);
        this.setNextShot(Game.getCurrentTime());
    }

    public void draw() {
        if(this.getState().isExploding())
            this.explode();
        else {
            GameLib.setColor(this.getColor());
            GameLib.drawPlayer(this.getX(), this.getY(), this.getRadius());
        }
    }

    public void addProjectile() {
        this.projectiles.add(new Projectile(this.getX(), this.getY() - 2 * this.getRadius(), 0.0, -1.0, 0.0));
        this.setNextShot(Game.getCurrentTime() + 100);
    }

    @Override
    public void drawProjectiles() {
        for (Projectile projectile : this.projectiles) {
            if(projectile.getState().isActive()) {
                GameLib.setColor(Color.GREEN);
                GameLib.drawLine(projectile.getX(), projectile.getY() - 5, projectile.getX(), projectile.getY() + 5);
                GameLib.drawLine(projectile.getX() - 1, projectile.getY() - 3, projectile.getX() - 1, projectile.getY() + 3);
                GameLib.drawLine(projectile.getX() + 1, projectile.getY() - 3, projectile.getX() + 1, projectile.getY() + 3);
            }
        }
    }

    @Override
    public void checkProjectilesState() {
        for (Projectile projectile : this.projectiles) {
            if (projectile.getState().isActive()) {
                if (projectile.getY() < 0) {
                    projectile.setState(State.INACTIVE);
                    //player.projectiles.remove(projectile);
                }
                else {
                    projectile.setX(projectile.getX() + (projectile.getVX() * Game.getDelta()));
                    projectile.setY(projectile.getY() + (projectile.getVY() * Game.getDelta()));
                }
            }
        }
    }
}
