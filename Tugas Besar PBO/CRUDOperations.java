public interface CRUDOperations<T> {
    void create(T item);
    T read(int id);
    void update(T item); // Metode yang harus diimplementasikan
    void update(int id, T item);
    void delete(int id);
}