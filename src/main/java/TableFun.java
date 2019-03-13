import java.util.*;

import static java.lang.Double.MAX_VALUE;

public class TableFun {
    private Map<Double, Double> table = new HashMap<Double, Double>();

    public TableFun(){
    }

    public void clear() {
        table.clear();
    }

    public int size() {
        return table.size();
    }

    public double getY(double x) { //+
        if (table.containsKey(x))
            return table.get(x);
        else throw new IllegalArgumentException("This x is not in the table");
    }

    public void add(double x, double y) { //+
        if (!table.containsKey(x))
            table.put(x,y);
        else throw new IllegalArgumentException("This x already exists. Cannot be repeated");
    }

    public void remove (double x, double y) { //+
        if (table.containsKey(x)){
            if (Double.compare(table.get(x), y) == 0)
                table.remove(x);
            else throw new IllegalArgumentException("This x does not have such y");
        }
        else throw new IllegalArgumentException("No such pair");
    }

    public void showAll() { //+
        int i = 1;
        for (Map.Entry<Double, Double> item : table.entrySet()) {
            System.out.println("Pair " + i +": (" + item.getKey() + " ," + item.getValue() + ")");
            i++;
        }
    }

    public Pair findX(double x0) { //+
        double min = MAX_VALUE;
        double x = MAX_VALUE;
        double y = MAX_VALUE;
        Pair pair = new Pair();
        if (!table.isEmpty()){
            for (Map.Entry<Double, Double> item : table.entrySet()) {
                if (Math.abs(x0 - item.getKey()) < min) {
                    min = Math.abs(x0 - item.getKey());
                    x = item.getKey();
                    y = item.getValue();
                }
            }
            pair.setX(x);
            pair.setY(y);
        }
        else throw new IllegalArgumentException("Table is empty");
        return pair;
    }


    public double interpolate(double node) { //+

        double lag = 0;
        double polinom;
        int size = table.size();


        if (size != 0 && size != 1) {
            for (Map.Entry<Double, Double> itemFirst : table.entrySet()) {
                polinom = 1;
                for (Map.Entry<Double, Double> itemSecond : table.entrySet()) {
                    if (!itemFirst.equals(itemSecond)) {
                        polinom *= (node - itemSecond.getKey()) / (itemFirst.getKey() - itemSecond.getKey());
                    }
                }
                lag += polinom * itemFirst.getValue();
            }
        }
        else throw new IllegalArgumentException("Table size is very small");
        return lag;
    }
}