package com.example.android.spotifystreamer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    private SearchView mArtistSearch;
    private ListView mListView;
    private TextView mEmptyView;
    private SpotifyApi mApi;
    private SpotifyService mSpotify;


    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mApi = new SpotifyApi();
        mSpotify = mApi.getService();
        mEmptyView = (TextView)rootView.findViewById(R.id.empty_view);
        mListView = (ListView)rootView.findViewById(R.id.artist_list_view);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Artist selectedArtist = (Artist)parent.getAdapter().getItem(position);
                Intent intent = new Intent(getActivity(), TrackActivity.class);
                intent.putExtra(TrackActivity.ARTIST_ID, selectedArtist.id);
                intent.putExtra(TrackActivity.ARTIST_NAME, selectedArtist.name);
                startActivity(intent);
            }
        });
        mArtistSearch = (SearchView)rootView.findViewById(R.id.artist_search_text);
        mArtistSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return searchArtistText(query);
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return searchArtistText(newText);
            }
        });
        return rootView;
    }

    private boolean searchArtistText(String query) {
        if (query != null && !query.equals("")){
            mSpotify.searchArtists(query, new Callback<ArtistsPager>() {
                @Override
                public void success(ArtistsPager artists, Response response) {
                    mListView.setAdapter(new ArtistAdapter(getActivity(), artists.artists.items));
                    mListView.setEmptyView(mEmptyView);
                }

                @Override
                public void failure(RetrofitError error) {
                    mListView.setEmptyView(mEmptyView);
                }
            });
        }
        return true;
    }
}
