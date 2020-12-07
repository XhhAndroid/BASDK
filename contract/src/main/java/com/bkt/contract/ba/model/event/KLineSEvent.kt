package com.bkt.contract.ba.model.event

import com.bkt.contract.ba.model.dto.KLineEventDto

/**
 * @Description: k线响应模型
 * @Author: XGod
 * @CreateDate: 2020/12/3 16:34
 */
class KLineSEvent : BaseSEvent() {
    val s: String? = null;
    val k: KLineEventDto? = null;
}