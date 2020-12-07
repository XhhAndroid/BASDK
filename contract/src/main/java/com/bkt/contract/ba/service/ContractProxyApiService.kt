package com.bkt.contract.ba.service

import com.bkt.contract.ba.model.dto.DepthEventDto
import com.bkt.contract.ba.model.dto.ExchangeInfoDto
import com.bkt.contract.ba.model.dto.KLineEventDto
import com.bkt.contract.ba.model.dto.TickerEventDto
import com.google.gson.JsonObject
import io.reactivex.Observable
import retrofit2.CacheType
import retrofit2.http.Cache
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @Description: ba Proxy http api
 * @Author: XGod
 * @CreateDate: 2020/12/1 20:10
 */
interface ContractProxyApiService {
    /**
     * 测试api
     *
     * @return
     */
    @GET("telematics/v3/weather?location=%E5%98%89%E5%85%B4&output=json&ak=5slgyqGDENN7Sy7pw29IUvrZ")
    fun testApi(): Observable<JsonObject>


    /**
     * 获取交易规则和交易对
     * https://binance-docs.github.io/apidocs/futures/cn/#0f3f2d5ee7
     */
    @GET("fapi/v1/exchangeInfo")
    fun getExchangeInfo(@Cache type: CacheType): Observable<ExchangeInfoDto>;


    /**
     * 单个交易对 24hr价格变动情况
     * https://binance-docs.github.io/apidocs/futures/cn/#24hr
     */
    @GET("fapi/v1/ticker/24hr")
    fun getTicker24hr(@Cache type: CacheType, @Query("symbol") symbol: String): Observable<TickerEventDto>;

    /**
     * 所有交易对 24hr价格变动情况
     * https://binance-docs.github.io/apidocs/futures/cn/#24hr
     */
    @GET("fapi/v1/ticker/24hr")
    fun getTicker24hr(@Cache type: CacheType): Observable<List<TickerEventDto>>;


    /**
     * K线数据
     *
     * https://binance-docs.github.io/apidocs/futures/cn/#k
     * @param symbol    STRING	YES	交易对
     * @param interval    ENUM	YES	时间间隔
     * @param startTime    LONG	NO	起始时间
     * @param  endTime    LONG	NO	结束时间
     * @param  limit    INT	NO	默认值:500 最大值:1500.
     */
    @GET("fapi/v1/klines")
    fun getKLines(@Cache type: CacheType,
                  @Query("symbol") symbol: String,
                  @Query("interval") interval: String,
                  @Query("startTime") startTime: Long,
                  @Query("endTime") endTime: Long,
                  @Query("limit") limit: Int): Observable<List<KLineEventDto>>

    /**
     * 获取深度
     * https://binance-docs.github.io/apidocs/futures/cn/#0f3f2d5ee7
     * @param limit  默认 500; 可选值:[5, 10, 20, 50, 100, 500, 1000]
     */
    @GET("fapi/v1/depth")
    fun getDepth(@Cache type: CacheType,
                 @Query("symbol") symbol: String,
                 @Query("limit") limit: Int): Observable<DepthEventDto>;


}