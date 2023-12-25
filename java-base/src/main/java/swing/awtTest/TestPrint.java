package swing.awtTest;

import java.awt.*;

/**
 * @author booty
 * @date 2021/6/29 14:07
 */
public class TestPrint {

    public static void main(String[] args) {
        new MyPrint().load();
    }
}
class MyPrint extends Frame{

    public void load()  {
        setBounds(200,200,500,500);
        setVisible(true);

    }


    @Override
    public void paint(Graphics g) {
        //设置颜色
        g.setColor(Color.BLACK);
        //圆
        g.drawOval(100,100,100,100);
        //矩形
        g.drawRect(200,100,100,100);
        //实心矩形
        g.fillRect(100,200,100,100);
    }
}
