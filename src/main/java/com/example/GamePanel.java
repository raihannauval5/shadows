package com.example;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import javax.swing.JPanel;

public class GamePanel extends JPanel  implements Runnable {

    // Pengaturan layar
    final int originalTileSize = 16;
    final int scale = 3;
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    // Pengaturan FPS
    int FPS = 60;

    // System objects
    public KeyHandler keyHandler = new KeyHandler(this);
    Thread gameThread;

    // Menentukan lokasi default pemain
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    // Konstruktor
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void update() {
        if (keyHandler.upPressed == true) {
            playerY -= playerSpeed;
        } else if (keyHandler.downPressed == true) {
            playerY += playerSpeed;
        } else if (keyHandler.leftPressed == true) {
            playerX -= playerSpeed;
        } else if (keyHandler.rightPressed == true) {
            playerX += playerSpeed;
        }
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.white);
        g2.fillRect(playerX, playerY, tileSize, tileSize);
        g2.dispose();
    }

    public void startGameThread() {
      gameThread = new Thread(this);
      gameThread.start();
  }


   @Override
   public void run() {
      double drawInterval = 1000000000 / FPS; // 0.01666 seconds
      double delta = 0;
      long lastTime = System.nanoTime();
      long currentTime;

      while (gameThread != null) {
          currentTime = System.nanoTime();
          delta += (currentTime - lastTime) / drawInterval;
          lastTime = currentTime;
          if (delta > 1) {
              update();
              repaint();
              delta--;
          }
      }
   }

}