
package com.kezhi.android.kezhiar.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kezhi.android.kezhiar.R;
import com.kezhi.android.kezhiar.engine.ShortVideo;
import com.kezhi.android.kezhiar.engine.ShortVideoLab;
import com.kezhi.android.kezhiar.engine.Utils;
import com.kezhi.android.kezhiar.utils.FileUtil;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLMediaPlayer;
import com.pili.pldroid.player.widget.PLVideoTextureView;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 *  This is a demo activity of PLVideoTextureView
 */
public class PLVideoTextureActivity extends VideoPlayerBaseActivity {

    private static final int MESSAGE_ID_RECONNECTING = 0x01;
    private MediaController mMediaController;
    private PLVideoTextureView mVideoView;
    private Toast mToast = null;
    private String mVideoPath = null;
    private int mDisplayAspectRatio = PLVideoTextureView.ASPECT_RATIO_PAVED_PARENT; //default
    private View mLoadingView;
    private View mCoverView = null;
    private boolean mIsActivityPaused = true;
    private int mIsLiveStreaming = 1;
    private TextView tv_videoname;
    private Button btn_pldvideo_collect;
    private ShortVideo mShortVideo;
    private DisplayMetrics dm=new DisplayMetrics();
    private int videoHeight;
    private int isColor=1;
    ViewGroup.LayoutParams params;

    private FileUtil fileUtil;
    private  Uri uri;
    private TextView lookNumText;
    private TextView collectNumText;
    private TextView videoDetail;



    private void setOptions(int codecType) {
        AVOptions options = new AVOptions();

        // the unit of timeout is ms
        options.setInteger(AVOptions.KEY_PREPARE_TIMEOUT, 10 * 1000);
        options.setInteger(AVOptions.KEY_GET_AV_FRAME_TIMEOUT, 10 * 1000);
        options.setInteger(AVOptions.KEY_PROBESIZE, 128 * 1024);
        // Some optimization with buffering mechanism when be set to 1
        options.setInteger(AVOptions.KEY_LIVE_STREAMING, mIsLiveStreaming);
        if (mIsLiveStreaming == 1) {
            options.setInteger(AVOptions.KEY_DELAY_OPTIMIZATION, 1);
        }

        // 1 -> hw codec enable, 0 -> disable [recommended]
        options.setInteger(AVOptions.KEY_MEDIACODEC, codecType);

        // whether start play automatically after prepared, default value is 1
        options.setInteger(AVOptions.KEY_START_ON_PREPARED, 0);

        mVideoView.setAVOptions(options);
    }
    // pass params between this and shortVideoFragment.java
    public static final String extra_id="com.kezhi.android.kezhiar.engine.id";
    public static Intent newIntent(Context packageContext, UUID id){
        Intent intent =new Intent(packageContext,PLVideoTextureActivity.class);
        intent.putExtra(extra_id,id);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pl_video_texture);
        //获取相应的短视频
        UUID shortVideoId=(UUID)getIntent().getSerializableExtra(extra_id);
        mShortVideo= ShortVideoLab.get(this).getShortVideo(shortVideoId);


       // 视频模块的加载，初始化
        mVideoView = (PLVideoTextureView) findViewById(R.id.VideoView);
        mLoadingView = findViewById(R.id.LoadingView);
        mVideoView.setBufferingIndicator(mLoadingView);
        mLoadingView.setVisibility(View.VISIBLE);
        mVideoView.setDisplayOrientation(0);
        mCoverView = (ImageView) findViewById(R.id.iv_pl_video_texture_CoverView);
        mVideoView.setCoverView(mCoverView);

        // 自己定义视频路径
        //mVideoPath="rtmp://live.hkstv.hk.lxdns.com/live/hks";
         mVideoPath="storage/emulated/0/DCIM/Camera/VID20161220202706.mp4";
        //isInternalStorageAvaliable();

