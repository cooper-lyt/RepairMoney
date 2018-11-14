package cc.coopersoft.framework.tools;

import java.util.*;

public abstract class SetLinkList<T> implements List<T> {

    public static <E extends Comparable<? super E>>  SetLinkList<E> instance(Set<E> set){
        return new InnerSetLinkList<>(set);
    }

    public static <E>  SetLinkList<E>  instance(Set<E> set, Comparator<? super E> c){
        return new OutSetLinkList<>(set,c);
    }

    private static class OutSetLinkList<E> extends SetLinkList<E> {

        private Comparator<? super E> c;

        private OutSetLinkList(Set<E> set , Comparator<? super E> c) {
            super(set);
            this.c = c;
        }

        @Override
        protected List<E> list() {
            List<E> result = new ArrayList<>(set);
            Collections.sort(result,c);
            return result;
        }
    }
    
    private static class InnerSetLinkList<E extends Comparable<? super E>> extends SetLinkList<E>{

        public InnerSetLinkList(Set<E> set) {
            super(set);
        }

        @Override
        protected List<E> list() {
            List<E> result = new ArrayList<>(set);
            Collections.sort(result);
            return result;
        }

    }

    protected Set<T> set;


    protected abstract List<T> list();

    protected SetLinkList(Set<T> set) {
        this.set = set;
    }


    @Override
    public int size() {
        return set.size();
    }

    @Override
    public boolean isEmpty() {
        return set.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return set.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return list().iterator();
    }

    @Override
    public Object[] toArray() {
        return list().toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return list().toArray(a);
    }

    @Override
    public boolean add(T t) {
        return set.add(t);
    }

    @Override
    public boolean remove(Object o) {
        return set.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return set.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return set.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return set.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return set.retainAll(c);
    }

    @Override
    public void clear() {
        set.clear();
    }

    @Override
    public T get(int index) {
        return list().get(index);
    }

    @Override
    public T set(int index, T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T remove(int index) {
        T result = list().get(index);
        set.remove(result);
        return result;
    }

    @Override
    public int indexOf(Object o) {
        return list().indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return list().lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        return list().listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return list().listIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return list().subList(fromIndex,toIndex);
    }
}
