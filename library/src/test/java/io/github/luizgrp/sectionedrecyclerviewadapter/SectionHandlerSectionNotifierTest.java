package io.github.luizgrp.sectionedrecyclerviewadapter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings({"PMD.MethodNamingConventions"})
public class SectionHandlerSectionNotifierTest {

    @Mock
    private SectionedRecyclerViewAdapter sectionedAdapter;
    @Mock
    private Section section;

    private SectionHandler cut;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        cut = new SectionHandler(sectionedAdapter, section);
    }

    @Test
    public void givenItemPositionInSection_whenNotifyItemInserted_thenNotifyItemInsertedIsCalledWithPositionInAdapter() {
        // Given
        final int itemPositionInSection = 0;
        final int itemPositionInAdapter = 11;
        when(sectionedAdapter.getPositionInAdapter(section, itemPositionInSection)).thenReturn(itemPositionInAdapter);

        // When
        cut.notifyItemInserted(itemPositionInSection);

        // Then
        verify(sectionedAdapter).notifyItemInserted(itemPositionInAdapter);
    }

    @Test
    public void givenSection_whenNotifyAllItemsInserted_thenNotifyItemRangeInsertedWithPositionRangeInAdapter() {
        // Given
        final int sectionPositionInAdapter = 11;
        when(sectionedAdapter.getPositionInAdapter(section, 0)).thenReturn(sectionPositionInAdapter);
        int sectionContentItemsTotal = 10;
        when(section.getContentItemsTotal()).thenReturn(sectionContentItemsTotal);

        // When
        cut.notifyAllItemsInserted();

        // Then
        verify(sectionedAdapter).notifyItemRangeInserted(sectionPositionInAdapter, sectionContentItemsTotal);
    }

    @Test
    public void givenItemPositionRangeInSection_whenNotifyItemRangeInserted_thenNotifyItemRangeInsertedWithPositionRangeInAdapter() {
        // Given
        int itemPositionInSection = 0;
        int sectionItemCount = 4;
        int itemPositionInAdapter = 11;
        when(sectionedAdapter.getPositionInAdapter(section, itemPositionInSection)).thenReturn(itemPositionInAdapter);

        // When
        cut.notifyItemRangeInserted(itemPositionInSection, sectionItemCount);

        // Then
        verify(sectionedAdapter).notifyItemRangeInserted(itemPositionInAdapter, sectionItemCount);
    }

    @Test
    public void givenItemPositionInSection_whenNotifyItemRemoved_thenNotifyItemRemovedWithPositionInAdapter() {
        // Given
        int itemPositionInSection = 0;
        int itemPositionInAdapter = 11;
        when(sectionedAdapter.getPositionInAdapter(section, itemPositionInSection)).thenReturn(itemPositionInAdapter);

        // When
        cut.notifyItemRemoved(itemPositionInSection);

        // Then
        verify(sectionedAdapter).notifyItemRemoved(itemPositionInAdapter);
    }

    @Test
    public void givenItemPositionRangeInSection_whenNotifyItemRangeRemoved_thenNotifyItemRangeRemovedWithPositionRangeInAdapter() {
        // Given
        int itemPositionInSection = 0;
        int sectionItemCount = 4;
        int itemPositionInAdapter = 11;
        when(sectionedAdapter.getPositionInAdapter(section, itemPositionInSection)).thenReturn(itemPositionInAdapter);

        // When
        cut.notifyItemRangeRemoved(itemPositionInSection, sectionItemCount);

        // Then
        verify(sectionedAdapter).notifyItemRangeRemoved(itemPositionInAdapter, sectionItemCount);
    }

    @Test
    public void givenSection_whenNotifyHeaderChanged_thenNotifyHeaderChangedWithPositionInAdapter() {
        // Given
        int itemPositionInAdapter = 10;
        when(sectionedAdapter.getHeaderPositionInAdapter(section)).thenReturn(itemPositionInAdapter);

        // When
        cut.notifyHeaderChanged();

        // Then
        verify(sectionedAdapter).notifyItemChanged(itemPositionInAdapter);
    }

    @Test
    public void givenSection_whenNotifyFooterChanged_thenNotifyFooterChangedWithAdapterPosition() {
        // Given
        int itemPositionInAdapter = 21;
        when(sectionedAdapter.getFooterPositionInAdapter(section)).thenReturn(itemPositionInAdapter);

        // When
        cut.notifyFooterChanged();

        // Then
        verify(sectionedAdapter).notifyItemChanged(itemPositionInAdapter);
    }

    @Test
    public void givenItemPositionInSection_whenNotifyItemChanged_thenNotifyItemChangedWithPositionInAdapter() {
        // Given
        int itemPositionInSection = 0;
        int itemPositionInAdapter = 11;
        when(sectionedAdapter.getPositionInAdapter(section, itemPositionInSection)).thenReturn(itemPositionInAdapter);

        // When
        cut.notifyItemChanged(itemPositionInSection);

        // Then
        verify(sectionedAdapter).notifyItemChanged(itemPositionInAdapter);
    }

    @Test
    public void givenSection_whenNotifyAllItemsChanged_thenNotifyItemRangeChangedWithPositionRangeInAdapter() {
        // Given
        int itemPositionInAdapter = 11;
        when(sectionedAdapter.getPositionInAdapter(section, 0)).thenReturn(itemPositionInAdapter);
        int sectionContentItemsTotal = 10;
        when(section.getContentItemsTotal()).thenReturn(sectionContentItemsTotal);

        // When
        cut.notifyAllItemsChanged();

        // Then
        verify(sectionedAdapter).notifyItemRangeChanged(itemPositionInAdapter, sectionContentItemsTotal);
    }

    @Test
    public void givenItemPositionRangeInSection_whenNotifyItemRangeChanged_thenNotifyItemRangeChangedWithPositionRangeInAdapter() {
        // Given
        int itemPositionInSection = 0;
        int sectionItemCount = 4;
        int itemPositionInAdapter = 11;
        when(sectionedAdapter.getPositionInAdapter(section, itemPositionInSection)).thenReturn(itemPositionInAdapter);

        // When
        cut.notifyItemRangeChanged(itemPositionInSection, sectionItemCount);

        // Then
        verify(sectionedAdapter).notifyItemRangeChanged(itemPositionInAdapter, sectionItemCount);
    }

    @Test
    @SuppressWarnings("linelength")
    public void givenItemPositionRangeInSectionAndPayload_whenNotifyItemRangeChanged_thenNotifyItemRangeChangedWithPositionRangeInAdapterAndPayload() {
        // Given
        int itemPositionInSection = 0;
        int sectionItemCount = 4;
        int itemPositionInAdapter = 11;
        when(sectionedAdapter.getPositionInAdapter(section, itemPositionInSection)).thenReturn(itemPositionInAdapter);
        Object payload = mock(Object.class);

        // When
        cut.notifyItemRangeChanged(itemPositionInSection, sectionItemCount, payload);

        // Then
        verify(sectionedAdapter).notifyItemRangeChanged(itemPositionInAdapter, sectionItemCount, payload);
    }

    @Test
    public void givenItemPositionRangeInSection_whenNotifyItemMoved_thenNotifyItemMovedWithPositionRangeInAdapter() {
        // Given
        int itemFromPositionInSection = 0;
        int itemToPositionInSection = 4;
        int itemFromPositionInAdapter = 11;
        int itemToPositionInAdapter = 15;
        when(sectionedAdapter.getPositionInAdapter(section, itemFromPositionInSection)).thenReturn(itemFromPositionInAdapter);
        when(sectionedAdapter.getPositionInAdapter(section, itemToPositionInSection)).thenReturn(itemToPositionInAdapter);

        // When
        cut.notifyItemMoved(itemFromPositionInSection, itemToPositionInSection);

        // Then
        verify(sectionedAdapter).notifyItemMoved(itemFromPositionInAdapter, itemToPositionInAdapter);
    }

    @Test
    public void givenDifferentPreviousState_whenNotifyNotLoadedStateChanged_thenNotifyItemChanged() {
        // Given
        Section.State previousState = Section.State.LOADING;
        when(section.getState()).thenReturn(Section.State.FAILED);
        int itemPositionInAdapter = 11;
        when(sectionedAdapter.getPositionInAdapter(section, 0)).thenReturn(itemPositionInAdapter);

        // When
        cut.notifyNotLoadedStateChanged(previousState);

        // Then
        verify(sectionedAdapter).notifyItemChanged(11);
    }

    @Test(expected = IllegalStateException.class)
    public void givenSamePreviousState_whenNotifyNotLoadedStateChanged_thenThrowsException() {
        // Given
        Section.State previousState = Section.State.LOADING;
        when(section.getState()).thenReturn(Section.State.LOADING);

        // When
        cut.notifyNotLoadedStateChanged(previousState);
    }

    @Test(expected = IllegalStateException.class)
    public void givenPreviousStateIsLoaded_whenNotifyNotLoadedStateChanged_thenThrowsException() {
        // Given
        Section.State previousState = Section.State.LOADED;
        when(section.getState()).thenReturn(Section.State.LOADING);

        // When
        cut.notifyNotLoadedStateChanged(previousState);
    }

    @Test(expected = IllegalStateException.class)
    public void givenCurrentStateIsLoaded_whenNotifyNotLoadedStateChanged_thenThrowsException() {
        // Given
        Section.State previousState = Section.State.LOADING;
        when(section.getState()).thenReturn(Section.State.LOADED);

        // When
        cut.notifyNotLoadedStateChanged(previousState);
    }

    @Test
    public void givenSectionHasMoreThanOneItems_whenNotifyStateChangedToLoaded_thenNotifyItemChanged_andNotifyItemRangeInserted() {
        // Given
        Section.State previousState = Section.State.LOADING;
        when(section.getState()).thenReturn(Section.State.LOADED);
        int sectionContentItemsTotal = 12;
        when(section.getContentItemsTotal()).thenReturn(sectionContentItemsTotal);
        int firstItemPositionInAdapter = 11;
        int secondItemPositionInAdapter = 12;
        when(sectionedAdapter.getPositionInAdapter(section, 0)).thenReturn(firstItemPositionInAdapter);
        when(sectionedAdapter.getPositionInAdapter(section, 1)).thenReturn(secondItemPositionInAdapter);

        // When
        cut.notifyStateChangedToLoaded(previousState);

        // Then
        verify(sectionedAdapter).notifyItemChanged(firstItemPositionInAdapter);
        verify(sectionedAdapter).notifyItemRangeInserted(secondItemPositionInAdapter, sectionContentItemsTotal - 1);
    }

    @Test
    public void givenSectionHasOnlyOneItem_whenNotifyStateChangedToLoaded_thenNotifyItemChanged() {
        // Given
        Section.State previousState = Section.State.LOADING;
        when(section.getState()).thenReturn(Section.State.LOADED);
        int sectionContentItemsTotal = 1;
        when(section.getContentItemsTotal()).thenReturn(sectionContentItemsTotal);
        int firstItemPositionInAdapter = 11;
        when(sectionedAdapter.getPositionInAdapter(section, 0)).thenReturn(firstItemPositionInAdapter);

        // When
        cut.notifyStateChangedToLoaded(previousState);

        // Then
        verify(sectionedAdapter).notifyItemChanged(firstItemPositionInAdapter);
        verify(sectionedAdapter, never()).notifyItemRangeInserted(anyInt(), anyInt());
    }

    @Test
    public void givenSectionHasNoItems_whenNotifyStateChangedToLoaded_thenNotifyItemRemoved() {
        // Given
        Section.State previousState = Section.State.LOADING;
        when(section.getState()).thenReturn(Section.State.LOADED);
        int sectionContentItemsTotal = 0;
        when(section.getContentItemsTotal()).thenReturn(sectionContentItemsTotal);
        int firstItemPositionInAdapter = 11;
        when(sectionedAdapter.getPositionInAdapter(section, 0)).thenReturn(firstItemPositionInAdapter);

        // When
        cut.notifyStateChangedToLoaded(previousState);

        // Then
        verify(sectionedAdapter).notifyItemRemoved(11);
    }

    @Test(expected = IllegalStateException.class)
    public void givenSamePreviousState_whenNotifyStateChangedToLoaded_thenThrowsException() {
        // Given
        Section.State previousState = Section.State.LOADED;
        when(section.getState()).thenReturn(Section.State.LOADED);

        // When
        cut.notifyStateChangedToLoaded(previousState);
    }

    @Test(expected = IllegalStateException.class)
    public void givenCurrentStateIsNotLoadedAndPreviousStateIsLoaded_whenNotifyStateChangedToLoaded_thenThrowsException() {
        // Given
        Section.State previousState = Section.State.LOADED;
        when(section.getState()).thenReturn(Section.State.LOADING);

        // When
        cut.notifyStateChangedToLoaded(previousState);
    }

    @Test(expected = IllegalStateException.class)
    public void givenCurrentStateIsNotLoadedAndPreviousStateIsNotLoaded_whenNotifyStateChangedToLoaded_thenThrowsException() {
        // Given
        Section.State previousState = Section.State.EMPTY;
        when(section.getState()).thenReturn(Section.State.LOADING);

        // When
        cut.notifyStateChangedToLoaded(previousState);
    }

    @Test
    public void givenSectionHadMoreThanOneItems_whenNotifyStateChangedFromLoaded_thenNotifyItemRangeRemoved_andNotifyItemChanged() {
        // Given
        when(section.getState()).thenReturn(Section.State.LOADING);
        int sectionPreviousContentItemsTotal = 10;
        when(section.getContentItemsTotal()).thenReturn(sectionPreviousContentItemsTotal);
        int firstItemPositionInAdapter = 11;
        int secondItemPositionInAdapter = 12;
        when(sectionedAdapter.getPositionInAdapter(section, 0)).thenReturn(firstItemPositionInAdapter);
        when(sectionedAdapter.getPositionInAdapter(section, 1)).thenReturn(secondItemPositionInAdapter);

        // When
        cut.notifyStateChangedFromLoaded(sectionPreviousContentItemsTotal);

        // Then
        verify(sectionedAdapter).notifyItemRangeRemoved(secondItemPositionInAdapter, sectionPreviousContentItemsTotal - 1);
        verify(sectionedAdapter).notifyItemChanged(firstItemPositionInAdapter);
    }

    @Test
    public void givenSectionHadNoItems_whenNotifyStateChangedFromLoaded_thenNotifyItemRangeInserted() {
        // Given
        when(section.getState()).thenReturn(Section.State.LOADING);
        int sectionPreviousContentItemsTotal = 0;
        int firstItemPositionInAdapter = 11;
        when(sectionedAdapter.getPositionInAdapter(section, 0)).thenReturn(firstItemPositionInAdapter);

        // When
        cut.notifyStateChangedFromLoaded(sectionPreviousContentItemsTotal);

        // Then
        verify(sectionedAdapter).notifyItemInserted(firstItemPositionInAdapter);
    }

    @Test
    public void givenSectionHadOnlyOneItem_whenNotifyStateChangedFromLoaded_thenNotifyItemRangeInserted() {
        // Given
        when(section.getState()).thenReturn(Section.State.LOADING);
        int sectionPreviousContentItemsTotal = 1;
        int firstItemPositionInAdapter = 11;
        when(sectionedAdapter.getPositionInAdapter(section, 0)).thenReturn(firstItemPositionInAdapter);

        // When
        cut.notifyStateChangedFromLoaded(sectionPreviousContentItemsTotal);

        // Then
        verify(sectionedAdapter).notifyItemChanged(firstItemPositionInAdapter);
        verify(sectionedAdapter, never()).notifyItemRangeRemoved(anyInt(), anyInt());
    }

    @Test(expected = IllegalStateException.class)
    public void givenLoadedAsCurrentState_whenNotifyStateChangedFromLoaded_thenThrowsException() {
        // Given
        when(section.getState()).thenReturn(Section.State.LOADED);
        int sectionPreviousContentItemsTotal = 1;

        // When
        cut.notifyStateChangedFromLoaded(sectionPreviousContentItemsTotal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenNegativeValueAsPreviousContentItemsCount_whenNotifyStateChangedFromLoaded_thenThrowsException() {
        // Given
        int sectionPreviousContentItemsTotal = -1;

        // When
        cut.notifyStateChangedFromLoaded(sectionPreviousContentItemsTotal);
    }

    @Test
    public void givenSection_whenNotifyHeaderInserted_thenNotifyItemInserted() {
        // Given
        int headerPositionInAdapter = 10;
        when(sectionedAdapter.getHeaderPositionInAdapter(section)).thenReturn(headerPositionInAdapter);

        // When
        cut.notifyHeaderInserted();

        // Then
        verify(sectionedAdapter).notifyItemInserted(headerPositionInAdapter);
    }

    @Test
    public void givenSection_whenNotifyFooterInserted_thenNotifyItemInserted() {
        // Given
        int footerPositionInAdapter = 21;
        when(sectionedAdapter.getFooterPositionInAdapter(section)).thenReturn(footerPositionInAdapter);

        // When
        cut.notifyFooterInserted();

        // Then
        verify(sectionedAdapter).notifyItemInserted(footerPositionInAdapter);
    }

    @Test
    public void givenSection_whenNotifyHeaderRemoved_thenNotifyItemRemoved() {
        // Given
        int headerPositionInAdapter = 21;
        when(sectionedAdapter.getSectionPosition(section)).thenReturn(headerPositionInAdapter);

        // When
        cut.notifyHeaderRemoved();

        // Then
        verify(sectionedAdapter).notifyItemRemoved(headerPositionInAdapter);
    }

    @Test
    public void givenSection_whenNotifyFooterRemoved_thenNotifyItemRemoved() {
        // Given
        int sectionPositionInAdapter = 11;
        when(sectionedAdapter.getSectionPosition(section)).thenReturn(sectionPositionInAdapter);
        int sectionItemsTotal = 10;
        when(section.getSectionItemsTotal()).thenReturn(sectionItemsTotal);

        // When
        cut.notifyFooterRemoved();

        // Then
        verify(sectionedAdapter).notifyItemRemoved(sectionPositionInAdapter + sectionItemsTotal);
    }

    @Test
    public void givenSection_whenNotifySectionChangedToVisible_thenNotifyItemRangeInserted() {
        // Given
        when(section.isVisible()).thenReturn(true);
        int sectionItemsTotal = 12;
        when(section.getSectionItemsTotal()).thenReturn(sectionItemsTotal);
        int sectionPositionInAdapter = 10;
        when(sectionedAdapter.getSectionPosition(section)).thenReturn(sectionPositionInAdapter);

        // When
        cut.notifySectionChangedToVisible();

        // Then
        verify(sectionedAdapter).notifyItemRangeInserted(sectionPositionInAdapter, sectionItemsTotal);
    }

    @Test(expected = IllegalStateException.class)
    public void givenSectionIsInvisible_whenNotifySectionChangedToVisible_thenThrowsException() {
        // Given
        when(section.isVisible()).thenReturn(false);

        // When
        cut.notifySectionChangedToVisible();
    }

    @Test
    public void givenPreviousSectionPosition_whenNotifySectionChangedToInvisible_thenNotifyItemRangeInserted() {
        // Given
        int previousSectionPosition = 10;
        when(section.isVisible()).thenReturn(false);
        int sectionItemsTotal = 12;
        when(section.getSectionItemsTotal()).thenReturn(sectionItemsTotal);

        // When
        cut.notifySectionChangedToInvisible(previousSectionPosition);

        // Then
        verify(sectionedAdapter).notifyItemRangeRemoved(previousSectionPosition, sectionItemsTotal);
    }

    @Test(expected = IllegalStateException.class)
    public void givenSectionIsVisible_whenNotifySectionChangedToInvisible_thenThrowsException() {
        // Given
        int previousSectionPosition = 10;
        when(section.isVisible()).thenReturn(true);

        // When
        cut.notifySectionChangedToInvisible(previousSectionPosition);
    }
}
