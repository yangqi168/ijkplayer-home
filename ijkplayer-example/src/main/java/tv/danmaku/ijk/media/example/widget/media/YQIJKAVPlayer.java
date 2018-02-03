package tv.danmaku.ijk.media.example.widget.media;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * Created by Administrator on 20174/12/15.
 *基于ijkplayer进行优化封装后的播放器
 */

public class YQIJKAVPlayer {
    private Context mContext;
    private int mVideoWidth = 120;
    private int mVideoHeight = 264;
    private IjkVideoView ksyMediaPlayer;
//    private KSYQosInfo info=null;  //qos信息,便于统计qos相关的信息
    //    ByteBuffer rawBuffer[];
//    private SurfaceView mVideoSurfaceView = null;
//    private SurfaceHolder mSurfaceHolder = null;
    private String TAG = "YQIJKAVPlayer";
    private String mDataSource = null;  //流地址
    private boolean mIsPullStream = false;  //是否正在拉流
    private boolean useHwCodec = false; //是否使用硬件编码
    private boolean mIsFirstStartPlayer = true; //是否是第一次启动player
    private boolean mIsScreenShot = false; //停止拉流的时候，截取最后的一张图片
    private int mPlayerIndex = 0;
    private OnVideoRenderingShow mOnVideoRenderingShow;

    private long playUserId = 0;

    public YQIJKAVPlayer() {

    }

    public IjkVideoView getKsyMediaPlayer() {
        return ksyMediaPlayer;

    }

    public void screenShot(String path){
        ksyMediaPlayer.screenShot(path);
    }

    public void setMuted(boolean mute){
        ksyMediaPlayer.setMuted(mute);
    }

    public void setAVReadTimeout(int seconds){
        ksyMediaPlayer.setAVReadTimeout(seconds);
    }

