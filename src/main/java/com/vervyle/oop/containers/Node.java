package com.vervyle.oop.containers;

public class Node<E> {
    E value;
    Node<E> next;
    Node<E> prev;

    Node(Node<E> prev, E element, Node<E> next) {
        this.value = element;
        this.next = next;
        this.prev = prev;
    }
}