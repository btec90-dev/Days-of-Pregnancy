package com.example.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pregnancy_state")
data class PregnancyStateEntity(
    @PrimaryKey val id: Int = 1,
    val fetDateMillis: Long = 1793836800000L, // Nov 5, 2026 default
    val lmpDateMillis: Long = 1792195200000L, // Oct 15, 2026 default
    val babyName: String = "Petit Garçon (46, XY)",
    val notes: String = "Transfert Blastocyste J5 euploïde réussi à RMA of New York."
)
