<!--
 * @Author: your name
 * @Date: 2020-08-24 18:57:33
 * @LastEditTime: 2020-08-27 20:35:10
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \ruoyi-ui\src\views\benyi\activity\index.vue
-->
<template>
  <div class="app-activity">
    <h2 class="title">幼儿园大型活动方案</h2>
    <p
      class="text indent"
    >每个幼儿园都会组织大型活动，活动的目的不外乎帮助幼儿园招生，扩大幼儿园的影响力，目前仍然有幼儿园以孩子表演节目为主要途径。从发点更多是迎合成人的角色，为突出节目效果无所不用其极，恐吓、威胁、诱惑、交易等等，孩子不配合、抵触排练节目、抵触上幼儿园时；直接影响到家长的情绪，家长心疼、不理解；重要是剥夺了孩子应该有的快乐时间，老师也十分劳累。</p>
    <p class="text">
      以下4个案例
      <b>（跳蚤市场、亲子美食厨房、生日会、Showtime）</b>均为开放性活动，孩子喜欢、玩的尽兴，家长容易参加，老师轻松，幼儿园易组织易管理。
    </p>
    <div class="tabs" ref="tabs">
      <el-tabs tab-position="top" v-model="activeName" style="margin-top: 10px;">
        <el-tab-pane label="跳蚤市场" name="t1">
          <div class="content">
            <h3 class="title2">一、活动整体介绍</h3>
            <p class="text"><b>市场的由来：</b>了解活动的由来丰富老师的已有经验，全面有效组织和管理本次活动。</p>
            <p class="text"><b>组织频率：</b>建议与节日相结合，更能衬托节日的气氛，如六一儿童节，活动也更热闹，幼儿园方便组织。</p>
            <p class="text"><b>活动形式：</b>由于是全园性质的开放性活动，为避免出现危险事故，建议每一个家庭由一个家长照顾一个孩子，能够保证幼儿安全的同时也减轻了老师的工作量。</p>
            <p class="text"><b>活动时间：</b>建议选择周六日，孩子的父母更容易参与。</p>
            <p class="text"><b>活动地点：</b>建议选择在幼儿园，户外活动场地为第一首选。老师们更了解幼儿园内部的地形，保证安全，便于进行紧急事件的处理。以防因为天气等突发原因所以活动的备案可定为幼儿活动教室和走廊。外部的弊端：危险的突发性、人员的可控性、场地的局限性等等，相对比室外的场地。</p>
            <h3 class="title2">二、注意事项</h3>
            <p class="text">1. 摊位费20元以内，象征性的收取一些摊位租赁费用，同时用摊位费限制人数。</p>
            <p class="text">2. 交易物品10元以内，本次活动以游戏体验为主，让孩子体验生活中的交易活动，增加社会生活经验，认识人民币以及使用方法。</p>
            <p class="text">3. 使用幼儿园特制钱币进行交易。</p>
            <p class="text">（1）本身交换钱币对于孩子来说就是一种学习的过程。</p>
            <p class="text">（2）用假币进行交易让孩子让孩子觉得是在玩，用假币淡化真币的商业性质。在没有压力的情况下进行交易。在活动结束后，家长可拿着游戏币兑换回等额的人民币。</p>
            <p class="text">4. 每次兑换钱币不得超过20元。因为商品的售价不是很高，再者也有以物换物的许可。</p>
            <h3 class="title2">三、跳蚤市场的组织流程</h3>
            <p class="text"><b>确定活动——岗位分配——物资准备——发放邀请函——统计人数——场地布置——短信提醒——播放音乐——持券入场——兑换货币——自由交易——抽奖时间——活动结束——场地清理——总结反馈</b></p>
            <p class="title3 text">1、确定活动时间</p>
            <p class="text">建议在学期初制定计划时将一学期中所有的大型活动执行的时间，呈现在园例中。每个班级发放一份，让老师们对一学期的大型活动安排做到心中有数。</p>
            <p class="text">在班长会或教研会上布置任务责任到人制，让整个活动进展更顺利。</p>
            <p class="title3 text">2、岗位分配</p>
            <p class="text"><b>总负责人：</b>可以是园长或其他管理层人员，主要负责掌控全局，协调各部门工作，保证活动如期顺利的举行。</p>
            <p class="text"><b>主持人：</b>活动开场主持和抽奖结束主持，主持词自写，自会议3天后，初审稿。</p>
            <p class="text"><b>管理员（照相师）：</b>交易市场巡逻人员，主要工作是负责调解纠纷、确保活动中家长和幼儿的安全、对违反活动规则的人进行提醒和监督。根据以往的活动经验可为园长们提供的参考数据：8个班，3个巡逻员。管理员可兼职照相。</p>
            <p class="text"><b>摄像师：</b>全程跟拍，用于网站等宣传用。</p>
            <p class="text"><b>钱币兑换员：</b>在入场后为家长们兑换游戏币的人员，根据园所人数兑换钱币可以设置2-3个兑换点，每个兑换点1名老师。提前把钱币捆好以20元为一捆，节约换钱币时间。大部分人员入场后，可留下一个兑换点以备不时之需，其他可撤掉。</p>
            <p class="text"><b>接待员：</b>门口入场接待的老师，保健医等人员。保证对于紧急情况能够及时处理。</p>
            <p class="text"><b>摊主：</b>以幼儿家庭为主，提前发放通知，由家长自愿报名。根据幼儿园的总人数和幼儿园场地确定摊主名额。根据摊位数量班级老师也可以参与到活动中来。</p>
            <p class="title3 text">3、物资准备（幼儿园）</p>
            <p class="text"><b>邀请函：</b>提前1周发放邀请函。邀请函的内容写清楚活动的目的、时间、地点、注意事项及活动流程。</p>
            <p class="text"><b>海报：</b>提前一周做好，贴在宣传栏、大门和后门上。海报内容基本与邀请函内容相同，活动目的需要写的有趣、吸引目光。</p>
            <p class="text"><b>入场券：</b>一个家庭一张票，所有家庭持票入场，允许一个家长带领一名幼儿，摊主家庭的另外一位家长需要出示摊位号。</p>
            <p class="text">入场券可以做成正副联、也可以做成单张。如图：</p>
            <p class="imgs">
              <img src="../../../assets/image/activity/1.png" alt="">
              <img src="../../../assets/image/activity/2.png" alt="">
            </p>
            <p class="text">入场券幼儿园可以自己设计制作，正面背景是幼儿园图片，写上活动名称及时间、地点。入场券的背面写上注意事项和活动规则。</p>
            <p class="text"><b>摊位号：</b>需要准备2套摊位号。1套在交摊位费报名时发给摊主，方便摊主在活动当天直接按号就位。另1套放在抽奖箱中用于抽奖时使用。</p>
            <p class="text"><b>横幅：</b>提前一周制作横幅挂在幼儿园大门上或教学楼上。</p>
            <p class="text"><b>游戏钱币：</b>根据幼儿园总人数，钱币可以多一些。有不同面值的钱币方便孩子兑换和使用。</p>
            <p class="imgs">
              <img src="../../../assets/image/activity/3.png" alt="">
            </p>
            <p class="text">另一种是不同面值的人民币若干，1元、5元和10元。方便活动结束后家长用剩余的游戏币兑换人民币。</p>
            <p class="text"><b>管理员和摊主挂牌：</b>可制作袖章或颈挂牌明确身份。</p>
            <p class="imgs">
              <img src="../../../assets/image/activity/4.png" alt="">
              <img src="../../../assets/image/activity/5.png" alt="">
            </p>
            <p class="text"><b>奖品：</b>摊主可凭摊位号参与抽奖活动，根据摊主人数合理安排奖品的等级和每个等级的人数。如：一等奖5名、二等奖10名、三等奖15名。</p>
            <p class="text">其他人员可凭入场券每个孩子均可获得一份参与奖品。</p>
            <p class="title3 text">4、物资准备（家长）</p>
            <p class="text"><b>摊位费：</b>20元，最好是正好的金额，交钱兑换，避免找零等待的时间过长。</p>
            <p class="text"><b>摊位布：</b>用家中的旧床单、窗帘、桌布等即可，椅子幼儿园提供。</p>
            <p class="text"><b>可卖物品：</b>家长可准备玩具、小饰品、手工品、图书、文具等。建议家长与孩子商量哪些物品是可以拿出去交易的，由孩子做决定。绝对禁止食品类涉及到安全问题。</p>
            <p class="text"><b>纸、笔、标语：</b>帮助自己卖物品，可在家做好标语带来，根据时间和物品的受欢迎程度，可调整物品的价格。如：原价2元，现价1元。</p>
            <p class="title3 text">5、发放邀请函</p>
            <p class="text">提前一周把邀请函发到家长手中，内容包括：活动目的、活动时间、活动流程、活动规则及注意事项等。最好也要有一份回执，方便幼儿园统计参与活动的人数。</p>
            <p class="title3 text">6、统计人数</p>
            <p class="text">知道参加活动的人数，便于做好活动前的一切准备，比如：奖品和游戏币等。</p>
            <p class="title3 text">7、场地布置</p>
            <p class="text">场地划分，老师明确会场各个地方的具体布置，规划摊主的交易摊位，用粉笔在地上写出摊位号。饮水机设立在大厅中，多准备几个饮水点，避免等待和拥挤，提前将温开水晾好导入饮水桶中。如图：</p>
            <p class="imgs">
              <img src="../../../assets/image/activity/6.png" alt="">
            </p>
            <p class="imgs">
              <img src="../../../assets/image/activity/7.png" alt="">
            </p>
            <p class="text"><b>活动规则的张贴：</b>在交易场的固定地点多张贴几张活动规则，时刻提醒家长和孩子们要遵守活动规则。</p>
            <p class="title3 text">8、微信提醒</p>
            <p class="text">活动前一天老师发送短信，短信的内容简洁明了，主要是提醒家长不要忘记明天的活动，再次提醒家长明天活动的入场时间。</p>
            <p class="title3 text">9、跳蚤市场活动当天的流程</p>
            <p class="text">（1）播放音乐：以古典音乐为主，如：晨曲幽默曲等等。可以循环播放一首也可以播放多首，直到活动开始后，音乐的声音适当调小一些。</p>
            <p class="text">（2）持券入场：用手撕、打孔、盖章等方法均可进行检票，鼓励家长和孩子排队进场。</p>
            <p class="text">（3）兑换货币：摊主提前30分钟入场，摆好摊后先兑换活动币。到入场时间其他人员陆续入场，第一件事先兑换游戏币，家长提前准备好20元钱，老师也提前把20元的游戏币准备好，前期人多可分为3个兑换点，等待所有家庭第一轮兑换后，可只留一个兑换处。</p>
            <p class="text">（4）主持开场：对家长和小朋友的到来表示欢迎，简单介绍饮水区和交易区的位置，强调活动的规则及注意安全的事项，抽奖的时间等。</p>
            <p class="text">（5）抽奖时间：预警活动结束的时间。告知摊主家庭抽奖位置，其他家庭可带领孩子到领取奖品的位置凭入场券领一份纪念奖。</p>
            <p class="text">（6）清理会场</p>
            <p class="title3 text">10、总结</p>
            <p class="text"><b>总结的时间：</b>定在活动当天，利用活动后30分钟的时间进行总结。如：11:30</p>
            <p class="text"><b>发言内容：</b>可以从活动是否顺利？是否出现预料之外的事件？老师们是否都按部就班的工作？本次活动的不足与优势，针对不足的解决方案。</p>
            <p class="title3 text">11、后续工作</p>
            <p class="text">根据幼儿园的家园互动平台在活动结束后可在网站、微信、QQ编辑本次活动的总结以及图片等。</p>
          </div>
        </el-tab-pane>
        <el-tab-pane label="亲子美食厨房" name="t2">
          <div class="content">
            <p class="text">从名字上看他是一个动手制作美食的活动，这是孩子们喜欢的活动之一，孩子们不仅得到自我服务的锻炼体验活动的乐趣，总之孩子喜欢，家长享受，老师轻松。</p>
            <p class="text">在这次活动中会涉及到6个美食厨房：</p>
            <h3 class="title2">曲奇妙妙屋</h3>
            <p class="text">三明治、清凉水吧、水果沙拉工作室、饺子馆、寿司屋</p>
            <h3 class="title3">一、亲子美食厨房的活动组织流程</h3>
            <p class="text"><b>确定活动——发放邀请函——岗位分配——物资准备——统计人数——场地布置——短信提醒——音乐播放——持券入场——自由活动——活动结束——场地清理——总结反馈</b></p>
            <p class="text">亲子美食厨房的组织流程同跳蚤市场的组织流程一致。只是在<span class="danger">物资准备</span>里有调整。</p>
            <h3 class="title3">1.（略）2.（略）3.（略）</h3>
            <h3 class="title3">4. 物资准备</h3>
            <p class="text">⑴在物资准备中，园所需要制作游园券。如图：</p>
            <p class="imgs">
              <img src="../../../assets/image/activity/8.png" alt="">
            </p>
            <p class="text">鼓励孩子参加每一个活动，每一个活动完成后得到一个印章，最终获得5个印章即可得到小礼品一份。（除饮品区外，其他活动均可参与一次）</p>
            <p class="text">⑵准备每个小屋活动所需的活动材料，每个活动小屋的负责人至少为3个，1人在门口在每个家庭进教室的时候印章，同时按照活动规则的规定人数限制人流，保证现场的秩序。2人在教室里，一边维持活动秩序，一边随时补充活动所需材料。</p>
            <p class="text">⑶曲奇妙妙屋</p>
            <el-row :gutter="10" class="row-item">
              <el-col :span="6">01、烤箱（2-3个）</el-col>
              <el-col :span="6">02、托盘</el-col>
              <el-col :span="6">03、半成品的面糊、面团</el-col>
              <el-col :span="6">04、装饼干的塑料袋</el-col>
            </el-row>
            <el-row :gutter="10" class="row-item">
              <el-col :span="6">05、油纸</el-col>
              <el-col :span="6">06、便利贴</el-col>
              <el-col :span="6">07、裱花袋</el-col>
              <el-col :span="6">08、笔</el-col>
            </el-row>
            <el-row :gutter="10" class="row-item">
              <el-col :span="6">09、曲奇妙妙屋的规则</el-col>
              <el-col :span="6">10、负责人标识</el-col>
            </el-row>
            <p class="text">准备两种制作曲奇的材料和工具，一种是面糊状需要用裱花袋制作的，另一种是面饼状需要用模具刻印的。幼儿可根据自己的兴趣选择。笔和便利贴是贴在自己制作的饼干旁边注明，避免拿错饼干。如果有想把饼干带走的孩子，可以装袋带走</p>
            <p class="text"><b>流程：</b>门口盖章后，家长和孩子进门领取材料，找座位开始制作饼干。</p>
            <p class="text"><b>规则：</b>请排队参加活动；不要靠近厨工区；活动前请洗手；每个家庭限做6个。其他小屋活动均如此。</p>
            <p class="text"><b>三明治：</b>自助取餐式，老师将所有材料切好，每一样食材放到一个盘子里，盘子前面的标牌写上食材的名字。每个孩子2片方形面包，蔬菜根据自己所需量取，不浪费，三明治做好后，斜对角切成三角形，孩子与家长共同分享劳动果实。</p>
            <p class="text"><b>清凉水吧：</b>可多次参加，准备果珍和白开水，建议家长提醒幼儿，虽然可多次但不要多喝饮料。可将水吧设立在户外，场地更大，视野更开阔。</p>
            <p class="text"><b>水果沙拉：</b>自助取餐式，如果可以让孩子自己切会更好，如果因为时间的原因或其他原因，可以让孩子们切一些软一些的水果，如：香蕉、哈密瓜等。</p>
            <p class="text"><b>饺子馆：</b>提供两种材料，家长可根据自己的能力选取材料，一种是饺子皮，家长只需要和孩子一起包就好；另一种是饺子面，家长可以自己擀皮自己包。饺子面老师可以请厨房用一些蔬菜汁活，做成有颜色的饺子皮，增加趣味性。每个家庭限包6个，因为煮饺子在班级中煮，老师要规划处一个危险区，做上标志，避免孩子接触发生危险。</p>
            <p class="text"><b>寿司：</b>可将制作寿司所需的材料及工具，每个桌放一份，孩子们可以随用随去，避免孩子一遍遍在教室内来回走动取物品。</p>
            <h3 class="title3">5-13.（略）</h3>
          </div>
        </el-tab-pane>
        <el-tab-pane label="生日会" name="t3">
          <div class="content">
            <p class="text">这个生日会称之为集体生日会，每个月为全园的老师和小朋友集体过生日。以班级为单位轮流组织生日会，提前汇总当月过生日的老师及小朋友名单。</p>
            <h3 class="title2">一、活动前的准备：</h3>
            <p class="text">• 统计全园本月过生日的小朋友及老师的名单，并将名单贴于幼儿园宣传栏中。</p>
            <p class="text">• 本班老师可组织幼儿为寿星制作礼物。</p>
            <p class="text">• 提前一周罗列生日会的节目名单。</p>
            <p class="text">• 提前一周准备等待的游戏，要考虑到各个年龄段的孩子。</p>
            <p class="text">• 提前三天预定蛋糕。幼儿园如果有条件可以自己做；也可以在蛋糕店定做，要保证是正规的蛋糕店。</p>
            <p class="text">• 提前准备生日歌的音乐，可以播放音乐也可以现场弹奏。</p>
            <p class="text">• 提前一天制作寿星的生日头饰。</p>
            <p class="text">• 全场跟踪照相和摄像。</p>
            <h3 class="title2">二、当天活动流程：</h3>
            <p class="text">• 主持人现场组织游戏等待所有班级陆续到场。</p>
            <p class="text">• 全园齐唱生日歌。</p>
            <p class="text">• 邀请寿星出场，可用常规介绍或是用声音猜想的方式出场。</p>
            <p class="text">• 请报节目的班级友情演出3—4个节目。</p>
            <p class="text">• 赠送礼物。</p>
            <p class="text">• 生日蛋糕隆重登场：把蛋糕摆好，为过生日的宝宝和老师合影—点蜡烛、唱生日歌、许生日愿望、吹蜡烛、合影、分蛋糕。</p>
            <p class="text">• 主持人请班级老师按照顺序领取本班的蛋糕。</p>
            <p class="text">• 现场各班老师分发蛋糕请幼儿品尝。</p>
            <p class="text">生日会的时间一般会借用户外活动的时间，在10点左右举行，可以和间点一起吃。</p>
          </div>
        </el-tab-pane>
        <el-tab-pane label="Showtime" name="t4">
          <div class="content">
            <p class="text">每月一次，以班级为单位轮流进行组织，主要表演内容以微型课程、音乐律动、戏剧表演、歌曲等为主。表演场地最好在户外操场，其一场地足够宽阔，足以容纳全班的家长和孩子；其二，可以让围观的群众也看得到，潜在的也是在给幼儿园打广告。<b class="danger">（表演时间 - 自加）</b></p>
            <h3 class="title2">注意事项：</h3>
            <p class="text">1. 表演的时间不宜过长。时间在30分钟左右即可，一般情况下会把时间定在晚餐后开始。</p>
            <p class="text">2. 给每个孩子表演的机会。编排节目时给每个孩子的上台次数尽量都一样。</p>
            <p class="text">3. 表演结束后不要马上把孩子节奏。为避免发生危险，在表演前就告诉家长不可直接接走，老师把孩子领回班级，家长由班级接走。</p>
            <p class="text">4. 每次表演后家长要给予孩子掌声。家长想把自家孩子每一次的台上表演都记录下来，这个心情可以理解，但是对于孩子来说，他们的表演也是需要大人的鼓励和认可的，在每一次的表演后给予的掌声能够给他们更大的信心和勇气。</p>
            <h3 class="title2">活动前准备：</h3>
            <p class="text">•提前一周确定Showtime的节目名单，并告知接待处和教学部。 </p>
            <p class="text">• 提前一周准备家长通知，并贴与布告栏中。</p>
            <p class="text">• 提前一周打电话邀请准备入园的小朋友和咨询的小朋友，并做好幼儿园Showtime 的家长邀请函。</p>
            <p class="text">• 准备Showtime的道具，不需要太复杂，简单的小头饰、小手环，或是如果有小型舞台剧还需要准备舞台剧的物品。</p>
            <p class="text">• 提前一天将音响调试好，将舞台布置好，如果没有舞台可以在户外活动场地中用警戒线规划出来。</p>
            <p class="text">• 照相机。</p>
            <p class="text">• 提前一天进行Showtime的彩排。</p>
            <p class="text"><b>参加彩排的人员：</b>班级教师、小朋友、教学主任、行政主任。 </p>
            <p class="text"><b>彩排要求：</b>准备好道具、音响等有关设备。</p>
            <h3 class="title2">活动当天流程：</h3>
            <p class="text">• 提前播放欢快的音乐，等待所有家长陆陆续续到场。</p>
            <p class="text">• 晚餐后，为所有幼儿装扮（简单的装束和衣服）。</p>
            <p class="text">• 老师带领幼儿入场，安排座位。</p>
            <p class="text">• 主持人宣布Showtime表演正式开始（主持人可以是老师担当，也可以是班级幼儿，如果是班级幼儿的话最好是小朋友们推举或自荐，避免老师指定人选，引起家长的不满）。</p>
            <p class="text">• 老师照顾暂时没有节目的幼儿。</p>
            <p class="text">• 演出节目5—7个节目。（可有亲子表演，提前一周发放通知，由家庭自荐表演。）</p>
            <p class="text">• 宣布活动结束，老师带领幼儿回到班级，卸妆换衣。</p>
            <p class="text">• 家长有序的接孩子（由老师亲手交到家长手中，务必确保幼儿的安全）。</p>
            <p class="text">• 善后工作（舞台拆卸、椅子归还、表演的装束和化妆品等）。</p>
            <p class="text">• 班级老师总结（家长的出席程度？是否出现意外事件？本次活动的不足？下次需要准一哪些方面？由班级中年轻的老师首先发言，班长总结）。</p>
            <p class="text">在每一次举办的活动的时候可以向一些已经入园咨询过，留下信息的家长发邀请函，这也是招生的一个手段。</p>
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>
    <el-backtop target="body .app-activity">
      <!-- <div class="back-ball">
        Top
      </div> -->
    </el-backtop>

  </div>
