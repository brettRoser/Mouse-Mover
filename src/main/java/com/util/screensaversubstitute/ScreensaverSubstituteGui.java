package screensaversubstitute;

import java.awt.Robot;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.AWTException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ScreensaverSubstituteGui extends JFrame implements ActionListener
{
  private static final int width = 440;
  private static final int height = 65;
  private static final int pause = 20000; // milliseconds
  private static int buttonCode;
  private JButton startMouseMovementButton = new JButton("Start Mouse Movement");
  private JButton stopMouseMovementButton = new JButton("Stop Mouse Movement");
  private JButton exitButton = new JButton("Exit");

  public ScreensaverSubstituteGui() {    
    
    super("Screensaver Substitute Gui");
    setSize(width, height);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    JPanel buttonPanel = new JPanel();
    buttonPanel.setBackground(Color.RED);
    buttonPanel.setLayout(new FlowLayout());
    
    addButtonsToActionListener();
    
    buttonPanel.add(startMouseMovementButton);
    buttonPanel.add(stopMouseMovementButton);
    buttonPanel.add(exitButton);
    
    add(buttonPanel, BorderLayout.CENTER);
  } // end of constructor
    
  private void addButtonsToActionListener() {
    startMouseMovementButton.addActionListener(this);
    stopMouseMovementButton.addActionListener(this);
    exitButton.addActionListener(this);
  }

  public void actionPerformed(ActionEvent event) 
  {  
    if (event.getSource().equals(startMouseMovementButton)) {
      usageOfThread myThread = new usageOfThread();
      myThread.start();
      buttonCode = 1;
    } else if (event.getSource().equals(stopMouseMovementButton)) {
      buttonCode = 0;
    } else if (event.getSource().equals(exitButton)) {
      System.exit(0);
    } 
  } // end of actionPerformed method

  private class usageOfThread extends Thread {
    @Override
    public void run() {
      try {
        Robot robot = new Robot();
        while (buttonCode > 0) {
          robot.mouseMove(300, 200);
          Thread.sleep(pause);
          if (buttonCode == 0) {
            break;
          }
          robot.mouseMove(300, 500);
          Thread.sleep(pause);
          if (buttonCode == 0) {
            break;
          }
          robot.mouseMove(800, 500);
          Thread.sleep(pause);
          if (buttonCode == 0) {
            break;
          }
          robot.mouseMove(800, 200);
          Thread.sleep(pause);
        }
      } catch (InterruptedException ex) {
        Logger.getLogger(ScreensaverSubstituteGui.class.
                getName()).log(Level.SEVERE, null, ex);
      } catch (AWTException ex) {
        Logger.getLogger(ScreensaverSubstituteGui.class.
                getName()).log(Level.SEVERE, null, ex);
      }
    } // end of run method
  } // end of inclass usageOfThread
} // end of class
