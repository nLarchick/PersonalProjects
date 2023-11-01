public class Buffer {
  //private boolean condition = false;
  private Double[] buffer;
  private int capacity;
  private int size;
  private int front;
  private int rear;

  public Buffer(int capacity) {
    this.capacity = capacity;
    this.buffer = new Double[capacity];
    this.size = 0;
    this.front = 0;
    this.rear = -1;
  }

  public boolean isEmpty() {
    return (size == 0);
  }
  public boolean isFull() {
    return (size == capacity);
  }
  public int getSize() {
    return size;
  }

  public void enqueue(Double item) {
    if (isFull()) {throw new IllegalStateException("Buffer is full");}
    rear = (rear + 1) % capacity;
    buffer[rear] = item;
    size++;
  }
  public Double dequeue() {
    if (isEmpty()) {throw new IllegalStateException("Buffer is empty");}
    Double item = buffer[front];
    front = (front + 1) % capacity;
    size--;
    return item;
  }

  /*
   * public synchronized void waitbuff() throws InterruptedException {
   *   while (!condition) {wait();}
   *   condition = false;
   * }
   * public synchronized void setbuff() {
   *   condition = true;
   *   notifyAll();
   * } 
  */

}