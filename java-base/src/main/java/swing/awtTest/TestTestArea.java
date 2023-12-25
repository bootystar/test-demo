package swing.awtTest;

import java.awt.*;

/**
 * @author booty
 * @date 2021/6/29 11:05
 */
public class TestTestArea {
    public static void main(String[] args) {
        Frame frame=new Frame();
        TextField textField = new TextField();
        //监听文本框
        textField.addActionListener(e-> {
            TextField field=(TextField)e.getSource();
            System.out.println(field.getText());
        });

        //使用*来替代显示输入的字符（后台获取的还是输入的字）
        textField.setEchoChar('*');

        frame.add(textField);
        frame.pack();
        frame.setVisible(true);
    }
}
