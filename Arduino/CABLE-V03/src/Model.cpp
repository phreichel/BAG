#include "Model.h"

//=============================================================================
inline const float SLED_HALF_WIDTH = 4.0f;
inline const float DRUM_RADIUS     = 2.5f;
inline const float STEP_LENGTH     = (DRUM_RADIUS * M_PI) / (100 * 16);
//=============================================================================

//=============================================================================
Model::Model() {
	init_a(-24.0f, -24.5f, 37.5f, 48.5f);
	init_b( 25.5f, -24.5f, 37.5f, 48.5f);
	init_c( 25.5f,  23.5f, 37.5f, 48.0f);
	init_d(-24.0f,  23.5f, 37.5f, 48.0f);
}
//=============================================================================

//=============================================================================
Model::~Model() {}
//=============================================================================

//=============================================================================
void Model::init_a(float x, float y, float z, float l) {
	ax = x;
	ay = y;
	az = z;
	al = l;
}
//=============================================================================

//=============================================================================
void Model::init_b(float x, float y, float z, float l) {
	bx = x;
	by = y;
	bz = z;
	bl = l;
}
//=============================================================================

//=============================================================================
void Model::init_c(float x, float y, float z, float l) {
	cx = x;
	cy = y;
	cz = z;
	cl = l;
}
//=============================================================================

//=============================================================================
void Model::init_d(float x, float y, float z, float l) {
	dx = x;
	dy = y;
	dz = z;
	dl = l;
}
//=============================================================================

//=============================================================================
void Model::at(float px, float py, float pz) {

	float sadx = px - SLED_HALF_WIDTH;
	float saby = py - SLED_HALF_WIDTH;
	float sbcx = px + SLED_HALF_WIDTH;
	float scdy = py + SLED_HALF_WIDTH;

	float dax = ax - sadx;
	float day = ay - saby;
	float daz = az - pz;

	float dbx = bx - sbcx;
	float dby = by - saby;
	float dbz = bz - pz;

	float dcx = cx - sbcx;
	float dcy = cy - scdy;
	float dcz = cz - pz;

	float ddx = dx - sadx;
	float ddy = dy - scdy;
	float ddz = dz - pz;

	float cal = sqrt(dax*dax + day*day + daz*daz);
	float cbl = sqrt(dbx*dbx + dby*dby + dbz*dbz);
	float ccl = sqrt(dcx*dcx + dcy*dcy + dcz*dcz);
	float cdl = sqrt(ddx*ddx + ddy*ddy + ddz*ddz);

	float dal = al - cal;
	float dbl = bl - cbl;
	float dcl = cl - ccl;
	float ddl = dl - cdl;

	stpa = round(dal / STEP_LENGTH);
	stpb = round(dbl / STEP_LENGTH);
	stpc = round(dcl / STEP_LENGTH);
	stpd = round(ddl / STEP_LENGTH);

}
//=============================================================================
