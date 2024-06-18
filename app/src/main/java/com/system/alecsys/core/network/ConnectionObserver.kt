package com.system.alecsys.core.network

import kotlinx.coroutines.flow.Flow

interface ConnectionObserver {
    fun observe(): Flow<Status>
}