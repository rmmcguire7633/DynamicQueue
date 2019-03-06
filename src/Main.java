/**
 * Homework 1, DynamicQueue.
 * Author -
 * Last modified - 03/06/2019
 * copyright info - n/a
 * */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

  /**
   * The Linked list to be used with with the DynamicQueue type.
   * */
  public static class DynamicNode {

    private Object info;
    private DynamicNode next;

    /**
     * Constructor used to set the DynamicNode.
     * */
    public DynamicNode(Object x, DynamicNode n){
      info=x;
      next=n;
    }

    /**
     * Gets the object inside the node.
     * @return the object on the node.
     * */
    public Object getInfo(){
      return info;
    }

    /**
     * gets what node the node is pointing to.
     * @return the pointer node.
     * */
    public DynamicNode getNext(){
      return next;
    }

    /**
     * Sets the object into the node.
     * @param x the object to be placed in the node.
     * */
    public void setInfo(Object x){
      info=x;
    }

    /**
     * Sets the pointer in the ndoe.
     * @param n the node to be pointed to.
     * */
    public void setNext(DynamicNode n){
      next=n;
    }

    /**
     * The to string method used to print out the objects in the node.
     * @return the object in the node.
     * */
    public String toString(){
      return info.toString();
    }
  }

  /**
   * The DynamicQueue Type.
   * */
  public static class DynamicQueue {

    private DynamicNode front, rear;

    // The max amount of objects inside the queue.
    private final int QUEUESIZE = 4;

    /**
     * Constructor used for initializing the DynamicQueue.
     * */
    public DynamicQueue() {

      front = rear = null;
    }

    /**
     * Checks the queue.
     * @return true if the queue is empty.
     * */
    public boolean empty() {

      return (front == null);
    }

    /**
     * Prints out all objects in the queue.
     * */
    public void print(){
      if(front == null){
        System.out.print("Empty");
      }
      DynamicNode p=front;

      while(p!=null){
        System.out.print(p.getInfo()+((p.getNext()!=null)?" ":""));
        p=p.getNext();
      }
      System.out.println();
    }

    /**
     * Inserts an object into the queue.
     * @param x the object to be inserted into the queue.
     * */
    public void insert(Object x) {

      DynamicNode p = new DynamicNode(x, null);

      //Checks to see if the object is already in the queue.
      if (checkIndex(p.getInfo()) == false) {

        if (empty()) {

          front = rear = p;
          System.out.print("Inserting " + x + " in rear.");

        // Checks how many objects are in the queue.
        // If the number of objects in the queue are equal to or greater than the max queue size,
        // removes the first object in the queue and inserts the new object into the rear.
        } else if (getCount() >= QUEUESIZE) {

          System.out.print("Q is full, removing front. Inserting " + x + " in rear.");
          remove();
          rear.setNext(p);
        } else {

          System.out.print("Inserting " + x + " in rear.");
          rear.setNext(p);
        }

        rear = p;

      // If the object is already in the queue.
      } else {

        DynamicNode pointer = front;
        DynamicNode previous = null;

        // if the front object is the same as the to be inserted object.
        // moves the front object to the rear.
        if (x.equals(pointer.getInfo())) {

          front = pointer.getNext();
          pointer.setNext(null);
          rear.setNext(pointer);
          rear = pointer;
          System.out.print("Moving " + x + " to rear.");

        // if the rear object is the same as the to be inserted object.
        // nothing has to be done with the queue.
        } else if (x.equals(rear.getInfo())) {

          System.out.print(x + " is already rear.");

        // If the to be inserted object matches an object in the middle of the queue.
        // Moves the object in the queue to the rear.
        } else {

          while (pointer.getNext() != null) {

            previous = pointer;
            pointer = pointer.getNext();

            previous.setNext(pointer.getNext());
            pointer.setNext(null);
            rear.setNext(pointer);
            rear = pointer;
            System.out.print("Moving " + x + " to rear.");
          }
        }
      }
    }

    /**
     * "pops" the queue removing the first item in the queue.
     * @return the object being removed from the queue.
     * */
    public Object remove() {

      if (empty()) {

        System.out.println("Queue underflow");
        System.exit(1);
      }

      DynamicNode p = front;
      Object temp = p.getInfo();
      front = p.getNext();

      if (front == null) {

        rear = null;
      }

      return temp;
    }

    /**
     * Gets how many objects are in the queue.
     * @return the number of objects inside the queue.
     * */
    public int getCount() {

      DynamicNode temp = front;
      int count = 0;

      while (temp != null) {

        count++;
        temp = temp.getNext();
      }

      return count;
    }

    /**
     * Checks to see of the passed in object is in the queue.
     * @param x - the object that is going to passed into the queue.
     * @return true if the object is inside the que already.
     * */
    public boolean checkIndex(Object x) {

      DynamicNode q = front;

      while (q != null) {

        if (x.equals(q.getInfo())) {

          return true;
        }
        q = q.getNext();
      }

      return false;
    }
  }

  public static void main(String[] args) throws FileNotFoundException {

    // The object to be inserted into the queue.
    String item;

    // The queue to inert the object in.
    String index;

    // Used for converting the index into an int to use with arrays.
    int result = 0;

    // The 1-D array size of the DynamicQueues.
    final int N = 4;

    DynamicQueue[] queue = new DynamicQueue[N];
    for (int i = 0; i < queue.length; i ++) {
      queue[i] = new DynamicQueue();
    }

    // path should work with any operating system just have data in same folder as program.
    // was done on windows.
    File file = new File("data.txt");
    Scanner sc = new Scanner(file);

    // goes through the data.txt file and puts the data in the correct queue.
    while (sc.hasNextLine()) {
      if (sc.hasNext()) {
        item = sc.next();
        index = sc.next();
        result = Integer.parseInt(index);
        System.out.print("Read key " + item + " for queue " + result + ". ");
        queue[result].insert(item);
        System.out.println(" Q" + result + ": ");
        queue[result].print();

      } else {
        break;
      }
    }

    System.out.println();
    System.out.println("..Final Queues..");
    for (int i = 0; i < queue.length; i++) {

      System.out.print("Q" + i + ": ");
      queue[i].print();
    }
  }
}
