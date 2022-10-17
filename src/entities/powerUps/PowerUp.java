package entities.powerUps;

import entities.Entity;
import entities.State;
import game.Game;
import game.GameLib;

import java.awt.*;

public class PowerUp extends Entity {
    private final Color color;

    public PowerUp() {
        super(State.ACTIVE, 5.0);
        this.color = new Color(	255,140,0);
        this.setX(Math.random() * GameLib.WIDTH);
        this.setY(Math.random() * GameLib.HEIGHT);
    }

    public void draw() {
        if(this.getState().isActive()) {
            GameLib.setColor(this.color);
            GameLib.drawCircle(this.getX(), this.getY(), this.getRadius());
        }
    }

    public void checkState() {
        if (this.getState().isActive()) {
            /* verificando se powerUp saiu da tela */
            if (this.getY() > GameLib.HEIGHT + 10) {
                this.setState(State.INACTIVE);
            } else {
                this.setY(this.getY() + this.getVY() * Game.getDelta() * (-1.0));
            }
        }
    }
}
