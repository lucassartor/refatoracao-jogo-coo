import game.*;

public class Main {
    public static void main(String [] args){

        /* Inicia o jogo */
        Game.init();

        while (Game.isRunning()) {
            /* Atualizações dos timers (delta e currentTime) */
            Game.updateTimers();

            /* Verificação de colisões */
            Game.checkCollisions();

            /* Atualizações de estados */
            Game.checkStates();

            /* Adicionando novas entidades (inimigos e powerUps) */
            Game.spawnEntities();

            /* Verificando entrada do usuário (teclado) */
            Game.checkPlayerInputs();

            /* Desenhando planos de fundo */
            Game.drawScene();

            /* faz uma pausa de modo que cada execução do laço do main loop demore aproximadamente 3 ms. */
            Game.busyWait(3);
        }
        System.exit(0);
    }
}
