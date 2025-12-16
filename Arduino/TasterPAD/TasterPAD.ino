#include <Arduino.h>

/* =======================
 *  PIN CONFIG (frei wählbar)
 * ======================= */
#define SDA_PIN 21
#define SCL_PIN 22

/* =======================
 *  TIMING / TOLERANZ
 * ======================= */
#define START_MIN_LOW_MS   2      // SDA muss so lange LOW sein
#define FRAME_TIMEOUT_MS  100     // Resync wenn nichts mehr kommt

/* =======================
 *  STATE
 * ======================= */
volatile bool inFrame = false;
volatile bool frameDone = false;

volatile uint8_t  bitCount  = 0;
volatile uint8_t  byteCount = 0;
volatile uint8_t  curByte   = 0;
volatile uint8_t  buffer[3];

volatile unsigned long lastEdgeMs = 0;

volatile bool sawClock = false;

/* =======================
 *  REVERSE BIT ORDER
 * ======================= */
uint16_t reverse16(uint16_t v)
{
  v = (v >> 8) | (v << 8);
  v = ((v & 0xF0F0) >> 4) | ((v & 0x0F0F) << 4);
  v = ((v & 0xCCCC) >> 2) | ((v & 0x3333) << 2);
  v = ((v & 0xAAAA) >> 1) | ((v & 0x5555) << 1);
  return v;
}

/* =======================
 *  SCL RISING ISR
 * ======================= */
void IRAM_ATTR onSclRise()
{
  if (!inFrame) return;

  sawClock = true;   // <<< WICHTIG

  uint8_t bit = digitalRead(SDA_PIN);

  curByte = (curByte << 1) | bit;
  bitCount++;

  if (bitCount == 8)
  {
    buffer[byteCount++] = curByte;
    curByte  = 0;
    bitCount = 0;
  }

  lastEdgeMs = millis();

  if (byteCount >= 3)
  {
    frameDone = true;
    inFrame   = false;
  }
}

/* =======================
 *  START DETECTION
 * ======================= */
void checkStart()
{
  static unsigned long lowSince = 0;

  // START nur zulassen, wenn wir NICHT im Frame sind
  if (!inFrame &&
      digitalRead(SCL_PIN) == HIGH &&
      digitalRead(SDA_PIN) == LOW)
  {
    if (lowSince == 0)
      lowSince = millis();

    if (millis() - lowSince >= START_MIN_LOW_MS)
    {
      inFrame   = true;
      frameDone = false;
      bitCount  = 0;
      byteCount = 0;
      curByte   = 0;
      sawClock  = false;    // <<< RESET

      // Serial.println("START detected"); debug only

      lowSince = 0;
    }
  }
  else
  {
    lowSince = 0;
  }
}

/* =======================
 *  OUTPUT
 * ======================= */
void printFrame()
{
  uint8_t addr = buffer[0] >> 1;

  uint16_t status =
    ((uint16_t)buffer[1] << 8) |
     (uint16_t)buffer[2];
  status = ~reverse16(status) & 0xFFFF;

  Serial.print("RX addr=0x");
  Serial.print(addr, HEX);
  Serial.print(" status=0x");
  Serial.print(status, HEX);
  Serial.println();
}

/* =======================
 *  SETUP
 * ======================= */
void setup()
{
  Serial.begin(115200);
  delay(500);

  pinMode(SDA_PIN, INPUT);
  pinMode(SCL_PIN, INPUT);

  attachInterrupt(digitalPinToInterrupt(SCL_PIN),
                  onSclRise,
                  RISING);

  Serial.println("ESP32 slow I2C-like software client ready");
}

/* =======================
 *  LOOP
 * ======================= */
void loop()
{
  checkStart();

  // Timeout -> Resync
  if (inFrame && sawClock && millis() - lastEdgeMs > FRAME_TIMEOUT_MS)
  {
    inFrame = false;
  }

  if (frameDone)
  {
    noInterrupts();
    frameDone = false;
    interrupts();

    printFrame();
  }
}
