package swing.swingTest;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * @author booty
 * @date 2021/6/30 09:29
 */
public class TestCheckBox extends JFrame{

    public static void main(String[] args) {

    }

    public TestCheckBox() {

        //多选框
        JCheckBox box1=new JCheckBox("box1");
        JCheckBox box2=new JCheckBox("box2");
        JCheckBox box3=new JCheckBox("box3");



        //添加
        Container contentPane = getContentPane();
        contentPane.add(box1,BorderLayout.NORTH);
        contentPane.add(box2,BorderLayout.CENTER);
        contentPane.add(box3,BorderLayout.WEST);
        setVisible(true);
        setSize(500,300);
    }
}
