#ifndef __Console_h__
#define __Console_h__

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
	void raw();
	void relraw();
	void home();
	void jog();
	String* readInput(char* prompt, String* buffer);

	Model     model;
	Hardware* hardwarePtr;

};
//=============================================================================

#endif
