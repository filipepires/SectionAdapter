package filipe.pires.me.sectionadapter.delegate;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public abstract class AdapterDelegate {

    protected int viewType;

    public static abstract class ViewHolderDelegate extends RecyclerView.ViewHolder {

        public ViewHolderDelegate(View itemView) {
            super(itemView);
        }

        public abstract boolean needsItemDecoration();
    }

    public AdapterDelegate(int viewType) {
        this.viewType = viewType;
    }

    public abstract ViewHolderDelegate onCreateViewHolder(ViewGroup parent);

    public abstract void onBindView(GenericCell genericCell, RecyclerView.ViewHolder holder);

    public abstract Class getHandlerClass();

    public int getItemViewType() {
        return viewType;
    }
}
