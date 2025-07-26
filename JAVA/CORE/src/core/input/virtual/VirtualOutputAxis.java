//************************************************************************************************
package core.input.virtual;

import core.event.EventManager;

//************************************************************************************************

//************************************************************************************************
public class VirtualOutputAxis extends VirtualAxisBase{

	//============================================================================================
	private final IVirtualAxis source;
	private final EventManager eventManager;
	private final String       channel;
	//============================================================================================
	
	//============================================================================================
	public VirtualOutputAxis(String ident, IVirtualAxis source, EventManager eventManager, String channel) {
		super(ident);
		this.source = source;
		this.eventManager = eventManager;
		this.channel = channel;
	}
	//============================================================================================
	
	//============================================================================================
	@Override
	public void update(int nFrames, long periodNs) {
		if (this.source.hasChanged()) {
			this.value = this.source.getValue();
			this.source.confirmChanged();
			this.hasChanged = true;
			var e = eventManager.createEvent();
			e.type = VirtualEventType.CHANNEL;
			e.text = channel;
			e.value = this.value;
			eventManager.postEvent(e);
		}
	}
	//============================================================================================

}
//************************************************************************************************
