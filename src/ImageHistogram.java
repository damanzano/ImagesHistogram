import java.util.ArrayList;
import java.util.Arrays;

import processing.core.PApplet;
import processing.core.PImage;

public class ImageHistogram extends PApplet {
	PImage image;
	PImage image2;
	ArrayList<float[]> imagehist;
	ArrayList<float[]> image2hist;
	PImage resImage;
	PImage resImage2;
	PImage resImage3;
	PImage resImage4;

	public void setup() {
		image = loadImage("../data/retrato1.jpg");
		image2 = loadImage("../data/retrato2.jpg");

		size(3 * (image.width + image2.width),
				max(image.height, image2.height) + 600);

		imagehist = colorHistograms(image);
		image2hist = colorHistograms(image2);
		resImage = colorTransferAHRGB(image, imagehist, image2, image2hist);
		resImage2 = colorTransferAHRGB(image2, image2hist, image, imagehist);
		resImage3 = colorTransferAHYXy(image, imagehist, image2, image2hist);
		resImage4 = colorTransferAHYXy(image2, image2hist, image, imagehist);
		resImage.save("../results/result.jpg");
		resImage2.save("../results/result2.jpg");
		resImage3.save("../results/result3.jpg");
		resImage4.save("../results/result4.jpg");

	}

	public void draw() {

		image(image, 0, 0);
		image(image2, image.width, 0);
		image(resImage, image.width + image2.width, 0);
		image(resImage2, (2 * image.width) + image2.width, 0);
		image(resImage3, (2 * image.width) + (2*image2.width), 0);
		image(resImage4, (3 * image.width) + (2*image2.width), 0);
		
		// Draw image labels
		text("Image 1", 0, 10);
		text("Image 2", image.width, 10);
		text("Image 1 to Image 2 RGB", image.width + image2.width, 10);
		text("Image 2 to Image 1 RGB", (2 * image.width) + image2.width, 10);
		text("Image 1 to Image 2 Yxy", (2 * image.width) + (2*image2.width), 10);
		text("Image 2 to Image 1 Yxy", (3 * image.width) + (2*image2.width), 10);

		// Red histograms
		// drawHistogram(imagehist.get(0), 0, max(image.height,image2.height),
		// color(255, 0, 0));
		// drawHistogram(image2hist.get(0), image.width,
		// max(image.height,image2.height), color(255, 0, 0));
		drawHistogram(imagehist.get(3), 0, max(image.height, image2.height),
				color(255, 0, 0), "Image 1 red Hist");
		drawHistogram(image2hist.get(3), image.width,
				max(image.height, image2.height), color(255, 0, 0), "Image 2 red Hist");
		
		// Y histograms
		// drawHistogram(imagehist.get(6), 0, max(image.height,image2.height),
		// color(255, 0, 0));
		// drawHistogram(image2hist.get(6), image.width,
		// max(image.height,image2.height), color(255, 0, 0));
		drawHistogram(imagehist.get(9), 2*image.width, max(image.height, image2.height),
				color(255, 255, 255), "Image 1 Y Hist");
		drawHistogram(image2hist.get(9), 3*image.width,
				max(image.height, image2.height), color(255, 255, 255), "Image 2 Y Hist");
		

		// Green histograms
		// drawHistogram(imagehist.get(1), 0,
		// max(image.height,image2.height)+200, color(0, 255, 0));
		// drawHistogram(image2hist.get(1), image.width,
		// max(image.height,image2.height)+200, color(0, 255, 0));
		drawHistogram(imagehist.get(4), 0,
				max(image.height, image2.height) + 200, color(0, 255, 0), "Image 1 green Hist");
		drawHistogram(image2hist.get(4), image.width,
				max(image.height, image2.height) + 200, color(0, 255, 0), "Image 2 green Hist");
		
		// x histograms
		// drawHistogram(imagehist.get(7), 0, max(image.height,image2.height),
		// color(255, 0, 0));
		// drawHistogram(image2hist.get(7), image.width,
		// max(image.height,image2.height), color(255, 0, 0));
		drawHistogram(imagehist.get(10), 2*image.width, max(image.height, image2.height) + 200,
				color(255, 255, 255), "Image 1 x Hist");
		drawHistogram(image2hist.get(10), 3*image.width,
				max(image.height, image2.height) + 200, color(255, 255, 255), "Image 2 x Hist");

		// Blue histograms
		// drawHistogram(imagehist.get(2), 0,
		// max(image.height,image2.height)+400, color(0, 0, 255));
		// drawHistogram(image2hist.get(2), image.width,
		// max(image.height,image2.height)+400, color(0, 0, 255));
		drawHistogram(imagehist.get(5), 0,
				max(image.height, image2.height) + 400, color(0, 0, 255), "Image 1 blue Hist");
		drawHistogram(image2hist.get(5), image.width,
				max(image.height, image2.height) + 400, color(0, 0, 255), "Image 2 blue Hist");
		
		// y histograms
		// drawHistogram(imagehist.get(8), 0, max(image.height,image2.height),
		// color(255, 0, 0));
		// drawHistogram(image2hist.get(8), image.width,
		// max(image.height,image2.height), color(255, 0, 0));
		drawHistogram(imagehist.get(11), 2*image.width, max(image.height, image2.height) + 400,
				color(255, 255, 255), "Image 1 y Hist");
		drawHistogram(image2hist.get(11), 3*image.width,
				max(image.height, image2.height) + 400, color(255, 255, 255), "Image 2 y Hist");
	}

