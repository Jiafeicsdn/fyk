package com.xdroid.common.utils;

import android.util.SparseArray;
import android.view.View;

/**
 * 超简洁的ViewHolder.
 * 代码更简单，性能略低，可以忽略
 * 使用方法：
 * 在Adapter的getView（）方法中：
 @Override
 public View getView(int position, View convertView, ViewGroup parent) {

     if (convertView == null) {
         convertView = LayoutInflater.from(context)
           .inflate(R.layout.banana_phone, parent, false);
     }

     ImageView bananaView = ViewHolderUtils.get(convertView, R.id.banana);
     TextView phoneView = ViewHolderUtils.get(convertView, R.id.phone);

     BananaPhone bananaPhone = getItem(position);
     phoneView.setText(bananaPhone.getPhone());
     bananaView.setImageResource(bananaPhone.getBanana());

     return convertView;
 }
 */
public class ViewHolderUtils {
    
    /**
     * ImageView view = ViewHolderUtils.get(convertView, R.id.imageView);
     * @param view
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T extends View> T get(View view, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }
}
