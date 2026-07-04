package com.example.myresponsive.activity.ui.transform

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TransformViewModel : ViewModel() {

    private val _texts = MutableLiveData<List<String>>().apply {
        value = listOf(
            "Item 1",
            "Item 2",
            "Item 3",
            "Item 4",
            "Item 5",
            "Item 6",
            "Item 7",
            "Item 8",
            "Item 9",
            "Item 10",
            "Item 11",
            "Item 12",
            "Item 13",
            "Item 14",
            "Item 15",
            "Item 16"
        )
    }
    val texts: LiveData<List<String>> = _texts

    private val _dex2CItems = MutableLiveData<List<Dex2CItem>>()
    val dex2CItems: LiveData<List<Dex2CItem>> = _dex2CItems

    private val _selectedItems = MutableLiveData<Set<String>>()
    val selectedItems: LiveData<Set<String>> = _selectedItems

    init {
        loadDex2CItems()
    }

    private fun loadDex2CItems() {
        val items = listOf(
            Dex2CItem("Lcom/example/myresponsive/activity/MainActivity;", "MainActivity"),
            Dex2CItem("Lcom/example/myresponsive/activity/ui/transform/TransformFragment;", "TransformFragment"),
            Dex2CItem("Lcom/example/myresponsive/activity/ui/transform/TransformViewModel;", "TransformViewModel"),
            Dex2CItem("Lcom/example/myresponsive/activity/ui/slideshow/SlideshowFragment;", "SlideshowFragment"),
            Dex2CItem("Lcom/example/myresponsive/activity/ui/slideshow/SlideshowViewModel;", "SlideshowViewModel"),
            Dex2CItem("Lcom/example/myresponsive/activity/ui/settings/SettingsFragment;", "SettingsFragment"),
            Dex2CItem("Lcom/example/myresponsive/activity/ui/settings/SettingsViewModel;", "SettingsViewModel"),
            Dex2CItem("Lcom/example/myresponsive/activity/ui/reflow/ReflowFragment;", "ReflowFragment"),
            Dex2CItem("Lcom/example/myresponsive/activity/ui/reflow/ReflowViewModel;", "ReflowViewModel"),
            Dex2CItem("Lcom/example/myresponsive/activity/ui/transform/SignatureVerifier;", "SignatureVerifier"),
            Dex2CItem("Lcom/example/myresponsive/activity/ui/transform/Dex2CSelectorFragment;", "Dex2CSelectorFragment")
        )
        _dex2CItems.value = items
    }

    fun toggleItemSelection(classDescriptor: String) {
        val current = _selectedItems.value?.toMutableSet() ?: mutableSetOf()
        if (current.contains(classDescriptor)) {
            current.remove(classDescriptor)
        } else {
            current.add(classDescriptor)
        }
        _selectedItems.value = current
    }

    fun applyDex2CSelection(): List<String> {
        return _selectedItems.value?.toList() ?: emptyList()
    }
}

data class Dex2CItem(
    val classDescriptor: String,
    val displayName: String
)