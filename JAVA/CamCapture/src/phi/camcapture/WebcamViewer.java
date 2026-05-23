//*****************************************************************************
package phi.camcapture;
//*****************************************************************************

//*****************************************************************************
import com.github.sarxos.webcam.Webcam;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
//*****************************************************************************

//*****************************************************************************
public class WebcamViewer extends JPanel {

	//=========================================================================
	private static final long serialVersionUID = 1L;
	//=========================================================================
	
	//=========================================================================
	private Webcam webcam;
    private BufferedImage image;
	//=========================================================================

	//=========================================================================
    public WebcamViewer() {
        
    	Dimension panelSize = new Dimension(640, 480);
    	this.setPreferredSize(panelSize);
    	this.setSize(panelSize);
    	this.setMinimumSize(panelSize);
    	this.setMaximumSize(panelSize);
    	
    	webcam = Webcam.getDefault();
        webcam.setViewSize(new Dimension(640, 480));
        webcam.open();

        new Thread(this::captureLoop).start();
    }
	//=========================================================================

	//=========================================================================
    private void captureLoop() {
        while (true) {

            BufferedImage frame = webcam.getImage();

            frame = filter(frame);   // Filterpipeline

            image = frame;

            repaint();

            try {
                Thread.sleep(16);   // ~60 FPS
            } catch (InterruptedException ignored) {}
        }
    }
	//=========================================================================

	//=========================================================================
    private BufferedImage filter(BufferedImage input) {
    	
        // hier Filter einbauen
    	int w = input.getWidth();
    	int h = input.getHeight();
    	
    	for (int y=0; y<h; y++) {
        	for (int x=0; x<w; x++) {
        		int color = input.getRGB(x, y);
        		int r = color >> 16 & 0xff;
        		int g = color >>  8 & 0xff;
        		int b = color >>  0 & 0xff;
        		int gs = (r+g+b) / 3;
        		//int rgb = ((x+y) % 2) == 0 ? gs*0x10000 : gs + gs*0x100 + gs*0x10000;
        		int rgb = gs*0x100;
        		input.setRGB(x, y, rgb);
        	}
    	}
    	
        return input;
    }
	//=========================================================================

	//=========================================================================
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, null);
            g.setColor(Color.GREEN);
            g.drawRect(10, 10, this.getWidth()-20, this.getHeight()-20);
        }
    }
	//=========================================================================

}
//*****************************************************************************
