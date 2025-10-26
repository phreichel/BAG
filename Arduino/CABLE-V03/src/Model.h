#ifndef __Model_h__
#define __Model_h__

#include <math.h>

//=============================================================================
class Model {

	//-------------------------------------------------------------------------
	public:
	//-------------------------------------------------------------------------

	Model();
	~Model();

	void init_a(float x, float y, float z, float l);
	void init_b(float x, float y, float z, float l);
	void init_c(float x, float y, float z, float l);
	void init_d(float x, float y, float z, float l);

	void at(float px, float py, float pz);

	unsigned int stpa, stpb, stpc, stpd;

	//-------------------------------------------------------------------------
	private:
	//-------------------------------------------------------------------------

	float ax, ay, az, al;
	float bx, by, bz, bl;
	float cx, cy, cz, cl;
	float dx, dy, dz, dl;

};
//=============================================================================

#endif
