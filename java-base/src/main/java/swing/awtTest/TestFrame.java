package swing.awtTest;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author booty
 * @date 2021/6/29 09:20
 */
public class TestFrame {
    public static void main(String[] args) {
        //主窗体
        Frame frame=new Frame("awtTest");
        //背景
        frame.setBackground(Color.BLACK);
        //可见
        frame.setVisible(true);
        //位置
        frame.setBounds(300,300,500,500);
        //添加关闭监听事件
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.exit(0);
            }
        });
        frame.setLayout(null);

        //面板布局
        Panel panel=new Panel();
        panel.setBounds(50,50,400,400);
        panel.setBackground(Color.GRAY);

        //将面板添加到主窗体
        frame.add(panel);
    }

}
