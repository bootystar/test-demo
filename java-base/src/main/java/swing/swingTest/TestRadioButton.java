package swing.swingTest;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * @author booty
 * @date 2021/6/30 09:18
 */
public class TestRadioButton  extends JFrame {

    public static void main(String[] args) {
        new TestRadioButton();
    }


    public TestRadioButton() throws HeadlessException {

        //单选框
        JRadioButton button=new JRadioButton("button1");
        JRadioButton button2=new JRadioButton("button2");
        JRadioButton button3=new JRadioButton("button3");

        //分成一个组，使选择框只能单选
        ButtonGroup group=new ButtonGroup();
        group.add(button);
        group.add(button2);
        group.add(button3);


        //添加
        Container contentPane = getContentPane();
        contentPane.add(button,BorderLayout.NORTH);
        contentPane.add(button2,BorderLayout.CENTER);
        contentPane.add(button3,BorderLayout.WEST);
        setVisible(true);
        setSize(500,300);
    }
}
