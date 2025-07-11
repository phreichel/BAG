//************************************************************************************************
package core.event;
//************************************************************************************************

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import core.api.IGameHandler;

//************************************************************************************************
public class EventManager {

	//============================================================================================
	private List<GameEvent> pool   = new ArrayList<>();
	private List<GameEvent> input  = new ArrayList<>();
	private List<GameEvent> output = new ArrayList<>();
	//============================================================================================

	//============================================================================================
	private Set<Class<? extends Enum<?>>> eventTypeClassSet = new HashSet<>();
	private Map<String,Class<? extends Enum<?>>> eventTypeClassMap = new HashMap<>(); 
	//============================================================================================
	
	//============================================================================================
	public void registerEventTypeClass(Class<? extends Enum<?>> eventTypeClass) {
		eventTypeClassSet.add(eventTypeClass);
		eventTypeClassMap.put(eventTypeClass.getCanonicalName(), eventTypeClass);
	}
	//============================================================================================

	//============================================================================================
	private Map<Enum<?>,List<IGameHandler>> eventHandlerMap = new HashMap<>();
	//============================================================================================

	//============================================================================================
	public void register(Enum<?> type, IGameHandler handler) {
		assert(eventTypeClassSet.contains(type.getClass()));
		var list = eventHandlerMap.get(type);
		if (list == null) {
			list = new ArrayList<>();
			eventHandlerMap.put(type,list);
		}
		if (!list.contains(handler)) {
			list.add(handler);
		}
	}
	//============================================================================================

	//============================================================================================
	public void unregister(Enum<?> type, IGameHandler handler) {
		var list = eventHandlerMap.get(type);
		if (list != null) {
			list.remove(handler);
		}
	}
	//============================================================================================

	//============================================================================================
	public GameEvent createEvent() {
		return alloc();
	}
	//============================================================================================

	//============================================================================================
	public void postEvent(GameEvent event) {
		assert(event.type != null);
		assert(eventTypeClassSet.contains(event.type.getDeclaringClass()));
		input.add(event);
	}
	//============================================================================================
	
	
	//============================================================================================
	public void update() {
		var list = input;
		input = output;
		output = list;
		for (var event : list) {
			var handlers = eventHandlerMap.get(event.type);
			if (handlers != null) {
				for (var handler : handlers) {
					handler.onGameEvent(event);
				}
			}
			free(event);
		}
		list.clear();
	}
	//============================================================================================
	
	//============================================================================================
	private GameEvent alloc() {
		GameEvent event = null;
		try {
			event = pool.removeFirst();
		} catch (NoSuchElementException e) {
			event = new GameEvent();
		}
		return event;
	}
	//============================================================================================

	//============================================================================================
	private void free(GameEvent event ) {
		event.clear();
		pool.add(event);
	}
	//============================================================================================
	
}
//************************************************************************************************
