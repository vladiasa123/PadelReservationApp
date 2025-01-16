package com.example.padel.api


import com.example.padel.data.AvailabilityRequest
import com.example.padel.data.AvailabilityResponse
import com.example.padel.data.LoginResponse
import com.example.padel.data.PageAcces
import com.example.padel.data.PageAccesResponse
import com.example.padel.data.ReservationRequest
import com.example.padel.data.ReservationResponse
import com.example.padel.data.UserLoginRequest
import com.example.padel.data.UserSignupRequest
import com.example.padel.data.UsersReservation
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("/req/signup")
    suspend fun signup(@Body user: UserSignupRequest): Response<UserSignupRequest>

    @POST("/req/login")
    suspend fun login(@Body user: UserLoginRequest): Response<LoginResponse>

    @GET("/req/login")
    suspend fun login(): Response<LoginResponse>

    @POST("/req/accesPage")
    suspend fun acces(@Body user: PageAcces): Response<PageAccesResponse>

    @POST("/req/Reservation")
    suspend fun sendReservation(@Body user: ReservationRequest): Response<ReservationResponse>

    @POST("/req/availability")
    suspend fun checkAvailability(@Body user: AvailabilityRequest): Response<AvailabilityResponse>

    @POST("req/userReservation")
    suspend fun getUsersReservations(@Body user: UsersReservation): Response<ReservationResponse>

}