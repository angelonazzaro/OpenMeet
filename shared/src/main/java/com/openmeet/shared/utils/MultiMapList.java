package com.openmeet.shared.utils;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This is a Java class that implements a MultiMapList,
 * which is a collection that maps keys to values, similar to a Map,
 * but in which each key may be associated with multiple values.
 *
 * @see java.util.Map
 *
 * @author Francesco Granozio
 * @author Angelo Nazzaro
 * */
public class MultiMapList<K, V> {

    private List<Multimap<K, V>> data;
    private Multimap<K, V> bufferMap;

    public MultiMapList() {
        data = new ArrayList<>();
        bufferMap = ArrayListMultimap.create();
    }

    @Override
    public String toString() {
        return data.toString();
    }

    /**
     * Returns the number of rows in the MultiMapList.
     *
     * @return an integer representing the number of rows in the MultiMapList.
     *
     * @author Francesco Granozio
     * @author Angelo Nazzaro
     */
    public int getRowsNumber() {
        return this.data.size();
    }

    /**
     * Returns the value(s) of the key specified at a specific row.
     *
     * @param key the key for which we want to get the values
     * @param row an integer representing the row number in which we are looking for the values.
     * @return a Collection of V objects representing the values
     *
     * @throws IndexOutOfBoundsException if the row number is out of bounds
     *
     * @author Francesco Granozio
     * @author Angelo Nazzaro
     */
    public Collection<V> get(K key, int row) throws IndexOutOfBoundsException {

        if (row < 0 || row >= data.size()) {
            throw new IndexOutOfBoundsException();
        }
        return data.get(row).get(key);
    }

    /**
     * Returns all the values of a specific row.
     *
     * @param row an integer representing the row number in which we are looking for the values.
     * @return a Collection of V objects representing the values of the specified row.
     *
     * @throws IndexOutOfBoundsException if the row number is out of bounds
     *
     * @author Francesco Granozio
     * @author Angelo Nazzaro
     */
    public Collection<V> getRow(int row) throws IndexOutOfBoundsException {

        if (row < 0 || row >= data.size()) {
            throw new IndexOutOfBoundsException();
        }
        return data.get(row).values();
    }

    /**
     * Adds an entry to the bufferMap
     *
     * @param key the key for which we want to add the value
     * @param value the value that will be added to the bufferMap for the key
     *
     * @author Francesco Granozio
     * @author Angelo Nazzaro
     */
    public void addEntryInCurrentRow(K key, V value) {
        bufferMap.put(key, value);
    }

    /**
     * Adds the current bufferMap as a row in the MultiMapList.
     * The addCurrentRow method should be called after adding
     * the entries to the bufferMap using the addEntryInCurrentRow method,
     * this will add the entries to the MultiMapList as a new row.
     *
     * @throws NullPointerException if the bufferMap is null
     *
     * @author Francesco Granozio
     * @author Angelo Nazzaro
     */
    public void addCurrentRow() throws NullPointerException {

        if (bufferMap == null) {

            throw new NullPointerException("Buffer map is null");
        }
        data.add(bufferMap);
        bufferMap = ArrayListMultimap.create();
    }

}
