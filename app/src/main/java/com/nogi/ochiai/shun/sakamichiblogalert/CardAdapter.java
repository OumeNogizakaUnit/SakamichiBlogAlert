package com.nogi.ochiai.shun.sakamichiblogalert;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;
import java.util.List;

/**
 * Created by shun on 2017/04/29.
 */

public class CardAdapter extends ArrayAdapter<Card> {

    private List<Card> cardList;

    public CardAdapter(Context context, int resourceID, List<Card> cardList) {
        super(context,resourceID,cardList);
        this.cardList = cardList;
    }

    @Override
    public int getCount() {
        return cardList.size();
    }

    @Override
    public Card getItem(int position) {
        return cardList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.card, null);
            viewHolder  = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Card CARDINFO = getItem(position);
        if (CARDINFO != null) {
            viewHolder.getAuth().setText(CARDINFO.getAuth());
            viewHolder.getTitle().setText(CARDINFO.getTitle());
            viewHolder.getURL().setText(CARDINFO.getURL());
            viewHolder.getDateString().setText(CARDINFO.getDateString());

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), CARDINFO.getTitle(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        return convertView;
    }
    private class ViewHolder {
        private TextView Auth = null;
        private TextView Title = null;
        private TextView URL  = null;
        private TextView DateString  = null;

        public ViewHolder(View view) {
            Auth = (TextView)view.findViewById(R.id.AuthInCard);
            Title  = (TextView) view.findViewById(R.id.TitleInCard);
            URL = (TextView) view.findViewById(R.id.URLInCard);
            DateString  = (TextView) view.findViewById(R.id.DateStringInCard);
        }

        public TextView getAuth() {
            return Auth;
        }

        public TextView getTitle() {
            return Title;
        }

        public TextView getURL() {
            return URL;
        }

        public TextView getDateString() {
            return DateString;
        }

    }
