#include <Arduino.h>
#include <Stepper.h>
#include <Wire.h>
#include <Adafruit_PWMServoDriver.h>

#define SRV_FREQ 50
#define SRV_MIN 150
#define SRV_MAX 600

#define SRV_SHOULDER 0
#define SRV_ELLBOW   1
#define SRV_WRIST    3
#define SRV_GRIPPER  2

#define SRV_RANGE (SRV_MAX-SRV_MIN)
#define SRV_MD1 (SRV_RANGE/2)
#define SRV_MD2 (-SRV_MD1)

Adafruit_PWMServoDriver servoDriver = Adafruit_PWMServoDriver();

int current[16];
int target[16];

Stepper stepper = Stepper(512, 4, 5, 6, 7);

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
  
  Serial.println("******** Robot Arm ********");
  Serial.println("** \"The UGLY MOTHERFKR\"  **");
  Serial.println("***************************");
  Serial.println();

  Serial.println("I2C Scanner:");
  Wire.begin();
  for (uint8_t addr = 1; addr < 127; addr++) {
    Wire.beginTransmission(addr);
    if (Wire.endTransmission() == 0) {
      Serial.print("I2C device found at address 0x");
      Serial.println(addr, HEX);
    }
  }  
  delay(500);

  Serial.println("servoDriver setup");
  servoDriver.begin();
  servoDriver.setPWMFreq(SRV_FREQ);
  
  // Setze die Drehgeschwindigkeit (in Umdrehungen pro Minute)
  stepper.setSpeed(10);
  delay(500);

  Serial.println("** > positioning to mid..");
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

}
//*****************************************************************************

//*****************************************************************************
void loop() {

  static int T = SRV_MD1;

  if (
    smooth(SRV_SHOULDER) &&
    smooth(SRV_ELLBOW) &&
    smooth(SRV_WRIST) &&
    smooth(SRV_GRIPPER)
  ) {
    switch (T) {
      case SRV_MD1: T = SRV_MIN; break;
      case SRV_MIN: T = SRV_MD2; break;
      case SRV_MD2: T = SRV_MAX; break;
      case SRV_MAX: T = SRV_MD1; break;
      default: T = SRV_MD1; break;
    }
    target[SRV_SHOULDER] = abs(T);
    target[SRV_ELLBOW]   = abs(T);
    target[SRV_WRIST]    = abs(T);
    target[SRV_GRIPPER]  = abs(T);
  }
  
  /*
  static int steps = 3*512;
  static int step  = 8;
  stepper.step(step);
  steps -= abs(step);
  if (steps <= 0) {
    steps = 3*512;
    step = -step;      
  }
  */

  delay(1);

}
//*****************************************************************************
