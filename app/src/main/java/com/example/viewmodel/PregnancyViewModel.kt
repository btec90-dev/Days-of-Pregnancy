package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.room.FatouDatabase
import com.example.data.room.PregnancyRepositoryRoom
import com.example.data.room.PregnancyStateEntity
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Calendar

data class PregnancyUiState(
    val fetDateMillis: Long = 1793836800000L, // Nov 5, 2026
    val lmpDateMillis: Long = 1792195200000L, // Oct 15, 2026
    val currentWeek: Int = 12,
    val currentDaysInWeek: Int = 3,
    val babyName: String = "Petit Garçon (46, XY)",
    val notes: String = "Transfert Blastocyste J5 euploïde réussi à RMA of New York."
)

class PregnancyViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PregnancyRepositoryRoom

    init {
        val dao = FatouDatabase.getDatabase(application).pregnancyDao()
        repository = PregnancyRepositoryRoom(dao)
        // Ensure default state exists
        viewModelScope.launch {
            repository.pregnancyState.collect { state ->
                if (state == null) {
                    repository.savePregnancyState(PregnancyStateEntity())
                }
            }
        }
    }

    val uiState: StateFlow<PregnancyUiState> = repository.pregnancyState
        .map { entity ->
            val state = entity ?: PregnancyStateEntity()
            val currentWeekAndDays = calculatePregnancyWeek(state.fetDateMillis)
            PregnancyUiState(
                fetDateMillis = state.fetDateMillis,
                lmpDateMillis = state.lmpDateMillis,
                currentWeek = currentWeekAndDays.first,
                currentDaysInWeek = currentWeekAndDays.second,
                babyName = state.babyName,
                notes = state.notes
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PregnancyUiState()
        )

    fun updateFetDate(newFetDateMillis: Long) {
        viewModelScope.launch {
            // Recalculate LMP as FET date minus 19 days (Day 5 transfer rule)
            val lmpMillis = newFetDateMillis - (19L * 24L * 60L * 60L * 1000L)
            val currentState = repository.pregnancyState.first() ?: PregnancyStateEntity()
            repository.savePregnancyState(
                currentState.copy(
                    fetDateMillis = newFetDateMillis,
                    lmpDateMillis = lmpMillis
                )
            )
        }
    }

    private fun calculatePregnancyWeek(fetDateMillis: Long): Pair<Int, Int> {
        val now = System.currentTimeMillis()
        val diffMillis = now - fetDateMillis
        val diffDays = (diffMillis / (1000L * 60L * 60L * 24L)).toInt()

        // Day 5 transfer equals 19 days gestational age at transfer date
        val totalGestationalDays = 19 + diffDays
        val weeks = (totalGestationalDays / 7).coerceIn(1, 40)
        val days = (totalGestationalDays % 7).coerceIn(0, 6)
        return Pair(weeks, days)
    }
}
