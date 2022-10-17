package entities.shooters.player;

import entities.Entity;
import entities.projectiles.Projectile;
import entities.shooters.Shooter;
import entities.State;
import entities.powerUps.PowerUp;
import game.*;

import java.awt.*;

public class Player extends Shooter {

    private boolean poweredUp = false;
    private long powerUpTimer;

    public Player() {
        super(Color.BLUE, 12.0);
        this.setX((double) GameLib.WIDTH / 2);
        this.setY(GameLib.HEIGHT * 0.90);
        this.setVX(0.25);
        this.setVY(0.25);
        this.setNextShot(Game.getCurrentTime());
    }

    public void draw() {
        if(Game.getCurrentTime() > this.getPowerUpTimer())
            this.deactivatePowerUp();
        if (this.getState().isExploding())
            this.explode();
        else {
            GameLib.setColor(this.getColor());
            GameLib.drawPlayer(this.getX(), this.getY(), this.getRadius());
        }
    }

    public void addProjectile() {
        if (isPoweredUp())
            this.projectiles.add(new Projectile(this.getX(), this.getY() - 2 * this.getRadius(), 0.0, -3.0, 0.0));
        else
            this.projectiles.add(new Projectile(this.getX(), this.getY() - 2 * this.getRadius(), 0.0, -1.0, 0.0));

        this.setNextShot(Game.getCurrentTime() + 100);
    }

    @Override
    public void checkColissions(Entity attacker) {
        double dx = attacker.getX() - this.getX();
        double dy = attacker.getY() - this.getY();
        double dist = Math.sqrt(dx * dx + dy * dy);

        if (dist < (this.getRadius() + attacker.getRadius()) * 0.8) {
            this.setState(State.EXPLODING);
            this.setExplosionStart(Game.getCurrentTime());
            this.setExplosionEnd(Game.getCurrentTime() + 2000);
        }
    }

    public void checkPowerUpColissions(PowerUp powerUp) {
        double dx = powerUp.getX() - this.getX();
        double dy = powerUp.getY() - this.getY();
        double dist = Math.sqrt(dx * dx + dy * dy);

        if (dist < (this.getRadius() + powerUp.getRadius()) * 0.8)
            this.activatePowerUp(powerUp);
    }

    public boolean isPoweredUp() {
        return poweredUp;
    }

    private void activatePowerUp(PowerUp powerUp) {
        powerUp.setState(State.INACTIVE);
        this.setPowerUpTimer(Game.getCurrentTime() + 3000);
        this.poweredUp = true;
        this.setRadius(5.0);
    }

    private void deactivatePowerUp() {
        this.poweredUp = false;
        this.setRadius(12.0);
    }

    @Override
    public void drawProjectiles() {
        for (Projectile projectile : this.projectiles) {
            if (projectile.getState().isActive()) {
                if(isPoweredUp()) {
                    GameLib.setColor(new Color(255,20,147));
                } else {
                    GameLib.setColor(Color.GREEN);
                }
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
                } else {
                    projectile.setX(projectile.getX() + (projectile.getVX() * Game.getDelta()));
                    projectile.setY(projectile.getY() + (projectile.getVY() * Game.getDelta()));
                }
            }
        }
        this.projectiles.removeIf(projectile -> !projectile.getState().isActive());
    }

    public long getPowerUpTimer() {
        return this.powerUpTimer;
    }

    public void setPowerUpTimer(long powerUpTimer) {
        this.powerUpTimer = powerUpTimer;
    }
}
