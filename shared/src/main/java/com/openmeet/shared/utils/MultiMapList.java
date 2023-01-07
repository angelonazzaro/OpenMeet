package com.openmeet.shared.utils;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    public int getRowsNumber() {
        return this.data.size();
    }

    public Collection<V> get(K key, int row) throws IndexOutOfBoundsException {

        if (row < 0 || row >= data.size()) {
            throw new IndexOutOfBoundsException();
        }
        return data.get(row).get(key);
    }

    public Collection<V> getRow(int row) throws IndexOutOfBoundsException {

        if (row < 0 || row >= data.size()) {
            throw new IndexOutOfBoundsException();
        }
        return data.get(row).values();
    }

    public void addEntryInCurrentRow(K key, V value) {
        bufferMap.put(key, value);
    }

    public void addCurrentRow() throws NullPointerException {

        if (bufferMap == null) {

            throw new NullPointerException("Buffer map is null");
        }
        data.add(bufferMap);
        bufferMap = ArrayListMultimap.create();
    }

}
