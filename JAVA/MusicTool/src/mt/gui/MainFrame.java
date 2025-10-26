//*********************************************************************************************************************
package mt.gui;
//*********************************************************************************************************************

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

//*********************************************************************************************************************
public class MainFrame extends JFrame {

	//=================================================================================================================
	private static final long serialVersionUID = 1L;
	//=================================================================================================================

	//=================================================================================================================
	private SpinnerModel halfStepModel = new SpinnerNumberModel(0, -7, 7, 1); 
	//=================================================================================================================
	
	//=================================================================================================================
	private JTextArea  sourceText = new JTextArea();
	private JTextArea  targetText = new JTextArea();
	private JSplitPane splitPane  = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	private JSpinner   halfSteps  = new JSpinner(halfStepModel);
	private JButton    transpose  = new JButton("Transpose");
	private JPanel     toolPanel  = new JPanel();
	//=================================================================================================================
	
	//=================================================================================================================
	public MainFrame() {
		targetText.setEditable(false);
		transpose.addActionListener(this::doTranspose);
		this.setTitle("Music Transposer");
		this.setSize(new Dimension(800, 600));
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		var cp = this.getContentPane();
		toolPanel.add(halfSteps);
		toolPanel.add(transpose);
		splitPane.setLeftComponent(sourceText);
		splitPane.setRightComponent(targetText);
		cp.add(splitPane, BorderLayout.CENTER);
		cp.add(toolPanel, BorderLayout.SOUTH);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		splitPane.setDividerLocation(.5);
		sourceText.requestFocus();
	}
	//=================================================================================================================

	//=================================================================================================================
	private void doTranspose(ActionEvent e) {
		var text = sourceText.getText();
		var buffer = parse(text);
		int shift = (int) halfSteps.getModel().getValue();
		var result = transpose(buffer, shift);
		targetText.setText(result);
	}
	//=================================================================================================================

	//=================================================================================================================
	private int[] parse(String text) {
		int length = text.length();
		text = text.toUpperCase();
		int buffer[] = new int[text.length()+1];
		buffer[0] = 0;
		for (int i=0; i<text.length(); i++) {			
			int value = -1;
			char c = text.charAt(i);
			switch (c) {
			case 'C':
				value = 0;
				break;
			case 'D':
				value = 2;
				break;
			case 'E':
				value = 4;
				break;
			case 'F':
				value = 5;
				break;
			case 'G':
				value = 7;
				break;
			case 'A':
				value = 9;
				break;
			case 'H':
				value = 11;
				break;
			default:				
				break;
			}
			if (
				(value != -1) &&
				(i < (length-1))
			) {
				if (text.charAt(i+1) == '#') {
					i++;
					value++;
				} else if (text.charAt(i+1) == 'B') {
					i++;
					value--;
				}
			}
			if (value != -1) {
				int idx = buffer[0] + 1;
				buffer[idx] = value;
				buffer[0] = idx;
			}
		}
		return buffer;
	}
	//=================================================================================================================

	//=================================================================================================================
	private String transpose(int[] buffer, int steps) {
		String result = "";
		for (int i=1; i<=buffer[0]; i++) {
			int value = (12 + buffer[i] + steps) % 12;
			switch (value) {
			case 0:  result += "C "; break;
			case 1:  result += "C#"; break;
			case 2:  result += "D "; break;
			case 3:  result += "D#"; break;
			case 4:  result += "E "; break;
			case 5:  result += "F "; break;
			case 6:  result += "F#"; break;
			case 7:  result += "G "; break;
			case 8:  result += "G#"; break;
			case 9:  result += "A "; break;
			case 10: result += "A#"; break;
			case 11: result += "H "; break;			
			}
			result += " ";
			if ((i) % 4 == 0) {
				result += "\r\n";
			}
		}
		return result;
	}
	//=================================================================================================================
	
}
//*********************************************************************************************************************
