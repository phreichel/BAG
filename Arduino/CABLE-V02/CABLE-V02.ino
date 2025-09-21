#include "CABLE-V02.h"

long TRG_CALI1 = -1;
long TRG_CALI2 = -1;
long TRG_CALI3 = -1;
long TRG_CALI4 = -1;
long TRG_TURN  = -1;
long  target = -750;

void setup() {
 
  initSerial();
  
  Serial.println("=== INIT ===");
  
  initHoldPins();
  initMotors();
  
  initEnablePin();
  setEnabled(true);
  
  Serial.println("=== START ===");

}

void loop() {
  
  long now = millis();
  updateHoldPins(now);
  
  if (
		(X.moving()==0) &&
		(Y.moving()==0) &&
		(Z.moving()==0) &&
		(A.moving()==0) &&
		(TRG_TURN==-1)
  ){
    TRG_TURN = now;
    Serial.println("STOP.");
  } else if (TRG_TURN != -1) {
	long delta = (now-TRG_TURN);
	if (delta >= 2000) {
		TRG_TURN = -1;
		Serial.println("TURN!");
		target = -target;
		X.moveTo(target);
		Y.moveTo(target);
		Z.moveTo(target);
		A.moveTo(target);
	}
  }
  
  delay(1);

}
