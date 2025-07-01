#include <WiFi.h>
#include <ArduinoJson.h>
#include "MotorController.hpp"

//=============================================================================
const char* wifi_ssid     = "hr";
const char* wifi_password = "59294905537503957609";
const int   wifi_port     = 80;
//=============================================================================

//=============================================================================
WiFiServer wifi_server(wifi_port);
WiFiClient wifi_client;
//=============================================================================

//=============================================================================
String wifi_request_string;
JsonDocument wifi_request_doc;
//=============================================================================

//=============================================================================
MotorController ctrl;
//=============================================================================

//=============================================================================
void handle_ping() {

  const String& txt = wifi_request_doc["text"].as<String>();

  Serial.print("> pong: ");
  Serial.print(txt);
  Serial.println();

}
//=============================================================================

//=============================================================================
void handle_single() {

  const String& motor = wifi_request_doc["motor"].as<String>();
  int steps = wifi_request_doc["steps"].as<int>();

  if      (motor.equals("A")) ctrl.move(steps, 0, 0, 0);
  else if (motor.equals("B")) ctrl.move(0, steps, 0, 0);
  else if (motor.equals("C")) ctrl.move(0, 0, steps, 0);
  else if (motor.equals("D")) ctrl.move(0, 0, 0, steps);

}
//=============================================================================

//=============================================================================
void handle_move() {

  int a = wifi_request_doc["a"].as<int>();
  int b = wifi_request_doc["b"].as<int>();
  int c = wifi_request_doc["c"].as<int>();
  int d = wifi_request_doc["d"].as<int>();

  ctrl.move(a, b, c, d);

}
//=============================================================================

//=============================================================================
void handle_disconnect() {

  Serial.println("> disconnect.");
  wifi_client.stop();

}
//=============================================================================

//=============================================================================
void handle_reset() {

  Serial.println("> reset.");
  ESP.restart();

}
//=============================================================================

//=============================================================================
void handle_request() {
  const String& cmd = wifi_request_doc["cmd"].as<String>();
  if      (cmd.equals("ping")) handle_ping();
  else if (cmd.equals("single")) handle_single();
  else if (cmd.equals("move")) handle_move();
  else if (cmd.equals("disconnect")) handle_disconnect();
  else if (cmd.equals("reset")) handle_reset();
}
//=============================================================================

//=============================================================================
void handle_response() {
  Serial.println("done.");
    if (wifi_client && wifi_client.connected()) {
      wifi_client.write("*");
      wifi_client.flush();
    }
}

//=============================================================================
void wifi_update() {

  if (!wifi_client || !wifi_client.connected()) {
    wifi_client = wifi_server.available();
    if (wifi_client && wifi_client.connected()) {
      Serial.println("> Wifi client connect");
    }
  } else  if (wifi_client && wifi_client.connected() && wifi_client.available()) {
    wifi_request_string = wifi_client.readStringUntil('\n');
    Serial.print("> Wifi client receive: ");
    Serial.println(wifi_request_string);

    wifi_request_doc.clear();
    DeserializationError error = deserializeJson(wifi_request_doc, wifi_request_string);

    if (error) {
      Serial.print("> Wifi JSON deserialization error: ");
      Serial.println(error.c_str());
    } else {
      handle_request();
    }
  }

}
//=============================================================================

//=============================================================================
void setup() {

  Serial.begin(115200);
  Wire.begin(19, 18);

  delay(1000);
  ctrl.init();

  Serial.println();
  Serial.println("******************");
  Serial.println("** Moving Frame **");
  Serial.println("******************");
  Serial.println();

  Serial.print("> WiFi setup");
  WiFi.mode(WIFI_STA);
  WiFi.begin(wifi_ssid, wifi_password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.print(".");
  }
  wifi_server.begin();
  Serial.println("done.");
  Serial.print("> WiFi Server host: ");
  Serial.print(WiFi.localIP());
  Serial.print(", port: ");
  Serial.println(wifi_port);
  Serial.println();

}
//=============================================================================

//=============================================================================
void loop() {

  wifi_update();
  if (ctrl.update())
    handle_response();
  delay(5);

}
//=============================================================================
