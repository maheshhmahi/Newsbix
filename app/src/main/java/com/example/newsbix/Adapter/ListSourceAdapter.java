package com.example.newsbix.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsbix.Common.Common;
import com.example.newsbix.Interface.IconBetterIdeaService;
import com.example.newsbix.Interface.ItemClickListner;
import com.example.newsbix.ListNews;
import com.example.newsbix.Main2Activity;
import com.example.newsbix.MainActivity;
import com.example.newsbix.Model.IconBetterIdea;
import com.example.newsbix.Model.WebSite;
import com.example.newsbix.R;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class ListSourceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    ItemClickListner itemClickListner;
    TextView source_title;
    CircularImageView source_image;

    public ListSourceViewHolder(@NonNull View itemView) {
        super(itemView);

        source_image = itemView.findViewById(R.id.source_image);
        source_title = itemView.findViewById(R.id.source_title);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }

    @Override
    public void onClick(View v) {
        itemClickListner.onClick(v,getAdapterPosition(),false);
    }
}


public class ListSourceAdapter extends RecyclerView.Adapter<ListSourceViewHolder>{

    private Context context;
    private WebSite webSite;
    private IconBetterIdeaService mService;

    public ListSourceAdapter(Context context, WebSite webSite) {
        this.context = context;
        this.webSite = webSite;
        mService = Common.getIconService();
    }

    @NonNull
    @Override
    public ListSourceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.source_layout,parent,false);
        return new ListSourceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListSourceViewHolder holder, int position) {

        StringBuilder iconBetterApi = new StringBuilder("https://vision-icon.netop.com/allicons.json?url=");
        iconBetterApi.append(webSite.getSources().get(position).getUrl());

        mService.getIconUrl(iconBetterApi.toString()).enqueue(new Callback<IconBetterIdea>() {
            @Override
            public void onResponse(Call<IconBetterIdea> call, Response<IconBetterIdea> response) {

                if(response.body().getIcons().size()>0)
                {
                    Picasso.get().load(response.body().getIcons().get(0).getUrl()).into(holder.source_image);
                }
            }

            @Override
            public void onFailure(Call<IconBetterIdea> call, Throwable t) {

            }
        });

        holder.source_title.setText(webSite.getSources().get(position).getName());

        holder.setItemClickListner(new ItemClickListner() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Intent i = new Intent(context, ListNews.class);
                i.putExtra("Source",webSite.getSources().get(position).getId());
                //i.putExtra("sortBy",webSite.getSources().get(position).getSortByAvailable().get(0));
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return webSite.getSources().size();
    }
}
