package com.udacity.udacitybakingapps.RecyclerViewDecorator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

public class listDecorator extends RecyclerView.ItemDecoration {

    private Drawable mDivider;
    private int offset;
    String TAG=this.getClass().getSimpleName();
    public listDecorator(Drawable divider,int offset){
        mDivider=divider;
        this.offset=offset;
    }
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
            outRect.top=offset;
            outRect.right=offset;
            outRect.left=offset;
            outRect.bottom=offset;
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int leftdivider=32;
        int rightdiver=parent.getWidth()-32;
        for(int i=0;i<parent.getChildCount();i++) {
            View child=parent.getChildAt(i);

            mDivider.setBounds(leftdivider, child.getTop(),rightdiver,child.getBottom());
            mDivider.draw(c);
        }

    }
}
