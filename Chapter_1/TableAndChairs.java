public class TableAndChairs {
    public static void main(String[] args) {

        // First 2 rows: "X                      X"
        for (int i = 0; i < 2; i++) {
            System.out.println("X                      X");
        }

        // Third row: "X      XXXXXXXXXX      X"
        System.out.println("X      XXXXXXXXXX      X");

        // Fourth row: "XXXXX  X        X  XXXXX"
        System.out.println("XXXXX  X        X  XXXXX");

        // Last 2 rows: "X   X  X        X  X   X"
        for (int i = 0; i < 2; i++) {
            System.out.println("X   X  X        X  X   X");
        }
    }
}