	public ArrayList<float[]> colorHistograms(PImage image) {
		ArrayList<float[]> histograms = new ArrayList<float[]>();
		float[] redHistogram = new float[100];
		float[] blueHistogram = new float[100];
		float[] greenHistogram = new float[100];
		float[] acumredHistogram = new float[100];
		float[] acumblueHistogram = new float[100];
		float[] acumgreenHistogram = new float[100];
		float[] YHistogram = new float[100];
		float[] xHistogram = new float[100];
		float[] yHistogram = new float[100];
		float[] acumYHistogram = new float[100];
		float[] acumxHistogram = new float[100];
		float[] acumyHistogram = new float[100];

		image.loadPixels();
		for (int i = 0; i < image.pixels.length; i++) {
			int color = image.pixels[i];
			int red = (int) red(color);
			int blue = (int) blue(color);
			int green = (int) green(color);
			
			int r = (int) map(red, 0, 255, 0, 99);
			int b = (int) map(blue, 0, 255, 0, 99);
			int g = (int) map(green, 0, 255, 0, 99);

			float [] Yxy = ColorSpace.RGBToYxy(red, green, blue);
			int Y = (int) map(Yxy[0], 0, 0.9994f, 0, 99);
			int x = (int) map(Yxy[1], 0, 1, 0, 99);
			int y = (int) map(Yxy[2], 0, 1, 0, 99);
			
			redHistogram[r]++;
			blueHistogram[b]++;
			greenHistogram[g]++;
			YHistogram[Y]++;
			xHistogram[x]++;
			yHistogram[y]++;

		}

		// Calculate accumulative histograms
		for (int i = 0; i < redHistogram.length; i++) {
			if (i > 0) {
				acumredHistogram[i] = acumredHistogram[i - 1] + redHistogram[i];
				acumgreenHistogram[i] = acumgreenHistogram[i - 1]
						+ greenHistogram[i];
				acumblueHistogram[i] = acumblueHistogram[i - 1]
						+ blueHistogram[i];
				
				acumYHistogram[i] = acumYHistogram[i-1] + YHistogram[i];
				acumxHistogram[i] = acumxHistogram[i-1] + xHistogram[i];
				acumyHistogram[i] = acumyHistogram[i-1] + yHistogram[i];
				
			} else {
				acumredHistogram[i] = redHistogram[i];
				acumgreenHistogram[i] = greenHistogram[i];
				acumblueHistogram[i] = blueHistogram[i];
				
				acumYHistogram[i] = YHistogram[i];
				acumxHistogram[i] = xHistogram[i];
				acumyHistogram[i] = yHistogram[i];
			}
		}

		histograms.add(redHistogram);
		histograms.add(greenHistogram);
		histograms.add(blueHistogram);
		histograms.add(acumredHistogram);
		histograms.add(acumgreenHistogram);
		histograms.add(acumblueHistogram);
		
		histograms.add(YHistogram);
		histograms.add(xHistogram);
		histograms.add(yHistogram);
		histograms.add(acumYHistogram);
		histograms.add(acumxHistogram);
		histograms.add(acumyHistogram);

		return histograms;
	}