    public YQIJKAVPlayer(Context context, IjkVideoView mIjkVideoView, String rtmpAddress, int index) {
        Log.e("com.esky.echat", TAG + index);
        mContext = context;
        mDataSource = rtmpAddress;
        ksyMediaPlayer = mIjkVideoView;
//        ksyMediaPlayer.initVideoView(context); //add by yangqi-20180109
//        ksyMediaPlayer.resetRender(); //防止render对应的SurfaceHolder被销毁掉
        mPlayerIndex = index;
        IjkMediaPlayer.loadLibrariesOnce(null);
//        IjkMediaPlayer.native_profileBegin("libijkplayer.so");

//        ksyMediaPlayer.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(IMediaPlayer mp) {
////                ksyMediaPlayer.start();
//            }
//        });
          //设置主要的回调监听
        ksyMediaPlayer.setOnPreparedListener(mOnPreparedListener);
//        ksyMediaPlayer.setOnBufferingUpdateListener(mBufferingUpdateListener);
        ksyMediaPlayer.setOnInfoListener(mOnInfoPlayerListener);  //播放过程中信息回调
        ksyMediaPlayer.setOnErrorListener(mOnErrorPlayerListener); //播放错误回调
        ksyMediaPlayer.setOnCompletionListener(mOnCompletionListener); //播放完成回调

//        ksyMediaPlayer.setScreenOnWhilePlaying(true);  //暂未提供此接口，后续考虑添加
//        // setBufferTimeMax --@param bufferTimeSecond 播放器在直播场景下缓冲的最大阈值，单位为秒 在播放直播视频时，可调用该接口，该阈值亦为直播追赶的阈
//        ksyMediaPlayer.setBufferTimeMax(10.f);  //暂未提供此接口，后续考虑添加--setBufferTimeMax(10);  --
//        /** setTimeout
//         * @param prepareTimeout 网络链接超时阈值，单位为秒，默认值为10s
//         * @param readTimeout 读取数据超时阈值，单位为秒，默认值30s
//         */
//        ksyMediaPlayer.setTimeout(5, 10); //暂未提供此接口，后续考虑添加
//        ksyMediaPlayer.setPlayerMute(1); // 暂未提供此接口，后续考虑添加 --1和0分别指播放静音和不静音
//
//        if (useHwCodec) {
//            //硬解264&265
//            Log.e(TAG, "Hardware !!!!!!!!");
//            ksyMediaPlayer.setDecodeMode(KSYMediaPlayer.KSYDecodeMode.KSY_DECODE_MODE_AUTO);
//        } else {
//            ksyMediaPlayer.setDecodeMode(KSYMediaPlayer.KSYDecodeMode.KSY_DECODE_MODE_SOFTWARE);
//        }
        try {
            ksyMediaPlayer.setVideoPath(mDataSource);
//            ksyMediaPlayer.setVideoURI(Uri.parse(mDataSource));
        } catch (Exception e) {
            e.printStackTrace();
        }

//        mVideoSurfaceView = surfaceView;
//        mPlayerIndex = index;
//        mSurfaceHolder = mVideoSurfaceView.getHolder();
//        mSurfaceHolder.addCallback(mSurfaceCallback);
//        //        mVideoSurfaceView1.setOnTouchListener(mTouchListener);
//        mVideoSurfaceView.setKeepScreenOn(true);

//        ksyMediaPlayer = new KSYMediaPlayer.Builder(mContext).build();
//        ksyMediaPlayer.setOnInfoListener(mOnInfoPlayerListener);
//        ksyMediaPlayer.setOnErrorListener(mOnErrorPlayerListener);
//        ksyMediaPlayer.setScreenOnWhilePlaying(true);
//         // setBufferTimeMax --@param bufferTimeSecond 播放器在直播场景下缓冲的最大阈值，单位为秒 在播放直播视频时，可调用该接口，该阈值亦为直播追赶的阈
//        ksyMediaPlayer.setBufferTimeMax(10.f);  //setBufferTimeMax(10);
//        /** setTimeout
//         * @param prepareTimeout 网络链接超时阈值，单位为秒，默认值为10s
//         * @param readTimeout 读取数据超时阈值，单位为秒，默认值30s
//         */
//        ksyMediaPlayer.setTimeout(5, 10);
//        ksyMediaPlayer.setPlayerMute(1); //1和0分别指播放静音和不静音
//        if (useHwCodec) {
//            //硬解264&265
//            Log.e(TAG, "Hardware !!!!!!!!");
//            ksyMediaPlayer.setDecodeMode(KSYMediaPlayer.KSYDecodeMode.KSY_DECODE_MODE_AUTO);
//        } else {
//            ksyMediaPlayer.setDecodeMode(KSYMediaPlayer.KSYDecodeMode.KSY_DECODE_MODE_SOFTWARE);
//        }
//        try {
//            ksyMediaPlayer.setDataSource(mDataSource);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

    //开始拉流
    public void startPullStream() {
        Log.e("com.esky.echat", "startPullStream: mPlayerIndex: " + mPlayerIndex);
        if (mIsFirstStartPlayer) {
//            ksyMediaPlayer.prepareAsync();
            mIsFirstStartPlayer = false;
            ksyMediaPlayer.start();
            Log.e("com.esky.echat", "startPullStream: yq_ksyMediaPlayer.start()aaa: " + mPlayerIndex);
            mIsPullStream = true;
        } else {
            if (!mIsPullStream) {
                if (ksyMediaPlayer != null) {  //
                    ksyMediaPlayer.start();
                    Log.e("com.esky.echat", "startPullStream: yq_ksyMediaPlayer.start()bbb: " + mPlayerIndex);
                }

            }
            mIsPullStream = true;
        }

    }

    //停止拉流
    public void stopPullStream() {
        mIsScreenShot = true;
        if (mIsPullStream) {
            if (ksyMediaPlayer != null) {
                ksyMediaPlayer.pause();
            }
        }
        mIsPullStream = false;

    }

    public void reloadUrlPath(String urlPath){
        mDataSource = urlPath;
        if(ksyMediaPlayer != null){
            ksyMediaPlayer.setVideoPath(urlPath);
            ksyMediaPlayer.start();
        }
    }

    public void onPause() {
        Log.e("com.esky.echat", "onPause: mPlayerIndex: " + mPlayerIndex);
        if (mIsPullStream && ksyMediaPlayer != null) {
            ksyMediaPlayer.pause();
        }
    }

    public void onResume() {
        Log.e("com.esky.echat", "onResume: mPlayerIndex: " + mPlayerIndex);
        if (mIsPullStream) {
            ksyMediaPlayer.start();
        }
    }

    public void onDestroy() {
        Log.e("com.esky.echat", "onDestroy: mPlayerIndex: " + mPlayerIndex);
        if (ksyMediaPlayer != null) {
            ksyMediaPlayer.stopPlayback();
            ksyMediaPlayer.release(true);
//            ksyMediaPlayer.releaseWithoutStop(); //add by yangqi-20180109
            ksyMediaPlayer = null;
        }

        playUserId = 0;
    }


//    private final SurfaceHolder.Callback mSurfaceCallback = new SurfaceHolder.Callback() {
//        @Override
//        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//            if (ksyMediaPlayer != null && ksyMediaPlayer.isPlaying())
//                ksyMediaPlayer.setVideoScalingMode(KSYMediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT); //裁剪模式，还有填充模式可设置
//        }
//
//        @Override
//        public void surfaceCreated(SurfaceHolder holder) {
//            if (ksyMediaPlayer != null)
//                ksyMediaPlayer.setDisplay(holder);
//        }
//
//        @Override
//        public void surfaceDestroyed(SurfaceHolder holder) {
//            Log.d(TAG, "surfaceDestroyed");
//            if (ksyMediaPlayer != null) {
//                ksyMediaPlayer.setDisplay(null);
//            }
//        }
//    };
    //Buffer更新监听
    private IMediaPlayer.OnBufferingUpdateListener mOnBufferingUpdateListener = new IMediaPlayer.OnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(IMediaPlayer mp, int percent) {
//            long duration = ksyMediaPlayer1.getDuration();
//            long progress = duration * percent/100;
//            mPlayerSeekbar.setSecondaryProgress((int)progress);
        }
    };

