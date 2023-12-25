package swing.swingTest;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * @author booty
 * @date 2021/6/30 09:07
 */
public class TestImageButton extends JFrame {
    public static void main(String[] args) {
        new TestImageButton();
    }


    public TestImageButton() throws HeadlessException {
        //将图片变为图标
        URL resource = this.getClass().getResource("pic.png");
        Icon icon=new ImageIcon(resource);

        //把图标放在图片上()
        JButton button=new JButton();
        button.setIcon(icon);
        button.setToolTipText("图片按钮");





        //添加
        Container contentPane = getContentPane();
        contentPane.add(button);
        setVisible(true);
        setSize(500,300);
    }
}
