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

package marabillas.loremar.gamehunter.apis;

import java.util.TreeMap;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GiantBombInterface {
    @GET("platforms/?api_key={KEY}&format=json&field_list=name,id&offset={OFFSET}")
    Observable<TreeMap<String, Integer>> getPlatformFilters(@Path("KEY") String key, @Path
            ("OFFSET") int offset);

    @GET("search/?api_key={KEY}&format=json&resources=game&query={KEYWORD}&limit={LIMIT}&page" +
            "={PAGE}")
    Observable<GiantBombResponse> search(@Path("KEY") String key, @Path("KEYWORD") String
            keyword, @Path("LIMIT") int limit, @Path("PAGE") int page);

    @GET("games/?api_key={KEY}&format=json&limit={LIMIT}&page={PAGE}{FILTER}{SORT}")
    Observable<GiantBombResponse> getGames(@Path("KEY") String key, @Path("LIMIT") int limit,
                                           @Path("PAGE") int page, @Path("FILTER") String filter,
                                           @Path("SORT") String sort);
}
