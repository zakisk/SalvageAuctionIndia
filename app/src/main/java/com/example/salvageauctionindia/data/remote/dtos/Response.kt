package com.example.salvageauctionindia.data.remote.dtos

data class Response (
    val fieldCount: Int? = null,
    val affectedRows: Int? = null,
    val insertId: Int? = null,
    val serverStatus: Int? = null,
    val warningCount: Int? = null,
    val message: String? = null,
    val protocol41: Boolean? = null,
    val changedRows: Int? = null,
    val errorMessage: String? = null
) {
    fun isNotNull() : Boolean {
        return fieldCount != null && affectedRows != null &&
               insertId != null && serverStatus != null &&
               warningCount != null && message != null &&
               protocol41 != null && changedRows != null
    }
}

//{"fieldCount":0,"affectedRows":1,"insertId":0,"serverStatus":2,"warningCount":0,"message":"","protocol41":true,"changedRows":0}
