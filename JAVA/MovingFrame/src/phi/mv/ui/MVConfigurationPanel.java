//*************************************************************************************************
package phi.mv.ui;
//*************************************************************************************************

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.vecmath.Vector2f;

import phi.mv.data.MVConfiguration;

//*************************************************************************************************
public class MVConfigurationPanel extends JPanel {

	//=============================================================================================
	private static final long serialVersionUID = 1L;
	//=============================================================================================

	//=============================================================================================
	private MVMap map;
	//=============================================================================================
	
	//=============================================================================================
	private JLabel commLabel = new JLabel("Communication Parameters");	
	private JLabel networkAddressLabel = new JLabel("IP Address");	
	private JTextField networkAddressValue = new JTextField();
	private JLabel networkPortLabel = new JLabel("Local IP Port");	
	private JTextField networkPortValue = new JTextField();
	//=============================================================================================

	//=============================================================================================
	private JLabel geoRotationLabel = new JLabel("Rotation Parameters");	
	private JLabel geoStepsPerRotationLabel = new JLabel("Steps/Rotation");	
	private JTextField geoStepsPerRotationValue = new JTextField();
	private JLabel geoRotationRadiusLabel = new JLabel("Rotation Radius");	
	private JTextField geoRotationRadiusValue = new JTextField();
	//=============================================================================================

	//=============================================================================================
	private JLabel geoPivotLabel = new JLabel("Pivot Parameters");	
	private JLabel geoPivotAXLabel = new JLabel("A(X)");	
	private JTextField geoPivotAXValue = new JTextField();
	private JLabel geoPivotAYLabel = new JLabel("A(Y)");	
	private JTextField geoPivotAYValue = new JTextField();
	private JLabel geoPivotBXLabel = new JLabel("B(X)");	
	private JTextField geoPivotBXValue = new JTextField();
	private JLabel geoPivotBYLabel = new JLabel("B(Y)");	
	private JTextField geoPivotBYValue = new JTextField();
	private JLabel geoPivotCXLabel = new JLabel("C(X)");	
	private JTextField geoPivotCXValue = new JTextField();
	private JLabel geoPivotCYLabel = new JLabel("C(Y)");	
	private JTextField geoPivotCYValue = new JTextField();
	private JLabel geoPivotDXLabel = new JLabel("D(X)");	
	private JTextField geoPivotDXValue = new JTextField();
	private JLabel geoPivotDYLabel = new JLabel("D(Y)");	
	private JTextField geoPivotDYValue = new JTextField();
	//=============================================================================================

	//=============================================================================================
	private JLabel headPositionLabel = new JLabel("Head Parameters");	
	private JLabel headPositionXLabel = new JLabel("H(X)");	
	private JTextField headPositionXValue = new JTextField();
	private JLabel headPositionYLabel = new JLabel("H(Y)");	
	private JTextField headPositionYValue = new JTextField();
	private JLabel headRadiusLabel = new JLabel("Radius");	
	private JTextField headRadiusValue = new JTextField();
	//=============================================================================================
	
	//=============================================================================================
	private JLabel headCordALabel = new JLabel("Cord Length A");	
	private JTextField headCordAValue = new JTextField();
	private JLabel headCordBLabel = new JLabel("Cord Length B");	
	private JTextField headCordBValue = new JTextField();
	private JLabel headCordCLabel = new JLabel("Cord Length C");	
	private JTextField headCordCValue = new JTextField();
	private JLabel headCordDLabel = new JLabel("Cord Length D");	
	private JTextField headCordDValue = new JTextField();
	//=============================================================================================
	
