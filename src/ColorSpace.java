
public class ColorSpace {
	public static float[] RGBToYxy(float r, float g, float b) {
		float[] XYZ = RGBToXYZ(r, g, b);
		float[] Yxy = XYZToYxy(XYZ[0], XYZ[1], XYZ[2]);

		return Yxy;
	}

	public static float[] RGBToXYZ(float r, float g, float b) {
		// scale rgb values from [0, 255] to [0, 1]
		r = r/255;
		g =	g/255;
		b =	b/255;
		float[][] XYZmatrix = { 
				{ 0.5141f, 0.3239f, 0.1604f },
				{ 0.2651f, 0.6702f, 0.0641f }, 
				{ 0.0241f, 0.1228f, 0.8444f }};
		float[] XYZ = new float[3];
		XYZ[0] = XYZmatrix[0][0] * r + XYZmatrix[0][1] * g + XYZmatrix[0][2]
				* b;
		XYZ[1] = XYZmatrix[1][0] * r + XYZmatrix[1][1] * g + XYZmatrix[1][2]
				* b;
		XYZ[2] = XYZmatrix[2][0] * r + XYZmatrix[2][1] * g + XYZmatrix[2][2]
				* b;
		return XYZ;
	}
	
	public static float[] XYZToYxy(float X, float Y, float Z){
		float[] Yxy = new float [3];
		float sumxyz = X+Y+Z;
		
		Yxy[0] = Y;
		if (sumxyz != 0) {
			Yxy[1] = X / sumxyz;
			Yxy[2] = Y / sumxyz;
		}

		return Yxy;
	}

	public static float[] YxyToRGB(float Y, float x, float y){
		float[] XYZ = YxyToXYZ(Y, x, y);
		float[] RGB = XYZtoRGB(XYZ[0], XYZ[1], XYZ[2]);
		
		return RGB;
	}
	
	public static float[] YxyToXYZ(float Y, float x, float y) {
		float[] XYZ = new float[3];
		
		if (y == 0) {
			XYZ[0] = 0;
			XYZ[1] = 0;
			XYZ[1] = 0;
		} else {
			XYZ[0] = (x * Y) / y;
			XYZ[1] = Y;
			XYZ[2] = ((1 - x - y) * Y) / y;
		}
		return XYZ;
	}
	
	public static float[] XYZtoRGB(float X, float Y, float Z){
		float[][] RGBMatrix = {
				{2.56552f, -1.16682f, -0.398764f}, 
				{-1.02201f, 1.97796f, 0.0439884f}, 
				{0.0754076f, -0.25435f, 1.18926f}};	
		float[] RGB = new float[3];
		RGB[0] = (RGBMatrix[0][0] * X + RGBMatrix[0][1] * Y + RGBMatrix[0][2]
				* Z) * 255;
		RGB[1] = (RGBMatrix[1][0] * X + RGBMatrix[1][1] * Y + RGBMatrix[1][2]
				* Z) * 255;
		RGB[2] = (RGBMatrix[2][0] * X + RGBMatrix[2][1] * Y + RGBMatrix[2][2]
				* Z) * 255;
		return RGB;
	}
}
