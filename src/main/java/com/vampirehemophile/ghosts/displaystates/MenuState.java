package com.vampirehemophile.ghosts.displaystates;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


/** Main menu state. */
public class MenuState extends State {

  /** main game menu state's panel. */
  @SuppressWarnings("serial")
  private class MenuPanel extends JPanel {

    /** The title font size. */
    private static final int FONT_SIZE = 20;

    /** The file to read the game from.*/
    private File chosenFile;

    /** Constructs a MenuPanel object. */
    MenuPanel() {
      super();

      JLabel label;
      AbstractButton button;

      // Layout
      setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

      // Title
      label = new JLabel("Ghosts!", JLabel.CENTER);
      label.setHorizontalTextPosition(JLabel.CENTER);
      label.setAlignmentX(Component.CENTER_ALIGNMENT);
      label.setFont(new Font(Font.MONOSPACED, Font.PLAIN, FONT_SIZE));
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
        @Override public void actionPerformed(final ActionEvent e) {
          MenuState.this.setChanged();
          if (chosenFile == null) {
            MenuState.this.notifyObservers(new PlayState(cheatModeEnabled));
          } else {
            MenuState.this.notifyObservers(
            new PlayState(cheatModeEnabled, chosenFile));
          }
        }
      });
      add(button);

      // Cheat mode button
      button = new JCheckBox("Cheat Mode ?");
      button.setSelected(MenuState.this.cheatModeEnabled);
      button.setAlignmentX(Component.CENTER_ALIGNMENT);
      button.addItemListener(new ItemListener() {
        @Override public void itemStateChanged(final ItemEvent e) {
          if (MenuState.this.cheatModeEnabled) {
            cheatModeEnabled = false;
          } else {
            cheatModeEnabled = true;
          }
        }
      });
      add(button);

      // File Chooser
      JTextField textArea = new JTextField();
      textArea.setAlignmentX(Component.CENTER_ALIGNMENT);
      textArea.setEditable(false);
      add(textArea);

      JFileChooser fc = new JFileChooser();

      button = new JButton("open a file");
      button.addActionListener(new ActionListener() {
        @Override public void actionPerformed(final ActionEvent e) {
          int returnVal = fc.showOpenDialog(MenuState.this.panel);
          if (returnVal == JFileChooser.APPROVE_OPTION) {
            chosenFile = fc.getSelectedFile();
            textArea.setText(chosenFile.getAbsolutePath());
          }
        }
      });
      button.setAlignmentX(Component.CENTER_ALIGNMENT);
      add(button);


      // Source
      if (java.awt.Desktop.isDesktopSupported()) {
        button = new JButton("Source");
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(new ActionListener() {
          @Override public void actionPerformed(final ActionEvent e) {
            try {
              java.awt.Desktop.getDesktop().browse(
              new java.net.URI("https://github.com/VampireHemophile/Ghosts"));
            } catch (java.net.URISyntaxException ex) {
            } catch (java.io.IOException ex) {
            }
          }
        });
        add(button);
      }
    }
  }


  /** states panel. */
  private JPanel panel;

  /** If the cheat mode is enabled. */
  private boolean cheatModeEnabled = false;

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
