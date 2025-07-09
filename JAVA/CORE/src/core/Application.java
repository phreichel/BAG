//************************************************************************************************
package core;
//************************************************************************************************

//************************************************************************************************
public class Application implements IApplication {

	//============================================================================================
	private IPlatform platform = new Platform();
	//============================================================================================
	
	//============================================================================================
	@Override
	public void run () {
		
		platform.init();
		platform.setTitle("PETERCHENS MONDFAHRT");

		Asset fontSystem = new Asset("system", "Font", "code", "Arial-PLAIN-20");
		Asset fontPlain = new Asset("plain", "Font", "code", "Arial-PLAIN-20");
		Asset fontBold  = new Asset("bold", "Font", "code", "Arial-BOLD-20");
		
		platform.addAsset(fontSystem);
		platform.addAsset(fontBold);
		platform.addAsset(fontPlain);
		
		platform.addCanvas(this::onPaint);
		while (true)  {
			platform.update();
			Thread.yield();
		}
		
	}
	//============================================================================================

	//============================================================================================
	private void onPaint(IGraphics g) {
		g.setColor(1, 0, 0);
		g.drawPolygon(100, 100, 165, 100, 165, 125, 100, 125);
		g.setColor(0, 1, 0);
		g.drawText("bold", "Hallo Papallo!", 100, 100);
	}
	//============================================================================================
	
}
//************************************************************************************************
