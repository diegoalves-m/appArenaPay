package com.developer.diegoalves.peladapay.adapters.clickAdapter;

import android.view.View;

/**
 * Created by Diego Alves on 28/11/2015.
 */
public interface RecyclerViewOnClickListener {

     void onClickListener(View view, int position);
     void onLongPressClickListener(View view, int position);
}
