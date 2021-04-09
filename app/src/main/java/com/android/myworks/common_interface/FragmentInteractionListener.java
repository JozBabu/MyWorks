package com.android.myworks.common_interface;

import androidx.fragment.app.Fragment;

/**
 * @author Jose Babu
 * @since 30/12/2020
 */

public interface FragmentInteractionListener {
    void replaceFragment(Fragment fragment, String tag);
    void setTitle(String title);
    void handleProgress(boolean isShow);
}
