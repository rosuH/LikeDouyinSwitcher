package me.rosuh.likedouyinswitcher;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * @author rosu
 * @date 2018/10/17
 */
public class ImageViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<String> mImageUrlList;
    private static final String TAG = "ImageViewAdapter";


    public ImageViewAdapter(Context context, List<String> urls) {
        mContext = context;
        mImageUrlList = urls;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ImageViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_image, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Log.i(TAG, "onBindViewHolder: ==========>>> Glide");
        ImageViewHolder holder = (ImageViewHolder) viewHolder;
        Glide.with(mContext)
                .load(mImageUrlList.get(i))
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mImageUrlList.size();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder{
        private AppCompatImageView mImageView;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.iv_whole);
        }
    }
}
