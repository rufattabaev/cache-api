package ru.tri;

import java.util.Collection;
import java.util.Map;

public interface Cache<K, V> {
    /**
     * Вносит запись в кэш, жизненный цикл которой истекает после указанного
     * количества секунд
     *
     * @param key
     *            Ключ записи, который используется для извлечения записи
     *            или для удаления записи до окончания её жизненного цикла.
     * @param value
     *            Фактический объект, который должен быть кэширован
     * @throws IllegalArgumentException
     *             Выбрасывает illegal argument exception, если ключ или значение равны
     *             null.
     */
    void put(K key, V value);

    /**
     * Извлекает из кэша запись, которая ранее была кэширована, используя
     * {@link #put(Object, Object)}
     *
     * @param key
     *            Ключ, с которым запись была кэширована
     * @return Возвращает кэшированную запись. Возвращает null, если не существует
     * записи с ключом К или жизненный цикл записи истёк.
     */
    V get(K key);

    /**
     * Извлекает все записи из кэша, используя коллекцию ключей.
     *
     * @param collection
     *            Коллекция ключей, для которой должныбыть возвращены значения.
     * @return Возвращает результат в виде map, содержащей ключи и значения.
     */
    Map<K, V> getAll(Collection<K> collection);

    /**
     * Очищает все записи из кэша
     */
    void clear();

    /**
     * Удаляет запись из кэша, используя ключ К. Возвращает true, если запись
     * успешно удалена. Иначе возвращает false.
     *
     * @param key
     *            Ключ записи которая должна быть удалена.
     * @return Возвращает true, если удаление прошло успешно.
     *         Иначе возвращает false.
     */
    boolean remove(K key);

    /**
     * Удаляет запись из кэша, используя ключ К. Возвращает удаленный объект.
     * Если операция прошла неудачно, возвращает <code>null</code>.
     *
     * @param key
     *            Ключ записи которая должна быть удалена.
     * @return Возвращает объект, который был удален.
     * В случае неудачи возвращает <code>null</code>
     */
    V removeAndGet(K key);

    /**
     * @return Возвращает размер кэша.
     */
    int size();

    /**
     * Извлекает все ключи из кэша.
     *
     * @return Возвращает коллекцию ключей, содержащихся в кэше.
     */
    Collection<K> getKeys();
}
