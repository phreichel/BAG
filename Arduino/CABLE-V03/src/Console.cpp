#include <Arduino.h>
#include "Console.h"

//=============================================================================
const int BAUDRATE = 115200;
//=============================================================================

//=============================================================================
Console::Console() {}
//=============================================================================

//=============================================================================
Console::~Console() {}
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
		if (line.startsWith("CALIBRATE", 0)) {
			calibrate();
		} else if (line.startsWith("HOME", 0)) {
			home();
		} else if (line.startsWith("JOG", 0)) {
			jog();
		}

		Serial.print(">>>");

	}
}
//=============================================================================

//=============================================================================
void Console::calibrate() {
	Serial.println("CALIBRATION");
}
//=============================================================================

//=============================================================================
void Console::home() {
	Serial.println("HOMING");
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

}
//=============================================================================

