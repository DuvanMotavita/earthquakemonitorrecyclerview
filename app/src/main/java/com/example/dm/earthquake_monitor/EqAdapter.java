package com.example.dm.earthquake_monitor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class EqAdapter extends ListAdapter<Earthquake,EqAdapter.EqViewHolder> {

    public static final DiffUtil.ItemCallback<Earthquake> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Earthquake>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull Earthquake oldEarthquake, @NonNull Earthquake newEarthquake) {
                    // User properties may have changed if reloaded from the DB, but ID is fixed
                    return oldEarthquake.getId().equals(newEarthquake.getId());
                }
                @Override
                public boolean areContentsTheSame(
                        @NonNull Earthquake oldEarthquake, @NonNull Earthquake newEarthquake) {
                    // NOTE: if you use equals, your object must properly override Object#equals()
                    // Incorrectly returning false here will result in too many animations.
                    return oldEarthquake.equals(newEarthquake);
                }
            };



    protected EqAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public EqAdapter.EqViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.eq_list_item,parent,false);
        return new EqViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EqAdapter.EqViewHolder holder, int position) {
        Earthquake earthquake = getItem(position);
        holder.bind(earthquake);
    }
    class EqViewHolder extends RecyclerView.ViewHolder{
        private TextView magnitudeText;
        private TextView placeText;

        public EqViewHolder(@NonNull View itemView) {
            super(itemView);
            magnitudeText = itemView.findViewById(R.id.magnitude_text);
            placeText = itemView.findViewById(R.id.place_text);
        }
        public  void bind(Earthquake earthquake){
            magnitudeText.setText(String.valueOf(earthquake.getMagnitude()));
            placeText.setText(earthquake.getPlace());
        }
    }
}
