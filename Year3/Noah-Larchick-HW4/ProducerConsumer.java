public class ProducerConsumer {

  public static void main(String[] args) {
    Buffer buff = new Buffer(1000);

    Producer factory = new Producer(buff);
    Consumer yeat = new Consumer(buff);

    Thread createThread = new Thread(factory);
    Thread destroyThread = new Thread(yeat);

    createThread.start();
    destroyThread.start();

    try {
      createThread.join();
      destroyThread.join();
    } catch (InterruptedException e) {
      System.out.println("Interupt exception cought!");
    }

    System.out.println("Exiting!");

  }
}
