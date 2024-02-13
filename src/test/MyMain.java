package test;
import work.*;

public class MyMain {
    public static void main(String[] args) {
        
    	if(args.length == 0){ // rulez aplicatia fara inputimag si outLocation din linia de comanda
	    	SharedBuffer buffer = new SharedBuffer(1); // creez un buffer in care threadul reader poate pune imagini si in care alt thread poate procesa imaginile de acolo
	
	        // Creez BMPreader threads
	        BMPReader readerThread1 = new BMPReader(buffer, "inImages/Lenabmp.bmp");
	        BMPReader readerThread2 = new BMPReader(buffer, "inImages/image2.bmp");
	        BMPReader readerThread3 = new BMPReader(buffer, "inImages/image3.bmp");
	        BMPReader readerThread4 = new BMPReader(buffer, "inImages/image4.bmp");
	        BMPReader readerThread5 = new BMPReader(buffer, "inImages/image5.bmp");
	        ///sasada
	        //creez thredul de procesare
	        ImageProcessor processorThread = new ImageProcessor(buffer,null);
	        
	        // pornesc toate threadurile
	        readerThread1.start();
	        readerThread2.start();
	        readerThread3.start();
	        readerThread4.start();
	        readerThread5.start();
	        processorThread.start();
	
	        // Astept ca thredurile de citire sa se termine si sa se uneasca cu threadul main
	        try {
	        	
	            readerThread1.join();
		        readerThread2.join();
		        readerThread3.join();
		        readerThread4.join(); 
	            readerThread5.join();
	            
	            
	        } catch (InterruptedException e) { // exceptie cand un thread nu se termina
	            Thread.currentThread().interrupt();
	        }
	
	        // in buffer pun valoarea null deoarece sa se stie ca nu mai este nicio imagine de produs
	        buffer.produce(null);
	
	        // Astept si threadul de procesare sa se termine
	        try {
	            processorThread.join();
	        } catch (InterruptedException e) {
	            Thread.currentThread().interrupt();
	        }
    	}
    	else if(args.length == 2){ // in cazul in care am argumente de la tastatura
    		SharedBuffer buffer = new SharedBuffer(3); // buffer pentru resursele comune
    		
    		BMPReader readerThread1 = new BMPReader(buffer, args[0]); // thread cu constructor in care folosesc primul argument 
    																 //de la linia de comanda ca imagine ce trebuie procesata
   
    		ImageProcessor processorThread = new ImageProcessor(buffer,args[1]); // in args[1] pun imaginea procesata
    		readerThread1.start();
    		processorThread.start();
    		
    		try {
				readerThread1.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		buffer.produce(null);
    		try {
	            processorThread.join();
	        } catch (InterruptedException e) {
	            Thread.currentThread().interrupt();
	        }
    		
    	}
    }
}