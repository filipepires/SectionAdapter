package filipe.pires.me.sectionadapter.recycler_section;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import filipe.pires.me.sectionadapter.delegate.ElementDelegate;
import filipe.pires.me.sectionadapter.delegate.FooterDelegate;
import filipe.pires.me.sectionadapter.delegate.GenericCell;
import filipe.pires.me.sectionadapter.delegate.HeaderDelegate;
import filipe.pires.me.sectionadapter.recycler_section.RecyclerSection;
import filipe.pires.me.sectionadapter.recycler_section.RecyclerSectionCluster;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class RecyclerSectionClusterTest {

    @Mock
    private GenericCell cell;

    private ElementDelegate.Cell genericCell;
    private ElementDelegate.Cell firstSectionElement;
    private ElementDelegate.Cell secondSectionElement;
    private ElementDelegate.Cell thirdSectionElement;
    private List<GenericCell> firstSection = new ArrayList<>();
    private List<GenericCell> secondSection = new ArrayList<>();
    private List<GenericCell> thirdSection = new ArrayList<>();

    private RecyclerSectionCluster recyclerSectionCluster;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        recyclerSectionCluster = new RecyclerSectionCluster();
        initCell();
    }

    private void initCell() {
        recyclerSectionCluster.clear();

        genericCell = new ElementDelegate.Cell();

        firstSectionElement = new ElementDelegate.Cell();
        firstSection.add(genericCell);
        firstSection.add(genericCell);
        firstSection.add(firstSectionElement);
        recyclerSectionCluster.addSection(firstSection);

        secondSectionElement = new ElementDelegate.Cell();
        secondSection.add(genericCell);
        secondSection.add(secondSectionElement);
        secondSection.add(genericCell);
        recyclerSectionCluster.addSection(secondSection);

        thirdSectionElement = new ElementDelegate.Cell();
        thirdSection.add(genericCell);
        thirdSection.add(genericCell);
        thirdSection.add(thirdSectionElement);
        recyclerSectionCluster.addSection(thirdSection);
    }

    @Test
    public void givenOneSection_sizeEqualsSectionSize() {
        recyclerSectionCluster.clear();
        recyclerSectionCluster.addSection(firstSection);

        assertEquals(firstSection.size(), recyclerSectionCluster.getItemCount());
    }

    @Test
    public void givenSeveralSection_sizeEqualsSectionsSum() {
        recyclerSectionCluster.clear();
        recyclerSectionCluster.addSection(firstSection);
        recyclerSectionCluster.addSection(secondSection);
        recyclerSectionCluster.addSection(thirdSection);

        assertEquals(firstSection.size() + secondSection.size() + thirdSection.size(), recyclerSectionCluster.getItemCount());
    }

    @Test
    public void itemBelongsToFirstSection_returnFirstSectionItem() {
        assertEquals(firstSectionElement, recyclerSectionCluster.getItemAtPosition(2));
    }

    @Test
    public void itemBelongsToSecondSection_returnSecondSectionItem() {
        assertEquals(secondSectionElement, recyclerSectionCluster.getItemAtPosition(4));
    }

    @Test
    public void itemBelongsToThirdSection_returnThirdSectionItem() {
        assertEquals(thirdSectionElement, recyclerSectionCluster.getItemAtPosition(8));
    }

    @Test
    public void givenSection_returnLastPositionForSection() {
        assertEquals(3, recyclerSectionCluster.getLastElementPositionFromSection(1));
    }

    @Test
    public void givenHeaderOnLastSection_returnPosition() {
        recyclerSectionCluster.addHeader(new HeaderDelegate.Cell());

        assertEquals(6, recyclerSectionCluster.getHeaderPosition());
    }

    @Test
    public void givenOneSectionWithHeader_returnZero() {
        recyclerSectionCluster.clear();
        recyclerSectionCluster.addHeader(new HeaderDelegate.Cell());

        assertEquals(0, recyclerSectionCluster.getHeaderPosition());
    }

    @Test
    public void givenSectionToGetHeaderMethod_positionIsNotZero() {
        int section = 1;
        recyclerSectionCluster.addHeader(new HeaderDelegate.Cell(), section);

        assertNotEquals(0, recyclerSectionCluster.getHeaderPosition(section));
    }

    @Test
    public void givenFooter_returnLastPosition() {
        recyclerSectionCluster.addFooter(new FooterDelegate.Cell());

        assertEquals(recyclerSectionCluster.getItemCount() - 1, recyclerSectionCluster.getFooterPosition());
    }

    @Test
    public void givenFooterOnClearedAdapter_returnZero() {
        recyclerSectionCluster.clear();
        recyclerSectionCluster.addFooter(new FooterDelegate.Cell());

        assertEquals(0, recyclerSectionCluster.getFooterPosition());
    }

    @Test
    public void givenSectionNumber_returnStartingIndex() {
        assertEquals(3, recyclerSectionCluster.getStartSectionPositionForSection(1));
    }

    @Test
    public void givenSectionWithHeader_shouldNotCreateSection() {
        recyclerSectionCluster.addSection();
        recyclerSectionCluster.addHeader(new HeaderDelegate.Cell());
        int sectionsBeforeAddingSection = recyclerSectionCluster.sections.size();
        recyclerSectionCluster.addSection(thirdSection);
        int sectionsAfterAddingSection = recyclerSectionCluster.sections.size();

        assertEquals(sectionsBeforeAddingSection, sectionsAfterAddingSection);
    }

    @Test
    public void givenSectionWithFooter_shouldNotCreateSection() {
        recyclerSectionCluster.addSection();
        recyclerSectionCluster.addFooter(new FooterDelegate.Cell());
        int sectionsBeforeAddingSection = recyclerSectionCluster.sections.size();
        recyclerSectionCluster.addSection(thirdSection);
        int sectionsAfterAddingSection = recyclerSectionCluster.sections.size();

        assertEquals(sectionsBeforeAddingSection, sectionsAfterAddingSection);
    }

    @Test
    public void givenSectionWithHeader_addingHeaderShouldCreateSection() {
        recyclerSectionCluster.addSection();
        recyclerSectionCluster.addHeader(new HeaderDelegate.Cell());
        int sectionsBeforeAddingSection = recyclerSectionCluster.sections.size();
        recyclerSectionCluster.addHeader(new HeaderDelegate.Cell());
        int sectionsAfterAddingSection = recyclerSectionCluster.sections.size();

        assertEquals(sectionsBeforeAddingSection + 1, sectionsAfterAddingSection);
    }

    @Test
    public void givenSectionWithFooter_addingFooterShouldCreateSection() {
        recyclerSectionCluster.addSection();
        recyclerSectionCluster.addFooter(new FooterDelegate.Cell());
        int sectionsBeforeAddingSection = recyclerSectionCluster.sections.size();
        recyclerSectionCluster.addFooter(new FooterDelegate.Cell());
        int sectionsAfterAddingSection = recyclerSectionCluster.sections.size();

        assertEquals(sectionsBeforeAddingSection + 1, sectionsAfterAddingSection);
    }

    @Test
    public void givenElementInSection_returnIndex() {
        RecyclerSection wrongSection = mock(RecyclerSection.class);
        doReturn(-1).when(wrongSection).indexOf(any(GenericCell.class));
        doReturn(4).when(wrongSection).getItemCount();

        RecyclerSection correctSection = mock(RecyclerSection.class);
        doReturn(3).when(correctSection).indexOf(any(GenericCell.class));
        doReturn(4).when(correctSection).getItemCount();

        recyclerSectionCluster.sections = new ArrayList<>();
        recyclerSectionCluster.sections.add(wrongSection);
        recyclerSectionCluster.sections.add(wrongSection);
        recyclerSectionCluster.sections.add(correctSection);
        recyclerSectionCluster.sections.add(wrongSection);

        int index = recyclerSectionCluster.indexOf(new ElementDelegate.Cell());

        assertEquals(11, index);
    }

    @Test
    public void givenElementNotInArray_returnNotFound() {
        RecyclerSection wrongSection = mock(RecyclerSection.class);
        doReturn(-1).when(wrongSection).indexOf(any(GenericCell.class));
        doReturn(4).when(wrongSection).getItemCount();


        recyclerSectionCluster.sections = new ArrayList<>();
        recyclerSectionCluster.sections.add(wrongSection);
        recyclerSectionCluster.sections.add(wrongSection);
        recyclerSectionCluster.sections.add(wrongSection);

        int index = recyclerSectionCluster.indexOf(new ElementDelegate.Cell());

        assertEquals(-1, index);
    }

}