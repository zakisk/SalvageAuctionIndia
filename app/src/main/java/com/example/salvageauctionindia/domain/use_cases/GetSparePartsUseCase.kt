package com.example.salvageauctionindia.domain.use_cases

import com.example.salvageauctionindia.domain.interfaces.SparePartDataSource
import com.example.salvageauctionindia.domain.model.DataState
import com.example.salvageauctionindia.domain.model.SparePart
import com.example.salvageauctionindia.util.asDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class GetSparePartsUseCase @Inject constructor(
    private val sparePartDataSource: SparePartDataSource
) {

    operator fun invoke(userId: String, byId: Boolean = false): Flow<DataState<List<SparePart>>> =
        flow {
            try {
                emit(DataState.Loading<Nothing>())
                val sparePartsDto = if (byId) {
                    sparePartDataSource.getSparePartsById(userId)
                } else {
                    sparePartDataSource.getSpareParts(userId)
                }
                val data = sparePartsDto.map { it.asDomain() }
                emit(DataState.Success(data = data))
            } catch (e: HttpException) {
                emit(DataState.Error(e.localizedMessage ?: "Unexpected Error"))
            } catch (e: IOException) {
                emit(DataState.Error("Couldn't reach server, Check your Internet Connection"))
            }
        }

}