	//=============================================================================================
	public MVConfigurationPanel(MVMap map) {
		
		this.map = map;
		this.map.inject(this);
		
		Dimension dimPanel = new Dimension(300, 650);
		Dimension dimLabel = new Dimension(80, 20);
		Dimension dimValue = new Dimension(150, 20);
		Dimension dimLabelGeo = new Dimension(100, 20);
		Dimension dimValueGeo = new Dimension(130, 20);
		Dimension dimLabelSmall = new Dimension(30, 20);
		Dimension dimValueSmall = new Dimension(80, 20);
		Dimension dimLabelFull = new Dimension(270, 20);
		
		int yOffset = 15;

		commLabel.setLocation(15,  yOffset);
		commLabel.setSize(dimLabelFull);
		commLabel.setOpaque(true);
		commLabel.setBackground(Color.LIGHT_GRAY);

		yOffset += 30;
		
		networkAddressLabel.setLocation(15, yOffset);
		networkAddressLabel.setSize(dimLabel);
		networkAddressValue.setLocation(100, yOffset);
		networkAddressValue.setSize(dimValue);

		yOffset += 25;
		
		networkPortLabel.setLocation(15, yOffset);
		networkPortLabel.setSize(dimLabel);
		networkPortValue.setLocation(100, yOffset);
		networkPortValue.setSize(dimValue);

		yOffset += 35;
		
		geoRotationLabel.setLocation(15,  yOffset);
		geoRotationLabel.setSize(dimLabelFull);
		geoRotationLabel.setOpaque(true);
		geoRotationLabel.setBackground(Color.LIGHT_GRAY);

		yOffset += 30;
		
		geoStepsPerRotationLabel.setLocation(15, yOffset);
		geoStepsPerRotationLabel.setSize(dimLabelGeo);
		geoStepsPerRotationValue.setLocation(125, yOffset);
		geoStepsPerRotationValue.setSize(dimValueGeo);

		yOffset += 25;

		geoRotationRadiusLabel.setLocation(15, yOffset);
		geoRotationRadiusLabel.setSize(dimLabelGeo);
		geoRotationRadiusValue.setLocation(125, yOffset);
		geoRotationRadiusValue.setSize(dimValueGeo);

		yOffset += 35;
		
		geoPivotLabel.setLocation(15,  yOffset);
		geoPivotLabel.setSize(dimLabelFull);
		geoPivotLabel.setOpaque(true);
		geoPivotLabel.setBackground(Color.LIGHT_GRAY);		

		yOffset += 30;
		
		geoPivotAXLabel.setLocation(15, yOffset);
		geoPivotAXLabel.setSize(dimLabelSmall);
		geoPivotAXValue.setLocation(50, yOffset);
		geoPivotAXValue.setSize(dimValueSmall);
		
		geoPivotAYLabel.setLocation(140, yOffset);
		geoPivotAYLabel.setSize(dimLabelSmall);
		geoPivotAYValue.setLocation(175, yOffset);
		geoPivotAYValue.setSize(dimValueSmall);

		yOffset += 25;

		geoPivotBXLabel.setLocation(15, yOffset);
		geoPivotBXLabel.setSize(dimLabelSmall);
		geoPivotBXValue.setLocation(50, yOffset);
		geoPivotBXValue.setSize(dimValueSmall);
		
		geoPivotBYLabel.setLocation(140, yOffset);
		geoPivotBYLabel.setSize(dimLabelSmall);
		geoPivotBYValue.setLocation(175, yOffset);
		geoPivotBYValue.setSize(dimValueSmall);

		yOffset += 25;

		geoPivotCXLabel.setLocation(15, yOffset);
		geoPivotCXLabel.setSize(dimLabelSmall);
		geoPivotCXValue.setLocation(50, yOffset);
		geoPivotCXValue.setSize(dimValueSmall);
		
		geoPivotCYLabel.setLocation(140, yOffset);
		geoPivotCYLabel.setSize(dimLabelSmall);
		geoPivotCYValue.setLocation(175, yOffset);
		geoPivotCYValue.setSize(dimValueSmall);

		yOffset += 25;

		geoPivotDXLabel.setLocation(15, yOffset);
		geoPivotDXLabel.setSize(dimLabelSmall);
		geoPivotDXValue.setLocation(50, yOffset);
		geoPivotDXValue.setSize(dimValueSmall);
		
		geoPivotDYLabel.setLocation(140, yOffset);
		geoPivotDYLabel.setSize(dimLabelSmall);
		geoPivotDYValue.setLocation(175, yOffset);
		geoPivotDYValue.setSize(dimValueSmall);
		
		yOffset += 35;

		headPositionLabel.setLocation(15,  yOffset);
		headPositionLabel.setSize(dimLabelFull);
		headPositionLabel.setOpaque(true);
		headPositionLabel.setBackground(Color.LIGHT_GRAY);

		yOffset += 30;
		
		headPositionXLabel.setLocation(15, yOffset);
		headPositionXLabel.setSize(dimLabelSmall);
		headPositionXValue.setLocation(50, yOffset);
		headPositionXValue.setSize(dimValueSmall);
		
		headPositionYLabel.setLocation(140, yOffset);
		headPositionYLabel.setSize(dimLabelSmall);
		headPositionYValue.setLocation(175, yOffset);
		headPositionYValue.setSize(dimValueSmall);

		yOffset += 25;

		headRadiusLabel.setLocation(15, yOffset);
		headRadiusLabel.setSize(dimLabelGeo);
		headRadiusValue.setLocation(125, yOffset);
		headRadiusValue.setSize(dimValueGeo);
		
		yOffset += 30;

		headCordALabel.setLocation(15, yOffset);
		headCordALabel.setSize(dimLabelGeo);
		headCordALabel.setEnabled(false);
		headCordAValue.setLocation(125, yOffset);
		headCordAValue.setSize(dimValueGeo);
		headCordAValue.setEnabled(false);

		yOffset += 25;

		headCordBLabel.setLocation(15, yOffset);
		headCordBLabel.setSize(dimLabelGeo);
		headCordBLabel.setEnabled(false);
		headCordBValue.setLocation(125, yOffset);
		headCordBValue.setSize(dimValueGeo);
		headCordBValue.setEnabled(false);

		yOffset += 25;

		headCordCLabel.setLocation(15, yOffset);
		headCordCLabel.setSize(dimLabelGeo);
		headCordCLabel.setEnabled(false);
		headCordCValue.setLocation(125, yOffset);
		headCordCValue.setSize(dimValueGeo);
		headCordCValue.setEnabled(false);

		yOffset += 25;

		headCordDLabel.setLocation(15, yOffset);
		headCordDLabel.setSize(dimLabelGeo);
		headCordDLabel.setEnabled(false);
		headCordDValue.setLocation(125, yOffset);
		headCordDValue.setSize(dimValueGeo);
		headCordDValue.setEnabled(false);
		
		this.setPreferredSize(dimPanel);
		this.setSize(dimPanel);
		this.setLayout(null);

		this.add(commLabel);
		
		this.add(networkAddressLabel);
		this.add(networkAddressValue);
		this.add(networkPortLabel);
		this.add(networkPortValue);

		this.add(geoRotationLabel);
		
		this.add(geoStepsPerRotationLabel);
		this.add(geoStepsPerRotationValue);

		this.add(geoRotationRadiusLabel);
		this.add(geoRotationRadiusValue);

		this.add(geoPivotLabel);
		
		this.add(geoPivotAXLabel);
		this.add(geoPivotAXValue);
		this.add(geoPivotAYLabel);
		this.add(geoPivotAYValue);

		this.add(geoPivotBXLabel);
		this.add(geoPivotBXValue);
		this.add(geoPivotBYLabel);
		this.add(geoPivotBYValue);

		this.add(geoPivotCXLabel);
		this.add(geoPivotCXValue);
		this.add(geoPivotCYLabel);
		this.add(geoPivotCYValue);

		this.add(geoPivotDXLabel);
		this.add(geoPivotDXValue);
		this.add(geoPivotDYLabel);
		this.add(geoPivotDYValue);
		
		this.add(headPositionLabel);

		this.add(headPositionXLabel);
		this.add(headPositionXValue);
		this.add(headPositionYLabel);
		this.add(headPositionYValue);
		this.add(headRadiusLabel);
		this.add(headRadiusValue);
		
		this.add(headCordALabel);
		this.add(headCordAValue);
		this.add(headCordBLabel);
		this.add(headCordBValue);
		this.add(headCordCLabel);
		this.add(headCordCValue);
		this.add(headCordDLabel);
		this.add(headCordDValue);
		
		loadConfig();
		
	}
	//=============================================================================================

