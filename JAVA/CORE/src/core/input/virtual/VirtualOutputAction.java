//************************************************************************************************
package core.input.virtual;
//************************************************************************************************

import core.event.EventManager;

//************************************************************************************************
public class VirtualOutputAction extends VirtualActionBase{

	//============================================================================================
	private final IVirtualAction source;
	private final EventManager eventManager;
	//============================================================================================
	
	//============================================================================================
	public VirtualOutputAction(String ident, IVirtualAction source, EventManager eventManager) {
		super(ident, source.getAction());
		this.source = source;
		this.eventManager = eventManager;
	}
	//============================================================================================
	
	//============================================================================================
	@Override
	public void update(int nFrames, long periodNs) {
		if (this.source.hasTriggered()) {
			this.source.confirmTriggered();
			this.hasTriggered = true;
			var e = eventManager.createEvent();
			e.type = VirtualEventType.ACTION;
			e.text = this.getAction();
			eventManager.postEvent(e);
		}
	}
	//============================================================================================

}
//************************************************************************************************
