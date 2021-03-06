package com.bkt.contract.ba.service

import com.bkt.contract.ba.common.HttpDataFunction
import com.bkt.contract.ba.enums.ContractType
import com.bkt.contract.ba.model.po.DepthEventDtoPo
import com.bkt.contract.ba.sdk.BaClient
import com.bkt.contract.ba.service.inner.DepthDbService
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import retrofit2.CacheType
import java.util.concurrent.TimeUnit

/**
 * @Description: 深度服务
   * @Author: XGod  xuanyouwu@163.com  17611639080  https://github.com/NBXXF     https://blog.csdn.net/axuanqq
 * @CreateDate: 2020/12/10 15:45
 */
interface DepthService : ExportService {

    companion object {
        internal val INSTANCE: DepthService by lazy {
            object : DepthService {
            }
        }
    }

    /**
     *  获取深度   max item=20
     *  @param symbol 交易对名称
     *  @param cacheType 缓存类型
     *  @param cacheTime 缓存时间
     */
    fun getDepth(symbol: String,
                 cacheType: CacheType = CacheType.onlyRemote,
                 cacheTime: Long = TimeUnit.MINUTES.toMillis(5)): Observable<DepthEventDtoPo> {
        return PairService.INSTANCE
                .getPairType(symbol)
                .flatMap(object : Function<ContractType, ObservableSource<DepthEventDtoPo>> {
                    override fun apply(t: ContractType): ObservableSource<DepthEventDtoPo> {
                        return BaClient.instance.initializer!!.getApiService(t).getDepth(cacheType, cacheTime, symbol, 20)
                                .map(HttpDataFunction())
                                .map(object : Function<DepthEventDtoPo, DepthEventDtoPo> {
                                    override fun apply(t: DepthEventDtoPo): DepthEventDtoPo {
                                        /**
                                         * 接口返回没有这个字段 这里装载一下
                                         */
                                        t.symbol = symbol;
                                        return t;
                                    }
                                });
                    }
                });
    }

    /**
     * 订阅深度变化  全量
     *  max item=20
     *  @param symbol 交易对名称
     */
    fun subDepth(symbol: String): Observable<DepthEventDtoPo> {
        return Observable.merge(
                PairService.INSTANCE
                        .getPairType(symbol)
                        .flatMap(object : Function<ContractType, ObservableSource<DepthEventDtoPo>> {
                            override fun apply(t: ContractType): ObservableSource<DepthEventDtoPo> {
                                return BaClient.instance.initializer!!.getSocketService(t).subDepth(symbol);
                            }
                        }).flatMap(object : Function<DepthEventDtoPo, ObservableSource<DepthEventDtoPo>> {
                            override fun apply(t: DepthEventDtoPo): ObservableSource<DepthEventDtoPo> {
                                return Observable.empty();
                            }
                        }),
                DepthDbService.subChange(symbol));
    }
}