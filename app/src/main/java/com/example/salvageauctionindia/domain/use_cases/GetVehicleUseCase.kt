package com.example.salvageauctionindia.domain.use_cases

import com.example.salvageauctionindia.data.remote.dtos.asDomain
import com.example.salvageauctionindia.domain.interfaces.VehicleDataSource
import com.example.salvageauctionindia.domain.model.DataState
import com.example.salvageauctionindia.domain.model.Vehicle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.lang.NullPointerException
import javax.inject.Inject

class GetVehicleUseCase @Inject constructor(
    private val vehicleDataSource: VehicleDataSource
) {

    operator fun invoke(postId: String, userId: String) : Flow<DataState<Vehicle>> = flow {
        try {
            emit(DataState.Loading<Nothing>())
            val vehicleDto = vehicleDataSource.getVehicle(postId, userId)
            val data = vehicleDto!!.asDomain()
            emit(DataState.Success(data = data))
        } catch (e: HttpException) {
            emit(DataState.Error(e.localizedMessage ?: "Unexpected Error"))
        } catch (e: IOException) {
            emit(DataState.Error("Couldn't reach server, Check your Internet Connection"))
        } catch (e: NullPointerException) {
            emit(DataState.Error("Couldn't reach server, Problem in fetching  data"))
        }
    }

}