package com.ruoyi.driver.utils;

import com.google.gson.Gson;
import org.apache.http.HttpHost;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;

/**
 * ClassName:Konsts
 * Description:
 */
public class Konsts {
    public static final String TOKEN = "Bearer " + "" +
            "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ7XCJyb2xlTGlzdFwiOltdLFwidXNlcklkXCI6MSxcInVzZXJuYW1lXCI6XCI" +
            "xODIxMTExMTExMVwifSIsImV4cCI6MTU2MTUxNDEwNn0.ckGTDdOCsIkxBSXmWANHWh4DKM3YIq3QDDSjBceVxgDFHuPe" +
            "Njd_o6cRAKgauvI0lS9UNlJjQjbaWdhCI38k4A";

    public static final String SIGNING_KEY = "MyJwtSecret";
    public static final String HEADER_AUTH_TOKEN = "AuthToken";

    public static final Gson GSON = new Gson();
    public static final String GLOBAL_MEDIATYPE = MediaType.APPLICATION_JSON + ";charset=UTF-8";
    public static final Long daySeconds = 24 * 60 * 60L;
    public static final Long dayLast60 = daySeconds - 60;

    public static final String DB_COLLECTORS_KEY = "$collectors";
    public static final String DB_TOKEN_KEY = "$token";
    public static final String DB_FILE_NAME = "db.properties";
    public static final String DB_FILE_TOKEN_NAME = "token.properties";

    public static final String REMOTE_COLLECTOR_URL_NAME = "url";
    public static final String HEADER_COLLECTOR_AUTH_STRING = "Authorization";
    public static final String DEFAULT_COLLECTOR_CODE = "CollectorCode 123456";
    public static final String DEBUG_TOKEN = "2d8a30778afd48bdae4680e619f77fa9";

//    public static HttpHost HTTP_PROXY = new HttpHost("127.0.0.1", 7890, "http");
    public static HttpHost HTTP_PROXY = new HttpHost("127.0.0.1", 58591, "http");
    public static RequestConfig HTTP_CONFIG = RequestConfig.custom().setProxy(HTTP_PROXY).setCookieSpec(CookieSpecs.STANDARD).build();
//    public static RequestConfig HTTP_CONFIG_PROD = RequestConfig.custom().setProxy(HTTP_PROXY).setCookieSpec(CookieSpecs.STANDARD).build();
    public static RequestConfig HTTP_CONFIG_PROD = RequestConfig.custom()
        .setSocketTimeout(10000)
        .setConnectTimeout(10000)
        .setConnectionRequestTimeout(10000)
        .setCookieSpec(CookieSpecs.STANDARD).build();
    //-------------------------------bitmex-------------------------------------//
    public static final String ENDPOINT_BITMEX = "https://www.bitmex.com";
    public static final String BITMEX_USER_WALLET = "/api/v1/user/wallet";
    public static final String BITMEX_USER_MARGIN = "/api/v1/user/margin";
    public static final String BITMEX_POSITION = "/api/v1/position";
    public static final String trade_history = "/api/v1/execution/tradeHistory";//流水
    public static final String user_wallet_summary = "/api/v1/user/walletSummary";
    public static final String user_wallet_history = "/api/v1/user/walletHistory";
    public static final String borders = "/api/v1/order";

    public static class BitMex{
        public static final String KLINE="/api/v1/trade/bucketed";
    }

