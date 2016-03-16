package kale.adapter.util.rcv;

import android.support.v7.widget.RecyclerView;

import kale.adapter.ExRcvAdapterWrapper;

/**
 * @author Kale
 * @date 2016/3/16
 */
public class RcvAdapterDataObserver extends RecyclerView.AdapterDataObserver {

    private final ExRcvAdapterWrapper mAdapter;

    public RcvAdapterDataObserver(ExRcvAdapterWrapper adapter) {
        mAdapter = adapter;
    }

    @Override
    public void onChanged() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemRangeChanged(int positionStart, int itemCount) {
        mAdapter.notifyItemRangeChanged(positionStart + mAdapter.getHeaderCount(), itemCount);
    }

    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
        mAdapter.notifyItemRangeInserted(positionStart + mAdapter.getHeaderCount(), itemCount);
    }

    @Override
    public void onItemRangeRemoved(int positionStart, int itemCount) {
        mAdapter.notifyItemRangeRemoved(positionStart + mAdapter.getHeaderCount(), itemCount);
    }

    @Override
    public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
        // TODO: 2015/11/23 还没支持"多个"item的转移的操作 
        mAdapter.notifyItemMoved(fromPosition + mAdapter.getHeaderCount(), mAdapter.getHeaderCount() + toPosition);
    }

}
