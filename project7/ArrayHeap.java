package project7;

import java.util.Random;

/**
 * Title:        Project #7
 * Description:  Implementation of a heap using an ArrayBinaryTree extension
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author add, removeRoot, and swapPosition methods - Caleb Willson
 * @version 1.0
 */

public class ArrayHeap extends ArrayBinaryTree implements Heap {

    Comparator heapComp;

    public ArrayHeap(Comparator newComp) {
        this (newComp, DEFAULT_SIZE);
    }

    public ArrayHeap(Comparator newComp, int newSize) {
        super (newSize);
        heapComp = newComp;
    }

    /**
     * Add a new key-element pair into the heap.
     * @param newKey (Object)
     * @param newElement (Object)
     * @throws InvalidObjectException when a key not comparable by the given comparator is passed
     */
    public void add(Object newKey, Object newElement) throws InvalidObjectException {
        if (!heapComp.isComparable(newKey)) {
            throw new InvalidObjectException("Key not comparable");
        }

        // expand the array if needed
        if (btArray.length == size) {
            ArrayPosition[] newArr = new ArrayPosition[size * 2];
            for (int i = 0; i < btArray.length; i++) {
                newArr[i] = btArray[i];
            }
            btArray = newArr;
        }

        // add the new element to the end of the array
        btArray[size] = new ArrayPosition(size, new Item(newKey, newElement));
        size++;

        // bubble up algorithm
        boolean sorted = false;
        while (!sorted) {
            sorted = true;
            int i = size - 1;
            while (i > 0) {
                ArrayPosition currNode = btArray[i];
                ArrayPosition parent = (ArrayPosition)parent(btArray[i]);
                int parentIndex = parent.getIndex();
                
                Object currKey = ((Item)(currNode.element())).key();
                Object parentKey = ((Item)(parent.element())).key();

                if (!heapComp.isLessThan(currKey, parentKey)) {
                    break;
                }

                swapPositions(parent, currNode);
                sorted = false;

                i = parentIndex;
            }
        }
    }

    /**
     * Remove and return the Item with the minimum key from the heap.
     * @return the Item with the minimum key as an Object.
     * @throws EmptyHeapException when called on a heap with no elements.
     */
    public Object removeRoot() throws EmptyHeapException {
        if (size == 0) {
            throw new EmptyHeapException("removeRoot() called on an empty heap.");
        }

        // remove the root of the tree and replace it with the last element in the array
        Object removedElement = ((ArrayPosition)btArray[0]).element();
        btArray[0] = btArray[size - 1];
        btArray[0].setIndex(0);
        btArray[size - 1] = null;
        size--;

        // bubble down algorithm
        ArrayPosition currNode = (size != 0) ? (ArrayPosition)root() : null;

        while (currNode != null) {
            // get the indices of the current node's children 
            ArrayPosition lChild = (ArrayPosition)leftChild(currNode);
            ArrayPosition rChild = (ArrayPosition)rightChild(currNode);

            // quit if the indeces are out of the heap
            if (lChild == null) {
                break;
            }
            
            Object currNodeKey = ((Item)(currNode.element())).key();
            Object lChildKey = ((Item)(lChild.element())).key();
            Object rChildKey = (rChild != null) ? ((Item)(rChild.element())).key() : null;

            // case 1: current node has two children
            if (rChild != null) {     
                // swap with the left child
                if(heapComp.isLessThanOrEqualTo(lChildKey, rChildKey)) {
                    // only swap if currNode is greater than the left child
                    if (heapComp.isGreaterThan(currNodeKey, lChildKey)) {
                        swapPositions(currNode, lChild);
                    }
                    else {
                        break;
                    }
                }
                // swap with the right child
                else {
                    // only swap if currNode is greater than the right child
                    if (heapComp.isGreaterThan(currNodeKey, rChildKey)) {
                        swapPositions(currNode, rChild);
                    }
                    else {
                        break;
                    }
                }
            }
            // case 2: current node has only a left child
            else if (rChild == null) {
                // only swap if currNode is greater than its one child
                if (heapComp.isGreaterThan(currNodeKey, lChildKey)) {
                    swapPositions(currNode, lChild);
                }
                else {
                    break;
                }
            }
        }

        return removedElement;
    }

    /**
     * Private helper method for add() and removeRoot() methods.
     * Swaps the elements at positions pos1 and pos2 in the heap array
     * @param pos1 (ArrayPosition)
     * @param pos2 (ArrayPosition)
     */
    private void swapPositions(ArrayPosition pos1, ArrayPosition pos2) {
        ArrayPosition temp = btArray[pos1.getIndex()];
        int index1 = pos1.getIndex();
        int index2 = pos2.getIndex();

        btArray[index1] = btArray[index2];
        btArray[index1].setIndex(index1);
        btArray[index2] = temp;
        btArray[index2].setIndex(index2);
    }


    public static void main (String[] args) {
	    Comparator myComp = new IntegerComparator();
        Heap myHeap = new ArrayHeap (myComp, 8);
     
        int numElements = 100000;
        int numIterations = 100;

        Random rng = new Random();
        int j = 0;
        boolean sorted = true;
        while (j < numIterations && sorted) {
            
            for (int i = 0; i < numElements; i++) {
                myHeap.add(rng.nextInt(1, numElements*2), i);
            }

            int prevKey = -1;
            int currKey = -1;
            while (!myHeap.isEmpty()) {
                Item removedItem = (Item) myHeap.removeRoot();

                currKey = (Integer)removedItem.key();
                if (!(currKey >= prevKey)) {
                    System.out.println("Heap not sorted. Aborting");
                    sorted = false;
                    break;
                }
                prevKey = currKey;
            }
            System.out.println(j + ": All nodes removed");
            j++;
        }

        try {
            myHeap.add("test", 1);
        }
        catch (InvalidObjectException e) {
            System.out.println(e.getMessage());
        }

        try {
            myHeap.removeRoot();
        }
        catch (EmptyHeapException e) {
            System.out.println(e.getMessage());
        }
    }
}
