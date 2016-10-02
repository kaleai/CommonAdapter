package kale.commonadapter.util;

import android.databinding.ListChangeRegistry;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author Kale
 * @date 2016/2/21
 *
 * Fork from {@link android.databinding.ObservableArrayList}
 */
public class ObservableArrayList<T> extends ArrayList<T> implements ObservableList<T> {

    private transient ListChangeRegistry mListeners = new ListChangeRegistry();

    @Override
    public void addOnListChangedCallback(OnListChangedCallback listener) {
        if (mListeners == null) {
            mListeners = new ListChangeRegistry();
        }
        mListeners.add(listener);
    }

    @Override
    public void removeOnListChangedCallback(OnListChangedCallback listener) {
        if (mListeners != null) {
            mListeners.remove(listener);
        }
    }

    @Override
    public boolean add(T object) {
        super.add(object);
        notifyAdd(size() - 1, 1);
        return true;
    }

    @Override
    public void add(int index, T object) {
        super.add(index, object);
        notifyAdd(index, 1);
    }

    @Override
    public boolean addAll(@NonNull Collection<? extends T> collection) {
        int oldSize = size();
        boolean added = super.addAll(collection);
        if (added) {
            notifyAdd(oldSize, size() - oldSize);
        }
        return added;
    }

    @Override
    public boolean addAll(int index, @NonNull Collection<? extends T> collection) {
        boolean added = super.addAll(index, collection);
        if (added) {
            notifyAdd(index, collection.size());
        }
        return added;
    }

    @Override
    public void clear() {
        int oldSize = size();
        super.clear();
        if (oldSize != 0) {
            notifyRemove(0, oldSize);
        }
    }

    @Override
    public T remove(int index) {
        T val = super.remove(index);
        notifyRemove(index, 1);
        return val;
    }

    @Override
    public boolean remove(Object object) {
        int index = indexOf(object);
        if (index >= 0) {
            remove(index);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public T set(int index, T object) {
        T val = super.set(index, object);
        if (mListeners != null) {
            mListeners.notifyChanged(this, index, 1);
        }
        return val;
    }

    @Override
    public void removeRange(int fromIndex, int toIndex) {
        super.removeRange(fromIndex, toIndex);
        notifyRemove(fromIndex, toIndex - fromIndex);
    }

    private void notifyAdd(int start, int count) {
        if (mListeners != null) {
            mListeners.notifyInserted(this, start, count);
        }
    }

    private void notifyRemove(int start, int count) {
        if (mListeners != null) {
            mListeners.notifyRemoved(this, start, count);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // custom
    ///////////////////////////////////////////////////////////////////////////

    public void removeRange(@NonNull Collection<T> collection) {
        Object firstObj = collection.iterator().next();
        int from = indexOf(firstObj);
        this.removeRange(from, from + collection.size() - 1);
    }

    public void set(int from, Collection<T> collection) {
        int i = 0;
        for (Iterator<T> iterator = collection.iterator(); iterator.hasNext(); i++) {
            T obj = iterator.next();
            super.set(from + i, obj);
        }

        if (mListeners != null) {
            mListeners.notifyChanged(this, from, collection.size());
        }
    }

    public void move(int fromPosition, int toPosition) {
        moveRange(fromPosition, toPosition, 1);
    }

    public void moveRange(int fromPosition, int toPosition, int itemCount) {
        List<T> list = new ArrayList<>();
        for (int i = 0; i < itemCount; i++) {
            list.add(super.get(i + fromPosition));
        }
        super.removeAll(list);
        super.addAll(toPosition, list);

        if (mListeners != null) {
            mListeners.notifyMoved(this, fromPosition, toPosition, itemCount);
        }
    }

    /**
     * Reset all data in the list
     *
     * @param collection new data
     */
    public void reset(Collection<T> collection) {
        super.clear();
        super.addAll(collection);
        if (mListeners != null) {
            mListeners.notifyChanged(this);
        }
    }
}