package com.alpha900i.samsungproject.view;

//interface for "click-on-list-item" processing
//don't want to put to much responsibilities in adapter
public interface OnItemClickListener {
    void onItemClicked(long id);
}
