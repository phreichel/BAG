//*****************************************************************************
package phi.camcapture;
//*****************************************************************************

import java.awt.BorderLayout;
import java.awt.Dimension;

//*****************************************************************************
import javax.swing.JFrame;
import javax.swing.JPanel;
//*****************************************************************************

//*****************************************************************************
public class Main {

	//=========================================================================
    public static void main(String[] args) {

        JFrame       frame = new JFrame("Webcam");
        WebcamViewer panel = new WebcamViewer();

        JPanel tools = new JPanel();
        tools.setPreferredSize(new Dimension(200, 480));

        JPanel spacer = new JPanel();
        spacer.setPreferredSize(new Dimension(840, 10));
        
        frame.add(tools, BorderLayout.WEST);
        frame.add(spacer, BorderLayout.SOUTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
	//=========================================================================

}
//*****************************************************************************
