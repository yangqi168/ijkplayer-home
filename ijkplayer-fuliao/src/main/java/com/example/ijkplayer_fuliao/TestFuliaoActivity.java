package com.example.ijkplayer_fuliao;

import android.content.Context;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;

import tv.danmaku.ijk.media.example.widget.media.IjkVideoView;
import tv.danmaku.ijk.media.example.widget.media.VideoView;
import tv.danmaku.ijk.media.example.widget.media.YQIJKAVPlayer;
import tv.danmaku.ijk.media.example.widget.media.YQVideoViewPlayer;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
//import tv.danmaku.ijk.media.player.IMediaPlayer;
//import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class TestFuliaoActivity extends AppCompatActivity {
    Context mContext;
    boolean isAudioMute = false;
    Button mbtMute;
     //三个IjkVideoView用于显示控件
    IjkVideoView mYQVideoSurfaceView1;
    IjkVideoView mYQVideoSurfaceView2;
    IjkVideoView mYQVideoSurfaceView3;

    YQIJKAVPlayer mIjkPlayer1 = null;
    YQIJKAVPlayer mIjkPlayer2 = null;
    YQIJKAVPlayer mIjkPlayer3 = null;

    private View mBufferingIndicator;
    private MediaController mMediaController;
    private ImageView mImageView1;
    private ImageView mImageView2;
    private ImageView mImageView3;
    private String mDefaultTestUrl = "rtmp://live.hkstv.hk.lxdns.com/live/hks";
    // "http://imgtest.ipaychat.com/photos/video/2017/11/14/e662cbc61de944799b4531dcd47f2d75.mp4";
        //rtmp://pili-live-rtmp.ipaychat.com/fuliao-live/666666";  //"rtmp://live.hkstv.hk.lxdns.com/live/hks";

//    IjkVideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_fuliao);
        mContext = TestFuliaoActivity.this;
              //TestDemo1 --测试VideoView方式实现播放
//        videoView = (VideoView) findViewById(R.id.ijk_video_view1);
//
//        mBufferingIndicator = findViewById(R.id.buffering_indicator);
//        mMediaController = new MediaController(this);
//
//        // init player
//        IjkMediaPlayer.loadLibrariesOnce(null);
//        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
//        videoView.setVideoURI(Uri.parse("rtmp://live.hkstv.hk.lxdns.com/live/hks")); //rtmp://pili-live-rtmp.ipaychat.com/fuliao-live/666666
//
//        videoView.setMediaController(mMediaController);
//        videoView.setMediaBufferingIndicator(mBufferingIndicator);
//        videoView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(IMediaPlayer mp) {
//                videoView.requestFocus();
//                videoView.start();
//            }
//        });

        //TestDemo2--IJKVideoView方式显示播放，对应xml配置需更改
//        IjkVideoView  mIjkVideoView = (IjkVideoView) findViewById(R.id.ijk_video_view1);
//        YQIJKAVPlayer mIjkPlayer = new YQIJKAVPlayer(TestFuliaoActivity.this,mIjkVideoView,"rtmp://live.hkstv.hk.lxdns.com/live/hks",1);
////        YQIJKAVPlayer mIjkPlayer = new YQIJKAVPlayer(TestFuliaoActivity.this,mIjkVideoView,"rtmp://pili-live-rtmp.ipaychat.com/fuliao-live/666666",1);
//        mIjkPlayer.setOnVideoRenderingShow(new YQIJKAVPlayer.OnVideoRenderingShow() {
//            @Override
//            public void playerPrepared(int palyerIndex) {
//                Log.d(TestFuliaoActivity.this.toString(),"playerPrepared,palyerIndex"+palyerIndex);
//            }
//
//            @Override
//            public void startRender(int playerIndex) {
//                Log.d(TestFuliaoActivity.this.toString(),"startRender,playerIndex"+playerIndex);
////                mImageView1.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void playerError(int playerIndex) {
//                Log.d(TestFuliaoActivity.this.toString(),"playerError,playerIndex"+playerIndex);
//
//            }
//
//            @Override
//            public void reload(int playerIndex) {
//                Log.d(TestFuliaoActivity.this.toString(),"reload,playerIndex"+playerIndex);
//
//            }
//        });
//        mIjkPlayer.startPullStream();


        //TestDemo3--VideoView封装成YQVideoViewPlayer之后做多窗口测试
        mbtMute = (Button)findViewById(R.id.btMute);
        mbtMute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isAudioMute){
                    isAudioMute = true;
                    mbtMute.setText("关闭静音");
                }else {
                    isAudioMute = false;
                    mbtMute.setText("开启静音");
                }
                if(mIjkPlayer1 != null){
                    mIjkPlayer1.setMuted(isAudioMute);
                }
                if(mIjkPlayer2 != null){
                    mIjkPlayer2.setMuted(isAudioMute);
                }
                if(mIjkPlayer3 != null){
                    mIjkPlayer3.setMuted(isAudioMute);
                }
            }
        });
        mYQVideoSurfaceView1 = (IjkVideoView) findViewById(R.id.ijk_video_view1);
        mYQVideoSurfaceView2 = (IjkVideoView) findViewById(R.id.ijk_video_view2);
        mYQVideoSurfaceView3 = (IjkVideoView) findViewById(R.id.ijk_video_view3);
        mImageView1 = (ImageView)findViewById(R.id.iv_pull_player_no_surface1_animation1);
        mImageView2 = (ImageView)findViewById(R.id.iv_pull_player_no_surface1_animation2);
        mImageView3 = (ImageView)findViewById(R.id.iv_pull_player_no_surface1_animation3);
        String testUrl = "rtmp://live.hkstv.hk.lxdns.com/live/hks";

        Message msg = new Message();
        msg.arg1 = -1;
        msg.obj = mDefaultTestUrl;
        mGetSingleVideoPairListHandler.sendMessage(msg);
