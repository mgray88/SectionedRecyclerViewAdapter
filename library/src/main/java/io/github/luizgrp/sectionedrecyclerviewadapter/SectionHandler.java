package io.github.luizgrp.sectionedrecyclerviewadapter;

public class SectionHandler implements SectionNotifier {
    
    private final SectionedRecyclerViewAdapter sectionedAdapter;
    private final Section section;

    SectionHandler(final SectionedRecyclerViewAdapter sectionedAdapter, final Section section) {
        this.sectionedAdapter = sectionedAdapter;
        this.section = section;
    }

    public void notifyItemInserted(int position) {
        sectionedAdapter.notifyItemInserted(sectionedAdapter.getPositionInAdapter(section, position));
    }

    public void notifyAllItemsInserted() {
        sectionedAdapter.notifyItemRangeInserted(
                sectionedAdapter.getPositionInAdapter(section, 0),
                section.getContentItemsTotal()
        );
    }

    public void notifyItemRangeInserted(int positionStart, int itemCount) {
        sectionedAdapter.notifyItemRangeInserted(
                sectionedAdapter.getPositionInAdapter(section, positionStart),
                itemCount
        );
    }

    public void notifyItemRemoved(int position) {
        sectionedAdapter.notifyItemRemoved(sectionedAdapter.getPositionInAdapter(section, position));
    }

    public void notifyItemRangeRemoved(int positionStart, int itemCount) {
        sectionedAdapter.notifyItemRangeRemoved(
                sectionedAdapter.getPositionInAdapter(section, positionStart),
                itemCount
        );
    }

    public void notifyHeaderChanged() {
        sectionedAdapter.notifyItemChanged(sectionedAdapter.getHeaderPositionInAdapter(section));
    }

    public void notifyFooterChanged() {
        sectionedAdapter.notifyItemChanged(sectionedAdapter.getFooterPositionInAdapter(section));
    }

    public void notifyItemChanged(int position) {
        sectionedAdapter.notifyItemChanged(sectionedAdapter.getPositionInAdapter(section, position));
    }

    public void notifyAllItemsChanged() {
        sectionedAdapter.notifyItemRangeChanged(
                sectionedAdapter.getPositionInAdapter(section, 0),
                section.getContentItemsTotal()
        );
    }

    public void notifyItemRangeChanged(int positionStart, int itemCount) {
        sectionedAdapter.notifyItemRangeChanged(
                sectionedAdapter.getPositionInAdapter(section, positionStart),
                itemCount
        );
    }

    public void notifyItemRangeChanged(int positionStart, int itemCount, Object payload) {
        sectionedAdapter.notifyItemRangeChanged(
                sectionedAdapter.getPositionInAdapter(section, positionStart),
                itemCount,
                payload
        );
    }

    public void notifyItemMoved(int fromPosition, int toPosition) {
        sectionedAdapter.notifyItemMoved(
                sectionedAdapter.getPositionInAdapter(section, fromPosition),
                sectionedAdapter.getPositionInAdapter(section, toPosition)
        );
    }

    public void notifyNotLoadedStateChanged(Section.State previousState) {
        Section.State state = section.getState();

        if (state == previousState) {
            throw new IllegalStateException("No state changed");
        }

        if (previousState == Section.State.LOADED) {
            throw new IllegalStateException("Use notifyStateChangedFromLoaded");
        }

        if (state == Section.State.LOADED) {
            throw new IllegalStateException("Use notifyStateChangedToLoaded");
        }

        notifyItemChanged(0);
    }

    public void notifyStateChangedToLoaded(Section.State previousState) {
        Section.State state = section.getState();

        if (state == previousState) {
            throw new IllegalStateException("No state changed");
        }

        if (state != Section.State.LOADED) {
            if (previousState == Section.State.LOADED) {
                throw new IllegalStateException("Use notifyStateChangedFromLoaded");
            } else {
                throw new IllegalStateException("Use notifyNotLoadedStateChanged");
            }
        }

        int contentItemsTotal = section.getContentItemsTotal();

        if (contentItemsTotal == 0) {
            notifyItemRemoved(0);
        } else {
            notifyItemChanged(0);

            if (contentItemsTotal > 1) {
                notifyItemRangeInserted(1, contentItemsTotal - 1);
            }
        }
    }

    public void notifyStateChangedFromLoaded(int previousContentItemsCount) {
        if (previousContentItemsCount < 0) {
            throw new IllegalArgumentException("previousContentItemsCount cannot have a negative value");
        }

        Section.State state = section.getState();
        if (state == Section.State.LOADED) {
            throw new IllegalStateException("Use notifyStateChangedToLoaded");
        }

        if (previousContentItemsCount == 0) {
            notifyItemInserted(0);
        } else {
            if (previousContentItemsCount > 1) {
                notifyItemRangeRemoved(1, previousContentItemsCount - 1);
            }

            notifyItemChanged(0);
        }
    }

    public void notifyHeaderInserted() {
        int headerPosition = sectionedAdapter.getHeaderPositionInAdapter(section);

        sectionedAdapter.notifyItemInserted(headerPosition);
    }

    public void notifyFooterInserted() {
        int footerPosition = sectionedAdapter.getFooterPositionInAdapter(section);

        sectionedAdapter.notifyItemInserted(footerPosition);
    }

    public void notifyHeaderRemoved() {
        int position = sectionedAdapter.getSectionPosition(section);

        sectionedAdapter.notifyItemRemoved(position);
    }

    public void notifyFooterRemoved() {
        int position = sectionedAdapter.getSectionPosition(section) + section.getSectionItemsTotal();

        sectionedAdapter.notifyItemRemoved(position);
    }

    public void notifySectionChangedToVisible() {
        if (!section.isVisible()) {
            throw new IllegalStateException("This section is not visible.");
        }

        int sectionPosition = sectionedAdapter.getSectionPosition(section);

        int sectionItemsTotal = section.getSectionItemsTotal();

        sectionedAdapter.notifyItemRangeInserted(sectionPosition, sectionItemsTotal);
    }

    public void notifySectionChangedToInvisible(int previousSectionPosition) {
        if (section.isVisible()) {
            throw new IllegalStateException("This section is not invisible.");
        }

        int sectionItemsTotal = section.getSectionItemsTotal();

        sectionedAdapter.notifyItemRangeRemoved(previousSectionPosition, sectionItemsTotal);
    }
}
