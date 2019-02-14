package main.rxjava2super.main;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.ArrayList;
import java.util.List;

import main.rxjava2super.main.beans.NewsEntity;

@Route(path = ARouterPath.RX_RETROFIT)
public class RxRetrofitActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<NewsEntity.ResultsBean> mResultsBeanList;
    private RxRetrofitAdapter mRxRetrofitAdapter;
    private SwipeRefreshLayout refreshLayout;
    private int currentPage = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_retrofit);
        refreshLayout = findViewById(R.id.srl_LoadRefresh);
        mRecyclerView = findViewById(R.id.rv_list);
        mResultsBeanList = new ArrayList<>();
        mRxRetrofitAdapter = new RxRetrofitAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        mRecyclerView.setAdapter(mRxRetrofitAdapter);

        RxRetrofitClass.getInstance().refreshArticle(currentPage, mRxRetrofitAdapter, mResultsBeanList, refreshLayout);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                RxRetrofitClass.getInstance().refreshArticle(++currentPage, mRxRetrofitAdapter, mResultsBeanList, refreshLayout);
            }
        });
    }

    private class RxRetrofitAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @SuppressLint("InflateParams")
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ItemVieHolder(LayoutInflater.from(RxRetrofitActivity.this).inflate(R.layout.item_rxretrofit_layout, null));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            if (viewHolder instanceof ItemVieHolder) {
                if (!TextUtils.isEmpty(mResultsBeanList.get(i).getType())) {
                    ((ItemVieHolder) viewHolder).acTvCategory.setText(mResultsBeanList.get(i).getType());
                }
                if (!TextUtils.isEmpty(mResultsBeanList.get(i).getDesc())) {
                    ((ItemVieHolder) viewHolder).acTvDesc.setText(mResultsBeanList.get(i).getDesc());
                }
            }
        }

        @Override
        public int getItemCount() {
            return mResultsBeanList.size() > 0 ? mResultsBeanList.size() : 0;
        }

        public class ItemVieHolder extends RecyclerView.ViewHolder {

            private AppCompatTextView acTvCategory, acTvDesc;

            public ItemVieHolder(@NonNull View itemView) {
                super(itemView);
                acTvCategory = itemView.findViewById(R.id.acTv_Category);
                acTvDesc = itemView.findViewById(R.id.acTv_Desc);
            }
        }

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxRetrofitClass.getInstance().destroy();
    }
}
