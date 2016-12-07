/*
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package by.onliner.news.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

import by.onliner.news.enums.NewsType;
import by.onliner.news.Fragments.tabs.TabAuto;
import by.onliner.news.Fragments.tabs.TabBase;
import by.onliner.news.Fragments.tabs.TabPeople;
import by.onliner.news.Fragments.tabs.TabRealt;
import by.onliner.news.Fragments.tabs.TabTechnologies;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private CharSequence Titles[];
    private int NumbOfTabs;

    private Map<Integer, TabBase> mPageReferenceMap;

    public ViewPagerAdapter(FragmentManager fm,CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
        this.mPageReferenceMap = new HashMap<>();
    }

    @Override
    public Fragment getItem(int position) {
        TabBase tab;
        switch (NewsType.fromInteger(position)) {
            case Autos:
                tab = new TabAuto();
                break;
            case Technologies:
                tab = new TabTechnologies();
                break;
            case Peoples:
                tab = new TabPeople();
                break;
            case Realt:
                tab = new TabRealt();
                break;
            default:
                throw new UnsupportedOperationException("Unknown tab type, position: " + position);
        }

        mPageReferenceMap.put(position, tab);
        return tab;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        mPageReferenceMap.remove(position);
    }

    public TabBase getFragment(int index) {
        return mPageReferenceMap.get(index);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}