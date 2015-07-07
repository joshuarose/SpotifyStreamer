package com.example.android.spotifystreamer;

import android.app.Activity;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.Track;

/**
 * Created by joshuarose on 7/6/15.
 */
public class TrackAdapter extends BaseAdapter implements Parcelable {
    private Context mContext;
    private List<Track> mTracks;

    public TrackAdapter(Context context, List<Track> tracks){
        mContext = context;
        mTracks = tracks;
    }

    @Override
    public int getCount() {
        return mTracks.size();
    }

    @Override
    public Object getItem(int position) {
        return mTracks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.list_item_track, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // object item based on the position
        Track track = mTracks.get(position);
        if (track != null){
            viewHolder.trackText.setText(track.name);
            viewHolder.albumText.setText(track.album.name);
            for (int i = 0; i < track.album.images.size(); i++){
                Image image = track.album.images.get(i);
                if (image.width < 640 && !image.url.isEmpty()){
                    Picasso.with(mContext).load(image.url).resize(200, 200).into(viewHolder.albumImage);
                }
            }
        }
        return convertView;
    }

    public static class ViewHolder {
        public final ImageView albumImage;
        public final TextView trackText;
        public final TextView albumText;

        public ViewHolder(View view){
            albumImage = (ImageView)view.findViewById(R.id.album_image);
            trackText = (TextView)view.findViewById(R.id.track_text);
            albumText = (TextView)view.findViewById(R.id.album_text);
        }
    }

    protected TrackAdapter(Parcel in) {
        mContext = (Context) in.readValue(Context.class.getClassLoader());
        if (in.readByte() == 0x01) {
            mTracks = new ArrayList<Track>();
            in.readList(mTracks, Track.class.getClassLoader());
        } else {
            mTracks = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(mContext);
        if (mTracks == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mTracks);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<TrackAdapter> CREATOR = new Parcelable.Creator<TrackAdapter>() {
        @Override
        public TrackAdapter createFromParcel(Parcel in) {
            return new TrackAdapter(in);
        }

        @Override
        public TrackAdapter[] newArray(int size) {
            return new TrackAdapter[size];
        }
    };
}