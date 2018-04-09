package hu.ait.android.wheresthepartyat.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import hu.ait.android.wheresthepartyat.MapsActivity;
import hu.ait.android.wheresthepartyat.R;
import hu.ait.android.wheresthepartyat.data.Party;

/**
 * Created by teagu_000 on 10/12/2017.
 */

public class PartiesAdapter extends RecyclerView.Adapter<PartiesAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvPartyName;
        public TextView tvDescription;
        public Button btnDelete;
        public CheckBox cbBYOB;
        public CheckBox cbBar;
        public CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            tvPartyName = itemView.findViewById(R.id.tvParty2);
            tvDescription = itemView.findViewById(R.id.tvDescription2);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            cbBYOB = itemView.findViewById(R.id.cbBYOB2);
            cbBar = itemView.findViewById(R.id.cbBar2);
            cardView = itemView.findViewById(R.id.card_view);

        }
    }

    private List<Party> partyList;
    private Context context;
    private int lastPosition = -1;
    private DatabaseReference partyRef;
    private List<String> partyKeys;

    public PartiesAdapter(Context context) {
        this.context = context;

        partyKeys = new ArrayList<String>();
        partyList = new ArrayList<Party>();
        partyRef = FirebaseDatabase.getInstance().getReference("parties");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_row, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        Party party = partyList.get(position);

        viewHolder.tvPartyName.setText(party.getName());
        viewHolder.tvDescription.setText(party.getDescription());
        viewHolder.cbBYOB.setChecked(party.isByob());
        viewHolder.cbBar.setChecked(party.isBar());


        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeParty(viewHolder.getAdapterPosition());
            }
        });

        setAnimation(viewHolder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return partyList.size();
    }


    public void removeParty(int index) {
        partyRef.child(partyKeys.get(index)).removeValue();
        partyList.remove(index);
        partyKeys.remove(index);
        notifyItemRemoved(index);
    }

    public void addParty(Party party, String key){
        partyList.add(party);
        partyKeys.add(key);
        notifyDataSetChanged();
    }

    public Party getParty(int i) {    return partyList.get(i);
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}

