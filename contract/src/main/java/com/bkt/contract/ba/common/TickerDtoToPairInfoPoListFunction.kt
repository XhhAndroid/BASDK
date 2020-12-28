package com.bkt.contract.ba.common

import com.bkt.contract.ba.enums.ContractType
import com.bkt.contract.ba.model.dto.TickerEventDto
import com.bkt.contract.ba.model.po.PairInfoPo
import com.bkt.contract.ba.service.inner.PairDbService
import io.reactivex.functions.Function

/**
 * @Description: ticker dto转pairinfo po
 * @Author: XGod  xuanyouwu@163.com  17611639080  https://github.com/NBXXF     https://blog.csdn.net/axuanqq
 * @CreateDate: 2020/12/10 10:01
 */
internal class TickerDtoToPairInfoPoListFunction : Function<List<TickerEventDto>, List<PairInfoPo>> {
    override fun apply(t: List<TickerEventDto>): List<PairInfoPo> {
        return PairDbService.getPairConfig()
                .map { pairCacheMap ->
                    val list: MutableList<PairInfoPo> = mutableListOf()

                    for (item: TickerEventDto in t) {
                        val pairInfoPo = PairInfoPo(item.symbol);
                        pairInfoPo.ticker = item

                        val cacheConfig = pairCacheMap[pairInfoPo.symbol]
                        pairInfoPo.contractType = cacheConfig?.contractClassifyType?.value ?:ContractType.USDT.value

                        list.add(pairInfoPo)
                    }
                    list;
                }.blockingFirst()
    }
}