//        initAVPlayer(-1,testUrl);

//        mIjkPlayer1 = new YQIJKAVPlayer(TestFuliaoActivity.this,mYQVideoSurfaceView1,"rtmp://live.hkstv.hk.lxdns.com/live/hks",1);
//        mIjkPlayer1.setOnVideoRenderingShow(mYQOnVideoRenderingShow);
//        mIjkPlayer2 = new YQIJKAVPlayer(TestFuliaoActivity.this,mYQVideoSurfaceView2,"rtmp://live.hkstv.hk.lxdns.com/live/hks",2);
//        mIjkPlayer2.setOnVideoRenderingShow(mYQOnVideoRenderingShow);
//        mIjkPlayer3 = new YQIJKAVPlayer(TestFuliaoActivity.this,mYQVideoSurfaceView3,"rtmp://live.hkstv.hk.lxdns.com/live/hks",3);
//        mIjkPlayer3.setOnVideoRenderingShow(mYQOnVideoRenderingShow);
//        mYQVideoSurfaceView1.setVisibility(View.VISIBLE);
//        mIjkPlayer1.startPullStream();
//        mYQVideoSurfaceView2.setVisibility(View.VISIBLE);
//        mIjkPlayer2.startPullStream();
//        mYQVideoSurfaceView3.setVisibility(View.VISIBLE);
//        mIjkPlayer3.startPullStream();

    }

    YQIJKAVPlayer.OnVideoRenderingShow mYQOnVideoRenderingShow = new YQIJKAVPlayer.OnVideoRenderingShow() {
        @Override
        public void playerPrepared(int palyerIndex) {
            Log.d(TestFuliaoActivity.this.toString(),"playerPrepared,palyerIndex"+palyerIndex);
        }

        @Override
        public void startRender(int playerIndex) {
            switch (playerIndex){
                case 1:
                    mImageView1.setVisibility(View.GONE);
                    mYQVideoSurfaceView1.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    mImageView2.setVisibility(View.GONE);
                    mYQVideoSurfaceView2.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    mImageView3.setVisibility(View.GONE);
                    mYQVideoSurfaceView3.setVisibility(View.VISIBLE);
                    break;
                default:
                        break;
            }
        }

        @Override
        public void playerError(int playerIndex) {
            destroyAVPlayer(playerIndex,true);
        }

        @Override
        public void reload(int playerIndex) {
            Log.d(TestFuliaoActivity.this.toString(),"reload,playerIndex"+playerIndex);

        }
    };

    private void initAVPlayer(int playerIndex,String streamurl){
        switch (playerIndex){
            case 1:
                if(mIjkPlayer1 == null){
                    mYQVideoSurfaceView1.setVisibility(View.INVISIBLE);
                    mIjkPlayer1 = new YQIJKAVPlayer(mContext, mYQVideoSurfaceView1,streamurl , 1);
                    mIjkPlayer1.setAVReadTimeout(10);
                    mIjkPlayer1.setOnVideoRenderingShow(mYQOnVideoRenderingShow);
//                    mIjkPlayer1.setMuted(true);
                    mIjkPlayer1.startPullStream();
                }
                break;
            case 2:
                if(mIjkPlayer2 == null){
                    mYQVideoSurfaceView2.setVisibility(View.INVISIBLE);
                    mIjkPlayer2 = new YQIJKAVPlayer(mContext, mYQVideoSurfaceView2,streamurl , 2);
                    mIjkPlayer2.setOnVideoRenderingShow(mYQOnVideoRenderingShow);
//                    mIjkPlayer2.setMuted(true);
                    mIjkPlayer2.startPullStream();
                }
                break;
            case 3:
                if(mIjkPlayer3 == null){
                    mYQVideoSurfaceView3.setVisibility(View.INVISIBLE);
                    mIjkPlayer3 = new YQIJKAVPlayer(mContext, mYQVideoSurfaceView3,streamurl , 3);
                    mIjkPlayer3.setOnVideoRenderingShow(mYQOnVideoRenderingShow);
//                    mIjkPlayer3.setMuted(true);
                    mIjkPlayer3.startPullStream();
                }
                break;
            case -1:
                if(mIjkPlayer1 == null){
                    mYQVideoSurfaceView1.setVisibility(View.INVISIBLE);
                    mIjkPlayer1 = new YQIJKAVPlayer(mContext, mYQVideoSurfaceView1,streamurl , 1);
                    mIjkPlayer1.setOnVideoRenderingShow(mYQOnVideoRenderingShow);
//                    mIjkPlayer1.setMuted(true);
                    mIjkPlayer1.startPullStream();
                }
                if(mIjkPlayer2 == null){
                    mYQVideoSurfaceView2.setVisibility(View.INVISIBLE);
                    mIjkPlayer2 = new YQIJKAVPlayer(mContext, mYQVideoSurfaceView2,streamurl , 2);
                    mIjkPlayer2.setOnVideoRenderingShow(mYQOnVideoRenderingShow);
//                    mIjkPlayer2.setMuted(true);
                    mIjkPlayer2.startPullStream();
                }
                if(mIjkPlayer3 == null){
                    mYQVideoSurfaceView3.setVisibility(View.INVISIBLE);
                    mIjkPlayer3 = new YQIJKAVPlayer(mContext, mYQVideoSurfaceView3,streamurl , 3);
                    mIjkPlayer3.setOnVideoRenderingShow(mYQOnVideoRenderingShow);
//                    mIjkPlayer3.setMuted(true);
                    mIjkPlayer3.startPullStream();
                }


        }
    }

    private void destroyAVPlayer(int index, final boolean destoryPlayer) {
        Message message = new Message();
        message.obj = mDefaultTestUrl;
        switch (index){
            case 1:
                if (mIjkPlayer1 != null && mIjkPlayer1.getKsyMediaPlayer() != null) {
                    mIjkPlayer1.stopPullStream();
                    mIjkPlayer1.onDestroy();
                    mIjkPlayer1 = null;
                }
                mYQVideoSurfaceView1.setVisibility(View.GONE);
                mImageView1.setVisibility(View.VISIBLE);

                message.arg1 = 1;

                break;
            case 2:
                if (mIjkPlayer2 != null && mIjkPlayer2.getKsyMediaPlayer() != null) {
                    mIjkPlayer2.stopPullStream();
                    mIjkPlayer2.onDestroy();
                    mIjkPlayer2 = null;
                }
                mYQVideoSurfaceView2.setVisibility(View.GONE);
                mImageView2.setVisibility(View.VISIBLE);

                message.arg1 = 2;
                break;
            case 3:
                if (mIjkPlayer3 != null && mIjkPlayer3.getKsyMediaPlayer() != null) {
                    mIjkPlayer3.stopPullStream();
                    mIjkPlayer3.onDestroy();
                    mIjkPlayer3 = null;
                }
                mYQVideoSurfaceView3.setVisibility(View.GONE);
                mImageView3.setVisibility(View.VISIBLE);

                message.arg1 = 3;
                break;

            case -1:
                if (mIjkPlayer1 != null && mIjkPlayer1.getKsyMediaPlayer() != null) {
                    mIjkPlayer1.stopPullStream();
                    mIjkPlayer1.onDestroy();
                    mIjkPlayer1 = null;
                }
                mYQVideoSurfaceView1.setVisibility(View.GONE);
                mImageView1.setVisibility(View.VISIBLE);

                if (mIjkPlayer2 != null && mIjkPlayer2.getKsyMediaPlayer() != null) {
                    mIjkPlayer2.stopPullStream();
                    mIjkPlayer2.onDestroy();
                    mIjkPlayer2 = null;
                }
                mYQVideoSurfaceView2.setVisibility(View.GONE);
                mImageView2.setVisibility(View.VISIBLE);

                if (mIjkPlayer3 != null && mIjkPlayer3.getKsyMediaPlayer() != null) {
                    mIjkPlayer3.stopPullStream();
                    mIjkPlayer3.onDestroy();
                    mIjkPlayer3 = null;
                }
                mYQVideoSurfaceView3.setVisibility(View.GONE);
                mImageView3.setVisibility(View.VISIBLE);

                message.arg1 = -1;
                break;
        }
//        mGetSingleVideoPairListHandler.removeCallbacksAndMessages(null);
        mGetSingleVideoPairListHandler.sendMessageDelayed(message,100);
    }

      //获取视频配对
    private Handler mGetSingleVideoPairListHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String streamUrl = (String)msg.obj;
            switch (msg.arg1) {
                case 1:
                    initAVPlayer(1,streamUrl);
                    break;
                case 2:
                    initAVPlayer(2,streamUrl);
                    break;
                case 3:
                    initAVPlayer(3,streamUrl);
                    break;
                case -1:
                    initAVPlayer(-1,streamUrl);
                    break;
            }
        }
    };

}
