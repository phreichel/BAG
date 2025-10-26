#ifndef __Hardware_h__
#define __Hardware_h__

#include <MobaTools.h>

//=============================================================================
inline const unsigned int STEPPER_DRIVER_TYPE = A4988;
inline const unsigned int STEPS_PER_ROTATION = 200;
inline const unsigned int MICROSTEP_RESOLUTION = 16;
inline const unsigned int MICROSTEPS_PER_ROTATION = STEPS_PER_ROTATION * MICROSTEP_RESOLUTION;
//=============================================================================

//=============================================================================
class Hardware {

	//-------------------------------------------------------------------------
	public:
	//-------------------------------------------------------------------------

	Hardware();
	~Hardware();

	void setup();
	void loop();

	bool enabled();
	void enabled(bool _enabled);

	void zero();
	void stop();
	void home();
	void move(int a, int b, int c, int d);
	void relmove(int a, int b, int c, int d);

	//-------------------------------------------------------------------------
	private:
	//-------------------------------------------------------------------------

	void setupMotor(MoToStepper& stepper, int stp, int dir);

	bool enabledFlag = true;
	MoToStepper A{MICROSTEPS_PER_ROTATION, STEPPER_DRIVER_TYPE};
	MoToStepper B{MICROSTEPS_PER_ROTATION, STEPPER_DRIVER_TYPE};
	MoToStepper C{MICROSTEPS_PER_ROTATION, STEPPER_DRIVER_TYPE};
	MoToStepper D{MICROSTEPS_PER_ROTATION, STEPPER_DRIVER_TYPE};

};
//=============================================================================

#endif
