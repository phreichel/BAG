#ifndef __CABLE_V2__
#define __CABLE_V2__

#include <MobaTools.h>

extern MoToStepper X;
extern MoToStepper Y;
extern MoToStepper Z;
extern MoToStepper A;

const int PIN_EN   = 8;   // Enable (LOW = Treiber EIN)

const int PIN_XSTP = 2;   // STEP
const int PIN_XDIR = 5;   // DIR

const int PIN_YSTP = 3;   // STEP
const int PIN_YDIR = 6;   // DIR

const int PIN_ZSTP = 4;   // STEP
const int PIN_ZDIR = 7;   // DIR

const int PIN_ASTP = 12;   // STEP
const int PIN_ADIR = 13;   // DIR

const int PIN_CALI1 = 9;
const int PIN_CALI2 = 10;
const int PIN_CALI3 = 11;
const int PIN_CALI4 = A3;

// Test-Parameter
const float MAX_SPEED = 1200.f;
const float ACCEL     =  800.f;

const unsigned int SPEED = 800;
const unsigned int RAMP  = 200;

void initSerial();
void initHoldPins();
void initMotors();
void initEnablePin();

void setEnabled(bool enabled);
void updateHoldPins(long now);

#endif
