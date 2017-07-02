package filipe.pires.me.sectionadapter.recycler_section;


import android.support.annotation.VisibleForTesting;

import java.util.ArrayList;
import java.util.List;

import filipe.pires.me.sectionadapter.delegate.GenericCell;

public class RecyclerSection {

    @VisibleForTesting
    GenericCell header;
    @VisibleForTesting
    List<GenericCell> elements = new ArrayList<>();
    @VisibleForTesting
    GenericCell footer;

    public int getItemCount() {
        int itemCount = elements.size();
        if (hasHeader())
            itemCount++;
        if (hasFooter())
            itemCount++;
        return itemCount;
    }

    public boolean hasHeader() {
        return header != null;
    }

    public boolean hasFooter() {
        return footer != null;
    }

    public GenericCell getItemAtPosition(int positionInsideSection) {
        if (isHeader(positionInsideSection))
            return header;
        else if (isFooter(positionInsideSection))
            return footer;
        else if (hasHeader())
            return elements.get(positionInsideSection - 1);
        else
            return elements.get(positionInsideSection);
    }

    private boolean isFooter(int positionInsideSection) {
        return positionInsideSection > elements.size() && hasFooter();
    }

    private boolean isHeader(int positionInsideSection) {
        return positionInsideSection == 0 && hasHeader();
    }

    public void addElement(GenericCell element) {
        elements.add(element);
    }

    public void addAll(List<GenericCell> elements) {
        this.elements = elements;
    }

    public void addHeader(GenericCell header) {
        this.header = header;
    }

    public void addFooter(GenericCell footer) {
        this.footer = footer;
    }

    public void removeHeader() {
        header = null;
    }

    public void removeFooter() {
        footer = null;
    }

    public int indexOf(GenericCell cell) {
        if (cell.equals(header))
            return 0;

        if (cell.equals(footer))
            return getItemCount() - 1;

        int base = hasHeader() ? 1 : 0;

        for (int i = 0; i < elements.size(); i++)
            if (elements.get(i).equals(cell))
                return base + i;

        return -1;
    }
}
