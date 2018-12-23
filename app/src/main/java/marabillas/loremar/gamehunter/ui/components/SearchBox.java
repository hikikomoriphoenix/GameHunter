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

package marabillas.loremar.gamehunter.ui.components;

import android.app.Service;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import marabillas.loremar.gamehunter.R;
import marabillas.loremar.gamehunter.databinding.ActivitySearcherSearchboxBinding;

public class SearchBox implements TextView.OnEditorActionListener {
    private Context context;
    private ActivitySearcherSearchboxBinding binding;
    private OnSearchBoxActionListener onSearchBoxActionListener;

    public SearchBox(Context context) {
        this.context = context;

        LayoutInflater inflater = LayoutInflater.from(context);
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_searcher_searchbox, null,
                false);
        binding.setSearchBox(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int e = context.getResources().getDimensionPixelSize(R.dimen
                    .activity_searcher_searchbox_elevation);
            binding.activitySearcherSearchboxContainer.setElevation(e);
        }

        binding.activitySearcherSearchboxEdittext.setOnEditorActionListener(this);
    }

    public void show(ViewGroup parent) {
        parent.addView(binding.getRoot());
        binding.activitySearcherSearchboxEdittext.requestFocus();

        // Open soft keyboard
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Service.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(binding.activitySearcherSearchboxEdittext, InputMethodManager
                    .SHOW_IMPLICIT);
        }

        // TODO perform animation
    }

    public void dismiss() {
        // Close soft keyboard
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Service.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(binding.activitySearcherSearchboxEdittext.getWindowToken(), 0);
        }

        ViewGroup parent = (ViewGroup) binding.getRoot().getParent();
        parent.removeView(binding.getRoot());
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            String keyword = String.valueOf(v.getText());
            if (onSearchBoxActionListener != null) {
                onSearchBoxActionListener.onSearchBoxAction(keyword);
            }

            dismiss();
        }
        return false;
    }

    public void setOnSearchBoxActionListener(OnSearchBoxActionListener onSearchBoxActionListener) {
        this.onSearchBoxActionListener = onSearchBoxActionListener;
    }

    public interface OnSearchBoxActionListener {
        void onSearchBoxAction(String keyword);
    }
}
