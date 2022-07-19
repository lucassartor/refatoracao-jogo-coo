package game;

import java.awt.*;

public class Background {
    private final double speed;
    private double count;
    private final Color color;
    private final int width;
    private final int height;

    public Background(double speed, Color color, int width, int height) {
        this.speed = speed;
        this.count += speed * Game.getDelta();
        this.color = color;
        this.width = width;
        this.height = height;
    }
    public void initBackground() {
        GameLib.setColor(color);
        this.count += this.speed * Game.getDelta();
        for(int i = 0; i < 50; i++){
            GameLib.fillRect(Math.random() * GameLib.WIDTH, (Math.random() * GameLib.HEIGHT + this.count) % GameLib.HEIGHT, this.width, this.height);
        }
    }
}
