package work;

// Clasa abstracta din care se extinde clasa MyImage, are o metoda abstracta

public abstract class ImageModel {
    private int width; // latimea imaginii
    private int height; // inaltimea imaginii
    private int[][] redChannel; // matricea ce stocheaza valoarea de rosu al unei imagini
    private int[][] greenChannel;// matricea ce stocheaza valoarea de verde al unei imagini
    private int[][] blueChannel;// matricea ce stocheaza valoarea de albastru al unei imagini

    public ImageModel(int width, int height) { //constructor
        this.width = width;
        this.height = height;
        this.redChannel = new int[width][height];
        this.greenChannel = new int[width][height];
        this.blueChannel = new int[width][height];
    }
    public abstract void abstractMethod();
    
    
    // seter si geters.
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    
    public int getRed(int x, int y) {
        return redChannel[x][y];
    }

    public int getGreen(int x, int y) {
        return greenChannel[x][y];
    }

    public int getBlue(int x, int y) {
        return blueChannel[x][y];
    }

    //metoda pt a seta valoarea unui pixel.
    public void setPixel(int x, int y, int red, int green, int blue) {
        redChannel[x][y] = red;
        greenChannel[x][y] = green;
        blueChannel[x][y] = blue;
    }

   

    
}
