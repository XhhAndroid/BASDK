package com.bkt.contract.ba.model.event

import com.bkt.contract.ba.model.dto.KLineEventDto

/**
 * @Description: k线响应模型
   * @Author: XGod  xuanyouwu@163.com  17611639080  https://github.com/NBXXF     https://blog.csdn.net/axuanqq
 * @CreateDate: 2020/12/3 16:34
 */
class KLineSEvent {
    val s: String;
    val k: KLineEventDto;

    constructor(s: String, k: KLineEventDto) : super() {
        this.s = s
        this.k = k
    }
}