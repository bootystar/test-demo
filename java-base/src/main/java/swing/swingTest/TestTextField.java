package swing.swingTest;

import javax.swing.*;
import java.awt.*;

/**
 * @author booty
 * @date 2021/6/30 09:43
 */
public class TestTextField extends JFrame {

    public static void main(String[] args) {
        new TestTextField();
    }

    public TestTextField() throws HeadlessException {


        //文本框
        JTextField textField=new JTextField("hello");
        JTextField textField2=new JTextField("world");

        //密码框
        JPasswordField passwordField=new JPasswordField();

        //文本域见TestScroll，一般配合滚动面板使用



        //添加
        Container contentPane = getContentPane();
        setVisible(true);
        setSize(500,300);
        contentPane.setLayout(new FlowLayout(FlowLayout.LEFT));
        contentPane.add(textField);
        contentPane.add(textField2);
        contentPane.add(passwordField);

    }
}
