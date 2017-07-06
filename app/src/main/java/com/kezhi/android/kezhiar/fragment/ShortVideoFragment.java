package com.kezhi.android.kezhiar.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kezhi.android.kezhiar.R;
import com.kezhi.android.kezhiar.activity.PLVideoTextureActivity;
import com.kezhi.android.kezhiar.base.BaseFragment;
import com.kezhi.android.kezhiar.engine.ShortVideo;
import com.kezhi.android.kezhiar.engine.ShortVideoLab;

import java.util.List;

/**
 * Author: FishpondKing
 * Date: 2017/5/1:9:53
 * Email: song511653502@gmail.com
 * Description: 短视频
 */

public class ShortVideoFragment extends BaseFragment {
    private LinearLayout mLinearLayout;
    private RecyclerView mShortVideoRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ShortVideoAdapter mAdapter;
    private int lastVisibleItem;
    private GridLayoutManager gridLayoutManager;
    private LinearLayout linearLayout1;

    public static ShortVideoFragment newInstance() {
        ShortVideoFragment fragment = new ShortVideoFragment();
        return fragment;
    }

    @Override
    protected int bindLayout() {
        return R.layout.fragment_short_video;
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        mLinearLayout=$ (view, R.id.short_vid_fragment);
        swipeRefreshLayout=$(view, R.id.shortVideo_swipeRefresh);
        mShortVideoRecyclerView=$(view, R.id.shortVideo_recycler_view);
        linearLayout1=$(view, R.id.linearLayout);

    }

    @Override
    protected void doBusiness(Context context) {
        //设置下拉刷新的高度，TypedValue.applyDimension是转换为标准尺
        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        //设置Recyclerview 的布局格式
        gridLayoutManager=new GridLayoutManager(getActivity(),2);
        mShortVideoRecyclerView.setLayoutManager(gridLayoutManager);
      //  mShortVideoRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),gridLayoutManager.getOrientation()));
        mShortVideoRecyclerView.setHasFixedSize(true);
        //上拉刷新下拉加载
        refresh_updateUI();
        //更新布局
        updateUI();
    }
    // 创建ar_case_holder
    private class ShortVideoHolder extends RecyclerView.ViewHolder {

        private ImageView mMainImageView;
        private ImageView mSecondImageView;
        private TextView mTextView;
        private TextView collectNum;
        private ShortVideo mShortVideo;


        //寻找视图
        public ShortVideoHolder(View itemView) {
            super(itemView);
            mMainImageView = (ImageView) itemView.findViewById(R.id.iv_imageView_main);
            mMainImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            ViewGroup.LayoutParams params=mMainImageView.getLayoutParams();
            params.height=400;
            mMainImageView.setLayoutParams(params);
            mSecondImageView=(ImageView)itemView.findViewById(R.id.iv_imageView_second);
            mTextView= (TextView) itemView.findViewById(R.id.tv_TextView_Description);
            collectNum = (TextView) itemView.findViewById(R.id.tv_collectNum);

        }
        public void bindShortVideo(ShortVideo shortVideo) {

            mShortVideo=shortVideo;
            mMainImageView.setImageResource(mShortVideo.getVideoView_main());
            mSecondImageView.setImageResource(R.drawable.ic_collcet_no_small);
            mTextView.setText(mShortVideo.getmTextView());
            collectNum.setText(""+mShortVideo.getCollectNumber());

        }
    }
    //创建adapter
    class ShortVideoAdapter extends RecyclerView.Adapter<ShortVideoHolder> implements OnRecyclerViewItemClickListener {
        private List<ShortVideo> mShortVideo;

        private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;
        //外部传入该接口，初始化
        public OnRecyclerViewItemClickListener getOnRecyclerViewItemClickListener() {
            return onRecyclerViewItemClickListener;
        }
        public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
            this.onRecyclerViewItemClickListener =onRecyclerViewItemClickListener;
        }
        public void onItemClick(View view,int position){
            //  Toast.makeText(getContext(),"您点击的Item的索引为:"+position,Toast.LENGTH_SHORT).show();
        }

