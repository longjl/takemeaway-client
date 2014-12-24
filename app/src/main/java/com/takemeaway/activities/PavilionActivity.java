package com.takemeaway.activities;

import android.os.Bundle;
import android.view.MenuItem;

import com.achep.header2actionbar.FadingActionBarHelper;
import com.takemeaway.R;
import com.takemeaway.fragments.PavilionFragment;

/**
 * Created by longjianlin on 14/12/23.
 */
public class PavilionActivity extends BaseActivity {
    private FadingActionBarHelper mFadingActionBarHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pavilion);
        mFadingActionBarHelper = new FadingActionBarHelper(getActionBar(), getResources().getDrawable(R.drawable.actionbar_bg));
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.container, new PavilionFragment()).commit();
        }

        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public FadingActionBarHelper getFadingActionBarHelper() {
        return mFadingActionBarHelper;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
