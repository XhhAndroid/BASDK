package com.bkt.contract.ba.sdk

import com.bkt.contract.ba.enums.ContractType
import com.bkt.contract.ba.model.dto.TradeEventDto
import com.bkt.contract.ba.service.DepthService
import com.bkt.contract.ba.service.ExportService
import com.bkt.contract.ba.service.PairService
import com.bkt.contract.ba.service.TradeService
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import java.lang.RuntimeException

/**
 * @Description: BA 客户端
 * @Author: XGod
 * @CreateDate: 2020/12/3 20:47
 */
class BaClient private constructor() {

    companion object {
        @JvmStatic
        val instance: BaClient by lazy {
            BaClient();
        }
    }

    interface Initializer {
        fun getApiService(type: ContractType): ContractProxyApiService;
        fun getSocketService(type: ContractType): ContractProxySocketService;
    }

    internal var initializer: Initializer? = null
        get() {
            if (field == null) {
                throw RuntimeException("BaClient must init");
            }
            return field;
        }

    /**
     * 初始化
     */
    fun init(initializer: Initializer) {
        this.initializer = initializer;
    }

    fun <T : ExportService> getService(clazz: Class<T>): T {
        if (clazz == PairService::class.java) {
            return PairService.INSTANCE as T;
        }
        if (clazz == DepthService::class.java) {
            return DepthService.INSTANCE as T;
        }
        if (clazz == TradeService::class.java) {
            return TradeService.INSTANCE as T;
        }
        return PairService.INSTANCE as T;
    }

    /**
     * 获取api service
     */
    internal fun getApiService(symbol: String): Observable<ContractProxyApiService> {
        return getService(PairService::class.java)
                .getPairType(symbol)
                .map(object : Function<ContractType, ContractProxyApiService> {
                    override fun apply(t: ContractType): ContractProxyApiService {
                        return BaClient.instance.initializer!!.getApiService(t);
                    }
                });
    }

    /**
     * 获取socket service
     */
    internal fun getSocketService(symbol: String): Observable<ContractProxySocketService> {
        return getService(PairService::class.java)
                .getPairType(symbol)
                .map(object : Function<ContractType, ContractProxySocketService> {
                    override fun apply(t: ContractType): ContractProxySocketService {
                        return BaClient.instance.initializer!!.getSocketService(t);
                    }
                });
    }

}