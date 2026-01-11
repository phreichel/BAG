#ifndef __Console_h__
#define __Console_h__

#include <Arduino.h>
#include "Hardware.h"
#include "Model.h"

//=============================================================================
class Console {

	//-------------------------------------------------------------------------
	public:
	//-------------------------------------------------------------------------

	Console(Hardware* _hardwarePtr);
	~Console();

	void setup();
	void loop();

	//-------------------------------------------------------------------------
	private:
	//-------------------------------------------------------------------------

	void enable();
	void calibrate();
	void raw(String& line);
	void relraw(String& line);
	void home();
	void center();
	void jog(String& line);

	Model     model;
	Hardware* hardwarePtr;

};
//=============================================================================

#endif