    //------------------------------binance-------------------------------------------//
    public static class Binance{
//        public static final String ENDPOINT_BINANCE = "https://fapi.binance.com/";//usdt
        public static final String ENDPOINT_BINANCE = "https://dapi.binance.com";//usd
//        public static final String ENDPOINT_BINANCE = "https://api.binance.com";//usd
//        public static final String KLINE="/fapi/v1/klines";//usdt
        public static final String TKPRICE="/dapi/v1/ticker/price";//usd
        public static final String KLINE="/dapi/v1/klines";//usd
        public static final String DUAL="/dapi/v1/positionSide/dual";//usd
        public static final String POST_ORDER="/dapi/v1/order";//usd
        public static final String GET_BALANCE="/dapi/v1/balance";
        public static final String GET_ACCOUNT="/dapi/v1/account";
        public static final String MARGIN="/dapi/v1/marginType";
        public static final String LEVERAGE="/dapi/v1/leverage";
        public static final String ALL_ORDERS="/dapi/v1/allOrders";
        public static final String OPEN_ORDERS="/dapi/v1/openOrders";
        public static final String ORDER="/dapi/v1/order";
        public static final String POSITION_RISK ="/dapi/v1/positionRisk";

    }

    //------------------------------okex-------------------------------------------------//
//    public static final String ENDPOINT_OKEX = "https://www.okx.com";
    public static final String ENDPOINT_OKEX = "https://aws.okx.com";
    public static final String SPOT_ACCOUNTS = "/api/spot/v3/accounts";//帐户
    public static final String spot_orders = "/api/spot/v3/orders";//订单
    public static final String SPOT_LEDGER = "/api/spot/v3/accounts/btc/ledger";//流水
    public static final String FUTURES_ACCOUNTS = "/api/futures/v3/accounts";
    public static final String SWAP_ACCOUNTS = "/api/swap/v3/accounts";
    public static final String MARGIN_ACCOUNTS = "/api/margin/v3/accounts";

    public static final String MARGIN_LEDGER = "/api/margin/v3/accounts/<instrument_id>/ledger";
    public static final String SWAP_LEDGER = "/api/swap/v3/accounts/<instrument_id>/ledger";
    public static final String TICKER = "/api/spot/v3/instruments/<instrument_id>/ticker";
    public static final String futures_orders = "/api/futures/v3/orders/<instrument_id>";

    public static final List<String> SWAP_INSTRUMENTS = Arrays.asList("BTC-USD-SWAP", "LTC-USD-SWAP",
            "ETH-USD-SWAP", "ETC-USD-SWAP", "XRP-USD-SWAP", "EOS-USD-SWAP", "BCH-USD-SWAP", "BSV-USD-SWAP");
    public static final List<String> FUTRUE_CURRENCIES = Arrays.asList("BTC", "EOS", "ETH", "LTC", "ETC", "XRP", "BCH", "BSV");
    public static final List<String> MARIGIN_LIST = Arrays.asList(
            "btc-usdt", "eth-usdt", "ltc-usdt", "etc-usdt", "bch-usdt", "eos-usdt", "xrp-usdt", "trx-usdt",
            "ltc-btc", "eth-btc", "etc-btc", "bch-btc", "eos-btc", "xrp-btc", "iost-btc", "qtum-btc", "trx-btc"
    );
    public static final String TAG_INSTRUMENT_ID = "<instrument_id>";
    public static final String TAG_CURRENCY = "<currency>";
    public static final String TAG_ORDER = "<order_id>";
    public static final String TAG_UNDERLING = "<underlying>";

    public static class PathSpot {
        public static final String OK_TICKERS = "/api/spot/v3/instruments/ticker";
        public static final String BATCH_ORDER = "/api/spot/v3/batch_orders";
        public static final String SINGLE_ORDER = "/api/spot/v3/orders";
        public static final String BATCH_CANCEL = "/api/spot/v3/cancel_batch_orders";
        public static final String WITHDRAWAL_HISTORY = "/api/account/v3/withdrawal/history";
        public static final String PENDING_ORDERS = "/api/spot/v3/orders_pending";
        public static final String INSTRUMENTS = "/api/spot/v3/instruments";
        public static final String CANCEL_ORDER = "/api/spot/v3/cancel_orders/<order_id>";
        public static final String ASSET_PATH = "/api/account/v3/asset-valuation";
        public static final String TRIGGER_PATH = "/api/spot/v3/order_algo";
        public static final String TRIGGER_ORDER = "/api/spot/v3/algo";
        public static final String CANCEL_TRIGGERS = "/api/spot/v3/cancel_batch_algos";
        public static final String KLINE = "/api/spot/v3/instruments/<instrument_id>/candles";
    }

