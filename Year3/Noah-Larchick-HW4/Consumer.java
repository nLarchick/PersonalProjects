public class Consumer implements Runnable{
  private Buffer buff;
  public int bufferValueCounter = 0;
  public Double counterValue = 0.0;

  public Consumer(Buffer sharedBuffer) {
    this.buff = sharedBuffer;
  }

  public void run() {
    synchronized(this.buff){
      while (bufferValueCounter < 1000000){
        if (!buff.isEmpty()) { // start consuming
          Double item = buff.dequeue();
          bufferValueCounter++;
          counterValue += item;
          if (bufferValueCounter % 100000 == 0) {
            System.out.printf("CONSUMER: ate [%d] items, total=%f\n", bufferValueCounter, counterValue);
          }
          buff.notify();
        } else { // the buffer is empty... wait for producer
          try {
            buff.wait();
          } catch (InterruptedException e) {
            System.out.println("there was an issue...");
          }
        }
      }
    }

    System.out.printf("CONSUMER: finished eating [%d] items\n", bufferValueCounter);
  }
}