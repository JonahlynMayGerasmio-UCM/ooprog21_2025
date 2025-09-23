 class DebugCircle {
    private int radius;
    private int diameter;
    private final double PI = 3.14159;
    private double area;
    
    
     public static void main(String[] args) {
        
        DebugCircle myCircle = new DebugCircle(10);
        
        
        System.out.println("Radius: " + myCircle.getRadius());
        System.out.println("Diameter: " + myCircle.getDiameter());
        System.out.println("Area: " + myCircle.getArea());
    }


    public DebugCircle(int r) {
        radius = r;
        diameter = 2 * r;
        area = PI * r * r;
    }

    public int getRadius() {
        return radius; 
    }

    public int getDiameter() {
        return diameter;  
    }

    public double getArea() {
        return area;
    }

   
   }
