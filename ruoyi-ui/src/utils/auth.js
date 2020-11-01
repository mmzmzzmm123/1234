import Cookies from 'js-cookie'

const TokenKey = 'Admin-Token'

export function getToken() {
  return Cookies.get(TokenKey)
}

/**
 * 设置30天token才会消失
 * @param token
 * @returns {*}
 */
export function setToken(token) {
  return Cookies.set(TokenKey, token, { expires: 30 })
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}
