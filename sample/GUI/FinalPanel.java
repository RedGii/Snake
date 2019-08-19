package sample.GUI;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import sample.logic.Snake;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JPanel;
import sample.logic.HotArea;
import sample.utils.Resources;

/**
 *
 * @author gigi
 */
public class FinalPanel extends JPanel {

    private sample.GUI.SnakeFrame snakeFrame;
    private Snake snake;

    private HotArea hotAreaRestart;
    private HotArea hotAreaExit;

    private Image restart;
    //private Dimension restartSize;
    private Point restartPosition;

    private Image exit;
    //private Dimension homeSize;
    private Point exitPosition;

    String clickSound2;
    FinalPanel.SoundEffect se = new FinalPanel.SoundEffect();

    public FinalPanel(sample.GUI.SnakeFrame snakeFrame) {
        this.snakeFrame = snakeFrame;

        this.setSize(800, 600);
        this.setLayout(null);

        restart = Resources.getImage("/sample/GUI/images/restart.png");
        restartPosition = new Point(275, 350);

        exit = Resources.getImage("/sample/GUI/images/exit.png");
        exitPosition = new Point(475, 350);

        hotAreaRestart = new HotArea();
        hotAreaRestart.setBounds(275, 350, 50, 50);

        hotAreaExit = new HotArea();
        hotAreaExit.setBounds(475, 350, 50, 50);

        this.addMouseListener(new FinalPanel.MyMouseListener());

        clickSound2 = "/Users/gigi/Desktop/Game/Game project/src/sample/song/gameover.wav";
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g2 = (Graphics2D) graphics;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Color.red);
        g2.setFont(new Font("Synchro LET", Font.BOLD, 120));
        g2.drawString("GAME OVER", 25, 150);

        g2.setColor(Color.black);
        g2.setFont(new Font("Bank Gothic", Font.BOLD, 20));
        g2.drawString("FINAL SCORE: ", 250, 250);

        g2.setColor(Color.red);
        g2.setFont(new Font("Bank Gothic", Font.BOLD, 30));
        g2.drawString(String.valueOf(snakeFrame.gamePanel.getfinalScore()), 430, 250);

        

        g2.drawImage(restart, restartPosition.x, restartPosition.y, null);
        g2.setColor(Color.black);
        g2.setFont(new Font("Bank Gothic", Font.BOLD, 20));
        g2.drawString("RESTART", restartPosition.x - 25, restartPosition.y + 80);

        g2.drawImage(exit, exitPosition.x, exitPosition.y, null);
        g2.setColor(Color.black);
        g2.setFont(new Font("Bank Gothic", Font.BOLD, 20));
        g2.drawString("EXIT", exitPosition.x, exitPosition.y + 80);

        se.setFile(clickSound2);
        se.play();
    }

    public static class SoundEffect {

        Clip clip;

        public void setFile(String soundFileName) {

            try {
                File file = new File(soundFileName);
                AudioInputStream sound = AudioSystem.getAudioInputStream(file);
                clip = AudioSystem.getClip();
                clip.open(sound);
            } catch (Exception e) {

            }
        }

        public void play() {
            clip.setFramePosition(0);
            clip.start();
        }
    }

    public class MyMouseListener extends MouseAdapter {

        @Override
        public void mouseReleased(MouseEvent e) {
            if (hotAreaRestart.contains(e.getPoint())) {
                snakeFrame.switchPanel(snakeFrame.gamePanel, snakeFrame.finalPanel);
                snakeFrame.gamePanel.startTimer();
                snakeFrame.gamePanel.setFinalScore(0);
            } else if (hotAreaExit.contains(e.getPoint())) {
                System.exit(0);
            }
        }
    }
}
