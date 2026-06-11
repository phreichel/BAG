#include <Adafruit_ST7735.h>
#include <Adafruit_GFX.h>
#include <SPI.h>
#include <WiFi.h>
#include <WiFiClient.h>
#include <WiFiUdp.h>

//=======================================================================
#define TFT_CS   5
#define TFT_DC   2
#define TFT_RST -1
//=======================================================================

//=======================================================================
#define SOUND_PIN 27
//=======================================================================

//=======================================================================
#define EYES_OPEN 0
#define EYES_NEUTRAL 1
#define EYES_CLOSED 2
#define EYES_QUENCHED 3
#define EYES_CONFUSED 4
#define EYES_SHOCKED 5
//=======================================================================

//=======================================================================
#define STATE_SLEEPING_NEUTRAL 0
#define STATE_SLEEPING_UNEASY  1
#define STATE_LISTENING        2
#define STATE_WAKE_HAPPY       3
#define STATE_WAKE_CONFUSED    4
//=======================================================================

//=======================================================================
const char* ssid = "hr";
const char* password = "59294905537503957609";
//=======================================================================

//=======================================================================
unsigned long textfade;
unsigned long wakefade;
//=======================================================================

//=======================================================================
int current_state = 3;
unsigned long mark;
int stage = 0;
int dx = 0;
const int dy = 0;
uint16_t color = ST77XX_BLUE;
int peak_value = LOW;
unsigned long last_peak = 0;
unsigned long activated;
unsigned long last_wifi_try = 0;
int peak_count = 0;
//=======================================================================

//=======================================================================
byte wol_mac_first[]  = { 0x74, 0x56, 0x3c, 0xfd, 0x91, 0xf2 };
byte wol_mac_second[] = { 0x0C, 0x9D, 0x92, 0x82, 0x31, 0x95 };
//=======================================================================

//=======================================================================
Adafruit_ST7735 tft = Adafruit_ST7735(TFT_CS, TFT_DC, TFT_RST);
WiFiClient client;
WiFiUDP udp;
//=======================================================================

//=======================================================================
void drawOpen(int x, int y, int ex, int ey, uint16_t c) {
    tft.drawRoundRect(18+x, 23+y, 44, 44, 5, c);
    tft.drawRoundRect(68+x, 23+y, 44, 44, 5, c);
    tft.fillRoundRect(20+x, 25+y, 40, 40, 5, c);
    tft.fillRoundRect(70+x, 25+y, 40, 40, 5, c);
    tft.fillCircle(40+x+ex, 45+y+ey,  4, ST77XX_BLACK);
    tft.fillCircle(90+x+ex, 45+y+ey,  4, ST77XX_BLACK);
}
//=======================================================================

//=======================================================================
void drawNeutral(int x, int y, int ex, int ey, uint16_t c) {
    tft.drawRoundRect(18+x, 23+y, 44, 44, 5, c);
    tft.drawRoundRect(68+x, 23+y, 44, 44, 5, c);
    tft.fillRoundRect(20+x, 35+y, 40, 20, 5, c);
    tft.fillRoundRect(70+x, 35+y, 40, 20, 5, c);
    tft.fillCircle(40+x+ex, 45+y+ey,  4, ST77XX_BLACK);
    tft.fillCircle(90+x+ex, 45+y+ey,  4, ST77XX_BLACK);
}
//=======================================================================

//=======================================================================
void drawConfused(int x, int y, int ex, int ey, uint16_t c) {
    tft.drawRoundRect(18+x, 23+y, 44, 44, 5, c);
    tft.drawRoundRect(68+x, 23+y, 44, 44, 5, c);
    tft.fillRoundRect(20+x, 25+y, 40, 40, 5, c);
    tft.fillRoundRect(70+x, 35+y, 40, 20, 5, c);
    tft.fillCircle(40+x+ex, 45+y+ey,  4, ST77XX_BLACK);
    tft.fillCircle(90+x+ex, 45+y+ey,  4, ST77XX_BLACK);
}
//=======================================================================

//=======================================================================
void drawClosed(int x, int y, int ex, int ey, uint16_t c) {
    tft.fillRoundRect(20+x, 40+y, 40, 10, 5, c);
    tft.fillRoundRect(70+x, 40+y, 40, 10, 5, c);
}
//=======================================================================

//=======================================================================
void drawQuenched(int x, int y, int ex, int ey, uint16_t c) {
    tft.fillRoundRect(20+x, 40+y, 40, 10, 5, c);
    tft.fillRoundRect(70+x, 40+y, 40, 10, 5, c);
    tft.drawLine(30+x, 25+y,  55+x, 45+y, c);
    tft.drawLine(75+x, 45+y, 100+x, 25+y, c);
    tft.drawLine(35+x, 25+y,  60+x, 45+y, c);
    tft.drawLine(70+x, 45+y,  95+x, 25+y, c);
}
//=======================================================================

