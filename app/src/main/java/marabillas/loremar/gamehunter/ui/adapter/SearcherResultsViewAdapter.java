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

import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.ListPreloader;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.util.ViewPreloadSizeProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import marabillas.loremar.gamehunter.GlideRequest;
import marabillas.loremar.gamehunter.R;
import marabillas.loremar.gamehunter.components.ResultsItem;
import marabillas.loremar.gamehunter.databinding.ActivitySearcherResultsViewItemBinding;

import static marabillas.loremar.gamehunter.ui.components.DarkOrLightLayout.Theme.DARK;
import static marabillas.loremar.gamehunter.ui.components.DarkOrLightLayout.Theme.LIGHT;

public class SearcherResultsViewAdapter extends RecyclerView.Adapter<SearcherResultsViewAdapter
        .SearcherResultsItemViewHolder> implements ListPreloader.PreloadModelProvider<ResultsItem> {
    private ViewPreloadSizeProvider<ResultsItem> sizeProvider;
    private GlideRequest<Drawable> glideRequest;
    private List<ResultsItem> results;

    public SearcherResultsViewAdapter(GlideRequest<Drawable> glideRequest,
                                      ViewPreloadSizeProvider<ResultsItem> sizeProvider) {
        this.glideRequest = glideRequest;
        this.sizeProvider = sizeProvider;
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

        boolean multiple2 = position % 2 == 0;
        if (multiple2) {
            holder.getBinding().searcherResultsViewItem.setTheme(LIGHT);
        } else {
            holder.getBinding().searcherResultsViewItem.setTheme(DARK);
        }
        int textColor = holder.getBinding().searcherResultsViewItem.getDefaultTextColor();
        holder.getBinding().setTextColor(textColor);

        glideRequest
                .load(resultsItem.thumbnailURL)
                .into(holder.getBinding().activitySearcherResultsViewItemImage);

        sizeProvider.setView(holder.getBinding().activitySearcherResultsViewItemImage);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void updateList(List<ResultsItem> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public List<ResultsItem> getPreloadItems(int position) {
        return Collections.singletonList(results.get(position));
    }

    @Nullable
    @Override
    public RequestBuilder<?> getPreloadRequestBuilder(@NonNull ResultsItem item) {
        return glideRequest.load(item.thumbnailURL);
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
