package com.maniraghu.flashchatnewfirebase;


import android.os.Bundle;

import androidx.fragment.app.Fragment;


public interface EventListener {
    void navigateToFragment(Fragment fragment, Bundle bundle);
    void setFragment(Fragment fragment);
}
