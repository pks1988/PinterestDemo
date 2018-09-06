package com.pinterest.demo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.image.downloader.Constants;
import com.image.downloader.ImageDownloader;
import com.pinterest.demo.R;
import com.pinterest.demo.models.User;
import com.pinterest.demo.models.UserDetail;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Pravesh Sharma on 04-09-2018.
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {


    LayoutInflater mInflater;
    List<UserDetail> mUserList;
    Context context;

    public UserAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_pin, parent, false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        if (mUserList != null) {
            UserDetail user = mUserList.get(position);
            setDetails(holder, user);

        }
    }

    private void setDetails(UserViewHolder holder, UserDetail user) {
        holder.Name.setText(user.getUser().getName());
        holder.UserName.setText(user.getUser().getUsername());
//        Picasso.get().load(user.getUser().getProfileImage().getMedium()).into(holder.userThumb);
//        Picasso.get().load(user.getUrls().getThumb()).into(holder.mediaImage);


        new ImageDownloader.Builder()
                .setContext(context)
                .setView(holder.userThumb)
                .setFileType(Constants.FILE_TYPE_IMAGE)
                .setSourceUrl(user.getUser().getProfileImage().getMedium())
                .build();


        new ImageDownloader.Builder()
                .setContext(context)
                .setView(holder.mediaImage)
                .setFileType(Constants.FILE_TYPE_IMAGE)
                .setSourceUrl(user.getUrls().getThumb())
                .build();


    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if (mUserList != null)
            return mUserList.size();
        else return 0;
    }

    public void updateUsers(List<UserDetail> users) {
        mUserList = users;
        notifyDataSetChanged();
    }

    public UserDetail getUserOnPos(int pos) {
        return mUserList.get(pos);
    }

 /*   public static class studentViewHolder extends RecyclerView.ViewHolder {

        private TextView txtStudent;

        public studentViewHolder(View itemView) {
            super(itemView);
            txtStudent = itemView.findViewById(R.id.textView);
        }
    }*/

    static class UserViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.user_thumb)
        ImageView userThumb;
        @BindView(R.id.Name)
        TextView Name;
        @BindView(R.id.User_Name)
        TextView UserName;
        @BindView(R.id.media_image)
        ImageView mediaImage;
        @BindView(R.id.img_likes)
        ImageView imgLikes;
        @BindView(R.id.likes_count)
        TextView likesCount;

        public UserViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
