package com.bkt.contract.ba.model.dto

import com.bkt.contract.ba.common.jsontypeadapter.Number_percent_auto_0_4_DOWN_FormatTypeAdapter
import com.google.gson.*
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.xxf.arch.XXF
import com.xxf.arch.json.JsonUtils
import com.xxf.arch.json.typeadapter.format.formatobject.NumberFormatObject
import com.xxf.arch.json.typeadapter.format.impl.number.Number_UNFormatTypeAdapter
import java.lang.reflect.Type

/**
 * @Description: 指数价 http socket 复用一个
 *  /dapi/v1/premiumIndex 和 /fapi/v1/premiumIndex 一个返回是数组 一个返回是对象
 * @Author: XGod
 * @CreateDate: 2020/12/11 17:16
 */
@JsonAdapter(PremiumIndexPriceDto.PremiumIndexPriceTypeAdapter::class)
open class PremiumIndexPriceDto {

    /**
     * /dapi/v1/premiumIndex 和 /fapi/v1/premiumIndex 一个返回是数组 一个返回是对象
     */
    internal class PremiumIndexPriceTypeAdapter : JsonSerializer<PremiumIndexPriceDto>, JsonDeserializer<PremiumIndexPriceDto> {
        override fun serialize(src: PremiumIndexPriceDto?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
            return context!!.serialize(src);
        }

        override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): PremiumIndexPriceDto {
            if (json!!.isJsonArray) {
                val firstElement = json.asJsonArray.get(0);
                return context!!.deserialize(firstElement, PremiumIndexPriceWithoutJsonAdapterDto::class.java);
            } else {
                return context!!.deserialize(json, PremiumIndexPriceWithoutJsonAdapterDto::class.java);
            }
        }
    }

    /**
     * 避免循环调用
     */
    internal class PremiumIndexPriceWithoutJsonAdapterDto : PremiumIndexPriceDto {


        constructor(symbol: String,
                    markPrice: NumberFormatObject,
                    indexPrice: NumberFormatObject,
                    estimatedSettlePrice: NumberFormatObject,
                    lastFundingRate: NumberFormatObject,
                    nextFundingTime: Long) : super(symbol,
                markPrice,
                indexPrice,
                estimatedSettlePrice,
                lastFundingRate,
                nextFundingTime) {
        }
    }
    /**
     * /fapi/v1/premiumIndex
     *
     * symbol : BTCUSDT
     * markPrice : 11793.63104562
     * indexPrice : 11781.80495970
     * lastFundingRate : 0.00038246
     * nextFundingTime : 1597392000000
     * interestRate : 0.00010000
     * time : 1597370495002
     */

    /**
     * <symbol>@markPrice
     *
     *   {
    "e": "markPriceUpdate",     // 事件类型
    "E": 1562305380000,         // 事件时间
    "s": "BTCUSDT",             // 交易对
    "p": "11794.15000000",      // 标记价格
    "i": "11784.62659091",      // 现货指数价格
    "P": "11784.25641265",      // 预估结算价,仅在结算前最后一小时有参考价值
    "r": "0.00038167",          // 资金费率
    "T": 1562306400000          // 下次资金时间
    }
     */
    /**
     * 交易对
     */
    @SerializedName("symbol", alternate = ["s"])
    val symbol: String;

    /**
     * 标记价格
     */
    @SerializedName("markPrice", alternate = ["p"])
    @JsonAdapter(Number_UNFormatTypeAdapter::class)
    val markPrice: NumberFormatObject

    /**
     * 指数价格,
     * USDT scoket 有,USD Socket没有该字段注意!!!
     * http接口 usdt和usd都有
     */
    @SerializedName("indexPrice", alternate = ["i"])
    @JsonAdapter(Number_UNFormatTypeAdapter::class)
    val indexPrice: NumberFormatObject

    /**
     *  预估结算价,仅在交割开始前最后一小时有意义
     */
    @SerializedName("estimatedSettlePrice", alternate = ["P"])
    @JsonAdapter(Number_UNFormatTypeAdapter::class)
    val estimatedSettlePrice: NumberFormatObject;

    /**
     * 最近更新的资金费率
     */
    @SerializedName("lastFundingRate", alternate = ["r"])
    @JsonAdapter(Number_percent_auto_0_4_DOWN_FormatTypeAdapter::class)
    val lastFundingRate: NumberFormatObject

    /**
     * 下次资金费时间
     */
    @SerializedName("nextFundingTime", alternate = ["t"])
    val nextFundingTime: Long;

    constructor(symbol: String, markPrice: NumberFormatObject, indexPrice: NumberFormatObject, estimatedSettlePrice: NumberFormatObject, lastFundingRate: NumberFormatObject, nextFundingTime: Long) {
        this.symbol = symbol
        this.markPrice = markPrice
        this.indexPrice = indexPrice
        this.estimatedSettlePrice = estimatedSettlePrice
        this.lastFundingRate = lastFundingRate
        this.nextFundingTime = nextFundingTime
    }

    override fun toString(): String {
        return "PremiumIndexPriceDto(symbol='$symbol', markPrice=$markPrice, indexPrice=$indexPrice, estimatedSettlePrice=$estimatedSettlePrice, lastFundingRate=$lastFundingRate, nextFundingTime=$nextFundingTime)"
    }


}