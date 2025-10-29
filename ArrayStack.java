import java.util.EmptyStackException;

public class ArrayStack<T> implements ArrayStackADT<T> {
    

    private T[] stack;
    private int top;
    
    // ============================================
    // CONSTRUCTORS
    // ============================================
    
    /**
     * Creates an empty stack with default capacity of 25
     */
    public ArrayStack() {
        // TODO: Initialize stack array with capacity 25
        stack = (T[])(new Object[25]);
        // TODO: Set top to -1
        top = -1;
    }
    
    /**
     * Creates an empty stack with specified initial capacity
     * @param initialCapacity the initial size of the array
     */
    public ArrayStack(int initialCapacity) {
        // TODO: Initialize stack array with given capacity
        stack = (T[])(new Object[initialCapacity]);
        // TODO: Set top to -1
        top = -1;
    }
    
    // ============================================
    // METHODS
    // ============================================
    
    /**
     * Adds dataItem to the top of the stack
     * Increases array capacity if needed
     * @param dataItem the element to push
     */
    public void push(T dataItem) {
        // TODO: Check if array is full (top == stack.length - 1)
        if (top == stack.length - 1) {
            int newSize;
            if (stack.length < 100) {
                newSize = stack.length * 3;
            } else {
                newSize = stack.length + 25;
            }
            T[] newStack = (T[])(new Object[newSize]);
            for (int i = 0; i < stack.length; i++) {
                newStack[i] = stack[i];
            }
            stack = newStack;
        }
        top++;
        stack[top] = dataItem;
        // TODO: If full, expand the array:
        //       - If length < 100: new size = length * 3
        //       - If length >= 100: new size = length + 25
        // TODO: Increment top
        // TODO: Add dataItem at stack[top]
    }
    
    /**
     * Removes and returns the top element
     * Shrinks array if needed
     * @return the top element
     * @throws EmptyStackException if stack is empty
     */
    public T pop() throws EmptyStackException {
        // TODO: Check if empty (top == -1), throw exception if true
        if (top == -1) {
            throw new EmptyStackException("empty stack");
        }
        T element = stack[top];
        top--;
        if (size() < stack.length / 4 && stack.length > 40) {
            int newSize = stack.length / 2;
            T[] newStack = (T[])(new Object[newSize]);
            for (int i = 0; i <= top; i++) {
                newStack[i] = stack[i];
            }
            stack = newStack;
        }
        return element;
        // TODO: Get the element at stack[top]
        // TODO: Decrement top
        // TODO: Check if should shrink:
        //       - If size() < length/4 AND length > 40: shrink to length/2
        // TODO: Return the element
    }
    
    /**
     * Returns the top element without removing it
     * @return the top element
     * @throws EmptyStackException if stack is empty
     */
    public T peek() throws EmptyStackException {
        // TODO: Check if empty, throw exception if true
        if (top == -1) {
            throw new EmptyStackException("empty stack");
        }
        return stack[top];
        // TODO: Return stack[top]
    }
    
    /**
     * Checks if the stack is empty
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        // TODO: Return true if top == -1
        if (top == -1) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Returns the number of elements in the stack
     * @return number of elements
     */
    public int size() {
        // TODO: Return top + 1
        return top + 1;
    }
    
    /**
     * Returns the capacity of the array
     * @return the length of the array
     */
    public int length() {
        // TODO: Return stack.length
        return stack.length;
    }
    
    /**
     * Returns a string representation of the stack
     * Format: "Stack: elem1, elem2, elem3"
     * @return string representation
     */
    public String toString() {
        // TODO: Build a string with format "Stack: "
        // TODO: Loop from i=0 to i=top
        // TODO: Add stack[i].toString() with commas between elements
        // TODO: Return the string
        String message = "Stack: ";
        for (int i = 0; i <= top; i++) {
            message += stack[i].toString();
            if (i < top) {
                message += ", ";
            }
        }
        return message; 
    }
    
