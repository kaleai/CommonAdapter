package kale.adapter;

import java.util.HashMap;

import android.support.v7.widget.RecyclerView;

/**
 * Author:    valuesfeng
 * Version    V1.0
 * Date:      16/11/30
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/11/30          valuesfeng              1.0                    1.0
 */
public class RecycledViewPool extends RecyclerView.RecycledViewPool {

    private HashMap<Object, Integer> typePool;

    public RecycledViewPool() {
        typePool = new HashMap<>();
    }

    public HashMap<Object, Integer> getTypePool() {
        return typePool;
    }
}