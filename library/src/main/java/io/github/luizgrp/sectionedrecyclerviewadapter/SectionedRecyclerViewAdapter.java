package io.github.luizgrp.sectionedrecyclerviewadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A custom RecyclerView Adapter that allows {@link Section Sections} to be added to it.
 * Sections are displayed in the same order they were added.
 */
@SuppressWarnings({"WeakerAccess", "SameParameterValue", "PMD.CollapsibleIfStatements"})
public class SectionedRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int VIEW_TYPE_HEADER = 0;
    public static final int VIEW_TYPE_FOOTER = 1;
    public static final int VIEW_TYPE_ITEM_LOADED = 2;
    public static final int VIEW_TYPE_LOADING = 3;
    public static final int VIEW_TYPE_FAILED = 4;
    public static final int VIEW_TYPE_EMPTY = 5;

    private final Map<String, Section> sections;
    private final Map<String, Integer> sectionViewTypeNumbers;

    private int viewTypeCount = 0;
    private static final int VIEW_TYPE_QTY = 6;

    public SectionedRecyclerViewAdapter() {
        sections = new LinkedHashMap<>();
        sectionViewTypeNumbers = new LinkedHashMap<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        for (Map.Entry<String, Integer> entry : sectionViewTypeNumbers.entrySet()) {
            if (viewType >= entry.getValue() && viewType < entry.getValue() + VIEW_TYPE_QTY) {

                Section section = sections.get(entry.getKey());
                int sectionViewType = viewType - entry.getValue();

                switch (sectionViewType) {
                    case VIEW_TYPE_HEADER:
                        viewHolder = getHeaderViewHolder(parent, section);
                        break;
                    case VIEW_TYPE_FOOTER:
                        viewHolder = getFooterViewHolder(parent, section);
                        break;
                    case VIEW_TYPE_ITEM_LOADED:
                        viewHolder = getItemViewHolder(parent, section);
                        break;
                    case VIEW_TYPE_LOADING:
                        viewHolder = getLoadingViewHolder(parent, section);
                        break;
                    case VIEW_TYPE_FAILED:
                        viewHolder = getFailedViewHolder(parent, section);
                        break;
                    case VIEW_TYPE_EMPTY:
                        viewHolder = getEmptyViewHolder(parent, section);
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid viewType");
                }
            }
        }

        return viewHolder;
    }

    private RecyclerView.ViewHolder getItemViewHolder(ViewGroup parent, Section section) {
        View view;
        if (section.isItemViewWillBeProvided()) {
            view = section.getItemView(parent);
            if (view == null) {
                throw new NullPointerException("Section.getItemView() returned null");
            }
        } else {
            Integer resId = section.getItemResourceId();
            if (resId == null) {
                throw new NullPointerException("Missing 'item' resource id");
            }
            view = inflate(resId, parent);
        }
        return section.getItemViewHolder(view);
    }

    private RecyclerView.ViewHolder getHeaderViewHolder(ViewGroup parent, Section section) {
        View view;
        if (section.isHeaderViewWillBeProvided()) {
            view = section.getHeaderView(parent);
            if (view == null) {
                throw new NullPointerException("Section.getHeaderView() returned null");
            }
        } else {
            Integer resId = section.getHeaderResourceId();
            if (resId == null) {
                throw new NullPointerException("Missing 'header' resource id");
            }
            view = inflate(resId, parent);
        }
        return section.getHeaderViewHolder(view);
    }

    private RecyclerView.ViewHolder getFooterViewHolder(ViewGroup parent, Section section) {
        View view;
        if (section.isFooterViewWillBeProvided()) {
            view = section.getFooterView(parent);
            if (view == null) {
                throw new NullPointerException("Section.getFooterView() returned null");
            }
        } else {
            Integer resId = section.getFooterResourceId();
            if (resId == null) {
                throw new NullPointerException("Missing 'footer' resource id");
            }
            view = inflate(resId, parent);
        }
        return section.getFooterViewHolder(view);
    }

    private RecyclerView.ViewHolder getLoadingViewHolder(ViewGroup parent, Section section) {
        View view;
        if (section.isLoadingViewWillBeProvided()) {
            view = section.getLoadingView(parent);
            if (view == null) {
                throw new NullPointerException("Section.getLoadingView() returned null");
            }
        } else {
            Integer resId = section.getLoadingResourceId();
            if (resId == null) {
                throw new NullPointerException("Missing 'loading' resource id");
            }
            view = inflate(resId, parent);
        }
        return section.getLoadingViewHolder(view);
    }

    private RecyclerView.ViewHolder getFailedViewHolder(ViewGroup parent, Section section) {
        View view;
        if (section.isFailedViewWillBeProvided()) {
            view = section.getFailedView(parent);
            if (view == null) {
                throw new NullPointerException("Section.getFailedView() returned null");
            }
        } else {
            Integer resId = section.getFailedResourceId();
            if (resId == null) {
                throw new NullPointerException("Missing 'failed' resource id");
            }
            view = inflate(resId, parent);
        }
        return section.getFailedViewHolder(view);
    }

    private RecyclerView.ViewHolder getEmptyViewHolder(ViewGroup parent, Section section) {
        View view;
        if (section.isEmptyViewWillBeProvided()) {
            view = section.getEmptyView(parent);
            if (view == null) {
                throw new NullPointerException("Section.getEmptyView() returned null");
            }
        } else {
            Integer resId = section.getEmptyResourceId();
            if (resId == null) {
                throw new NullPointerException("Missing 'empty' resource id");
            }
            view = inflate(resId, parent);
        }
        return section.getEmptyViewHolder(view);
    }

    /**
     * Add a section to this recyclerview.
     *
     * @param tag     unique identifier of the section
     * @param section section to be added
     */
    public void addSection(String tag, Section section) {
        this.sections.put(tag, section);
        this.sectionViewTypeNumbers.put(tag, viewTypeCount);
        viewTypeCount += VIEW_TYPE_QTY;
    }

    /**
     * Add a section to this recyclerview with a random tag.
     *
     * @param section section to be added
     * @return generated tag
     */
    public String addSection(Section section) {
        String tag = UUID.randomUUID().toString();

        addSection(tag, section);

        return tag;
    }

    /**
     * Return the section with the tag provided.
     *
     * @param tag unique identifier of the section
     * @return section
     */
    public Section getSection(String tag) {
        return this.sections.get(tag);
    }

    /**
     * Remove section from this recyclerview.
     *
     * @param section section to be removed
     */
    public void removeSection(Section section) {
        String tag = null;
        for (final Map.Entry<String, Section> entry : this.sections.entrySet()) {
            if (entry.getValue() == section) {
                tag = entry.getKey();
            }
        }

        if (tag != null) {
            this.removeSection(tag);
        }
    }

    /**
     * Remove section from this recyclerview.
     *
     * @param tag unique identifier of the section
     */
    public void removeSection(String tag) {
        this.sections.remove(tag);
        this.sectionViewTypeNumbers.remove(tag);
    }

    /**
     * Remove all sections from this recyclerview.
     */
    public void removeAllSections() {
        this.sections.clear();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        int currentPos = 0;

        for (Map.Entry<String, Section> entry : sections.entrySet()) {
            Section section = entry.getValue();

            // ignore invisible sections
            if (!section.isVisible()) {
                continue;
            }

            int sectionTotal = section.getSectionItemsTotal();

            // check if position is in this section
            if (position >= currentPos && position <= (currentPos + sectionTotal - 1)) {

                if (section.hasHeader()) {
                    if (position == currentPos) {
                        // delegate the binding to the section header
                        getSectionForPosition(position).onBindHeaderViewHolder(holder);
                        return;
                    }
                }

                if (section.hasFooter()) {
                    if (position == (currentPos + sectionTotal - 1)) {
                        // delegate the binding to the section header
                        getSectionForPosition(position).onBindFooterViewHolder(holder);
                        return;
                    }
                }

                // delegate the binding to the section content
                getSectionForPosition(position).onBindContentViewHolder(holder, getPositionInSection(position));
                return;
            }

            currentPos += sectionTotal;
        }

        throw new IndexOutOfBoundsException("Invalid position");
    }

    @Override
    public int getItemCount() {
        int count = 0;

        for (Map.Entry<String, Section> entry : sections.entrySet()) {
            Section section = entry.getValue();

            // ignore invisible sections
            if (!section.isVisible()) {
                continue;
            }

            count += section.getSectionItemsTotal();
        }

        return count;
    }

    @Override
    public int getItemViewType(int position) {
        /*
         Each Section has 6 "viewtypes":
         1) header
         2) footer
         3) items
         4) loading
         5) load failed
         6) empty
         */
        int currentPos = 0;

        for (Map.Entry<String, Section> entry : sections.entrySet()) {
            Section section = entry.getValue();

            // ignore invisible sections
            if (!section.isVisible()) {
                continue;
            }

            int sectionTotal = section.getSectionItemsTotal();

            // check if position is in this section
            if (position >= currentPos && position <= (currentPos + sectionTotal - 1)) {

                int viewType = sectionViewTypeNumbers.get(entry.getKey());

                if (section.hasHeader()) {
                    if (position == currentPos) {
                        return viewType;
                    }
                }

                if (section.hasFooter()) {
                    if (position == (currentPos + sectionTotal - 1)) {
                        return viewType + 1;
                    }
                }

                switch (section.getState()) {
                    case LOADED:
                        return viewType + 2;
                    case LOADING:
                        return viewType + 3;
                    case FAILED:
                        return viewType + 4;
                    case EMPTY:
                        return viewType + 5;
                    default:
                        throw new IllegalStateException("Invalid state");
                }
            }

            currentPos += sectionTotal;
        }

        throw new IndexOutOfBoundsException("Invalid position");
    }

    /**
     * Calculates the section adapter item view type from a view type from the adapter.
     *
     * @param itemViewType adapter view type
     * @return one of the view types:
     * <ul>
     * <li>SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER</li>
     * <li>SectionedRecyclerViewAdapter.VIEW_TYPE_FOOTER</li>
     * <li>SectionedRecyclerViewAdapter.VIEW_TYPE_ITEM_LOADED</li>
     * <li>SectionedRecyclerViewAdapter.VIEW_TYPE_LOADING</li>
     * <li>SectionedRecyclerViewAdapter.VIEW_TYPE_FAILED</li>
     * <li>SectionedRecyclerViewAdapter.VIEW_TYPE_EMPTY</li>
     * </ul>
     */
    public int getSectionItemViewTypeForAdapterViewType(int itemViewType) {
        return itemViewType % VIEW_TYPE_QTY;
    }

    /**
     * Returns the Section ViewType of an item based on the position in the adapter.
     *
     * @param position position in the adapter
     * @return one of the view types:
     * <ul>
     * <li>SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER</li>
     * <li>SectionedRecyclerViewAdapter.VIEW_TYPE_FOOTER</li>
     * <li>SectionedRecyclerViewAdapter.VIEW_TYPE_ITEM_LOADED</li>
     * <li>SectionedRecyclerViewAdapter.VIEW_TYPE_LOADING</li>
     * <li>SectionedRecyclerViewAdapter.VIEW_TYPE_FAILED</li>
     * <li>SectionedRecyclerViewAdapter.VIEW_TYPE_EMPTY</li>
     * </ul>
     */
    public int getSectionItemViewType(int position) {
        int viewType = getItemViewType(position);

        return getSectionItemViewTypeForAdapterViewType(viewType);
    }

    /**
     * Returns the Section object for a position in the adapter.
     *
     * @param position position in the adapter
     * @return Section object for that position
     */
    public Section getSectionForPosition(int position) {

        int currentPos = 0;

        for (Map.Entry<String, Section> entry : sections.entrySet()) {
            Section section = entry.getValue();

            // ignore invisible sections
            if (!section.isVisible()) {
                continue;
            }

            int sectionTotal = section.getSectionItemsTotal();

            // check if position is in this section
            if (position >= currentPos && position <= (currentPos + sectionTotal - 1)) {
                return section;
            }

            currentPos += sectionTotal;
        }

        throw new IndexOutOfBoundsException("Invalid position");
    }

    /**
     * Return the item position relative to the section.
     *
     * @param position position of the item in the adapter
     * @return position of the item in the section
     */
    public int getPositionInSection(int position) {
        int currentPos = 0;

        for (Map.Entry<String, Section> entry : sections.entrySet()) {
            Section section = entry.getValue();

            // ignore invisible sections
            if (!section.isVisible()) {
                continue;
            }

            int sectionTotal = section.getSectionItemsTotal();

            // check if position is in this section
            if (position >= currentPos && position <= (currentPos + sectionTotal - 1)) {
                return position - currentPos - (section.hasHeader() ? 1 : 0);
            }

            currentPos += sectionTotal;
        }

        throw new IndexOutOfBoundsException("Invalid position");
    }

    /**
     * Return the section position in the adapter.
     *
     * @param tag unique identifier of the section
     * @return position of the section in the adapter
     */
    public int getSectionPosition(String tag) {
        Section section = getValidSectionOrThrowException(tag);

        return getSectionPosition(section);
    }

    /**
     * Return the section position in the adapter.
     *
     * @param section a visible section of this adapter
     * @return position of the section in the adapter
     */
    public int getSectionPosition(Section section) {
        int currentPos = 0;

        for (Map.Entry<String, Section> entry : sections.entrySet()) {
            Section loopSection = entry.getValue();

            // ignore invisible sections
            if (!loopSection.isVisible()) {
                continue;
            }

            if (loopSection == section) {
                return currentPos;
            }

            int sectionTotal = loopSection.getSectionItemsTotal();

            currentPos += sectionTotal;
        }

        throw new IllegalArgumentException("Invalid section");
    }

    /**
     * Return a copy of the map with all sections of this adapter.
     *
     * @return a copy of the map with all sections
     */
    @NonNull
    public Map<String, Section> getCopyOfSectionsMap() {
        return new LinkedHashMap<>(sections);
    }

    /**
     * Helper method that receives position in relation to the section, and returns the position in
     * the adapter.
     *
     * @param tag      unique identifier of the section
     * @param position position of the item in the section
     * @return position of the item in the adapter
     */
    public int getPositionInAdapter(String tag, int position) {
        Section section = getValidSectionOrThrowException(tag);

        return getPositionInAdapter(section, position);
    }

    /**
     * Helper method that receives position in relation to the section, and returns the position in
     * the adapter.
     *
     * @param section  a visible section of this adapter
     * @param position position of the item in the section
     * @return position of the item in the adapter
     */
    public int getPositionInAdapter(Section section, int position) {
        return getSectionPosition(section) + (section.hasHeader() ? 1 : 0) + position;
    }

    /**
     * Helper method that returns the position of header in the adapter.
     *
     * @param tag unique identifier of the section
     * @return position of the header in the adapter
     */
    public int getHeaderPositionInAdapter(String tag) {
        Section section = getValidSectionOrThrowException(tag);

        return getHeaderPositionInAdapter(section);
    }

    /**
     * Helper method that returns the position of header in the adapter.
     *
     * @param section a visible section of this adapter
     * @return position of the header in the adapter
     */
    public int getHeaderPositionInAdapter(Section section) {
        if (!section.hasHeader()) {
            throw new IllegalStateException("Section doesn't have a header");
        }

        return getSectionPosition(section);
    }

    /**
     * Helper method that returns the position of footer in the adapter.
     *
     * @param tag unique identifier of the section
     * @return position of the footer in the adapter
     */
    public int getFooterPositionInAdapter(String tag) {
        Section section = getValidSectionOrThrowException(tag);

        return getFooterPositionInAdapter(section);
    }

    /**
     * Helper method that returns the position of header in the adapter.
     *
     * @param section a visible section of this adapter
     * @return position of the footer in the adapter
     */
    public int getFooterPositionInAdapter(Section section) {
        if (!section.hasFooter()) {
            throw new IllegalStateException("Section doesn't have a footer");
        }

        return getSectionPosition(section) + section.getSectionItemsTotal() - 1;
    }

    public SectionHandler getSectionHandler(String tag) {
        Section section = getValidSectionOrThrowException(tag);

        return getSectionHandler(section);
    }

    public SectionHandler getSectionHandler(Section section) {
        return new SectionHandler(this, section);
    }

    @VisibleForTesting // in order to allow this class to be unit tested
    View inflate(@LayoutRes int layoutResourceId, ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(layoutResourceId, parent, false);
    }

    @NonNull
    private Section getValidSectionOrThrowException(String tag) {
        Section section = getSection(tag);

        if (section == null) {
            throw new IllegalArgumentException("Invalid tag: " + tag);
        }

        return section;
    }
}