    // 播放完成时会发出onCompletion回调
    private IMediaPlayer.OnCompletionListener mOnCompletionListener = new IMediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(IMediaPlayer mp) {
//            Toast.makeText(mContext, "OnCompletionListener, play complete.", Toast.LENGTH_LONG).show();
            Log.e(TAG, "mYQAVPlayer_ijk_onCompletion: mPlayerIndex: " + mPlayerIndex);
//            videoPlayEnd();  //changed by yangqi-20171219  -- 暂时的处理策略是报错丢到外层来做处理
            if (mPlayerIndex > 0 && mOnVideoRenderingShow!=null) {
                mOnVideoRenderingShow.playerError(mPlayerIndex);
            }
        }
    };

    private void videoPlayEnd() {
        Log.e("com.esky.echat", "videoPlayEnd: mPlayerIndex: " + mPlayerIndex);
        if (ksyMediaPlayer != null) {
            ksyMediaPlayer.release(true);
            ksyMediaPlayer = null;
        }
    }

    // 播放SDK提供的监听器
    // 播放器在准备完成，可以开播时会发出onPrepared回调
    private IMediaPlayer.OnPreparedListener mOnPreparedListener = new IMediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(IMediaPlayer mp) {
            Log.e("com.esky.echat", "onPrepared: mPlayerIndex: " + mPlayerIndex);
            if (ksyMediaPlayer != null) {
//                mVideoWidth = ksyMediaPlayer.getVideoWidth();
//                mVideoHeight = ksyMediaPlayer.getVideoHeight();
//                // Set Video Scaling Mode
//                ksyMediaPlayer.setVideoScalingMode(KSYMediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
                //start player
                if(mPlayerIndex>0 && mOnVideoRenderingShow!=null){
                    mOnVideoRenderingShow.playerPrepared(mPlayerIndex);
                }
                if (!mIsPullStream) {
                    ksyMediaPlayer.start();
                    mIsPullStream = true;
                }
            }
            //其他相关实现参考官方demo
        }
    };

     //更新Qos信息--测试音视频数据与缓存长度