	//=============================================================================================
	private void setupActions() {

		performSaveEnabled = true;
		
		networkAddressValue.addActionListener(this::performSave);
		networkPortValue.addActionListener(this::performSave);
		geoStepsPerRotationValue.addActionListener(this::performSave);
		geoRotationRadiusValue.addActionListener(this::performSave);
		geoPivotAXValue.addActionListener(this::performSave);
		geoPivotAYValue.addActionListener(this::performSave);
		geoPivotBXValue.addActionListener(this::performSave);
		geoPivotBYValue.addActionListener(this::performSave);
		geoPivotCXValue.addActionListener(this::performSave);
		geoPivotCYValue.addActionListener(this::performSave);
		geoPivotDXValue.addActionListener(this::performSave);
		geoPivotDYValue.addActionListener(this::performSave);
		headPositionXValue.addActionListener(this::performSave);
		headPositionYValue.addActionListener(this::performSave);
		headRadiusValue.addActionListener(this::performSave);
		
	}
	//=============================================================================================

	//=============================================================================================
	private void teardownActions() {
		
		performSaveEnabled = false;
		
		networkAddressValue.removeActionListener(this::performSave);
		networkPortValue.removeActionListener(this::performSave);
		geoStepsPerRotationValue.removeActionListener(this::performSave);
		geoRotationRadiusValue.removeActionListener(this::performSave);
		geoPivotAXValue.removeActionListener(this::performSave);
		geoPivotAYValue.removeActionListener(this::performSave);
		geoPivotBXValue.removeActionListener(this::performSave);
		geoPivotBYValue.removeActionListener(this::performSave);
		geoPivotCXValue.removeActionListener(this::performSave);
		geoPivotCYValue.removeActionListener(this::performSave);
		geoPivotDXValue.removeActionListener(this::performSave);
		geoPivotDYValue.removeActionListener(this::performSave);
		headPositionXValue.removeActionListener(this::performSave);
		headPositionYValue.removeActionListener(this::performSave);
		headRadiusValue.removeActionListener(this::performSave);
		
	}
	//=============================================================================================
	
