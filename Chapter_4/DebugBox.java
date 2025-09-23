public class DebugBox {
    private int width;
    private int length;
    private int height;

   public static void main(String[] args) {
        
        DebugBox box1 = new DebugBox();
        System.out.println("Box: 1");
        box1.showData();
        System.out.println("Volume: " + box1.getVolume());
        
       
        DebugBox box2 = new DebugBox(2, 3, 4);
        System.out.println("\nBox: 2");
        box2.showData();
        System.out.println("Volume: " + box2.getVolume());
    }
    
    public DebugBox() {
        length = 1;
        width = 1;
        height = 1;
    }

   
    public DebugBox(int width, int length, int height) {
        this.width = width;
        this.length = length;  
        this.height = height;  
    }

    
    public void showData() {
        System.out.println("Width: " + width + "\tLength: " + length + "\tHeight: " + height);
    }

    
    public double getVolume() {
        double vol = length * width * height;
        return vol;
    }

    
 
}
