#include "Hardware.h"

//=============================================================================
inline const int SHIELD_ENABLE_PIN = 8;
//=============================================================================
inline const unsigned int SHIELD_XAXIS_STEP_PIN      = 2;
inline const unsigned int SHIELD_XAXIS_DIRECTION_PIN = 5;
inline const unsigned int SHIELD_YAXIS_STEP_PIN      = 3;
inline const unsigned int SHIELD_YAXIS_DIRECTION_PIN = 6;
inline const unsigned int SHIELD_ZAXIS_STEP_PIN      = 4;
inline const unsigned int SHIELD_ZAXIS_DIRECTION_PIN = 7;
inline const unsigned int SHIELD_AAXIS_STEP_PIN      = 12;
inline const unsigned int SHIELD_AAXIS_DIRECTION_PIN = 13;
//=============================================================================
inline const unsigned int SHIELD_XENDSTOP_PIN = A3;
inline const unsigned int SHIELD_YENDSTOP_PIN = 10;
inline const unsigned int SHIELD_ZENDSTOP_PIN =  9;
inline const unsigned int SHIELD_AENDSTOP_PIN = 11;
//=============================================================================
inline const unsigned int MOTOR_SPEED = 800;
inline const unsigned int MOTOR_RAMP  = 200;
//=============================================================================

//=============================================================================
Hardware::Hardware() {};
//=============================================================================

//=============================================================================
Hardware::~Hardware() {};
//=============================================================================

//=============================================================================
void Hardware::setup() {

	// Enable Pin
	pinMode(SHIELD_ENABLE_PIN, OUTPUT);

	pinMode(SHIELD_XENDSTOP_PIN, INPUT_PULLUP);
	pinMode(SHIELD_YENDSTOP_PIN, INPUT_PULLUP);
	pinMode(SHIELD_ZENDSTOP_PIN, INPUT_PULLUP);
	pinMode(SHIELD_AENDSTOP_PIN, INPUT_PULLUP);

	// Motors and Motor Pins
	setupMotor(A, SHIELD_AAXIS_STEP_PIN, SHIELD_AAXIS_DIRECTION_PIN);
	setupMotor(B, SHIELD_YAXIS_STEP_PIN, SHIELD_YAXIS_DIRECTION_PIN);
	setupMotor(C, SHIELD_XAXIS_STEP_PIN, SHIELD_XAXIS_DIRECTION_PIN);
	setupMotor(D, SHIELD_ZAXIS_STEP_PIN, SHIELD_ZAXIS_DIRECTION_PIN);

};
//=============================================================================

//=============================================================================
void Hardware::setupMotor(MoToStepper& motor, int stp, int dir) {
  motor.attach(stp, dir);
  motor.setRampLen( MOTOR_RAMP );
  motor.setSpeed( MOTOR_SPEED );
  motor.setZero();
}
//=============================================================================

//=============================================================================
void Hardware::loop() {
};
//=============================================================================

//=============================================================================
bool Hardware::enabled() {
	return enabledFlag;
};
//=============================================================================

//=============================================================================
void Hardware::enabled(bool _enabled) {
  enabledFlag = _enabled;
  digitalWrite(SHIELD_ENABLE_PIN , enabledFlag? HIGH : LOW);
};
//=============================================================================

//=============================================================================
void Hardware::zero() {
	A.setZero();
	B.setZero();
	C.setZero();
	D.setZero();
};
//=============================================================================

//=============================================================================
void Hardware::stop() {
	A.stop();
	B.stop();
	C.stop();
	D.stop();
};
//=============================================================================

//=============================================================================
void Hardware::home() {
	move(0,0,0,0);
};
//=============================================================================

//=============================================================================
void Hardware::move(int a, int b, int c, int d) {
	A.moveTo(a);
	B.moveTo(b);
	C.moveTo(c);
	D.moveTo(d);
};
//=============================================================================

//=============================================================================
void Hardware::relmove(int a, int b, int c, int d) {
	A.move(a);
	B.move(b);
	C.move(c);
	D.move(d);
};
//=============================================================================
