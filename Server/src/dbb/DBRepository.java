package dbb;


public interface DBRepository {

    void connect() throws Exception;

    void disconnect() throws Exception;

    void commit() throws Exception;

    void rollback() throws Exception;
}
