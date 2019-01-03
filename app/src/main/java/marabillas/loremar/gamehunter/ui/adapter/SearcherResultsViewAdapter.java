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
import android.databinding.ViewDataBinding;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.ListPreloader;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.util.ViewPreloadSizeProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import marabillas.loremar.gamehunter.BR;
import marabillas.loremar.gamehunter.GlideRequest;
import marabillas.loremar.gamehunter.R;
import marabillas.loremar.gamehunter.components.ResultsItem;
import marabillas.loremar.gamehunter.ui.components.DarkOrLightLayout;

import static android.view.View.GONE;
import static marabillas.loremar.gamehunter.ui.components.DarkOrLightLayout.Theme.DARK;
import static marabillas.loremar.gamehunter.ui.components.DarkOrLightLayout.Theme.LIGHT;

public class SearcherResultsViewAdapter extends RecyclerView.Adapter<SearcherResultsViewAdapter
        .SearcherResultsItemViewHolder> implements ListPreloader.PreloadModelProvider<ResultsItem> {
    private ViewPreloadSizeProvider<ResultsItem> sizeProvider;
    private GlideRequest<Drawable> glideRequest;
    private List<ResultsItem> results;
    private int layoutID;
    private boolean displayThumbnail;
    private boolean displayDescription;
    private boolean displayReleaseDate;
    private int spanCount;

    public static class Builder {
        private ViewPreloadSizeProvider<ResultsItem> sizeProvider;
        private GlideRequest<Drawable> glideRequest;
        private int layoutID;
        private boolean displayThumbnail;
        private boolean displayDescription;
        private boolean displayReleaseDate;
        private int spanCount;

        public Builder setSizeProvider(ViewPreloadSizeProvider<ResultsItem> sizeProvider) {
            this.sizeProvider = sizeProvider;
            return this;
        }

        public Builder setGlideRequest(GlideRequest<Drawable> glideRequest) {
            this.glideRequest = glideRequest;
            return this;
        }

        public Builder setLayoutID(int layoutID) {
            this.layoutID = layoutID;
            return this;
        }

        public Builder setDisplayThumbnail(boolean displayThumbnail) {
            this.displayThumbnail = displayThumbnail;
            return this;
        }

        public Builder setDisplayDescription(boolean displayDescription) {
            this.displayDescription = displayDescription;
            return this;
        }

        public Builder setDisplayReleaseDate(boolean displayReleaseDate) {
            this.displayReleaseDate = displayReleaseDate;
            return this;
        }

        public Builder setSpanCount(int spanCount) {
            this.spanCount = spanCount;
            return this;
        }

        public SearcherResultsViewAdapter create() {
            return new SearcherResultsViewAdapter(glideRequest, sizeProvider, layoutID,
                    displayThumbnail, displayDescription, displayReleaseDate, spanCount);
        }
    }

    private SearcherResultsViewAdapter(GlideRequest<Drawable> glideRequest,
                                       ViewPreloadSizeProvider<ResultsItem> sizeProvider,
                                       int layoutID,
                                       boolean displayThumbnail,
                                       boolean displayDescription,
                                       boolean displayReleaseDate,
                                       int spanCount) {
        this.glideRequest = glideRequest;
        this.sizeProvider = sizeProvider;
        this.layoutID = layoutID;
        this.displayThumbnail = displayThumbnail;
        this.displayDescription = displayDescription;
        this.displayReleaseDate = displayReleaseDate;
        this.spanCount = spanCount;

        results = new ArrayList<>();
    }

    @NonNull
    @Override
    public SearcherResultsItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, layoutID, parent, false);

        if (!displayThumbnail) {
            binding.setVariable(BR.thumbnailVisibility, GONE);
        }

        return new SearcherResultsItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearcherResultsItemViewHolder holder, int position) {
        ResultsItem resultsItem = results.get(position);
        holder.getBinding().setVariable(BR.resultsItem, resultsItem);
        DarkOrLightLayout dol = holder.getBinding().getRoot().findViewById(R.id
                .searcher_results_view_item);

        if (spanCount > 0) {
            setDarkOrLightThemeForGridLayoutItem(position, spanCount, dol);
        } else {
            boolean multiple2 = position % 2 == 0;
            if (multiple2) {
                dol.setTheme(LIGHT);
            } else {
                dol.setTheme(DARK);
            }
        }

        int textColor = dol.getDefaultTextColor();
        holder.getBinding().setVariable(BR.textColor, textColor);

        if (resultsItem.thumbnailURL != null) {
            ImageView thumbnail = holder.getBinding().getRoot().findViewById(R.id
                    .activity_searcher_results_view_item_image);
            glideRequest
                    .load(resultsItem.thumbnailURL)
                    .into(thumbnail);

            sizeProvider.setView(thumbnail);
        }
    }

    private void setDarkOrLightThemeForGridLayoutItem(int position, int spanCount,
                                                      DarkOrLightLayout layout) {
        int row = position / spanCount;
        int column = position % spanCount;

        boolean rowEven = row % 2 == 0; // Row position is an even number
        boolean columnEven = column % 2 == 0; // Column position is an even number

        // Set theme to be alternatively light and dark for each item in the grid layout. This
        // makes the recycler view look like a checkerboard.
        if (rowEven && columnEven || !rowEven && !columnEven) {
            layout.setTheme(LIGHT);
        } else {
            layout.setTheme(DARK);
        }
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
        private ViewDataBinding binding;

        public SearcherResultsItemViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ViewDataBinding getBinding() {
            return binding;
        }
    }
}
