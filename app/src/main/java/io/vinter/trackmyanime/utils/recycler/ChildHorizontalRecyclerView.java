package io.vinter.trackmyanime.utils.recycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import io.vinter.trackmyanime.utils.recycler.MainVerticalRecyclerView;

public class ChildHorizontalRecyclerView extends MainVerticalRecyclerView {
    public ChildHorizontalRecyclerView(@NonNull Context context) {
        super(context);
    }

    public ChildHorizontalRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ChildHorizontalRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept){

    }
}
