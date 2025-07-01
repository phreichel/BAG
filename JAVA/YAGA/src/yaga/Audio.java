//*****************************************************************************
package yaga;
//*****************************************************************************

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
		for (var clipName : clips.keySet()) {
			var clipFile = clips.get(clipName);
			register(clipName, clipFile);
		}
	}
	//=========================================================================
	
	//=========================================================================
	public void register(String clipName, File clipFile) {
		try {
			var inputStream = AudioSystem.getAudioInputStream(clipFile);
			var clip = AudioSystem.getClip();
			clip.open(inputStream);
			clipMap.put(clipName, clip);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//=========================================================================

	//=========================================================================
	public void remove(String ...  clipNames) {
		for (var name : clipNames) {
			remove(name);
		}
	}
	//=========================================================================
	
	//=========================================================================
	public void remove(Collection<String> clipNames) {
		for (var name : clipNames) {
			remove(name);
		}
	}
	//=========================================================================
	
	//=========================================================================
	public void remove(String name) {
		var target = clipMap.remove(name);
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
		var target = clipMap.get(name);
		if (target != null) {
			target.setFramePosition(0);
			target.start();
		}
	}
	//=========================================================================
	
	//=========================================================================
	public void done() {
		for (var key : clipMap.keySet()) {
			var value = clipMap.get(key);
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
