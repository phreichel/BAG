//*************************************************************************************************
package phi.mv.ui;
//*************************************************************************************************

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import phi.mv.msg.MVMsgManager;

//*************************************************************************************************
public class MVControlPanel extends JPanel {

	//=============================================================================================
	public static enum MVMotor {
		A,
		B,
		C,
		D
	};
	//=============================================================================================

	//=============================================================================================
	public static enum MVDirection {
		EXPAND,
		RETRACT
	};
	//=============================================================================================
	
	//=============================================================================================
	private static final long serialVersionUID = 1L;
	//=============================================================================================

	//=============================================================================================
	private MVConfigurationPanel cfg;
	//=============================================================================================
	
	//=============================================================================================
	private JButton connectButton = new JButton("Connect");
	private JButton stopButton = new JButton("STOP");
	//=============================================================================================

	//=============================================================================================
	private JLabel motorControlLabel = new JLabel("Single Motor Control");
	private JSpinner motorSteps = new JSpinner();
	private JLabel  motorALabel = new JLabel("A");
	private JButton motorARetract = new JButton("<");
	private JButton motorAExpand = new JButton(">");
	private JLabel motorBLabel = new JLabel("B");
	private JButton motorBRetract = new JButton("<");
	private JButton motorBExpand = new JButton(">");
	private JLabel motorCLabel = new JLabel("C");
	private JButton motorCRetract = new JButton("<");
	private JButton motorCExpand = new JButton(">");
	private JLabel motorDLabel = new JLabel("D");
	private JButton motorDRetract = new JButton("<");
	private JButton motorDExpand = new JButton(">");
	//=============================================================================================

	//=============================================================================================
	private JLabel   moveLabel = new JLabel("Movement Control");
	private JButton  moveUpLeft = new JButton("\\");
	private JButton  moveUp = new JButton("|");
	private JButton  moveUpRight = new JButton("/");
	private JButton  moveLeft = new JButton("-");
	private JButton  moveRight = new JButton("-");
	private JButton  moveDownLeft = new JButton("/");
	private JButton  moveDown = new JButton("|");
	private JButton  moveDownRight = new JButton("\\");
	private JSpinner moveLength = new JSpinner();
	//=============================================================================================
	
