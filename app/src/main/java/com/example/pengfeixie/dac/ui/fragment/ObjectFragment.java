package com.example.pengfeixie.dac.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.pengfeixie.dac.R;
import com.example.pengfeixie.dac.base.BaseRecyclerViewMvpFragment;
import com.example.pengfeixie.dac.dao.RealmHelper;
import com.example.pengfeixie.dac.event.BusProvider;
import com.example.pengfeixie.dac.event.CreateObjectEvent;
import com.example.pengfeixie.dac.event.DeleteObjectEvent;
import com.example.pengfeixie.dac.model.CentralizedObject;
import com.example.pengfeixie.dac.model.CentralizedSubject;
import com.example.pengfeixie.dac.model.Power;
import com.example.pengfeixie.dac.presenter.ObjectPresenter;
import com.example.pengfeixie.dac.ui.adapter.SelfObjectAdapter;
import com.example.pengfeixie.dac.utils.PreferenceUtil;
import com.example.pengfeixie.dac.view.ObjectView;
import com.squareup.otto.Subscribe;

import java.util.List;

/**
 * Created by pengfeixie on 16/1/6.
 */
public class ObjectFragment extends BaseRecyclerViewMvpFragment<ObjectView, ObjectPresenter, RecyclerView> implements ObjectView {

    private TextView emptyText;

    private ImageView emptyImage;

    private RelativeLayout emptyView;

    private SelfObjectAdapter adapter;

    private CentralizedSubject subject;

    public static ObjectFragment getInstance(String name) {
        ObjectFragment fragment = new ObjectFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getInstance().register(this);
        if (getArguments() != null) {
            this.subject = RealmHelper.getInstance().getUser(getArguments().getString("name"));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
    }

    @Subscribe
    public void onCreateObjectEvent(CreateObjectEvent event) {
        adapter.clear();
        presenter.loadData();
    }

    @Subscribe
    public void onDeleteObjectEvent(DeleteObjectEvent event) {
        adapter.clear();
        presenter.loadData();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.loadData();
    }

    @Override
    protected void setupViews(View contentView) {
        emptyView = ((RelativeLayout) contentView.findViewById(R.id.empty_view));
        emptyText = ((TextView) contentView.findViewById(R.id.empty_text));
        emptyImage = ((ImageView) contentView.findViewById(R.id.empty_image));

        adapter = new SelfObjectAdapter(getActivity());
        adapter.setLongClickListener(new SelfObjectAdapter.OnObjectLongClickListener() {
            @Override
            public void longClick(View view, final Power power) {
                new MaterialDialog.Builder(getActivity())
                        .title("操作客体")
                        .autoDismiss(false)
                        .items(R.array.powers)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                switch (which) {
                                    case 0:
                                        if (power.getRights().get(1).isHas()) {
                                            Toast.makeText(dialog.getContext(), "读成功", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(dialog.getContext(), "对不起,您没有读的权限", Toast.LENGTH_LONG).show();
                                        }
                                        break;
                                    case 1:
                                        if (power.getRights().get(2).isHas()) {
                                            Toast.makeText(dialog.getContext(), "写成功", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(dialog.getContext(), "对不起,您没有写的权限", Toast.LENGTH_LONG).show();
                                        }
                                        break;
                                    case 2:
                                        if (power.getRights().get(4).isHas()) {
                                            Toast.makeText(dialog.getContext(), "添加成功", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(dialog.getContext(), "对不起,您没有添加的权限", Toast.LENGTH_LONG).show();
                                        }
                                        break;
                                    case 3:
                                        if (power.getRights().get(3).isHas()) {
                                            String objName = power.getoName();
                                            RealmHelper.getInstance().deletePower(power.getId());
                                            RealmHelper.getInstance().deleteObject(objName);
                                            BusProvider.getInstance().post(new DeleteObjectEvent());
                                            Toast.makeText(dialog.getContext(), "删除成功", Toast.LENGTH_LONG).show();
                                            dialog.dismiss();
                                        } else {
                                            Toast.makeText(dialog.getContext(), "对不起,您没有删除的权限", Toast.LENGTH_LONG).show();
                                        }
                                        break;
                                    case 4:
                                        if (power.getRights().get(5).isHas()) {
                                            Toast.makeText(dialog.getContext(), "执行成功", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(dialog.getContext(), "对不起,您没有执行的权限", Toast.LENGTH_LONG).show();
                                        }
                                        break;
                                }
                            }
                        })
                        .build().show();
            }
        });
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.addItemDecoration(new MarginDecoration(CommonUtils.convertDimenToPix(2)));
        mRecyclerView.setAdapter(adapter);
    }

    @NonNull
    @Override
    public ObjectPresenter createPresenter() {
        return new ObjectPresenter(PreferenceUtil.getPreString("user", ""));
    }

    @Override
    public void setData(List<Power> objects) {
        if (objects.size() == 0) {
            emptyView.setVisibility(View.VISIBLE);
            emptyText.setText("还没有任何客体");
        } else {
            emptyView.setVisibility(View.GONE);
            adapter.addAll(objects);
        }
    }

    @Override
    public void setError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
    }
}
