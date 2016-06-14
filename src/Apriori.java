import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: dreamer
 * Date: 13.05.13
 * Time: 15:51
 * To change this template use File | Settings | File Templates.
 */
public class Apriori {
    Transaction[] trans;
    String[] attrList;
    ArrayList<UsualMass> Ci;
    ArrayList<UsualMass> Fi;
    ArrayList<UsualMass> Ii;

    public Apriori(Transaction[] trans, String[] attrList) {
        this.trans = trans;
        this.attrList = attrList;
    }

    void start(double minSupp) {
        Ci = new ArrayList<UsualMass>();
        Fi = new ArrayList<UsualMass>();
        Ii = new ArrayList<UsualMass>();

        for(int j = 0; j < attrList.length; j++) {
            UsualMass usualMass = new UsualMass(1);
            System.out.println(attrList[j]);
            usualMass.mass[0] = attrList[j];
            if(!massContains(Ci, usualMass.mass)) Ci.add(usualMass);
        }
        int i = 1;
        while (Ci.size() != 0) {
            supportCount(Ci);
            Fi = new ArrayList<UsualMass>();
            for(int j = 0; j < Ci.size(); j++)
                if(Ci.get(j).support >= minSupp) Fi.add(Ci.get(j));
            Ii.addAll(Fi);
            i++;
            Ci = apprioriGen(Fi, i);
        }
    }

    ArrayList<UsualMass> apprioriGen(ArrayList<UsualMass> Fi, int size) {
        ArrayList<UsualMass> result = new ArrayList<UsualMass>();
        for(int i = 0; i < Fi.size(); i++) {
            for(int j = 0; j < attrList.length; j++) {
                if(Arrays.asList(Fi.get(i).mass).contains(attrList[j])) continue;
                UsualMass usualMass = new UsualMass(size);
                for(int z = 0; z < size - 1; z++) usualMass.mass[z] = Fi.get(i).mass[z];
                usualMass.mass[size - 1] = attrList[j];
                if(!massContains(result, usualMass.mass)) result.add(usualMass);
            }
        }
        return result;
    }

    void supportCount(ArrayList<UsualMass> Ci) {
        for(int i = 0; i < Ci.size(); i++) {
            int supp = 0;
            String[] mass = Ci.get(i).mass;
            String name = "";
            for(int j = 0; j < trans.length; j++) {
                boolean isOk = true;
                name = "<";
                for(int z = 0; z < mass.length; z++)
                {
                    name += mass[z] + ",";
                    if(!trans[j].names.contains(mass[z])) isOk = false;
                }
                name += ">";
                if(isOk) supp++;
            }

            double supprt = (double)supp / (double)trans.length;
            System.out.println(name + " supp = " + supprt);
            Ci.get(i).support = supprt;
        }
    }

    public String print() {
        String result = "";
        for(int i = 0; i < Ii.size(); i++) {
            UsualMass mass = Ii.get(i);
            result += "<";
            for(int j = 0; j < mass.mass.length; j++) result += mass.mass[j] + " ";
            result += ">\n";
        }
        return result;
    }


    boolean massContains(ArrayList<UsualMass> mass, String[] attrs) {
        if(mass.size() == 0) return false;
        boolean result = true;
        for(int i = 0; i < mass.size(); i++) {
            String[] rules = mass.get(i).mass;
            for(int j = 0; j < attrs.length; j++) {
                if(rules[j].equals(attrs[j]) == false) result = false;
            }
        }
        return result;
    }
}