</template>

<script>
export default {
  name: "Activity",
  data() {
    return {
      isFixed: false,
      activeName: 't1'
    };
  },
  created() {},
  mounted() {
  },
  computed: {},
  methods: {}
};
</script>
<style lang="scss" scoped>
.app-activity {
  padding: 0 10%;
  height: 100vh;
  overflow: hidden;
  overflow-y: auto;
  margin: 0 auto;
  color: #333;
  height: 100%;
  ::v-deep .el-tabs__item {
    font-weight: bold;
  }
  p {
    margin: 0;
  }
  .indent {
    text-indent: 2rem;
  }
  .text {
    font-size: 14px;
    line-height: 22px;
    padding-bottom: 12px;
  }
  .title {
    padding: 10px 0;
    margin: 0;
    font-size: 22px;
    text-align: center;
  }
  .title2 {
    text-align: left;
    font-size: 16px;
    display: flex;
    align-items: center;
    &::before {
      content: "";
      margin-right: 8px;
      width: 4px;
      height: 18px;
      background: #325cf3;
    }
  }
  .title3 {
    font-weight: bold;
    font-size: 14px;
  }
  .tabs {
    margin-bottom: 40px;
  }
  .danger {
    color: #F56C6C;
  }
  .err {
    font-size: 16px;
  }
  .content {
    padding: 0 15px;
    .imgs {
      display: flex;
      padding: 0 10%;
      img {
        flex: 1;
        padding: 5px;
      }
    }
    .row-item {
      line-height: 24px;
      padding-bottom: 5px;
      font-size: 14px;
    }
  }
}
</style>