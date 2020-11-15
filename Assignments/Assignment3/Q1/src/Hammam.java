import java.util.ArrayList;
import java.util.Collections;

public class Hammam<T extends Comparable> {
    private ArrayList<T> elementsInInputOrder = new ArrayList<>();
    private ArrayList<T> elementsInComparingOrder = new ArrayList<>();
    private ArrayList<T> elementsRemoved = new ArrayList<>();

    public ArrayList<T> getElementsInInputOrder() {
        return elementsInInputOrder;
    }

    public ArrayList<T> getElementsInComparingOrder() {
        return elementsInComparingOrder;
    }

    public ArrayList<T> getElementsRemoved() {
        return elementsRemoved;
    }

    public void add (T item){
        elementsInInputOrder.add(item);
        elementsInComparingOrder.add(item);
    }

    public T getMin () throws IllegalStateException{
        Collections.sort(elementsInComparingOrder);
        if(elementsInComparingOrder.size() <= 0)
            throw new IllegalStateException("Empty!");
        elementsInInputOrder.remove(elementsInComparingOrder.get(0));
        elementsRemoved.add(elementsInComparingOrder.get(0));
        return elementsInComparingOrder.remove(0);
    }

    public T getLast (boolean remove) throws IllegalStateException{
        if(elementsInInputOrder.size() <= 0)
            throw new IllegalStateException("Empty!");
        if(remove){
            elementsInComparingOrder.remove(elementsInInputOrder.get(elementsInInputOrder.size() - 1));
            elementsRemoved.add(elementsInInputOrder.get(elementsInInputOrder.size() - 1));
            return elementsInInputOrder.remove(elementsInInputOrder.size() - 1);
        }else {
            return elementsInInputOrder.get(elementsInInputOrder.size() - 1);
        }
    }

    public T getFirst (boolean remove) throws IllegalStateException{
        if(elementsInInputOrder.size() <= 0)
            throw new IllegalStateException("Empty!");
        if(remove){
            elementsInComparingOrder.remove(elementsInInputOrder.get(0));
            elementsRemoved.add(elementsInInputOrder.get(0));
            return elementsInInputOrder.remove(0);
        }else {
            return elementsInInputOrder.get(0);
        }
    }

    public Comparable[] getLess (T element, boolean remove){
        Comparable[] output;
        if(!elementsInComparingOrder.contains(element))
            return null;
        Collections.sort(elementsInComparingOrder);
        output = new Comparable[elementsInComparingOrder.indexOf(element)];
        for(int i = 0 ; i < output.length ; i++){
            output[i] = elementsInComparingOrder.get(i);
        }
        if(remove){
            for(int i = 0 ; i < output.length ; i++){
                elementsRemoved.add((T) output[i]);
                elementsInInputOrder.remove(output[i]);
                elementsInComparingOrder.remove(output[i]);
            }
        }
        return output;
    }

    public Comparable[] getRecentlyRemoved (int n){
        Comparable[] output;
        if(n > elementsRemoved.size())
            n = elementsRemoved.size();
        output = new Comparable[n];
        int k = 0;
        for(int i = elementsRemoved.size() - 1 ; i > elementsRemoved.size() - 1 - n ; i--){
            output[k] = elementsRemoved.get(i);
            k++;
        }
        return output;
    }
}
