package com.kezhi.android.kezhiar.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
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
import com.kezhi.android.kezhiar.activity.DetailActivity;
import com.kezhi.android.kezhiar.base.BaseFragment;
import com.kezhi.android.kezhiar.engine.DividerItemDecoration;
import com.kezhi.android.kezhiar.engine.TypicalCourse;
import com.kezhi.android.kezhiar.engine.TypicalCourseLab;

import java.util.List;

/**
 * Author: FishpondKing
 * Date: 2017/5/1:9:53
 * Email: song511653502@gmail.com
 * Description: 精品课堂
 */

public class HighQualityClassFragment extends BaseFragment {
    private LinearLayout mLinearLayout;
    private RecyclerView mTypicalCourseRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TypicalCourseAdapter mAdapter;
    private int lastVisibleItem;
    private LinearLayoutManager linearLayoutManager;
    private LinearLayout linearLayout1;

    public static HighQualityClassFragment newInstance() {
        HighQualityClassFragment fragment = new HighQualityClassFragment();
        return fragment;
    }

    @Override
    protected int bindLayout() {
        return R.layout.fragment_high_quality_class;
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        mLinearLayout=$ (view, R.id.high_qua_fragment);
        swipeRefreshLayout=$(view, R.id.typical_course_swipeRefresh);
        mTypicalCourseRecyclerView=$(view, R.id.typical_course_recycler_view);
        linearLayout1=$(view, R.id.linearLayout);
    }

    @Override
    protected void doBusiness(Context context) {
        //设置下拉刷新的高度，TypedValue.applyDimension是转换为标准尺
        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        //设置Recyclerview 的布局格式
        linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mTypicalCourseRecyclerView.setLayoutManager(linearLayoutManager);
       // mTypicalCourseRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),linearLayoutManager.getOrientation()));
        mTypicalCourseRecyclerView.setHasFixedSize(true);
        //上拉刷新下拉加载
        refresh_updateUI();
        //更新布局
        updateUI();

    }
    // 创建ar_case_holder
    private class TypicalCourseHolder extends RecyclerView.ViewHolder {

        private ImageView mMainImageView;
        private ImageView mSecondImageView;
        private ImageView mGeLi;
        private TextView mTextView;
        private TextView collectNum;
        private TypicalCourse mTypicalCourse;

        //寻找视图
        public TypicalCourseHolder(View itemView) {
            super(itemView);
            mMainImageView = (ImageView) itemView.findViewById(R.id.iv_imageView_main2);
            mMainImageView.setScaleType(ImageView.ScaleType.FIT_XY );
            ViewGroup.LayoutParams params=mMainImageView.getLayoutParams();
            params.height=450;
            mMainImageView.setLayoutParams(params);
            mSecondImageView=(ImageView)itemView.findViewById(R.id.iv_imageView_second2);
            mGeLi=(ImageView)itemView.findViewById(R.id.iv_geli);
            mTextView= (TextView) itemView.findViewById(R.id.tv_TextView_Description2);
            collectNum = (TextView) itemView.findViewById(R.id.tv_collectNum2);

        }


        //
        public void bindTypicalCourse(TypicalCourse typicalCourse) {
            mTypicalCourse = typicalCourse;
            mMainImageView.setImageResource(mTypicalCourse.getTypicalCourse_main());
            mSecondImageView.setImageResource(R.drawable.ic_collcet_no_small);
            mGeLi.setImageResource(R.drawable.ic_highquality_geli);
            mTextView.setText(mTypicalCourse.getmTextView());
            collectNum.setText(mTypicalCourse.getCollectNumTextView());

        }
    }

    //创建adapter
    private class TypicalCourseAdapter extends RecyclerView.Adapter<TypicalCourseHolder>implements OnRecyclerViewItemClickListener {
        private List<TypicalCourse> mTypicalCourse;

        private ShortVideoFragment.OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;

        //外部传入该接口，初始化
        public ShortVideoFragment.OnRecyclerViewItemClickListener getOnRecyclerViewItemClickListener() {
            return onRecyclerViewItemClickListener;
        }

        public void setOnRecyclerViewItemClickListener(ShortVideoFragment.OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
            this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
        }

        public void onItemClick(View view, int position) {
            //  Toast.makeText(getContext(),"您点击的Item的索引为:"+position,Toast.LENGTH_SHORT).show();
        }

        public TypicalCourseAdapter(List<TypicalCourse> typicalCourse) {
            mTypicalCourse = typicalCourse;
        }

        @Override
        public TypicalCourseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.item_highqualityclassfragment, parent, false);
            return new TypicalCourseHolder(view);
        }

        @Override//将item布局绑定在列表的行中
        public void onBindViewHolder(final TypicalCourseHolder holder, int position) {
            TypicalCourse typicalCourse = mTypicalCourse.get(position);
            holder.bindTypicalCourse(typicalCourse);

            //绑定数据时，修改每个itemView的高度
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            lp.height = 450;
            holder.itemView.setLayoutParams(lp);

            if (onRecyclerViewItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getLayoutPosition();
                        onRecyclerViewItemClickListener.onItemClick(holder.itemView, pos);
                    }
                });
            }
        }


        @Override
        public int getItemCount() {
            return mTypicalCourse.size();
        }

        //添加数据
        public void addItem(TypicalCourse typicalCourse, int position) {
            mTypicalCourse.add(position, typicalCourse);
            notifyItemInserted(position);//这个方法是recyclerView的adapter自带的，还有别的
            //positiom 是指在index多少添加数据
        }

        public void addMoreItem(TypicalCourse typicalCourse) {
            mTypicalCourse.add(typicalCourse);
            notifyDataSetChanged();
        }
    }

    private void updateUI() {
        TypicalCourseLab typicalCourseLab=TypicalCourseLab.get(getActivity());
        final List<TypicalCourse> typicalCourses=typicalCourseLab.getTypicalCourse();
        mAdapter = new TypicalCourseAdapter(typicalCourses);
        mAdapter.setOnRecyclerViewItemClickListener(new ShortVideoFragment.OnRecyclerViewItemClickListener() {
                                                        @Override
                                                        public void onItemClick(View view, int position) {

                                                            Toast.makeText(getContext(),"精品课堂:"+position,Toast.LENGTH_SHORT).show();
                                                           /* Intent intent=PLVideoTextureActivity.newIntent(getActivity(),typicalCourses.get(position).getmId());
                                                            startActivity(intent);*/
                                                            Intent intent=new Intent(getContext(), DetailActivity.class);
                                                            startActivity(intent);
                                                        }
                                                    }
        );

        mTypicalCourseRecyclerView.setAdapter(mAdapter);
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
                        for (int i = 0; i <2; i++) {
                            int index = i + 1;
                            TypicalCourse newShortVideo=new TypicalCourse();
                            newShortVideo.setTypicalCourse_main(R.drawable.ic_test_image4);
                            newShortVideo.setmTextView("虚假信息"+index);
                            newShortVideo.setCollectNumTextView(""+40+index);
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
        mTypicalCourseRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                                TypicalCourse newTypicalCourse=new TypicalCourse();
                                newTypicalCourse.setTypicalCourse_main(R.drawable.ic_test_image4);
                                newTypicalCourse.setmTextView("虚假信息"+index);
                                newTypicalCourse.setCollectNumTextView(""+50+index);
                                mAdapter.addMoreItem(newTypicalCourse);
                            }
                        }
                    },3000);
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView,dx, dy);
                lastVisibleItem =linearLayoutManager.findLastVisibleItemPosition();
            }
        });
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
