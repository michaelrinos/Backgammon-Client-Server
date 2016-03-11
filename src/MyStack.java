import java.util.ArrayList;

/**
 * Created by michael on 27/2/2016.
 */
public class MyStack<E> {
    public ArrayList<E> array;
    private int count;

    public MyStack(){
        count = -1;
        this.array = new ArrayList<>();
    }

    public MyStack(MyStack<E> o){
        count = -1;
        this.array = new ArrayList<>();
        for (int i = 0; i <o.array.size() ; i++) {
            this.array.add(++count, o.array.get(i));
        }
    }

    public E pop(){
        if (count < 0)
            throw new IndexOutOfBoundsException();
        else
            return array.remove(count--);
    }

    public void push(E element){
        array.add(++count, element);
    }

    public int size(){
        return this.count;
    }

    public boolean isEmpty(){
        return count  == -1;
    }

    /*public MyStack<E> getCopy(){
        E[] copy = ((E[]) new Object[Max_Amount]);
        for (int i = 0; i < count-1; i++) {
            copy[0] = array[i];
        }
        return new MyStack<E>(copy);
    }*/


    public static void main(String[] args){
        MyStack<Integer> stack = new MyStack<Integer>();
        stack.push(1);
        System.out.println(stack.isEmpty());
        System.out.println(stack.pop());
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        System.out.println("Size: " +stack.size());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
    }
}
