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

package marabillas.loremar.gamehunter.ui.activity;

import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.text.Html;
import android.text.Spanned;

import java.util.ArrayList;
import java.util.List;

import marabillas.loremar.gamehunter.R;
import marabillas.loremar.gamehunter.components.GameSiteViewModel;
import marabillas.loremar.gamehunter.databinding.ActivitySiteChooserBinding;
import marabillas.loremar.gamehunter.ui.adapter.SiteChooserAdapter;

import static marabillas.loremar.gamehunter.utils.MeasurementUtils.convertPixelsToDp;
import static marabillas.loremar.gamehunter.utils.MeasurementUtils.getScreenWidthInPixels;

/**
 * Activity for selecting the site which hosts the game database the user wants to search into.
 */
public class SiteChooserActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivitySiteChooserBinding binding = DataBindingUtil.setContentView(this, R.layout
                .activity_site_chooser);

        // Get resource values necessary for the game sites
        TypedArray drawables = getResources().obtainTypedArray(R.array.arrays_websites_drawables);
        String[] labels = getResources().getStringArray(R.array.arrays_websites_labels);
        String[] urls = getResources().getStringArray(R.array.arrays_websites_urls);

        // Make sure that each element has a drawable, label, and url
        boolean dl = drawables.length() != labels.length;
        boolean lu = labels.length != urls.length;
        if (dl || lu) {
            throw new RuntimeException("The number of elements for selectible websites didn't " +
                    "match.");
        }

        // Initialize the view model bindings for each site
        List<GameSiteViewModel> sites = new ArrayList<>();
        for (int i = 0; i < drawables.length(); ++i) {
            GameSiteViewModel gs = new GameSiteViewModel();

            gs.drawable.setValue(drawables.getDrawable(i));
            gs.tag.setValue(labels[i]);

            String hyperlink = "<a href=" + urls[i] + ">" + labels[i] + "</a>";
            Spanned label = Html.fromHtml(hyperlink);
            gs.label.setValue(label);

            sites.add(gs);
        }
        drawables.recycle(); // Required for TypedArray

        // The following code enforces maximum width for each element
        int screenWidthInPixels = getScreenWidthInPixels();
        float screenWidthInDp = convertPixelsToDp(screenWidthInPixels, this);
        int spanCount = (int) (screenWidthInDp / 180);
        if (screenWidthInDp % 180 > 0) {
            ++spanCount;
        }

        // Set up recycler view
        SiteChooserAdapter adapter = new SiteChooserAdapter(sites);
        binding.activitySitechooserRecyclerview.setAdapter(adapter);
        binding.activitySitechooserRecyclerview.setLayoutManager(new GridLayoutManager(this, spanCount));
    }
}
