package dam.a51728.generics

import java.util.*

class Cache<K: Any, V: Any>(private val cacheMap: MutableMap<K, V>) {

    fun put(key: K, value: V){
        cacheMap[key] = value
    }

    fun get(key: K): V?{
        return cacheMap[key]
    }

    fun evict(key: K){
        cacheMap.remove(key)
    }

    fun size(): Int{return cacheMap.size}

    fun getOrPut(key: K, default: () -> V): V{
        return cacheMap.getOrPut(key, default)
    }

    fun transform(key: K, action: (V) -> V): Boolean {
        if(!cacheMap.contains(key)) return false
        cacheMap[key] = action.invoke(cacheMap[key]!!)
        return true
    }

    fun snapshot(): Map<K, V>{
        return Collections.unmodifiableMap(cacheMap)
    }

    fun filterByValues(predicate: (V) -> Boolean): Map<K, V>{
        return Collections.unmodifiableMap(cacheMap.filterValues(predicate))
    }
}