package com.app.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.app.domain.usecase.location.LocationManagerCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class ShowdownViewModel @Inject constructor(
    private val locationManagerCase: LocationManagerCase,
    @ApplicationContext private val appContext: Context
) : ViewModel() {
    
}