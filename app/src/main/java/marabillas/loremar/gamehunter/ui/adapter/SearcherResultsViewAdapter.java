/*
 *     GameHunter is an Android app for searching video games
 *     Copyright (C) 2018 Loremar Marabillas
 *
 *     This program is free software; you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation; either version 2 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License along
 *     with this program; if not, write to the Free Software Foundation, Inc.,
 *     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package marabillas.loremar.gamehunter.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import marabillas.loremar.gamehunter.R;
import marabillas.loremar.gamehunter.components.ResultsItem;
import marabillas.loremar.gamehunter.databinding.ActivitySearcherResultsViewItemBinding;

public class SearcherResultsViewAdapter extends RecyclerView.Adapter<SearcherResultsViewAdapter.SearcherResultsItemViewHolder> {
    private List<ResultsItem> results;

    public SearcherResultsViewAdapter() {
        results = new ArrayList<>();
    }

    @NonNull
    @Override
    public SearcherResultsItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ActivitySearcherResultsViewItemBinding binding = DataBindingUtil.inflate(inflater, R.layout
                .activity_searcher_results_view_item, parent, false);
        return new SearcherResultsItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearcherResultsItemViewHolder holder, int position) {
        ResultsItem resultsItem = results.get(position);
        holder.getBinding().setResultsItem(resultsItem);

        Context context = holder.getBinding().getRoot().getContext();
        Drawable drawable;
        int textColor;
        boolean multiple2 = position % 2 == 0;
        if (multiple2) {
            drawable = context.getResources().getDrawable(R.drawable
                    .background_searcher_results_item_white);
            textColor = ResourcesCompat.getColor(context.getResources(), R.color.lighterBlack,
                    null);
        } else {
            drawable = context.getResources().getDrawable(R.drawable
                    .background_searcher_results_item_black);
            textColor = ResourcesCompat.getColor(context.getResources(), R.color.white, null);
        }

        ViewCompat.setBackground(holder.getBinding().searcherResultsViewItem, drawable);
        holder.getBinding().activitySearcherResultsViewItemTitle.setTextColor(textColor);
        holder.getBinding().activitySearcherResultsViewItemDescription.setTextColor(textColor);
        holder.getBinding().activitySearcherResultsViewItemReleaseDate.setTextColor(textColor);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void updateList(List<ResultsItem> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    class SearcherResultsItemViewHolder extends RecyclerView.ViewHolder {
        private ActivitySearcherResultsViewItemBinding binding;

        public SearcherResultsItemViewHolder(ActivitySearcherResultsViewItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ActivitySearcherResultsViewItemBinding getBinding() {
            return binding;
        }
    }
}
