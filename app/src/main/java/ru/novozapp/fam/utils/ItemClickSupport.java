package ru.novozapp.fam.utils;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class ItemClickSupport {
    private final RecyclerView mRecyclerView;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private int mItemID;

    public ItemClickSupport(RecyclerView recyclerView, int mItemID) {
        this.mRecyclerView = recyclerView;
        this.mItemID = mItemID;
        this.mRecyclerView.setTag(mItemID, this);
        this.mRecyclerView.addOnChildAttachStateChangeListener(mAttachListener);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(v);
                mOnItemClickListener.onItemClicked(mRecyclerView, holder.getAdapterPosition(), v);
            }
        }
    };

    private View.OnLongClickListener mOnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if (mOnItemLongClickListener != null) {
                RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(v);
                return mOnItemLongClickListener.onItemLongClicked(mRecyclerView, holder.getAdapterPosition(), v);
            }
            return false;
        }
    };

    private RecyclerView.OnChildAttachStateChangeListener mAttachListener = new RecyclerView.OnChildAttachStateChangeListener() {
        @Override
        public void onChildViewAttachedToWindow(View view) {
            if (mOnItemClickListener != null)    view.setOnClickListener(mOnClickListener);
            if (mOnItemLongClickListener != null)    view.setOnLongClickListener(mOnLongClickListener);
        }

        @Override
        public void onChildViewDetachedFromWindow(View view) {

        }
    };

    public static ItemClickSupport addTo(RecyclerView view, int itemID) {
        ItemClickSupport support = (ItemClickSupport) view.getTag(itemID);
        if (support == null)    support = new ItemClickSupport(view, itemID);
        return support;
    }

    public static ItemClickSupport removeFrom(RecyclerView view, int itemID) {
        ItemClickSupport support = (ItemClickSupport) view.getTag(itemID);
        if (support != null)    support.detach(view);
        return support;
    }

    public ItemClickSupport setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
        return this;
    }

    public ItemClickSupport setOnItemLongClickListener(OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
        return this;
    }

    private void detach(RecyclerView view) {
        view.removeOnChildAttachStateChangeListener(mAttachListener);
        view.setTag(mItemID, null);
    }

    public interface OnItemClickListener {
        void onItemClicked(RecyclerView recyclerView, int position, View v);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClicked(RecyclerView recyclerView, int position, View v);
    }
}