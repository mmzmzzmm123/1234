import request from "../request";

// 基础url
let bashRequestUrl = "/api/distribution";

let pageDistributionRelationUrl = "/pageDistributionRelation";
let pageDistributionCommissionRecordUrl = "/pageDistributionCommissionRecord";

/**
 * 分销关系分页
 */
let pageDistributionRelation = function (params, onStart, onSuccess, onFailed, onWarn) {
  request.get(bashRequestUrl + pageDistributionRelationUrl, params, onStart, onSuccess, onFailed, onWarn, onFailed);
}

/**
 * 分销佣金记录分页
 */
let pageDistributionCommissionRecord = function(params, onStart, onSuccess, onFailed, onWarn){
  request.get(bashRequestUrl + pageDistributionCommissionRecordUrl, params, onStart, onSuccess, onFailed, onWarn, onFailed);
}

module.exports = {
  pageDistributionRelation: pageDistributionRelation,
  pageDistributionCommissionRecord: pageDistributionCommissionRecord
}