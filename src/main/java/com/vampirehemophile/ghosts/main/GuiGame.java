package com.vampirehemophile.ghosts.main;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.IOException;

import com.vampirehemophile.ghosts.gamestatemanager.GameStateManager;
import com.vampirehemophile.ghosts.managers.KeyManager;

/**
 * GuiGame class.
 */
public class GuiGame implements Runnable {

  // JFrame
  private Display display;
  private int width, height;
  public String title;

  // GameLoop Variables
  private boolean running = false;
  private Thread thread;

  // Drawing Variables
  private BufferStrategy bs;
  private Graphics g;


  /** GameStateManager */
  private GameStateManager gsm;

  /** KeyManager */
  private KeyManager km;


  /**
   * Constructor for GuiGame.
   *
   * @param title a {@link java.lang.String} object.
   * @param width width of the window.
   * @param height height of the window.
   */
  public GuiGame(String title, int width, int height) {

    this.width = width;
    this.height = height;
    this.title = title;

    this.km = new KeyManager();
    this.gsm = new GameStateManager(km);
  }

  private void init() throws IOException {
    display = new Display(title, width, height);
    display.getFrame().addKeyListener(km);
  }


  /**
   * start.
   */
  public synchronized void start() {
    if (running) {
      return; //safety
    }
    running = true;
    thread = new Thread(this); // this = game class
    thread.start();
  }

  /** {@inheritDoc} */
  @Override public void run() {
    try {
      init();
    } catch (IOException e) {
      e.printStackTrace();
    }

    // calculates the fps
    int fps = 60;
    double timePerTick = 1000000000 / fps; //nano seconds --> 1 sec hehe
    double delta = 0;
    long now;
    long lastTime = System.nanoTime();
    long timer = 0;
    int ticks = 0;
    // end variables

    while (running) {
      now = System.nanoTime();
      delta += (now - lastTime) / timePerTick;
      timer += now - lastTime;
      lastTime = now;

      if (delta >= 1) {
        tick();
        render();
        ticks++;
        delta--;
      }
      if (timer >= 1000000000){
        ticks = 0;
        timer = 0;
      }
    }
  }

  /**
   * tick.
   */
  private void tick() {
    km.tick();

    if(gsm.currentState() != null) {
      gsm.currentState().tick();
    }
  }

  /**
   * render.
   */
  private void render() {
    // display
    bs = display.getCanvas().getBufferStrategy();
    if(bs == null){
      display.getCanvas().createBufferStrategy(3);
      return;
    }

    g = bs.getDrawGraphics();

    // Clear Screen
    g.clearRect(0, 0, width, height);

    // Draw here
    if(gsm.currentState() != null) {
      gsm.currentState().render(g);
    }
    // End Drawing

    bs.show();
    g.dispose();
  }

  /**
   * stop.
   */
  public synchronized void stop() {
    if (!running) {
      return;
    }
    running = false;
    try {
      thread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
