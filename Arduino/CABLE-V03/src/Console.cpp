#include "Console.h"

//=============================================================================
inline const long BAUDRATE = 115200;
//=============================================================================

//=============================================================================
Console::Console(Hardware* _hardwarePtr) {
	hardwarePtr = _hardwarePtr;
}
//=============================================================================

//=============================================================================
Console::~Console() {
	hardwarePtr = NULL;
}
//=============================================================================

//=============================================================================
void Console::setup() {
	Serial.begin(BAUDRATE);
	delay(100);
	Serial.println("CABLE CONSOLE:");
	Serial.print(">>>");
}
//=============================================================================

//=============================================================================
void Console::loop() {
	if (Serial.available() > 0) {

		String line = Serial.readStringUntil('\n');
		line.trim();
		line.toLowerCase();

		if (line.startsWith("enable", 0)) {
			enable();
		} else if (line.startsWith("cal", 0)) {
			calibrate();
		} else if (line.startsWith("center", 0)) {
			center();
		} else if (line.startsWith("home", 0)) {
			home();
		} else if (line.startsWith("jog", 0)) {
			jog(line);
		} else if (line.startsWith("raw", 0)) {
			raw(line);
		} else if (line.startsWith("rel", 0)) {
			relraw(line);
		}

		Serial.print(">>");

	}
}
//=============================================================================

//=============================================================================
void Console::enable() {
	if (hardwarePtr->enabled()) {
		Serial.println("DISABLE");
		hardwarePtr->enabled(false);
	} else {
		Serial.println("ENABLE");
		hardwarePtr->enabled(true);
	}
}
//=============================================================================

//=============================================================================
void Console::calibrate() {
	Serial.println("CALIBRATION");
	hardwarePtr->zero();
}
//=============================================================================

//=============================================================================
void Console::center() {
	Serial.println("CENTERING");
	model.posx =  0.f;
	model.posy =  0.f;
	model.posz = 10.f;
	model.pos2stp();
	hardwarePtr->move(model.stpa, model.stpb, model.stpc, model.stpd);
}
//=============================================================================

//=============================================================================
void Console::home() {
	Serial.println("HOMING");
	hardwarePtr->home();
}
//=============================================================================

//=============================================================================
void Console::jog(String& line) {

	float x, y, z;

	if (sscanf(line.c_str(), "jog %f %f %f", &x, &y, &z) != 3) {
		Serial.println("ERR: jog x y z");
		return;
	}

	Serial.print("JOGGING TO: ");
	Serial.print(x);
	Serial.print(" ");
	Serial.print(y);
	Serial.print(" ");
	Serial.print(z);
	Serial.println();

	model.posx = x;
	model.posy = y;
	model.posz = z;
	model.pos2stp();
	hardwarePtr->move(model.stpa, model.stpb, model.stpc, model.stpd);

}
//=============================================================================

//=============================================================================
void Console::raw(String& line) {

	int a, b, c, d;

	if (sscanf(line.c_str(), "raw %i %i %i %i", &a, &b, &c, &d) != 4) {
		Serial.println("ERR: raw a b c d");
		return;
	}

	Serial.print("RAW TO: ");
	Serial.print(a);
	Serial.print(" ");
	Serial.print(b);
	Serial.print(" ");
	Serial.print(c);
	Serial.print(" ");
	Serial.print(d);
	Serial.println();

	hardwarePtr->move(a, b, c, d);

}
//=============================================================================

//=============================================================================
void Console::relraw(String& line) {

	int a, b, c, d;

	if (sscanf(line.c_str(), "rel %i %i %i %i", &a, &b, &c, &d) != 4) {
		Serial.println("ERR: raw a b c d");
		return;
	}

	Serial.print("REL TO: ");
	Serial.print(a);
	Serial.print(" ");
	Serial.print(b);
	Serial.print(" ");
	Serial.print(c);
	Serial.print(" ");
	Serial.print(d);
	Serial.println();

	hardwarePtr->relmove(a, b, c, d);

}
//=============================================================================
