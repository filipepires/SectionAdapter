package filipe.pires.me.sectionadapter.recycler_section;


import java.util.ArrayList;
import java.util.List;

import filipe.pires.me.sectionadapter.delegate.GenericCell;

public class RecyclerSectionCluster {

    List<RecyclerSection> sections = new ArrayList<>();

    public int getItemCount() {
        int itemCount = 0;
        for (RecyclerSection section : sections)
            itemCount += section.getItemCount();
        return itemCount;
    }

    public GenericCell getItemAtPosition(int position) {
        boolean itemBelongsToSection = false;
        int section = 0;
        int positionInsideSection = position;
        int totalSize = 0;

        while (!itemBelongsToSection) {
            totalSize += sections.get(section).getItemCount();
            if (totalSize <= position) {
                section++;
                positionInsideSection = position - totalSize;
            } else
                itemBelongsToSection = true;
        }
        return sections.get(section).getItemAtPosition(positionInsideSection);
    }

    public int indexOf(GenericCell cell) {
        int count = 0;
        for (RecyclerSection section : sections) {
            int index = section.indexOf(cell);
            if (index >= 0)
                return count + index;
            else
                count += section.getItemCount();
        }
        return -1;
    }

    public void addSection() {
        sections.add(new RecyclerSection());
    }

    public void addElement(GenericCell element, int section) {
        sections.get(section).addElement(element);
    }

    public void addElement(GenericCell element) {
        int lastSection = sections.size() - 1;
        sections.get(lastSection).addElement(element);
    }

    public void addSection(List<GenericCell> elements) {
        int lastSection = getLastSection();
        sections.get(lastSection).addAll(elements);
    }

    private int getLastSection() {
        int lastSection = sections.size() - 1;
        if (lastSection < 0) {
            lastSection = 0;
            addSection();
        } else {
            if (!sections.get(lastSection).hasFooter() && !sections.get(lastSection).hasHeader()) {
                addSection();
                lastSection = sections.size() - 1;
            }
        }
        return lastSection;
    }

    public void clear() {
        sections.clear();
    }

    public int getLastElementPositionFromSection(int section) {
        int i = 0;
        int position = 0;
        while (i < section) {
            position += sections.get(i).getItemCount();
            i++;
        }
        return position;
    }

    public void addHeader(GenericCell header, int section) {
        sections.get(section).addHeader(header);
    }

    public void addHeader(GenericCell header) {
        int lastSection = 0;
        if (!sections.isEmpty()) {
            lastSection = sections.size() - 1;
            if (sections.get(lastSection).hasHeader()) {
                addSection();
                lastSection++;
            }
        } else {
            addSection();
        }
        addHeader(header, lastSection);
    }

    public int getHeaderPosition(int section) {
        int position = 0;
        int i = 0;
        while (i < section) {
            position += sections.get(i).getItemCount();
            i++;
        }
        return position;
    }

    public int getHeaderPosition() {
        int lastSection = sections.size() - 1;
        return getHeaderPosition(lastSection);
    }

    public void removeHeader(int section) {
        sections.get(section).removeHeader();
    }

    public void removeHeader() {
        int lastSection = sections.size() - 1;
        removeHeader(lastSection);
    }

    public void addFooter(GenericCell footer, int section) {
        sections.get(section).addFooter(footer);
    }

    public void addFooter(GenericCell footer) {
        int lastSection = 0;
        if (!sections.isEmpty()) {
            lastSection = sections.size() - 1;
            if (sections.get(lastSection).hasFooter()) {
                addSection();
                lastSection++;
            }
        } else {
            addSection();
        }
        addFooter(footer, lastSection);
    }

    public int getFooterPosition(int section) {
        int position = 0;
        int i = 0;
        while (i <= section) {
            position += sections.get(i).getItemCount();
            i++;
        }
        position--;
        return position;
    }

    public int getFooterPosition() {
        int lastSection = sections.size() - 1;
        return getFooterPosition(lastSection);
    }

    public void removeFooter(int section) {
        sections.get(section).removeFooter();
    }

    public void removeFooter() {
        int lastSection = sections.size() - 1;
        removeFooter(lastSection);
    }

    public int getStartSectionPositionForSection(int section) {
        int position = 0;
        int i = 0;
        while (i < section) {
            position += sections.get(i).getItemCount();
            i++;
        }
        return position;
    }

    public int getItemCountForSection(int section) {
        return sections.get(section).getItemCount();
    }

    public void removeSection(int section) {
        sections.remove(section);
    }

    public void clearLastSection() {
        sections.remove(sections.size() - 1);
    }

    public int getFirstItemInLastSection() {
        int lastSectionSize = getItemCountForSection(sections.size() - 1);
        return lastSectionSize - getItemCount();
    }

}
