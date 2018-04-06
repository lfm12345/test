package pool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Mypool implements PoolInterface {

	private int max = 0;
	private int idlecount = 0;
	private long maxawit = 0;

	// 当前连接数量
	AtomicInteger acctiveNums = new AtomicInteger(0);

	// 定义2个队列，空闲队列，繁忙队列

	LinkedBlockingQueue<JdbcConnection> busy;

	LinkedBlockingQueue<JdbcConnection> idle;

	@Override
	public void init(int max, long maxawit, int idlecount) {
		this.max = max;
		this.maxawit = maxawit;
		this.idlecount = idlecount;
		busy = new LinkedBlockingQueue<>();
		idle = new LinkedBlockingQueue<>();
	}

	@Override
	public JdbcConnection getResource() {
		JdbcConnection jdbcConnection = null;

		// 1.在空闲里面找
		jdbcConnection = idle.poll();
		if (jdbcConnection != null) {
			busy.offer(jdbcConnection);
			return jdbcConnection;
		}

		// 2.没有空闲,去创建
		if (acctiveNums.get() < max) {

			if (acctiveNums.incrementAndGet() <= max) {
				jdbcConnection = new JdbcConnection();
				busy.offer(jdbcConnection);

				return jdbcConnection;

			} else {

				acctiveNums.decrementAndGet();
			}

		}

		// 3.没有空闲的,连接池也满了,等待其他线程用完之后,放回连接池
		long timeout = maxawit;
		try {
			jdbcConnection = idle.poll(timeout, TimeUnit.MILLISECONDS);

			if (jdbcConnection != null) {

				busy.offer(jdbcConnection);
				return jdbcConnection;
			} else {

				try {
					throw new Exception("time out，超时了！");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return jdbcConnection;
	}

	@Override
	public void returnConnection(JdbcConnection conn) {
		// TODO Auto-generated method stub

	}

}
