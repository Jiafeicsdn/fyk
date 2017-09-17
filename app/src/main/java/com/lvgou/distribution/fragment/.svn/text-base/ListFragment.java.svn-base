package com.lvgou.distribution.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lvgou.distribution.R;

/**
 * Created by jiangjieqiang on 16/1/18.
 */
public class ListFragment extends Fragment {

    private static final int LIST_NUM = 20;

    private View view;
    @ViewInject(R.id.id_listview)
    private ListView listView;





//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Bundle bundle = getArguments();
//        if (bundle != null){
//            type = bundle.getInt("type");
//        }
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_fragment, null);
        ViewUtils.inject(this, view);


        return view;
    }


}
