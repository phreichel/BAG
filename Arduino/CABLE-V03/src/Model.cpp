#include "Model.h"

//=============================================================================
void Model::at(float px, float py, float pz) {

	float sabx = px - 4.0f;
	float sady = py - 4.0f;
	float scdx = px + 4.0f;
	float sbcy = py + 4.0f;

	float dax = ax - sabx;
	float day = ay - sady;
	float daz = az - pz;

	float dbx = bx - sabx;
	float dby = by - sbcy;
	float dbz = bz - pz;

	float dcx = cx - scdx;
	float dcy = cy - sbcy;
	float dcz = cz - pz;

	float ddx = dx - scdx;
	float ddy = dy - sady;
	float ddz = dz - pz;

	float cal = sqrt(dax*dax + day*day + daz*daz);
	float cbl = sqrt(dax*dax + day*day + daz*daz);
	float ccl = sqrt(dax*dax + day*day + daz*daz);
	float cdl = sqrt(dax*dax + day*day + daz*daz);

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