      /*
        存储视频文件到外部存储
      String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)) {
            videoPath = fileUtil.getExternalFileDir(this,"ShortVideo_File");
        }


        File myfile=new File(videoPath,"han.mp4");
        try {
            myfile.createNewFile();
            FileOutputStream fos=new FileOutputStream(myfile);
            OutputStreamWriter osw=new OutputStreamWriter(fos);
            osw.write(inputStream2String(getResources().openRawResource(R.raw.arkanche)));
            osw.flush();
            osw.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

      //  mVideoPath="/storage/emulated/0/Android/data/com.kezhi.android.kezhiar/files/ShortVideo_File/han.mp4";
      //  mVideoPath="android.resource://" + getPackageName() + "/" + R.raw.arkanche;
      //  uri=Uri.parse("android.resource://"+getPackageName()+"/"+han);

        mIsLiveStreaming=1;//直播
        // 1 -> hw codec enable, 0 -> disable [recommended]
        int codec = getIntent().getIntExtra("mediaCodec", AVOptions.MEDIA_CODEC_SW_DECODE);
        setOptions(codec);
        // You can mirror the display
        // mVideoView.setMirror(true);

        //use a custom `MediaController` widget
        mMediaController = new MediaController(this, true, mIsLiveStreaming==0);

        //MediaController 's height setting
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        videoHeight=mVideoView.getLayoutParams().height;
        int statuBarHeight=0;
        int statuBarId=getResources().getIdentifier("status_bar_height","dimen","android");
        if (statuBarId>0){
            statuBarHeight=getResources().getDimensionPixelSize(statuBarId);
        }
        if(getResources().getConfiguration().orientation==Configuration.ORIENTATION_PORTRAIT){
            mMediaController.setPadding(0,0,0,dm.heightPixels-videoHeight);
            Log.e("sdfafd","  "+statuBarHeight);
         //   mMediaController.setPadding(0,videoHeight,0,0);
        }
        else
        {
            mMediaController.setPadding(0,0,0,0);
        }

        //VIDEO SETTING
        mVideoView.setMediaController(mMediaController);
        mVideoView.setOnCompletionListener(mOnCompletionListener);
        mVideoView.setOnErrorListener(mOnErrorListener);
        mVideoView.setDisplayAspectRatio(mDisplayAspectRatio);

       // mVideoView.setVideoURI(uri);
        mVideoView.setVideoPath(mVideoPath);
        mVideoView.start();



        //视频播放名称
        tv_videoname=(TextView)findViewById(R.id.tv_PLVideoTexture_videoName);
        tv_videoname.setText(mShortVideo.getmTextView());
        //收藏按钮
        btn_pldvideo_collect=(Button)findViewById(R.id.btn_PLVideotexture_collect);
        btn_pldvideo_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isColor%2==1) {
                    btn_pldvideo_collect.setTextColor(getResources().getColor(R.color.green_light));
                    //  collectView.setImageResource(R.drawable.collect_yes_small);
                    isColor++;
                    mShortVideo.setCollectNumber(mShortVideo.getCollectNumber()+1);
                }
                else
                {
                    btn_pldvideo_collect.setTextColor(getResources().getColor(R.color.black ));
                    //  collectView.setImageResource(R.drawable.collect_yes_small);
                    isColor++;
                    mShortVideo.setCollectNumber(mShortVideo.getCollectNumber()-1);
                }
            }

        });
        //浏览次数设置
        lookNumText=(TextView)this.findViewById(R.id.tv_plvideoTexture_browseNum);
        lookNumText.setText(""+(mShortVideo.getLookNum()+1));
        if(getResources().getConfiguration().orientation==Configuration.ORIENTATION_PORTRAIT) {
            mShortVideo.setLookNum(mShortVideo.getLookNum()+1);
        }
        //收藏视图显示
        collectNumText=(TextView)findViewById(R.id.tv_plvideoTexture_collectNum);
        collectNumText.setText(""+mShortVideo.getCollectNumber());

        // 视频详情
        videoDetail=(TextView)findViewById(R.id.tv_plvideoTexture_videoDescription);
        String str= null;
        try {
            str = inputStream2String(getResources().openRawResource(mShortVideo.getVideoDescriptionId()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        videoDetail.setText(str);
    }

  public   String   inputStream2String   (InputStream in)   throws IOException {
        StringBuffer   out   =   new   StringBuffer();
        byte[]   b   =   new   byte[4096];
        for   (int   n;   (n   =   in.read(b))   !=   -1;)   {
            out.append(new   String(b,   0,   n));
        }
        return   out.toString();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mToast = null;
        mVideoView.pause();
        mIsActivityPaused = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mIsActivityPaused = false;
        mVideoView.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVideoView.stopPlayback();
    }

    // back click setting
    public void onClickBack(View v){
        if(getResources().getConfiguration().orientation==Configuration.ORIENTATION_PORTRAIT) {
            finish();
        }
        else
        {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    //full screen setting
    public void onFullScreen(View v){
        if ( this!= null) {
                           switch (getResources().getConfiguration().orientation) {
                                   case Configuration.ORIENTATION_LANDSCAPE://横屏
                                   {
                                       setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                                       break;
                                   }
                        case Configuration.ORIENTATION_PORTRAIT://竖屏
                        {
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                            break;
                        }
                      }

                      }
    }

    /*
    以下为视频预览格式、视频方向切换
    public void onClickRotate(View v) {
        mRotation = (mRotation + 90) % 360;
        mVideoView.setDisplayOrientation(mRotation);

    }
    public void onClickSwitchScreen(View v) {
       mDisplayAspectRatio = (mDisplayAspectRatio + 1) % 5;
        mVideoView.setDisplayAspectRatio(mDisplayAspectRatio);
        switch (mVideoView.getDisplayAspectRatio()) {
           case PLVideoTextureView.ASPECT_RATIO_ORIGIN:
                showToastTips("Origin mode"+mDisplayAspectRatio+"    "+mVideoView.getDisplayAspectRatio());//0
                break;
            case PLVideoTextureView.ASPECT_RATIO_FIT_PARENT:
                showToastTips("Fit parent !"+mDisplayAspectRatio+"    "+mVideoView.getDisplayAspectRatio());//1
                break;
            case PLVideoTextureView.ASPECT_RATIO_PAVED_PARENT:
            {
                showToastTips("Paved parent !"+mDisplayAspectRatio+"    "+mVideoView.getDisplayAspectRatio());//2
                break;}
           case PLVideoTextureView.ASPECT_RATIO_16_9:
                showToastTips("16 : 9 !"+mDisplayAspectRatio+"    "+mVideoView.getDisplayAspectRatio());//3
                break;
            case PLVideoTextureView.ASPECT_RATIO_4_3:
                showToastTips("4 : 3 !"+mDisplayAspectRatio+"    "+mVideoView.getDisplayAspectRatio());//4
                break;
            default:
                break;
        }
    }*/


