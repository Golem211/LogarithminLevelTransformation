package work;
public class ImageProcessor extends Thread {
    private final SharedBuffer buffer; // resursa comuna
    private String outFile; // poate accepta si outfile pt a salva imaginea procesata
    { // bloc de initializare
    	outFile = null;
    }
    public ImageProcessor(SharedBuffer buffer, String file) {
        this.buffer = buffer;
        setOutFile(file);
    }

   
	public String getOutFile() {
		return outFile;
	}

	public void setOutFile(String outFile) {
		this.outFile = outFile;
	}

	@Override
    public void run() { // implementarea run pt consumator
    	int i = 0;
        while (true) { // ruleaza cat timp sunt imagini in buffer
        
            MyImage myImage = buffer.consume(); // stochez din buffer imaginea mea
            if (myImage == null) { // daca ce iau din buffer e nimic inchid threadul
                // No more items in the buffer, exit the thread
                break;
            }
            System.out.println("Consumatorul a citit imaginea: "+(i+1));
            // Perform image processing on myImage
            MyImage transformedImg = myImage.manipulateImage(); // se deschide metoda de procesare a imaginii
            if(outFile ==null){ // in cazul in care nu sunt specificate fisierul de iesire
	            transformedImg.saveToBMP("OutImg/image_processed"+i+".bmp");
	            i++;
            }
            else
            	transformedImg.saveToBMP(outFile);
        }
    }
}