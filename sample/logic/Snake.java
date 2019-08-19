package sample.logic;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author gigi
 */
public class Snake implements Runnable {

    public static final int DEFAULT_DIMENSION = 10;
    public static final int HEAD = 0;

    public static final int UP = 1;
    public static final int RIGHT = 2;
    public static final int DOWN = 3;
    public static final int LEFT = 4;

    private ArrayList<GameArea> snake;
    private int dimension;
    private int direction;
    private int score = 0;
    private int moves = 0;
    boolean difficulty;

    public static int vel = 120;

    private Dimension bounds;

    private boolean newBodyToAdd;
    private boolean alive;

    private TurnUpAction turnUpAction;
    private TurnRightAction turnRightAction;
    private TurnDownAction turnDownAction;
    private TurnLeftAction turnLeftAction;

    // Creazione dello sample
    public Snake(Dimension bounds) {
        this.bounds = bounds;
        this.snake = new ArrayList<GameArea>(3);
        this.snake.add(Snake.HEAD, new GameArea(Snake.DEFAULT_DIMENSION, Color.black));
        this.dimension = Snake.DEFAULT_DIMENSION;
        this.direction = ThreadLocalRandom.current().nextInt(1, 4 + 1);
        this.newBodyToAdd = true;
        this.alive = true;


        this.turnUpAction = new TurnUpAction();
        this.turnRightAction = new TurnRightAction();
        this.turnDownAction = new TurnDownAction();
        this.turnLeftAction = new TurnLeftAction();
    }



    // Thread dello sample
    @Override
    public void run() {
        while (this.alive) {
            this.move();
            if (this.headIntersectsBody()) {
                this.alive = false;
            }
            try {
                // Possibile modificare la velocità
                if (difficulty == false) {
                    Thread.sleep(vel);
                } else if (difficulty == true) {
                    Thread.sleep(50);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // funzione per disegnare il serpente nel gamepanel
    public void draw(Graphics2D g2) {
        for (GameArea square : this.snake) {
            square.draw(g2);
        }
    }

    // Per l'aggiunta del corpo allo sample
    public void addBody() {
        this.newBodyToAdd = true;
    }

    // Gestione delle direzioni possibili
    // Continua in quella direzione fin quando non si cambia direzione
    public void move() {
        Point prevHeadPosition = this.snake.get(0).getLocation();
        switch (this.direction) {
            case Snake.UP:
                this.snake.get(Snake.HEAD).y -= this.snake.get(Snake.HEAD).height;
                if (this.snake.get(Snake.HEAD).y < 0) {
                    this.snake.get(Snake.HEAD).y = 0;
                    this.alive = false;
                }
                break;
            case Snake.RIGHT:
                this.snake.get(Snake.HEAD).x += this.snake.get(Snake.HEAD).width;
                if (this.snake.get(Snake.HEAD).x > this.bounds.width - this.dimension) {
                    this.snake.get(Snake.HEAD).x = this.bounds.width - this.dimension;
                    this.alive = false;
                }
                break;
            case Snake.DOWN:
                this.snake.get(Snake.HEAD).y += this.snake.get(Snake.HEAD).height;
                if (this.snake.get(Snake.HEAD).y > this.bounds.height - this.dimension) {
                    this.snake.get(Snake.HEAD).y = this.bounds.height - this.dimension;
                    this.alive = false;
                }
                break;
            case Snake.LEFT:
                this.snake.get(Snake.HEAD).x -= this.snake.get(Snake.HEAD).width;
                if (this.snake.get(Snake.HEAD).x < 0) {
                    this.snake.get(Snake.HEAD).x = 0;
                    this.alive = false;
                    break;
                }
        }

        // Se lo sample è appena nato aggiungo all'array un quadratino con dimensione e colore preimpostati
        // Altrimenti lo aggiungo al posto della testa in precedenza
        if (this.alive) {
            if (this.newBodyToAdd) {
                this.snake.add(1, new GameArea(prevHeadPosition.x, prevHeadPosition.y, this.dimension, new Color(97, 78, 0)));
                this.newBodyToAdd = false;
            } else if (this.snake.size() > 1) {
                this.snake.add(1, this.snake.remove(this.snake.size() - 1));
                this.snake.get(1).setLocation(prevHeadPosition);
            }
        }
    }

    // Collisione con i bordi
    public boolean intersects(GameArea gameArea) {
        for (GameArea gameToCheck : this.snake) {
            if (gameToCheck.intersects(gameArea)) {
                return true;
            }
        }
        return false;
    }

    // Ritorno della collisione
    public boolean headIntersects(GameArea gameArea) {
        return (this.snake.get(Snake.HEAD).intersects(gameArea));
    }

    // Funzione in cui la testa interseca il corpo
    public boolean headIntersectsBody() {
        for (int i = 1; i < this.snake.size(); i++) {
            if (this.headIntersects(this.snake.get(i))) {
                return true;
            }
        }
        return false;
    }

    // Se il serpente è vivo
    public boolean isAlive() {
        return this.alive;
    }

    // Gestione della direzione da prendere
    // Non può prendere la direzione opposta
    public void turnUp() {
        if (this.direction != Snake.DOWN) {
            this.setMoves(this.getMoves() + 1);

            this.direction = Snake.UP;
        }
    }

    public void turnRight() {
        if (this.direction != Snake.LEFT) {
            this.setMoves(this.getMoves() + 1);

            this.direction = Snake.RIGHT;
        }
    }

    public void turnDown() {
        if (this.direction != Snake.UP) {
            this.setMoves(this.getMoves() + 1);

            this.direction = Snake.DOWN;
        }
    }

    public void turnLeft() {
        if (this.direction != Snake.RIGHT) {
            this.setMoves(this.getMoves() + 1);

            this.direction = Snake.LEFT;
        }
    }

    public TurnUpAction getTurnUpAction() {
        return turnUpAction;
    }

    public TurnRightAction getTurnRightAction() {
        return turnRightAction;
    }

    public TurnDownAction getTurnDownAction() {
        return turnDownAction;
    }

    public TurnLeftAction getTurnLeftAction() {
        return turnLeftAction;
    }

    public class TurnUpAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            turnUp();
        }
    }

    public class TurnRightAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            turnRight();
        }
    }

    public class TurnDownAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            turnDown();
        }
    }

    public class TurnLeftAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            turnLeft();
        }
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public int getMoves() {
        return moves;
    }

    public void setMoves(int moves) {
        this.moves = moves;
    }

    public boolean isDifficulty() {
        return difficulty;
    }

    public void setDifficulty(boolean diff) {
        this.difficulty = diff;
    }

    public void setVel(int vel) {
        this.vel = vel;
    }

    public static int getVel() {
        return vel;
    }

    // Funzione per il reset dello sample (Ricrea tutto da zero)
    public void resetSnake() {
        this.bounds = bounds;
        this.snake = new ArrayList<GameArea>(3);
        this.snake.add(Snake.HEAD, new GameArea(Snake.DEFAULT_DIMENSION, Color.black));
        this.dimension = Snake.DEFAULT_DIMENSION;
        this.direction = ThreadLocalRandom.current().nextInt(1, 4 + 1);
        this.newBodyToAdd = true;
        this.alive = true;

        this.turnUpAction = new TurnUpAction();
        this.turnRightAction = new TurnRightAction();
        this.turnDownAction = new TurnDownAction();
        this.turnLeftAction = new TurnLeftAction();
        setScore(0);
        setMoves(0);
        setVel(120);
    }

}
