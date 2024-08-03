package com.fortatic.apps.places.domain.detail

import com.fortatic.apps.places.data.model.Place
import com.fortatic.apps.places.data.repository.DefaultHomeRepository
import com.fortatic.apps.places.di.IoDispatcher
import com.fortatic.apps.places.domain.FlowUseCase
import com.fortatic.apps.places.util.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FindSpecificPlaceUseCase @Inject constructor(
    private val defaultHomeRepository: DefaultHomeRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : FlowUseCase<String, Place>(dispatcher) {

    override fun execute(parameters: String): Flow<Result<Place>> {
        return defaultHomeRepository.getPlaces().map { list ->
            Result.Success(list.first { it.id == parameters })
        }
    }
}