	//=============================================================================================
	public void loadConfig() {

		teardownActions();
		
		MVConfiguration cfg = MVConfiguration.data; 

		networkAddressValue.setText(cfg.wifi.ipAddress);
		networkPortValue.setText("" + cfg.wifi.ipPort);

		geoStepsPerRotationValue.setText("" + cfg.rotation.stepsPerRotation);
		geoRotationRadiusValue.setText("" + cfg.rotation.rotationRadius);
		
		geoPivotAXValue.setText("" + cfg.pivots.a.x);
		geoPivotAYValue.setText("" + cfg.pivots.a.y);
		
		geoPivotBXValue.setText("" + cfg.pivots.b.x);
		geoPivotBYValue.setText("" + cfg.pivots.b.y);
		
		geoPivotCXValue.setText("" + cfg.pivots.c.x);
		geoPivotCYValue.setText("" + cfg.pivots.c.y);
		
		geoPivotDXValue.setText("" + cfg.pivots.d.x);
		geoPivotDYValue.setText("" + cfg.pivots.d.y);

		headPositionXValue.setText("" + cfg.headPosition.x);
		headPositionYValue.setText("" + cfg.headPosition.y);
		
		headRadiusValue.setText("" + cfg.headRadius);
		
		performUseNetwork(null);
		calcCordLengths();
		
		setupActions();
		
		map.repaint(10);
		
	}
	//=============================================================================================

