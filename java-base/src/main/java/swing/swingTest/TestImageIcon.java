package swing.swingTest;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * @author booty
 * @date 2021/6/29 16:10
 */
public class TestImageIcon {
    public static void main(String[] args) {
        new MyImageIcon();
    }
}
class MyImageIcon extends JFrame {
    public MyImageIcon() throws HeadlessException {
        //获取当前目录下同级目录图片(编译之后的target下)
        URL resource = MyImageIcon.class.getResource("pic.png");

        ImageIcon imageIcon = new ImageIcon(resource);

        JLabel label=new JLabel("imageIcon");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setIcon(imageIcon);

        Container contentPane = getContentPane();
        contentPane.add(label);
        setVisible(true);
        pack();
    }
}