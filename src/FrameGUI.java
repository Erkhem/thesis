import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;


public class FrameGUI {

    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Number of late job calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel frameLabel = new JLabel("every processing time is equal to 1");
        frameLabel.setPreferredSize(new Dimension(175, 100));
        frame.getContentPane().add(frameLabel, BorderLayout.CENTER);
        
        JTable tableOfWeights = new JTable();

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}
