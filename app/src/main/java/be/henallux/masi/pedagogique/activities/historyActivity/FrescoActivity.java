package be.henallux.masi.pedagogique.activities.historyActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Protocol;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import be.henallux.masi.pedagogique.R;
import be.henallux.masi.pedagogique.activities.QuestionnaireActivity;
import be.henallux.masi.pedagogique.activities.historyActivity.synthesis.Synthesis;
import be.henallux.masi.pedagogique.activities.historyActivity.synthesis.SynthesisImage;
import be.henallux.masi.pedagogique.activities.historyActivity.synthesis.SynthesisVideo;
import be.henallux.masi.pedagogique.activities.historyActivity.synthesis.SynthesisWebView;
import be.henallux.masi.pedagogique.activities.mapActivity.Location;
import be.henallux.masi.pedagogique.model.Group;
import be.henallux.masi.pedagogique.utils.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FrescoActivity extends AppCompatActivity implements OnStartDragListener {

    @BindView(R.id.recyclerViewItems)
    RecyclerView recyclerView;

    @BindView(R.id.floatingActionButtonGotoQuestionnaire)
    FloatingActionButton gotoQuestionnaire;

    private ItemsAdapter adapter;
    private ItemTouchHelper mItemTouchHelper;

    public static final int VIEWTYPE_VIDEO = 0x81;
    public static final int VIEWTYPE_IMAGE = 0x41;
    public static final int VIEWTYPE_WEBVIEW = 0x37;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco);

        ButterKnife.bind(this);

        final ArrayList<Location> locationsChosen = getIntent().getParcelableArrayListExtra(Constants.KEY_LOCATIONS_CHOSEN);
        final Group currentGroup = getIntent().getParcelableExtra(Constants.KEY_CURRENT_GROUP);
        ArrayList<Synthesis> synthesisChosen = new ArrayList<>();
        for(Location l : locationsChosen){
            synthesisChosen.addAll(l.getSynthesisArrayList());
        }

        adapter = new ItemsAdapter(this, synthesisChosen);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);

        gotoQuestionnaire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FrescoActivity.this, QuestionnaireActivity.class);
                intent.putExtra(Constants.KEY_LOCATIONS_CHOSEN,locationsChosen);
                intent.putExtra(Constants.KEY_CURRENT_GROUP,currentGroup);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    public class ItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemTouchHelperAdapter {

        private final OnStartDragListener mDragStartListener;
        private ArrayList<Synthesis> synthesis;

        public ItemsAdapter(OnStartDragListener mDragStartListener, ArrayList<Synthesis> locationsChosen) {
            this.mDragStartListener = mDragStartListener;
            this.synthesis = locationsChosen;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType){
                case VIEWTYPE_IMAGE:
                    CardView c1 = (CardView) LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.fresco_item_layout_image,parent,false);
                    ViewHolderImage viewHolder1 = new ViewHolderImage(c1,this);
                    return viewHolder1;

                case VIEWTYPE_VIDEO:
                    CardView c2 = (CardView) LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.fresco_item_layout_video,parent,false);
                    ViewHolderVideo viewHolder2 = new ViewHolderVideo(c2,this);
                    return viewHolder2;

                case VIEWTYPE_WEBVIEW:
                    CardView c3 = (CardView) LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.fresco_item_layout_webview,parent,false);
                    ViewHolderWebView viewHolder3 = new ViewHolderWebView(c3,this);
                    return viewHolder3;
            }
            throw new IllegalStateException("Unrecognized viewtype");
        }

        @Override
        public int getItemViewType(int position) {
            Synthesis synth = synthesis.get(position);
            if(synth instanceof SynthesisVideo) return VIEWTYPE_VIDEO;
            if(synth instanceof SynthesisWebView) return VIEWTYPE_WEBVIEW;
            if(synth instanceof SynthesisImage) return VIEWTYPE_IMAGE;
            throw new IllegalStateException("Unrecognized viewtype");
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            final Synthesis act = synthesis.get(position);

            switch (holder.getItemViewType()){
                case VIEWTYPE_VIDEO:
                    bindVideo((ViewHolderVideo)holder,act);
                    break;

                case VIEWTYPE_IMAGE:
                    bindImage((ViewHolderImage)holder,act);
                    break;

                case VIEWTYPE_WEBVIEW:
                    bindWebView((ViewHolderWebView)holder,act);
                    break;
            }
        }

        //region binders
        private void bindVideo(final ViewHolderVideo holder, final Synthesis act) {
            holder.textViewTitle.setText(act.getLocation().getTitle());
            holder.textViewContent.setText(act.getText());

            holder.handleView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (MotionEventCompat.getActionMasked(event) ==
                            MotionEvent.ACTION_DOWN) {
                        mDragStartListener.onStartDrag(holder);
                    }
                    return false;
                }
            });

            final YouTubeThumbnailLoader.OnThumbnailLoadedListener  onThumbnailLoadedListener = new YouTubeThumbnailLoader.OnThumbnailLoadedListener(){
                @Override
                public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

                }

                @Override
                public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                    youTubeThumbnailView.setVisibility(View.VISIBLE);
                    holder.linearLayout.setVisibility(View.VISIBLE);
                }
            };


            String key = getResources().getString(R.string.google_maps_key);
            holder.youTubeThumbnailView.initialize(key, new YouTubeThumbnailView.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {

                    String str = act.getUrl().toString();
                    String videoID =  str.substring(str.length()-11,str.length());
                    youTubeThumbnailLoader.setVideo(videoID);
                    youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener);
                }

                @Override
                public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                    //write something for failure
                }
            });
        }

        private void bindImage(ViewHolderImage holder, Synthesis act) {
            holder.textViewTitle.setText(act.getLocation().getTitle());
            holder.textViewContent.setText(act.getText());

            OkHttpClient client = new OkHttpClient();
            client.setProtocols(Arrays.asList(Protocol.HTTP_1_1));
            Picasso picasso = new Picasso.Builder(FrescoActivity.this)
                    .downloader(new OkHttpDownloader(client))
                    .build();

            picasso.load(act.getUrl().toString()).into(holder.imageView);
        }

        private void bindWebView(ViewHolderWebView holder, Synthesis act) {
            holder.textViewTitle.setText(act.getLocation().getTitle());
            holder.textViewContent.setText(act.getText());
            holder.webView.loadUrl(act.getUrl().toString());
        }

        //endregion
        @Override
        public int getItemCount() {
            return synthesis.size();
        }

        @Override
        public void onItemDismiss(int position) {
            synthesis.remove(position);
            notifyItemRemoved(position);
        }

        @Override
        public void onItemMove(int fromPosition, int toPosition) {
            Collections.swap(synthesis,fromPosition,toPosition);
            notifyItemMoved(fromPosition, toPosition);
        }

        public ArrayList<Synthesis> getSynthesis() {
            return synthesis;
        }
    }

    //region ViewHolders


    private class ViewHolderVideo extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder, View.OnClickListener {

        public TextView textViewTitle;
        public TextView textViewContent;
        public ImageView handleView;
        public YouTubeThumbnailView youTubeThumbnailView;
        public LinearLayout linearLayout;
        public ItemsAdapter adapter;

        public ViewHolderVideo(View itemView, ItemsAdapter adapter) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            handleView = itemView.findViewById(R.id.drag);
            youTubeThumbnailView = itemView.findViewById(R.id.youtube_thumbnail);
            linearLayout = itemView.findViewById(R.id.linearLayout);
            youTubeThumbnailView.setOnClickListener(this);
            textViewContent = itemView.findViewById(R.id.textViewText);
            this.adapter = adapter;
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(Color.WHITE);
        }


        @Override
        public void onClick(View v) {
            String str = adapter.getSynthesis().get(getAdapterPosition()).getUrl().toString();
            String videoID =  str.substring(str.length()-11,str.length());
            Intent intent = YouTubeStandalonePlayer.createVideoIntent(FrescoActivity.this, Constants.ACTIVITY_KEY, videoID);
            FrescoActivity.this.startActivity(intent);
        }
    }


    private class ViewHolderImage extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {

        public TextView textViewContent;
        public TextView textViewTitle;
        public ImageView handleView;
        public ImageView imageView;
        public ItemsAdapter adapter;

        public ViewHolderImage(View itemView, ItemsAdapter adapter) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            handleView = itemView.findViewById(R.id.drag);
            imageView = itemView.findViewById(R.id.imageView);
            textViewContent = itemView.findViewById(R.id.textViewText);
            this.adapter = adapter;
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(Color.WHITE);
        }

    }

    private class ViewHolderWebView extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {

        public TextView textViewContent;
        public TextView textViewTitle;
        public ImageView handleView;
        public WebView webView;
        public ItemsAdapter adapter;

        public ViewHolderWebView(View itemView, ItemsAdapter adapter) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            handleView = itemView.findViewById(R.id.drag);
            textViewContent = itemView.findViewById(R.id.textViewText);
            webView = itemView.findViewById(R.id.webview);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient());
            this.adapter = adapter;
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(Color.WHITE);
        }

    }
    //endregion
}