    public static class PathFuture {
        public static final String FUTURE_INSTRUMENTS = "/api/futures/v3/instruments";
        public static final String BATCH_ORDER = "/api/futures/v3/orders";
        public static final String ONE_ORDER = "/api/futures/v3/order";
        public static final String CANCEL_ORDERS = "/api/futures/v3/cancel_batch_orders";
        public static final String OK_TICKER = "/api/futures/v3/instruments/<instrument_id>/ticker";
        public static final String CANCEL_ORDER = "/api/futures/v3/cancel_order/<instrument_id>/<order_id>";
        public static final String SET_MODEL = "/api/futures/v3/accounts/margin_mode";
        public static final String SET_LEVERAGE_MODEL = "/api/futures/v3/accounts/<underlying>/leverage";
        public static final String SINGLE_POSITION = "/api/futures/v3/<instrument_id>/position";
        public static final String CLOSE_POSITION = "/api/futures/v3/close_position";
        public static final String LEVERAGE_PATH = "/api/futures/v3/accounts/<underlying>/leverage";
        public static final String MARK_PRICE_PATH = "/api/futures/v3/instruments/<instrument_id>/mark_price";
        public static final String SINGLE_ACCOUNT = "/api/futures/v3/accounts/<underlying>";
        public static final String TRIGGER_PATH = "/api/futures/v3/order_algo";
        public static final String TRIGGER_CANCLE_PATH = "/api/futures/v3/cancel_algos";
        public static final String CANCELL_ALL = "/api/futures/v3/cancel_all";
        public static final String LIMIT_PRICE_PATH = "/api/futures/v3/instruments/<instrument_id>/price_limit";
        public static final String FUTURES_POSITION = "/api/futures/v3/position";
        public static final String FUTURES_LEDGER = "/api/futures/v3/accounts/<currency>/ledger";
        public static final String TICKERS = "/api/futures/v3/instruments/ticker";
        public static final String KLINE = "/api/futures/v3/instruments/<instrument_id>/candles";
    }

    public static class PathSwap {
        public static final String SINGLE_POSITION = "/api/swap/v3/<instrument_id>/position";
        public static final String GET_SETTING = "/api/swap/v3/accounts/<instrument_id>/settings";
        public static final String GET_TICKER = "/api/swap/v3/instruments/<instrument_id>/ticker";
        public static final String GET_INSTRUMENTS="/api/swap/v3/instruments";
        public static final String GET_SWAP_ACCOUNT = "/api/swap/v3/<instrument_id>/accounts";

        public static final String POST_ORDER = "/api/swap/v3/order";
        public static final String POST_ALGO = "/api/swap/v3/order_algo";
        public static final String POST_CLOSE_POSITION="/api/swap/v3/close_position";
        public static final String GET_ORDER_INFO="/api/swap/v3/orders/<instrument_id>/<order_id>";
        public static final String GET_ALGO_ORDERS="/api/swap/v3/order_algo/<instrument_id>";
        public static final String TRIGGER_ORDER = "/api/swap/v3/order_algo/<instrument_id>";
        public static final String CANCEL_ALGO_ORDER="/api/swap/v3/cancel_algos";


        public static final String KLINE = "/api/swap/v3/instruments/<instrument_id>/candles";
        public static final String BATCH_ORDER = "/api/swap/v3/orders";
        public static final String SINGLE_ORDER = "/api/swap/v3/order";
        public static final String BATCH_CANCEL_ORDER="/api/swap/v3/cancel_batch_orders/<instrument_id>";

    }

