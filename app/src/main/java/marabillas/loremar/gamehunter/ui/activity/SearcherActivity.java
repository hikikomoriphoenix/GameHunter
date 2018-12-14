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
import android.widget.NumberPicker;

import marabillas.loremar.gamehunter.R;
import marabillas.loremar.gamehunter.apis.APIFactory;
import marabillas.loremar.gamehunter.apis.BaseAPI;
import marabillas.loremar.gamehunter.components.SearcherViewModel;
import marabillas.loremar.gamehunter.databinding.ActivitySearcherBinding;

import static marabillas.loremar.gamehunter.utils.UIUtils.setNumberPickerDividerColor;

public class SearcherActivity extends AppCompatActivity {
    private SearcherViewModel viewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String site = getIntent().getStringExtra("site");
        BaseAPI api = APIFactory.getAPI(site);
        viewModel = ViewModelProviders.of(this).get(SearcherViewModel.class);
        viewModel.setApi(api);

        ActivitySearcherBinding binding = DataBindingUtil.setContentView(this, R.layout
                .activity_searcher);
        binding.searcherToolbar.inflateMenu(R.menu.searcher_menu);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

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
    }
}
