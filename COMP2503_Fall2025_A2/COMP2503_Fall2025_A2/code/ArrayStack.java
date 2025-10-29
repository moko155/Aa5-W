//import java.util.EmptyStackException;

/**
 * ArrayStack is an array implementation of a stack data structure
 */

public class ArrayStack<T> implements ArrayStackADT<T> {
    
// generic array to hold stack elements
    private T[] stack;
    private int top;
    
    /**
     * Creat an empty stack with capacity of 25
     * set top to -1
     */
    public ArrayStack() {
        
        stack = (T[])(new Object[25]);
        top = -1;
    }
    
    /**
     * Creat an empty stack with initial capacity
     * @param initialCapacity the initial size of the array
     */
    public ArrayStack(int initialCapacity) {
        stack = (T[])(new Object[initialCapacity]);
        top = -1;
    }
    
    /**
     * Add dataItems to top of the stack
     * Increase array capacity if needed
     * @param dataItem the element
     */
    public void push(T dataItem) {
            System.out.println("debug msg: Pushing: " + dataItem + " | Stack size before: " + size());

        if (top == stack.length - 1) {
            int newSize;
            if (stack.length < 100) {
                newSize = stack.length * 3;
            } else {
                newSize = stack.length + 25;
            }
                System.out.println("debugging: array full from" + stack.length + " to " + newSize);

            T[] newStack = (T[])(new Object[newSize]);
            for (int i = 0; i < stack.length; i++) {
                newStack[i] = stack[i];
            }
            stack = newStack;
        }
        top++;
        stack[top] = dataItem;
    }
        //       - If length < 100: new size = length * 3
        //       - If length >= 100: new size = length + 25
        
    
    /**
     * if top is -1 throw exception otherwise remove and return top element
     * @return the top element
     * @throws EmptyStackException if stack is empty
     */
    public T pop() throws EmptyStackException {
        if (top == -1) {
            throw new EmptyStackException("empty stack");
        }
        T element = stack[top];
        top--;
        if (size() < stack.length / 4 && stack.length > 40) {
            int newSize = stack.length / 2;
                System.out.println("test: Shrinking from " + stack.length + " to " + newSize);

            T[] newStack = (T[])(new Object[newSize]);
            for (int i = 0; i <= top; i++) {
                newStack[i] = stack[i];
            }
            stack = newStack;
        }
        System.out.println("test" + element + " | New size: " + size());
        return element;
        // TODO: Get the element at stack[top]
        // TODO: Decrement top
        // TODO: Check if should shrink:
        // - If size() < length/4 AND length > 40: shrink to length/2
        // TODO: Return the element
    }
    
    /**
     * check if top is -1 throw exception otherwise return top element
     * @return the top element
     * @throws EmptyStackException if stack is empty
     */
    public T peek() throws EmptyStackException {
        if (top == -1) {
            throw new EmptyStackException("empty stack");
        }
        return stack[top];
    }
    
    /**
     * if top is -1 return true otherwise false
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        if (top == -1) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Return the number of elements
     * @return number of elements
     */
    public int size() {
        return top + 1;
    }
    
    /**
     * length of the array for when to grow the array
     * @return the length of the array
     */
    public int length() {
        return stack.length;
    }
    
    /**
     * its concatenating the string from each stored element
     *  from bottom to top, its iterating from index 0 to top index 
     * it appends/adds a comma and space between element 
     * @return string representation
     */
    public String toString() {
        String message = "Stack: ";
        for (int i = 0; i <= top; i++) {
            message += stack[i].toString();
            if (i < top) {
                message += ", ";
            }
        }
            return message; 
        }
        
    }
    
