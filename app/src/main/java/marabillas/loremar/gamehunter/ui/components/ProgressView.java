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

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.Objects;

import marabillas.loremar.gamehunter.R;

public class ProgressView {
    private Dialog dialog;

    public ProgressView(Context context) {
        Resources r = context.getResources();

        // Create dialog with transparent window of fixed size. The dialog should block user
        // interaction.
        dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        View decorView = Objects.requireNonNull(dialog.getWindow()).getDecorView();
        ViewCompat.setBackground(decorView, new ColorDrawable(Color.TRANSPARENT));
        int size = r.getDimensionPixelSize(R.dimen.activity_searcher_progressview_size);
        dialog.getWindow().setLayout(size, size);

        // Create indeterminate circular progress bar.
        ProgressBar progressBar = new ProgressBar(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams
                .MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        progressBar.setLayoutParams(params);
        progressBar.setIndeterminate(true);
        int color = ResourcesCompat.getColor(r, R.color.neonGreen, null);
        Drawable d = progressBar.getIndeterminateDrawable();
        d.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);

        dialog.setContentView(progressBar);
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }
}
