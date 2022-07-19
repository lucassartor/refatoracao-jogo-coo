package entities.enemies;

import entities.Projectile;
import game.Game;
import game.GameLib;

import java.awt.*;

public class Enemy2 extends Enemy {
    private double spawnX;
    private int count;

    public Enemy2() {
        super(Color.MAGENTA,12.0,Game.getCurrentTime() + 7000);
    }

    public void draw() {
        GameLib.setColor(this.getColor());
        GameLib.drawDiamond(this.getX(), this.getY(), this.getRadius());
    }

    public void addProjectile() {
        this.projectiles.add(new Projectile(this.getX(), this.getY() - 2 * this.getRadius(), 0.0, -1.0, 0.0));
        this.setNextShot(Game.getCurrentTime() + 100);
    }
}
