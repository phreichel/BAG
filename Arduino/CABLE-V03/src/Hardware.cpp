#include "Hardware"

//=============================================================================
inline const int SHIELD_ENABLE_PIN = 8;
//=============================================================================
inline const int SHIELD_XAXIS_STEP_PIN      = 2;
inline const int SHIELD_XAXIS_DIRECTION_PIN = 5;
inline const int SHIELD_YAXIS_STEP_PIN      = 3;
inline const int SHIELD_YAXIS_DIRECTION_PIN = 6;
inline const int SHIELD_ZAXIS_STEP_PIN      = 4;
inline const int SHIELD_ZAXIS_DIRECTION_PIN = 7;
inline const int SHIELD_AAXIS_STEP_PIN      = 12;
inline const int SHIELD_AAXIS_DIRECTION_PIN = 13;
//=============================================================================
inline const int SHIELD_XENDSTOP_PIN = A3;
inline const int SHIELD_YENDSTOP_PIN = 10;
inline const int SHIELD_ZENDSTOP_PIN =  9;
inline const int SHIELD_AENDSTOP_PIN = 11;
//=============================================================================
inline const int MOTOR_SPEED = 800;
inline const int MOTOR_RAMP  = 200;
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
	setupMotor(A, SHIELD_XAXIS_STEP_PIN, SHIELD_XAXIS_DIRECTION_PIN);
	setupMotor(B, SHIELD_YAXIS_STEP_PIN, SHIELD_YAXIS_DIRECTION_PIN);
	setupMotor(C, SHIELD_ZAXIS_STEP_PIN, SHIELD_ZAXIS_DIRECTION_PIN);
	setupMotor(D, SHIELD_AAXIS_STEP_PIN, SHIELD_AAXIS_DIRECTION_PIN);

};
//=============================================================================

//=============================================================================
void Hardware::setupMotor(MoToStepper& motor, int stp, int dir) {
  motor.attach(stp, dir);
  motor.setRampLen( MOTOR_RAMP );
  motor.setSpeed( MOTOR_SPEED );
}
//=============================================================================

//=============================================================================
void Hardware::loop() {
};
//=============================================================================

//=============================================================================
bool Hardware::enabled() {
	return digitalRead(PIN_EN) == HIGH;
};
//=============================================================================

//=============================================================================
void Hardware::enabled(bool enabled) {
  digitalWrite(PIN_EN, enabled? HIGH : LOW);
};
//=============================================================================
