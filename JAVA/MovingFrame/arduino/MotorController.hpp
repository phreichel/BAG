#ifndef __MOTORCONTROLLER_HPP__
#define __MOTORCONTROLLER_HPP__

#include <Wire.h>
#include <Adafruit_MCP23X17.h>

//=============================================================================
class MotorController {

  //=========================================================================
  public:
  //=========================================================================

  //-----------------------------------------------------------------------
  void init() {

    mcp.begin_I2C();
    for (int i = 0; i < 16; i++) mcp.pinMode(i, OUTPUT);

    mode = 0;
    mcp.writeGPIOAB(0x0000);

  }
  //-----------------------------------------------------------------------

  //-----------------------------------------------------------------------
  void move(int a, int b, int c, int d) {

    mode = 1;

    n = 0;

    n = max(n, abs(a));
    n = max(n, abs(b));
    n = max(n, abs(c));
    n = max(n, abs(d));

    na = +a; nb = -b; nc = +c; nd = -d;
    sa =  0; sb =  0; sc =  0; sd =  0;
    pa =  0; pb =  0; pc =  0; pd =  0;

  }
  //-----------------------------------------------------------------------

  //-----------------------------------------------------------------------
  bool update() {
    
    if (mode != 1) return false;

    if (n <= 0) {

      mode = 0;
      na = 0; nb = 0; nc = 0; nd = 0;
      pa = 0; pb = 0; pc = 0; pd = 0;
      mcp.writeGPIOAB(0x0000);

      return true;

    } else {

      int da=0, db=0, dc=0, dd=0;

      if ((sa == 0) && (na != 0)) sa = n / abs(na);
      if ((sb == 0) && (nb != 0)) sb = n / abs(nb);
      if ((sc == 0) && (nc != 0)) sc = n / abs(nc);
      if ((sd == 0) && (nd != 0)) sd = n / abs(nd);
      sa--; sb--; sc--; sd--;
      
      if (sa == 0) {
          da = na / abs(na);
          na -= da;
          pa += da;
      } else {
        da = 0;
      }

      if (sb == 0) {
          db = nb / abs(nb);
          nb -= db;
          pb += db;
      } else {
        db = 0;
      }

      if (sc == 0) {
          dc = nc / abs(nc);
          nc -= dc;
          pc += dc;
      } else {
        dc = 0;
      }

      if (sd == 0) {
          dd = nd / abs(nd);
          nd -= dd;
          pd += dd;
      } else {
        dd = 0;
      }

      n -= 1;
      
      uint16_t pins =
        pattern(pa) * 0x0001 +
        pattern(pb) * 0x0010 +
        pattern(pc) * 0x0100 +
        pattern(pd) * 0x1000;
      mcp.writeGPIOAB(pins);

      return false;

    }

  }
  //-----------------------------------------------------------------------

  //=========================================================================
  private:
  //=========================================================================

  //-----------------------------------------------------------------------
  uint16_t pattern(int phase) {
    if (phase == 0) return 0b0000;
    else if (phase > 0) {
      switch((+phase-1) % 4) {
        case 0: return 0b0001;
        case 1: return 0b0010;
        case 2: return 0b0100;
        case 3: return 0b1000;
      }
    } else {
      switch((-phase-1) % 4) {
        case 0: return 0b1000;
        case 1: return 0b0100;
        case 2: return 0b0010;
        case 3: return 0b0001;
      }
    }
    return 0b0000; // just in case.
  }
  //-----------------------------------------------------------------------

  //-----------------------------------------------------------------------
  int mode = 0;
  int n;
  int na, nb, nc, nd;
  int sa, sb, sc, sd;
  int pa, pb, pc, pd;
  Adafruit_MCP23X17 mcp;
  //-----------------------------------------------------------------------

};
//=============================================================================

#endif