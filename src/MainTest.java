import entities.*;
import entities.Projectile;
import entities.enemies.Enemy;
import entities.enemies.Enemy1;
import game.Game;
import game.GameLib;

import java.util.*;

public class MainTest {
    public static void main(String [] args){
        Player player = new Player();

        ArrayList<Enemy> enemyList = new ArrayList<>();

        /* Inicia o jogo */
        Game.init();

        // TODO: tirar isso
        long nextEnemy1 = Game.getCurrentTime() + 2000;


        // TODO: Colocar toda essa lógica dentro da classe Game?
        while (Game.isRunning()) {
            Game.updateTimers();

            /***************************/
            /* Verificação de colisões */
            /***************************/
            if(player.getState().isActive()) {
                for (Enemy enemy : enemyList) {
                    /* colisões player - inimigos */
                    player.checkColissions(enemy);

                    /* colisões player - projeteis (inimigo) */
                    for (Projectile projectile : enemy.projectiles) {
                        player.checkColissions(projectile);
                    }
                }
            }

            /* colisões projeteis (player) - inimigos */
            for (Projectile projectile : player.projectiles) {
                for (Enemy enemy : enemyList) {
                    if(enemy.getState().isActive()) {
                        enemy.checkColissions(projectile);
                    }
                }
            }

            /***************************/
            /* Atualizações de estados */
            /***************************/

            /* projeteis (player) */
            player.checkProjectilesState();

            /* projeteis (inimigos)*/
            for (Enemy enemy : enemyList) {
                enemy.checkProjectilesState();
            }

            /* inimigos */
            for (Enemy enemy : enemyList) {
                enemy.checkState(player.getX());
            }
            // TODO: melhorar isso e adicionar para projeteis
            enemyList.removeIf(enemy -> !enemy.getState().isActive() && !enemy.getState().isExploding());


            /* verificando se novos inimigos (tipo 1) devem ser "lançados" */
            // TODO: Melhorar isso e deixar dentro de um loop só para todos os tipos de inimigos
            if(Game.getCurrentTime() > nextEnemy1) {
                Enemy1 newEnemy = new Enemy1();
                newEnemy.setX(Math.random() * (GameLib.WIDTH - 20.0) + 10.0);
                newEnemy.setY(-10.0);
                newEnemy.setVX(0.20 + Math.random() * 0.15);
                newEnemy.setVY(0.20 + Math.random() * 0.15);
                //newEnemy.setRV(0.0);
                //newEnemy.setState(State.ACTIVE);
                newEnemy.setAngle((3 * Math.PI) / 2);
                newEnemy.setNextShot(Game.getCurrentTime() + 500);
                newEnemy.setNextSpawnTime(Game.getCurrentTime() + 500);
                nextEnemy1 = Game.getCurrentTime() + 500;

                enemyList.add(newEnemy);
            }

            /* Verificando se a explosão do player já acabou.         */
            /* Ao final da explosão, o player volta a ser controlável */
            //TODO: colocar isso dentro da classe de player
            if(player.getState().isExploding()){
                if(Game.getCurrentTime() > player.getExplosionEnd()){
                    player.setState(State.ACTIVE);
                }
            }

            /* Verificando entrada do usuário (teclado) */
            Game.checkPlayerInputs(player);

            /* desenhando planos de fundo */
            Game.initBackgrounds();

            /* desenhando player */
            player.draw();

            /* deenhando projeteis (player) */
            player.drawProjectiles();

            for (Enemy enemy : enemyList) {
                /* desenhando projeteis (inimigos) */
                enemy.drawProjectiles();
                /* desenhando inimigos */
                enemy.draw();
            }

            /* chamada a display() da classe GameLib atualiza o desenho exibido pela interface do jogo. */
            GameLib.display();

            /* faz uma pausa de modo que cada execução do laço do main loop demore aproximadamente 3 ms. */
            Game.busyWait(Game.getCurrentTime() + 3);
        }
        System.exit(0);
    }
}