	public PImage colorTransferRGB(PImage base, ArrayList<float[]> baseHists,
			PImage target, ArrayList<float[]> targetHists) {
		PImage resImage = createImage(target.width, target.height, RGB);
		resImage.loadPixels();
		for (int i = 0; i < resImage.pixels.length; i++) {
			// Get each channel value in target's histograms
			int tColor = target.pixels[i];
			int tRed = (int) red(tColor);
			int tBlue = (int) blue(tColor);
			int tGreen = (int) green(tColor);

			int r = (int) map(tRed, 0, 255, 0, 99);
			int b = (int) map(tBlue, 0, 255, 0, 99);
			int g = (int) map(tGreen, 0, 255, 0, 99);

			// System.out.println("Target colors and indexes: ("+tRed+","+tGreen+","+tBlue+")("+r+","+g+","+b+")");

			float tRedValue = targetHists.get(0)[r];
			float tGreenValue = targetHists.get(1)[g];
			float tBlueValue = targetHists.get(2)[b];

			// Search in the base's histograms
			int bRed = getValueIndex(tRedValue, baseHists.get(0));
			int bGreen = getValueIndex(tGreenValue, baseHists.get(1));
			int bBlue = getValueIndex(tBlueValue, baseHists.get(2));

			r = (int) map(bRed, 0, 99, 0, 255);
			b = (int) map(bBlue, 0, 99, 0, 255);
			g = (int) map(bGreen, 0, 99, 0, 255);

			// System.out.println("Base colors and indexes: ("+r+","+g+","+b+")("+bRed+","+bGreen+","+bBlue+")");

			resImage.pixels[i] = color(r, g, b);

		}
		resImage.updatePixels();

		return resImage;
	}

	public PImage colorTransferAHRGB(PImage base, ArrayList<float[]> baseHists,
			PImage target, ArrayList<float[]> targetHists) {
		PImage resImage = createImage(target.width, target.height, RGB);
		resImage.loadPixels();
		for (int i = 0; i < resImage.pixels.length; i++) {
			// Get each channel value in target's histograms
			int tColor = target.pixels[i];
			int tRed = (int) red(tColor);
			int tBlue = (int) blue(tColor);
			int tGreen = (int) green(tColor);

			int r = (int) map(tRed, 0, 255, 0, 99);
			int b = (int) map(tBlue, 0, 255, 0, 99);
			int g = (int) map(tGreen, 0, 255, 0, 99);

			// System.out.println("Target colors and indexes: ("+tRed+","+tGreen+","+tBlue+")("+r+","+g+","+b+")");

			float tRedValue = targetHists.get(3)[r];
			float tGreenValue = targetHists.get(4)[g];
			float tBlueValue = targetHists.get(5)[b];

			// Search in the base's histograms
			int bRed = getValueIndex(tRedValue, baseHists.get(3));
			int bGreen = getValueIndex(tGreenValue, baseHists.get(4));
			int bBlue = getValueIndex(tBlueValue, baseHists.get(5));

			r = (int) map(bRed, 0, 99, 0, 255);
			b = (int) map(bBlue, 0, 99, 0, 255);
			g = (int) map(bGreen, 0, 99, 0, 255);

			// System.out.println("Base colors and indexes: ("+r+","+g+","+b+")("+bRed+","+bGreen+","+bBlue+")");

			resImage.pixels[i] = color(r, g, b);

		}
		resImage.updatePixels();

		return resImage;
	}

