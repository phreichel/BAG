//************************************************************************************************
package core.input.virtual;
//************************************************************************************************

import java.util.HashMap;
import java.util.Map;

import core.event.EventManager;


//************************************************************************************************
public class VirtualDevice {

	//============================================================================================
	private final EventManager eventManager;
	private final String ident;
	private Map<String, IVirtualAxis>   axes    = new HashMap<>();
	private Map<String, IVirtualAction> actions = new HashMap<>();
	//============================================================================================ 

	//============================================================================================
	public VirtualDevice(String ident, EventManager eventManager) {
		this.ident = ident;
		this.eventManager = eventManager;
	}
	//============================================================================================

	//============================================================================================
	public String getIdent() {
		return ident;
	}
	//============================================================================================
	
	//============================================================================================
	public void add(String axisName, IVirtualAxis axis) {
		axes.put(axisName, axis);
	}
	//============================================================================================

	//============================================================================================
	public void add(String actionName, IVirtualAction action) {
		actions.put(actionName, action);
	}
	//============================================================================================

	//============================================================================================
	public void update(int nFrames, long periodNs) {
		boolean hasChanged = false;
		var data = new DeviceData();
		for (var axisName : axes.keySet()) {
			var axis = axes.get(axisName);
			data.set(axisName, axis.getValue());
			if (axis.hasChanged()) {
				hasChanged = true;
				axis.confirmChanged();
			}
		}
		for (var actionName : actions.keySet()) {
			var action = actions.get(actionName);
			if (action.hasTriggered()) {
				data.set(actionName);
				hasChanged = true;
				action.confirmTriggered();
			}
		}
		if (hasChanged) {
			var e = eventManager.createEvent();
			e.type = VirtualEventType.DEVICE;
			e.text = this.getIdent();
			e.data = data;
			eventManager.postEvent(e);
		}
	}
	//============================================================================================
	
}
//************************************************************************************************
