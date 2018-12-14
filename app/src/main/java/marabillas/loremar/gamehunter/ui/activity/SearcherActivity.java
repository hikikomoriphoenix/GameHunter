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

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.NumberPicker;

import marabillas.loremar.gamehunter.R;
import marabillas.loremar.gamehunter.apis.APIFactory;
import marabillas.loremar.gamehunter.apis.BaseAPI;
import marabillas.loremar.gamehunter.components.SearcherEvent;
import marabillas.loremar.gamehunter.components.SearcherViewModel;
import marabillas.loremar.gamehunter.databinding.ActivitySearcherBinding;
import marabillas.loremar.gamehunter.ui.components.ProgressView;

import static marabillas.loremar.gamehunter.utils.UIUtils.setNumberPickerDividerColor;

public class SearcherActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {
    private SearcherViewModel viewModel;
    private ActivitySearcherBinding binding;
    private Menu menu;
    private ProgressView progressView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String site = getIntent().getStringExtra("site");
        BaseAPI api = APIFactory.getAPI(site);

        viewModel = ViewModelProviders.of(this).get(SearcherViewModel.class);
        viewModel.setApi(api);
        viewModel.eventBus.observe(this, this::handleEvent);

        binding = DataBindingUtil.setContentView(this, R.layout
                .activity_searcher);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        binding.searcherToolbar.inflateMenu(R.menu.searcher_menu);
        binding.searcherToolbar.setOnMenuItemClickListener(this);
        menu = binding.searcherToolbar.getMenu();

        int color;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            color = getColor(R.color.white);
        } else {
            color = getResources().getColor(R.color.darkRed);
        }
        NumberPicker picker1 = findViewById(R.id.activity_searcher_options_fromyear);
        NumberPicker picker2 = findViewById(R.id.activity_searcher_options_toyear);
        setNumberPickerDividerColor(picker1, color);
        setNumberPickerDividerColor(picker2, color);

        progressView = new ProgressView(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        progressView.show();
        viewModel.init();
    }

    private void handleEvent(SearcherEvent event) {
        switch (event) {
            case HIDE_SEARCH_ICON:
                MenuItem searchTool = menu.findItem(R.id.searcher_menu_search);
                searchTool.setVisible(false);
                break;
            case HIDE_SEARCH_OPTIONS_ICON:
                MenuItem searchOptionsTool = menu.findItem(R.id.searcher_menu_searchoptions);
                searchOptionsTool.setVisible(false);
                break;
            case HIDE_PROGRESS_VIEW:
                progressView.dismiss();
                break;
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }
}
