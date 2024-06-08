package com.system.hasilkarya.core.network

import com.system.hasilkarya.dashboard.data.CheckDriverIdResponse
import com.system.hasilkarya.dashboard.data.CheckStationIdResponse
import com.system.hasilkarya.dashboard.data.CheckTokenResponse
import com.system.hasilkarya.dashboard.data.CheckTruckIdResponse
import com.system.hasilkarya.heavy_vehicle_fuel.data.CheckHeavyVehicleIdResponse
import com.system.hasilkarya.heavy_vehicle_fuel.data.HeavyVehicleFuelLogRequest
import com.system.hasilkarya.heavy_vehicle_fuel.data.HeavyVehicleFuelRequest
import com.system.hasilkarya.heavy_vehicle_fuel.data.HeavyVehicleFuelResponse
import com.system.hasilkarya.heavy_vehicle_fuel.data.HeavyVehicleLogResponse
import com.system.hasilkarya.login.data.LoginRequest
import com.system.hasilkarya.login.data.LoginResponse
import com.system.hasilkarya.material.data.PostMaterialLogRequest
import com.system.hasilkarya.material.data.PostMaterialRequest
import com.system.hasilkarya.material.data.PostMaterialResponse
import com.system.hasilkarya.material.data.PostToLogResponse
import com.system.hasilkarya.profile.data.LogoutResponse
import com.system.hasilkarya.station.data.GetStationResponse
import com.system.hasilkarya.truck_fuel.data.TruckFuelLogRequest
import com.system.hasilkarya.truck_fuel.data.TruckFuelLogResponse
import com.system.hasilkarya.truck_fuel.data.TruckFuelRequest
import com.system.hasilkarya.truck_fuel.data.TruckFuelResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiServices {

    @POST("login")
    fun login(
        @Body request: LoginRequest
    ): Call<LoginResponse>

    @POST("checker/material-movement/store")
    fun postMaterial(
        @Header("Authorization") token: String,
        @Body request: PostMaterialRequest
    ): Call<PostMaterialResponse>

    @GET("driver/check-availability/{id}")
    fun checkDriverId(
        @Path("id") driverId: String,
        @Header("Authorization") token: String
    ): Call<CheckDriverIdResponse>

    @GET("truck/check-availability/{id}")
    fun checkTruckId(
        @Path("id") truckId: String,
        @Header("Authorization") token: String
    ): Call<CheckTruckIdResponse>

    @GET("station/check-availability/gas/{id}")
    fun checkGasStationId(
        @Path("id") stationId: String,
        @Header("Authorization") token: String
    ): Call<CheckStationIdResponse>

    @GET("station/check-availability/mine/{id}")
    fun checkMineStationId(
        @Path("id") stationId: String,
        @Header("Authorization") token: String
    ): Call<CheckStationIdResponse>

    @POST("logout")
    fun logout(
        @Header("Authorization") token: String
    ): Call<LogoutResponse>

    @POST("checker/material-movement-error-log/store")
    fun postToLog(
        @Header("Authorization") token: String,
        @Body request: PostMaterialLogRequest
    ): Call<PostToLogResponse>

    @POST("gas-operator/fuel-log/truck/store")
    fun postFuelTruck(
        @Header("Authorization") token: String,
        @Body truckFuelRequest: TruckFuelRequest
    ): Call<TruckFuelResponse>

    @POST("fuel-consumption/truck")
    fun postFuelLog(
        @Header("Authorization") token: String,
        @Body truckFuelLogRequest: TruckFuelLogRequest
    ): Call<TruckFuelLogResponse>

    @POST("gas-operator/fuel-log/heavy-vehicle/store")
    fun postFuelHeavyVehicle(
        @Header("Authorization") token: String,
        @Body heavyVehicleFuelRequest: HeavyVehicleFuelRequest
    ): Call<HeavyVehicleFuelResponse>

    @POST("fuel-consumption/heavy-vehicle")
    fun postFuelHeavyVehicleLog(
        @Header("Authorization") token: String,
        @Body heavyVehicleLogRequest: HeavyVehicleFuelLogRequest
    ): Call<HeavyVehicleLogResponse>

    @GET("heavy-vehicle/check-availability/{id}")
    fun checkHeavyVehicleId(
        @Path("id") heavyVehicleId: String,
        @Header("Authorization") token: String
    ): Call<CheckHeavyVehicleIdResponse>

    @GET("checker/station/{id}")
    fun getStation(
        @Path("id") stationId: String,
        @Header("Authorization") token: String
    ): Call<GetStationResponse>

    @GET("me")
    fun checkToken(
        @Header("Authorization") token: String
    ): Call<CheckTokenResponse>

}