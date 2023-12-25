package swing.awtTest;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author booty
 * @date 2021/6/29 14:55
 */
public class TestKeyBoard {
    public static void main(String[] args) {
        new MyKeyBoard();
    }
}
class MyKeyBoard extends Frame{
    public MyKeyBoard() throws HeadlessException {
        setBounds(200,200,400,400);
        setVisible(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode==KeyEvent.VK_UP){
                    System.out.println("按下了上键");
                }
                super.keyPressed(e);
            }
        });
    }
}
