//~ #include <AccelStepper.h>
#include <MobaTools.h>

const int PIN_EN   = 8;   // Enable (LOW = Treiber EIN)

const int PIN_XSTP = 2;   // STEP
const int PIN_XDIR = 5;   // DIR

const int PIN_YSTP = 3;   // STEP
const int PIN_YDIR = 6;   // DIR

const int PIN_ZSTP = 4;   // STEP
const int PIN_ZDIR = 7;   // DIR

const int PIN_ASTP = 12;   // STEP
const int PIN_ADIR = 13;   // DIR

//~ AccelStepper X(AccelStepper::DRIVER, PIN_XSTP, PIN_XDIR);
//~ AccelStepper Y(AccelStepper::DRIVER, PIN_YSTP, PIN_YDIR);
//~ AccelStepper Z(AccelStepper::DRIVER, PIN_ZSTP, PIN_ZDIR);
//~ AccelStepper A(AccelStepper::DRIVER, PIN_ASTP, PIN_ADIR);

MoToStepper X(200*16, A4988);
MoToStepper Y(200*16, A4988);
MoToStepper Z(200*16, A4988);
MoToStepper A(200*16, A4988);

// Test-Parameter
const float MAX_SPEED = 1200.f;
const float ACCEL     =  800.f;

const unsigned int SPEED = 800;
const unsigned int RAMP  = 200;

long  target = -750;

void setup() {
 
  Serial.begin(115200);

  while (!Serial) {;}  // für Leonardo/Micro irrelevant beim UNO, schadet nicht

  pinMode(PIN_EN, OUTPUT);
  digitalWrite(PIN_EN, LOW);    // Treiber EIN

  //~ X.setAcceleration(ACCEL);
  //~ X.setMaxSpeed(MAX_SPEED);
  //~ X.setMinPulseWidth(3);

  //~ Y.setAcceleration(ACCEL);
  //~ Y.setMaxSpeed(MAX_SPEED);
  //~ Y.setMinPulseWidth(3);

  //~ Z.setAcceleration(ACCEL);
  //~ Z.setMaxSpeed(MAX_SPEED);
  //~ Z.setMinPulseWidth(3);

  //~ A.setAcceleration(ACCEL);
  //~ A.setMaxSpeed(MAX_SPEED);
  //~ A.setMinPulseWidth(3);

  X.attach(PIN_XSTP, PIN_XDIR);
  X.setRampLen( RAMP );
  X.setSpeed( SPEED );

  Y.attach(PIN_YSTP, PIN_YDIR);
  Y.setRampLen( RAMP );
  Y.setSpeed( SPEED );

  Z.attach(PIN_ZSTP, PIN_ZDIR);
  Z.setRampLen( RAMP );
  Z.setSpeed( SPEED );

  A.attach(PIN_ASTP, PIN_ADIR);
  A.setRampLen( RAMP );
  A.setSpeed( SPEED );
  
  Serial.println(F("AccelStepper Test gestartet"));

}

void loop() {
  
  //~ if (
		//~ (X.distanceToGo()==0) &&
		//~ (Y.distanceToGo()==0) &&
		//~ (Z.distanceToGo()==0) &&
		//~ (A.distanceToGo()==0)
  //~ ){
    
    //~ delay(5000);
    //~ Serial.println(F("Richtung gewechselt."));
    
    //~ target = -target;
    
    //~ X.moveTo( target);
    //~ Y.moveTo(-target);
    //~ Z.moveTo( target);
    //~ A.moveTo(-target);
    
  //~ }

  //~ X.run();
  //~ Y.run();
  //~ Z.run();
  //~ A.run();

  if (
		(X.moving()==0) &&
		(Y.moving()==0) &&
		(Z.moving()==0) &&
		(A.moving()==0)
  ){
    
    delay(2000);
    Serial.println(F("Richtung gewechselt."));
    
    target = -target;
    
    X.moveTo(target);
    Y.moveTo(target);
    Z.moveTo(target);
    A.moveTo(target);
    
  }
  
}
