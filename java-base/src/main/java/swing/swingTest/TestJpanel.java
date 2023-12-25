package swing.swingTest;

import javax.swing.*;
import java.awt.*;

/**
 * @author booty
 * @date 2021/6/29 16:25
 */
public class TestJpanel extends JFrame {

    public static void main(String[] args) {
        new TestJpanel();
    }

    public TestJpanel() throws HeadlessException {
        JPanel jPanel=new JPanel(new GridLayout(1,3));
        JPanel jPanel2=new JPanel(new GridLayout(1,2));
        JPanel jPanel3=new JPanel(new GridLayout(2,3));
        JPanel jPanel4=new JPanel(new GridLayout(3,2));

        jPanel.add(new Button("1"));
        jPanel.add(new Button("1"));
        jPanel.add(new Button("1"));

        jPanel2.add(new Button("2"));
        jPanel2.add(new Button("2"));

        jPanel3.add(new Button("3"));
        jPanel3.add(new Button("3"));

        jPanel4.add(new Button("4"));
        jPanel4.add(new Button("4"));
        jPanel4.add(new Button("4"));
        jPanel4.add(new Button("4"));
        jPanel4.add(new Button("4"));
        jPanel4.add(new Button("4"));




        Container contentPane = getContentPane();
        //最后两个参数是内外边距,设置后每个面板有固定间距
        contentPane.setLayout(new GridLayout(2,1,10,10));
        contentPane.add(jPanel);
        contentPane.add(jPanel2);
        contentPane.add(jPanel3);
        contentPane.add(jPanel4);
        pack();
        setVisible(true);
    }
}
