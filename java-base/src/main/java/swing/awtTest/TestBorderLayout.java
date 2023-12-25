package swing.awtTest;

import java.awt.*;

/**
 * @author booty
 * @date 2021/6/29 10:12
 */
public class TestBorderLayout {

    public static void main(String[] args) {
        layout1();
        layout2();
        layout3();
    }

    private static void layout1(){
        //主窗体
        Frame frame=new Frame("流式布局");
        //背景
        frame.setBackground(Color.BLACK);
        //可见
        frame.setVisible(true);
        //位置
        frame.setBounds(300,300,500,500);

        //设置为流式布局(可以指定左右中，默认中)
        frame.setLayout(new FlowLayout(FlowLayout.LEFT));


        //按钮
        Button button=new Button("button1");
        Button button2=new Button("button2");
        Button button3=new Button("button3");
        Button button4=new Button("button4");
        Button button5=new Button("button5");

        //添加按钮
        frame.add(button);
        frame.add(button2);
        frame.add(button3);
        frame.add(button4);
        frame.add(button5);

    }

    private static void layout2(){
        //主窗体
        Frame frame=new Frame("东西南北中");
        //背景
        frame.setBackground(Color.BLACK);
        //可见
        frame.setVisible(true);
        //位置
        frame.setBounds(300,300,500,500);


        //按钮
        Button button=new Button("center");
        Button button2=new Button("east");
        Button button3=new Button("west");
        Button button4=new Button("south");
        Button button5=new Button("north");


        //添加按钮(并指定方位)
        frame.add(button, BorderLayout.CENTER);
        frame.add(button2, BorderLayout.EAST);
        frame.add(button3, BorderLayout.WEST);
        frame.add(button4, BorderLayout.SOUTH);
        frame.add(button5, BorderLayout.NORTH);

    }


    private static void layout3(){
        //主窗体
        Frame frame=new Frame("表格布局");
        //背景
        frame.setBackground(Color.BLACK);
        //可见
        frame.setVisible(true);
        //位置
        frame.setBounds(300,300,500,500);
        //设置表格布局
        frame.setLayout(new GridLayout(3,2));

        //按钮
        Button button=new Button("button1");
        Button button2=new Button("button2");
        Button button3=new Button("button3");
        Button button4=new Button("button4");
        Button button5=new Button("button5");
        Button button6=new Button("button6");

        //添加按钮
        frame.add(button);
        frame.add(button2);
        frame.add(button3);
        frame.add(button4);
        frame.add(button5);
        frame.add(button6);

        //java函数，自动选择最优布局方式
        frame.pack();

    }

}
