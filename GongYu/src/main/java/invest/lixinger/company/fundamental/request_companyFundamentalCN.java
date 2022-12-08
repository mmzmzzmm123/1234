package invest.lixinger.company.fundamental;

import invest.lixinger.company.fsTypeOfCompany.VO.fsTypeOfCompanyCNResult_RootVO;
import invest.lixinger.company.fundamental.VO.companyFundamentalCNResult_DataVO;
import invest.lixinger.company.fundamental.VO.companyFundamentalCNResult_RootVO;
import invest.lixinger.ruoyi.entity.codeAndNameVO;
import invest.lixinger.ruoyi.mapper.codeAndNameMapper;
import invest.lixinger.utils.netRequest;
import mybatisNoSpringUtils.mybatisNoSpringUtils;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static invest.lixinger.company.fs.request_fsCompanyCN.requestFsCompanyCN;
import static invest.lixinger.company.fsTypeOfCompany.request_fsTypeOfCompanyCN.requestFsTypeOfCompanyCN;
import static mybatisNoSpringUtils.mybatisNoSpringUtils.session;

/**
 * 计算pe、pb、ps
 */
public class request_companyFundamentalCN extends mybatisNoSpringUtils {


    public static Map<String, Map<String, String>> requestCompanyFundamentalCN(fsTypeOfCompanyCNResult_RootVO fsTypeOfCompanyCNResult_rootVO, Map<String, Map<String, String>> doubleFsMap) throws IOException, ParseException {
        InputStream inputStream = request_companyFundamentalCN.class.getClassLoader().getResourceAsStream("indexReqParam.yml");
        Map indexReqParam = new Yaml().load(inputStream);
        String fsType = (String) indexReqParam.get("fsType");
        String URL = null;
        if (fsType.equals("bank")) {
            URL = (String) indexReqParam.get("companyFundamentalBankURLCN");
        } else if (fsType.equals("security")) {
            URL = (String) indexReqParam.get("companyFundamentalSecurityURLCN");
        }
        String paramJson = getParam_companyFundamentalCN.getParamCompanyFundamentalCN(fsTypeOfCompanyCNResult_rootVO);
        String resultJson = netRequest.jsonNetPost(URL, paramJson);
//        String resultJson = "{\"code\":1,\"message\":\"success\",\"data\":[{\"date\":\"2022-12-07T00:00:00+08:00\",\"d_pe_ttm\":4.19129145448787,\"pb_wo_gw\":0.37642497905116673,\"ps_ttm\":1.1269338241588467,\"dyr\":0.05585830992601357,\"sp\":7.34,\"mc\":215444954635.04,\"d_pe_ttm_pos10\":0.07178631051752922,\"pb_wo_gw_pos10\":0.02587646076794658,\"ps_ttm_pos10\":0.037562604340567615,\"stockCode\":\"600000\"},{\"date\":\"2022-12-07T00:00:00+08:00\",\"d_pe_ttm\":3.4847350344070853,\"pb_wo_gw\":0.33375500736078273,\"ps_ttm\":0.8821436373170022,\"dyr\":0.061197126146347446,\"sp\":5.34,\"mc\":84985718019.12,\"d_pe_ttm_pos10\":0.06298888431453273,\"pb_wo_gw_pos10\":0.04158089748867847,\"ps_ttm_pos10\":0.07451626183614657,\"stockCode\":\"600015\"},{\"date\":\"2022-12-07T00:00:00+08:00\",\"d_pe_ttm\":4.771487475268475,\"pb_wo_gw\":0.3095146945214663,\"ps_ttm\":1.062006676435935,\"dyr\":0.05983146067415731,\"sp\":3.56,\"mc\":155865409867.12,\"d_pe_ttm_pos10\":0.11650885137916839,\"pb_wo_gw_pos10\":0.019761218608480857,\"ps_ttm_pos10\":0.13956360642239604,\"stockCode\":\"600016\"},{\"date\":\"2022-12-07T00:00:00+08:00\",\"d_pe_ttm\":6.710953166336329,\"pb_wo_gw\":1.1287192898815734,\"ps_ttm\":2.584354427425524,\"dyr\":0.043091732729331825,\"sp\":35.32,\"mc\":890764946627.32,\"d_pe_ttm_pos10\":0.2369727047146402,\"pb_wo_gw_pos10\":0.1674937965260546,\"ps_ttm_pos10\":0.4392059553349876,\"stockCode\":\"600036\"},{\"date\":\"2022-12-07T00:00:00+08:00\",\"d_pe_ttm\":5.655450899023884,\"pb_wo_gw\":0.6910792345755918,\"ps_ttm\":2.16177474668933,\"dyr\":0.03396224087310494,\"sp\":5.3,\"mc\":9866895520,\"d_pe_ttm_pos10\":0.017310252996005325,\"pb_wo_gw_pos10\":0.016644474034620507,\"ps_ttm_pos10\":0.016644474034620507,\"stockCode\":\"600908\"},{\"date\":\"2022-12-07T00:00:00+08:00\",\"d_pe_ttm\":4.5428935129131585,\"pb_wo_gw\":0.6703031306972705,\"ps_ttm\":1.5493306698662839,\"dyr\":0.0539082905743535,\"sp\":7.42,\"mc\":109590814204.2,\"d_pe_ttm_pos10\":0.02992843201040989,\"pb_wo_gw_pos10\":0.247234873129473,\"ps_ttm_pos10\":0.17696811971372803,\"stockCode\":\"600919\"},{\"date\":\"2022-12-07T00:00:00+08:00\",\"d_pe_ttm\":6.922757220573624,\"pb_wo_gw\":0.9937378595671029,\"ps_ttm\":2.3687754872086333,\"dyr\":0.026515094932768844,\"sp\":13.2,\"mc\":78279665706,\"d_pe_ttm_pos10\":0.0396505376344086,\"pb_wo_gw_pos10\":0.385752688172043,\"ps_ttm_pos10\":0.20900537634408603,\"stockCode\":\"600926\"},{\"date\":\"2022-12-07T00:00:00+08:00\",\"d_pe_ttm\":6.184630471062397,\"pb_wo_gw\":0.5486176098353801,\"ps_ttm\":2.3348024505190415,\"dyr\":0.05352112676056338,\"sp\":3.55,\"mc\":15777777779.75,\"d_pe_ttm_pos10\":0.16793893129770993,\"pb_wo_gw_pos10\":0.05343511450381679,\"ps_ttm_pos10\":0.12104689203925845,\"stockCode\":\"600928\"},{\"date\":\"2022-12-07T00:00:00+08:00\",\"d_pe_ttm\":5.821010739964517,\"pb_wo_gw\":0.867241307023198,\"ps_ttm\":2.3089248517918777,\"dyr\":0.04367157545913897,\"sp\":10.23,\"mc\":105776613081.48001,\"d_pe_ttm_pos10\":0.21024370095002065,\"pb_wo_gw_pos10\":0.10326311441553077,\"ps_ttm_pos10\":0.20280875671210244,\"stockCode\":\"601009\"},{\"date\":\"2022-12-07T00:00:00+08:00\",\"d_pe_ttm\":4.1771818319607945,\"pb_wo_gw\":0.3883322373815963,\"ps_ttm\":1.3629017050002703,\"dyr\":0.06936813186813187,\"sp\":3.64,\"mc\":41339480000,\"d_pe_ttm_pos10\":0.10582010582010581,\"pb_wo_gw_pos10\":0.041005291005291,\"ps_ttm_pos10\":0.14153439153439154,\"stockCode\":\"601077\"},{\"date\":\"2022-12-07T00:00:00+08:00\",\"d_pe_ttm\":7.918757423608589,\"pb_wo_gw\":0.958952448286529,\"ps_ttm\":2.364908254689098,\"dyr\":0.02663115845539281,\"sp\":7.51,\"mc\":20583827996.75,\"d_pe_ttm_pos10\":0.04061251664447404,\"pb_wo_gw_pos10\":0.059254327563249,\"ps_ttm_pos10\":0.017310252996005325,\"stockCode\":\"601128\"},{\"date\":\"2022-12-07T00:00:00+08:00\",\"d_pe_ttm\":4.052454039033521,\"pb_wo_gw\":0.5703617913866457,\"ps_ttm\":1.6015015170460833,\"dyr\":0.058873569079901614,\"sp\":17.58,\"mc\":365211210451.74,\"d_pe_ttm_pos10\":0.019389438943894388,\"pb_wo_gw_pos10\":0.018151815181518153,\"ps_ttm_pos10\":0.026402640264026403,\"stockCode\":\"601166\"},{\"date\":\"2022-12-07T00:00:00+08:00\",\"d_pe_ttm\":3.9255851011970053,\"pb_wo_gw\":0.3998664435827399,\"ps_ttm\":1.3517655894122154,\"dyr\":0.07027649769628845,\"sp\":4.34,\"mc\":91760551740.48,\"d_pe_ttm_pos10\":0.039347007115948095,\"pb_wo_gw_pos10\":0.028463792381749686,\"ps_ttm_pos10\":0.03976559229803265,\"stockCode\":\"601169\"},{\"date\":\"2022-12-07T00:00:00+08:00\",\"d_pe_ttm\":6.251074799703429,\"pb_wo_gw\":0.7059872589138875,\"ps_ttm\":2.5455521852230865,\"dyr\":0.043859649122807015,\"sp\":5.7,\"mc\":15043028961.6,\"d_pe_ttm_pos10\":0.08349514563106795,\"pb_wo_gw_pos10\":0.08932038834951456,\"ps_ttm_pos10\":0.08932038834951456,\"stockCode\":\"601187\"},{\"date\":\"2022-12-07T00:00:00+08:00\",\"d_pe_ttm\":3.8306468372712215,\"pb_wo_gw\":0.43721863000497196,\"ps_ttm\":1.5201482728669153,\"dyr\":0.0662251032425585,\"sp\":6.04,\"mc\":85808239760.48,\"d_pe_ttm_pos10\":0.06309362279511534,\"pb_wo_gw_pos10\":0.026458616010854818,\"ps_ttm_pos10\":0.06648575305291723,\"stockCode\":\"601229\"},{\"date\":\"2022-12-07T00:00:00+08:00\",\"d_pe_ttm\":3.992423286914832,\"pb_wo_gw\":0.4614808014012848,\"ps_ttm\":1.3627627806497773,\"dyr\":0.07180555555555555,\"sp\":2.88,\"mc\":1007951137554.24,\"d_pe_ttm_pos10\":0.014820913956360642,\"pb_wo_gw_pos10\":0.009468917249897077,\"ps_ttm_pos10\":0.0345821325648415,\"stockCode\":\"601288\"},{\"date\":\"2022-12-07T00:00:00+08:00\",\"d_pe_ttm\":3.9039360709601914,\"pb_wo_gw\":0.4275696467590578,\"ps_ttm\":1.2620858672887985,\"dyr\":0.07473684210526316,\"sp\":4.75,\"mc\":352747951563.75,\"d_pe_ttm_pos10\":0.058460271716755864,\"pb_wo_gw_pos10\":0.02140798682585426,\"ps_ttm_pos10\":0.03952243721696171,\"stockCode\":\"601328\"},{\"date\":\"2022-12-07T00:00:00+08:00\",\"d_pe_ttm\":4.255745161538682,\"pb_wo_gw\":0.5004002077917376,\"ps_ttm\":1.6230189371089665,\"dyr\":0.06836829836829837,\"sp\":4.29,\"mc\":1528982842911.81,\"d_pe_ttm_pos10\":0.009057225195553726,\"pb_wo_gw_pos10\":0.009057225195553726,\"ps_ttm_pos10\":0.03087690407575134,\"stockCode\":\"601398\"},{\"date\":\"2022-12-07T00:00:00+08:00\",\"d_pe_ttm\":7.269151791186742,\"pb_wo_gw\":0.7056818153922123,\"ps_ttm\":2.8745068215254443,\"dyr\":0.02647058823529412,\"sp\":6.8,\"mc\":10263613449.199999,\"d_pe_ttm_pos10\":0.0906515580736544,\"pb_wo_gw_pos10\":0.10764872521246459,\"ps_ttm_pos10\":0.11614730878186968,\"stockCode\":\"601528\"},{\"date\":\"2022-12-07T00:00:00+08:00\",\"d_pe_ttm\":4.207202315301285,\"pb_wo_gw\":0.5243439187776694,\"ps_ttm\":1.2421105465866507,\"dyr\":0.05043227665706051,\"sp\":6.94,\"mc\":27909583052.760002,\"d_pe_ttm_pos10\":0.046214355948869225,\"pb_wo_gw_pos10\":0.04031465093411996,\"ps_ttm_pos10\":0.046214355948869225,\"stockCode\":\"601577\"},{\"date\":\"2022-12-07T00:00:00+08:00\",\"d_pe_ttm\":4.850351334545433,\"pb_wo_gw\":0.6090830409151785,\"ps_ttm\":1.2214341693518311,\"dyr\":0.055470852017937236,\"sp\":4.46,\"mc\":412032495518.3,\"d_pe_ttm_pos10\":0.05096418732782369,\"pb_wo_gw_pos10\":0.05096418732782369,\"ps_ttm_pos10\":0.05371900826446281,\"stockCode\":\"601658\"},{\"date\":\"2022-12-07T00:00:00+08:00\",\"d_pe_ttm\":6.374965614906405,\"pb_wo_gw\":0.6962573648822925,\"ps_ttm\":1.7237083663667774,\"dyr\":0.04289044289044289,\"sp\":4.29,\"mc\":19651775002.86,\"d_pe_ttm_pos10\":0.09776536312849161,\"pb_wo_gw_pos10\":0.11452513966480447,\"ps_ttm_pos10\":0.11173184357541899,\"stockCode\":\"601665\"},{\"date\":\"2022-12-07T00:00:00+08:00\",\"d_pe_ttm\":3.713700627073105,\"pb_wo_gw\":0.4249821943425377,\"ps_ttm\":1.0859201734435664,\"dyr\":0.06525972588451082,\"sp\":3.08,\"mc\":166418352500.4,\"d_pe_ttm_pos10\":0.041770057899090156,\"pb_wo_gw_pos10\":0.03598014888337469,\"ps_ttm_pos10\":0.04466501240694789,\"stockCode\":\"601818\"},{\"date\":\"2022-12-07T00:00:00+08:00\",\"d_pe_ttm\":5.152064232351566,\"pb_wo_gw\":0.5623107679376854,\"ps_ttm\":2.21206274119684,\"dyr\":0.051194539249146756,\"sp\":5.86,\"mc\":56516444447.700005,\"d_pe_ttm_pos10\":0.15605095541401273,\"pb_wo_gw_pos10\":0.21337579617834396,\"ps_ttm_pos10\":0.21656050955414013,\"stockCode\":\"601825\"},{\"date\":\"2022-12-07T00:00:00+08:00\",\"d_pe_ttm\":5.912647383020587,\"pb_wo_gw\":1.0784308699633196,\"ps_ttm\":2.761228215848298,\"dyr\":0.04120500703776312,\"sp\":14.99,\"mc\":55229170045.63,\"d_pe_ttm_pos10\":0.16921768707482993,\"pb_wo_gw_pos10\":0.58078231292517,\"ps_ttm_pos10\":0.5102040816326531,\"stockCode\":\"601838\"},{\"date\":\"2022-12-07T00:00:00+08:00\",\"d_pe_ttm\":6.389345073981661,\"pb_wo_gw\":0.6055859505462655,\"ps_ttm\":2.1871779509187954,\"dyr\":0.03662985853141896,\"sp\":2.73,\"mc\":9994462749.18,\"d_pe_ttm_pos10\":0.040923399790136414,\"pb_wo_gw_pos10\":0.030430220356768102,\"ps_ttm_pos10\":0.04302203567681007,\"stockCode\":\"601860\"},{\"date\":\"2022-12-07T00:00:00+08:00\",\"d_pe_ttm\":4.687446529434551,\"pb_wo_gw\":0.4682686007150641,\"ps_ttm\":1.022599712339895,\"dyr\":0,\"sp\":2.97,\"mc\":63168029430.66,\"d_pe_ttm_pos10\":0.03260869565217391,\"pb_wo_gw_pos10\":0.03804347826086957,\"ps_ttm_pos10\":0.03804347826086957,\"stockCode\":\"601916\"},{\"date\":\"2022-12-07T00:00:00+08:00\",\"d_pe_ttm\":4.461047315817876,\"pb_wo_gw\":0.5275930822641904,\"ps_ttm\":1.6830383247991763,\"dyr\":0.06511627906976744,\"sp\":5.59,\"mc\":1397561364146.74,\"d_pe_ttm_pos10\":0.01440922190201729,\"pb_wo_gw_pos10\":0.009880609304240428,\"ps_ttm_pos10\":0.03869905310827501,\"stockCode\":\"601939\"},{\"date\":\"2022-12-07T00:00:00+08:00\",\"d_pe_ttm\":5.117106795152015,\"pb_wo_gw\":0.5441494253333297,\"ps_ttm\":1.7516558359202432,\"dyr\":0.05531865180940955,\"sp\":7.05,\"mc\":24495482769.149998,\"d_pe_ttm_pos10\":0.09706546275395034,\"pb_wo_gw_pos10\":0.07900677200902935,\"ps_ttm_pos10\":0.11512415349887133,\"stockCode\":\"601963\"},{\"date\":\"2022-12-07T00:00:00+08:00\",\"d_pe_ttm\":4.090148559062747,\"pb_wo_gw\":0.45987245469407595,\"ps_ttm\":1.497445605313339,\"dyr\":0.07038216560509554,\"sp\":3.14,\"mc\":924377664496.74,\"d_pe_ttm_pos10\":0.03499382461918485,\"pb_wo_gw_pos10\":0.009468917249897077,\"ps_ttm_pos10\":0.03787566899958831,\"stockCode\":\"601988\"},{\"date\":\"2022-12-07T00:00:00+08:00\",\"d_pe_ttm\":3.4442571976046787,\"pb_wo_gw\":0.41007111102680044,\"ps_ttm\":1.2835968953967853,\"dyr\":0.052910052910052914,\"sp\":5.67,\"mc\":20730643090.92,\"d_pe_ttm_pos10\":0.05805609915198956,\"pb_wo_gw_pos10\":0.028049575994781473,\"ps_ttm_pos10\":0.028049575994781473,\"stockCode\":\"601997\"},{\"date\":\"2022-12-07T00:00:00+08:00\",\"d_pe_ttm\":4.025674330403448,\"pb_wo_gw\":0.4550971526613217,\"ps_ttm\":1.1686420226497127,\"dyr\":0.06027943965434965,\"sp\":5.01,\"mc\":245163566721.56998,\"d_pe_ttm_pos10\":0.048207663782447466,\"pb_wo_gw_pos10\":0.0815822002472188,\"ps_ttm_pos10\":0.12031314379892871,\"stockCode\":\"601998\"},{\"date\":\"2022-12-07T00:00:00+08:00\",\"d_pe_ttm\":6.574503020679596,\"pb_wo_gw\":0.6115941853620943,\"ps_ttm\":2.1628886686607953,\"dyr\":0.03312624497901567,\"sp\":4.83,\"mc\":8708843106.81,\"d_pe_ttm_pos10\":0.017064846416382253,\"pb_wo_gw_pos10\":0.017064846416382253,\"ps_ttm_pos10\":0.032081911262798635,\"stockCode\":\"603323\"},{\"date\":\"2022-12-07T00:00:00+08:00\",\"d_pe_ttm\":5.8215011967470796,\"pb_wo_gw\":0.7335681638755059,\"ps_ttm\":1.414112005584125,\"dyr\":0.017338403041825094,\"sp\":13.15,\"mc\":255187824303.7,\"d_pe_ttm_pos10\":0.02554594149155336,\"pb_wo_gw_pos10\":0.02595797280593325,\"ps_ttm_pos10\":0.04449938195302843,\"stockCode\":\"000001\"},{\"date\":\"2022-12-07T00:00:00+08:00\",\"d_pe_ttm\":17.09223535733549,\"pb_wo_gw\":1.0189830804109805,\"ps_ttm\":7.600301001042194,\"dyr\":0.00917431189728977,\"sp\":3.27,\"mc\":12962322006.42,\"d_pe_ttm_pos10\":0.058622165169020114,\"pb_wo_gw_pos10\":0.06846384253316218,\"ps_ttm_pos10\":0.2160890029952931,\"stockCode\":\"000563\"},{\"date\":\"2022-12-07T00:00:00+08:00\",\"d_pe_ttm\":9.261491983437221,\"pb_wo_gw\":1.3880588416311332,\"ps_ttm\":3.544389141698473,\"dyr\":0.015873015873015872,\"sp\":31.5,\"mc\":208013109948,\"d_pe_ttm_pos10\":0.4836844279223461,\"pb_wo_gw_pos10\":0.26724494010739364,\"ps_ttm_pos10\":0.5518380834365965,\"stockCode\":\"002142\"},{\"date\":\"2022-12-07T00:00:00+08:00\",\"d_pe_ttm\":6.2846168830489795,\"pb_wo_gw\":0.6609661253558973,\"ps_ttm\":2.244959293214698,\"dyr\":0.04466417693152695,\"sp\":4.03,\"mc\":8753363434.4,\"d_pe_ttm_pos10\":0.015873015873015872,\"pb_wo_gw_pos10\":0.016534391534391533,\"ps_ttm_pos10\":0.015873015873015872,\"stockCode\":\"002807\"},{\"date\":\"2022-12-07T00:00:00+08:00\",\"d_pe_ttm\":7.046503465640094,\"pb_wo_gw\":0.7861784638936129,\"ps_ttm\":2.1037269257735867,\"dyr\":0.028307282172171728,\"sp\":4.71,\"mc\":10219048212.42,\"d_pe_ttm_pos10\":0.01625441696113074,\"pb_wo_gw_pos10\":0.019081272084805655,\"ps_ttm_pos10\":0.02049469964664311,\"stockCode\":\"002839\"},{\"date\":\"2022-12-07T00:00:00+08:00\",\"d_pe_ttm\":6.298966858540439,\"pb_wo_gw\":0.466875142713373,\"ps_ttm\":1.263812954841666,\"dyr\":0,\"sp\":2.46,\"mc\":20333222493.54,\"d_pe_ttm_pos10\":0.04603330068560235,\"pb_wo_gw_pos10\":0.03232125367286973,\"ps_ttm_pos10\":0.024485798237022526,\"stockCode\":\"002936\"},{\"date\":\"2022-12-07T00:00:00+08:00\",\"d_pe_ttm\":6.767682674798747,\"pb_wo_gw\":0.6889476812035806,\"ps_ttm\":1.647487841668752,\"dyr\":0.045325779036827205,\"sp\":3.53,\"mc\":20545852175.719997,\"d_pe_ttm_pos10\":0.10341151385927505,\"pb_wo_gw_pos10\":0.08208955223880597,\"ps_ttm_pos10\":0.04157782515991471,\"stockCode\":\"002948\"},{\"date\":\"2022-12-07T00:00:00+08:00\",\"d_pe_ttm\":5.346252589886473,\"pb_wo_gw\":0.5397109954107919,\"ps_ttm\":1.6803447604755593,\"dyr\":0.033444796542657515,\"sp\":2.99,\"mc\":16611266574.500002,\"d_pe_ttm_pos10\":0.03333333333333333,\"pb_wo_gw_pos10\":0.03,\"ps_ttm_pos10\":0.07888888888888888,\"stockCode\":\"002958\"},{\"date\":\"2022-12-07T00:00:00+08:00\",\"d_pe_ttm\":7.44775177460525,\"pb_wo_gw\":0.7892361043071163,\"ps_ttm\":2.326029134038663,\"dyr\":0.034121227819998066,\"sp\":7.46,\"mc\":27353795661.86,\"d_pe_ttm_pos10\":0.16379310344827586,\"pb_wo_gw_pos10\":0.39532019704433496,\"ps_ttm_pos10\":0.39285714285714285,\"stockCode\":\"002966\"}]}";
        companyFundamentalCNResult_RootVO resultObj = (companyFundamentalCNResult_RootVO) getResult_companyFundamentalCN.getResultCompanyFundamentalCN(resultJson);
        Map<String, Map<String, String>> dasdf = getdoubleCompanyFundamentalMap(resultObj.getData(), resultObj.getData().get(0).getDate(), doubleFsMap);

        return getdoubleCompanyFundamentalMap(resultObj.getData(), resultObj.getData().get(0).getDate(), doubleFsMap);
    }

