package appkite.jordiguzman.com.backingapp.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

import appkite.jordiguzman.com.backingapp.R;
import appkite.jordiguzman.com.backingapp.ui.DetailIngredients;


public class DetailFragment extends Fragment implements View.OnClickListener {


    private int position;
    private SimpleExoPlayer mSimpleExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private MediaSource mediaSource;
    private final String LOG_TAG = DetailFragment.class.getSimpleName();
    private TextView  tv_item_detail_ingredients;
    private Button btn_next;
    private boolean isPlaying;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.detail_ingredients, container, false);

        if (getArguments() != null){
            position = getArguments().getInt("position");
        }

        mPlayerView = rootView.findViewById(R.id.player_video);
        tv_item_detail_ingredients = rootView.findViewById(R.id.tv_item_detail_ingredients);
        btn_next = rootView.findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);


        populateDescription();
        initialiteAndPopulatePlayer();




        return rootView;
    }


    public void populateDescription(){
        if (DetailIngredients.mStep != null){
            tv_item_detail_ingredients.setText(DetailIngredients.mStep.get(position).description);
        }
    }

    public void initialiteAndPopulatePlayer(){
        if (DetailIngredients.mStep != null){
            if (mSimpleExoPlayer==null){
                mSimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), new DefaultTrackSelector());
                mPlayerView.setPlayer(mSimpleExoPlayer);
                if (getActivity()!=null)
                mediaSource = new ExtractorMediaSource(Uri.parse(DetailIngredients.mStep.get(position).videoURL), new DefaultDataSourceFactory(
                        getActivity(), "string"), new DefaultExtractorsFactory(), null, null);
                mSimpleExoPlayer.prepare(mediaSource);
                mSimpleExoPlayer.setPlayWhenReady(true);
                isPlaying= true;
            }

        }
    }


    @Override
    public void onClick(View v) {
        if (isPlaying)mSimpleExoPlayer.release();
        position++;
        if (DetailIngredients.mStep.get(position).videoURL.length() ==0){
            hideVideoPlayer();
            mSimpleExoPlayer=null;
        }else {
            showVideoPlayer();
            mSimpleExoPlayer=null;
        }

        populateDescription();
        initialiteAndPopulatePlayer();
    }

    private void hideVideoPlayer() {
        mPlayerView.setVisibility(View.INVISIBLE);
    }
    private void showVideoPlayer(){
        mPlayerView.setVisibility(View.VISIBLE);
    }
}
