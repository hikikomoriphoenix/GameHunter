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

public class GiantBombGameDetailsResponse {
    @SerializedName("status_code")
    private int statusCode;

    @SerializedName("error")
    private String error;

    @SerializedName("results")
    @Expose
    private GiantBombGameDetails gameDetails;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public GiantBombGameDetails getGameDetails() {
        return gameDetails;
    }

    public void setGameDetails(GiantBombGameDetails gameDetails) {
        this.gameDetails = gameDetails;
    }
}
