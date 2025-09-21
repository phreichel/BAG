#include "CABLE-V02.h"

MoToStepper X(200*16, A4988);
MoToStepper Y(200*16, A4988);
MoToStepper Z(200*16, A4988);
MoToStepper A(200*16, A4988);

MoToStepper* Steppers[] {    &X,    &Y,    &Z,    &A };
int  HoldPinIdent[]     { PIN_CALI1, PIN_CALI2, PIN_CALI3, PIN_CALI4 };
bool HoldPinPress[]     { false, false, false, false };
long HoldPinMark[]      {    -1,    -1,    -1,    -1 };

void initSerial() {
  Serial.begin(115200);
  // für Leonardo/Micro irrelevant beim UNO, schadet nicht
  while (!Serial) delay(10);
  // leere Serial streams.
  delay(1000);
  Serial.flush();
}

void initHoldPins() {
  for (int i=0; i<4; i++) {
    pinMode(HoldPinIdent[i], INPUT_PULLUP);
  }
}

void initMotor(MoToStepper& Stepper) {
  Stepper.attach(PIN_ASTP, PIN_ADIR);
  Stepper.setRampLen( RAMP );
  Stepper.setSpeed( SPEED );
}

void initMotors() {
  for (int i=0; i<4; i++) {
    initMotor(Steppers[i]);
  }
}

void initEnablePin() {
  pinMode(PIN_EN, OUTPUT);
}

void setEnabled(bool enabled) {
  int state = enabled? HIGH : LOW;
  digitalWrite(PIN_EN, state);
}

void updateHoldPins(long now) {
	for (int i=0; i<4; i++) {
		int probe = digitalRead(HoldPinIdent[i]);
		if (!HoldPinPress[i] && probe==LOW) {
			HoldPinPress[i] = true;
			HoldPinMark[i] = now;
			Serial.print("PIN: ");
			Serial.print(i);
			Serial.println(" PRESSED");
		}
		else if (HoldPinPress[i] && probe==HIGH && (now - HoldPinMark[i]) >= 10) {
		  HoldPinPress[i] = false;
		  Serial.print("PIN: ");
		  Serial.print(i);
		  Serial.println(" RELEASED");
		}
	}
}

