package appkite.jordiguzman.com.backingapp.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.robertlevonyan.views.customfloatingactionbutton.FloatingActionButton;

import appkite.jordiguzman.com.backingapp.R;
import appkite.jordiguzman.com.backingapp.ui.DetailRecipeActivity;


public class DetailRecipeFragment extends Fragment implements View.OnClickListener {

    public final String URL_NO_VIDEO = "https://firebasestorage.googleapis.com/v0/b/friendychat-1e0b0.appspot." +
            "com/o/video%2Fno_video.mp4?alt=media&token=16219a24-977e-44ad-9dad-c96628aeca72";
    private int position;
    private SimpleExoPlayer mSimpleExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private MediaSource mediaSource;
    private TextView tv_item_detail_ingredients;
    private TextView  tv_title;
    private boolean isPlaying, noVideo;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.detail_ingredients_fragment, container, false);

        if (getArguments() != null) {
            position = getArguments().getInt("position");
        }

        mPlayerView = rootView.findViewById(R.id.player_video);

        tv_item_detail_ingredients = rootView.findViewById(R.id.tv_item_detail_ingredients);
        tv_title = rootView.findViewById(R.id.tv_item_detail_title);
        FloatingActionButton fbDetail = rootView.findViewById(R.id.fb_steps);
        fbDetail.setOnClickListener(this);


        if (savedInstanceState != null){
            position = savedInstanceState.getInt("position");
            noVideo = savedInstanceState.getBoolean("noVideo");
            if (noVideo){
                populateDescription();
                initialitePlayer();
                populatePlayerNoVideo();
            }
        }
        if (!noVideo){
            populateDescription();
            initialitePlayer();
            populatePlayerJson();
        }
        return rootView;
    }

    public void populateDescription() {
        if (DetailRecipeActivity.mStep != null) {
            tv_item_detail_ingredients.setText(DetailRecipeActivity.mStep.get(position).description);
            tv_title.setText(DetailRecipeActivity.mStep.get(position).shortDescription);
        }
    }

    public void initialitePlayer() {
        if (DetailRecipeActivity.mStep != null) {
            if (mSimpleExoPlayer == null) {
                mSimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), new DefaultTrackSelector());
                mPlayerView.setPlayer(mSimpleExoPlayer);
            }
        }
    }

    public void populatePlayerJson(){
        if (getActivity() != null)
            mediaSource = new ExtractorMediaSource(Uri.parse(DetailRecipeActivity.mStep.get(position).videoURL), new DefaultDataSourceFactory(
                    getActivity(), "string"), new DefaultExtractorsFactory(), null, null);
        mSimpleExoPlayer.prepare(mediaSource);
        mSimpleExoPlayer.setPlayWhenReady(true);
        mPlayerView.hideController();
        isPlaying = true;
        noVideo= false;
    }
    public void populatePlayerNoVideo(){
        if (getActivity() != null)
        mediaSource = new ExtractorMediaSource(Uri.parse(URL_NO_VIDEO), new DefaultDataSourceFactory(
                getActivity(), "string"), new DefaultExtractorsFactory(), null, null);
        mSimpleExoPlayer.prepare(mediaSource);
        mSimpleExoPlayer.setPlayWhenReady(true);
        mPlayerView.hideController();
        isPlaying = true;
    }


    @Override
    public void onClick(View v) {

        if (isPlaying) {
            mSimpleExoPlayer.release();
            mSimpleExoPlayer= null;
            isPlaying = false;
        }
        position++;
        initialitePlayer();
        if (position >= DetailRecipeActivity.mStep.size())position=0;
        if (DetailRecipeActivity.mStep.get(position).videoURL.length() == 0 && getActivity() != null) {
            noVideo=true;
             populatePlayerNoVideo();
             populateDescription();
            return;
        }
        populateDescription();
        populatePlayerJson();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSimpleExoPlayer.release();
        mSimpleExoPlayer= null;
        isPlaying = false;
        noVideo=false;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", position);
        outState.putBoolean("noVideo", noVideo);


    }
}