    private PLMediaPlayer.OnErrorListener mOnErrorListener = new PLMediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(PLMediaPlayer mp, int errorCode) {
            boolean isNeedReconnect = false;
            switch (errorCode) {
                case PLMediaPlayer.ERROR_CODE_INVALID_URI:
                    showToastTips("Invalid URL !");
                    break;
                case PLMediaPlayer.ERROR_CODE_404_NOT_FOUND:
                    showToastTips("404 resource not found !");
                    break;
                case PLMediaPlayer.ERROR_CODE_CONNECTION_REFUSED:
                    showToastTips("Connection refused !");
                    break;
                case PLMediaPlayer.ERROR_CODE_CONNECTION_TIMEOUT:
                    showToastTips("Connection timeout !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_EMPTY_PLAYLIST:
                    showToastTips("Empty playlist !");
                    break;
                case PLMediaPlayer.ERROR_CODE_STREAM_DISCONNECTED:
                    showToastTips("Stream disconnected !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_IO_ERROR:
                    showToastTips("Network IO Error !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_UNAUTHORIZED:
                    showToastTips("Unauthorized Error !");
                    break;
                case PLMediaPlayer.ERROR_CODE_PREPARE_TIMEOUT:
                    showToastTips("Prepare timeout !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_READ_FRAME_TIMEOUT:
                    showToastTips("Read frame timeout !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_HW_DECODE_FAILURE:
                    setOptions(AVOptions.MEDIA_CODEC_SW_DECODE);
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.MEDIA_ERROR_UNKNOWN:
                    break;
                default:
                    showToastTips("unknown error !");
                    break;
            }
            // Todo pls handle the error status here, reconnect or call finish()
            if (isNeedReconnect) {
                sendReconnectMessage();
            } else {
                finish();
            }
            // Return true means the error has been handled
            // If return false, then `onCompletion` will be called
            return true;
        }
    };


    private PLMediaPlayer.OnCompletionListener mOnCompletionListener = new PLMediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(PLMediaPlayer plMediaPlayer) {
            showToastTips("Play Completed !");
            finish();
        }
    };

    private void showToastTips(final String tips) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mToast != null) {
                    mToast.cancel();
                }
                mToast = Toast.makeText(PLVideoTextureActivity.this, tips, Toast.LENGTH_SHORT);
                mToast.show();
            }
        });
    }

    private void sendReconnectMessage() {
        showToastTips("正在重连...");
        mLoadingView.setVisibility(View.VISIBLE);
        mHandler.removeCallbacksAndMessages(null);
        mHandler.sendMessageDelayed(mHandler.obtainMessage(MESSAGE_ID_RECONNECTING), 500);
    }

    protected Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what != MESSAGE_ID_RECONNECTING) {
                return;
            }
            if (mIsActivityPaused || !Utils.isLiveStreamingAvailable()) {
                finish();
                return;
            }
            if (!Utils.isNetworkAvailable(PLVideoTextureActivity.this)) {
                sendReconnectMessage();
                return;
            }
            mVideoView.setVideoPath(mVideoPath);
            mVideoView.start();
        }
    };
}
