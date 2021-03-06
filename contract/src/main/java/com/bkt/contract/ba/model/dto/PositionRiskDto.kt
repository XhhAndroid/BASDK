package com.bkt.contract.ba.model.dto

import com.bkt.contract.ba.common.jsontypeadapter.Number_Abs_UNFormatTypeAdapter
import com.bkt.contract.ba.enums.MarginType
import com.bkt.contract.ba.enums.PositionDirection
import com.bkt.contract.ba.model.PairConfigProviderModel
import com.bkt.contract.ba.service.CommonService
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.annotations.Expose
import com.google.gson.annotations.JsonAdapter
import com.xxf.arch.json.typeadapter.format.formatobject.NumberFormatObject
import com.xxf.arch.json.typeadapter.format.impl.number.Number_UNFormatTypeAdapter
import com.xxf.arch.json.typeadapter.format.impl.number.Number_percent_auto_2_2_DOWN_FormatTypeAdapter
import com.xxf.arch.utils.NumberUtils
import java.lang.reflect.Type
import java.math.BigDecimal

/**
 * @Description: 持仓
 * @Author: XGod  xuanyouwu@163.com  17611639080  https://github.com/NBXXF     https://blog.csdn.net/axuanqq
 * @CreateDate: 2020/12/15 13:36
 */
@JsonAdapter(PositionRiskDto.PositionRiskDtoJsonAdapter::class)
open class PositionRiskDto : PairConfigProviderModel {

