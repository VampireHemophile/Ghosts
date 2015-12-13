package com.vampirehemophile.ghosts.gamestates;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;

/** Main menu state. */
public class MenuState extends State {

  /** main game menu state's panel. */
  @SuppressWarnings("serial")
  private class MenuPanel extends JPanel {

    /** Constructs a MenuPanel object. */
    public MenuPanel() {
      super();

      JLabel label;
      JButton button;

      // Layout
      setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

      // Title
      label = new JLabel("Ghosts!", JLabel.CENTER);
      label.setHorizontalTextPosition(JLabel.CENTER);
      label.setAlignmentX(Component.CENTER_ALIGNMENT);
      label.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
      add(label);

      // Credits
      label = new JLabel("Antonin DECIMO & Perrine PULLICINO", JLabel.CENTER);
      label.setHorizontalTextPosition(JLabel.CENTER);
      label.setAlignmentX(Component.CENTER_ALIGNMENT);
      add(label);

      // Play button
      button = new JButton("Play !");
      button.setAlignmentX(Component.CENTER_ALIGNMENT);
      button.addActionListener(new ActionListener() {
        @Override public void actionPerformed(ActionEvent e) {
          MenuState.this.setChanged();
          MenuState.this.notifyObservers(new PlayState());
        }
      });
      add(button);

      // Source
      if (java.awt.Desktop.isDesktopSupported()) {
        button = new JButton("Source");
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(new ActionListener() {
          @Override public void actionPerformed(ActionEvent e) {
            try {
              java.awt.Desktop.getDesktop().browse(
                new java.net.URI("https://github.com/VampireHemophile/Ghosts"));
            } catch (Exception ex) {}
          }
        });
        add(button);
      }
    }
  }


  /** states panel. */
  private JPanel panel;


  /** Constructs a MenuState object. */
  public MenuState() {
    super();
    panel = new MenuPanel();
  }

  /** {@inheritDoc} */
  @Override
  public JPanel render() {
    return panel;
  }
}
