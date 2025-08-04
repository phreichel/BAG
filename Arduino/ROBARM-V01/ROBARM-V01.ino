//==============================================================================
#include <Wire.h>
#include <AccelStepper.h>
#include <Adafruit_PWMServoDriver.h>
//==============================================================================

//==============================================================================
const int SHOULDER = 0x04;
const int ELLBOW   = 0x08;
const int WRIST    = 0x0C;
//==============================================================================

//==============================================================================
AccelStepper            stp(AccelStepper::FULL4WIRE, 5, 8, 6, 7);
Adafruit_PWMServoDriver pwm = Adafruit_PWMServoDriver();
//==============================================================================

//==============================================================================
float speed = 3;
//==============================================================================

//==============================================================================
int target_shoulder = 375;
int target_ellbow   = 325;
int target_wrist    = 325;
//==============================================================================

//==============================================================================
int current_shoulder = 375;
int current_ellbow   = 325;
int current_wrist    = 325;
//==============================================================================

//==============================================================================
long past;
long clock;
//==============================================================================

//==============================================================================
void target_apply(
		int _shoulder,
		int _ellbow,
		int _wrist) {
	target_shoulder = _shoulder;
	target_ellbow   = _ellbow;
	target_wrist    = _wrist;
};
//==============================================================================

//==============================================================================
void current_apply(
		int _shoulder,
		int _ellbow,
		int _wrist) {

	current_shoulder = _shoulder;
	current_ellbow   = _ellbow;
	current_wrist    = _wrist;
	
	pwm.setPWM(SHOULDER, 0, current_shoulder);
	pwm.setPWM(ELLBOW, 0, current_ellbow);
	pwm.setPWM(WRIST, 0, current_wrist);
	
};
//==============================================================================

//==============================================================================
void update(float _dT) {

	float dS = (target_shoulder - current_shoulder) * _dT * speed;
	float dE = (target_ellbow   - current_ellbow) * _dT * speed;
	float dW = (target_wrist    - current_wrist) * _dT * speed;
	
	current_shoulder += (int) round(dS);
	current_ellbow   += (int) round(dE);
	current_wrist    += (int) round(dW);

	pwm.setPWM(SHOULDER, 0, current_shoulder);
	pwm.setPWM(ELLBOW, 0, current_ellbow);
	pwm.setPWM(WRIST, 0, current_wrist);
	
}
//==============================================================================

//==============================================================================
int stptarget = 1024;
//==============================================================================

//==============================================================================
void setup() {
	
	Serial.begin(9600);
	
	stp.setAcceleration(1e9);
	stp.setMaxSpeed(300);
	stp.setSpeed(300);

	stp.setCurrentPosition(0);
	stp.moveTo(stptarget);
	
	pwm.begin();
	pwm.setPWMFreq(50);

	delay(500);
	current_apply(
		target_shoulder,
		target_ellbow,
		target_wrist);
	delay(500);

	past = millis();
	
};
//==============================================================================

//==============================================================================
void loop() {
	
	/*

	long mark = millis();
	long delta = mark - past;
	if (delta >= 100) {
		clock += delta;
		past = mark;
		float dT = (float) delta / 1000.;
		update(dT);
	}

	if (clock >= 2000) {
		target_apply(500, 450, 325);
	}

	if (clock >= 4000) {
		target_apply(250, 200, 325);
	}

	if (clock >= 6000) {
		clock = 0;
		target_apply(375, 325, 325);
	}
	
	*/
	
	//stp.runSpeed();
	if (!stp.run()) {
		stptarget = 1024 - stptarget;
		stp.moveTo(stptarget);
	}
	//yield();
	
}
//==============================================================================