//=======================================================================
void drawShocked(int x, int y, int ex, int ey, uint16_t c) {
    tft.drawRoundRect(18+x, 23+y, 44, 44, 5, c);
    tft.drawRoundRect(68+x, 23+y, 44, 44, 5, c);
    tft.fillCircle(40+x, 45+y, 24, c);
    tft.fillCircle(90+x, 45+y, 24, c);
    tft.fillCircle(40+x+ex, 45+y+ey,  4, ST77XX_BLACK);
    tft.fillCircle(90+x+ex, 45+y+ey,  4, ST77XX_BLACK);
}
//=======================================================================

//=======================================================================
void (*draw[])(int,int,int,int,uint16_t) = {
  drawOpen,
  drawNeutral,
  drawClosed,
  drawQuenched,
  drawConfused,
  drawShocked
};
//=======================================================================

//=======================================================================
void render(
  int oldstage,
  int newstage,
  int olddx,
  int newdx,
  int dy,
  int ex,
  int ey,
  uint16_t color) {
  if ((oldstage == newstage) && (olddx == newdx)) return;
  draw[oldstage](olddx, dy, ex, ey, ST77XX_BLACK);
  draw[newstage](newdx, dy, ex, ey, color);
}
//=======================================================================

//=======================================================================
void text(const char* str) {
  int16_t  rx, ry;
  uint16_t rw, rh;
  const String text = str;
  tft.getTextBounds(text, 0, 0, &rx, &ry, &rw, &rh);
  int tx = (tft.width() - rw) / 2;  
  textfade = millis();
  tft.fillRect(0, 110, tft.width(), tft.height()-110, ST77XX_BLACK);
  tft.setCursor(tx, 110);
  tft.setTextColor(ST77XX_ORANGE, ST77XX_BLACK);
  tft.write(text.c_str());
}
//=======================================================================

//=======================================================================
void sendWakeOnLan(byte* mac)
{
    byte packet[102];
    // 6x FF
    for (int i = 0; i < 6; i++) {
      packet[i] = 0xFF;
    }

    // MAC 16x wiederholen
    for (int i = 0; i < 16; i++)
    {
        for (int j = 0; j < 6; j++)
        {
            packet[6 + i * 6 + j] = mac[j];
        }
    }

    udp.beginPacket(IPAddress(192,168,178,255), 9);
    udp.write(packet, sizeof(packet));
    udp.endPacket();
}
//=======================================================================


//=======================================================================
bool detectSoundPeak() {
  int new_value = digitalRead(SOUND_PIN);
  unsigned long now = millis();
  bool detected = false;
  if ((peak_value == LOW) && (new_value == HIGH)) {
    if ((now - last_peak) >= 200) {
      detected = true;
      last_peak = now;
    }
  }
  peak_value = new_value;
  return detected;
}
//=======================================================================

//=======================================================================
void tasmotaCommand(
    const char* host,
    const char* command)
{
    if (!client.connect(host, 80))
        return;

    client.print("GET /cm?cmnd=");
    client.print(command);
    client.println(" HTTP/1.1");

    client.print("Host: ");
    client.println(host);

    client.println("Connection: close");
    client.println();
}
//=======================================================================

//=======================================================================
void setup() {
  // put your setup code here, to run once:

  tft.initR(INITR_BLACKTAB);
  tft.fillScreen(ST77XX_BLACK);
  
  mark = wakefade = millis();
  
  color = ST77XX_BLUE;
  draw[stage](dx, dy, 0, 0, color);

  tft.setTextSize(2);
  tft.setTextColor(ST77XX_ORANGE);

  textfade = 0;
  text("Hello!");
  
  pinMode(SOUND_PIN, INPUT);

  current_state = STATE_WAKE_HAPPY;
 
}
//=======================================================================

//=======================================================================
void updateWifi()
{
    unsigned long now = millis();

    if (WiFi.status() == WL_CONNECTED)
        return;

    if (now - last_wifi_try >= 5000)
    {
        last_wifi_try = now;
        WiFi.begin(ssid, password);
        udp.begin(9);
        text("WIFI");
    }
}
//=======================================================================

//=======================================================================
void aniSleepingNeutral() {

  int oldstage = stage;
  int olddx    = dx;
  
  int chance = random(100);
  if (chance < 5) {
    text("Zzzzz");
  }
  if (chance < 10) {
    dx = (random(3)-1) * 10;
  } else {
    dx = 0;
  }

  chance = random(100);
  if (chance < 1) {
    stage = EYES_CONFUSED;
  } else if (chance < 5) {
    stage = EYES_QUENCHED;
  } else {
    stage = EYES_CLOSED;  
  }

  render(oldstage, stage, olddx, dx, dy, dx/2, 0, color);
  
}
//=======================================================================

