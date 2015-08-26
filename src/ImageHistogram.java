import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;


public class ImageHistogram extends PApplet{
	PImage image;
	PImage image2;
	ArrayList<int[]> imagehist;
	ArrayList<int[]> image2hist;
	PImage resImage;
	PImage resImage2;
	
	
	public void setup(){
		image=loadImage("../data/retrato1.jpg");
		image2 = loadImage("../data/retrato2.jpg");
		
		size(2*(image.width+image2.width),max(image.height,image2.height)+600);
		
		imagehist = colorHistograms(image);
		image2hist = colorHistograms(image2);
		resImage = colorTransfer(image, imagehist, image2, image2hist);
		resImage2 = colorTransfer(image2, image2hist, image, imagehist);
		//resImage.save("../results/result.jpg");
		//resImage2.save("../results/result2.jpg");
	}
	
	public void draw(){
		
		image(image,0,0);
		image(image2,image.width,0);
		image(resImage, image.width+image2.width,0);
		image(resImage2, (2*image.width)+image2.width,0);
		
		
		drawHistogram(imagehist.get(0), 0, max(image.height,image2.height), color(255, 0, 0));
		drawHistogram(image2hist.get(0), image.width, max(image.height,image2.height), color(255, 0, 0));
		drawHistogram(imagehist.get(1), 0, max(image.height,image2.height)+200, color(0, 255, 0));
		drawHistogram(image2hist.get(1), image.width, max(image.height,image2.height)+200, color(0, 255, 0));
		drawHistogram(imagehist.get(2), 0, max(image.height,image2.height)+400, color(0, 0, 255));
		drawHistogram(image2hist.get(2), image.width, max(image.height,image2.height)+400, color(0, 0, 255));
	}
	
	public ArrayList<int[]> colorHistograms(PImage image){
		ArrayList<int[]> histograms = new ArrayList<int[]>();
		int[] redHistogram = new int[100];
		int[] blueHistogram = new int[100];
		int[] greenHistogram = new int[100];
		
		image.loadPixels();
		for (int x = 0; x < image.pixels.length; x++) {
			int color = image.pixels[x];
			int red = (int) red(color);
			int blue = (int) blue(color);
			int green = (int) green(color);
			
			int r = (int) map(red, 0, 255, 0, 99);
			int b = (int) map(blue, 0, 255, 0, 99);
			int g = (int) map(green, 0, 255, 0, 99);
			
			redHistogram[r]++;
			blueHistogram[b]++;
			greenHistogram[g]++;
			
		}
		histograms.add(redHistogram);
		histograms.add(greenHistogram);
		histograms.add(blueHistogram);
		
		return histograms;
	}
	
	public PImage colorTransfer(PImage base, ArrayList<int[]> baseHists, PImage target, ArrayList<int[]> targetHists){
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
			
			//System.out.println("Target colors and indexes: ("+tRed+","+tGreen+","+tBlue+")("+r+","+g+","+b+")");
			
			int tRedValue = targetHists.get(0)[r];
			int tGreenValue = targetHists.get(1)[g];
			int tBlueValue = targetHists.get(2)[b];
			
			// Search in the base's histograms
			int bRed = getValueIndex(tRedValue, baseHists.get(0));
			int bGreen = getValueIndex(tGreenValue, baseHists.get(1));
			int bBlue = getValueIndex(tBlueValue, baseHists.get(2));
			
			r = (int) map(bRed, 0, 99, 0, 255);
			b = (int) map(bBlue, 0, 99, 0, 255);
			g = (int) map(bGreen, 0, 99, 0, 255);
			
			System.out.println("Base colors and indexes: ("+r+","+g+","+b+")("+bRed+","+bGreen+","+bBlue+")");
			
			resImage.pixels[i] = color(r, g, b);
			
		}
		resImage.updatePixels();
		
		return resImage;
	}
	
	public int getValueIndex(int value, int[] histogram){
		for (int i = 0; i < histogram.length; i++) {
			if(histogram[i]==value)
				return i;
		}
		return -1;
	}
	public void drawHistogram(int[] histogram, int x, int y, int color){
		int histMax = max(histogram);
		 stroke(color);
		for (int i = 0; i < histogram.length; i ++) {
			  int bar = (int)map(histogram[i], 0, histMax, y+200, y);
			  line(i+x, y+200, i+x, bar);
			}
	}
	
}
