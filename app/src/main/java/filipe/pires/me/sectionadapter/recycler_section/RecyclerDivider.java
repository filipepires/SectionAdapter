package filipe.pires.me.sectionadapter.recycler_section;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.DimenRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import filipe.pires.me.sectionadapter.delegate.AdapterDelegate;

public class RecyclerDivider extends RecyclerView.ItemDecoration {

    private Context context;
    private Drawable divider;
    private int horizontalPadding;

    public RecyclerDivider(Context context) {
        divider = ContextCompat.getDrawable(context, android.R.drawable.divider_horizontal_bright);
        this.horizontalPadding = 0;
        this.context = context.getApplicationContext();
    }

    public void setPadding(@DimenRes int dimen) {
        this.horizontalPadding = context.getResources().getDimensionPixelOffset(dimen);
    }

    @Override
    public void onDrawOver(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft() + horizontalPadding;
        int right = parent.getWidth() - parent.getPaddingRight() - horizontalPadding;
        int childCount = parent.getChildCount();

        if (childCount == 1)
            return;

        for (int i = 0; i < childCount - 1; i++) {
            View child = parent.getChildAt(i);
            View nextChild = parent.getChildAt(i + 1);
            if (needsDecoration(child, parent) && needsDecoration(nextChild, parent)) {
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                int childBottom = child.getBottom() + params.bottomMargin;
                int dividerEnd = childBottom + divider.getIntrinsicHeight();

                divider.setBounds(left, childBottom, right, dividerEnd);
            }
        }
    }

    private boolean needsDecoration(View view, RecyclerView parent) {
        AdapterDelegate.ViewHolderDelegate holderDelegate = (AdapterDelegate.ViewHolderDelegate) parent.getChildViewHolder(view);
        return holderDelegate.needsItemDecoration();
    }

}
