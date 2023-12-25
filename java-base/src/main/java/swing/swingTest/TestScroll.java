package swing.swingTest;

import javax.swing.*;
import java.awt.*;

/**
 * @author booty
 * @date 2021/6/29 16:47
 */
public class TestScroll extends JFrame {
    public static void main(String[] args) {
        new TestScroll();
    }


    public TestScroll() throws HeadlessException {
        JTextArea textArea=new JTextArea(20,50);
        textArea.setText("wwwwwwwwwwwwwwwww");

        //可滚动的面板
        JScrollPane scrollPane=new JScrollPane(textArea);


        Container contentPane = getContentPane();
        contentPane.add(scrollPane);
        setBounds(200,200,400,400);
        setVisible(true);
    }
}
