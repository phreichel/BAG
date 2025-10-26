//==============================================================================
#include <Wire.h>
#include <AccelStepper.h>
#include <Adafruit_PWMServoDriver.h>
//==============================================================================


//==============================================================================
const int SHOULDER       = 0x04;
const int ELBOW          = 0x08;
const int WRIST          = 0x0C;
//==============================================================================
const int ROT_A          = 5;
const int ROT_B          = 6;
const int ROT_C          = 7;
const int ROT_D          = 8;
//==============================================================================
const float SPEED        = 3.0;
const int   ROT_MID      = 0;
const int   SHOULDER_MID = 375;
const int   ELBOW_MID    = 325;
const int   WRIST_MID    = 345;
//==============================================================================


//==============================================================================
long mark;
bool pressed = false;
int  mode = 0;
//==============================================================================
int target_rotation = ROT_MID;
int target_shoulder = SHOULDER_MID;
int target_elbow    = ELBOW_MID;
int target_wrist    = WRIST_MID;
//==============================================================================
int current_rotation = target_rotation;
int current_shoulder = target_shoulder;
int current_elbow    = target_elbow;
int current_wrist    = target_wrist;
//==============================================================================
AccelStepper            stp(AccelStepper::FULL4WIRE, ROT_A, ROT_D, ROT_B, ROT_C);
Adafruit_PWMServoDriver pwm = Adafruit_PWMServoDriver();
//==============================================================================


//==============================================================================
void set_target(
		int _rotation,
		int _shoulder,
		int _elbow,
		int _wrist) {
	target_rotation = _rotation;
	target_shoulder = _shoulder;
	target_elbow   = _elbow;
	target_wrist    = _wrist;
};
//==============================================================================


//==============================================================================
void set_pose(
		int _rotation,
		int _shoulder,
		int _elbow,
		int _wrist) {

	current_rotation = _rotation;
	current_shoulder = _shoulder;
	current_elbow    = _elbow;
	current_wrist    = _wrist;
	
	/*
	bool doRotation = (target_rotation - current_rotation) != 0;
	bool doShoulder = (target_shoulder - current_shoulder) != 0;
	bool doElbow    = (target_elbow    - current_elbow   ) != 0;
	bool doWrist    = (target_wrist    - current_wrist   ) != 0;
	
	if (doRotation) stp.moveTo(current_rotation);
	if (doShoulder) pwm.setPWM(SHOULDER, 0, current_shoulder);
	if (doElbow)    pwm.setPWM(ELBOW, 0, current_elbow);
	if (doWrist)    pwm.setPWM(WRIST, 0, current_wrist);
	*/

	stp.moveTo(current_rotation);
	pwm.setPWM(SHOULDER, 0, current_shoulder);
	pwm.setPWM(ELBOW,    0, current_elbow);
	pwm.setPWM(WRIST,    0, current_wrist);
	
};
//==============================================================================


//==============================================================================
void update(float _dT) {

	if (!pressed && digitalRead(3) == LOW) {
		pressed = true;
		update_mode();
	} else if (pressed && digitalRead(3) == HIGH) {
		pressed = false;
	}

	float dR = (target_rotation - current_rotation) * _dT * SPEED;
	float dS = (target_shoulder - current_shoulder) * _dT * SPEED;
	float dE = (target_elbow    - current_elbow)    * _dT * SPEED;
	float dW = (target_wrist    - current_wrist)    * _dT * SPEED;
	
	current_rotation += (int) round(dR);
	current_shoulder += (int) round(dS);
	current_elbow    += (int) round(dE);
	current_wrist    += (int) round(dW);
	
	set_pose(
		current_rotation,
		current_shoulder,
		current_elbow,
		current_wrist);

}
//==============================================================================


//==============================================================================
void setup() {
	
	Serial.begin(9600);

	Serial.println("INIT BUTTON");
	pressed = false;
	mode    = 0;
	pinMode(3, INPUT_PULLUP);

	Serial.print("INITIAL MODE: ");
	Serial.print(mode);
	Serial.println();

	Serial.println("INIT STEPPER");
	stp.setAcceleration(1e9);
	stp.setMaxSpeed(300);
	stp.setSpeed(300);
	stp.setCurrentPosition(0);

	Serial.println("INIT SERVOS");
	pwm.begin();
	pwm.setPWMFreq(50);
	delay(500);

	Serial.println("INIT POSE");
	set_target(
		target_rotation,
		target_shoulder,
		target_elbow,
		target_wrist);
	delay(500);

	mark = millis();

};
//==============================================================================

//==============================================================================
void update_mode() {
	mode = (mode+1) % 6;
	Serial.print("NEW MODE: ");
	Serial.print(mode);
	Serial.println();
	switch (mode) {
		case 0:
			set_target(
				ROT_MID,
				SHOULDER_MID,
				ELBOW_MID,
				WRIST_MID);
			break;
		case 1:
			set_target(
				ROT_MID + 512,
				target_shoulder,
				target_elbow,
				target_wrist);
			break;
		case 2:
			set_target(
				ROT_MID - 512,
				target_shoulder,
				target_elbow,
				target_wrist);
			break;
		case 3:
			set_target(
				ROT_MID,
				SHOULDER_MID,
				ELBOW_MID,
				WRIST_MID + 125);
			break;
		case 4:
			set_target(
				ROT_MID,
				SHOULDER_MID - 125,
				ELBOW_MID - 125,
				WRIST_MID - 125);
			break;
		case 5:
			set_target(
				ROT_MID,
				SHOULDER_MID + 125,
				ELBOW_MID + 125,
				WRIST_MID + 125);
			break;
	};
}
//==============================================================================

//==============================================================================
void loop() {
	long now = millis();
	long delta = now - mark;
	if (delta >= 50) {
		mark = now;
		float dT = (float) delta / 1000.;		
		update(dT);
	}
	stp.run();
}
//==============================================================================
