package com.example.adrian.bakingapp.ui;

import android.app.Activity;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.adrian.bakingapp.R;
import com.example.adrian.bakingapp.data.model.Step;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
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
    public static final String PLAYER_KEY = "playerPosition";

    private Step mStep;
    private TextView detailTextView;
    private MediaSessionCompat mediaSession;
    private PlaybackStateCompat.Builder stateBuilder;
    private MediaSessionConnector mediaSessionConnector;
    private View parent;
    private SimpleExoPlayer player;
    private PlayerView playerView;

    private long playerPosition = 0;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StepDetailFragment() {
    }

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
            //CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            Toolbar appBarLayout = activity.findViewById(R.id.detail_toolbar);
            if (appBarLayout != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    appBarLayout.setTitle("Step #" + mStep.getId());
                }
            }

        }

        if (savedInstanceState != null){
            if (savedInstanceState.containsKey(PLAYER_KEY)){
                playerPosition = savedInstanceState.getLong(PLAYER_KEY);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.step_detail, container, false);

        playerView = rootView.findViewById(R.id.player_view);
//        inflater.inflate(R.layout.activity_step_detail, container,false);
        // Show the content as text in a TextView.
        if (mStep != null) {
            detailTextView = rootView.findViewById(R.id.step_detail);
            detailTextView.setText(mStep.getDescription());
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (player != null){
            outState.putLong(PLAYER_KEY, player.getContentPosition());
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStart() {
        if (playerView == null)
        playerView = getActivity().findViewById(R.id.player_view);

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
        mediaSessionConnector = new MediaSessionConnector(mediaSession);

        if (playerView != null)
            initializePlayer(mStep.videoURL);

        super.onStart();

    }

    private void initializePlayer(String uriString) {

        player = ExoPlayerFactory.newSimpleInstance(getContext(), new DefaultTrackSelector());
        playerView.setPlayer(player);

        if (uriString.equals("")) {
            releasePlayer();
            playerView.setVisibility(View.GONE);
            return;
        }

        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(),
                Util.getUserAgent(getContext(), "exoplay")));
        ExtractorMediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(uriString));

        player.prepare(mediaSource);
        player.seekTo(playerPosition);
        player.setPlayWhenReady(true);

        mediaSessionConnector.setPlayer(player, null, (MediaSessionConnector.CustomActionProvider[]) null);
        mediaSession.setActive(true);
    }

    private void releasePlayer() {
        if(player!=null)
            player.release();
        mediaSessionConnector.setPlayer(null, null, (MediaSessionConnector.CustomActionProvider[]) null);
        mediaSession.setActive(false);
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (player != null){
            player.stop();
        }
        releasePlayer();
    }

}
