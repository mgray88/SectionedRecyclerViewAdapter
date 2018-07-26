package io.github.luizgrp.sectionedrecyclerviewadapter;

/**
 * Information for a specific position in the adapter.
 */
public class PositionInfo {

    private final int position;
    private final int itemViewType;
    private final int sectionItemViewType;
    private final Section section;

    PositionInfo(final int position,
                 final int itemViewType,
                 final int sectionItemViewType,
                 final Section section) {
        this.position = position;
        this.itemViewType = itemViewType;
        this.sectionItemViewType = sectionItemViewType;
        this.section = section;
    }

    public int getPosition() {
        return position;
    }

    /**
     * Refer to {@link android.support.v7.widget.RecyclerView.Adapter#getItemViewType}
     */
    public int getItemViewType() {
        return itemViewType;
    }

    /**
     * Returns the Section ViewType of the item on this position in the adapter.
     *
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
    public int getSectionItemViewType() {
        return sectionItemViewType;
    }

    /**
     * Returns the Section object for this position in the adapter.
     *
     * @return Section object for that position
     */
    public Section getSection() {
        return section;
    }
}
