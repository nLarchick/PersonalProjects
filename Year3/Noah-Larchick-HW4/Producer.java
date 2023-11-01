import java.util.Random;

public class Producer implements Runnable{
  private Buffer buff;
  public int bufferValueCounter = 0;
  public Double counterValue = 0.0;

  public Producer(Buffer sharedBuffer) {
    this.buff = sharedBuffer;
  }

  public Double create(){
    Random randy = new Random();
    Double item = randy.nextDouble() * 100.0;
    return item;
  }

  public void run() {
    synchronized(this.buff){
      while (bufferValueCounter <  1000000) {
        if (!buff.isFull()) { // start producing
          Double item = this.create();
          buff.enqueue(item);
          bufferValueCounter++;
          counterValue += item;
          if (bufferValueCounter % 100000 == 0) {
            System.out.printf("PRODUCER: created [%d] items, total=%f\n", bufferValueCounter, counterValue);
          }
          buff.notify();
        } else { // the buffer is full... wait for comsumer
          try {
            buff.wait();
          } catch (InterruptedException e) {
            System.out.println("there was an issue...");
          }
        }
      }
    }

    System.out.printf("PRODUCER: finished producing [%d] items\n", bufferValueCounter);
  }
}