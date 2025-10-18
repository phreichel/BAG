#include "CABLE-V02.h"

//-----------------------------------------------------------------------------
enum ActionType : uint8_t { MOVE, WAIT };
struct ActionTarget { int32_t X, Y, Z, A; };
struct ActionTime { uint32_t MS; };
struct Action {
	ActionType type;
	union {
		ActionTarget target;
		ActionTime   time;
	};
};
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
constexpr uint8_t ACTION_BUFFER_SIZE = 10;
uint8_t actionBufferStart = 0;
uint8_t actionBufferStop  = 0;
Action actionBuffer[ACTION_BUFFER_SIZE];
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
bool addMoveAction(int32_t x, int32_t y, int32_t z, int32_t a) {
	uint8_t actionBufferNext = (actionBufferStop+1) % ACTION_BUFFER_SIZE;
	if (actionBufferNext == actionBufferStart)
		return false;
	Action* entry = &actionBuffer[actionBufferStop];
	actionBufferStop = actionBufferNext;
	entry->type = ActionType::MOVE;
	entry->target.X = x;
	entry->target.Y = y;
	entry->target.Z = z;
	entry->target.A = a;
	return true;
}
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
bool addWaitAction(uint32_t ms) {
	uint8_t actionBufferNext = (actionBufferStop+1) % ACTION_BUFFER_SIZE;
	if (actionBufferNext == actionBufferStart)
		return false;
	Action* entry = &actionBuffer[actionBufferStop];
	actionBufferStop = actionBufferNext;
	entry->type = ActionType::WAIT;
	entry->time.MS = ms;
	return true;
}
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
bool peekAction(Action* dst) {
	if (dst == nullptr) return false;
	if (actionBufferStart == actionBufferStop) return false;
	Action* src = &actionBuffer[actionBufferStart];
	*dst = *src;
	return true;	
}
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
bool popAction(Action* dst) {
	if (!peekAction(dst)) return false;
	uint8_t actionBufferNext = (actionBufferStart+1) % ACTION_BUFFER_SIZE;
	actionBufferStart = actionBufferNext;
	return true;	
}
//-----------------------------------------------------------------------------

// ---- Geometrie & Mechanik ---------------------------------------------------
struct Vec3 { float x, y, z; };           // cm
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// Aufhängepunkte relativ zum Koordinatenursprung (Mitte oben am Schlitten)
static const float ANCH[4][3] = {
  {-20.0f, -20.0f, 38.8f},  // A (links vorn)
  { 20.0f, -20.0f, 38.8f},  // Y (rechts vorn)
  { 20.0f,  20.0f, 38.8f},  // X (rechts hinten)
  {-20.0f,  20.0f, 38.8f}   // Z (links hinten)
};
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// Schritt-/Trommel-Parameter
static const int   FULL_STEPS_PER_REV = 200;  // 1.8°/Step
static const int   MICROSTEP_DIV      = 16;   // 1/16
static const float DRUM_RADIUS_CM     = 2.5f; // cm
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// Richtungskorrektur pro Motor (falls Verkabelung/Treiber invertiert):
// +1 = "positiv = kürzer", -1 = invertieren
static const int DIR[4] = { +1, +1, +1, +1 };
//-----------------------------------------------------------------------------

// ---- abgeleitete Konstanten -------------------------------------------------
static const int   MICROSTEPS_PER_REV = FULL_STEPS_PER_REV * MICROSTEP_DIV; // 3200
static const float DRUM_CIRCUMF_CM    = 2.0f * 3.14159265358979323846f * DRUM_RADIUS_CM;
static const float MICROSTEPS_PER_CM  = (float)MICROSTEPS_PER_REV / DRUM_CIRCUMF_CM; // ≈101.859
//-----------------------------------------------------------------------------

