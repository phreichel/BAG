#ifndef __Hardware_h__
#define __Hardware_h__

#include <MobaTools.h>

//=============================================================================
#define STEPPER_DRIVER_TYPE A4988
#define STEPS_PER_ROTATION 200
#define MICROSTEP_RESOLUTION 16
#define MICROSTEPS_PER_ROTATION STEPS_PER_ROTATION * MICROSTEP_RESOLUTION
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
	void enabled(bool enabled);

	//-------------------------------------------------------------------------
	private:
	//-------------------------------------------------------------------------

	void setupMotor(MoToStepper& stepper, int stp, int dir);

	MoToStepper A(MICROSTEPS_PER_ROTATION, STEPPER_DRIVER_TYPE);
	MoToStepper B(MICROSTEPS_PER_ROTATION, STEPPER_DRIVER_TYPE);
	MoToStepper C(MICROSTEPS_PER_ROTATION, STEPPER_DRIVER_TYPE);
	MoToStepper D(MICROSTEPS_PER_ROTATION, STEPPER_DRIVER_TYPE);

};
//=============================================================================

#endif