    public static class PathV5 {
        public static final String GET_POSITIONS = "/api/v5/account/positions";
        public static final String GET_LEVERAGES = "/api/v5/account/leverage-info";
        public static final String GET_TICKER = "/api/v5/market/tickers";
        public static final String GET_INSTRUMENTS="/api/v5/public/instruments";
        public static final String GET_SWAP_ACCOUNT = "/api/v5/account/balance";

        public static final String POST_ALGO = "/api/v5/trade/order-algo";
        public static final String POST_CLOSE_POSITION="/api/v5/trade/close-position";
        public static final String GET_ORDER_INFO="/api/v5/trade/order";
        public static final String GET_ORDER_PENDING="/api/v5/trade/orders-pending";
        public static final String TRIGGER_ORDER_HIS = "/api/v5/trade/orders-algo-history";
        public static final String GET_TRIGGER_PENDING = "/api/v5/trade/orders-algo-pending";
        public static final String CANCEL_ALGO_ORDER="/api/v5/trade/cancel-algos";


        public static final String KLINE = "/api/v5/market/candles";
        public static final String KLINE_HISTORY = "/api/v5/market/history-candles";
        public static final String BATCH_ORDER = "/api/v5/trade/batch-orders";
        public static final String CRATE_ORDER = "/api/v5/trade/order";
        public static final String BATCH_CANCEL_ORDER="/api/v5/trade/cancel-batch-orders";
        public static final String BATCH_AMEND_ORDER="/api/v5/trade/amend-batch-orders";

        public static final String GET_MAX_SIZE = "/api/v5/account/max-size";

    }

    public static List<String> POST_SIMPLE_URL = Arrays.asList(PathFuture.ONE_ORDER, PathSpot.SINGLE_ORDER, PathFuture.CLOSE_POSITION, PathFuture.CANCELL_ALL);


    public static class PathMargin {

    }

    public static final String INSTRUMENT_BTC_USDT = "BTC-USDT";
    public static final String INSTRUMENT_btcusdt = "btcusdt";
    public static final String INSTRUMENT_BTCUSDT = "BTCUSDT";
    public static final String SIDE_LONG = "long";
    public static final String SIDE_SHORT = "short";


    public static final String LEDGER_TRANSFER = "transfer";
    public static final String LEDGER_FEE = "fee";
    //futrue
    public static final String LEDGER_MATCH = "match";
    public static final String LEDGER_SETTLEMENT = "settlement";
    public static final String LEDGER_LIQUIDATION = "liquidation";
    //swap
    public static final String LEDGER_FUNDING = "funding";
    public static final String LEDGER_MARGIN = "margin";
    //api-margin
    public static final String LEDGER_TRADE = "trade";
    public static final String LEDGER_REBATE = "rebate";
    //---------------------------huobi----------------------------------------------------//
    public static final String ENDPOINT_HUOBI = "https://api.huobi.pro";
    public static final String ENDPOINT_HBDM = "https://api.hbdm.com";
    public static final String ACCOUNTS = "/v1/account/accounts";
    public static final String METHOD_GET = "get";
    public static final String METHOD_POST = "post";
    public static final String TICKERS_HUOBI = "/market/tickers";
    public static final String CONTRACT_ACCOUNT_INFO = "/api/v1/contract_account_info";
    public static final String CONTRACT_ACCOUNT_POSITION = "/api/v1/contract_position_info";
    public static final String CONTRACT_ACCOUNT_SUBLIST = "/api/v1/contract_sub_account_list";
    public static final String _BALANCE = "/balance";

    public static final String PLATFORM_OKEX = "okex";
    public static final String PLATFORM_BILANCE = "binance";
    public static final String PLATFORM_BITMEX = "bitmex";
    public static final String PLATFORM_HUOBI = "huobi";

    public static final String TYPE_SPOT = "api";
    public static final String TYPE_FUTRUE = "futrue";
    public static final String TYPE_OK_SWAP = "swap";
    public static final String TYPE_OK_MARGIN = "margin";

