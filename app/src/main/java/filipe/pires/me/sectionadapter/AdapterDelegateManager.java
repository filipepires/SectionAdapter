package filipe.pires.me.sectionadapter;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

import filipe.pires.me.sectionadapter.delegate.AdapterDelegate;
import filipe.pires.me.sectionadapter.delegate.GenericCell;

public class AdapterDelegateManager {

    SparseArrayCompat<AdapterDelegate> delegates = new SparseArrayCompat<>();
    Map<Class, Integer> classes = new HashMap<>();

    public void addDelegate(AdapterDelegate delegate) {
        addDelegate(delegate, false);
    }

    private AdapterDelegateManager addDelegate(AdapterDelegate delegate, boolean allowReplacement) {
        if (delegate == null)
            throw new IllegalStateException("Adapater delegate cannot be null");

        int viewType = delegate.getItemViewType();

        Class delegateClass = delegate.getHandlerClass();
        classes.put(delegateClass, viewType);

        if (!allowReplacement && delegates.get(viewType) != null)
            throw new IllegalStateException("View type " + viewType + "already registered as " + delegates.get(viewType));

        delegates.put(viewType, delegate);

        return this;
    }

    public int getItemViewType(GenericCell genericCell) {
        if (classes.containsKey(genericCell.getClass()))
            return classes.get(genericCell.getClass());
        else
            throw new IllegalStateException("No adapter delegate for type " + genericCell.getClass());
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        AdapterDelegate delegate = delegates.get(viewType);

        if (delegate == null)
            throw new NullPointerException("Adpater delegate for " + viewType);

        AdapterDelegate.ViewHolderDelegate viewHolder = delegate.onCreateViewHolder(parent);
        if (viewHolder == null)
            throw new NullPointerException("View holder " + viewType + " from adapter delegate " + delegate + " is null");

        return viewHolder;
    }

    public void onBindViewHolder(GenericCell genericCell, RecyclerView.ViewHolder holder, int position) {
        AdapterDelegate delegate = delegates.get(holder.getItemViewType());

        if (delegate == null)
            throw new NullPointerException("Adapter delegate for " + holder.getItemViewType() + " not found");

        delegate.onBindView(genericCell, holder);
    }
}
