package com.ruoyi.web.test.controller;

/**
 * @Auther: Wang
 * @Date: 2021/07/25 02:03
 * 功能描述:
 */
public interface Constant {
    String APP_ID="24066444";
    String APP_NAME="藏趣云";
    String APP_KEY="7NpRdcorSxoqxIKAkzLXuOhVr8NQekA9";
    String SECRET_KEY="RfvvGbkN95CuXlaoZVzzsvGiz9Her6qq";
    String SING_key="dBpomXW%duC2L8l6MW3yTpaXhX=-8oj0";
    String APP_PATH="/apps/"+APP_NAME+"/";

//    https://openapi.baidu.com/oauth/2.0/authorize?response_type=code&client_id=7NpRdcorSxoqxIKAkzLXuOhVr8NQekA9&redirect_uri=https://www.baidu.com/&scope=basic,netdisk&display=tv&qrcode=1&force_login=1
    //  https://openapi.baidu.com/oauth/2.0/token?grant_type=authorization_code&code=0a291eb3fd3ed6ccc74cc46bc5540191&client_id=7NpRdcorSxoqxIKAkzLXuOhVr8NQekA9&client_secret=RfvvGbkN95CuXlaoZVzzsvGiz9Her6qq&redirect_uri=oob
    //单位mb
    // 普通用户单个分片大小固定为4MB（文件大小如果小于4MB，无需切片，直接上传即可），单文件总大小上限为4G。
    //普通会员用户单个分片大小上限为16MB，单文件总大小上限为10G。
    //超级会员用户单个分片大小上限为32MB，单文件总大小上限为20G。
    Integer UNIT=4;


    //获取授权码，需要扫码登陆
    String GET_CODE_URL="https://openapi.baidu.com/oauth/2.0/authorize?response_type=code&client_id="+APP_KEY+"&redirect_uri=oob&scope=basic,netdisk&display=tv&qrcode=1&force_login=1";

    //获取到的授权码
    String CODE="0a291eb3fd3ed6ccc74cc46bc5540191";

    //根据授权码换取token
    String GET_TOKEN_BY_CODE="https://openapi.baidu.com/oauth/2.0/token?grant_type=authorization_code&code="+CODE+"&client_id="+APP_KEY+"&client_secret="+SECRET_KEY+"&redirect_uri=oob";

    //获取到的TOKEN
    String RTOKEN="122.fec5f9d6dd1644c2c57c89cc510f7ec8.YBMpVZwjo9y5kSMFnVmSMJL9dj25T5X02gjLwV8.1J2sEw";
    String ATOKEN="121.d8ab3687bd67e9b245b6a10f7af7afc3.YCQGBie5JO6U7nCl-ZLVC3pX-sTUFIgnV6hH1Rw.3zI4rQ";


    //操作文件 copy, mover, rename, delete
    String FILE_MANAGER_URL=" https://pan.baidu.com/rest/2.0/xpan/file";

    //预上传
    String GET_READY_FILE_URL="https://pan.baidu.com/rest/2.0/xpan/file";

    //分片上传
    String SLICING_UPLOAD_FILE_URL="https://d.pcs.baidu.com/rest/2.0/pcs/superfile2";

    //下载文件
    String DOWN_LOUE_URL="https://pan.baidu.com/rest/2.0/xpan/multimedia";

    //文件搜索
    String FILE_SEARCH="https://pan.baidu.com/rest/2.0/xpan/file?method=search";


}
