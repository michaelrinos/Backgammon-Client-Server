import java.util.Iterator;

/**
 * Created by michael on 27/2/2016.
 */
public class MyStack<E> {
    private E[] array;
    private int count;
    private static final int Max_Amount = 15;

    public MyStack(){
        count = 0;
        this.array = (E[]) new Object[Max_Amount];
    }

    public E pop(){
        if (count < 1)
            throw new IndexOutOfBoundsException();
        else
            return array[count--];
    }

    public void push(E element){
        array[++count] = element;
    }

    public int size(){
        return this.count;
    }

    public boolean isEmpty(){
        return count  == 0;
    }


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
        System.out.println(stack.size());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());

    }
}