//=======================================================================
void aniSleepingUneasy() {

  int oldstage = stage;
  int olddx    = dx;
  
  int chance = random(100);
  if (chance < 5) {
    text("Chrr");
  }
  if (chance < 50) {
    dx = (random(3)-1) * 10;
  }

  chance = random(100);
  if (chance < 5) {
    stage = EYES_CONFUSED;
  } else if (chance < 20) {
    stage = EYES_QUENCHED;
  } else {
    stage = EYES_CLOSED;
  }

  render(oldstage, stage, olddx, dx, dy, dx/2, 0, color);
  
}
//=======================================================================

//=======================================================================
void aniListening() {

  int oldstage = stage;
  int olddx    = dx;
  dx = 0;

  int chance = random(100);
  if (chance < 5) {
    stage = EYES_SHOCKED;
  } else {
    stage = EYES_OPEN;  
  }

  render(oldstage, stage, olddx, dx, dy, dx/2, 0, color);
  
}
//=======================================================================

//=======================================================================
void aniWakeConfused() {

  int oldstage = stage;
  int olddx    = dx;
  
  int chance = random(100);
  if (chance < 25) {
    dx = (random(3)-1) * 10;
  }

  chance = random(100);
  if (chance < 1) {
    stage = EYES_SHOCKED;
  } else if (chance < 15) {
    stage = EYES_CONFUSED;
  } else {
    stage = EYES_OPEN;
  }

  render(oldstage, stage, olddx, dx, dy, dx/2, 0, color);
  
}
//=======================================================================

//=======================================================================
void aniWakeHappy() {

  int oldstage = stage;
  int olddx    = dx;
  
  int chance = random(100);
  if (chance < 25) {
    dx = (random(3)-1) * 10;
  }

  chance = random(100);
  if (chance < 1) {
    stage = EYES_QUENCHED;
  } else if (chance < 5) {
    stage = EYES_CLOSED;
  } else if (chance < 40) {
    stage = EYES_OPEN;
  } else {
    stage = EYES_NEUTRAL;
  }

  render(oldstage, stage, olddx, dx, dy, dx/2, 0, color);
  
}
//=======================================================================

//=======================================================================
void loop() {

  updateWifi();

  if (detectSoundPeak()) {
    switch (current_state) {
      case STATE_LISTENING: {
        peak_count++;
        int oldstage = stage;
        int olddx = dx;
        stage = EYES_QUENCHED;
        dx = 0;
        render(oldstage, stage, olddx, dx, dy, 0, 0, color);
        break;
      }
      default: {
        current_state = STATE_LISTENING;
        peak_count = 0;
        text("HUH?");
        int oldstage = stage;
        int olddx = dx;
        stage = EYES_SHOCKED;
        dx = 0;
        render(oldstage, stage, olddx, dx, dy, 0, 0, color);
        activated = millis();
        break;
      }
    }
  }

  unsigned long now = millis();
  unsigned long delta = now-mark;
  
  if (delta >= 200) {
    mark = now;
    switch (current_state) {
      case STATE_SLEEPING_NEUTRAL: {
        aniSleepingNeutral();
        break;
      }
      case STATE_SLEEPING_UNEASY: {
        aniSleepingUneasy();
        break;
      }
      case STATE_LISTENING: {
        aniListening();
        unsigned long delta = now-activated;
        if (delta >= 3000) {
          current_state = STATE_WAKE_HAPPY;
          wakefade = now;
          switch (peak_count) {
            case 1: {
              text("Nacht!");
              tasmotaCommand("192.168.178.84", "Power%20Toggle");
              break;
            }
            case 2: {
              text("Licht!");
              tasmotaCommand("192.168.178.82", "Power%20Toggle");
              break;
            }
            case 3: {
              text("An Prime!");
              sendWakeOnLan(wol_mac_first);
              break;
            }
            case 4: {
              text("An Second!");
              sendWakeOnLan(wol_mac_second);
              break;
            }
            default: {
              current_state = STATE_WAKE_CONFUSED;
              text("HMM?");
              break;
            }
          }
        }
        break;
      }
      case STATE_WAKE_CONFUSED: {
        aniWakeConfused();
        if ((now-wakefade) >= (10000 + random(30000))) {
          current_state = STATE_SLEEPING_UNEASY;
        }
        break;
      }
      case STATE_WAKE_HAPPY: {
        aniWakeHappy();
        if ((now-wakefade) >= (10000 + random(10000))) {
          current_state = STATE_SLEEPING_NEUTRAL;
        }
        break;
      }
      default: {
        aniWakeHappy();
        break;
      }
    }
  }

  if (textfade != 0) {
    unsigned long textdelta = now-textfade;
    if (textdelta >= 2500) {
      textfade = 0;
      tft.fillRect(0, 110, tft.width(), tft.height()-110, ST77XX_BLACK);
    }
  }
 
}
//=======================================================================
