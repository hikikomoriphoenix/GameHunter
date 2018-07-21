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

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedHashSet;
import java.util.Set;

import marabillas.loremar.gamehunter.R;
import marabillas.loremar.gamehunter.ui.activity.MainActivity;

/**
 * This adapter is for setting up a grid layout containing thumbnails for the selectable websites
 * . Clicking a thumbnail would notify the MainActivity that a website has been selected. The  user
 * can also click the link below the thumbnail to go to the website's homepage.
 */
public class WebsiteSelectionAdapter extends RecyclerView.Adapter<WebsiteSelectionAdapter
        .WebsiteSelectionViewHolder> {
    private MainActivity activity;
    private Set<WebsiteButton> websiteButtons;

    public WebsiteSelectionAdapter(MainActivity activity) {
        this.activity = activity;

        websiteButtons = new LinkedHashSet<>();

        // Prepare the data
        Resources resources = this.activity.getResources();
        TypedArray drawables = resources.obtainTypedArray(R.array.arrays_websites_drawables);
        String[] labels = resources.getStringArray(R.array.arrays_websites_labels);
        String[] urls = resources.getStringArray(R.array.arrays_websites_urls);

        // Make sure that each element has a drawable, label, and url
        boolean dl = drawables.length() != labels.length;
        boolean lu = labels.length != urls.length;
        if (dl || lu) {
            throw new RuntimeException("The number of elements for selectible websites didn't " +
                    "match.");
        }

        // Populate the data
        for (int i = 0; i < drawables.length(); ++i) {
            WebsiteButton wb = new WebsiteButton();
            wb.drawable = drawables.getDrawable(i);
            wb.label = labels[i];
            wb.url = urls[i];
            websiteButtons.add(wb);
        }

        drawables.recycle(); // Required for TypedArray
    }

    @NonNull
    @Override
    public WebsiteSelectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View itemView = inflater.inflate(R.layout.adapter_website_selection_item_view, parent, false);
        return new WebsiteSelectionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WebsiteSelectionViewHolder holder, int position) {
        holder.bind((WebsiteButton) websiteButtons.toArray()[position]);
    }

    @Override
    public int getItemCount() {
        return websiteButtons.size();
    }

    /**
     * ViewHolder for each of WebsiteSelectionAdapter's items
     */
    class WebsiteSelectionViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;

        WebsiteSelectionViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.adapter_website_selection_item_view_logo);
            textView = itemView.findViewById(R.id.adapter_website_selection_item_view_label);
            imageView.setOnClickListener(activity);
        }

        void bind(WebsiteButton wb) {
            // Thumbnail with tag
            imageView.setImageDrawable(wb.drawable);
            String tag = wb.label;
            imageView.setTag(tag);

            // Transforms the label with hyperlink format. Linkify looks for links and makes them
            // clickable, with clicks opening view intent.
            String caption = "<a href=" + wb.url + ">" + wb.label + "</a>";
            textView.setText(Html.fromHtml(caption));
            Linkify.addLinks(textView, Linkify.WEB_URLS);
            textView.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    // A single element of the data corresponding to a website for selection
    private class WebsiteButton {
        Drawable drawable;
        String label, url;
    }
}
