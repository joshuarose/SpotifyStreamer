package com.example.android.spotifystreamer;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import kaaes.spotify.webapi.android.models.Artist;

/**
 * Created by joshuarose on 7/4/15.
 */
public class ArtistAdapter extends BaseAdapter {

    private Context mContext;
    private List<Artist> mArtists;

    public ArtistAdapter(Context context, List<Artist> artists){
        mContext = context;
        mArtists = artists;
    }

    @Override
    public int getCount() {
        return mArtists.size();
    }

    @Override
    public Artist getItem(int position) {
        return mArtists.get(position);
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
            convertView = inflater.inflate(R.layout.list_item_artist, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // object item based on the position
        Artist artist = mArtists.get(position);
        if (artist != null){
            viewHolder.artistText.setText(artist.name);
            if (artist.images != null && artist.images.size() > 0){
                int smallestImageIndex = artist.images.size() -1;
                String url = artist.images.get(smallestImageIndex).url;
                if (!url.isEmpty()){
                    Picasso.with(mContext).load(url).resize(200,200).into(viewHolder.artistImage);
                }
            }
        }
        return convertView;

    }

    public static class ViewHolder {
        public final ImageView artistImage;
        public final TextView artistText;

        public ViewHolder(View view){
            artistImage = (ImageView)view.findViewById(R.id.artist_image);
            artistText = (TextView)view.findViewById(R.id.artist_text);
        }
    }
}
