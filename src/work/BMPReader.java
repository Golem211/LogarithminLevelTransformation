package work;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BMPReader extends Thread {
    private final SharedBuffer buffer; //resursa comuna
    private final String filePath; // locatia imaginii de intrare
    static int counter; // counter pentru a vedea cate imagini procesez
    static{//bloc static de initializare
    	counter = 0;
    }

    public BMPReader(SharedBuffer buffer, String filePath) { //constructor cu parametrii
        this.buffer = buffer;
        this.filePath = filePath;
    }

    @Override
    public void run() { // implementez metoda run a threadului este thread de Producere
    	
    	try {
			sleep(1000); 
			MyImage myImage = BMPReader.readBMP(filePath); // creez o instanta a clasei MyImage in care stochez o imagine
	        buffer.produce(myImage); // ilustrez modul in care se produce o imagine 
	        System.out.println("producatorul a produs imaginea: " + (counter));
		} catch (InterruptedException e) { // tratez exceptia in cazul in care nu se poate pune threadul la sleep
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        
    }

    public static MyImage readBMP(String filePath) { // stochez in obiect MyImage imaginea propiu zisa.bmp
        try {
            // Read the BMP file using javax.imageio
            BufferedImage bufferedImage = ImageIO.read(new File(filePath)); // folosesc ImgageIO pt a citi fisiere de tip image

            // Get width and height of the image
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();
            long startCitire= System.currentTimeMillis(); // incep masurarea timpului pentru citirea fisierului si stocarea sa
            // Create a custom Image object
            MyImage myImage = new MyImage(width, height);

            // Populez myImage cu date pixel 
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int rgb = bufferedImage.getRGB(x, y); 
                    int red = (rgb >> 16) & 0xFF;
                    int green = (rgb >> 8) & 0xFF;
                    int blue = rgb & 0xFF;

                    myImage.setPixel(x, y, red, green, blue); // introduc cate un bit de informatie pe fiecare canal al imaginii mele
                }
            }
            long finalCitire = System.currentTimeMillis();
            long elapsedTimeProcessing = finalCitire - startCitire;
            System.out.println("Elapsed time for Reading Image" + (++counter)+  ": "+ elapsedTimeProcessing + " milliseconds");
            return myImage;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}



