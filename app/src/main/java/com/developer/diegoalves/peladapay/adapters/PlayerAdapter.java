package com.developer.diegoalves.peladapay.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.developer.diegoalves.peladapay.R;
import com.developer.diegoalves.peladapay.adapters.clickAdapter.RecyclerViewOnClickListener;
import com.developer.diegoalves.peladapay.entities.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diego Alves on 24/11/2015.
 */
public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayersViewHolder> {

    private List<Player> players;
    private final Context context;
    RecyclerViewOnClickListener recyclerViewOnClickListener;
    private SparseBooleanArray selectedItems;

    private PlayerOnClickListener onClickListener;

    public PlayerAdapter(Context context, List<Player> players) {
        this.players = new ArrayList<>(players);
        this.context = context;
        selectedItems = new SparseBooleanArray();
    }

    @Override
    public PlayersViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.colored_item, viewGroup, false);
        PlayersViewHolder viewHolder = new PlayersViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final PlayersViewHolder holder, final int position) {
        final Player p = players.get(position);
        holder.name.setText(p.getName());
        if(p.getIsPaid() == 0) {
            holder.name.setTextColor(ContextCompat.getColor(context, R.color.white));
            CardView cardView = (CardView) holder.view.findViewById(R.id.card_view);
            cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.red_100));
        } else {
            holder.name.setTextColor(ContextCompat.getColor(context, R.color.white));
            CardView cardView = (CardView) holder.view.findViewById(R.id.card_view);
            cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.green_2));
        }
        holder.itemView.setActivated(selectedItems.get(position, false));
    }

    public void addItem(Player p, int position) {
        players.add(position, p);
        notifyDataSetChanged();
    }

    public void removeItem(Player p, int position) {
        players.remove(p);
        notifyItemRemoved(position);
    }

    public void toggleSelection(int position) {
        if(selectedItems.get(position, false)) {
            selectedItems.delete(position);
        } else {
            selectedItems.put(position, true);
        }
        notifyItemChanged(position);
    }

    public void clearSelections() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    public List<Integer> getSelectedItems() {
        List<Integer> items = new ArrayList<>(selectedItems.size());

        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }

    @Override
    public int getItemCount() {
        return this.players != null ? this.players.size() : 0;
    }

    public interface PlayerOnClickListener {
        void onClickPlayer(View view, int id);
    }

    public boolean isSelected(int position) {
        return getSelectedItems().contains(position);
    }

    public void setRecyclerViewOnClickListener(RecyclerViewOnClickListener recyclerViewOnClickListener) {
        this.recyclerViewOnClickListener = recyclerViewOnClickListener;
    }

    public class PlayersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name;
        private View view;

        public PlayersViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            name = (TextView) view.findViewById(R.id.card_pName);
        }

        @Override
        public void onClick(View v) {
            if(recyclerViewOnClickListener != null) {
                recyclerViewOnClickListener.onClickListener(v, getAdapterPosition());
            }
        }
    }
}
