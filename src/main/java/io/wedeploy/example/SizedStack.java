package io.wedeploy.example;

import java.util.Stack;

public class SizedStack<T> extends Stack<T> {
    private int maxSize;

    public SizedStack(int size) {
        super();
        this.maxSize = size;
    }

    @Override
    public synchronized T peek() {
        int len = size();

        if (len == 0)
            return null;
        else
            return elementAt(len - 1);
    }


    @Override
    public T push(T object) {
        while (this.size() >= maxSize) {
            this.remove(0);
        }
        return super.push(object);
    }
}
