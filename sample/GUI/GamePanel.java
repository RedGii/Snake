package sample.GUI;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import sample.logic.Snake;
import sample.logic.Food;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.StringValueExp;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import sample.logic.HotArea;
import sample.utils.Resources;

/**
 *
 * @author gigi
 */
public class GamePanel extends JPanel {

    private Snake snake;
    private Food food;
    private GameState gameState;

    private HotArea hotAreaFast;
    private HotArea hotAreaNormal;

    // Variabili per il timer
    private int seconds;
    private boolean running;

    // Variabile punteggi finali
    private int finalScore;

    private sample.GUI.SnakeFrame snakeFrame;

    // Immagini caricate
    private Image logo;
    private Point logoPosition;

    private Image trophy;
    private Point trophyPosition;

    private Image time;
    private Point timePosition;

    private Image moves;
    private Point movesPosition;

    String clickSound;
    SoundEffect se = new SoundEffect();

    // Dimensione del campo di gioco
    public static final Dimension DIMENSION = new Dimension(600, 600);

    // Stati del gioco per far partire lo sample
    public enum GameState {
        STARTED, STOPPED;
    }

    public GamePanel(sample.GUI.SnakeFrame snakeFrame) {

        this.snakeFrame = snakeFrame;

        this.setSize(800, 600);
        this.setLayout(null);

        // Colore dell'area di gioco
        this.setBackground(new Color(89, 163, 105));

        logo = Resources.getImage("/sample/GUI/images/logo.png");
        logoPosition = new Point(610, 30);

        trophy = Resources.getImage("/sample/GUI/images/trophy.png");
        trophyPosition = new Point(605, 275);

        time = Resources.getImage("/sample/GUI/images/time.png");
        timePosition = new Point(605, 170);

        moves = Resources.getImage("/sample/GUI/images/moves.png");
        movesPosition = new Point(605, 380);

        hotAreaFast = new HotArea();
        hotAreaFast.setBounds(620, 470, 160, 40);

        hotAreaNormal = new HotArea();
        hotAreaNormal.setBounds(620, 530, 160, 40);

        // Per gestire i pulsanti
        this.addMouseListener(new GamePanel.MyMouseListener());

        // Carico il suono
        clickSound = "/Users/gigi/Desktop/Game/Game project/src/sample/song/eat.wav";

        // Dimensione del serpente
        this.snake = new Snake(GamePanel.DIMENSION);
        // Dimensione e colore del cibo
        this.food = new Food(10, Color.red);

        // Gestione dei movimenti
        this.initKeyBindings();

        // Stato del gioco settato su START
        this.gameState = GameState.STARTED;

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (gameState == GameState.STARTED) {
                    repaint();
                    update();
                }
            }
        }).start();
    }

    // Thread per la gestione del timer
    private class TimerThread implements Runnable {

        @Override
        public void run() {
            while (running) {
                seconds = seconds + 1;
                /*if (finalScore == 10) {
                    //snake.setDifficulty(true);
                    snake.setVel(100);
                } else if (finalScore == 20) {
                    //snake.setDifficulty(true);
                    snake.setVel(80);
                } else if (finalScore == 30) {
                    //snake.setDifficulty(true);
                    snake.setVel(60);
                } else  if (finalScore == 40) {
                    //snake.setDifficulty(true);
                    snake.setVel(50);
                } else if (finalScore == 50) {
                    //snake.setDifficulty(true);
                    snake.setVel(40);
                } else if (finalScore == 60) {
                    //snake.setDifficulty(true);
                    snake.setVel(30);
                }*/
                repaint();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static class SoundEffect {

        Clip clip;

        public void setFile(String soundFileName){

            try{
                File file = new File(soundFileName);
                AudioInputStream sound = AudioSystem.getAudioInputStream(file);
                clip = AudioSystem.getClip();
                clip.open(sound);
            }
            catch(Exception e){

            }
        }

        public void play(){
            clip.setFramePosition(0);
            clip.start();
        }
    }

    // Attributi del timer
    public void startTimer() {
        this.seconds = 0;
        this.running = true;
        Thread timer = new Thread(new TimerThread());
        timer.start();
    }

    public void stopTimer() {
        running = false;
    }

    // Richiamata nel thread
    // Se lo sample interseca il cibo si allunga, setta il punteggio a +10 e
    // crea del nuovo cibo in modo casuale
    public void update() {
        if (snake.headIntersects(food)) {
            snake.addBody();
            // Ogni frutto mangiato aumento la velocità
            if (finalScore <= 110) {
                snake.setVel(snake.vel - 5);
            }
            // Seleziono il file audio e lo riproduco
            se.setFile(clickSound);
            se.play();
            snake.setScore(snake.getScore() + 5);
            finalScore = snake.getScore();
            spawnFood();
            // Se non è vivo si resetta la partita, stoppa il timer e cambia pannello
        } else if (!snake.isAlive()) {
            snake.resetSnake();
            stopTimer();
            snakeFrame.switchPanel(snakeFrame.finalPanel, snakeFrame.gamePanel);
        }
        try {
            Thread.sleep(1000 / 60);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g2 = (Graphics2D) graphics;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Color.white);
        g2.fillRect(600, 0, 200, 600);

        g2.drawImage(logo, logoPosition.x, logoPosition.y, null);
        g2.drawImage(time, timePosition.x, timePosition.y, null);

        g2.setColor(Color.black);
        g2.setFont(new Font("Bank Gothic", Font.BOLD, 20));
        g2.drawString("Time: ", 665, 200);

        g2.setColor(Color.red);
        g2.setFont(new Font("Bank Gothic", Font.BOLD, 20));
        g2.drawString(String.valueOf(this.seconds + " sec"), 725, 200);

        g2.drawImage(trophy, trophyPosition.x, trophyPosition.y, null);

        g2.setColor(Color.black);
        g2.setFont(new Font("Bank Gothic", Font.BOLD, 20));
        g2.drawString("Score: ", 665, 305);

        g2.setColor(Color.red);
        g2.setFont(new Font("Bank Gothic", Font.BOLD, 20));
        g2.drawString(String.valueOf(snake.getScore()), 730, 305);

        g2.drawImage(moves, movesPosition.x, movesPosition.y, null);

        g2.setColor(Color.black);
        g2.setFont(new Font("Bank Gothic", Font.BOLD, 20));
        g2.drawString("Moves: ", 665, 410);

        g2.setColor(Color.red);
        g2.setFont(new Font("Bank Gothic", Font.BOLD, 20));
        g2.drawString(String.valueOf(snake.getMoves()), 740, 410);

        g2.setColor(Color.black);
        g2.drawRect(620, 470, 160, 40);
        g2.setFont(new Font("Bank Gothic", Font.BOLD, 20));
        g2.drawString("FAST", hotAreaFast.x + 50, hotAreaFast.y + 25);

        g2.setColor(Color.black);
        g2.drawRect(620, 530, 160, 40);
        g2.setFont(new Font("Bank Gothic", Font.BOLD, 20));
        g2.drawString("NORMAL", hotAreaNormal.x + 30, hotAreaNormal.y + 25);

        // Disegna lo sample e il cibo
        this.snake.draw(g2);
        this.food.draw(g2);
    }

    // Associazioni dei tasti al movimento del serpente
    private void initKeyBindings() {
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "snakeUp");
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "snakeRight");
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "snakeDown");
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "snakeLeft");

        this.getActionMap().put("snakeUp", this.snake.getTurnUpAction());
        this.getActionMap().put("snakeRight", this.snake.getTurnRightAction());
        this.getActionMap().put("snakeDown", this.snake.getTurnDownAction());
        this.getActionMap().put("snakeLeft", this.snake.getTurnLeftAction());
    }

    // Classe che gestisce la generazione del cibo
    private void spawnFood() {
        // Fin quando il serpente interseca il cibo viene ricreato in una posizione random
        // Altrimenti viene disabilitato
        while (this.snake.intersects(this.food)) {
            if (food.isEnabled()) {
                food.enable();
                food.setRandomPosition();
            } else {
                food.disable();
            }
        }
    }

    // Classe che incrementa l'Arraylist "corpo" del serpente
    public class AddBody extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            snake.addBody();
        }
    }

    // Classe che avvia il thread del serpente
    public void startSnake() {
        new Thread(this.snake).start();
    }

    public class MyMouseListener extends MouseAdapter {

        @Override
        public void mouseReleased(MouseEvent e) {
            if (hotAreaFast.contains(e.getPoint())) {
                snake.setDifficulty(true);
            } else if (hotAreaNormal.contains(e.getPoint())) {
                snake.setDifficulty(false);
            }
        }
    }

    public int getfinalScore() {
        return finalScore;
    }

    public void setFinalScore(int finalScore) {
        this.finalScore = finalScore;
    }

}
