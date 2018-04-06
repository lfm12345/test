package pool;

public interface PoolInterface {
	
	public void init(int max,long maxawit,int idlecount);
 
	public JdbcConnection getResource();
	
	public void returnConnection(JdbcConnection conn);
	
	
}
