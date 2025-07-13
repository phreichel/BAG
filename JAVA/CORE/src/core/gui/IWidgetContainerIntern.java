//************************************************************************************************
package core.gui;
//************************************************************************************************

//************************************************************************************************
public interface IWidgetContainerIntern {

	//============================================================================================
	public void    _addChild(IWidget child);
	public void    _addChild(int idx, IWidget child);
	public boolean _removeChild(IWidget child);
	public IWidget _removeChild(int idx);
	public boolean _relocateChild(int fromIdx, int toIdx);
	public boolean _relocateChild(IWidget child, int toIdx);
	public boolean _relocateChild(IWidget child, IWidget before);
	//============================================================================================
	
}
//************************************************************************************************
