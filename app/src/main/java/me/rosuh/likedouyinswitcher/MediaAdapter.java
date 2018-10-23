package me.rosuh.likedouyinswitcher;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import java.util.List;

/**
 * @author rosu
 * @date 2018/10/18
 */
public class MediaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int VIEW_TYPE_IMAGE = 1001100;
    public static final int VIEW_TYPE_VIDEO = 10012011;

    private Context mContext;
    private static final String TAG = "ImageViewAdapter";
    private List<Pair<Integer, MediaDataBean>> mMediaDataPairs;
    private GSYVideoOptionBuilder mGSYVideoOptionBuilder;

    public MediaAdapter(Context context, List<Pair<Integer, MediaDataBean>> mediaDataPairs) {
        mContext = context;
        mMediaDataPairs = mediaDataPairs;
        mGSYVideoOptionBuilder = new GSYVideoOptionBuilder();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        switch (viewType){
            case VIEW_TYPE_IMAGE:
                return new ImageViewHolder(inflater, viewGroup);
            case VIEW_TYPE_VIDEO:
            default:
                return new VideoViewHolder(inflater, viewGroup);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        int viewType = getItemViewType(position);

        switch (viewType){
            case VIEW_TYPE_IMAGE:
                ImageViewHolder imageViewHolder = (ImageViewHolder)viewHolder;
                imageViewHolder.bindData(mMediaDataPairs.get(position).second.getImageUrl());
                break;
            case VIEW_TYPE_VIDEO:
                VideoViewHolder videoViewHolder = (VideoViewHolder)viewHolder;
                videoViewHolder.bindData(mMediaDataPairs.get(position).second);
                break;
                default:
        }
    }

    @Override
    public int getItemViewType(int position) {
        super.getItemViewType(position);
        return mMediaDataPairs.get(position).first;
    }

    @Override
    public int getItemCount() {
        if (mMediaDataPairs == null){
            return 0;
        }
        return mMediaDataPairs.size();
    }

    private class ImageViewHolder extends RecyclerView.ViewHolder{
        private AppCompatImageView mImageView;

        private ImageViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_image, parent, false));
            mImageView = itemView.findViewById(R.id.iv_whole);
        }

        public void bindData(String imageUrl){
            Glide.with(itemView.getContext())
                    .load(imageUrl)
                    .into(mImageView);
        }

    }

    class VideoViewHolder extends RecyclerView.ViewHolder{
        public StandardGSYVideoPlayer mPlayer;
        VideoViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_video, parent, false));
            mPlayer = itemView.findViewById(R.id.video_player);

//            ViewGroup.LayoutParams layoutParams = mPlayer.getLayoutParams();
//            layoutParams.width = itemView.getResources().getDisplayMetrics().widthPixels;
//            layoutParams.height = (int)(layoutParams.width * 9f / 16f);
//            mPlayer.setLayoutParams(layoutParams);
        }

        public void bindData(MediaDataBean dataBean){
//            mGSYVideoOptionBuilder
//                    .setIsTouchWiget(false)
//                    .setUrl(dataBean.getVideoUrl())
//                    .setSetUpLazy(true)
//                    .setVideoTitle(dataBean.getTitle())
//                    .setCacheWithPlay(true)
//                    .setShowFullAnimation(true)
//                    .set
            mPlayer.setUp(dataBean.getVideoUrl(), true, dataBean.getTitle());
        }
    }

}
