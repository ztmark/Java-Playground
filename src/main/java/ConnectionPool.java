import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;

/**
 * Author: Mark
 * Date  : 15/11/24.
 */
public class ConnectionPool {

    private final LinkedList<Connection> pool = new LinkedList<>();

    public ConnectionPool(int initialSize) {
        if (initialSize > 0) {
            for (int i = 0; i < initialSize; i++) {
                pool.addLast(ConnectionDriver.createConnection());
            }
        }
    }

    public void releaseConnection(Connection connection) {
        synchronized (pool) {
            if (connection != null) {
                pool.addLast(connection);
                pool.notifyAll();
            }
        }
    }

    public Connection fetchConnection(long millis) throws InterruptedException {
        synchronized (pool) {
            if (millis <= 0) {
                while (pool.isEmpty()) {
                    pool.wait();
                }
                return pool.removeFirst();
            } else {
                long future = System.currentTimeMillis() + millis;
                long remained = future - System.currentTimeMillis();
                while (pool.isEmpty() && remained > 0) {
                    pool.wait(remained);
                    remained = future - System.currentTimeMillis();
                }
                if (!pool.isEmpty()) {
                    return pool.removeFirst();
                }
            }
        }
        return null;
    }


}
