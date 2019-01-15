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

package marabillas.loremar.gamehunter.components;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.Set;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import marabillas.loremar.gamehunter.apis.BaseAPI;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static marabillas.loremar.gamehunter.utils.LogUtils.log;

public class GameDetailsViewModel extends ViewModel {
    // Visibility values for each component
    public MutableLiveData<Integer> mainImageVisibility = new MutableLiveData<>();
    public MutableLiveData<Integer> descriptionVisibility = new MutableLiveData<>();
    public MutableLiveData<Integer> plotVisibility = new MutableLiveData<>();
    public MutableLiveData<Integer> charactersVisibility = new MutableLiveData<>();
    public MutableLiveData<Integer> gameplayVisibility = new MutableLiveData<>();
    public MutableLiveData<Integer> receptionVisibility = new MutableLiveData<>();
    public MutableLiveData<Integer> releaseDateVisibility = new MutableLiveData<>();
    public MutableLiveData<Integer> developersVisibility = new MutableLiveData<>();
    public MutableLiveData<Integer> publishersVisibility = new MutableLiveData<>();
    public MutableLiveData<Integer> platformsVisibility = new MutableLiveData<>();
    public MutableLiveData<Integer> genresVisibility = new MutableLiveData<>();
    public MutableLiveData<Integer> themesVisibility = new MutableLiveData<>();
    public MutableLiveData<Integer> singleplayerMultiplayerVisibility = new MutableLiveData<>();
    public MutableLiveData<Integer> ratingsVisibility = new MutableLiveData<>();
    public MutableLiveData<Integer> websiteVisibility = new MutableLiveData<>();
    public MutableLiveData<Integer> licenseVisibility = new MutableLiveData<>();

    // Content values for each component
    public MutableLiveData<String> title = new MutableLiveData<>();
    public MutableLiveData<String> mainImageURL = new MutableLiveData<>();
    public MutableLiveData<String> description = new MutableLiveData<>();
    public MutableLiveData<String> plot = new MutableLiveData<>();
    public MutableLiveData<String> characters = new MutableLiveData<>();
    public MutableLiveData<String> gameplay = new MutableLiveData<>();
    public MutableLiveData<String> reception = new MutableLiveData<>();
    public MutableLiveData<String> releaseDate = new MutableLiveData<>();
    public MutableLiveData<String> developers = new MutableLiveData<>();
    public MutableLiveData<String> publishers = new MutableLiveData<>();
    public MutableLiveData<String> platforms = new MutableLiveData<>();
    public MutableLiveData<String> genres = new MutableLiveData<>();
    public MutableLiveData<String> themes = new MutableLiveData<>();
    public MutableLiveData<String> singleplayerMultiplayer = new MutableLiveData<>();
    public MutableLiveData<String> ratings = new MutableLiveData<>();
    public MutableLiveData<String> website = new MutableLiveData<>();
    public MutableLiveData<String> license = new MutableLiveData<>();

    private BaseAPI api;
    private Disposable disposable;

    public void setApi(BaseAPI api) {
        this.api = api;
    }