        public ShortVideoAdapter(List<ShortVideo> shortVideo) {

            mShortVideo= shortVideo;
        }

        @Override
        public ShortVideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.item_short_video, parent, false);
           /* view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onRecyclerViewItemClickListener!=null){
                        onRecyclerViewItemClickListener.onItemClick(v,(int)v.getTag());
                    }
                }
            });*/
            ShortVideoHolder vh = new ShortVideoHolder(view);
            return  vh;
        }

        @Override//将item布局绑定在列表的行中
        public void onBindViewHolder(final ShortVideoFragment.ShortVideoHolder holder, int position) {
            ShortVideo shortVideo = mShortVideo.get(position);
            //holder.itemView.setTag(position);
            holder.bindShortVideo(shortVideo);

            //绑定数据时，修改每个itemView的高度

            ViewGroup.LayoutParams lp=holder.itemView.getLayoutParams();
            lp.height=500;
            holder.itemView.setLayoutParams(lp);

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
            return mShortVideo.size();
        }

        //添加数据
        public void addItem(ShortVideo shortVideo, int position) {
            mShortVideo.add(position, shortVideo);
            notifyItemInserted(position);//这个方法是recyclerView的adapter自带的，还有别的
            //positiom 是指在index处添加数据
        }

        public void addMoreItem(ShortVideo shortVideo) {
            mShortVideo.add(shortVideo);
            notifyDataSetChanged();
        }
    }

    @Override
    public void viewClick(View v) {

    }

    //实现上拉加载下拉刷新功能
    public void refresh_updateUI(){
        //下拉刷新功能
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                linearLayout1.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //  List<TypicalCourse> mTypicalCourses = new ArrayList<>();
                        for (int i = 0; i <2; i++) {
                            int index = i + 1;
                            ShortVideo newShortVideo=new ShortVideo();
                            newShortVideo.setVideoView_main(R.drawable.test_surf);
                            newShortVideo.setmTextView("虚假信息"+index);
                            newShortVideo.setCollectNumber(40+index);
                            //   mTypicalCourses.add(newTypicalCourse);
                            mAdapter.addItem(newShortVideo,2);

                        }
                        //   mAdapter.addItem(mTypicalCourses);
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getContext(), "更新了2条数据...", Toast.LENGTH_SHORT).show();
                    }
                }, 2000);
            }
        });
        //上拉到底部进行加载
        mShortVideoRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                linearLayout1.setVisibility(View.GONE);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 ==mAdapter.getItemCount()) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //  List<String> newDatas = new ArrayList<String>();
                            for (int i = 0; i< 2; i++) {
                                int index = i +1;
                                ShortVideo newShortVideo=new ShortVideo();
                                newShortVideo.setVideoView_main(R.drawable.ic_test_image4);
                                newShortVideo.setmTextView("虚假信息"+index);
                                newShortVideo.setCollectNumber(50+index);
                                mAdapter.addMoreItem(newShortVideo);
                            }
                        }
                    },3000);
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView,dx, dy);
                lastVisibleItem =gridLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    public void updateUI(){
        ShortVideoLab shortVideoLab=ShortVideoLab.get(getActivity());
        final List<ShortVideo> shortVideos=shortVideoLab.getShortVideo();
        mAdapter = new ShortVideoFragment.ShortVideoAdapter(shortVideos);
        mAdapter.setOnRecyclerViewItemClickListener(new OnRecyclerViewItemClickListener() {
                                                        @Override
                                                        public void onItemClick(View view, int position) {

                                                            Toast.makeText(getContext(),"您点击的Item的索引为:"+position,Toast.LENGTH_SHORT).show();
                                                            Intent intent=PLVideoTextureActivity.newIntent(getActivity(),shortVideos.get(position).getmId());
                                                            startActivity(intent);
                                                        }
                                                    }
        );
        mShortVideoRecyclerView.setAdapter(mAdapter);
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
}
