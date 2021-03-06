package com.bkt.contract.ba.model.dto

import com.bkt.contract.ba.enums.PositionMarginType
import java.math.BigDecimal

/**
 * @Description: 调整逐仓保证金 返回结果
   * @Author: XGod  xuanyouwu@163.com  17611639080  https://github.com/NBXXF     https://blog.csdn.net/axuanqq
 * @CreateDate: 2020/12/16 16:23
 */
class PositionMarginResultDto : BaResultDto() {
    val amount: BigDecimal? = null;
    val type: PositionMarginType? = null;
    override fun toString(): String {
        return "PositionMarginResultDto(amount=$amount, type=$type)" + super.toString();
    }
}