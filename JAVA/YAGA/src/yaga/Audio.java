//*****************************************************************************
package yaga;
//*****************************************************************************

import java.io.File;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

//*****************************************************************************
public class Audio {

	//=========================================================================
	private Map<String, Clip> clipMap;
	//=========================================================================
	
	//=========================================================================
	public Audio() {
		clipMap = new HashMap<>();
	}
	//=========================================================================

	//=========================================================================
	public void register(Map<String, File> clips) {
		for (String clipName : clips.keySet()) {
			File clipFile = clips.get(clipName);
			register(clipName, clipFile);
		}
	}
	//=========================================================================
	
	//=========================================================================
	public void register(String clipName, File clipFile) {
		try {
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(clipFile);
			Clip clip = AudioSystem.getClip();
			clip.open(inputStream);
			clipMap.put(clipName, clip);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//=========================================================================

	//=========================================================================
	public void remove(String ...  clipNames) {
		for (String name : clipNames) {
			remove(name);
		}
	}
	//=========================================================================
	
	//=========================================================================
	public void remove(Collection<String> clipNames) {
		for (String name : clipNames) {
			remove(name);
		}
	}
	//=========================================================================
	
	//=========================================================================
	public void remove(String name) {
		Clip target = clipMap.remove(name);
		if (target.isActive()) target.stop();
		if (target.isOpen()) target.close();		
	}
	//=========================================================================

	//=========================================================================
	public void init() {}
	//=========================================================================
	
	//=========================================================================
	public void update() {}
	//=========================================================================

	//=========================================================================
	public void play(String name) {
		Clip target = clipMap.get(name);
		if (target != null) {
			target.setFramePosition(0);
			target.start();
		}
	}
	//=========================================================================
	
	//=========================================================================
	public void done() {
		for (String key : clipMap.keySet()) {
			Clip value = clipMap.get(key);
			if (value.isActive()) {
				value.stop();
			}
			if (value.isOpen()) {
				value.close();
			}
		}
		clipMap.clear();
	}
	//=========================================================================

}
//*****************************************************************************
