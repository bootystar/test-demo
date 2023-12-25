package swing.swingTest;

import javax.swing.*;
import java.awt.*;

/**
 * @author booty
 * @date 2021/6/29 15:24
 */
public class TestJFrame {
    public static void main(String[] args) {
        //swing的组件基本与jwt一致，不过具有更多的功能
        JFrame jFrame = new JFrame();
        jFrame.setBounds(200,200,400,400);

        //获取容器（需要使用容器才能设置颜色之类）
        Container contentPane = jFrame.getContentPane();
        //设置背景色
        contentPane.setBackground(Color.GRAY);

        //标签
        JLabel jLabel=new JLabel("aaaaaaaaaa");
        //设置文字居中
        jLabel.setHorizontalAlignment(SwingConstants.CENTER);

        contentPane.add(jLabel);

        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
