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

import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import marabillas.loremar.gamehunter.program.ResultsItem;

import static marabillas.loremar.gamehunter.utils.LogUtils.log;

public class APICallbackTest implements APICallback {
    private CountDownLatch countDownLatch;
    private Set<String> platformFilters;
    private List<ResultsItem> results;

    @Override
    public void onPlatformFiltersObtained(Set<String> platformFilters) {
        log("Platform filters obtained.");
        this.platformFilters = platformFilters;
        if (countDownLatch != null) {
            countDownLatch.countDown();
        }
    }

    Set<String> getPlatformFilters() {
        return platformFilters;
    }

    @Override
    public void onQueryResults(List<ResultsItem> results) {
        log("Query results obtained.");
        this.results = results;
        if (countDownLatch != null) {
            countDownLatch.countDown();
        }
    }

    List<ResultsItem> getResults() {
        return results;
    }

    void setCountDownLatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }
}