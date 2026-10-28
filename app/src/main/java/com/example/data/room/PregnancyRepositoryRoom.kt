package com.example.data.room

import kotlinx.coroutines.flow.Flow

class PregnancyRepositoryRoom(private val pregnancyDao: PregnancyDao) {
    val pregnancyState: Flow<PregnancyStateEntity?> = pregnancyDao.getPregnancyState()

    suspend fun savePregnancyState(state: PregnancyStateEntity) {
        pregnancyDao.insertPregnancyState(state)
    }
}