    public static final String BTC = "BTC";
    public static final String USDT = "USDT";
    public static final String BTCHuobi = "btc";

    public static final String PREFIX_OK_FUTRUE = "okexFutrue";
    public static final String PREFIX_OK_SPOT = "okexSpot";
    public static final String PREFIX_OK_SWAP = "okexSwap";
    public static final String PREFIX_OK_MARGIN = "okexMargin";
    public static final String PREFIX_BITMEX_FUTRUE = "bitmexfutrue";
    public static final String PREFIX_HUOBI_SPOT = "huobiSpot";
    public static final String PREFIX_HUOBI_FUTRUE = "huobiFutrue";
    public static final String PREFIX_BINANCE_SPOT = "binaceSpot";
    public static final String PREFIX_ALL_PLATFORM = "all";

    public static final int PREFIX_INIT_PLATFORM_CODE = 0;
    public static final int PREFIX_ALL_PLATFORM_CODE = 1;
    public static final int PREFIX_OK_FUTRUE_CODE = 2;
    public static final int PREFIX_OK_SPOT_CODE = 3;
    public static final int PREFIX_OK_SWAP_CODE = 4;
    public static final int PREFIX_OK_MARGIN_CODE = 5;
    public static final int PREFIX_BITMEX_FUTRUE_CODE = 6;
    public static final int PREFIX_HUOBI_SPOT_CODE = 7;
    public static final int PREFIX_BINANCE_SPOT_CODE = 8;
    public static final int PREFIX_HUOBI_FUTRUE_CODE = 9;


    public static final int BIG_DECIMAL_SCALE = 6;

    public static final String MARGIN_MODEL_CROSSED = "crossed";

    public static final String BASE_BTC = "BTC";
    public static final String BASE_ETH = "ETH";
    public static final String BASE_USDT = "USDT";
    public static final String BASE_EOS = "EOS";

    public static final String DEFAULT_API_KEY = "0ba2cf40-5f43-4b0e-8d08-87484216bbfc";
    public static final String DEFAULT_API_SECRETE = "63AE8190D02CA60F9C5D4CA2F36EF3BD";
    public static final String DEFAULT_API_PASS = "xiadanshouread";
    public static final String OK_TYPE_LIMIT = "limit";
    public static final String OK_TYPE_MARKET = "market";
    public static final String OK_ORDER_TYPE_NOMORE = "0";
    public static final String OK_ORDER_TYPE_MAKER = "1";
    public static final String OK_ORDER_TYPE_FOK = "2";
    public static final String OK_ORDER_TYPE_IOC = "3";
    public static final String OK_SIDE_BUY = "buy";
    public static final String OK_SIDE_SELL = "sell";

    public static void main(String[] args) {
        System.out.println(new Double(Math.ceil(100 / 10d)).intValue());
        System.out.println(new Double(Math.ceil(101 / 10d)).intValue());
    }

    public static final List<Integer> NOT_OVER_STATE = Arrays.asList(0, 1, 3, 4, 6);

    public static final int DEFAULT_REDIS_TTL = 10;
    public static final Double MIN_STEP = 0.003;

    public static final Long FIXED_STEP_NUM = 6l;
    public static final Long FIXED_STEP_MIN_NUM = 3l;

    public static final String OFFICIAL_DOWN_URL = "http://106.14.191.105/app/andriod/ai_app_ch1.apk";
    public static final String OFFICIAL_DOWN_UR_BASE = "http://106.14.191.105/app/andriod/ai_app";

    public static final int FIVE_MINUTES = 5 * 60;


    public static String PUSH_URL = "http://sdk.open.api.igexin.com/apiex.htm";
    public static final String DEFAULT_START_DATE = "2019-01-01T00:00:00.000Z";
    public static final String DEFAULT_INSTRUMENT = "BTC-USD-200925";

}
