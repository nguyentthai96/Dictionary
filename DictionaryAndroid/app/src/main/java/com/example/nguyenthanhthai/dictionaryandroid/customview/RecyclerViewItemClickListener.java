package com.example.nguyenthanhthai.dictionaryandroid.customview;

import android.view.View;

/**
 * Created by NguyenThanhThai on 3/27/2017.
 */

public interface RecyclerViewItemClickListener {
    public void onClick(View view, int position);

    public void onLongClick(View view, int position);
}
