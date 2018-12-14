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

package marabillas.loremar.gamehunter.activity;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import marabillas.loremar.gamehunter.ui.activity.SearcherActivity;

public class SearcherActivityTest {
    @Rule
    public ActivityTestRule<SearcherActivity> searcherActivityTestRule = new ActivityTestRule<>
            (SearcherActivity.class, false, false);

    @Test
    public void test() {
        Intent intent = new Intent();
        intent.putExtra("site", "Giant Bomb");
        searcherActivityTestRule.launchActivity(intent);

        CountDownLatch cd = new CountDownLatch(1);
        try {
            cd.await();
        } catch (InterruptedException e) {
            Assert.fail("Await interrupted: " + e.getMessage());
        }
    }
}
