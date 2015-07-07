package com.example.android.spotifystreamer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.HashMap;
import java.util.Map;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Tracks;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class TrackActivityFragment extends Fragment {
    private static final String TAG = TrackActivityFragment.class.getSimpleName();

    private SpotifyApi mApi;
    private SpotifyService mSpotify;
    private String mArtistId;
    private ListView mListView;

    public TrackActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_track, container, false);
        mApi = new SpotifyApi();
        mSpotify = mApi.getService();
        mArtistId = getActivity().getIntent().getExtras().getString(TrackActivity.ARTIST_ID);
        mListView = (ListView)rootView.findViewById(R.id.track_list_view);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Map<String, Object> map = new HashMap<>();
        map.put("country", "us");
        mSpotify.getArtistTopTrack(mArtistId, map, new Callback<Tracks>() {
            @Override
            public void success(Tracks tracks, Response response) {
                mListView.setAdapter(new TrackAdapter(getActivity(), tracks.tracks));
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, error.getMessage());
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }
}
