package swing.awtTest;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author booty
 * @date 2021/6/29 13:36
 */
public class TestCalculator  extends Frame{
    TextField num1;
    TextField num2;
    TextField num3;



    public TestCalculator() {
        super();
        num1=new TextField(5);
        num2=new TextField(5);
        num3=new TextField(5);
        Label label=new Label("+");
        Button button=new Button("=");

        button.addActionListener(new MyCalculatorListener(this));

        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.add(num1);
        this.add(label);
        this.add(num2);
        this.add(button);
        this.add(num3);
        this.pack();
        this.setVisible(true);
    }

    public static void main(String[] args) {
        TestCalculator testCalculator = new TestCalculator();

    }


}
class MyCalculatorListener implements ActionListener{
  private TestCalculator calculator;

    public MyCalculatorListener(TestCalculator testCalculator) {
       this.calculator=testCalculator;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int num1= Integer.parseInt(calculator.num1.getText());
        int num2= Integer.parseInt(calculator.num2.getText());
        calculator.num3.setText(num1+num2+"");

    }
}