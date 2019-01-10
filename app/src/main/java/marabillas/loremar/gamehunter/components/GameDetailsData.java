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

import java.util.Set;

public class GameDetailsData {
    private String title;
    private String description;
    private Set<String> platforms;
    private Set<String> genres;
    private Set<String> themes;
    private String releaseDate;
    private Set<String> developers;
    private Set<String> publishers;
    private Set<String> characters;
    private String plot;
    private String gameplay;
    private boolean singleplayer;
    private boolean multiplayer;
    private String reception;
    private String license;
    private String website;
    private Set<String> ratings;
    private Set<String> images;

    public String getTitle() {
        return title;
    }

    public GameDetailsData setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public GameDetailsData setDescription(String description) {
        this.description = description;
        return this;
    }

    public Set<String> getPlatforms() {
        return platforms;
    }

    public GameDetailsData setPlatforms(Set<String> platforms) {
        this.platforms = platforms;
        return this;
    }

    public Set<String> getGenres() {
        return genres;
    }

    public GameDetailsData setGenres(Set<String> genres) {
        this.genres = genres;
        return this;
    }

    public Set<String> getThemes() {
        return themes;
    }

    public GameDetailsData setThemes(Set<String> themes) {
        this.themes = themes;
        return this;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public GameDetailsData setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    public Set<String> getDevelopers() {
        return developers;
    }

    public GameDetailsData setDevelopers(Set<String> developers) {
        this.developers = developers;
        return this;
    }

    public Set<String> getPublishers() {
        return publishers;
    }

    public GameDetailsData setPublishers(Set<String> publishers) {
        this.publishers = publishers;
        return this;
    }

    public Set<String> getCharacters() {
        return characters;
    }

    public GameDetailsData setCharacters(Set<String> characters) {
        this.characters = characters;
        return this;
    }

    public String getPlot() {
        return plot;
    }

    public GameDetailsData setPlot(String plot) {
        this.plot = plot;
        return this;
    }

    public String getGameplay() {
        return gameplay;
    }

    public GameDetailsData setGameplay(String gameplay) {
        this.gameplay = gameplay;
        return this;
    }

    public boolean isSingleplayer() {
        return singleplayer;
    }

    public GameDetailsData setSingleplayer(boolean singleplayer) {
        this.singleplayer = singleplayer;
        return this;
    }

    public boolean isMultiplayer() {
        return multiplayer;
    }

    public GameDetailsData setMultiplayer(boolean multiplayer) {
        this.multiplayer = multiplayer;
        return this;
    }

    public String getReception() {
        return reception;
    }

    public GameDetailsData setReception(String reception) {
        this.reception = reception;
        return this;
    }

    public String getLicense() {
        return license;
    }

    public GameDetailsData setLicense(String license) {
        this.license = license;
        return this;
    }

    public String getWebsite() {
        return website;
    }

    public GameDetailsData setWebsite(String website) {
        this.website = website;
        return this;
    }

    public Set<String> getRatings() {
        return ratings;
    }

    public GameDetailsData setRatings(Set<String> ratings) {
        this.ratings = ratings;
        return this;
    }

    public Set<String> getImages() {
        return images;
    }

    public GameDetailsData setImages(Set<String> images) {
        this.images = images;
        return this;
    }
}
