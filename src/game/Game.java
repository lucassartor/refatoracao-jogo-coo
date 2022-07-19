package game;

import entities.Player;

import java.awt.*;
import java.util.ArrayList;

public class Game {
    private static boolean running = false;
    /* variáveis usadas no controle de tempo efetuado no main loop */
    private static long delta;
    private static long currentTime;

    public static void init() {
        running = true;
        currentTime = System.currentTimeMillis();
        GameLib.initGraphics();
        delta = System.currentTimeMillis() - currentTime;
    }

    public static void updateTimers() {
        delta = System.currentTimeMillis() - currentTime;
        currentTime = System.currentTimeMillis();
    }

    // TODO: Arrumar isso
    public static void initBackgrounds() {
        ArrayList<Background> backgrounds = new ArrayList<>();
        backgrounds.add(new Background(0.0045, Color.DARK_GRAY, 2, 2));
        backgrounds.add(new Background(0.0070, Color.GRAY, 3, 3));
        for (Background background: backgrounds) {
            background.initBackground();
        }
    }

    public static boolean isRunning() {
        return running;
    }

    public static long getDelta() {
        return delta;
    }

    private void setDelta(long delta) {
        this.delta = delta;
    }

    public static long getCurrentTime() {
        return currentTime;
    }

    private void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public static void checkPlayerInputs(Player player) {
        if(player.getState().isActive()) {
            if(GameLib.iskeyPressed(GameLib.KEY_UP)) player.setY(player.getY() - delta * player.getVY());
            if(GameLib.iskeyPressed(GameLib.KEY_DOWN)) player.setY(player.getY() + delta * player.getVY());
            if(GameLib.iskeyPressed(GameLib.KEY_LEFT)) player.setX(player.getX() - delta * player.getVX());
            if(GameLib.iskeyPressed(GameLib.KEY_RIGHT)) player.setX(player.getX() + delta * player.getVX());

            if(GameLib.iskeyPressed(GameLib.KEY_CONTROL)) {
                if(currentTime > player.getNextShot()) {
                    player.addProjectile();
                }
            }
        }
        if(GameLib.iskeyPressed(GameLib.KEY_ESCAPE)) running = false;

        /* Verificando se coordenadas do player ainda estão dentro */
        /* da tela de jogo após processar entrada do usuário.      */
        if(player.getX() < 0.0) player.setX(0.0);
        if(player.getX() >= GameLib.WIDTH) player.setX(GameLib.WIDTH - 1);
        if(player.getY() < 25.0) player.setY(25.0);
        if(player.getY() >= GameLib.HEIGHT) player.setY(GameLib.WIDTH - 1);
    }

    public static void busyWait(long time) {
        while(System.currentTimeMillis() < time) Thread.yield();
    }
}
