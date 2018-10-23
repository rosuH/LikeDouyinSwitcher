package me.rosuh.likedouyinswitcher;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;
import com.shuyu.gsyvideoplayer.GSYVideoADManager;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static me.rosuh.likedouyinswitcher.MediaAdapter.VIEW_TYPE_IMAGE;
import static me.rosuh.likedouyinswitcher.MediaAdapter.VIEW_TYPE_VIDEO;

/**
 * @author rosu
 * @date 2018/10/17
 */
public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 11;

    private RecyclerView mRecyclerView;
    private List<String> mImageUrls = new ArrayList<>();
    private List<MediaDataBean> mMediaDataBeans = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.rv_whole);

        // 权限校验
        boolean isGrantReadPermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (!isGrantReadPermission){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
        }

        MediaAdapter mediaAdapter = new MediaAdapter(MainActivity.this, produceMediaData());

        PagerLayoutManager pagerLayoutManager = new PagerLayoutManager(this, LinearLayoutManager.VERTICAL);
        pagerLayoutManager.setOnViewPagerListener(new OnViewPagerListener() {
            public StandardGSYVideoPlayer mPlayer;

            @Override
            public void onInitComplete(View view) {
            }

            @Override
            public void onPageRelease(boolean isNext, int position, View view) {
                if (mPlayer == null){
                    return;
                }
                mPlayer.release();
            }

            @Override
            public void onPageSelected(int position, boolean isBottom, View view) {
                mPlayer = view.findViewById(R.id.video_player);
                if (mPlayer == null){
                    return;
                }
                mPlayer.startPlayLogic();
            }
        });
        mRecyclerView.setAdapter(mediaAdapter);
        mRecyclerView.setLayoutManager(pagerLayoutManager);
    }

    private List<Pair<Integer, MediaDataBean>> produceMediaData(){
        List<Pair<Integer, MediaDataBean>> pairs = new ArrayList<>();
        mMediaDataBeans = DataUtil.getVideoListData();
        String localPath = Environment.getExternalStorageDirectory().toString() + "/Download/";

        mImageUrls.add(localPath + "1.jpg");
        mImageUrls.add(localPath + "2.jpg");
        mImageUrls.add(localPath + "3.jpg");
        mImageUrls.add(localPath + "4.jpg");
        mImageUrls.add(localPath + "5.jpg");

        for (int i = 0; i < mImageUrls.size(); i++){
            mMediaDataBeans.add(new MediaDataBean(mImageUrls.get(i)));
        }
        Collections.shuffle(mMediaDataBeans);

        for (MediaDataBean dataBean : mMediaDataBeans){
            if (dataBean.getVideoUrl() == null){
                pairs.add(new Pair<>(VIEW_TYPE_IMAGE, dataBean));
                continue;
            }
            pairs.add(new Pair<>(VIEW_TYPE_VIDEO, dataBean));
        }
        return pairs;
    }

    @Override
    protected void onStop() {
        super.onStop();
        GSYVideoADManager.releaseAllVideos();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}
