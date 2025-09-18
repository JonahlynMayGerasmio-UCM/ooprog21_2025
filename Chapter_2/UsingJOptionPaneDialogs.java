import javax.swing.JOptionPane;

public class UsingJOptionPaneDialogs {
    public static void main(String[] args) {
        while (true) {
            
            String name = JOptionPane.showInputDialog("Please enter your name:");

            
            if (name == null) {
                break;
            }

            
            int confirmation = JOptionPane.showConfirmDialog(
                null, 
                "Do you want to display your name: " + name + "?", 
                "Confirm", 
                JOptionPane.YES_NO_CANCEL_OPTION
            );

            
            if (confirmation == JOptionPane.YES_OPTION) {
                
                JOptionPane.showMessageDialog(null, "Your name is: " + name);
                break; 
            } 
            else if (confirmation == JOptionPane.NO_OPTION) {
                
                continue;
            }
            else if (confirmation == JOptionPane.CANCEL_OPTION) {
               
                break;
            }
        }
    }
}
