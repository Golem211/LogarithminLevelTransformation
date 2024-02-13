package work;

import java.util.ArrayList;
import java.util.List;

public class SharedBuffer { // am creat un buffer in care sa stochez imaginile de procesat
    private final List<MyImage> buffer; // lista de imagini
    private final int maxSize;//dimensiunea maxima a listei

    public SharedBuffer(int maxSize) { //constructor
        this.buffer = new ArrayList<>();
        this.maxSize = maxSize;
    }
    public int getSize(){
    	int size = buffer.size(); 
    	return size;
    }

    public synchronized void produce(MyImage myImage) { // un exemplu  de producator
        while (buffer.size() >= maxSize) {  // cat timp buffer-ul este full asteapta
            try {
                wait(1000);
                // Wait if the buffer is full
            } catch (InterruptedException e) { // daca threadul nu 
                Thread.currentThread().interrupt();
            }
        }
        // cand nu este full adauga imaginea 
        buffer.add(myImage);
        notifyAll();  // Notify consumer thread(s) that a new item is available
    }

    public synchronized MyImage consume() { // sterge imaginea  
        while (buffer.isEmpty()) { // cat timp bufferul este gol asteapta sa mai apara o imagine in buffer
            try {
                wait(1000);  // Wait if the buffer is empty
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        // cand nu e gol returnez imaginea ce trebuie procesata.
        
        MyImage myImage = buffer.remove(0);
        notifyAll();  // Notify producer thread(s) that space is available
        return myImage;
    }
}