    internal class PositionRiskDtoJsonAdapter : JsonDeserializer<PositionRiskDto> {
        override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): PositionRiskDto {
            val deserialize = context!!.deserialize<WithoutJsonAdapterDto>(json, WithoutJsonAdapterDto::class.java);
            deserialize.reset();
            return deserialize;
        }
    }

    internal class WithoutJsonAdapterDto : PositionRiskDto() {
    }

    /**
     *   {
    "entryPrice": "0.00000", // 开仓均价
    "marginType": "isolated", // 逐仓模式或全仓模式
    "isAutoAddMargin": "false",
    "isolatedMargin": "0.00000000", // 逐仓保证金
    "leverage": "10", // 当前杠杆倍数
    "liquidationPrice": "0", // 参考强平价格
    "markPrice": "6679.50671178",   // 当前标记价格
    "maxNotionalValue": "20000000", // 当前杠杆倍数允许的名义价值上限
    "positionAmt": "0.000", // 头寸数量，符号代表多空方向, 正数为多，负数为空
    "symbol": "BTCUSDT", // 交易对
    "unRealizedProfit": "0.00000000", // 持仓未实现盈亏
    "positionSide": "BOTH", // 持仓方向
    }
     */
    /***
     * 开仓均价
     */
    @JsonAdapter(Number_UNFormatTypeAdapter::class)
    var entryPrice: NumberFormatObject? = null

    /**
     * 逐仓模式或全仓模式
     */
    var marginType: MarginType? = null

    /**
     *
     */
    var isAutoAddMargin: Boolean? = null

    /**
     *  逐仓保证金 (这个包含了盈亏)
     */
    @JsonAdapter(Number_UNFormatTypeAdapter::class)
    var isolatedMargin: NumberFormatObject? = null

    /**
     *  逐仓保证金
     */
    @JsonAdapter(Number_UNFormatTypeAdapter::class)
    var isolatedWallet: NumberFormatObject? = null;

    /**
     * 当前杠杆倍数
     */
    var leverage: Int = 0

    /**
     * 参考强平价格
     */
    @JsonAdapter(Number_UNFormatTypeAdapter::class)
    var liquidationPrice: NumberFormatObject? = null

    /**
     * 当前标记价格
     */
    @JsonAdapter(Number_UNFormatTypeAdapter::class)
    var markPrice: NumberFormatObject? = null

    /**
     * 当前杠杆倍数允许的名义价值上限
     */
    @JsonAdapter(Number_UNFormatTypeAdapter::class)
    var maxNotionalValue: NumberFormatObject? = null

    /**
     *  头寸数量，符号代表多空方向, 正数为多，负数为空
     */
    @JsonAdapter(Number_Abs_UNFormatTypeAdapter::class)
    var positionAmt: NumberFormatObject? = null

    /**
     * 交易对
     */
    var symbol: String? = null

    /**
     *  持仓未实现盈亏
     */
    @JsonAdapter(Number_UNFormatTypeAdapter::class)
    var unRealizedProfit: NumberFormatObject? = null

    /**
     * 持仓方向
     */
    var positionSide: PositionDirection? = null


    /**
     * 本地字段,接口没有
     *
     * 收益率
     * unRealizedProfit/isolatedMargin *100
     */
    @Expose(serialize = false, deserialize = false)
    var earningRate: NumberFormatObject? = null


    /**
     * 本地字段,接口没有
     *
     * 保证金率
     * 逐仓仓位维持保证金/（逐仓模式下钱包余额+未实现盈亏）
     *
     * https://docs.qq.com/doc/DV1NzZWVCZXdQbW1Q
     */
    @Expose(serialize = false, deserialize = false)
    var marginRate: NumberFormatObject? = null;


    /**
     * 本地字段,接口没有
     *
     * 维持保证金率
     * 需要调这个接口 获取 /dapi/v1/leverageBracket
     */
    @Expose(serialize = false, deserialize = false)
    var maintenanceMarginRate: NumberFormatObject? = null;


    /**
     * 持仓adl队列 自动加/减仓进度
     * 本地字段 http接口并没有,通过v1/adlQuantile 组装了
     *
     * 如果想及时更新 请订阅
     * BaClient.instance.getService(CommonService::class.java).subAdlQuantileXXX()
     */
    @Expose(serialize = false, deserialize = false)
    var adlQuantile: AdlQuantileDto.AdlQuantileItem? = null;


    /**
     * 仓位价值
     * 本地字段 接口并未返回
     */
    @Expose(serialize = false, deserialize = false)
    var positionValue: NumberFormatObject? = null;


    /**
     * 本地字段
     */
    @Expose(serialize = false, deserialize = false)
    var leverageBracket: LeverageBracketDto.BracketsBean? = null;

    /**
     * 重置  价格变化 其他字段要变
     */
    fun reset() {
        if (this.positionAmt != null && this.markPrice != null) {
            this.positionValue = CommonService.INSTANCE.calculatePositionValue(
                    this.symbol!!,
                    this.positionAmt!!.origin,
                    this.markPrice!!.origin);
        }
        /**
         * 计算仓位保证金
         */
        if (unRealizedProfit != null && isolatedMargin != null) {
            val isolatedWalletDecimal = NumberUtils.subtract(isolatedMargin?.origin!!, unRealizedProfit?.origin!!);
            this.isolatedWallet = NumberFormatObject(isolatedWalletDecimal, Number_UNFormatTypeAdapter().format(isolatedWalletDecimal));
        }

        if (leverageBracket != null) {
            /**
             * 维持保证金率
             */
            this.maintenanceMarginRate = leverageBracket?.maintMarginRatio;

            /**
             * 逐仓仓位维持保证金/（逐仓模式下钱包余额+未实现盈亏）
             */
            val isolatedMainMargin = NumberUtils.subtract(
                    NumberUtils.multiply(
                            NumberUtils.multiply(positionAmt?.origin?.abs(), this.markPrice?.origin),
                            maintenanceMarginRate?.origin),
                    leverageBracket?.cum);
            val marginRateDecimal = NumberUtils.divide(isolatedMainMargin, NumberUtils.add(isolatedWallet?.origin, unRealizedProfit?.origin));
            this.marginRate = NumberFormatObject(marginRateDecimal, Number_percent_auto_2_2_DOWN_FormatTypeAdapter().format(marginRateDecimal));


            /**
             * 计算回报率
             */
            this.earningRate = CommonService.INSTANCE.calculateEarningRate(symbol!!,
                    positionSide!!,
                    entryPrice?.origin!!,
                    markPrice?.origin!!,
                    positionAmt?.origin!!,
                    leverage,
                    unRealizedProfit?.origin!!)
        }
    }

    /**
     * 计算预估强平价
     */
    fun calculatePredictClosePrice(inputMargin: BigDecimal): NumberFormatObject? {
        try {
            return CommonService.INSTANCE.calculatePredictClosePrice(
                    symbol!!,
                    positionSide!!,
                    inputMargin,
                    isolatedWallet?.origin!!,
                    positionAmt?.origin!!,
                    entryPrice?.origin!!,
                    this.maintenanceMarginRate?.origin!!,
                    leverageBracket?.cum!!)
        } catch (e: Throwable) {
            e.printStackTrace();
        }
        return null;
    }

    override fun provideSymbol(): String? {
        return symbol;
    }

    override fun toString(): String {
        return "PositionRiskDto(entryPrice=$entryPrice, marginType=$marginType, isAutoAddMargin=$isAutoAddMargin, isolatedMargin=$isolatedMargin, leverage=$leverage, liquidationPrice=$liquidationPrice, markPrice=$markPrice, maxNotionalValue=$maxNotionalValue, positionAmt=$positionAmt, symbol=$symbol, unRealizedProfit=$unRealizedProfit, positionSide=$positionSide, earningRate=$earningRate, marginRate=$marginRate, maintenanceMarginRate=$maintenanceMarginRate, adlQuantile=$adlQuantile)"
    }


}