// ---- Hilfen -----------------------------------------------------------------
static inline float sqr(float v){ return v*v; }
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// euklidische Länge vom Punkt p zur i-ten Aufhängung
static float cableLengthCm(int i, const Vec3& p){
  float dx = ANCH[i][0] - p.x;
  float dy = ANCH[i][1] - p.y;
  float dz = ANCH[i][2] - p.z;
  return sqrtf(sqr(dx) + sqr(dy) + sqr(dz));
}
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// Rechne Längen (cm) für alle vier Kabel
static void cableLengthsCm(const Vec3& p, float L[4]){
  for(int i=0;i<4;++i) L[i] = cableLengthCm(i, p);
}
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// Delta-Länge (cm) -> Mikroschritte (Vorzeichenkonvention: + = kürzen)
// Mit DIR[i] für motorindividuelle Invertierung
static long deltaLenCmToMicrosteps(float dLenCm, int motorIndex){
  // dLenCm < 0 bedeutet: Kabel wird kürzer -> positive Schritte
  float micro = (-dLenCm) * MICROSTEPS_PER_CM;
  long  steps = lroundf(micro);
  return steps * DIR[motorIndex];
}
//-----------------------------------------------------------------------------

// ---- Hauptfunktionen --------------------------------------------------------
// Schritte relativ zu einer REFERENZ-Position pRef (z.B. letzte Istposition)
void stepsFromTo(const Vec3& pRef, const Vec3& pNew, long stepsOut[4]){
  float Lref[4], Lnew[4];
  cableLengthsCm(pRef, Lref);
  cableLengthsCm(pNew, Lnew);
  for(int i=0;i<4;++i){
    float dL = Lnew[i] - Lref[i];           // + => länger, - => kürzer
    stepsOut[i] = deltaLenCmToMicrosteps(dL, i);
  }
}
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// Schritte relativ zur HOME-Position (Ruhe, p=(0,0,0))
void stepsFromHome(const Vec3& pNew, long stepsOut[4]){
  const Vec3 HOME{0.0f, 0.0f, 0.0f};
  stepsFromTo(HOME, pNew, stepsOut);
}
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// Optional: Workspace-Check (x,y in [-20,20], z in [0,38])
static bool withinWorkspace(const Vec3& p){
  return (p.x>=-20 && p.x<=20) && (p.y>=-20 && p.y<=20) && (p.z>=0 && p.z<=38);
}
//-----------------------------------------------------------------------------

// ---- Beispielnutzung --------------------------------------------------------
/*
void setup(){
  Serial.begin(115200);

  Vec3 pTarget{ 10.0f, -5.0f, 20.0f };     // cm
  long steps[4];
  stepsFromHome(pTarget, steps);

  Serial.println(withinWorkspace(pTarget) ? "OK workspace" : "OUT OF RANGE");
  Serial.print("A: "); Serial.println(steps[0]);
  Serial.print("Y: "); Serial.println(steps[1]);
  Serial.print("X: "); Serial.println(steps[2]);
  Serial.print("Z: "); Serial.println(steps[3]);
}

void loop(){}
*/

