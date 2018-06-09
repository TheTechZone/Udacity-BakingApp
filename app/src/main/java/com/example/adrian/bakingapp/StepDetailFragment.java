package com.example.adrian.bakingapp;

import android.app.Activity;
import android.app.FragmentManager;
import android.net.Uri;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.adrian.bakingapp.data.model.Step;
import com.example.adrian.bakingapp.dummy.DummyContent;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import org.parceler.Parcels;

/**
 * A fragment representing a single Step detail screen.
 * This fragment is either contained in a {@link StepListActivity}
 * in two-pane mode (on tablets) or a {@link StepDetailActivity}
 * on handsets.
 */
public class StepDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    private Step mStep;
    private MediaSessionCompat mediaSession;
    private PlaybackStateCompat.Builder stateBuilder;
    private MediaSessionConnector mediaSessionConnector;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StepDetailFragment() {
    }

    private View parent;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            Parcelable p = getArguments().getParcelable(ARG_ITEM_ID);
            mStep = Parcels.unwrap(p);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle("Step #" + mStep.getId());
            }


        }
    }

//    @Override
//    public void onST(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//    }


    @Override
    public void onStart() {
        boolean a = getActivity().findViewById(R.id.player_view) != null;
        playerView = (PlayerView) getActivity().findViewById(R.id.player_view);

        mediaSession = new MediaSessionCompat(getContext(), "TAG");
        mediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mediaSession.setMediaButtonReceiver(null);
        stateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
                );

        mediaSession.setPlaybackState(stateBuilder.build());
//        mediaSession.setCallback(new mySessionCallback());
//        mediaSession.setActive(true);
        mediaSessionConnector = new MediaSessionConnector(mediaSession);

        if(playerView != null)
            initializePlayer(mStep.videoURL);

        super.onStart();

    }

    private SimpleExoPlayer player;
    private PlayerView playerView;

    private void initializePlayer(String uriString){


       player = ExoPlayerFactory.newSimpleInstance(getContext(), new DefaultTrackSelector());
       playerView.setPlayer(player);

       if(uriString.equals("")){
            releasePlayer();
            playerView.setVisibility(View.GONE);
            return;
       }
       DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(),
               Util.getUserAgent(getContext(), "exoplay")));
        ExtractorMediaSource mediaSource =  new ExtractorMediaSource.Factory(dataSourceFactory)
               .createMediaSource(Uri.parse(uriString));

       player.prepare(mediaSource);
       player.setPlayWhenReady(true);

       mediaSessionConnector.setPlayer(player, null,null);
       mediaSession.setActive(true);
    }

    private void releasePlayer(){
        mediaSessionConnector.setPlayer(null,null,null);
        mediaSession.setActive(false);
        if (player!=null) {
            playerView.setPlayer(null);
            player.release();
            player = null;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.step_detail, container, false);

        // Show the content as text in a TextView.
        if (mStep != null) {
            ((TextView) rootView.findViewById(R.id.step_detail)).setText(mStep.getDescription());
        }

        return rootView;
    }

    private class mySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            super.onPlay();
        }

        @Override
        public void onPause() {
            super.onPause();
        }

        @Override
        public void onSkipToPrevious() {
            super.onSkipToPrevious();
        }
    }
}
