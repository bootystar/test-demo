package swing.swingTest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author booty
 * @date 2021/6/29 15:40
 */
public class TestDialog {

    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
        jFrame.setBounds(200,200,400,400);

        //获取容器（需要使用容器才能设置颜色之类）
        Container contentPane = jFrame.getContentPane();
        //设置背景色
        contentPane.setBackground(Color.GRAY);
        //设置绝对布局(大小不可变)
        contentPane.setLayout(null);

        //按钮
        JButton jButton=new JButton("点击弹窗");
        jButton.setBounds(30,30,30,30);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog jDialog = new JDialog();
                jDialog.setBounds(100,100,200,100);
                Container dialogContentPane = jDialog.getContentPane();
                dialogContentPane.setLayout(null);
                jDialog.show();
            }
        });

        contentPane.add(jButton);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
