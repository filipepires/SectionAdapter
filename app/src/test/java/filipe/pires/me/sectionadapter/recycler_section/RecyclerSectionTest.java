package filipe.pires.me.sectionadapter.recycler_section;

import org.junit.Before;
import org.junit.Test;

import filipe.pires.me.sectionadapter.delegate.ElementDelegate;
import filipe.pires.me.sectionadapter.delegate.FooterDelegate;
import filipe.pires.me.sectionadapter.delegate.HeaderDelegate;
import filipe.pires.me.sectionadapter.recycler_section.RecyclerSection;

import static junit.framework.Assert.assertEquals;

public class RecyclerSectionTest {

    private RecyclerSection section;

    @Before
    public void setUp() {
        section = new RecyclerSection();
    }

    @Test
    public void giveNoHeaderOrFooter_sizeIsNumberOfElements() {
        section.addElement(new ElementDelegate.Cell("some element"));
        section.addElement(new ElementDelegate.Cell("some element"));

        assertEquals(section.elements.size(), section.getItemCount());
    }

    @Test
    public void givenHeader_sizeIsNumberOfElementsPlusOne() {
        section.addHeader(new HeaderDelegate.Cell("some header"));
        section.addElement(new ElementDelegate.Cell("some element"));
        section.addElement(new ElementDelegate.Cell("some element"));
        int exceptedSize = section.elements.size() + 1;

        assertEquals(exceptedSize, section.getItemCount());
    }

    @Test
    public void givenFooter_sizeIsNumberOfElementsPlusOne() {
        section.addFooter(new FooterDelegate.Cell("some footer"));
        section.addElement(new ElementDelegate.Cell("some element"));
        section.addElement(new ElementDelegate.Cell("some element"));
        int exceptedSize = section.elements.size() + 1;

        assertEquals(exceptedSize, section.getItemCount());
    }

    @Test
    public void givenHeaderAndFooter_sizeIsNumberOfElementsPlusTwo() {
        section.addHeader(new HeaderDelegate.Cell("some header"));
        section.addFooter(new FooterDelegate.Cell("some footer"));
        section.addElement(new ElementDelegate.Cell("some element"));
        section.addElement(new ElementDelegate.Cell("some element"));
        int exceptedSize = section.elements.size() + 2;

        assertEquals(exceptedSize, section.getItemCount());
    }

    @Test
    public void givenHeaderRemoved_sizeIsDecreased() {
        section.addHeader(new HeaderDelegate.Cell("some header"));
        section.addFooter(new FooterDelegate.Cell("some footer"));
        section.addElement(new ElementDelegate.Cell("some element"));
        section.addElement(new ElementDelegate.Cell("some element"));
        int sizeBeforeRemoving = section.getItemCount();
        section.removeHeader();

        assertEquals(sizeBeforeRemoving - 1, section.getItemCount());
    }

    @Test
    public void givenFooterRemoved_sizeIsDecreased() {
        section.addHeader(new HeaderDelegate.Cell("some header"));
        section.addFooter(new FooterDelegate.Cell("some footer"));
        section.addElement(new ElementDelegate.Cell("some element"));
        section.addElement(new ElementDelegate.Cell("some element"));
        int sizeBeforeRemoving = section.getItemCount();
        section.removeFooter();

        assertEquals(sizeBeforeRemoving - 1, section.getItemCount());
    }

    @Test
    public void giveSectionWithHeader_returnHeader() {
        HeaderDelegate.Cell header = new HeaderDelegate.Cell("some header");
        section.header = header;
        section.addElement(new ElementDelegate.Cell("some element"));
        section.addElement(new ElementDelegate.Cell("some element"));

        assertEquals(header, section.getItemAtPosition(0));
    }

    @Test
    public void giveSectionWithoutHeader_returnFirstElement() {
        ElementDelegate.Cell firstElement = new ElementDelegate.Cell("some element");
        section.addElement(firstElement);
        ElementDelegate.Cell secondElement = new ElementDelegate.Cell("second element");
        section.addElement(secondElement);

        assertEquals(firstElement, section.getItemAtPosition(0));
    }

    @Test
    public void giveSectionWithFooter_returnFooter() {
        FooterDelegate.Cell footer = new FooterDelegate.Cell("some footer");
        section.footer = footer;
        section.addElement(new ElementDelegate.Cell("some element"));
        section.addElement(new ElementDelegate.Cell("some element"));

        assertEquals(footer, section.getItemAtPosition(2));
    }

    @Test
    public void givenHeader_returnZero() {
        HeaderDelegate.Cell header = new HeaderDelegate.Cell("some header");
        section.header = header;
        section.addElement(new ElementDelegate.Cell("some element"));
        section.addElement(new ElementDelegate.Cell("some element"));

        assertEquals(0, section.indexOf(header));
    }

    @Test
    public void givenFooter_returnLastPosition() {
        FooterDelegate.Cell footer = new FooterDelegate.Cell("some footer");
        section.footer = footer;
        section.addElement(new ElementDelegate.Cell("some element"));
        section.addElement(new ElementDelegate.Cell("some element"));

        assertEquals(section.getItemCount() - 1, section.indexOf(footer));
    }

    @Test
    public void givenElement_returnPosition() {
        section.header = new HeaderDelegate.Cell("some header");
        section.footer = new FooterDelegate.Cell("some footer");
        ElementDelegate.Cell firstElement = new ElementDelegate.Cell("element");
        section.addElement(firstElement);
        section.addElement(new ElementDelegate.Cell("some element"));

        assertEquals(1, section.indexOf(firstElement));
    }

    @Test
    public void givenElementNotInSection_returnNotFound() {
        section.header = new HeaderDelegate.Cell("some header");
        section.footer = new FooterDelegate.Cell("some footer");
        ElementDelegate.Cell firstElement = new ElementDelegate.Cell("element");
        section.addElement(new ElementDelegate.Cell("some element"));

        assertEquals(-1, section.indexOf(firstElement));
    }

}