	//=============================================================================================
	public MVControlPanel(MVConfigurationPanel cfg) {
		
		this.cfg = cfg;
		
		Dimension dimPanel = new Dimension(300, 600);
		Dimension dimFullButton = new Dimension(270, 40);
		Dimension dimLabelMotor = new Dimension(20, 20);
		Dimension dimButtonMotor = new Dimension(50, 20);
		Dimension dimSpinnerMotor = new Dimension(270, 20);
		Dimension dimLabelFull = new Dimension(270, 20);
		Dimension dimMoveLength = new Dimension(115, 20);
		
		int yOffset = 15;

		connectButton.setLocation(15, yOffset);
		connectButton.setPreferredSize(dimFullButton);
		connectButton.setSize(dimFullButton);

		yOffset += 55;		
		
		stopButton.setLocation(15, yOffset);
		stopButton.setPreferredSize(dimFullButton);
		stopButton.setSize(dimFullButton);
		stopButton.setOpaque(true);
		stopButton.setForeground(Color.WHITE);
		stopButton.setBackground(Color.red);
		
		yOffset += 55;		
		
		motorControlLabel.setLocation(15,  yOffset);
		motorControlLabel.setSize(dimLabelFull);
		motorControlLabel.setOpaque(true);
		motorControlLabel.setBackground(Color.LIGHT_GRAY);

		yOffset += 30;		

		motorSteps.setLocation(15, yOffset);
		motorSteps.setPreferredSize(dimSpinnerMotor);
		motorSteps.setSize(dimSpinnerMotor);
		motorSteps.setValue(50);
		
		yOffset += 25;
		
		motorDLabel.setLocation(15, yOffset);
		motorDLabel.setSize(dimLabelMotor);
		motorDRetract.setLocation(35, yOffset);
		motorDRetract.setPreferredSize(dimButtonMotor);
		motorDRetract.setSize(dimButtonMotor);
		motorDExpand.setLocation(85, yOffset);
		motorDExpand.setPreferredSize(dimButtonMotor);
		motorDExpand.setSize(dimButtonMotor);
		
		motorCLabel.setLocation(165, yOffset);
		motorCLabel.setSize(dimLabelMotor);
		motorCRetract.setLocation(185, yOffset);
		motorCRetract.setPreferredSize(dimButtonMotor);
		motorCRetract.setSize(dimButtonMotor);
		motorCExpand.setLocation(235, yOffset);
		motorCExpand.setPreferredSize(dimButtonMotor);
		motorCExpand.setSize(dimButtonMotor);

		yOffset += 25;
		
		motorBLabel.setLocation(15, yOffset);
		motorBLabel.setSize(dimLabelMotor);
		motorBRetract.setLocation(35, yOffset);
		motorBRetract.setPreferredSize(dimButtonMotor);
		motorBRetract.setSize(dimButtonMotor);
		motorBExpand.setLocation(85, yOffset);
		motorBExpand.setPreferredSize(dimButtonMotor);
		motorBExpand.setSize(dimButtonMotor);
		
		motorALabel.setLocation(165, yOffset);
		motorALabel.setSize(dimLabelMotor);
		motorARetract.setLocation(185, yOffset);
		motorARetract.setPreferredSize(dimButtonMotor);
		motorARetract.setSize(dimButtonMotor);
		motorAExpand.setLocation(235, yOffset);
		motorAExpand.setPreferredSize(dimButtonMotor);
		motorAExpand.setSize(dimButtonMotor);

		yOffset += 30;

		moveLabel.setLocation(15,  yOffset);
		moveLabel.setSize(dimLabelFull);
		moveLabel.setOpaque(true);
		moveLabel.setBackground(Color.LIGHT_GRAY);

		yOffset += 25;
		
		moveUpLeft.setLocation(15, yOffset);
		moveUpLeft.setPreferredSize(dimButtonMotor);
		moveUpLeft.setSize(dimButtonMotor);
		moveUp.setLocation(65, yOffset);
		moveUp.setPreferredSize(dimButtonMotor);
		moveUp.setSize(dimButtonMotor);
		moveUpRight.setLocation(115, yOffset);
		moveUpRight.setPreferredSize(dimButtonMotor);
		moveUpRight.setSize(dimButtonMotor);

		moveLength.setLocation(170, yOffset);
		moveLength.setPreferredSize(dimMoveLength);
		moveLength.setSize(dimMoveLength);
		moveLength.setValue(10);
		
		yOffset += 20;

		moveLeft.setLocation(15, yOffset);
		moveLeft.setPreferredSize(dimButtonMotor);
		moveLeft.setSize(dimButtonMotor);
		moveRight.setLocation(115, yOffset);
		moveRight.setPreferredSize(dimButtonMotor);
		moveRight.setSize(dimButtonMotor);
		
		yOffset += 20;

		moveDownLeft.setLocation(15, yOffset);
		moveDownLeft.setPreferredSize(dimButtonMotor);
		moveDownLeft.setSize(dimButtonMotor);
		moveDown.setLocation(65, yOffset);
		moveDown.setPreferredSize(dimButtonMotor);
		moveDown.setSize(dimButtonMotor);
		moveDownRight.setLocation(115, yOffset);
		moveDownRight.setPreferredSize(dimButtonMotor);
		moveDownRight.setSize(dimButtonMotor);

		this.setPreferredSize(dimPanel);
		this.setSize(dimPanel);
		this.setLayout(null);
		
		this.add(connectButton);
		this.add(stopButton);
		
		this.add(motorControlLabel);

		this.add(motorSteps);
		
		this.add(motorALabel);
		this.add(motorARetract);
		this.add(motorAExpand);

		this.add(motorBLabel);
		this.add(motorBRetract);
		this.add(motorBExpand);

		this.add(motorCLabel);
		this.add(motorCRetract);
		this.add(motorCExpand);

		this.add(motorDLabel);
		this.add(motorDRetract);
		this.add(motorDExpand);
		
		this.add(moveLabel);
		this.add(moveUpLeft);
		this.add(moveUp);
		this.add(moveUpRight);
		this.add(moveLeft);
		this.add(moveRight);
		this.add(moveDownLeft);
		this.add(moveDown);
		this.add(moveDownRight);
		this.add(moveLength);
		
		this.applyEvents();
		
	}
	//=============================================================================================

	//=============================================================================================
	private void applyEvents() {
		connectButton.addActionListener(this::performConnect);
		stopButton.addActionListener(this::performStop);
		motorAExpand.addActionListener(this::performAExpand);
		motorBExpand.addActionListener(this::performBExpand);
		motorCExpand.addActionListener(this::performCExpand);
		motorDExpand.addActionListener(this::performDExpand);
		motorARetract.addActionListener(this::performARetract);
		motorBRetract.addActionListener(this::performBRetract);
		motorCRetract.addActionListener(this::performCRetract);
		motorDRetract.addActionListener(this::performDRetract);
		moveUpLeft.addActionListener(this::performMoveUpLeft);
		moveUp.addActionListener(this::performMoveUp);
		moveUpRight.addActionListener(this::performMoveUpRight);
		moveLeft.addActionListener(this::performMoveLeft);
		moveRight.addActionListener(this::performMoveRight);
		moveDownLeft.addActionListener(this::performMoveDownLeft);
		moveDown.addActionListener(this::performMoveDown);
		moveDownRight.addActionListener(this::performMoveDownRight);
	}
	//=============================================================================================

