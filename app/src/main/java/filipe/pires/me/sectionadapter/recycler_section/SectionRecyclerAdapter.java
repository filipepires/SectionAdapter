package filipe.pires.me.sectionadapter.recycler_section;


import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import filipe.pires.me.sectionadapter.delegate.AdapterDelegate;
import filipe.pires.me.sectionadapter.AdapterDelegateManager;
import filipe.pires.me.sectionadapter.delegate.GenericCell;

public class SectionRecyclerAdapter extends RecyclerView.Adapter {

    private AdapterDelegateManager delegateManager;
    private RecyclerSectionCluster sections;

    public static SectionRecyclerAdapter createAdapterWith(AdapterDelegate... delegates) {
        SectionRecyclerAdapter adapter = new SectionRecyclerAdapter();
        AdapterDelegateManager delegateManager = new AdapterDelegateManager();

        for (AdapterDelegate delegate : delegates)
            delegateManager.addDelegate(delegate);

        adapter.setDelegateManager(delegateManager);
        adapter.initSections();
        return adapter;
    }

    private void initSections() {
        sections = new RecyclerSectionCluster();
    }
    
    public void clear() {
        int totalNumberOfElements = sections.getItemCount();
        sections.clear();
        notifyItemRangeRemoved(0, totalNumberOfElements);
    }

    public void addHeader(GenericCell header, int section) {
        sections.addHeader(header, section);
        int headerPosition = sections.getHeaderPosition(section);
        notifyItemInserted(headerPosition);
    }

    public void addHeader(GenericCell header) {
        sections.addHeader(header);
        int headerPosition = sections.getHeaderPosition();
        notifyItemInserted(headerPosition);
    }

    public void removeHeader() {
        int headerPosition = sections.getHeaderPosition();
        sections.removeHeader();
        notifyItemRemoved(headerPosition);
    }

    public void removeHeader(int section) {
        int headerPosition = sections.getHeaderPosition(section);
        sections.removeHeader(section);
        notifyItemRemoved(headerPosition);
    }

    public void addFooter(GenericCell footer, int section) {
        sections.addFooter(footer, section);
        int footerPosition = sections.getFooterPosition(section);
        notifyItemInserted(footerPosition);
    }

    public void addFooter(GenericCell footer) {
        sections.addFooter(footer);
        int footerPosition = sections.getFooterPosition();
        notifyItemInserted(footerPosition);
    }

    public void removeFooter(int section) {
        int footerPosition = sections.getFooterPosition(section);
        sections.removeFooter(footerPosition);
        notifyItemRemoved(footerPosition);
    }

    public void removerFooter() {
        int footerPosition = sections.getFooterPosition();
        sections.removeFooter();
        notifyItemRemoved(footerPosition);
    }

    public void addElement(GenericCell element) {
        sections.addElement(element);
        notifyItemInserted(sections.getItemCount());
    }

    public void addElementToSection(GenericCell element, int section) {
        sections.addElement(element, section);
        int elementPosition = sections.getLastElementPositionFromSection(section);
        notifyItemInserted(elementPosition);
    }

    public void addSection(List<GenericCell> elements) {
        int startPosition = sections.getItemCount();
        sections.addSection(elements);
        notifyItemRangeInserted(startPosition, elements.size());
    }

    public void removeSection(int section) {
        int startingPosition = sections.getStartSectionPositionForSection(section);
        int size = sections.getItemCountForSection(section);
        sections.removeSection(section);
        notifyItemRangeRemoved(startingPosition, size);
    }

    @Override
    public int getItemViewType(int position) {
        GenericCell genericCell = sections.getItemAtPosition(position);
        return delegateManager.getItemViewType(genericCell);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return delegateManager.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GenericCell genericCell = sections.getItemAtPosition(position);
        delegateManager.onBindViewHolder(genericCell, holder, position);
    }

    @Override
    public int getItemCount() {
        return sections.getItemCount();
    }

    public void setDelegateManager(AdapterDelegateManager delegateManager) {
        this.delegateManager = delegateManager;
    }

    public int indexOf(GenericCell cell) {
        return sections.indexOf(cell);
    }

    public void notifyItemChanged(GenericCell cell) {
        notifyItemChanged(indexOf(cell));
    }

    public void clearLastSection() {
        sections.clearLastSection();
        notifyItemRangeRemoved(sections.getFirstItemInLastSection(), getItemCount() - 1);
    }

    public void addSection() {
        addSection(new ArrayList<GenericCell>());
    }

}
