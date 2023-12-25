package swing.awtTest;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author booty
 * @date 2021/6/29 14:24
 */
public class TestMouse {
    public static void main(String[] args) {
        new MyMouse();
    }

}
class MyMouse extends Frame{
    ArrayList<Point> list=new ArrayList<>();

    public MyMouse() throws HeadlessException {
        setBounds(200,200,400,400);
        setVisible(true);
        addMouseListener(new MyMouseListener());
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Iterator<Point> iterator = list.iterator();
        while (iterator.hasNext()){
            Point next = iterator.next();
            g.fillOval((int)next.getX(),(int)next.getY(),5,5);
        }

    }

    private void addPoint(Point point) {
        list.add(point);
        repaint();
    }

    private class MyMouseListener extends MouseAdapter{
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            MyMouse frame = (MyMouse) e.getSource();
            frame.addPoint(new Point(e.getX(),e.getY()));
        }
    }


}