	//=============================================================================================
	private void performConnect(ActionEvent e) {
		if (MVMsgManager.msg == null) {
			try {
				MVMsgManager.connect();
			} catch (Exception x) {
				x.printStackTrace();
			}
			if (MVMsgManager.msg != null) {
				connectButton.setText("Disconnect");
			}
		} else {
			try {
				MVMsgManager.disconnect();
			} catch (Exception x) {
				x.printStackTrace();
			}
			if (MVMsgManager.msg == null) {
				connectButton.setText("Connect");
			}
		}
	}
	//=============================================================================================

	//=============================================================================================
	private void performStop(ActionEvent e) {
		if (MVMsgManager.msg != null) {
			MVMsgManager.msg.reset();
		}
	}
	//=============================================================================================
	
	//=============================================================================================
	private void performAExpand(ActionEvent e) {
		performSingleMove(MVMotor.A, MVDirection.EXPAND);
	}
	//=============================================================================================

	//=============================================================================================
	private void performBExpand(ActionEvent e) {
		performSingleMove(MVMotor.B, MVDirection.EXPAND);
	}
	//=============================================================================================

	//=============================================================================================
	private void performCExpand(ActionEvent e) {
		performSingleMove(MVMotor.C, MVDirection.EXPAND);
	}
	//=============================================================================================

	//=============================================================================================
	private void performDExpand(ActionEvent e) {
		performSingleMove(MVMotor.D, MVDirection.EXPAND);
	}
	//=============================================================================================

	//=============================================================================================
	private void performARetract(ActionEvent e) {
		performSingleMove(MVMotor.A, MVDirection.RETRACT);
	}
	//=============================================================================================

	//=============================================================================================
	private void performBRetract(ActionEvent e) {
		performSingleMove(MVMotor.B, MVDirection.RETRACT);
	}
	//=============================================================================================

	//=============================================================================================
	private void performCRetract(ActionEvent e) {
		performSingleMove(MVMotor.C, MVDirection.RETRACT);
	}
	//=============================================================================================

	//=============================================================================================
	private void performDRetract(ActionEvent e) {
		performSingleMove(MVMotor.D, MVDirection.RETRACT);
	}
	//=============================================================================================

	//=============================================================================================
	private void performSingleMove(MVMotor motor, MVDirection direction) {
		int nsteps = switch(motor) {
			case A -> Integer.parseInt(this.motorSteps.getValue().toString());
			case B -> Integer.parseInt(this.motorSteps.getValue().toString());
			case C -> Integer.parseInt(this.motorSteps.getValue().toString());
			case D -> Integer.parseInt(this.motorSteps.getValue().toString());
		};
		if (MVMsgManager.msg != null) {
			MVMsgManager.msg.single(motor, direction, nsteps);
		}
	}
	//=============================================================================================

	//=============================================================================================
	private void performMoveUpLeft(ActionEvent e) {
		float length = (Integer) moveLength.getValue();
		float angle  = (float) Math.toRadians(135);
		performMove(length, angle);
	}
	//=============================================================================================

	//=============================================================================================
	private void performMoveUp(ActionEvent e) {
		float length = (Integer) moveLength.getValue();
		float angle  = (float) Math.toRadians(90);
		performMove(length, angle);
	}
	//=============================================================================================

	//=============================================================================================
	private void performMoveUpRight(ActionEvent e) {
		float length = (Integer) moveLength.getValue();
		float angle  = (float) Math.toRadians(45);
		performMove(length, angle);
	}
	//=============================================================================================

	//=============================================================================================
	private void performMoveLeft(ActionEvent e) {
		float length = (Integer) moveLength.getValue();
		float angle  = (float) Math.toRadians(180);
		performMove(length, angle);
	}
	//=============================================================================================

	//=============================================================================================
	private void performMoveRight(ActionEvent e) {
		float length = (Integer) moveLength.getValue();
		float angle  = (float) Math.toRadians(0);
		performMove(length, angle);
	}
	//=============================================================================================

	//=============================================================================================
	private void performMoveDownLeft(ActionEvent e) {
		float length = (Integer) moveLength.getValue();
		float angle  = (float) Math.toRadians(225);
		performMove(length, angle);
	}
	//=============================================================================================

	//=============================================================================================
	private void performMoveDown(ActionEvent e) {
		float length = (Integer) moveLength.getValue();
		float angle  = (float) Math.toRadians(270);
		performMove(length, angle);
	}
	//=============================================================================================

	//=============================================================================================
	private void performMoveDownRight(ActionEvent e) {
		float length = (Integer) moveLength.getValue();
		float angle  = (float) Math.toRadians(315);
		performMove(length, angle);
	}
	//=============================================================================================

	//=============================================================================================
	public void performMove(float length, float angle) {

		if (MVMsgManager.msg != null) {
			MVMsgManager.msg.moveBy(length, angle);
			cfg.loadConfig();
		}
		
	}
	//=============================================================================================
	
}
//*************************************************************************************************
