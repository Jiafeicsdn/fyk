package com.xdroid.functions.holder;

/**
 * 创建viewholder接口，由LazyViewHolderCreator类实现，返回一个ViewHolderBase给ListViewDataAdapterBase类
 *
 * @param <ItemDataType>每一项的泛型类型的数据
 * @author Robin
 */
public interface ViewHolderCreator<ItemDataType> {
	/**
	 * 创建viewholder回调函数
	 * @return 返回一个ViewHolderBase对象
	 */
    public ViewHolderBase<ItemDataType> createViewHolder();
}