    public static Map<String, Map<String, String>> getdoubleCompanyFundamentalMap(List<companyFundamentalCNResult_DataVO> voList, String date, Map<String, Map<String, String>> doubleFsMap) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateFormat = sdf.format(sdf.parse(date));
        Map<String, Map<String, String>> doubleFundMap = new HashMap<>();
        for (int i = 0; i < voList.size(); i++) {
            companyFundamentalCNResult_DataVO vo = voList.get(i);
            Map<String, String> mapTemp = doubleFsMap.get(vo.getStockCode());
            mapTemp.put("pe", String.valueOf(new DecimalFormat("0.0000").format(vo.getD_pe_ttm())));
            mapTemp.put("pb", String.valueOf(new DecimalFormat("0.0000").format(vo.getPb_wo_gw())));
            mapTemp.put("ps", String.valueOf(new DecimalFormat("0.0000").format(vo.getPs_ttm())));
            mapTemp.put("pepos", String.valueOf(new DecimalFormat("0.0000").format(vo.getD_pe_ttm_pos10())));
            mapTemp.put("pbpos", String.valueOf(new DecimalFormat("0.0000").format(vo.getPb_wo_gw_pos10())));
            mapTemp.put("pspos", String.valueOf(new DecimalFormat("0.0000").format(vo.getPs_ttm_pos10())));
            mapTemp.put("mc", String.valueOf(new DecimalFormat("0.00").format(vo.getMc() / 100000000)));
            mapTemp.put("syr", String.valueOf(new DecimalFormat("0.0000").format(vo.getDyr())));
            mapTemp.put("sp", String.valueOf(vo.getSp()));
            mapTemp.put("stockCode", String.valueOf(vo.getStockCode()));
            mapTemp.put("date", dateFormat);
            mapTemp.put("rankfund", "");
            doubleFundMap.put(vo.getStockCode(), mapTemp);
        }
//        System.out.println(doubleMap.get("601997"));
        doubleFundMap = getdoubleCompanyFundamentalMapSort(voList, doubleFundMap);
        return doubleFundMap;

    }

    public static Map<String, Map<String, String>> getdoubleCompanyFundamentalMapSort(List<companyFundamentalCNResult_DataVO> voList, Map<String, Map<String, String>> doubleFundMap) {
        voList.sort(Comparator.comparing(companyFundamentalCNResult_DataVO::getD_pe_ttm));
        for (int i = 0; i < voList.size(); i++) {
            String StockCode = voList.get(i).getStockCode();
            Map<String, String> mapTemp = doubleFundMap.get(StockCode);
            mapTemp.put("rankfund", String.valueOf(i));
            doubleFundMap.put(StockCode, mapTemp);
        }

        voList.sort(Comparator.comparing(companyFundamentalCNResult_DataVO::getPb_wo_gw));

        for (int i = 0; i < voList.size(); i++) {
            String StockCode = voList.get(i).getStockCode();
            Map<String, String> mapTemp = doubleFundMap.get(StockCode);
            mapTemp.put("rankfund", String.valueOf(Integer.parseInt(doubleFundMap.get(voList.get(i).getStockCode()).get("rankfund")) + i));
            doubleFundMap.put(voList.get(i).getStockCode(), mapTemp);
        }

        voList.sort(Comparator.comparing(companyFundamentalCNResult_DataVO::getPs_ttm));
//        voList.forEach(System.out::println);
        for (int i = 0; i < voList.size(); i++) {
            String StockCode = voList.get(i).getStockCode();
            Map<String, String> mapTemp = doubleFundMap.get(StockCode);
            mapTemp.put("rankfund", String.valueOf(Integer.parseInt(doubleFundMap.get(voList.get(i).getStockCode()).get("rankfund")) + i));
            doubleFundMap.put(voList.get(i).getStockCode(), mapTemp);
        }
//        for (int i = 0; i < voList.size(); i++) {
//            System.out.println(doubleFundMap.get(voList.get(i).getStockCode()));
//        }
        return doubleFundMap;
    }

    public static void getAllCompany(List<companyFundamentalCNResult_DataVO> voList) {
        Map<String, Map<String, String>> doublemap = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        voList.sort(Comparator.comparing(companyFundamentalCNResult_DataVO::getD_pe_ttm));
        for (int i = 0; i < voList.size(); i++) {
            map.put(voList.get(i).getStockCode(), i);
        }
        System.out.println("map = " + map);

        voList.sort(Comparator.comparing(companyFundamentalCNResult_DataVO::getPb_wo_gw));

        for (int i = 0; i < voList.size(); i++) {
            map.put(voList.get(i).getStockCode(), (int) map.get(voList.get(i).getStockCode()) + i);
        }
        System.out.println("map = " + map);

        voList.sort(Comparator.comparing(companyFundamentalCNResult_DataVO::getPs_ttm));
        voList.forEach(System.out::println);
        for (int i = 0; i < voList.size(); i++) {
            map.put(voList.get(i).getStockCode(), (int) map.get(voList.get(i).getStockCode()) + i);
        }
        System.out.println("map = " + map);
    }

    @Test
    public void test() throws IOException, ParseException {
        fsTypeOfCompanyCNResult_RootVO fsTypeOfCompanyCNResult_rootVO = requestFsTypeOfCompanyCN();
        codeAndNameMapper codeAndNameMapper = session.getMapper(codeAndNameMapper.class);
        List<codeAndNameVO> codeAndNameList = codeAndNameMapper.findAll();
        Map<String, Map<String, String>> doubleFsMap = requestFsCompanyCN(fsTypeOfCompanyCNResult_rootVO, codeAndNameList);
        requestCompanyFundamentalCN(fsTypeOfCompanyCNResult_rootVO, doubleFsMap);
    }

    public request_companyFundamentalCN() throws FileNotFoundException {
    }
}
