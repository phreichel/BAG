#ifndef F_CPU
#define F_CPU 1200000UL   // ATtiny13A CKDIV8
#endif

#include <stdint.h>
#include <avr/io.h>
#include <util/delay.h>

/* =======================
 *  SHIFT REGISTER INPUT
 * ======================= */
#define PIN_DATA   PB3   // Q7
#define PIN_PL     PB4   // Parallel Load (LOW)
#define PIN_CLK    PB1   // Shift Clock
#define DEBOUNCE_MS 10

/* =======================
 *  "SLOW I2C-LIKE" BUS CONFIG (Open-Drain)
 * ======================= */
#define I2C_SCL    PB0
#define I2C_SDA    PB2
#define I2C_ADDR   0x42

/* =======================
 *  TIMING (ms, absichtlich LANG)
 *  -> damit ESP32 alles locker dekodieren kann
 * ======================= */
#define BUS_IDLE_MS        5    // Bus muss vor START stabil HIGH/HIGH sein
#define START_HOLD_MS      3    // SDA LOW bei SCL HIGH (START) halten
#define STOP_HOLD_MS       3    // SDA HIGH bei SCL HIGH (STOP) halten

#define BIT_SETUP_MS       1    // DATA stabilisieren bevor SCL HIGH
#define SCL_HIGH_MS        2
#define SCL_LOW_MS         2

#define BYTE_GAP_MS        2    // Pause zwischen Bytes (zusätzlich zum SCL low)
#define ACK_SLOT_MS        2    // "ACK-Clock" High-Zeit (Dummy, keine Auswertung)

#define FRAME_GAP_MS      20    // nach STOP: eindeutige Frame-Trennung

#define DEBUG_PERIOD_MS  500    // periodisches Senden (Debug)

/* =======================
 *  Open-Drain Primitives
 * ======================= */
static inline void sda_low(void) {
    DDRB |=  (1 << I2C_SDA);
    PORTB &= ~(1 << I2C_SDA);
}
static inline void sda_release(void) {
    DDRB &= ~(1 << I2C_SDA);   // extern Pull-Up => HIGH
}

static inline void scl_low(void) {
    DDRB |=  (1 << I2C_SCL);
    PORTB &= ~(1 << I2C_SCL);
}
static inline void scl_release(void) {
    DDRB &= ~(1 << I2C_SCL);   // extern Pull-Up => HIGH
}

/* =======================
 *  Robust "I2C-like" signaling
 * ======================= */
static inline void bus_idle(void)
{
    sda_release();
    scl_release();
    _delay_ms(BUS_IDLE_MS);
}

static inline void i2c_like_start(void)
{
    bus_idle();

    // START: SDA fällt bei SCL HIGH
    sda_low();
    _delay_ms(START_HOLD_MS);

    // danach SCL LOW (Frame läuft)
    scl_low();
    _delay_ms(SCL_LOW_MS);
}

static inline void i2c_like_stop(void)
{
    // Vorbereitung: SDA LOW, SCL LOW
    sda_low();
    scl_low();
    _delay_ms(SCL_LOW_MS);

    // SCL HIGH
    scl_release();
    _delay_ms(SCL_HIGH_MS);

    // STOP: SDA steigt bei SCL HIGH
    sda_release();
    _delay_ms(STOP_HOLD_MS);
}

static inline void i2c_like_clock_bit(void)
{
    // SCL HIGH -> sample point
    scl_release();
    _delay_ms(SCL_HIGH_MS);

    // SCL LOW
    scl_low();
    _delay_ms(SCL_LOW_MS);
}

static inline void i2c_like_write_byte(uint8_t byte)
{
    for (uint8_t i = 0; i < 8; i++)
    {
        // DATA setzen (MSB first)
        if (byte & 0x80) sda_release();
        else             sda_low();

        _delay_ms(BIT_SETUP_MS);

        i2c_like_clock_bit();

        byte <<= 1;
    }

    // ACK-SLOT: Dummy-Clock, DATA loslassen, aber NICHT auswerten
    sda_release();
    _delay_ms(BIT_SETUP_MS);

    // ein separater "ACK clock"
    scl_release();
    _delay_ms(ACK_SLOT_MS);
    scl_low();
    _delay_ms(SCL_LOW_MS);

    // Gap zwischen Bytes für Decoder-Resync
    _delay_ms(BYTE_GAP_MS);
}

/* =======================
 *  SHIFT REGISTER READ
 * ======================= */
static inline void clk_pulse(void)
{
    PORTB |=  (1 << PIN_CLK);
    _delay_us(5);
    PORTB &= ~(1 << PIN_CLK);
    _delay_us(5);
}

uint16_t read_shift_register(void)
{
    uint16_t value = 0;

    // Parallel Load
    PORTB &= ~(1 << PIN_PL);
    _delay_us(20);
    PORTB |=  (1 << PIN_PL);
    _delay_us(20);

    PORTB &= ~(1 << PIN_CLK);

    for (uint8_t i = 0; i < 16; i++)
    {
        value <<= 1;
        if (PINB & (1 << PIN_DATA))
            value |= 1;
        clk_pulse();
    }

    return value;
}

uint16_t debounce_read(void)
{
    uint16_t prev = read_shift_register();

    for (;;)
    {
        _delay_ms(DEBOUNCE_MS);
        uint16_t curr = read_shift_register();
        if (curr == prev)
            return curr;
        prev = curr;
    }
}

/* =======================
 *  EVENT SEND (I2C-like)
 *  Packet: Status(16) + Diff(16) => 4 bytes MSB first
 * ======================= */
void output_event_i2c_like(uint16_t curr_state)
{
    static uint16_t prev_state = 0xFFFF;

    uint16_t diff = curr_state ^ prev_state;

    uint32_t packet =
        ((uint32_t)curr_state << 16) |
        (uint32_t)diff;

    // "Frame"
    i2c_like_start();

    // Address + W (wie I2C: 7bit<<1)
    i2c_like_write_byte((uint8_t)(I2C_ADDR << 1));

    // 4 Datenbytes MSB first
    for (int8_t i = 3; i >= 0; i--)
        i2c_like_write_byte((uint8_t)((packet >> (8 * i)) & 0xFF));

    i2c_like_stop();

    // harte Frame-Trennung
    _delay_ms(FRAME_GAP_MS);

    prev_state = curr_state;
}

/* =======================
 *  MAIN
 * ======================= */
int main(void)
{
    // Shift-Register IO
    DDRB &= ~(1 << PIN_DATA);
    DDRB |=  (1 << PIN_PL) | (1 << PIN_CLK);

    PORTB |=  (1 << PIN_DATA); // Pull-Up
    PORTB |=  (1 << PIN_PL);
    PORTB &= ~(1 << PIN_CLK);

    // Bus idle
    sda_release();
    scl_release();

    for (;;)
    {
        uint16_t stable = debounce_read();
        output_event_i2c_like(stable);

        // Debug: periodisch senden, egal ob Änderung
        _delay_ms(DEBUG_PERIOD_MS);
    }
}
