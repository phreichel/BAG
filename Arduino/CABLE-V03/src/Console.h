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
	void raw(char* line);
	void relraw(char* line);
	void home();
	void center();
	void jog(char* line);

	Model     model;
	Hardware* hardwarePtr;

};
//=============================================================================

#endif
