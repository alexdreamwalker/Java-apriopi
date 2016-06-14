import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: dreamer
 * Date: 14.05.13
 * Time: 14:00
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Main");
        frame.setContentPane(new Main().mainpanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private JPanel mainpanel;
    private JTextArea textArea1;
    private JButton goButton;
    private JTextArea textArea2;

    public Main() {
        goButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String input = textArea1.getText();
                String[] inputs = input.split("\n");
                int trCount = inputs.length;
                Transaction[] transactions = new Transaction[trCount];
                ArrayList<String> uniqStr = new ArrayList<String>();
                int strCount = 0;

                for(int i = 0; i < trCount; i++) {
                    String[] trString = inputs[i].split("\t");
                    int number = Integer.parseInt(trString[0]);
                    ArrayList<String> values = new ArrayList<String>();
                    String[] valStrs = trString[1].split(",");
                    for(int j = 0; j < valStrs.length; j++) {
                        values.add(valStrs[j]);
                        if(!uniqStr.contains(valStrs[j])) {
                            uniqStr.add(valStrs[j]);
                            strCount++;
                        }
                    }
                    Transaction transaction = new Transaction(number, values);
                    transactions[i] = transaction;
                }

                String[] attrs = new String[uniqStr.size()];
                for(int i = 0; i < attrs.length; i++) attrs[i] = uniqStr.get(i);

                Apriori apriori = new Apriori(transactions, attrs);
                apriori.start(0.5);
                textArea2.setText(apriori.print());
            }
        });
    }
}
