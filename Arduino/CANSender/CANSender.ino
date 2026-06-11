#include <driver/twai.h>

void setup() {

  //Serial.begin(115200);

  twai_general_config_t g = TWAI_GENERAL_CONFIG_DEFAULT(
    GPIO_NUM_17,
    GPIO_NUM_16,
    TWAI_MODE_NORMAL);

  twai_timing_config_t t = TWAI_TIMING_CONFIG_500KBITS();
  twai_filter_config_t f = TWAI_FILTER_CONFIG_ACCEPT_ALL();

  twai_driver_install(&g, &t, &f);
  twai_start();  
  
}

void loop() {
  
  twai_message_t msg = {};
  msg.identifier = 0x123;
  msg.data_length_code = 6;
  strcpy((char*) msg.data, "Hello");

  twai_transmit(&msg, pdMS_TO_TICKS(100));
  delay(1000);
  
}
