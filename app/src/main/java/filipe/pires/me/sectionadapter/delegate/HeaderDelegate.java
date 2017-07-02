package filipe.pires.me.sectionadapter.delegate;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import filipe.pires.me.sectionadapter.R;

public class HeaderDelegate extends AdapterDelegate {

    private Context context;

    public static class Cell implements GenericCell {
        private String text;

        public Cell(String text) {
            this.text = text;
        }

        public Cell() {

        }
    }

    public static class DelegateViewHolder extends ViewHolderDelegate {

        TextView text;

        public DelegateViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
        }

        @Override
        public boolean needsItemDecoration() {
            return false;
        }

        public void resetView(Cell cell) {
            text.setText(cell.text);
        }
    }

    public HeaderDelegate(Context context, int viewType) {
        super(viewType);
        this.context = context;
    }

    @Override
    public ViewHolderDelegate onCreateViewHolder(ViewGroup parent) {
        return new DelegateViewHolder(LayoutInflater.from(context).inflate(R.layout.delelgate_header, parent, false));
    }

    @Override
    public void onBindView(GenericCell genericCell, RecyclerView.ViewHolder holder) {
        Cell cell = (Cell) genericCell;
        DelegateViewHolder viewHolder = (DelegateViewHolder) holder;

        viewHolder.resetView(cell);
    }

    @Override
    public Class getHandlerClass() {
        return Cell.class;
    }
}
