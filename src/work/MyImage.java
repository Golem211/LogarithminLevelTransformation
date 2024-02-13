package work;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

//threaduri in transformarea si citirea si scrierea informatiei

public class MyImage extends ImageModel implements BMPWritable { // extinde clasa abstracta si implementeaza metoda de scriere
	
	private int par; // parametrul ce reprezinta constanta de procesare 
    public MyImage(int width, int height) {
        super(width, height);
        par = 250;
    }

    
    public int getPar() {
		return par;
	}


	public void setPar(int par) {
		this.par = par;
	}


	public MyImage manipulateImage() {
        // Creez o imagine noua in pe care vreau sa o procesez
        MyImage transformedImage = new MyImage(this.getWidth(), this.getHeight());
        long startProcesare = System.currentTimeMillis(); // 
        // Implement log-level transform
        for (int x = 0; x < this.getWidth(); x++) {
            for (int y = 0; y < this.getHeight(); y++) {
                int red = getRed(x, y);
                int green = getGreen(x, y);
                int blue = getBlue(x, y);
                
                int c = this.getPar();
            
                red = (int) (c* Math.log(1 + red) ); // aplic functia pe fiecare canal 
                green = (int) (c* Math.log(1 + green) );
                blue = (int) (c* Math.log(1 + blue) );
                
                transformedImage.setPixel(x, y, red, green, blue); // modific pixelul(x,y) din imagine
             // Applying log-level transform to each channel
                /*red = (int) (c* Math.log(1 + red) / Math.log(256) * 255);
                green = (int) (c* Math.log(1 + green) / Math.log(256) * 255);
                blue = (int) (c* Math.log(1 + blue) / Math.log(256) * 255);
                */
                
                // as putea mai intai sa fac imaginea alb negru si sa stochez imaginea doar pe un canal.
                //System.out.printf("value of pix : %d %d %d\n", red, green, blue);

                // Set the transformed values to the new image
            }
        }
        long finalProcesare = System.currentTimeMillis();  
        long elapsedTimeProcessing = finalProcesare - startProcesare;
        System.out.println("Elapsed time for Processing onea Images: " + elapsedTimeProcessing + " milliseconds");
        return transformedImage; 
    }



	@Override
	public void saveToBMP(String filePath) { // implementez metoda pt a salva imaginea
		 int width = getWidth();
        int height = getHeight();

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); //  fac un buffer pentru ca stie sa scrie in fisier cu bufferul
        long startScriere = System.currentTimeMillis();
        for (int x = 0; x < width; x++) { // completez bufferul
            for (int y = 0; y < height; y++) {
                int red = getRed(x, y);
                int green = getGreen(x, y);
                int blue = getBlue(x, y);

                int rgb = (red << 16) | (green << 8) | blue;
                bufferedImage.setRGB(x, y, rgb);
            }
        }
       
        try {
            ImageIO.write(bufferedImage, "bmp", new File(filePath)); // scriu in fisier imaginea din buffer
            System.out.println("BMP file saved successfully.");
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        long finalScriere = System.currentTimeMillis();
        long elapsedTimeProcessing = finalScriere - startScriere;
        System.out.println("Elapsed time for Saving one Images: " + elapsedTimeProcessing + " milliseconds");
        System.out.println(" ");
    
		
	}

	
	@Override
	public void abstractMethod() { // metoda abstracta
		System.out.println("Aceasta este implementarea metodei abstracte");
		
	}
}
