import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: dreamer
 * Date: 13.05.13
 * Time: 16:22
 * To change this template use File | Settings | File Templates.
 */
public class Transaction {
    int number;
    ArrayList<String> names;

    public Transaction(int number, ArrayList<String> names) {
        this.number = number;
        this.names = names;
    }
}
