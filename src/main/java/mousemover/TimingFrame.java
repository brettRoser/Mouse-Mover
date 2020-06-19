/*
 * This class allows the user to set the interval of the mouse movement.
 */
package mousemover;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

@SuppressWarnings("serial")
public class TimingFrame extends JFrame implements ActionListener, DocumentListener, KeyListener
{
  private final JButton exitButton = new JButton(StringConstants.EXIT_TIMER_FRAME_BUTTON_TEXT);
  private final JButton applyButton = new JButton(StringConstants.APPLY_TIMER_FRAME_BUTTON_TEXT);
  private final JTextField enterTimerValueField = new JTextField();
  private int pauseInterval = 20000; // milliseconds

  public TimingFrame() {
    super(StringConstants.TIMER_FRAME_DIALOG_TITLE);
    final int FRAME_WIDTH = 270;
    final int FRAME_HEIGHT = 100;
    setSize(FRAME_WIDTH, FRAME_HEIGHT);

    JPanel dataPanel = new JPanel();
    dataPanel.setBackground(Color.GREEN.darker());
    dataPanel.setLayout(new FlowLayout());

    JPanel buttonPanel = new JPanel();
    buttonPanel.setBackground(Color.GREEN.darker());
    buttonPanel.setLayout(new FlowLayout());
    
    JLabel enterTimerValueLabel = new JLabel(StringConstants.ENTER_TIMER_VALUE_LABEL_TEXT);
    JLabel secondsLabel = new JLabel(StringConstants.SECONDS_TIMER_FRAME_LABEL_TEXT);
    
    applyButton.setEnabled(false);
    addItemsToAppropriateListener();

    enterTimerValueLabel.setForeground(Color.WHITE);
    secondsLabel.setForeground(Color.WHITE);

    enterTimerValueField.setPreferredSize(new Dimension(50, 26));
    enterTimerValueField.setText("" + pauseInterval/1000);
    enterTimerValueField.setCaretPosition(enterTimerValueField.getText().length());

    dataPanel.add(enterTimerValueLabel);
    dataPanel.add(enterTimerValueField);
    dataPanel.add(secondsLabel);
    buttonPanel.add(applyButton);
    buttonPanel.add(exitButton);

    add(dataPanel, BorderLayout.CENTER);
    add(buttonPanel, BorderLayout.SOUTH);
  }

  /*
   * This method adds the various items to the Action Listener, Document
   * Listener and Key Listener.
   */
  private void addItemsToAppropriateListener() {
    // Action Listener
    applyButton.addActionListener(this);
    exitButton.addActionListener(this);

    // Document Listener
    enterTimerValueField.getDocument().addDocumentListener(this);

    // Key Listener
    enterTimerValueField.addKeyListener(this);
  }

  /*
   * This method responds to button clicks and performs the appropriate
   * logic when button clicks are detected.
   */
  @Override
  public void actionPerformed(ActionEvent evt) {
    if(evt.getSource().equals(applyButton)) {
      boolean disposeOfWindow = true;

      // Fetch value from text field
      pauseInterval = Integer.parseInt(enterTimerValueField.getText());

      if(pauseInterval < 3) {
        JOptionPane.showMessageDialog(null,
           StringConstants.INVALID_TIME_ENTERED_VALUE_TEXT,
           "Alert", JOptionPane.ERROR_MESSAGE);
        enterTimerValueField.setText("");
        disposeOfWindow = false;
      }

      // Convert to milliseconds
      pauseInterval = pauseInterval * 1000;

      /* To prevent a situation where the dialog can be closed with no value
       * we will set the pauseInterval to 20 seconds by default and set it in
       * the text field
       */
      if(disposeOfWindow) {
        this.dispose();
      } else {
        enterTimerValueField.setText("20");
      }
    } else if(evt.getSource().equals(exitButton)) {
      this.dispose();
    } else {
      JOptionPane.showMessageDialog(null,
         StringConstants.ACTION_PERFORMED_INVALID_CHOICE,
        "Alert", JOptionPane.ERROR_MESSAGE);
    }
  }

  /*
   * This method is a public getter which is used in the MainFrame class
   * to change the pause interval at which the mouse moves.
   */
  public int getPauseValue() {
    return pauseInterval;
  }

  /*
   * This method is a required method of the Document Listener and will
   * trigger when an update has been performed on the text field which
   * allows the user to enter the new pause interval.
   */
  @Override
  public void changedUpdate(DocumentEvent e) {
    // This shouldn't execute but is required...
    applyButton.setEnabled(true);
  }

  /*
   * This method is a required method of the Document Listener and will
   * trigger when a value has been entered in the text field which allows
   * the user to enter the new pause interval.
   */
  @Override
  public void insertUpdate(DocumentEvent e) {
    applyButton.setEnabled(true);
  }

  /*
   * This method is a required method of the Document Listener and will
   * trigger when a value has been removed from the text field which allows
   * the user to enter the new pause interval.
   */
  @Override
  public void removeUpdate(DocumentEvent e) {
    if(enterTimerValueField.getText().equals("") ||
      enterTimerValueField.getText() == null) {
      applyButton.setEnabled(false);
    }
  }

  /*
   * This method is a required method of the Key Listener and does not
   * perform any functionality in this program.
   */
  @Override
  public void keyPressed(KeyEvent e) {
    // Do nothing...
  }

  /*
   * This method is a required method of the Key Listener and does not
   * perform any functionality in this program.
   */
  @Override
  public void keyReleased(KeyEvent e) {
    // Do nothing...
  }

  /*
   * This method is a required method of the Key Listener and prevents the
   * user from entering any data that is not a digit.
   */
  @Override
  public void keyTyped(KeyEvent e) {
    char c = e.getKeyChar();
    if((!(Character.isDigit(c))) && // Only digits
      (c != '\b') ) // For backspace
    {
      e.consume();
    }
  }
}
