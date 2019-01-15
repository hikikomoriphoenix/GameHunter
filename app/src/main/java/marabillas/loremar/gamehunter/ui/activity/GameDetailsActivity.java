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

import marabillas.loremar.gamehunter.R;
import marabillas.loremar.gamehunter.apis.APIFactory;
import marabillas.loremar.gamehunter.apis.BaseAPI;
import marabillas.loremar.gamehunter.components.GameDetailsViewModel;
import marabillas.loremar.gamehunter.databinding.ActivityGameDetailsBinding;

public class GameDetailsActivity extends AppCompatActivity {
    private ActivityGameDetailsBinding binding;
    private GameDetailsViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout
                .activity_game_details);
        binding.setLifecycleOwner(this);

        viewModel = ViewModelProviders.of(this).get(GameDetailsViewModel
                .class);
        binding.setViewModel(viewModel);

        String site = getIntent().getStringExtra("site");


        BaseAPI api = APIFactory.getAPI(site);
        viewModel.setApi(api);
    }

    @Override
    protected void onStart() {
        super.onStart();

        setHeaderTexts();

        String gameId = getIntent().getStringExtra("game_id");
        viewModel.fetchGameDetails(gameId);
    }

    private void setHeaderTexts() {
        binding.setPlotHeaderText("Plot");
        binding.setCharactersHeaderText("Characters");
        binding.setGameplayHeaderText("Gameplay");
        binding.setReceptionHeaderText("Reception");
        binding.setReleaseDateHeaderText("Release Date");
        binding.setDevelopersHeaderText("Developers");
        binding.setPublishersHeaderText("Publishers");
        binding.setPlatformsHeaderText("Platforms");
        binding.setGenresHeaderText("Genres");
        binding.setThemesHeaderText("Themes");
        binding.setSingleplayerMultiplayerHeaderText("Singleplayer/Multiplayer");
        binding.setRatingsHeaderText("Ratings");
        binding.setWebsiteHeaderText("Website");
        binding.setLicenseHeaderText("License");
    }
}