//-----------------------------------------------------------------------------
const int PIN_EN   = 8;   // Enable (LOW = Treiber EIN)
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
const int PIN_XSTP = 2;   // STEP
const int PIN_XDIR = 5;   // DIR
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
const int PIN_YSTP = 3;   // STEP
const int PIN_YDIR = 6;   // DIR
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
const int PIN_ZSTP = 4;   // STEP
const int PIN_ZDIR = 7;   // DIR
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
const int PIN_ASTP = 12;   // STEP
const int PIN_ADIR = 13;   // DIR
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
const int PIN_CALI_Z = 9;
const int PIN_CALI_Y = 10;
const int PIN_CALI_A = 11;
const int PIN_CALI_X = A3;
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// Test-Parameter
const float MAX_SPEED = 1200.f;
const float ACCEL     =  800.f;
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
const unsigned int SPEED = 800;
const unsigned int RAMP  = 200;
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
MoToStepper X(200*16, A4988);
MoToStepper Y(200*16, A4988);
MoToStepper Z(200*16, A4988);
MoToStepper A(200*16, A4988);
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
MoToStepper* Steppers[] { &Z, &Y, &A, &X };
int  HoldPinIdent[]     { PIN_CALI_Z, PIN_CALI_Y, PIN_CALI_A, PIN_CALI_X };
bool HoldPinPress[]     { false, false, false, false };
long HoldPinMark[]      { -1, -1, -1, -1 };
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
void initSerial() {
  Serial.begin(115200);
  // für Leonardo/Micro irrelevant beim UNO, schadet nicht
  while (!Serial) delay(10);
  // leere Serial streams.
  delay(1000);
  Serial.flush();
}
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
void initHoldPins() {
  for (int i=0; i<4; i++) {
    pinMode(HoldPinIdent[i], INPUT_PULLUP);
  }
}
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
void initMotor(MoToStepper& S, int stp, int dir) {
  S.attach(stp, dir);
  S.setRampLen( RAMP );
  S.setSpeed( SPEED );
}
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
void initMotors() {
	initMotor(X,PIN_XSTP,PIN_XDIR);
	initMotor(Y,PIN_YSTP,PIN_YDIR);
	initMotor(Z,PIN_ZSTP,PIN_ZDIR);
	initMotor(A,PIN_ASTP,PIN_ADIR);
}
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
void initEnablePin() {
  pinMode(PIN_EN, OUTPUT);
}
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
void setEnabled(bool enabled) {
  int state = enabled? HIGH : LOW;
  digitalWrite(PIN_EN, state);
}
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
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
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
bool probeHoldPins() {
	for (int i=0; i<4; i++) {
		if (!HoldPinPress[i]) {
			return false;
		}
	}
	return true;
}
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
bool probeAnyHoldPin() {
	for (int i=0; i<4; i++) {
		if (HoldPinPress[i]) {
			return true;
		}
	}
	return false;
}
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
enum class CalibrateMode : uint8_t { PROBE, RX, PX, RY, PY, RZ, PZ, RA, PA, U1, SET };
enum class StopMode : uint8_t { CENTER, LOWER, STOP };
enum class RunMode : uint8_t { INIT, ERROR, STOP, MOVE, IDLE, SLEEP, WAIT };
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
RunMode       mode          = RunMode::INIT; 
CalibrateMode calibrateMode = CalibrateMode::PROBE;
StopMode      stopMode      = StopMode::CENTER;
int           error         = 0;
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
void initCable() {
  initSerial();
  Serial.println("=== INIT ===");
  initHoldPins();
  initMotors();
  initEnablePin();
  setEnabled(true);
  Serial.println("=== START ===");
}
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
void probe() {
	
	X.move(-50);
	Y.move(-50);
	Z.move(-50);
	A.move(-50);
	
	delay(500);
		
	if (!probeHoldPins()) {
		mode = RunMode::ERROR;
		error = 1;
	} else {
		
		X.stop();
		Y.stop();
		Z.stop();
		A.stop();
		
		X.setZero();
		Y.setZero();
		Z.setZero();
		A.setZero();
		
		Serial.println("PROBED");
		calibrateMode = CalibrateMode::RX;
		
	}

}
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
void tighten(MoToStepper& S, int nextError, CalibrateMode nextMode) {
	if (abs(S.currentPosition()) >= 500) {
		S.stop();
		mode = RunMode::ERROR;
		error = nextError;
	} else if (probeHoldPins() && S.stepsToDo() == 0) {
		S.move(1);
	} else if (!probeHoldPins()) {
		S.stop();
		S.setZero();
		Serial.println("TIGHTENED");
		calibrateMode = nextMode;
	}
}
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
void loosen(MoToStepper& S, int nextError, CalibrateMode nextMode) {
	if (abs(S.currentPosition()) >= 500) {
		S.stop();
		mode = RunMode::ERROR;
		error = nextError;
	} else if (!probeHoldPins() && S.stepsToDo() == 0) {
		S.move(-1);
	} else if (probeHoldPins()) {
		S.stop();
		S.setZero();
		Serial.println("LOOSENED");
		calibrateMode = nextMode;
	}
}
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
void lift(int nextError, CalibrateMode nextMode) {
	if (
		(abs(X.currentPosition()) >= 500) ||
		(abs(Y.currentPosition()) >= 500) ||
		(abs(Z.currentPosition()) >= 500) ||
		(abs(A.currentPosition()) >= 500)
	) {
		X.stop();
		Y.stop();
		Z.stop();
		A.stop();
		mode = RunMode::ERROR;
		error = nextError;
	} else if (
		probeAnyHoldPin() &&
		X.stepsToDo() == 0 &&
		Y.stepsToDo() == 0 &&
		Z.stepsToDo() == 0 &&
		A.stepsToDo() == 0
	) {
		X.move(1);
		Y.move(1);
		Z.move(1);
		A.move(1);
	} else if (!probeAnyHoldPin()) {
		X.stop();
		Y.stop();
		Z.stop();
		A.stop();
		X.setZero();
		Y.setZero();
		Z.setZero();
		A.setZero();
		Serial.println("LIFTED");
		calibrateMode = nextMode;
	}
}
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
void stopMotors() {
	switch (stopMode) {
		case StopMode::CENTER:
			X.moveTo(3000);
			Y.moveTo(3000);
			Z.moveTo(3000);
			A.moveTo(3000);
			stopMode = StopMode::LOWER;
			Serial.println("CENTERING");
			break;
		case StopMode::LOWER:
			if (
				(X.stepsToDo() == 0) &&
				(Y.stepsToDo() == 0) &&
				(Z.stepsToDo() == 0) &&
				(A.stepsToDo() == 0)
			) {
				X.moveTo(0);
				Y.moveTo(0);
				Z.moveTo(0);
				A.moveTo(0);
				stopMode = StopMode::STOP;
				Serial.println("LOWERING");
			}
			break;
		case StopMode::STOP:
			X.stop();
			Y.stop();
			Z.stop();
			A.stop();
			setEnabled(false);
			Serial.println("STOPPED");
			mode = RunMode::SLEEP;
			break;
	}
}
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
Vec3 stepsCoords;
long stepsOut[4];
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
void calibrate() {
	switch (calibrateMode) {
		case CalibrateMode::PROBE:
			probe();
			break;
		case CalibrateMode::RX:
			tighten(X,2,CalibrateMode::PX);
			break;
		case CalibrateMode::PX:
			loosen(X,3,CalibrateMode::RA);
			break;
		case CalibrateMode::RA:
			tighten(A,8,CalibrateMode::PA);
			break;
		case CalibrateMode::PA:
			loosen(A,9,CalibrateMode::RY);
			break;
		case CalibrateMode::RY:
			tighten(Y,4,CalibrateMode::PY);
			break;
		case CalibrateMode::PY:
			loosen(Y,5,CalibrateMode::RZ);
			break;
		case CalibrateMode::RZ:
			tighten(Z,6,CalibrateMode::U1);
			break;
		case CalibrateMode::U1:
			lift(10,CalibrateMode::SET);
			break;
		case CalibrateMode::SET:

			X.stop();
			Y.stop();
			Z.stop();
			A.stop();

			X.setZero();
			Y.setZero();
			Z.setZero();
			A.setZero();

			Serial.println("SET");
			mode = RunMode::IDLE;

			stepsCoords.x = 0.0f;
			stepsCoords.y = 0.0f;
			stepsCoords.z = 30.0f;
			stepsFromHome(stepsCoords, stepsOut);
			addMoveAction(
				stepsOut[2], // X
				stepsOut[1], // Y
				stepsOut[3], // Z
				stepsOut[0]  // A
			);

			addWaitAction(500);

			stepsCoords.x =-10.0f;
			stepsCoords.y =-10.0f;
			stepsCoords.z = 30.0f;
			stepsFromHome(stepsCoords, stepsOut);
			addMoveAction(
				stepsOut[2], // X
				stepsOut[1], // Y
				stepsOut[3], // Z
				stepsOut[0]  // A
			);

			addWaitAction(500);

			stepsCoords.x = 10.0f;
			stepsCoords.y =-10.0f;
			stepsCoords.z = 30.0f;
			stepsFromHome(stepsCoords, stepsOut);
			addMoveAction(
				stepsOut[2], // X
				stepsOut[1], // Y
				stepsOut[3], // Z
				stepsOut[0]  // A
			);

			addWaitAction(500);

			stepsCoords.x = 10.0f;
			stepsCoords.y = 10.0f;
			stepsCoords.z = 30.0f;
			stepsFromHome(stepsCoords, stepsOut);
			addMoveAction(
				stepsOut[2], // X
				stepsOut[1], // Y
				stepsOut[3], // Z
				stepsOut[0]  // A
			);

			addWaitAction(500);

			stepsCoords.x =-10.0f;
			stepsCoords.y = 10.0f;
			stepsCoords.z = 30.0f;
			stepsFromHome(stepsCoords, stepsOut);
			addMoveAction(
				stepsOut[2], // X
				stepsOut[1], // Y
				stepsOut[3], // Z
				stepsOut[0]  // A
			);

			addWaitAction(500);

			stepsCoords.x =-10.0f;
			stepsCoords.y =-10.0f;
			stepsCoords.z = 30.0f;
			stepsFromHome(stepsCoords, stepsOut);
			addMoveAction(
				stepsOut[2], // X
				stepsOut[1], // Y
				stepsOut[3], // Z
				stepsOut[0]  // A
			);

			addWaitAction(500);

			stepsCoords.x = 0.0f;
			stepsCoords.y = 0.0f;
			stepsCoords.z = 30.0f;
			stepsFromHome(stepsCoords, stepsOut);
			addMoveAction(
				stepsOut[2], // X
				stepsOut[1], // Y
				stepsOut[3], // Z
				stepsOut[0]  // A
			);

			addWaitAction(500);

			stepsCoords.x = 0.0f;
			stepsCoords.y = 0.0f;
			stepsCoords.z = 0.0f;
			stepsFromHome(stepsCoords, stepsOut);
			addMoveAction(
				stepsOut[2], // X
				stepsOut[1], // Y
				stepsOut[3], // Z
				stepsOut[0]  // A
			);

			break;
	}
}
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
Action   currentAction;
uint32_t currentWait;
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
void runCable() {

	long now = millis();
	updateHoldPins(now);

	switch (mode) {

	  case RunMode::INIT:
		calibrate();
		break;

	  case RunMode::MOVE:
		if ((X.stepsToDo() +  Y.stepsToDo() +  Z.stepsToDo() +  A.stepsToDo()) == 0) {
			mode = RunMode::IDLE;
		}
		break;

	  case RunMode::WAIT:
		if (currentWait >= now) {
			mode = RunMode::IDLE;
		}
		break;

	  case RunMode::IDLE:
		if (popAction(&currentAction)) {
			switch (currentAction.type) {
				case ActionType::WAIT:
					Serial.print("WAIT: ");
					Serial.print(currentAction.time.MS);
					Serial.println("ms");
					currentWait = now + currentAction.time.MS;
					mode = RunMode::WAIT;
					break;
				case ActionType::MOVE:
					Serial.print("MOVE: ");
					Serial.print(currentAction.target.X);
					Serial.print("; ");
					Serial.print(currentAction.target.Y);
					Serial.print("; ");
					Serial.print(currentAction.target.Z);
					Serial.print("; ");
					Serial.print(currentAction.target.A);
					Serial.println(";");
					X.moveTo(currentAction.target.X);
					Y.moveTo(currentAction.target.Y);
					Z.moveTo(currentAction.target.Z);
					A.moveTo(currentAction.target.A);
					mode = RunMode::MOVE;
					break;
			}
		}
		break;

	  case RunMode::ERROR:
		X.stop();
		Y.stop();
		Z.stop();
		A.stop();
		setEnabled(false);
		Serial.print("ERROR: ");
		Serial.print(error);
		Serial.println(".");
		mode = RunMode::STOP;
		break;
		
	  case RunMode::STOP:
		stopMotors();
		break;
		
	  case RunMode::SLEEP:
		delay(10);
		break;
		
	}
}
//-----------------------------------------------------------------------------

