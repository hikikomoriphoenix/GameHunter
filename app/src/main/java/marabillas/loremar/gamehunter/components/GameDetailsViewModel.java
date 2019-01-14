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

public class GameDetailsViewModel extends ViewModel {
    // Visibility values for each component
    public MutableLiveData<Integer> mainImageVisibility = new MutableLiveData<>();
    public MutableLiveData<Integer> descriptionVisibility = new MutableLiveData<>();
    public MutableLiveData<Integer> plotVisibility = new MutableLiveData<>();
    public MutableLiveData<Integer> charactersVisibility = new MutableLiveData<>();
    public MutableLiveData<Integer> gameplayVisibility = new MutableLiveData<>();
    public MutableLiveData<Integer> receptionVisibiliy = new MutableLiveData<>();
    public MutableLiveData<Integer> releaseDateVisibility = new MutableLiveData<>();
    public MutableLiveData<Integer> developersVisibility = new MutableLiveData<>();
    public MutableLiveData<Integer> publishersVisibility = new MutableLiveData<>();
    public MutableLiveData<Integer> platformsVisibility = new MutableLiveData<>();
    public MutableLiveData<Integer> genresVisibility = new MutableLiveData<>();
    public MutableLiveData<Integer> themesVisibility = new MutableLiveData<>();
    public MutableLiveData<Integer> singleplayerMultiplayerVisibility = new MutableLiveData<>();
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
    public MutableLiveData<Boolean> singleplayerMultiplayer = new MutableLiveData<>();
    public MutableLiveData<String> website = new MutableLiveData<>();
    public MutableLiveData<String> license = new MutableLiveData<>();
}
