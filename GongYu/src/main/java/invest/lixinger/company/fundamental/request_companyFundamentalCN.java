package invest.lixinger.company.fundamental;

import invest.lixinger.company.fundamental.VO.companyFundamentalCNResult_DataVO;
import invest.lixinger.company.fundamental.VO.companyFundamentalCNResult_RootVO;
import javassist.Loader;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class request_companyFundamentalCN {
    public static void requestCompanyFundamentalCN() throws IOException, ParseException {
        InputStream inputStream = request_companyFundamentalCN.class.getClassLoader().getResourceAsStream("indexReqParam.yml");
        Map indexReqParam = new Yaml().load(inputStream);
        String companyFundamentalBankURLCN = (String) indexReqParam.get("companyFundamentalBankURLCN");
//        String paramJson = getParam_companyFundamentalCN.getParamCompanyFundamentalCN();
        String paramJson = "{\"date\":\"2022-12-05\",\"metricsList\":[\"d_pe_ttm\",\"d_pe_ttm_pos10\",\"pb_wo_gw\",\"pb_wo_gw_pos10\",\"ps_ttm\",\"ps_ttm_pos10\",\"mc\",\"sp\",\"dyr\"],\"stockCodes\":[\"600000\",\"600015\",\"600016\",\"600036\",\"600908\",\"600919\",\"600926\",\"600928\",\"601009\",\"601077\",\"601128\",\"601166\",\"601169\",\"601187\",\"601229\",\"601288\",\"601328\",\"601398\",\"601528\",\"601577\",\"601658\",\"601665\",\"601818\",\"601825\",\"601838\",\"601860\",\"601916\",\"601939\",\"601963\",\"601988\",\"601997\",\"601998\",\"603323\",\"000001\",\"000563\",\"002142\",\"002807\",\"002839\",\"002936\",\"002948\",\"002958\",\"002966\"],\"token\":\"d58c3650-20f9-4387-8515-d595031c23a4\"}";
//        String resultJson = netRequest.jsonNetPost(companyFundamentalBankURLCN, paramJson);
        String resultJson = "{\"code\":1,\"message\":\"success\",\"data\":[{\"date\":\"2022-12-05T00:00:00+08:00\",\"d_pe_ttm\":4.2027118671703985,\"pb_wo_gw\":0.3774506601929955,\"ps_ttm\":1.1300044885298517,\"dyr\":0.05570652104034506,\"sp\":7.36,\"mc\":216031998108.16,\"d_pe_ttm_pos10\":0.07470784641068448,\"pb_wo_gw_pos10\":0.027545909849749584,\"ps_ttm_pos10\":0.037562604340567615,\"stockCode\":\"600000\"},{\"date\":\"2022-12-05T00:00:00+08:00\",\"d_pe_ttm\":3.471683592330655,\"pb_wo_gw\":0.33250498860662253,\"ps_ttm\":0.8788397285630061,\"dyr\":0.061427190530356265,\"sp\":5.32,\"mc\":84667419449.76001,\"d_pe_ttm_pos10\":0.061342116097159324,\"pb_wo_gw_pos10\":0.03664059283655825,\"ps_ttm_pos10\":0.07204610951008646,\"stockCode\":\"600015\"},{\"date\":\"2022-12-05T00:00:00+08:00\",\"d_pe_ttm\":4.758084420562665,\"pb_wo_gw\":0.3086452712222487,\"ps_ttm\":1.0590235116144857,\"dyr\":0.060000000000000005,\"sp\":3.55,\"mc\":155427585682.1,\"d_pe_ttm_pos10\":0.10868670234664471,\"pb_wo_gw_pos10\":0.018526142445450804,\"ps_ttm_pos10\":0.13874022231370933,\"stockCode\":\"600016\"},{\"date\":\"2022-12-05T00:00:00+08:00\",\"d_pe_ttm\":6.859156548831865,\"pb_wo_gw\":1.1536457068155381,\"ps_ttm\":2.641426807193132,\"dyr\":0.0421606648199446,\"sp\":36.1,\"mc\":910436426196.1,\"d_pe_ttm_pos10\":0.25144747725392885,\"pb_wo_gw_pos10\":0.20347394540942929,\"ps_ttm_pos10\":0.45822994210090984,\"stockCode\":\"600036\"},{\"date\":\"2022-12-05T00:00:00+08:00\",\"d_pe_ttm\":5.687462885244774,\"pb_wo_gw\":0.6949910038279065,\"ps_ttm\":2.1740112075196465,\"dyr\":0.03377108379501992,\"sp\":5.33,\"mc\":9922745872,\"d_pe_ttm_pos10\":0.017333333333333333,\"pb_wo_gw_pos10\":0.016666666666666666,\"ps_ttm_pos10\":0.016666666666666666,\"stockCode\":\"600908\"},{\"date\":\"2022-12-05T00:00:00+08:00\",\"d_pe_ttm\":4.591873496879877,\"pb_wo_gw\":0.6775301186293166,\"ps_ttm\":1.5660350436653812,\"dyr\":0.053333268808227065,\"sp\":7.5,\"mc\":110772386325,\"d_pe_ttm_pos10\":0.04495114006514658,\"pb_wo_gw_pos10\":0.2671009771986971,\"ps_ttm_pos10\":0.19804560260586318,\"stockCode\":\"600919\"},{\"date\":\"2022-12-05T00:00:00+08:00\",\"d_pe_ttm\":7.043381020629073,\"pb_wo_gw\":1.011052988938348,\"ps_ttm\":2.4100496055463596,\"dyr\":0.026061001720964167,\"sp\":13.43,\"mc\":79643629578.15,\"d_pe_ttm_pos10\":0.051816958277254375,\"pb_wo_gw_pos10\":0.40713324360699865,\"ps_ttm_pos10\":0.23822341857335128,\"stockCode\":\"600926\"},{\"date\":\"2022-12-05T00:00:00+08:00\",\"d_pe_ttm\":6.2020519653470805,\"pb_wo_gw\":0.5501630115532262,\"ps_ttm\":2.3413793588303626,\"dyr\":0.053370786516853924,\"sp\":3.56,\"mc\":15822222224.2,\"d_pe_ttm_pos10\":0.17158469945355193,\"pb_wo_gw_pos10\":0.0546448087431694,\"ps_ttm_pos10\":0.12459016393442623,\"stockCode\":\"600928\"},{\"date\":\"2022-12-05T00:00:00+08:00\",\"d_pe_ttm\":5.88929239087319,\"pb_wo_gw\":0.8774142255806547,\"ps_ttm\":2.3360090142762395,\"dyr\":0.043165238352366346,\"sp\":10.35,\"mc\":107017394466.59999,\"d_pe_ttm_pos10\":0.23048327137546468,\"pb_wo_gw_pos10\":0.12061131763733994,\"ps_ttm_pos10\":0.2395704254440314,\"stockCode\":\"601009\"},{\"date\":\"2022-12-05T00:00:00+08:00\",\"d_pe_ttm\":4.188657606224423,\"pb_wo_gw\":0.3893990841875897,\"ps_ttm\":1.3666459404535678,\"dyr\":0.06917808219178082,\"sp\":3.65,\"mc\":41453050000,\"d_pe_ttm_pos10\":0.10742705570291777,\"pb_wo_gw_pos10\":0.04376657824933687,\"ps_ttm_pos10\":0.1419098143236074,\"stockCode\":\"601077\"},{\"date\":\"2022-12-05T00:00:00+08:00\",\"d_pe_ttm\":8.034744549653455,\"pb_wo_gw\":0.972998356317357,\"ps_ttm\":2.3995473902437987,\"dyr\":0.026246719160104987,\"sp\":7.62,\"mc\":20885322148.5,\"d_pe_ttm_pos10\":0.042666666666666665,\"pb_wo_gw_pos10\":0.084,\"ps_ttm_pos10\":0.024,\"stockCode\":\"601128\"},{\"date\":\"2022-12-05T00:00:00+08:00\",\"d_pe_ttm\":4.128523995397632,\"pb_wo_gw\":0.58106824139561,\"ps_ttm\":1.6315638322124775,\"dyr\":0.057788796450288685,\"sp\":17.91,\"mc\":372066710989.23,\"d_pe_ttm_pos10\":0.02764026402640264,\"pb_wo_gw_pos10\":0.02392739273927393,\"ps_ttm_pos10\":0.03712871287128713,\"stockCode\":\"601166\"},{\"date\":\"2022-12-05T00:00:00+08:00\",\"d_pe_ttm\":3.9255851011970053,\"pb_wo_gw\":0.3998664435827399,\"ps_ttm\":1.3517655894122154,\"dyr\":0.07027649769628845,\"sp\":4.34,\"mc\":91760551740.48,\"d_pe_ttm_pos10\":0.039347007115948095,\"pb_wo_gw_pos10\":0.028463792381749686,\"ps_ttm_pos10\":0.03976559229803265,\"stockCode\":\"601169\"},{\"date\":\"2022-12-05T00:00:00+08:00\",\"d_pe_ttm\":6.316875587068727,\"pb_wo_gw\":0.7134187037445598,\"ps_ttm\":2.5723474713833294,\"dyr\":0.04340277777777778,\"sp\":5.76,\"mc\":15201376634.88,\"d_pe_ttm_pos10\":0.08576998050682261,\"pb_wo_gw_pos10\":0.0935672514619883,\"ps_ttm_pos10\":0.0935672514619883,\"stockCode\":\"601187\"},{\"date\":\"2022-12-05T00:00:00+08:00\",\"d_pe_ttm\":3.8243047067459384,\"pb_wo_gw\":0.43649475810099025,\"ps_ttm\":1.5176314710906458,\"dyr\":0.06633492928442011,\"sp\":6.03,\"mc\":85666173138.36,\"d_pe_ttm_pos10\":0.06046195652173913,\"pb_wo_gw_pos10\":0.025135869565217392,\"ps_ttm_pos10\":0.06521739130434782,\"stockCode\":\"601229\"},{\"date\":\"2022-12-05T00:00:00+08:00\",\"d_pe_ttm\":4.006285867772176,\"pb_wo_gw\":0.4630831652950393,\"ps_ttm\":1.367494595860367,\"dyr\":0.07155709342560553,\"sp\":2.89,\"mc\":1011450967892.9701,\"d_pe_ttm_pos10\":0.018114450391107453,\"pb_wo_gw_pos10\":0.01029230135858378,\"ps_ttm_pos10\":0.037463976945244955,\"stockCode\":\"601288\"},{\"date\":\"2022-12-05T00:00:00+08:00\",\"d_pe_ttm\":3.9039360709601914,\"pb_wo_gw\":0.4275696467590578,\"ps_ttm\":1.2620858672887985,\"dyr\":0.07473684210526316,\"sp\":4.75,\"mc\":352747951563.75,\"d_pe_ttm_pos10\":0.058460271716755864,\"pb_wo_gw_pos10\":0.02140798682585426,\"ps_ttm_pos10\":0.03952243721696171,\"stockCode\":\"601328\"},{\"date\":\"2022-12-05T00:00:00+08:00\",\"d_pe_ttm\":4.265665313430381,\"pb_wo_gw\":0.5015666418425342,\"ps_ttm\":1.6268021980346283,\"dyr\":0.0682093023255814,\"sp\":4.3,\"mc\":1532546905482.7,\"d_pe_ttm_pos10\":0.009057225195553726,\"pb_wo_gw_pos10\":0.009057225195553726,\"ps_ttm_pos10\":0.033347056401811445,\"stockCode\":\"601398\"},{\"date\":\"2022-12-05T00:00:00+08:00\",\"d_pe_ttm\":7.3974309404429786,\"pb_wo_gw\":0.7181350238991338,\"ps_ttm\":2.9252334124935406,\"dyr\":0.026011560693641623,\"sp\":6.92,\"mc\":10444736039.48,\"d_pe_ttm_pos10\":0.11965811965811966,\"pb_wo_gw_pos10\":0.13105413105413105,\"ps_ttm_pos10\":0.14245014245014245,\"stockCode\":\"601528\"},{\"date\":\"2022-12-05T00:00:00+08:00\",\"d_pe_ttm\":4.249638073524785,\"pb_wo_gw\":0.529632690292718,\"ps_ttm\":1.2546390391314728,\"dyr\":0.0499286733238231,\"sp\":7.01,\"mc\":28191091815.54,\"d_pe_ttm_pos10\":0.0561576354679803,\"pb_wo_gw_pos10\":0.05123152709359606,\"ps_ttm_pos10\":0.0561576354679803,\"stockCode\":\"601577\"},{\"date\":\"2022-12-05T00:00:00+08:00\",\"d_pe_ttm\":4.937353152205441,\"pb_wo_gw\":0.6200082972544643,\"ps_ttm\":1.2433433024343754,\"dyr\":0.05449339207048459,\"sp\":4.54,\"mc\":419423212926.7,\"d_pe_ttm_pos10\":0.055248618784530384,\"pb_wo_gw_pos10\":0.055248618784530384,\"ps_ttm_pos10\":0.07458563535911603,\"stockCode\":\"601658\"},{\"date\":\"2022-12-05T00:00:00+08:00\",\"d_pe_ttm\":6.419545794031625,\"pb_wo_gw\":0.701126297503847,\"ps_ttm\":1.735762271026685,\"dyr\":0.04259259259259259,\"sp\":4.32,\"mc\":19789200002.88,\"d_pe_ttm_pos10\":0.10674157303370786,\"pb_wo_gw_pos10\":0.12078651685393259,\"ps_ttm_pos10\":0.12078651685393259,\"stockCode\":\"601665\"},{\"date\":\"2022-12-05T00:00:00+08:00\",\"d_pe_ttm\":3.6413558096625906,\"pb_wo_gw\":0.416703320426774,\"ps_ttm\":1.06476588435051,\"dyr\":0.06655627672989844,\"sp\":3.02,\"mc\":163176436542.6,\"d_pe_ttm_pos10\":0.03887510339123242,\"pb_wo_gw_pos10\":0.03225806451612903,\"ps_ttm_pos10\":0.043837882547559964,\"stockCode\":\"601818\"},{\"date\":\"2022-12-05T00:00:00+08:00\",\"d_pe_ttm\":5.169648069322049,\"pb_wo_gw\":0.5642299173163122,\"ps_ttm\":2.2196124433852247,\"dyr\":0.05102040816326531,\"sp\":5.88,\"mc\":56709333336.6,\"d_pe_ttm_pos10\":0.15705128205128205,\"pb_wo_gw_pos10\":0.21474358974358973,\"ps_ttm_pos10\":0.21794871794871795,\"stockCode\":\"601825\"},{\"date\":\"2022-12-05T00:00:00+08:00\",\"d_pe_ttm\":6.003368456942851,\"pb_wo_gw\":1.0949778412836375,\"ps_ttm\":2.8035952932095465,\"dyr\":0.04058232953325027,\"sp\":15.22,\"mc\":56076582261.14,\"d_pe_ttm_pos10\":0.20357751277683134,\"pb_wo_gw_pos10\":0.6124361158432708,\"ps_ttm_pos10\":0.5477001703577513,\"stockCode\":\"601838\"},{\"date\":\"2022-12-05T00:00:00+08:00\",\"d_pe_ttm\":6.43615346280204,\"pb_wo_gw\":0.6100224776564945,\"ps_ttm\":2.203201232610508,\"dyr\":0.03636345956028137,\"sp\":2.75,\"mc\":10067682256.5,\"d_pe_ttm_pos10\":0.04416403785488959,\"pb_wo_gw_pos10\":0.031545741324921134,\"ps_ttm_pos10\":0.050473186119873815,\"stockCode\":\"601860\"},{\"date\":\"2022-12-05T00:00:00+08:00\",\"d_pe_ttm\":4.734794474176313,\"pb_wo_gw\":0.4729985865808728,\"ps_ttm\":1.0329290023635305,\"dyr\":0,\"sp\":3,\"mc\":63806090334,\"d_pe_ttm_pos10\":0.035422343324250684,\"pb_wo_gw_pos10\":0.03814713896457766,\"ps_ttm_pos10\":0.039509536784741145,\"stockCode\":\"601916\"},{\"date\":\"2022-12-05T00:00:00+08:00\",\"d_pe_ttm\":4.484988535759654,\"pb_wo_gw\":0.5304245299328713,\"ps_ttm\":1.6920707308356657,\"dyr\":0.06476868327402134,\"sp\":5.62,\"mc\":1405061693471.32,\"d_pe_ttm_pos10\":0.02428983120625772,\"pb_wo_gw_pos10\":0.011527377521613832,\"ps_ttm_pos10\":0.03993412927130506,\"stockCode\":\"601939\"},{\"date\":\"2022-12-05T00:00:00+08:00\",\"d_pe_ttm\":5.189689870260555,\"pb_wo_gw\":0.5518678568983414,\"ps_ttm\":1.7765020179900342,\"dyr\":0.05454496437151571,\"sp\":7.15,\"mc\":24842936425.45,\"d_pe_ttm_pos10\":0.11337868480725624,\"pb_wo_gw_pos10\":0.09977324263038549,\"ps_ttm_pos10\":0.1564625850340136,\"stockCode\":\"601963\"},{\"date\":\"2022-12-05T00:00:00+08:00\",\"d_pe_ttm\":4.103174509887788,\"pb_wo_gw\":0.4613370166517004,\"ps_ttm\":1.5022145403621074,\"dyr\":0.07015873015873016,\"sp\":3.15,\"mc\":927321542409.15,\"d_pe_ttm_pos10\":0.036228900782214905,\"pb_wo_gw_pos10\":0.009880609304240428,\"ps_ttm_pos10\":0.03828736105393166,\"stockCode\":\"601988\"},{\"date\":\"2022-12-05T00:00:00+08:00\",\"d_pe_ttm\":3.4624807806607887,\"pb_wo_gw\":0.4122407994449317,\"ps_ttm\":1.2903884133618477,\"dyr\":0.05263157894736842,\"sp\":5.7,\"mc\":20840329033.2,\"d_pe_ttm_pos10\":0.06139777922926192,\"pb_wo_gw_pos10\":0.03200522534291313,\"ps_ttm_pos10\":0.03200522534291313,\"stockCode\":\"601997\"},{\"date\":\"2022-12-05T00:00:00+08:00\",\"d_pe_ttm\":4.097991833344827,\"pb_wo_gw\":0.4632725506133215,\"ps_ttm\":1.1896355919188693,\"dyr\":0.05921568483691995,\"sp\":5.1,\"mc\":249567702650.69998,\"d_pe_ttm_pos10\":0.06180469715698393,\"pb_wo_gw_pos10\":0.09064688916357644,\"ps_ttm_pos10\":0.12649361351462712,\"stockCode\":\"601998\"},{\"date\":\"2022-12-05T00:00:00+08:00\",\"d_pe_ttm\":6.642562058160752,\"pb_wo_gw\":0.6179253881091139,\"ps_ttm\":2.1852788205102858,\"dyr\":0.032786836731279856,\"sp\":4.88,\"mc\":8798996762.16,\"d_pe_ttm_pos10\":0.019138755980861243,\"pb_wo_gw_pos10\":0.01845522898154477,\"ps_ttm_pos10\":0.04647983595352016,\"stockCode\":\"603323\"},{\"date\":\"2022-12-05T00:00:00+08:00\",\"d_pe_ttm\":5.989727086843192,\"pb_wo_gw\":0.7547663313487145,\"ps_ttm\":1.4549760787492936,\"dyr\":0.016851441241685146,\"sp\":13.53,\"mc\":262562073218.94,\"d_pe_ttm_pos10\":0.03955500618046971,\"pb_wo_gw_pos10\":0.03419859909353111,\"ps_ttm_pos10\":0.08199423156159868,\"stockCode\":\"000001\"},{\"date\":\"2022-12-05T00:00:00+08:00\",\"d_pe_ttm\":17.980822516585345,\"pb_wo_gw\":1.0719577359675145,\"ps_ttm\":7.995423683053561,\"dyr\":0.008720930204691148,\"sp\":3.44,\"mc\":13636204190.24,\"d_pe_ttm_pos10\":0.0975609756097561,\"pb_wo_gw_pos10\":0.1317928968763372,\"ps_ttm_pos10\":0.21994009413778348,\"stockCode\":\"000563\"},{\"date\":\"2022-12-05T00:00:00+08:00\",\"d_pe_ttm\":9.678994161738203,\"pb_wo_gw\":1.450631652904664,\"ps_ttm\":3.7041679538004364,\"dyr\":0.015188335358444714,\"sp\":32.92,\"mc\":217390208872.64,\"d_pe_ttm_pos10\":0.5543163981825692,\"pb_wo_gw_pos10\":0.34283353985956216,\"ps_ttm_pos10\":0.5968608013217679,\"stockCode\":\"002142\"},{\"date\":\"2022-12-05T00:00:00+08:00\",\"d_pe_ttm\":6.362589797230728,\"pb_wo_gw\":0.6691666976307844,\"ps_ttm\":2.2728123861826224,\"dyr\":0.04411682182207197,\"sp\":4.08,\"mc\":8861965958.4,\"d_pe_ttm_pos10\":0.017880794701986755,\"pb_wo_gw_pos10\":0.019205298013245033,\"ps_ttm_pos10\":0.017880794701986755,\"stockCode\":\"002807\"},{\"date\":\"2022-12-05T00:00:00+08:00\",\"d_pe_ttm\":7.166189299451391,\"pb_wo_gw\":0.7995318135988122,\"ps_ttm\":2.1394590179310997,\"dyr\":0.027834509192260718,\"sp\":4.79,\"mc\":10392620156.58,\"d_pe_ttm_pos10\":0.020523708421797595,\"pb_wo_gw_pos10\":0.02264685067232838,\"ps_ttm_pos10\":0.027600849256900213,\"stockCode\":\"002839\"},{\"date\":\"2022-12-05T00:00:00+08:00\",\"d_pe_ttm\":6.37578352754703,\"pb_wo_gw\":0.4725687420147556,\"ps_ttm\":1.279225307949491,\"dyr\":0,\"sp\":2.49,\"mc\":20581188621.510002,\"d_pe_ttm_pos10\":0.05691854759568204,\"pb_wo_gw_pos10\":0.03925417075564279,\"ps_ttm_pos10\":0.02845927379784102,\"stockCode\":\"002936\"},{\"date\":\"2022-12-05T00:00:00+08:00\",\"d_pe_ttm\":6.882714108364733,\"pb_wo_gw\":0.7006578400908937,\"ps_ttm\":1.6754904678727536,\"dyr\":0.04456824512534819,\"sp\":3.59,\"mc\":20895073459.16,\"d_pe_ttm_pos10\":0.13034188034188035,\"pb_wo_gw_pos10\":0.09294871794871795,\"ps_ttm_pos10\":0.06196581196581197,\"stockCode\":\"002948\"},{\"date\":\"2022-12-05T00:00:00+08:00\",\"d_pe_ttm\":5.399893920219782,\"pb_wo_gw\":0.5451261558998634,\"ps_ttm\":1.6972044069017351,\"dyr\":0.033112563464419195,\"sp\":3.02,\"mc\":16777934801,\"d_pe_ttm_pos10\":0.044543429844097995,\"pb_wo_gw_pos10\":0.0334075723830735,\"ps_ttm_pos10\":0.0957683741648107,\"stockCode\":\"002958\"},{\"date\":\"2022-12-05T00:00:00+08:00\",\"d_pe_ttm\":7.677374148353134,\"pb_wo_gw\":0.8135691209278452,\"ps_ttm\":2.397743169002321,\"dyr\":0.0331006969489188,\"sp\":7.69,\"mc\":28197143249.29,\"d_pe_ttm_pos10\":0.18395061728395062,\"pb_wo_gw_pos10\":0.41728395061728396,\"ps_ttm_pos10\":0.4567901234567901,\"stockCode\":\"002966\"}]}";
        companyFundamentalCNResult_RootVO resultObj = (companyFundamentalCNResult_RootVO) getResult_companyFundamentalCN.getResultCompanyFundamentalCN(resultJson);
//        getAllCompany(resultObj.getData());
//        resultObj.getData().forEach(System.out::println);
//        System.out.println(resultObj.getData().size());
        getdoubleMap(resultObj.getData(),resultObj.getData().get(0).getDate());
    }

    public static void getdoubleMap(List<companyFundamentalCNResult_DataVO> voList,String date) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String dateFormat=sdf.format(sdf.parse(date));
        Map<String, Map<String, String>> doubleMap=new HashMap<>();
        for (int i = 0; i < voList.size(); i++) {
            companyFundamentalCNResult_DataVO vo=voList.get(i);
            Map<String, String> mapTemp = new HashMap<>();
            mapTemp.put("pe", String.valueOf(new DecimalFormat("0.0000").format(vo.getD_pe_ttm())));
            mapTemp.put("pb", String.valueOf(new DecimalFormat("0.0000").format(vo.getPb_wo_gw_pos10())));
            mapTemp.put("ps", String.valueOf(new DecimalFormat("0.0000").format(vo.getPs_ttm())));
            mapTemp.put("pepos", String.valueOf(new DecimalFormat("0.0000").format(vo.getD_pe_ttm_pos10())));
            mapTemp.put("pbpos", String.valueOf(new DecimalFormat("0.0000").format(vo.getPb_wo_gw_pos10())));
            mapTemp.put("pspos", String.valueOf(new DecimalFormat("0.0000").format(vo.getPs_ttm_pos10())));
            mapTemp.put("mc", String.valueOf(new DecimalFormat("0.00").format(vo.getMc()/100000000)));
            mapTemp.put("syr", String.valueOf(new DecimalFormat("0.0000").format(vo.getDyr())));
            mapTemp.put("sp", String.valueOf(vo.getSp()));
            mapTemp.put("stockcode", String.valueOf(vo.getStockCode()));
            mapTemp.put("date", dateFormat);
            mapTemp.put("rank", "");
            doubleMap.put(vo.getStockCode(),mapTemp);
        }
//        System.out.println(doubleMap.get("601997"));
        getdoubleMapweight(voList,doubleMap);
    }

    public static void getdoubleMapweight(List<companyFundamentalCNResult_DataVO> voList,Map<String, Map<String, String>> doubleMap) {
        voList.sort(Comparator.comparing(companyFundamentalCNResult_DataVO::getD_pe_ttm));
        for (Integer i = 0; i < voList.size(); i++) {
            String StockCode=voList.get(i).getStockCode();
            Map<String, String> mapTemp = doubleMap.get(StockCode);
            mapTemp.put("rank", String.valueOf(i));
            doubleMap.put(StockCode, mapTemp);
        }

        voList.sort(Comparator.comparing(companyFundamentalCNResult_DataVO::getPb_wo_gw));

        for (int i = 0; i < voList.size(); i++) {
            String StockCode=voList.get(i).getStockCode();
            Map<String, String> mapTemp = doubleMap.get(StockCode);
            mapTemp.put("rank", String.valueOf(Integer.parseInt(doubleMap.get(voList.get(i).getStockCode()).get("rank"))+i));
            doubleMap.put(voList.get(i).getStockCode(), mapTemp);
        }

        voList.sort(Comparator.comparing(companyFundamentalCNResult_DataVO::getPs_ttm));
//        voList.forEach(System.out::println);
        for (int i = 0; i < voList.size(); i++) {
            String StockCode=voList.get(i).getStockCode();
            Map<String, String> mapTemp = doubleMap.get(StockCode);
            mapTemp.put("rank", String.valueOf(Integer.parseInt(doubleMap.get(voList.get(i).getStockCode()).get("rank"))+i));
            doubleMap.put(voList.get(i).getStockCode(), mapTemp);
        }
        for (int i = 0; i < voList.size(); i++) {
            System.out.println(doubleMap.get(voList.get(i).getStockCode()));
        }
//        System.out.println("doubleMap = " + doubleMap);
//        System.out.println(doubleMap.get("601997"));
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

    public static void main(String[] args) throws IOException, ParseException {
        requestCompanyFundamentalCN();
    }

}
