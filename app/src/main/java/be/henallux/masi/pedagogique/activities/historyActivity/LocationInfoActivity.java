package be.henallux.masi.pedagogique.activities.historyActivity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Protocol;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;

import be.henallux.masi.pedagogique.R;
import be.henallux.masi.pedagogique.activities.historyActivity.synthesis.Synthesis;
import be.henallux.masi.pedagogique.activities.historyActivity.synthesis.SynthesisImage;
import be.henallux.masi.pedagogique.activities.historyActivity.synthesis.SynthesisVideo;
import be.henallux.masi.pedagogique.activities.historyActivity.synthesis.SynthesisWebView;
import be.henallux.masi.pedagogique.activities.mapActivity.IMapActivityRepository;
import be.henallux.masi.pedagogique.activities.mapActivity.Location;
import be.henallux.masi.pedagogique.activities.mapActivity.SQLiteMapActivityRepository;
import be.henallux.masi.pedagogique.dao.interfaces.ISynthesisRepository;
import be.henallux.masi.pedagogique.dao.sqlite.SQLSynthesisRepository;
import be.henallux.masi.pedagogique.utils.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LocationInfoActivity extends AppCompatActivity {

    @BindView(R.id.floatingActionButtonAddLocation)
    public FloatingActionButton floatingActionButtonAdd;
    @BindView(R.id.imageView)
    public ImageView imageView;
    @BindView(R.id.webView)
    public WebView webView;
    @BindView(R.id.youtube_thumbnail)
    public YouTubeThumbnailView youTubeThumbnailView;
    @BindView(R.id.textViewTitle)
    public TextView textViewTitle;
    @BindView(R.id.textViewContent)
    public TextView textViewContent;

    private ISynthesisRepository synthesisRepository = new SQLSynthesisRepository(this);
    private Location clickedLocation;
    private boolean isInDeleteMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_info);

        ButterKnife.bind(this);

        clickedLocation = getIntent().getExtras().getParcelable(Constants.KEY_LOCATION_CLICKED);
        isInDeleteMode = getIntent().getExtras().getBoolean(Constants.KEY_IS_IN_DELETE_MODE);
        ArrayList<Synthesis> synthesis =  synthesisRepository.getAllSynthesisOfLocation(clickedLocation);

        textViewTitle.setText(synthesis.get(0).getLocation().getTitle());
        textViewContent.setText(synthesis.get(0).getText());

        webView.setVisibility(View.GONE);
        youTubeThumbnailView.setVisibility(View.GONE);
        imageView.setVisibility(View.GONE);

        for(Synthesis s : synthesis){
            if(s instanceof SynthesisVideo){
                final SynthesisVideo synthesisVideo = (SynthesisVideo)s;
                final YouTubeThumbnailLoader.OnThumbnailLoadedListener  onThumbnailLoadedListener = new YouTubeThumbnailLoader.OnThumbnailLoadedListener(){
                    @Override
                    public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

                    }

                    @Override
                    public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                        youTubeThumbnailView.setVisibility(View.VISIBLE);
                    }
                };


                String key = getResources().getString(R.string.google_maps_key);
                youTubeThumbnailView.initialize(key, new YouTubeThumbnailView.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {

                        String str = synthesisVideo.getUrl().toString();
                        String videoID =  str.substring(str.length()-11,str.length());
                        youTubeThumbnailLoader.setVideo(videoID);
                        youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener);
                    }

                    @Override
                    public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                        //write something for failure
                    }
                });
                youTubeThumbnailView.setVisibility(View.VISIBLE);
                youTubeThumbnailView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String str = synthesisVideo.getUrl().toString();
                        String videoID =  str.substring(str.length()-11,str.length());
                        Intent intent = YouTubeStandalonePlayer.createVideoIntent(LocationInfoActivity.this, Constants.ACTIVITY_KEY, videoID);
                        LocationInfoActivity.this.startActivity(intent);
                    }
                });
            }
            else if(s instanceof SynthesisWebView){
                SynthesisWebView synthesisWebView = (SynthesisWebView)s;
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setWebViewClient(new WebViewClient());
                webView.loadUrl(s.getUrl().toString());
                webView.setVisibility(View.VISIBLE);
            }
            else if(s instanceof SynthesisImage){

                SynthesisImage synthesisImage = (SynthesisImage)s;

                OkHttpClient client = new OkHttpClient();
                client.setProtocols(Arrays.asList(Protocol.HTTP_1_1));
                Picasso picasso = new Picasso.Builder(LocationInfoActivity.this)
                        .downloader(new OkHttpDownloader(client))
                        .build();

                picasso.load(synthesisImage.getUrl().toString()).into(imageView);
                imageView.setVisibility(View.VISIBLE);
            }
        }

        if(isInDeleteMode)
            floatingActionButtonAdd.setImageResource(R.drawable.ic_delete);
        else
            floatingActionButtonAdd.setImageResource(R.drawable.ic_add_white_24dp);

        floatingActionButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(Constants.RESULT_DATA_HAS_CHOOSEN_LOCATION, true);
                intent.putExtra(Constants.KEY_LOCATION_CLICKED, clickedLocation);
                intent.putExtra(Constants.KEY_IS_IN_DELETE_MODE,isInDeleteMode);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
