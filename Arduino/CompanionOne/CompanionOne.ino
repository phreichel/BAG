#include <Adafruit_ST7735.h>
#include <Adafruit_GFX.h>
#include <SPI.h>
#include <WiFi.h>
#include <WiFiClient.h>
#include <WiFiUdp.h>

#define TFT_CS   5
#define TFT_DC   2
#define TFT_RST -1

#define SOUND_PIN 27

//=======================================================================
const char* ssid = "hr";
const char* password = "59294905537503957609";
//=======================================================================

//=======================================================================
Adafruit_ST7735 tft = Adafruit_ST7735(TFT_CS, TFT_DC, TFT_RST);
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
const int DRAW_COUNT = sizeof(draw) / sizeof(draw[0]);
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
unsigned long textfade;
//=======================================================================

//=======================================================================
void text(const char* str) {
  int16_t  rx, ry;
  uint16_t rw, rh;
  const String text = str;
  tft.getTextBounds(text, 0, 0, &rx, &ry, &rw, &rh);
  int tx = (tft.width() - rw) / 2;  
  tft.fillRect(0, 110, tft.width(), tft.height()-110, ST77XX_BLACK);
  tft.setCursor(tx, 110);
  tft.write(text.c_str());
  textfade = millis();
}
//=======================================================================

#define PASSIVE 0
#define ACTIVE  1

//=======================================================================
unsigned int input_mode = PASSIVE;
//=======================================================================

//=======================================================================
unsigned long mark;
unsigned long sound;
//=======================================================================

//=======================================================================
int stage = 0;
int dx    = 0;
const int dy = 0;
uint16_t color = ST77XX_BLUE;
//=======================================================================

//=======================================================================
int peak_value = LOW;
unsigned long last_peak = 0;
unsigned long activated;
int peak_count = 0;
//=======================================================================

//=======================================================================
byte wol_mac_first[]  = { 0x74, 0x56, 0x3c, 0xfd, 0x91, 0xf2 };
byte wol_mac_second[] = { 0x0C, 0x9D, 0x92, 0x82, 0x31, 0x95 };
//=======================================================================

//=======================================================================
WiFiClient client;
WiFiUDP udp;
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
  
  mark = millis();
  color = ST77XX_BLUE;
  draw[stage](dx, dy, 0, 0, color);

  tft.setTextSize(2);
  tft.setTextColor(ST77XX_ORANGE);

  textfade = 0;
  text("Hello!");
  
  sound = 0;  
  pinMode(SOUND_PIN, INPUT);
 
}
//=======================================================================

//=======================================================================
unsigned long last_wifi_try = 0;
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
void loop() {

  updateWifi();
  
  unsigned long ms = millis();
  unsigned long delta = ms-mark;
  
  if (delta >= 500) {
  
    mark = ms;
    long rnd = random(100);
    
    if (rnd < 20) {
      int oldstage = stage;
      stage = random(DRAW_COUNT-1);
      render(oldstage, stage, dx, dx, dy, dx/2, 0, color);
    }
    
    else if (rnd < 30) {
      int olddx = dx;
      dx = (random(3)-1) * 10;
      render(stage, stage, olddx, dx, dy, dx/2, 0, color);
    }
    
  }

  if ((input_mode == PASSIVE) && detectSoundPeak()) {
    input_mode = ACTIVE;
    peak_count = 0;
    text("YES?");
    render(stage, 5, dx, 0, dy, 0, 0, color);
    stage = 5;
    dx = 0;
    activated = millis();
  }

  if (input_mode == ACTIVE) {
    unsigned long compare = millis();
    unsigned long delta = compare - activated;
    if (delta >= 3000) {
      input_mode = PASSIVE;
      switch (peak_count) {
        case 1:
          text("Nacht!");
          tasmotaCommand("192.168.178.84", "Power%20Toggle");
          break;
        case 2:
          text("Licht!");
          tasmotaCommand("192.168.178.82", "Power%20Toggle");
          break;
        case 3:
          text("An Prime!");
          sendWakeOnLan(wol_mac_first);
          break;
        case 4:
          text("An Second!");
          sendWakeOnLan(wol_mac_second);
          break;
        default:
          text("Hm.");
          break;
      }
    }
    if (detectSoundPeak()) peak_count++;
  }  

  if (textfade != 0) {
    unsigned long d = millis() - textfade;
    if (d >= 2500) {
      textfade = 0;
      tft.fillRect(0, 110, tft.width(), tft.height()-110, ST77XX_BLACK);
    }
  }
 
}
//=======================================================================
