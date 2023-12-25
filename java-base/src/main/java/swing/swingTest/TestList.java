package swing.swingTest;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * @author booty
 * @date 2021/6/30 09:37
 */
public class TestList extends JFrame {
    public static void main(String[] args) {
    new TestList();

    }


    public TestList() throws HeadlessException {


       String [] infos={"aaa","bbb","ccc"};
       JList list=new JList(infos);



        //添加
        Container contentPane = getContentPane();
        contentPane.add(list);
        setVisible(true);
        setSize(500,300);
    }
}
