package com.kezhi.android.kezhiar.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kezhi.android.kezhiar.R;
import com.kezhi.android.kezhiar.activity.DetailActivity;
import com.kezhi.android.kezhiar.base.BaseFragment;
import com.kezhi.android.kezhiar.engine.ArCase;
import com.kezhi.android.kezhiar.engine.ArCaseLab;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: FishpondKing
 * Date: 2017/5/1:9:52
 * Email: song511653502@gmail.com
 * Description: AR模型
 */

public class ARModelFragment extends BaseFragment {
    private LinearLayout mLinearLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView mArCaseRecyclerView;
    private ArCaseAdapter mAdapter;
    private int lastVisibleItem;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private LinearLayout linearLayout1;
    private ViewGroup.LayoutParams params;
    private List<Double> mHeight=new ArrayList<Double>();
    private List<Double> rate=new ArrayList<Double>();
    public int witch;
    public WindowManager wm;
    DisplayMetrics dm=new DisplayMetrics();


    public static ARModelFragment newInstance() {
        ARModelFragment fragment = new ARModelFragment();
        return fragment;
    }

    @Override
    protected int bindLayout() {
        return R.layout.fragment_ar_model;
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        mLinearLayout=$ (view, R.id.ar_mod_fragment);
        swipeRefreshLayout=$(view, R.id.ar_case_swipeRefresh);
        mArCaseRecyclerView=$(view, R.id.ar_case_recycler_view);
        linearLayout1=$(view, R.id.linearLayout);
    }


    @Override
    protected void doBusiness(Context context) {
        wm=getActivity().getWindowManager();
        wm.getDefaultDisplay().getMetrics(dm);
        witch=dm.widthPixels/2;
        //设置下拉刷新的高度，TypedValue.applyDimension是转换为标准尺
        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        //设置Recyclerview 的布局格式
        staggeredGridLayoutManager=new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mArCaseRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        mArCaseRecyclerView.setHasFixedSize(true);
        //上拉刷新下拉加载
        refresh_updateUI();
        //更新布局
        updateUI();

    }

    // 创建ar_case_holder
    private class ArCaseHolder extends RecyclerView.ViewHolder {

        private ImageView mMainImageView;
        private ImageView mSecondImageView;
        private TextView mTextView;
        private TextView collectNum;

        private ArCase mArCase;


        //寻找视图
        public ArCaseHolder(View itemView) {
            super(itemView);

            mMainImageView = (ImageView) itemView.findViewById(R.id.iv_imageView_main);
            mMainImageView.setAdjustViewBounds(true);
            params=mMainImageView.getLayoutParams();
            params.width=witch;
            mMainImageView.setLayoutParams(params);
            mMainImageView.setMaxWidth(witch);
            mMainImageView.setMaxHeight(witch*3);

            mSecondImageView=(ImageView)itemView.findViewById(R.id.iv_imageView_second);
            mTextView= (TextView) itemView.findViewById(R.id.tv_TextView_Description);
            collectNum = (TextView) itemView.findViewById(R.id.tv_collectNum);
        }

        public void bindArCase(ArCase arCase) {
            mArCase = arCase;
            Bitmap bitmap= BitmapFactory.decodeResource(getResources(),mArCase.getArCase_main());
            rate.add( 1.00*bitmap.getHeight()/bitmap.getWidth());
            mMainImageView.setImageResource(mArCase.getArCase_main());
            mSecondImageView.setImageResource(R.drawable.ic_collcet_no_small);
            mTextView.setText(mArCase.getmTextView().toString());
            collectNum.setText(mArCase.getCollectNumTextView().toString());
        }
    }

    //创建adapter
     class ArCaseAdapter extends RecyclerView.Adapter<ArCaseHolder> implements ARModelFragment.OnRecyclerViewItemClickListener {
        private List<ArCase> mArCase;
        private ARModelFragment.OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;
        //外部传入该接口，初始化
        public ARModelFragment.OnRecyclerViewItemClickListener getOnRecyclerViewItemClickListener() {
            return onRecyclerViewItemClickListener;
        }
        public void setOnRecyclerViewItemClickListener(ARModelFragment.OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
            this.onRecyclerViewItemClickListener =onRecyclerViewItemClickListener;
        }
        public void onItemClick(View view,int position){
            //  Toast.makeText(getContext(),"您点击的Item的索引为:"+position,Toast.LENGTH_SHORT).show();

        }
        //以下为任意高度的定义
        public ArCaseAdapter(List<ArCase> arCases) {
            mArCase = arCases;
         /*   rate=new ArrayList<Integer>();
             //随机获取一个高度
            for(int i=0;i<mArCase.size();i++){
               // mHeight.add((int)(500+Math.random()*300));
                //mHeight.add(500);
                params.height=witch* rate[i];
                mHeight.add(params.height+120);
            }*/

        }


        @Override
        public ArCaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.item_short_video, parent, false);

