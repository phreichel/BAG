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
		} else if (line.startsWith("RELRAW", 0)) {
			relraw();
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

	String xStr;
	readInput("X COORD:", &xStr);

	String yStr;
	readInput("Y COORD:", &yStr);

	String zStr;
	readInput("Z COORD:", &zStr);

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

	hardwarePtr->move(model.stpa, model.stpb, model.stpc, model.stpd);

}
//=============================================================================

//=============================================================================
void Console::raw() {

	Serial.println("RAW MODE");

	String aStr;
	readInput("A STEPS:", &aStr);

	String bStr;
	readInput("B STEPS:", &bStr);

	String cStr;
	readInput("C STEPS:", &cStr);

	String dStr;
	readInput("D STEPS:", &dStr);

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

//=============================================================================
void Console::relraw() {

	Serial.println("RELATIVE RAW MODE");

	String aStr;
	readInput("A STEPS:", &aStr);

	String bStr;
	readInput("B STEPS:", &bStr);

	String cStr;
	readInput("C STEPS:", &cStr);

	String dStr;
	readInput("D STEPS:", &dStr);

	int aInt = aStr.toInt();
	int bInt = bStr.toInt();
	int cInt = cStr.toInt();
	int dInt = dStr.toInt();

	Serial.print("RELATIVE RAW MOVE TO: ");
	Serial.print(aInt);
	Serial.print(" | ");
	Serial.print(bInt);
	Serial.print(" | ");
	Serial.print(cInt);
	Serial.print(" | ");
	Serial.print(dInt);
	Serial.println();

	hardwarePtr->relmove(aInt, bInt, cInt, dInt);

}
//=============================================================================


//=============================================================================
String* Console::readInput(char* prompt, String* buffer) {
	
	if (buffer == NULL) return buffer;

	Serial.print(prompt);
	Serial.print(">");
	
	while (Serial.available() == 0) delay(10);
	*buffer = Serial.readStringUntil('\n');
	buffer->trim();
	buffer->toUpperCase();

	return buffer;

}
//=============================================================================
