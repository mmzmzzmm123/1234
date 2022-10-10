/**
 * keepAlive 重写keepalive处理缓存问题
 * Copyright (c) 2022 小红
 */
/**
 * 验证数据类型是否是正则
 * @param v
 * @returns {boolean}
 */
function isRegExp (v) {
  return Object.prototype.toString.call(v) === '[object RegExp]'
}

/**
 * 移除数组中指定的项
 * @param arr
 * @param item
 * @returns {*|{}|number|Array|*[]|[]|T[]}
 */
export function remove (arr, item) {
  if (arr.length) {
    const index = arr.indexOf(item)
    if (index > -1) {
      return arr.splice(index, 1)
    }
  }
}

/**
 * 判断数据是否定义了
 * @param v
 * @returns {boolean}
 */
function isDef (v) {
  return v !== undefined && v !== null
}

function isAsyncPlaceholder (node) {
  return node.isComment && node.asyncFactory
}

/**
 * 获取KeepAlive下的第一个子组件
 * @param children
 * @returns {*}
 */
function getFirstComponentChild (children) {
  if (Array.isArray(children)) {
    for (let i = 0; i < children.length; i++) {
      const c = children[i]
      if (isDef(c) && (isDef(c.componentOptions) || isAsyncPlaceholder(c))) {
        return c
      }
    }
  }
}

/**
 * 匹配缓存的页面组件
 * @param pattern
 * @param name
 * @returns {boolean|*}
 */
function matches (pattern, name) {
  if (Array.isArray(pattern)) {
    return pattern.indexOf(name) > -1
  } else if (typeof pattern === 'string') {
    return pattern.split(',').indexOf(name) > -1
  } else if (isRegExp(pattern)) {
    return pattern.test(name)
  }
  /* istanbul ignore next */
  return false
}

/**
 * 原先对于没有设置组件name值的，设置为路由的name
 * 现在我们直接取fullPath为name
 * @param {*} opts
 */
function getComponentName (opts) {
  // return (opts && opts.Ctor.options.name) || this.$route.name
  return this.$route.fullPath
}

/**
 * 删除缓存
 * @param keepAliveInstance
 * @param filter
 */
function pruneCache (keepAliveInstance, filter) {
  const { cache, keys, _vnode } = keepAliveInstance
  Object.keys(cache).forEach(key => {
    const cachedNode = cache[key]
    if (cachedNode) {
      if (key && !filter(key)) {
        pruneCacheEntry(cache, key, keys, _vnode)
      }
    }
  })
}

/**
 * 删除缓存条目
 * @param cache
 * @param key
 * @param keys
 * @param current
 */
function pruneCacheEntry (cache, key, keys, current) {
  const cached = cache[key]
  if (cached && (!current || cached.tag !== current.tag)) {
    cached.componentInstance.$destroy()
  }
  cache[key] = null
  remove(keys, key)
}

const patternTypes = [String, RegExp, Array]

export default {
  name: 'KeepAlive',
  // abstract: true,
  props: {
    include: patternTypes,
    exclude: patternTypes,
    max: [String, Number]
  },

  created () {
    // Object.create(null)创建一个非常干净且高度可定制的对象
    // 新创建的对象除了自身属性外，原型链上没有任何属性，也就是说没有继承Object的任何东西
    this.cache = Object.create(null)
    this.keys = []
  },

  mounted () {
    this.$watch('include', val => {
      pruneCache(this, name => matches(val, name))
    })
    this.$watch('exclude', val => {
      pruneCache(this, name => !matches(val, name))
    })
  },

  destroyed () {
    Object.keys(this.cache).forEach(key => {
      pruneCacheEntry(this.cache, key, this.keys)
    })
  },

  render () {
    const slot = this.$slots.default
    const vnode = getFirstComponentChild(slot)
    const componentOptions = vnode && vnode.componentOptions
    if (componentOptions) {
      // 获取组件的名称，此处修改后取fullPath作为name
      const key = getComponentName.call(this, componentOptions)

      const { include, exclude } = this
      // 没有缓存的直接返回vnode
      if (
        // not included
        (include && (!key || !matches(include, key))) ||
        // excluded
        (exclude && key && matches(exclude, key))
      ) {
        return vnode
      }

      const { cache, keys } = this
      if (cache[key]) {
        // 取缓存中的实例作为vnode的实例
        vnode.componentInstance = cache[key].componentInstance
        // 将当前缓存的key设置为最新的，便于后面缓存的数量超了以后删除最老的
        remove(keys, key)
        keys.push(key)
      } else {
        cache[key] = vnode
        keys.push(key)
        // 移除最老的缓存
        if (this.max && keys.length > parseInt(this.max)) {
          pruneCacheEntry(cache, keys[0], keys, this._vnode)
        }
      }
      vnode.data.keepAlive = true
    }
    return vnode || (slot && slot[0])
  }
}