	//=============================================================================================
	private void storeConfig() {
		
		MVConfiguration cfg = MVConfiguration.data;
		
		cfg.wifi.ipAddress = networkAddressValue.getText();
		cfg.wifi.ipPort= Integer.parseInt(networkPortValue.getText());
		
		cfg.rotation.stepsPerRotation = Integer.parseInt(geoStepsPerRotationValue.getText());
		cfg.rotation.rotationRadius = Float.parseFloat(geoRotationRadiusValue.getText());

		cfg.pivots.a.x = Float.parseFloat(geoPivotAXValue.getText());
		cfg.pivots.a.y = Float.parseFloat(geoPivotAYValue.getText());

		cfg.pivots.b.x = Float.parseFloat(geoPivotBXValue.getText());
		cfg.pivots.b.y = Float.parseFloat(geoPivotBYValue.getText());

		cfg.pivots.c.x = Float.parseFloat(geoPivotCXValue.getText());
		cfg.pivots.c.y = Float.parseFloat(geoPivotCYValue.getText());

		cfg.pivots.d.x = Float.parseFloat(geoPivotDXValue.getText());
		cfg.pivots.d.y = Float.parseFloat(geoPivotDYValue.getText());

		cfg.headPosition.x = Float.parseFloat(headPositionXValue.getText());
		cfg.headPosition.y = Float.parseFloat(headPositionYValue.getText());
		cfg.headRadius = Float.parseFloat(headRadiusValue.getText());
		
		MVConfiguration.store();

		map.invalidate();
		map.repaint(10);
		
	}
	//=============================================================================================

	//=============================================================================================
	private void calcCordLengths() {
		
		MVConfiguration cfg = MVConfiguration.data;
		
		Vector2f head = new Vector2f(cfg.headPosition.x, cfg.headPosition.y);
		Vector2f piva = new Vector2f(cfg.pivots.a.x, cfg.pivots.a.y);
		Vector2f pivb = new Vector2f(cfg.pivots.b.x, cfg.pivots.b.y);
		Vector2f pivc = new Vector2f(cfg.pivots.c.x, cfg.pivots.c.y);
		Vector2f pivd = new Vector2f(cfg.pivots.d.x, cfg.pivots.d.y);
		
		Vector2f dlta = new Vector2f(head); dlta.sub(piva);
		Vector2f dltb = new Vector2f(head); dltb.sub(pivb);
		Vector2f dltc = new Vector2f(head); dltc.sub(pivc);
		Vector2f dltd = new Vector2f(head); dltd.sub(pivd);
		
		float lena = dlta.length();
		float lenb = dltb.length();
		float lenc = dltc.length();
		float lend = dltd.length();
		
		float radius = cfg.rotation.rotationRadius;
		float nsteps = cfg.rotation.stepsPerRotation;
		float circum = (float) (2.0 * radius * Math.PI);
		
		float rota = lena / circum;
		float rotb = lenb / circum;
		float rotc = lenc / circum;
		float rotd = lend / circum;

		float stpa = rota * nsteps;
		float stpb = rotb * nsteps;
		float stpc = rotc * nsteps;
		float stpd = rotd * nsteps;
		
		String pattern = "%4.2f | %4.2f | %4.2f";
		String stra = String.format(pattern, lena, rota, stpa);
		String strb = String.format(pattern, lenb, rotb, stpb);
		String strc = String.format(pattern, lenc, rotc, stpc);
		String strd = String.format(pattern, lend, rotd, stpd);
		
		this.headCordAValue.setText(stra);
		this.headCordBValue.setText(strb);
		this.headCordCValue.setText(strc);
		this.headCordDValue.setText(strd);
		
	}
	//=============================================================================================
	
	//=============================================================================================
	private void performUseNetwork(ActionEvent e) {
		
		networkAddressLabel.setEnabled(true);
		networkAddressValue.setEnabled(true);
		
		networkPortLabel.setEnabled(true);
		networkPortValue.setEnabled(true);
		
	}
	//=============================================================================================

	//=============================================================================================
	private boolean performSaveEnabled = false;
	//=============================================================================================
	
	//=============================================================================================
	private void performSave(ActionEvent e) {
		if (performSaveEnabled) {
	 		this.storeConfig();
			this.calcCordLengths();
		}
	}
	//=============================================================================================
	
}
//*************************************************************************************************
