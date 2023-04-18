package com.vervyle.oop.containers;

import java.util.Iterator;

public interface MyList<T> {

    boolean isEmpty();

    int size();

    /**
     * adds element to the end of a container
     */
    void add(T val);

    void add(T val, int where);

    /**
     * removes first entrance of an element in a container
     */
    void remove(T val);

    void remove(int where);

    void remove(MyList<T> toDelete);

    T getLast();

    T get(int index);

    Iterator<T> iterator();

    Iterator<T> descendingIterator();
}