//    public void updateQosView()
//    {
//        Log.e("com.esky.echat", "updateQosView: mPlayerIndex: " + mPlayerIndex);
//        info = ksyMediaPlayer.getStreamQosInfo();
//        if(info != null) {
//            Log.e(TAG,"vBufferTime=:" + info.videoBufferTimeLength + "(ms) aBufferTime="+info.audioBufferTimeLength + "(ms)");
//        }
//    }


    private IMediaPlayer.OnBufferingUpdateListener mBufferingUpdateListener = new IMediaPlayer.OnBufferingUpdateListener() {
        public void onBufferingUpdate(IMediaPlayer mp, int percent) {
            int mCurrentBufferPercentage = percent;
            if (mOnBufferingUpdateListener != null)
                mOnBufferingUpdateListener.onBufferingUpdate(mp, percent);
        }
    };
    public IMediaPlayer.OnInfoListener mOnInfoPlayerListener = new IMediaPlayer.OnInfoListener() {
        @Override
        public boolean onInfo(IMediaPlayer iMediaPlayer, int i, int i1) {
            switch (i) {
                case IMediaPlayer.MEDIA_INFO_BUFFERING_START:
                    Log.e(TAG, "mYQAVPlayer_ijk_Buffering Start: mPlayerIndex: " + mPlayerIndex);
                    if (mPlayerIndex > 0 && mOnVideoRenderingShow!=null) {
                        mOnVideoRenderingShow.playerError(mPlayerIndex);
                    }
                    break;
                case IMediaPlayer.MEDIA_INFO_BUFFERING_END:
                    Log.e("com.esky.echat", "MEDIA_INFO_BUFFERING_END: " + mPlayerIndex);
                    Log.d(TAG, "Buffering End.");
//                    String tempDataSource="rtmp://live.hkstv.hk.lxdns.com/live/hks";
//                    ksyMediaPlayer.reload(tempDataSource, true); //, KSYMediaPlayer.KSYReloadMode.KSY_RELOAD_MODE_ACCURATE
                    // 播放新视频并快速reload
//                    ksyMediaPlayer.reload(tempDataSource, KSYMediaPlayer.KSYReloadMode.KSY_RELOAD_MODE_ACCURATE);
                    break;
                case IMediaPlayer.MEDIA_INFO_AUDIO_RENDERING_START:
                    Log.e("com.esky.echat", "MEDIA_INFO_AUDIO_RENDERING_START: " + mPlayerIndex);
//                    Toast.makeText(mContext, "Audio Rendering Start", Toast.LENGTH_SHORT).show();
                    break;
                case IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START: //开始渲染的回调
//                    Toast.makeText(mContext, "Video Rendering Start", Toast.LENGTH_SHORT).show();
                    Log.e("com.esky.echat", "MEDIA_INFO_VIDEO_RENDERING_START: " + mPlayerIndex);
                    if (mPlayerIndex > 0 && mOnVideoRenderingShow!=null) {
                        mOnVideoRenderingShow.startRender(mPlayerIndex);
                    }
                    break;
//                case IMediaPlayer.MEDIA_INFO_SUGGEST_RELOAD:
//                    // Player find a new stream(video or audio), and we could reload the video.
//                    LogUtils.e("com.esky.echat", "MEDIA_INFO_SUGGEST_RELOAD: " + mPlayerIndex);
//                    if (mPlayerIndex > 0) {
//                        mOnVideoRenderingShow.reload(mPlayerIndex);
//                    }
//                    break;
//                case IMediaPlayer.MEDIA_INFO_RELOADED:
//                    LogUtils.e("com.esky.echat", "MEDIA_INFO_RELOADED: " + mPlayerIndex);
////                    Toast.makeText(mContext, "Succeed to reload video.", Toast.LENGTH_SHORT).show();
////                    Log.d(TAG, "Succeed to reload video.");
//                    return false;
            }
            return false;
        }
    };
//    private IMediaPlayer.OnVideoSizeChangedListener mOnVideoSizeChangeListener = new IMediaPlayer.OnVideoSizeChangedListener() {
//        @Override
//        public void onVideoSizeChanged(IMediaPlayer mp, int width, int height, int sarNum, int sarDen) {
//            Log.e("com.esky.echat", "onVideoSizeChanged: " + mPlayerIndex);
//            if (mVideoWidth > 0 && mVideoHeight > 0) {
//                if (width != mVideoWidth || height != mVideoHeight) {
//                    mVideoWidth = mp.getVideoWidth();
//                    mVideoHeight = mp.getVideoHeight();
//                    if (ksyMediaPlayer != null)
//                        ksyMediaPlayer.setVideoScalingMode(KSYMediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
//                }
//            }
//        }
//    };
    // 播放器遇到错误时会发出onError回调
    private IMediaPlayer.OnErrorListener mOnErrorPlayerListener = new IMediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(IMediaPlayer mp, int what, int extra) {
            switch (what) {
                case IMediaPlayer.MEDIA_ERROR_UNKNOWN:
                    Log.e("com.esky.echat", "MEDIA_ERROR_UNKNOWN: " + mPlayerIndex);
                    break;
                default:
                    Log.e(TAG, "mYQAVPlayer_ijk_OnErrorListener, Error:" + what + ",extra:" + extra);
                    break;
            }
//            videoPlayEnd();
            Log.e(TAG, "mYQAVPlayer_ijk_mOnErrorPlayerListener: mPlayerIndex: " + mPlayerIndex+",errCode="+what);
            if (mPlayerIndex > 0 && mOnVideoRenderingShow!=null) {
                mOnVideoRenderingShow.playerError(mPlayerIndex);
            }
            return false;
        }
    };
    private IMediaPlayer.OnSeekCompleteListener mOnSeekCompletedListener = new IMediaPlayer.OnSeekCompleteListener() {
        @Override
        public void onSeekComplete(IMediaPlayer mp) {
            Log.e(TAG, "onSeekComplete...............");
        }
    };

    public void setOnVideoRenderingShow(OnVideoRenderingShow onVideoRenderingShow) {
        Log.e("com.esky.echat", "setOnVideoRenderingShow: mPlayerIndex: " + mPlayerIndex);
        mOnVideoRenderingShow = onVideoRenderingShow;
    }

    public interface OnVideoRenderingShow {
        void playerPrepared(int palyerIndex); //播放器已准备好
        void startRender(int playerIndex);
        void playerError(int playerIndex);
        void reload(int playerIndex);
    }

    public long getPlayUserId(){
        return this.playUserId;
    }

    public void setPlayUserId(long playUserId){
        this.playUserId = playUserId;
    }
}
