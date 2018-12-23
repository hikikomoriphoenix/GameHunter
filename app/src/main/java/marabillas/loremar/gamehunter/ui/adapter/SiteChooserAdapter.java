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

package marabillas.loremar.gamehunter.ui.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import marabillas.loremar.gamehunter.R;
import marabillas.loremar.gamehunter.components.GameSiteViewModel;
import marabillas.loremar.gamehunter.databinding.AdapterSiteChooserItemBinding;

public class SiteChooserAdapter extends RecyclerView.Adapter<SiteChooserAdapter.SiteChooserViewHolder> {
    private List<GameSiteViewModel> gameSites;

    public SiteChooserAdapter(List<GameSiteViewModel> gameSites) {
        this.gameSites = gameSites;
    }

    @NonNull
    @Override
    public SiteChooserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        AdapterSiteChooserItemBinding binding = DataBindingUtil.inflate(inflater, R.layout
                        .adapter_site_chooser_item, parent,
                false);
        binding.adapterSiteChooserItemViewLabel.setMovementMethod(LinkMovementMethod.getInstance());
        return new SiteChooserViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SiteChooserViewHolder holder, int position) {
        GameSiteViewModel viewModel = gameSites.get(position);
        holder.getBinding().setViewModel(viewModel);
    }

    @Override
    public int getItemCount() {
        return gameSites.size();
    }

    class SiteChooserViewHolder extends RecyclerView.ViewHolder {
        private AdapterSiteChooserItemBinding binding;

        public SiteChooserViewHolder(AdapterSiteChooserItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.setLifecycleOwner(this);
        }

        public AdapterSiteChooserItemBinding getBinding() {
            return binding;
        }
    }
}
