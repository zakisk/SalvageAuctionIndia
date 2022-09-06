package com.example.salvageauctionindia.domain.use_cases

import com.example.salvageauctionindia.data.remote.dtos.VehicleDto
import com.example.salvageauctionindia.data.remote.dtos.asDomain
import com.example.salvageauctionindia.domain.interfaces.VehicleDataSource
import com.example.salvageauctionindia.domain.model.DataState
import com.example.salvageauctionindia.domain.model.Vehicle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetVehiclesUseCase @Inject constructor(
    private val vehicleDataSource: VehicleDataSource
) {

    operator fun invoke(sellType: String? = null, userId : String): Flow<DataState<List<Vehicle>>> = flow {
        try {
            emit(DataState.Loading<Nothing>())
            val vehiclesDto: List<VehicleDto> = if (sellType != null) {
                vehicleDataSource.getVehicles(sellType, userId)
            } else {
                vehicleDataSource.getVehicles(userId)
            }
            val data = vehiclesDto.map { it.asDomain() }
            emit(DataState.Success(data = data))
        } catch (e: HttpException) {
            emit(DataState.Error(e.localizedMessage ?: "Unexpected Error"))
        } catch (e: IOException) {
            emit(DataState.Error("Couldn't reach server, Check your Internet Connection"))
        }
    }
}