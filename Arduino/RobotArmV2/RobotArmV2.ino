#include <Arduino.h>
#include <Stepper.h>
#include <Wire.h>
#include <Adafruit_PWMServoDriver.h>

#define SRV_FREQ 50
#define SRV_MIN 150
#define SRV_MAX 600

#define SRV_SHOULDER 0
#define SRV_ELLBOW   2
#define SRV_WRIST    1
#define SRV_GRIPPER  3

#define SRV_RANGE (SRV_MAX-SRV_MIN)
#define SRV_MD1 (SRV_RANGE/2)
#define SRV_MD2 (-SRV_MD1)

Adafruit_PWMServoDriver servoDriver = Adafruit_PWMServoDriver();

int current[16];
int target[16];

Stepper stepper = Stepper(2048, 4, 5, 6, 7);

int poses = 0;
int pose[100][4];
int play = -1;

//*****************************************************************************
bool smooth(int channel) {
  if (current[channel] == target[channel]) return true;
  if (current[channel] < target[channel]) current[channel]++;
  if (current[channel] > target[channel]) current[channel]--;
  servoDriver.setPWM(channel, 0, current[channel]);
  return false;
}
//*****************************************************************************

//*****************************************************************************
void setup() {

  Serial.begin(9600);  
  delay(1000);
  
  Serial.println("** Robot Arm **");
  Serial.println();

  Serial.println("I2C Scan:");
  Wire.begin();
  for (uint8_t addr = 1; addr < 127; addr++) {
    Wire.beginTransmission(addr);
    if (Wire.endTransmission() == 0) {
      Serial.print("I2C found at 0x");
      Serial.println(addr, HEX);
    }
  }  
  delay(500);

  Serial.println("Servo Setup");
  servoDriver.begin();
  servoDriver.setPWMFreq(SRV_FREQ);
  
  // Setze die Drehgeschwindigkeit (in Umdrehungen pro Minute)
  Serial.println("Stepper Setup");
  stepper.setSpeed(10);
  delay(500);

  Serial.println("Positioning to mid.");
  servoDriver.setPWM(SRV_SHOULDER, 0, SRV_MD1);
  servoDriver.setPWM(SRV_ELLBOW, 0, SRV_MD1);
  servoDriver.setPWM(SRV_WRIST, 0, SRV_MD1);
  servoDriver.setPWM(SRV_GRIPPER, 0, SRV_MD1);

  current[SRV_SHOULDER] = SRV_MD1;
  current[SRV_ELLBOW]   = SRV_MD1;
  current[SRV_WRIST]    = SRV_MD1;
  current[SRV_GRIPPER]  = SRV_MD1;
  
  target[SRV_SHOULDER] = SRV_MD1;
  target[SRV_ELLBOW]   = SRV_MD1;
  target[SRV_WRIST]    = SRV_MD1;
  target[SRV_GRIPPER]  = SRV_MD1;

  delay(500);

  Serial.println("READY.");

}
//*****************************************************************************

