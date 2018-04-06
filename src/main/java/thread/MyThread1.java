package thread;

public class MyThread1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyThread t1 = new MyThread();
		Thread a = new Thread(t1, "A");
		Thread b = new Thread(t1, "B");
		Thread c = new Thread(t1, "C");
		Thread d = new Thread(t1, "D");
		Thread e = new Thread(t1, "E");
		a.start();
		b.start();
		c.start();
		d.start();
		e.start();
	}

}

class MyThread extends Thread {

	MyThread() {

	}

	private int count = 5;

	public MyThread(String name) {
		super();
		this.setName(name);

	}

	@Override
	public void run() {
		super.run();
		synchronized (this) {
			count--;
			System.out.println("由" + this.currentThread().getName() + "计算，count=" + count);
		}
	}

}
