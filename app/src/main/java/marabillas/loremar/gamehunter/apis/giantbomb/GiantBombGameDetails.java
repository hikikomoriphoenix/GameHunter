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

package marabillas.loremar.gamehunter.apis.giantbomb;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GiantBombGameDetails {
    @SerializedName("name")
    private String title;

    @SerializedName("image")
    private GiantBombImageItem mainImage;

    @SerializedName("description")
    private String description;

    @SerializedName("platforms")
    @Expose
    private ArrayList<GiantBombGamePlatform> platforms;

    @SerializedName("genres")
    @Expose
    private ArrayList<GiantBombGameGenre> genres;

    @SerializedName("themes")
    @Expose
    private ArrayList<GiantBombGameTheme> themes;

    @SerializedName("original_release_date")
    private String originalReleaseDate;

    @SerializedName("expected_release_year")
    private String expectedReleaseYear;

    @SerializedName("developers")
    @Expose
    private ArrayList<GiantBombGameDeveloper> developers;

    @SerializedName("publishers")
    @Expose
    private ArrayList<GiantBombGamePublisher> publishers;

    @SerializedName("characters")
    @Expose
    private ArrayList<GiantBombGameCharacter> characters;

    @SerializedName("images")
    @Expose
    private ArrayList<GiantBombImagesItem> images;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public GiantBombImageItem getMainImage() {
        return mainImage;
    }

    public void setMainImage(GiantBombImageItem mainImage) {
        this.mainImage = mainImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<GiantBombGamePlatform> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(ArrayList<GiantBombGamePlatform> platforms) {
        this.platforms = platforms;
    }

    public ArrayList<GiantBombGameGenre> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<GiantBombGameGenre> genres) {
        this.genres = genres;
    }

    public ArrayList<GiantBombGameTheme> getThemes() {
        return themes;
    }

    public void setThemes(ArrayList<GiantBombGameTheme> themes) {
        this.themes = themes;
    }

    public String getOriginalReleaseDate() {
        return originalReleaseDate;
    }

    public void setOriginalReleaseDate(String originalReleaseDate) {
        this.originalReleaseDate = originalReleaseDate;
    }

    public String getExpectedReleaseYear() {
        return expectedReleaseYear;
    }

    public void setExpectedReleaseYear(String expectedReleaseYear) {
        this.expectedReleaseYear = expectedReleaseYear;
    }

    public ArrayList<GiantBombGameDeveloper> getDevelopers() {
        return developers;
    }

    public void setDevelopers(ArrayList<GiantBombGameDeveloper> developers) {
        this.developers = developers;
    }

    public ArrayList<GiantBombGamePublisher> getPublishers() {
        return publishers;
    }

    public void setPublishers(ArrayList<GiantBombGamePublisher> publishers) {
        this.publishers = publishers;
    }

    public ArrayList<GiantBombGameCharacter> getCharacters() {
        return characters;
    }

    public void setCharacters(ArrayList<GiantBombGameCharacter> characters) {
        this.characters = characters;
    }

    public ArrayList<GiantBombImagesItem> getImages() {
        return images;
    }

    public void setImages(ArrayList<GiantBombImagesItem> images) {
        this.images = images;
    }
}
