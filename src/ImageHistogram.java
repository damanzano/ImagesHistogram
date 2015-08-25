import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;


public class ImageHistogram extends PApplet{
	PImage image;
	PImage image2;
	int[] redHistogram = new int[100];
	int[] blueHistogram = new int[100];
	int[] greenHistogram = new int[100];
	
	public void setup(){
		image=loadImage("../data/yoquese.jpg");
		image2 = loadImage("../data/yoquese2.jpg");
		size(image.width,image.height);
		
		
	}
	
	public void draw(){
		
		image(image,0,0);
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
	}
}
