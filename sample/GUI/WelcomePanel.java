package sample.GUI;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Graphics;
import java.awt.Graphics2D;
import sample.logic.HotArea;
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
import sample.utils.Resources;

/**
 *
 * @author gigi
 */
public class WelcomePanel extends JPanel {

    private sample.GUI.SnakeFrame snakeFrame;

    private Image sfondo;
    private Point sfondoPosition;

    private HotArea hotAreaNuovaPartita;
    private HotArea hotAreaUscita;

    String clickSound;
    SoundEffect se = new SoundEffect();


    public WelcomePanel(sample.GUI.SnakeFrame pSnakeFrame) {

        this.snakeFrame = pSnakeFrame;

        this.setSize(800, 600);
        this.setLayout(null);

        // Aggiungo mouselistener per creare azioni a i pulsanti
        this.addMouseListener(new MyMouseListener());

        // Da dove prende l'immagine
        sfondo = Resources.getImage("/sample/GUI/images/SfondoIniziale.png");
        // La posizione
        sfondoPosition = new Point(0, 0);

        // Creo le HotArea e setto i parametri
        hotAreaNuovaPartita = new HotArea();
        hotAreaNuovaPartita.setBounds(315, 315, 170, 40);

        // Creo le HotArea e setto i parametri
        hotAreaUscita = new HotArea();
        hotAreaUscita.setBounds(340, 415, 120, 40);

        // Carico il suono
        clickSound = "/Users/gigi/Desktop/Game/Game project/src/sample/song/snake.wav";

        // Seleziono il file audio e lo riproduco
        se.setFile(clickSound);
        //se.play();
    }

    public class SoundEffect {

        Clip clip;

        public void setFile(String soundFileName){

            try{
                File file = new File(soundFileName);
                AudioInputStream sound = AudioSystem.getAudioInputStream(file);
                clip = AudioSystem.getClip();
                clip.open(sound);
                clip.loop(Clip.LOOP_CONTINUOUSLY);

            }
            catch(Exception e){

            }
        }

        public void play(){

            clip.setFramePosition(0);
            clip.start();
        }

    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g2 = (Graphics2D) graphics;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Disegna
        g2.drawImage(sfondo, sfondoPosition.x, sfondoPosition.y, sfondo.getWidth(this), sfondo.getHeight(this), null);

    }

    // Se premo il tasto passa da un pannello a l'altro o esce
    public class MyMouseListener extends MouseAdapter {

        @Override
        public void mouseReleased(MouseEvent e) {
            if (hotAreaNuovaPartita.contains(e.getPoint())) {
                snakeFrame.switchPanel(snakeFrame.infoGamePanel, snakeFrame.welcomePanel);
            } else {
                // Se premo il tasto chiude tutto
                if (hotAreaUscita.contains(e.getPoint())) {
                    System.exit(0);
                }
            }
        }
    }
}
