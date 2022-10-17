package game;

import entities.projectiles.Projectile;
import entities.shooters.player.Player;
import entities.State;
import entities.backgrounds.*;
import entities.powerUps.PowerUp;
import entities.shooters.enemies.*;

import java.awt.*;
import java.util.ArrayList;

public class Game {
    private static boolean running = false;
    private static long delta;
    private static long currentTime;
    private static final Player player = new Player();
    private static final ArrayList<Background> backgrounds = new ArrayList<>();
    private static final ArrayList<Enemy> enemies = new ArrayList<>();
    private static final ArrayList<PowerUp> powerUps = new ArrayList<>();

    private static long nextEnemy1;
    private static long nextEnemy2;
    private static long nextEnemy3;
    private static int enemy2Count;
    private static long nextPowerUp;

    public static void init() {
        backgrounds.add(new Background(0.070, 0.0, 20, Color.GRAY, 3, 3));
        backgrounds.add(new Background(0.045, 0.0, 50, Color.DARK_GRAY, 2, 2));
        running = true;
        currentTime = System.currentTimeMillis();
        nextEnemy1 = currentTime + 2000;
        nextEnemy2 = currentTime + 7000;
        nextEnemy3 = currentTime + 10000;
        enemy2Count = 0;
        nextPowerUp = currentTime + 4000;

        GameLib.initGraphics();
        delta = System.currentTimeMillis() - currentTime;
    }

    public static void updateTimers() {
        delta = System.currentTimeMillis() - currentTime;
        currentTime = System.currentTimeMillis();
    }

    public static void drawScene(){
        /* Desenhando backgrounds */
        for(Background background : backgrounds)
            background.draw();
        /* Desenhando player */
        player.draw();
        /* desenhando inimigos */
        for(Enemy enemy : enemies) {
            enemy.draw();
            /* desenhando projeteis dos inimigos */
            enemy.drawProjectiles();
        }
        /* desenhando projeteis do player */
        player.drawProjectiles();
        /* desenhando powerups */
        for(PowerUp powerUp : powerUps)
            powerUp.draw();
        /* chamada a display() da classe GameLib atualiza o desenho exibido pela interface do jogo. */
        GameLib.display();
    }

    public static void checkCollisions() {
        if(player.getState().isActive()) {
            for (Enemy enemy : enemies) {
                /* colisões player - inimigos */
                player.checkColissions(enemy);

                /* colisões player - projeteis (inimigo) */
                for (Projectile projectile : enemy.projectiles) {
                    player.checkColissions(projectile);
                }
            }

            /* colisões projeteis (player) - powerups */
            for (PowerUp powerUp : powerUps) {
               player.checkPowerUpColissions(powerUp);
            }
        }

        /* colisões projeteis (player) - inimigos */
        for (Projectile projectile : player.projectiles) {
            for (Enemy enemy : enemies) {
                if(enemy.getState().isActive()) {
                    enemy.checkColissions(projectile);
                }
            }
        }
    }

    public static void checkStates() {
        /* checando estado de projeteis do player  */
        player.checkProjectilesState();

        /* checando estado de explosao player  */
        if(player.getState().isExploding()){
            if(Game.getCurrentTime() > player.getExplosionEnd()){
                player.setState(State.ACTIVE);
            }
        }

        /* checando estado dos inimigos e de seus projeteis  */
        for (Enemy enemy : enemies) {
            enemy.checkProjectilesState();
            enemy.checkState(player.getX());
        }
        /* remove inimigos inativos da lista  */
        enemies.removeIf(enemy -> !enemy.getState().isActive() && !enemy.getState().isExploding());

        /* checando estado dos power ups  */
        for (PowerUp powerUp : powerUps) {
            powerUp.checkState();
        }
        /* remove power ups inativos da lista  */
        powerUps.removeIf(powerUp -> !powerUp.getState().isActive());
    }

    public static void spawnEntities() {
        /* adiciona novos inimigos do tipo 1 na lista  */
        if(getCurrentTime() > nextEnemy1) {
            Enemy1 newEnemy = new Enemy1();
            nextEnemy1 = getCurrentTime() + 500;
            enemies.add(newEnemy);
        }

        /* adiciona novos inimigos do tipo 2 na lista  */
        if(getCurrentTime() > nextEnemy2) {
            Enemy2 newEnemy = new Enemy2();
            enemy2Count++;
            if(enemy2Count < 10){
                nextEnemy2 = currentTime + 120;
            }
            else {
                enemy2Count = 0;
                newEnemy.setSpawnX(Math.random() > 0.5 ? GameLib.WIDTH * 0.2 : GameLib.WIDTH * 0.8);
                nextEnemy2 = (long) (currentTime + 3000 + Math.random() * 3000);
            }
            enemies.add(newEnemy);
        }

        /* adiciona novos inimigos do tipo 3 na lista  */
        if(getCurrentTime() > nextEnemy3) {
            Enemy3 newEnemy = new Enemy3();
            nextEnemy3 = getCurrentTime() + 2000;
            enemies.add(newEnemy);
        }

        /* adiciona novos powerups do tipo 1 na lista  */
        if(getCurrentTime() > nextPowerUp) {
            nextPowerUp = getCurrentTime() + 10000;
            powerUps.add(new PowerUp());
        }
    }

    public static boolean isRunning() {
        return running;
    }

    public static long getDelta() {
        return delta;
    }

    public static long getCurrentTime() {
        return currentTime;
    }

    public static void checkPlayerInputs() {
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
        while(System.currentTimeMillis() < Game.getCurrentTime() + time) Thread.yield();
    }
}
