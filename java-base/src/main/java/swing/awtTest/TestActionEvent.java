package swing.awtTest;

import java.awt.*;

/**
 * @author booty
 * @date 2021/6/29 10:54
 */
public class TestActionEvent {
    public static void main(String[] args) {
        //主窗体
        Frame frame=new Frame();
        //按钮
        Button button=new Button("button");
        //添加按钮点击事件（一个按钮可以添加多个事件，一个事件也可以多个按钮共用）
        button.addActionListener(e-> System.out.println("按钮触发"));
        //添加按钮
        frame.add(button);
        //自动布局
        frame.pack();
        //显示窗体
        frame.setVisible(true);
    }
}
