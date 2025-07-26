//************************************************************************************************
package core.input.virtual;

import core.event.EventManager;

//************************************************************************************************

//************************************************************************************************
public class VirtualOutputText extends VirtualTextBase {

	//============================================================================================
	private final IVirtualText source;
	private final EventManager eventManager;
	//============================================================================================
	
	//============================================================================================
	public VirtualOutputText(String ident, IVirtualText source, EventManager eventManager) {
		super(ident);
		this.source = source;
		this.eventManager = eventManager;
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
			e.type = VirtualEventType.TEXT;
			e.text = this.value;
			eventManager.postEvent(e);
		}
	}
	//============================================================================================

}
//************************************************************************************************
