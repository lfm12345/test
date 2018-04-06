package pool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;

public class NopoolTest {

	final static int threadnums = 500;

	private final static CountDownLatch count_Down_LATCH = new CountDownLatch(threadnums);
	Mypool pool = new Mypool();
	public static void main(String[] args) {
		for (int i = 0; i < threadnums; i++) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					// 等待计数器到0，再执行后面的代码
					try {
						count_Down_LATCH.await();
						String sql = "select name from user LIMIT 1";
						Connection connection = new JdbcConnection().getConnection();
						ResultSet result = connection.createStatement().executeQuery(sql);
						result.next();
						System.out.println(Thread.currentThread().getName() + "查询结果======" + result.getString("name"));

					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}).start();

			count_Down_LATCH.countDown();
		}

	}

}
