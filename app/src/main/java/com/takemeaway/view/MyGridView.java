package com.takemeaway.view;

/**
 * Created by longjianlin on 14-9-26.
 */

import android.widget.GridView;

public class MyGridView extends GridView {
    public MyGridView(android.content.Context context) {
        super(context);
    }

    public MyGridView(android.content.Context context,
                      android.util.AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置不滚动
     */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }

}