    public void fetchGameDetails(String gameId) {
        disposable = api.getGameDetails(gameId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(gameDetailsData -> {
                    log("game details fetched");
                    title.setValue(gameDetailsData.getTitle());

                    if (gameDetailsData.getMainImage() != null) {
                        mainImageVisibility.setValue(VISIBLE);
                        mainImageURL.setValue(gameDetailsData.getMainImage());
                    } else {
                        mainImageVisibility.setValue(GONE);
                        mainImageURL.setValue(null);
                    }

                    if (gameDetailsData.getDescription() != null) {
                        descriptionVisibility.setValue(VISIBLE);
                        description.setValue(gameDetailsData.getDescription());
                    } else {
                        descriptionVisibility.setValue(GONE);
                    }

                    if (gameDetailsData.getPlot() != null) {
                        plotVisibility.setValue(VISIBLE);
                        plot.setValue(gameDetailsData.getPlot());
                    } else {
                        plotVisibility.setValue(GONE);
                    }

                    if (gameDetailsData.getCharacters().size() > 0) {
                        charactersVisibility.setValue(VISIBLE);
                        String s = getStringOfListFromSet(gameDetailsData.getCharacters());
                        characters.setValue(s);
                    }

                    if (gameDetailsData.getGameplay() != null) {
                        gameplayVisibility.setValue(VISIBLE);
                        gameplay.setValue(gameDetailsData.getGameplay());
                    } else {
                        gameplayVisibility.setValue(GONE);
                    }

                    if (gameDetailsData.getReception() != null) {
                        receptionVisibility.setValue(VISIBLE);
                        reception.setValue(gameDetailsData.getReception());
                    } else {
                        receptionVisibility.setValue(GONE);
                    }

                    if (gameDetailsData.getReleaseDate() != null) {
                        releaseDateVisibility.setValue(VISIBLE);
                        releaseDate.setValue(gameDetailsData.getReleaseDate());
                    } else {
                        releaseDateVisibility.setValue(GONE);
                    }

                    if (gameDetailsData.getDevelopers().size() > 0) {
                        developersVisibility.setValue(VISIBLE);
                        String s = getStringOfListFromSet(gameDetailsData.getDevelopers());
                        developers.setValue(s);
                    } else {
                        developersVisibility.setValue(GONE);
                    }

                    if (gameDetailsData.getPublishers().size() > 0) {
                        publishersVisibility.setValue(VISIBLE);
                        String s = getStringOfListFromSet(gameDetailsData.getPublishers());
                        publishers.setValue(s);
                    } else {
                        publishersVisibility.setValue(GONE);
                    }

                    if (gameDetailsData.getPlatforms().size() > 0) {
                        platformsVisibility.setValue(VISIBLE);
                        String s = getStringOfListFromSet(gameDetailsData.getPlatforms());
                        platforms.setValue(s);
                    } else {
                        platformsVisibility.setValue(GONE);
                    }

                    if (gameDetailsData.getGenres().size() > 0) {
                        genresVisibility.setValue(VISIBLE);
                        String s = getStringOfListFromSet(gameDetailsData.getGenres());
                        genres.setValue(s);
                    } else {
                        genresVisibility.setValue(GONE);
                    }

                    if (gameDetailsData.getThemes().size() > 0) {
                        themesVisibility.setValue(VISIBLE);
                        String s = getStringOfListFromSet(gameDetailsData.getThemes());
                        themes.setValue(s);
                    } else {
                        themesVisibility.setValue(GONE);
                    }

                    if (!gameDetailsData.isSingleplayer() && !gameDetailsData.isMultiplayer()) {
                        singleplayerMultiplayerVisibility.setValue(GONE);
                    } else {
                        singleplayerMultiplayerVisibility.setValue(VISIBLE);

                        StringBuilder sb = new StringBuilder();
                        if (gameDetailsData.isSingleplayer()) {
                            sb.append("Singleplayer");
                            if (gameDetailsData.isMultiplayer()) {
                                sb.append("\nMultiplayer");
                            }
                        } else {
                            sb.append("Multiplayer");
                        }
                    }

                    if (gameDetailsData.getRatings().size() > 0) {
                        ratingsVisibility.setValue(VISIBLE);
                        String s = getStringOfListFromSet(gameDetailsData.getRatings());
                        ratings.setValue(s);
                    } else {
                        ratingsVisibility.setValue(GONE);
                    }

                    if (gameDetailsData.getWebsite() != null) {
                        websiteVisibility.setValue(VISIBLE);
                        website.setValue(gameDetailsData.getWebsite());
                    } else {
                        websiteVisibility.setValue(GONE);
                    }

                    if (gameDetailsData.getLicense() != null) {
                        licenseVisibility.setValue(VISIBLE);
                        license.setValue(gameDetailsData.getLicense());
                    } else {
                        licenseVisibility.setValue(GONE);
                    }

                    disposable.dispose();
                    disposable = null;
                });
    }

    private String getStringOfListFromSet(Set<String> items) {
        String[] a = new String[items.size()];
        items.toArray(a);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < a.length; ++i) {
            if (i == 0) {
                sb.append(a[i]);
            } else {
                sb.append("\n").append(a[i]);
            }
        }
        return sb.toString();
    }
}
