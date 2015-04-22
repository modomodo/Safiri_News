package Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.michael.the_one.News;
import com.example.michael.the_one.R;
import com.example.michael.the_one.articleBrowser;

import java.util.ArrayList;

import network.VolleySingleton;

/**
 * Created by Michael on 15/04/2015.
 */
public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.MyViewHolder> {

    LayoutInflater inflater; //Inflater to pass the custom-row layout into
    private ArrayList<News> mListNews = new ArrayList<>();     //list of news articles
    private VolleySingleton volleySingleton; //Instance of volley singleton class to handle Image parsing
    private ImageLoader imageLoader;    //Instance of Image loader to handle image parsing
    String urlPhoto;

    //Constructor of adapter taking context as a parameter to base for inflating the view of the custom_row
    public NewsRecyclerAdapter(Context context){
        inflater = LayoutInflater.from(context);
        volleySingleton = VolleySingleton.getsInstance();
        imageLoader = volleySingleton.getImageLoader();
    }

    public void setNews(ArrayList<News> listNews) {
        this.mListNews = listNews;
        //update the adapter to reflect the new set of news articles
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.custom_row, parent,false); //View representing the root of the custom_row xml file to work with for recycler
        MyViewHolder holder = new MyViewHolder(view); //A holder for the root view obtained so that the contents can be parsed
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //Get the current news article from the array list listNews for the current position in the recyclerview
        News news = mListNews.get(position);
        //Use the holder to set the text for the Title
        urlPhoto = news.getUrlImg();
        loadImages(urlPhoto, holder);
        holder.title.setText(news.getTitle());
        CharSequence relativeTime = DateUtils.getRelativeTimeSpanString(news.getTimestamp()); //Get the relative time from the timestamp retrieved in the JSON feed
        holder.domain.setText(news.getDomain());
        holder.time.setText(" - " + relativeTime);
        holder.currentNews = news;
    }

    //Check if the url image is null and in that case load it
    private void loadImages(String urlPhoto, final MyViewHolder holder) {
        if (urlPhoto!=null) {
            imageLoader.get(urlPhoto, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    holder.photo.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                   // holder.photo.setImageDrawable(R.drawable.ic_abstract);
                }
            });
        }
        //Remember to implement default image... Or like Google News and Weather, none at all
    }

    @Override
    public int getItemCount() { return mListNews.size(); }

    class MyViewHolder extends RecyclerView.ViewHolder {
        //Fields within the custom_row component
        ImageView photo;
        TextView title;
        TextView domain;
        TextView time;
        public News currentNews;

        //Attach the components of text and image view within the view holder so that we can set the Titles and images
        public MyViewHolder(View itemView) {
            super(itemView);
            photo = (ImageView) itemView.findViewById(R.id.imgPhoto);
            title = (TextView) itemView.findViewById(R.id.txtTitle);
            domain = (TextView) itemView.findViewById(R.id.txtDomain);
            time = (TextView) itemView.findViewById(R.id.txtTime);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // item clicked
                    String selectedArticleUrl = currentNews.getUrlNews();
                    showSelectedArticle(v, selectedArticleUrl);
                }
            });
        }
    }
    //Display selected article in separate inapp browser
    private void showSelectedArticle(View view, String articleUrl) {
        Intent intent = new Intent(view.getContext(), articleBrowser.class);
        intent.putExtra("articleUrl", articleUrl);
        view.getContext().startActivity(intent);
    }
}