//*****************************************************************************
void loop() {

  static String command = "";
  static int    step    = 1;
  
  if (Serial.available()) {
    command = Serial.readString();
    delay(1);
    while (Serial.available()) {
      command += Serial.readString();
      delay(1);
    }
    Serial.print(">>");
    Serial.println(command);
    
    if (command.startsWith("+")) {
      step++;
      Serial.print("STEP SET TO: ");
      Serial.print(step);
      Serial.println();
    } else if (command.startsWith("-")) {
      step--;
      if (step < 0) step = 0;
      Serial.print("STEP SET TO: ");
      Serial.print(step);
      Serial.println();
    } else if (command.startsWith("s+")) {
      target[SRV_SHOULDER] = target[SRV_SHOULDER] + step;
      Serial.print("SRV_SHOULDER SET TO: ");
      Serial.print(target[SRV_SHOULDER]);
      Serial.println();
    } else if (command.startsWith("s-")) {
      target[SRV_SHOULDER] = target[SRV_SHOULDER] - step;
      if (target[SRV_SHOULDER] < 0) target[SRV_SHOULDER] = 0;
      Serial.print("SRV_SHOULDER SET TO: ");
      Serial.print(target[SRV_SHOULDER]);
      Serial.println();
    } else if (command.startsWith("s")) {
      target[SRV_SHOULDER] = command.substring(1).toInt();
      if (target[SRV_SHOULDER] < 0) target[SRV_SHOULDER] = 0;
      Serial.print("SRV_SHOULDER SET TO: ");
      Serial.print(target[SRV_SHOULDER]);
      Serial.println();
    } else if (command.startsWith("e+")) {
      target[SRV_ELLBOW] = target[SRV_ELLBOW] + step;
      Serial.print("SRV_ELLBOW SET TO: ");
      Serial.print(target[SRV_ELLBOW]);
      Serial.println();
    } else if (command.startsWith("e-")) {
      target[SRV_ELLBOW] = target[SRV_ELLBOW] - step;
      if (target[SRV_ELLBOW] < 0) target[SRV_ELLBOW] = 0;
      Serial.print("SRV_ELLBOW SET TO: ");
      Serial.print(target[SRV_ELLBOW]);
      Serial.println();
    } else if (command.startsWith("e")) {
      target[SRV_ELLBOW] = command.substring(1).toInt();
      if (target[SRV_ELLBOW] < 0) target[SRV_ELLBOW] = 0;
      Serial.print("SRV_ELLBOW SET TO: ");
      Serial.print(target[SRV_ELLBOW]);
      Serial.println();
    } else if (command.startsWith("w+")) {
      target[SRV_WRIST] = target[SRV_WRIST] + step;
      Serial.print("SRV_WRIST SET TO: ");
      Serial.print(target[SRV_WRIST]);
      Serial.println();
    } else if (command.startsWith("w-")) {
      target[SRV_WRIST] = target[SRV_WRIST] - step;
      if (target[SRV_WRIST] < 0) target[SRV_WRIST] = 0;
      Serial.print("SRV_WRIST SET TO: ");
      Serial.print(target[SRV_WRIST]);
      Serial.println();
    } else if (command.startsWith("w")) {
      target[SRV_WRIST] = command.substring(1).toInt();
      if (target[SRV_WRIST] < 0) target[SRV_WRIST] = 0;
      Serial.print("SRV_WRIST SET TO: ");
      Serial.print(target[SRV_WRIST]);
      Serial.println();
    } else if (command.startsWith("g+")) {
      target[SRV_GRIPPER] = target[SRV_GRIPPER] + step;
      Serial.print("SRV_GRIPPER SET TO: ");
      Serial.print(target[SRV_GRIPPER]);
      Serial.println();
    } else if (command.startsWith("g-")) {
      target[SRV_GRIPPER] = target[SRV_GRIPPER] - step;
      if (target[SRV_GRIPPER] < 0) target[SRV_GRIPPER] = 0;
      Serial.print("SRV_GRIPPER SET TO: ");
      Serial.print(target[SRV_GRIPPER]);
      Serial.println();
    } else if (command.startsWith("g")) {
      target[SRV_GRIPPER] = command.substring(1).toInt();
      if (target[SRV_GRIPPER] < 0) target[SRV_GRIPPER] = 0;
      Serial.print("SRV_GRIPPER SET TO: ");
      Serial.print(target[SRV_GRIPPER]);
      Serial.println();
    } else if (command.startsWith("xstop")) {
      play = -1;
      target[SRV_SHOULDER] = current[SRV_SHOULDER];
      target[SRV_ELLBOW]   = current[SRV_ELLBOW];
      target[SRV_WRIST]    = current[SRV_WRIST];
      target[SRV_GRIPPER]  = current[SRV_GRIPPER];
      Serial.println("Stop");
    } else if (command.startsWith("xclear")) {
      poses = 0;
      Serial.println("Clear");
    } else if (command.startsWith("xpose")) {      
      pose[poses][SRV_SHOULDER] = current[SRV_SHOULDER];
      pose[poses][SRV_ELLBOW] = current[SRV_ELLBOW];
      pose[poses][SRV_WRIST] = current[SRV_WRIST];
      pose[poses][SRV_GRIPPER] = current[SRV_GRIPPER];
      poses++;
      Serial.print("# Poses: ");
      Serial.println(poses);
    } else if (command.startsWith("xgo")) {
      int selpose = command.substring(3).toInt();
      if ((selpose < 0) || (selpose >= poses)) {
        Serial.println("Invalid pose selection");
      } else {
        target[SRV_SHOULDER] = pose[selpose][SRV_SHOULDER];
        target[SRV_ELLBOW]   = pose[selpose][SRV_ELLBOW];
        target[SRV_WRIST]    = pose[selpose][SRV_WRIST];
        target[SRV_GRIPPER]  = pose[selpose][SRV_GRIPPER];
      }
    } else if (command.startsWith("xstart")) {
      play = 0;
      Serial.println("Start");
    } else if (command.startsWith("xget")) {
      Serial.println("POSE DATA");
      Serial.print("SRV_SHOULDER: ");
      Serial.println(current[SRV_SHOULDER]);
      Serial.print("SRV_ELLBOW: ");
      Serial.println(current[SRV_ELLBOW]);
      Serial.print("SRV_WRIST: ");
      Serial.println(current[SRV_WRIST]);
      Serial.print("SRV_GRIPPER: ");
      Serial.println(current[SRV_GRIPPER]);
      Serial.println("***********************");
    } else if (command.startsWith("xspeed")) {
      int spd = command.substring(6).toInt();
      spd = spd >= 1 ? spd : 1;
      Serial.print("Step Speed: ");
      Serial.println(spd);
      stepper.setSpeed(spd);
    } else if (command.startsWith("xstep")) {
      int steps = command.substring(5).toInt();
      Serial.print("Steps: ");
      Serial.println(steps);
      long dlt = millis();
      stepper.step(steps);
      dlt = millis() - dlt;
      Serial.print("Millis: ");
      Serial.println(dlt);
    } else {
      Serial.println("N/A).");
    }
  }

  bool ready =
    smooth(SRV_SHOULDER) &&
    smooth(SRV_ELLBOW) &&
    smooth(SRV_WRIST) &&
    smooth(SRV_GRIPPER);

  if (ready && play >= 0) {
    if (play < poses) {
      target[SRV_SHOULDER] = pose[play][SRV_SHOULDER];
      target[SRV_ELLBOW]   = pose[play][SRV_ELLBOW];
      target[SRV_WRIST]    = pose[play][SRV_WRIST];
      target[SRV_GRIPPER]  = pose[play][SRV_GRIPPER];
    }
    play = (play+1) % poses;
  }

  delay(1);

}
//*****************************************************************************
