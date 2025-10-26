//*************************************************************************************************
package phi;
//*************************************************************************************************

import javax.vecmath.Vector2f;

//*************************************************************************************************
public class Frame {

	//=============================================================================================
	public int stepsPerRevolutiuon = 2048;
	public float lengthPerRevolution = 1f;
	//=============================================================================================
	
	//=============================================================================================
	public Vector2f a = new Vector2f();
	public Vector2f b = new Vector2f();
	public Vector2f c = new Vector2f();
	public Vector2f d = new Vector2f();
	//=============================================================================================

	//=============================================================================================
	public StepSequence calcStepSequence(Steps steps) {
		
		var max = 0;
		max = Math.max(max, steps.a);
		max = Math.max(max, steps.b);
		max = Math.max(max, steps.c);
		max = Math.max(max, steps.d);

		var s = new StepSequence(max);
		equalDistribution(s.a, Math.abs(steps.a), 0, s.a.length-1);
		equalDistribution(s.b, Math.abs(steps.b), 0, s.b.length-1);
		equalDistribution(s.c, Math.abs(steps.c), 0, s.c.length-1);
		equalDistribution(s.d, Math.abs(steps.d), 0, s.d.length-1);
		if (steps.a < 0) for (var i=0; i<s.a.length; i++) if (s.a[i] == 1) s.a[i] = -1;
		if (steps.b < 0) for (var i=0; i<s.b.length; i++) if (s.b[i] == 1) s.b[i] = -1;
		if (steps.c < 0) for (var i=0; i<s.c.length; i++) if (s.c[i] == 1) s.c[i] = -1;
		if (steps.d < 0) for (var i=0; i<s.d.length; i++) if (s.d[i] == 1) s.d[i] = -1;
		return s;
		
	}
	//=============================================================================================

	//=============================================================================================
	public static void equalDistribution(byte[] arr, int m, int start, int end) {

		if (m == 0 || start > end) return;
		m--;
	
		var mid = start + (end - start) / 2;
		arr[mid] = 1;

		equalDistribution(arr, m / 2, start, mid - 1);
		equalDistribution(arr, m - m / 2, mid + 1, end);

	}
	//=============================================================================================
	   
	//=============================================================================================
	public Steps calcSteps(Lengths lengths) {
		
		var steps = new Steps();
		steps.a = calcSteps(lengths.a);
		steps.b = calcSteps(lengths.b);
		steps.c = calcSteps(lengths.c);
		steps.d = calcSteps(lengths.d);
		return steps;
		
	}
	//=============================================================================================
	
	//=============================================================================================
	public int calcSteps(float length) {
		
		var revolutions = length / lengthPerRevolution;
		var steps = (int) Math.rint(stepsPerRevolutiuon * revolutions);
		return steps;
		
	}
	//=============================================================================================
	
	//=============================================================================================
	public Lengths calcLengthsDifferences(Vector2f pos1, Vector2f pos2) {
		
		var l1 = calcLengths(pos1);
		var l2 = calcLengths(pos2);
		var ld = new Lengths();
		
		ld.a = l2.a - l1.a;
		ld.b = l2.b - l1.b;
		ld.c = l2.c - l1.c;
		ld.d = l2.d - l1.d;
		
		return ld;

	}
	//=============================================================================================
	
	//=============================================================================================
	public Lengths calcLengths(Vector2f pos) {
		
		var l = new Lengths();
		l.a = calcLength(pos, a);
		l.b = calcLength(pos, b);
		l.c = calcLength(pos, c);
		l.d = calcLength(pos, d);
		return l;
		
	}
	//=============================================================================================

	//=============================================================================================
	public float calcLength(Vector2f pos1, Vector2f pos2) {

		var d = new Vector2f(pos2);
		d.sub(pos1);
		return d.length();

	}
	//=============================================================================================
	
}
//*************************************************************************************************
