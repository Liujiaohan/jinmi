package com.cyl.musicapi.netease

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap
import retrofit2.http.Url



interface NeteaseApiService {
    //    @Headers({"referer: http://music.163.com"})
    @GET("top/playlist")
    fun getTopPlaylist(@Query("cat") cat: String? = null, @Query("limit") limit: Int): Observable<NeteasePlaylist>

    @GET("/top/playlist/highquality")
    fun getTopPlaylistHigh(@QueryMap map: MutableMap<String, Any>): Observable<NeteasePlaylist>

    @GET("/playlist/detail")
    fun getPlaylistDetail(@Query("id") id: String): Observable<NeteasePlaylistDetail>

    //    @Headers({"referer: http://music.163.com"})
    @GET("/toplist/artist")
    fun getTopArtists(@Query("offset") offset: Int, @Query("limit") limit: Int): Observable<ArtistsInfo>


    @GET("/mv/first")
    fun getNewestMv(@Query("limit") limit: Int): Observable<MvInfo>


    @GET
    fun searchNetease(@Url url: String): Observable<SearchInfo>


    @GET("/top/mv")
    fun getTopMv(@Query("offset") offset: Int, @Query("limit") limit: Int): Observable<MvInfo>

    @GET("/mv/detail")
    fun getMvDetailInfo(@Query("mvid") mvid: String): Observable<MvDetailInfo>

    @GET("simi/mv")
    fun getSimilarMv(@Query("mvid") mvid: String): Observable<SimilarMvInfo>

    @GET("comment/mv")
    fun getMvComment(@Query("id") id: String): Observable<MvComment>

    @GET("search/hot")
    fun getHotSearchInfo(): Observable<SearchInfo>

    @GET("playlist/catlist")
    fun getCatList(): Observable<CatListBean>

    @GET("banner")
    fun getBanner(): Observable<BannerResult>

    @GET("login/cellphone")
    fun loginPhone(@Query("phone") phone: String, @Query("password") password: String): Observable<LoginInfo>

    @GET("login")
    fun loginEmail(@Query("email") email: String, @Query("password") password: String): Observable<LoginInfo>


    @GET("/login/status")
    fun getLoginStatus(): Observable<LoginInfo>


    @GET("recommend/songs")
    fun recommendSongs(): Observable<RecommendSongsInfo>


    @GET("recommend/resource")
    fun recommendPlaylist(): Observable<RecommendPlaylist>


    @GET("/personalized")
    fun personalizedPlaylist(): Observable<PersonalizedInfo>


    @GET("/personalized/mv")
    fun personalizedMv(): Observable<PersonalizedInfo>


    @GET("/user/playlist")
    fun getUserPlaylist(@Query("uid") uid: String): Observable<NeteasePlaylist>

}
