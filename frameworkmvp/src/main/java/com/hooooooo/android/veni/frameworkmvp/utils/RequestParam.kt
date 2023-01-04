package com.hooooooo.android.veni.frameworkmvp.utils

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import kotlin.LazyThreadSafetyMode.PUBLICATION

/**
 * Created by heyangpeng on 2022/12/16
 * <p>
 * Describe:
 */
class RequestParam(private val source: Map<String, String>?) {
     val urlParams: ConcurrentMap<String, String> by lazy(PUBLICATION) { ConcurrentHashMap() }
     val param: ConcurrentMap<String, Int> by lazy(PUBLICATION) { ConcurrentHashMap() }
     val fileParams: ConcurrentMap<String, Any> by lazy(PUBLICATION) { ConcurrentHashMap() }

    constructor() : this(null)
    constructor(key: String, value: String) : this(mapOf(pair = Pair(key, value)))

    /**
     * Adds a key/value string pair to the request.
     *
     * @param key   the key name for the new param.
     * @param value the value string for the new param.
     */
    fun put(key: String, value: String) = urlParams.put(key, value)
    fun <T> put(key: String, value: T) = fileParams.put(key, value)
    fun hasParams(): Boolean = urlParams.size > 0 || fileParams.size > 0
}