	public PImage colorTransferAHYXy(PImage base, ArrayList<float[]> baseHists,
			PImage target, ArrayList<float[]> targetHists) {
		PImage resImage = createImage(target.width, target.height, RGB);
		resImage.loadPixels();
		for (int i = 0; i < resImage.pixels.length; i++) {
			// Get RGB values in target's image
			int tColor = target.pixels[i];
			float tRed = red(tColor);
			float tBlue = blue(tColor);
			float tGreen = green(tColor);
			
			// Convert it to Yxy
			float[] Yxy = ColorSpace.RGBToYxy(tRed, tGreen, tBlue);
			//System.out.println("RGB ["+tRed+","+tGreen+","+tBlue+"] = Yxy"+Arrays.toString(Yxy));
			
			// Map the Yxy value to index
			int Yi = (int) map(Yxy[0], 0, 0.9994f, 0, 99);
			int xi = (int) map(Yxy[1], 0, 1, 0, 99);
			int yi = (int) map(Yxy[2], 0, 1, 0, 99);
			//System.out.println("Target histogram Indexes Yxy["+Yi+","+xi+","+yi+"]");
			
			// Get the Yxy value in target Yxy accumulative histogram
			float tYValue = targetHists.get(9)[Yi];
			float txValue = targetHists.get(10)[xi];
			float tyValue = targetHists.get(11)[yi];
			//System.out.println("Accumulated target values Yxy["+tYValue+","+txValue+","+tyValue+"]");
			

			// Search the index for the target value in the base's Yxy histograms
			Yi = getValueIndex(tYValue, baseHists.get(9));
			xi = getValueIndex(txValue, baseHists.get(10));
			yi = getValueIndex(tyValue, baseHists.get(11));
			//System.out.println("Base histogram Indexes Yxy["+Yi+","+xi+","+yi+"]");
			
			// Map the index to a Yxy value
			float bYValue = map(Yi, 0, 99, 0, 0.9994f);
			float bxValue = map(xi, 0, 99, 0, 1);
			float byValue = map(yi, 0, 99, 0, 1);
			//System.out.println("Base values Yxy["+bYValue+","+bxValue+","+byValue+"]");
			
			// Convert it to RGB
			float[] RGB = ColorSpace.YxyToRGB(bYValue, bxValue, byValue);
			//System.out.println("Yxy ("+bYValue+","+bxValue+","+byValue+"= RGB"+Arrays.toString(RGB));

			// Update de result image
			resImage.pixels[i] = color(RGB[0], RGB[1], RGB[2]);

		}
		resImage.updatePixels();

		return resImage;
	}

	public int getValueIndex(float value, float[] histogram) {
		int mindiffPos = -1;
		float mindiff = 0;
		for (int i = 0; i < histogram.length; i++) {
			float diff = abs(histogram[i] - value);
			if (diff == 0) {
				return i;
			} else {
				if (i == 0) {
					mindiff = diff;
					mindiffPos = 0;
				} else {
					if (diff < mindiff) {
						mindiff = diff;
						mindiffPos = i;
					}
				}
			}
		}
		return mindiffPos;
	}

	public void drawHistogram(float[] histogram, int x, int y, int color, String name) {
		float histMax = max(histogram);
		stroke(color);
		for (int i = 0; i < histogram.length; i++) {
			int bar = (int) map(histogram[i], 0, histMax, y + 200, y);
			line(x + (2 * i), y + 200, x + (2 * i), bar);
		}
		
		// Draw the label in the upper left corner
		text(name, x, y+10);
	}

	
}