            return new ArCaseHolder(view);
        }
        @Override//将item布局绑定在列表的行中
        public void onBindViewHolder(final ArCaseHolder holder, int position) {
            ArCase arCase = mArCase.get(position);
            holder.bindArCase(arCase);

            //绑定数据时，修改每个itemView的高度
            ViewGroup.LayoutParams lp=holder.itemView.getLayoutParams();
            mHeight.add(witch*rate.get(position));
            lp.height=(int)(mHeight.get(position)+120);
            holder.itemView.setLayoutParams(lp);
          /* ViewGroup.LayoutParams lp=holder.itemView.getLayoutParams();
            lp.height=700;
            holder.itemView.setLayoutParams(lp);*/

            //针对每个item检测是否有监听
            if(onRecyclerViewItemClickListener!=null)
            {
                holder.itemView.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        int pos =holder.getLayoutPosition();
                        onRecyclerViewItemClickListener.onItemClick(holder.itemView, pos);
                    }
                });

            }
        }

        @Override
        public int getItemCount() {
            return mArCase.size();
        }
        //添加数据
        public void addItem(ArCase arCase, int position) {
            mArCase.add(position, arCase);
            notifyItemInserted(position);//这个方法是recyclerView的adapter自带的，还有别的
            //positiom 是指在index多少添加数据
        }

        public void addMoreItem(ArCase arCase) {
            mArCase.add(arCase);
            notifyDataSetChanged();
        }
    }
    //实现上拉加载下拉刷新功能
    public void refresh_updateUI(){
        //下拉刷新功能
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //   Log.d("zttjiangqq","invoke onRefresh...");
                linearLayout1.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        //  List<TypicalCourse> mTypicalCourses = new ArrayList<>();
                        for (int i = 0; i <1; i++) {
                            int index = i + 1;
                            ArCase newShortVideo=new ArCase();
                            newShortVideo.setArCase_main(R.drawable.ic_test_image4);
                            newShortVideo.setmTextView("ARCASE"+index);
                            newShortVideo.setCollectNumTextView(""+40+index);
                            //   mTypicalCourses.add(newTypicalCourse);
                            mAdapter.addItem(newShortVideo,2);

                        }
                        //   mAdapter.addItem(mTypicalCourses);
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getContext(), "更新了数据...", Toast.LENGTH_SHORT).show();
                    }
                }, 2000);
            }
        });
        //上拉到底部进行加载
        mArCaseRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE )
                {
                    // 用来记录lastItem的position
                    // 由于瀑布流有多个列 所以此处用数组存储
                    int column = staggeredGridLayoutManager.getColumnCountForAccessibility(null,null);
                    int positions[] = new int[column];
                    staggeredGridLayoutManager.findLastVisibleItemPositions(positions);
                   /* for(int i=column-1;i>=0;i--) {
                        if(positions[i]!=0) {
                            lastVisibleItem = positions[i];
                            i=-2;
                        }
                    }*/
                    lastVisibleItem = positions[0];
                    if(lastVisibleItem + 1 ==mAdapter.getItemCount())
                    {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            linearLayout1.setVisibility(View.GONE);
                            //  List<String> newDatas = new ArrayList<String>();
                           for (int i = 0; i < 2; i++) {
                                int index = i + 1;
                                ArCase newShortVideo = new ArCase();
                                newShortVideo.setArCase_main(R.drawable.ic_test_image4);
                                newShortVideo.setmTextView("虚假信息" + index);
                                newShortVideo.setCollectNumTextView("" + 50 + index);
                                mAdapter.addMoreItem(newShortVideo);
                            }
                            Toast.makeText(getContext(), "加载了数据...", Toast.LENGTH_SHORT).show();
                        }
                    }, 3000);
                }
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView,dx, dy);
             //   lastVisibleItem =gridLayoutManager.findLastVisibleItemPosition();


            }
        });
    }

    public void updateUI() {
        ArCaseLab arCaseLab = ArCaseLab.get(getActivity());
        final List<ArCase> arCase = arCaseLab.getArCase();
        mAdapter = new ArCaseAdapter(arCase);
        mAdapter.setOnRecyclerViewItemClickListener(new ARModelFragment.OnRecyclerViewItemClickListener() {
                                                        @Override
                                                        public void onItemClick(View view, int position) {

                                                            Toast.makeText(getContext(),"ar模型:"+position,Toast.LENGTH_SHORT).show();
                                             /*              Intent intent=PLVideoTextureActivity.newIntent(getActivity(),arCase.get(position).getmId());
                                                            */
                                                            Intent intent=new Intent(getContext(), DetailActivity.class);
                                                            startActivity(intent);
                                                        }
                                                    }
        );
        mArCaseRecyclerView.setAdapter(mAdapter);
    }
    //创建类似ListView的 onItemClickListener接口
    public interface OnRecyclerViewItemClickListener{
        /**
         * Item View发生点击回调的方法
         * @param view   点击的View
         * @paramposition  具体Item View的索引
         */
        void onItemClick(View view, int position);

    }

    @Override
    public void viewClick(View v) {

    }
}
