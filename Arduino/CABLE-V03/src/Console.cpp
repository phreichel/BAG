#include <Arduino.h>
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
		line.toUpperCase();
		if (line.startsWith("ENABLE", 0)) {
			enable();
		} else if (line.startsWith("CALIBRATE", 0)) {
			calibrate();
		} else if (line.startsWith("HOME", 0)) {
			home();
		} else if (line.startsWith("JOG", 0)) {
			jog();
		} else if (line.startsWith("RAW", 0)) {
			raw();
		}

		Serial.print(">>>");

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
void Console::home() {
	Serial.println("HOMING");
	hardwarePtr->home();
}
//=============================================================================

//=============================================================================
void Console::jog() {

	Serial.println("JOGGING");

	Serial.print("X COORD:>");
	while (Serial.available() == 0) delay(10);
	String xStr = Serial.readStringUntil('\n');

	Serial.print("Y COORD:>");
	while (Serial.available() == 0) delay(10);
	String yStr = Serial.readStringUntil('\n');

	Serial.print("Z COORD:>");
	while (Serial.available() == 0) delay(10);
	String zStr = Serial.readStringUntil('\n');

	xStr.trim();
	yStr.trim();
	zStr.trim();

	float xFloat = xStr.toFloat();
	float yFloat = yStr.toFloat();
	float zFloat = zStr.toFloat();

	Serial.print("JOGGING TO: ");
	Serial.print(xFloat);
	Serial.print(" | ");
	Serial.print(yFloat);
	Serial.print(" | ");
	Serial.print(zFloat);
	Serial.println();

	model.at(xFloat, yFloat, zFloat);

	Serial.print("RAW MOVE TO: ");
	Serial.print(model.stpa);
	Serial.print(" | ");
	Serial.print(model.stpb);
	Serial.print(" | ");
	Serial.print(model.stpc);
	Serial.print(" | ");
	Serial.print(model.stpd);
	Serial.println();

	//hardwarePtr->move(aInt, bInt, cInt, dInt);

}
//=============================================================================

//=============================================================================
void Console::raw() {

	Serial.println("RAW MODE");

	Serial.print("A STEPS:>");
	while (Serial.available() == 0) delay(10);
	String aStr = Serial.readStringUntil('\n');

	Serial.print("B STEPS:>");
	while (Serial.available() == 0) delay(10);
	String bStr = Serial.readStringUntil('\n');

	Serial.print("C STEPS:>");
	while (Serial.available() == 0) delay(10);
	String cStr = Serial.readStringUntil('\n');

	Serial.print("D STEPS:>");
	while (Serial.available() == 0) delay(10);
	String dStr = Serial.readStringUntil('\n');

	aStr.trim();
	bStr.trim();
	cStr.trim();
	dStr.trim();

	unsigned int aInt = aStr.toInt();
	unsigned int bInt = bStr.toInt();
	unsigned int cInt = cStr.toInt();
	unsigned int dInt = dStr.toInt();

	Serial.print("RAW MOVE TO: ");
	Serial.print(aInt);
	Serial.print(" | ");
	Serial.print(bInt);
	Serial.print(" | ");
	Serial.print(cInt);
	Serial.print(" | ");
	Serial.print(dInt);
	Serial.println();

	hardwarePtr->move(aInt, bInt, cInt, dInt);

}
//=============================================================================

