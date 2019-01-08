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
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import marabillas.loremar.gamehunter.R;

import static android.view.Gravity.CENTER_HORIZONTAL;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.view.inputmethod.EditorInfo.IME_ACTION_GO;

public class GoToPageDialog implements TextView.OnEditorActionListener, TextWatcher {
    private Context context;
    private long totalPages;
    private EditText editText;
    private AlertDialog dialog;
    private OnGoToPageDialogActionListener onGoToPageDialogActionListener;

    public GoToPageDialog(Context context, long totalPages) {
        this.context = context;
        this.totalPages = totalPages;

        editText = new EditText(context);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setImeOptions(IME_ACTION_GO);
        editText.setOnEditorActionListener(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(MATCH_PARENT,
                WRAP_CONTENT);
        editText.setLayoutParams(params);
        editText.addTextChangedListener(this);
        editText.setGravity(CENTER_HORIZONTAL);

        TextView totalView = new TextView(context);
        String totalText = "of " + totalPages;
        totalView.setText(totalText);
        totalView.setGravity(CENTER_HORIZONTAL);
        totalView.setLayoutParams(params);

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(MATCH_PARENT,
                MATCH_PARENT);
        layout.setLayoutParams(layoutParams);
        int p = context.getResources().getDimensionPixelSize(R.dimen.small_spacing);
        layout.setPadding(p, p, p, p);
        layout.addView(editText);
        layout.addView(totalView);

        dialog = new AlertDialog.Builder(context)
                .setMessage(R.string.activity_searcher_gotopage_dialog_message)
                .setView(layout)
                .setNegativeButton("Cancel", (dialog, which) -> dismiss())
                .create();
    }

    public void show() {
        dialog.show();

        // Open soft keyboard
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Service.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }

    public void dismiss() {
        // Close soft keyboard
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Service.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }

        dialog.dismiss();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == IME_ACTION_GO) {
            if (onGoToPageDialogActionListener != null) {
                String text = String.valueOf(editText.getText());
                if (text.length() <= 0) {
                    return true;
                }

                long pageNumber = Long.parseLong(text);
                onGoToPageDialogActionListener.onGoToPageDialogAction(pageNumber);
            }

            dismiss();
        }
        return false;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        // If input value is greater than total pages then dismiss last key input.
        String text = s.toString();
        if (text.length() > 0) {
            long p = Long.parseLong(text);
            if (p > totalPages) {
                int l = text.length();
                String prev = text.substring(0, l - 1);
                editText.setText(prev);
                editText.setSelection(prev.length());
            }
        }
    }

    public void setOnGoToPageDialogActionListener(OnGoToPageDialogActionListener onGoToPageDialogActionListener) {
        this.onGoToPageDialogActionListener = onGoToPageDialogActionListener;
    }

    public interface OnGoToPageDialogActionListener {
        void onGoToPageDialogAction(long pageNumber);
    }
}
