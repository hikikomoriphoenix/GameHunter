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

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface GiantBombInterface {
    @GET("platforms/?format=json&field_list=name,id")
    Observable<GiantBombResponse<GiantBombPlatformFilter>> getPlatformFilters(
            @Query("api_key") String key,
            @Query("offset") int offset
    );

    @GET("search/?format=json&resources=game")
    Observable<GiantBombResponse<GiantBombQueryResultsItem>> search(
            @QueryMap Map<String, String> queryMap
    );

    @GET("games/?format=json")
    Observable<GiantBombResponse<GiantBombQueryResultsItem>> getGames(
            @QueryMap Map<String, String> queryMap
    );
}
