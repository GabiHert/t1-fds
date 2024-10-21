package com.fds.siscaa.domain.utils;

import java.util.List;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;

public class CustomList<E> implements List<E>, Iterator<E> {
    private List<E> list;
    private int currentIndex = 0;

    private <T> T parse(Class<T> clazz, E element, String methodName) {
        try {
            Method toEntityMethod = element.getClass().getMethod(methodName);
            return clazz.cast(toEntityMethod.invoke(element));
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Error invoking 'toEntity' method on class: " + element.getClass().getName(), e);
        }
    }

    public <T> CustomList<T> toEntities(Class<T> clazz) {
        CustomList<T> newList = new CustomList<T>();
        for (E element : list) {
            newList.add(this.parse(clazz, element, "toEntity"));
        }

        return newList;
    }

    public <T> CustomList<T> toDtos(Class<T> clazz) {
        CustomList<T> newList = new CustomList<>();
        try {
            for (E element : list) {
                T dto = clazz.getConstructor(element.getClass()).newInstance(element);
                newList.add(dto);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Error converting element to DTO: " + e.getMessage(), e);
        }
        return newList;
    }

    public CustomList(List<E> list) {
        this.list = list;
    }

    public CustomList() {
        this.list = new ArrayList<E>();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return list.iterator();
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return list.toArray(a);
    }

    @Override
    public boolean add(E e) {
        return list.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return list.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return list.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return list.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return list.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return list.retainAll(c);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public E get(int index) {
        return list.get(index);
    }

    @Override
    public E set(int index, E element) {
        return list.set(index, element);
    }

    @Override
    public void add(int index, E element) {
        list.add(index, element);
    }

    @Override
    public E remove(int index) {
        return list.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    @Override
    public ListIterator<E> listIterator() {
        return list.listIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return list.listIterator(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex, toIndex);
    }

    @Override
    public boolean hasNext() {
        // Check if the current index is less than the size of the list
        return currentIndex < list.size();
    }

    @Override
    public E next() {
        // Return the next element and increment the index
        if (!hasNext()) {
            throw new IllegalStateException("No more elements");
        }
        return list.get(currentIndex++);
    }
}