package com.fortatic.apps.places.domain.home

import com.fortatic.apps.places.data.model.HomePlace
import com.fortatic.apps.places.data.model.toHomePlace
import com.fortatic.apps.places.data.repository.DefaultHomeRepository
import com.fortatic.apps.places.di.IoDispatcher
import com.fortatic.apps.places.domain.FlowUseCase
import com.fortatic.apps.places.util.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoadPlacesUseCase @Inject constructor(
    private val defaultHomeRepository: DefaultHomeRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, List<HomePlace>>(dispatcher) {

    override fun execute(parameters: Unit): Flow<Result<List<HomePlace>>> {
        return defaultHomeRepository.getPlaces().map {
            Result.Success(it.toHomePlace())
        }
    }
}