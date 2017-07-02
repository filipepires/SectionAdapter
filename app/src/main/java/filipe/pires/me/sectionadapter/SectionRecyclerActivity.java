package filipe.pires.me.sectionadapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import filipe.pires.me.sectionadapter.delegate.ElementDelegate;
import filipe.pires.me.sectionadapter.delegate.FooterDelegate;
import filipe.pires.me.sectionadapter.delegate.GenericCell;
import filipe.pires.me.sectionadapter.delegate.HeaderDelegate;
import filipe.pires.me.sectionadapter.recycler_section.RecyclerDivider;
import filipe.pires.me.sectionadapter.recycler_section.SectionRecyclerAdapter;

public class SectionRecyclerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_recycler);

        SectionRecyclerAdapter adapter = initRecycler();
        fillAdapter(adapter);
    }

    private SectionRecyclerAdapter initRecycler() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.section_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        RecyclerDivider divider = new RecyclerDivider(getApplicationContext());
        divider.setPadding(R.dimen.activity_horizontal_margin);
        recyclerView.addItemDecoration(divider);

        SectionRecyclerAdapter adapter = SectionRecyclerAdapter.createAdapterWith(
                new HeaderDelegate(getApplicationContext(), 0),
                new ElementDelegate(getApplicationContext(), 1),
                new FooterDelegate(getApplicationContext(), 2));
        recyclerView.setAdapter(adapter);
        return adapter;
    }

    private void fillAdapter(SectionRecyclerAdapter adapter) {
        for (int i = 0; i < 10; i++) {
            adapter.addHeader(new HeaderDelegate.Cell("Header " + (i + 1)));
            initSection(adapter);
            adapter.addFooter(new FooterDelegate.Cell("Footer " + (i + 1)));
        }
    }

    private void initSection(SectionRecyclerAdapter adapter) {
        List<GenericCell> cells = new ArrayList<>();
        for (int j = 0; j < 5; j++) {
            cells.add(new ElementDelegate.Cell("Element " + (j + 1)));
        }
        adapter.addSection(cells);
    }
}
