package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.room.FatouDatabase
import com.example.data.room.PregnancyRepositoryRoom
import com.example.data.room.PregnancyStateEntity
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class PregnancyUiState(
    val fetDateMillis: Long = 1793836800000L, // Nov 5, 2026
    val lmpDateMillis: Long = 1792195200000L, // Oct 15, 2026
    val currentWeek: Int = 12,
    val currentDaysInWeek: Int = 3,
    val daysSinceTransfer: Int = 68, // Days Post Transfer (DPT) specifically for J5 FET
    val totalTransferDays: Int = 266, // 266 days from D5 FET to full term (40 SA)
    val dptProgressFraction: Float = 0.255f, // Progress fraction (0f to 1f)
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
            val metrics = calculatePregnancyMetrics(state.fetDateMillis)
            val dpt = metrics.third
            val fraction = (dpt.toFloat() / 266f).coerceIn(0f, 1f)
            PregnancyUiState(
                fetDateMillis = state.fetDateMillis,
                lmpDateMillis = state.lmpDateMillis,
                currentWeek = metrics.first,
                currentDaysInWeek = metrics.second,
                daysSinceTransfer = dpt,
                totalTransferDays = 266,
                dptProgressFraction = fraction,
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
            // Recalculate LMP as FET date minus 19 days (Day 5 transfer rule: 2 weeks + 5 days)
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

    private fun calculatePregnancyMetrics(fetDateMillis: Long): Triple<Int, Int, Int> {
        val now = System.currentTimeMillis()
        val diffMillis = now - fetDateMillis
        val diffDays = (diffMillis / (1000L * 60L * 60L * 24L)).toInt()

        // For Day 5 blastocyst transfer, the embryo is 19 days gestational age on transfer day (DPT 0).
        // If current date is >= fetDate, daysSinceTransfer = diffDays.
        // For protocol preview / active week calculation, default to 68 DPT (Week 12 + 3d).
        val daysSinceTransfer = if (diffDays >= 0) diffDays.coerceIn(0, 266) else 68
        val totalGestationalDays = 19 + daysSinceTransfer
        val weeks = (totalGestationalDays / 7).coerceIn(1, 40)
        val days = (totalGestationalDays % 7).coerceIn(0, 6)
        return Triple(weeks, days, daysSinceTransfer)
    }
}
