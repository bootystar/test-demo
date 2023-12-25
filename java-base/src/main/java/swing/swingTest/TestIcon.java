package swing.swingTest;

import javax.swing.*;
import java.awt.*;

/**
 * @author booty
 * @date 2021/6/29 15:54
 */
public class TestIcon {
    public static void main(String[] args) {
        MyIcon myIcon = new MyIcon();

    }
}

class MyIcon extends JFrame implements Icon{
    private int width;
    private int height;

    public MyIcon() throws HeadlessException {
        this.width = 15;
        this.height = 15;
        JLabel label=new JLabel("MyIcon",this,SwingConstants.CENTER);
        Container contentPane = getContentPane();
        contentPane.add(label);
        pack();
        setVisible(true);
    }





    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        g.fillOval(x-15,y-10,width,height);
    }

    @Override
    public int getIconWidth() {
        return 0;
    }

    @Override
    public int getIconHeight() {
        return 0;
    }
}
