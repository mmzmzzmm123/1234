import request from "../request";

// 基础url
let bashRequestUrl = "/api/staffWallet";

let selectStaffWalletUrl = "/selectStaffWallet";
let pageWalletRecordUrl = "/pageWalletRecord";
let pageSettlementRecordUrl = "/pageSettlementRecord";

/**
 * 查询员工钱包数据
 */
let selectStaffWallet = function (onStart, onSuccess, onFailed, onWarn) {
  request.get(bashRequestUrl + selectStaffWalletUrl, null, onStart, onSuccess, onFailed, onWarn, onFailed);
}

/**
 * 分页查询员工钱包记录数据
 */
let pageWalletRecord = function(params, onStart, onSuccess, onFailed){
  request.get(bashRequestUrl + pageWalletRecordUrl, params, onStart, onSuccess, onFailed, onFailed, onFailed);
}


/**
 * 分页查询员工结算记录
 */
let pageSettlementRecord = function (onStart, onSuccess, onFailed, onWarn) {
  request.get(bashRequestUrl + pageSettlementRecordUrl, null, onStart, onSuccess, onFailed, onWarn, onFailed);
}

module.exports = {
  selectStaffWallet: selectStaffWallet,
  pageWalletRecord: pageWalletRecord,
  pageSettlementRecord: pageSettlementRecord
}