<template>
  <div class="app-container">
    <div class="main">
      <div class="flex list-btn">
        <swiper class="doctor-nav" :options="infoNavOption" ref="infoSwiper">
          <!-- slides -->
          <swiper-slide v-for="(item, index) in btnList" :key="index">
            <div
              class="flex justify center content"
              @click="changeNav(item.value)"
              :class="[item.value === currentIndex ? 'active' : '']"
              :key="item.value"
            >
              {{ item.label }}
            </div>
          </swiper-slide>
        </swiper>
      </div>
      <div class="main-list">
        <ul class="list" ref="list" @scroll="onScroll">
          <li
            class="item"
            :ref="`item${i + 1}`"
            v-for="(item, i) in contents"
            :key="item.title"
          >
            <h2 class="list-title">{{ item.title }}</h2>
            <div
              style="margin-bottom: 30px"
              v-for="(info, index) in item.infos"
              :key="info.title"
            >
              <div class="title flex">
                <div class="line flex align-center">
                  <i class="num">{{ index + 1 }}</i>
                  <h3>{{ info.title }}</h3>
                </div>
              </div>
              <div class="content">
                <p
                  class="articles"
                  v-for="sec in info.section"
                  :key="sec"
                  v-html="sec"
                ></p>
              </div>
            </div>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import { treeselect, getFamilyedu } from '@/api/benyi/familyedu';
import Clipboard from 'clipboard';

export default {
  name: 'familyedu_study',
  data() {
    return {
      currentIndex: 1,
      infoNavOption: {
        // loop: true,
        // spaceBetween: 10,
        freeMode: true,
        slidesPerView: 'auto',
        freeModeMomentum: true,
        watchSlidesProgress: true,
        watchSlidesVisibility: true
      },
      btnList: [
        {
          label: '家是什么',
          value: 1
        },
        {
          label: '家教',
          value: 2
        },
        {
          label: '同情心',
          value: 3
        },
        {
          label: '个性与理想',
          value: 4
        },
        {
          label: '游戏',
          value: 5
        }
      ],
      contents: [
        {
          title: '一、家是什么',
          infos: [
            {
              title: '孩子最大心愿就是家里人都能幸福快乐',
              section: [
                '当孩子被问到"你对家有什么更高的期望"时，孩子们回答得最多的是"家里人都能幸福快乐。"可见，孩子们所企求的，就是一件再自然不过的事。作为父母，没有理由不认真面对这个现实。',
                '只要满足孩子的吃喝拉撒睡的需求，孩子就能自然长大的时代已经过去了。现在，安宁、幸福及快乐的家，是孩子健康成长的最好温床。这需要全家人，包括爸爸妈妈，祖父祖母、外祖父外祖母，都要有意识的共同努力才能做到。为了孩子，为了自己，有必要对“家”的概念重新认识一下。'
              ]
            },
            {
              title: '不会珍惜自己的人也不会珍惜孩子',
              section: [
                '尽管养育孩子确实很重要，但是整天神经绷得紧紧的也没必要。因为父母的烦躁不安会传染给孩子。',
                '正因为养育孩子很辛苦，所以，。父母应拥有属于自己的时间，保持心理健康很重要，互相配合，共同承担。有效利用到幼儿园或学校接送孩子的时间，可通过温馨的亲子互动让你的身心得到放松。',
                '此外，有什么事别自己一个人憋着烦恼。鼓起勇气去幼儿园跟园长或孩子的老师谈谈。本一幼儿园园长和教师都经过专门训练，他们都是理想的家庭教育话题健谈者。'
              ]
            },
            {
              title: '"养育孩子是孩子妈的事"，有这种想法的父亲要当心',
              section: [
                '在现在家庭里，由于教育参与的缺席，似乎父亲的权威变得越来越弱。对孩子辨别善恶、分清好坏以及勇气意志方面的社会与人格教育，也越来越马虎。一般说来，父母对孩子的养育想法不会差别太大。但父亲通常会站在与母亲不同角度与孩子互动，这就能矫正过于密切的母子关系。父亲自然地参与养育孩子，发挥父亲特有的影响力，父母的默契配合很有必要。一方面，母亲应注意不要在孩子面前贬低和瞧不起父亲。另一方面父亲也应注意，不要在孩子面前大声斥责或讽刺挖苦母亲，更不要动辄摆出“老子权威”对孩子无厘头地大喊大叫。',
              ]
            },
            {
              title: '未必"一说就知道"，但不说就更不知道了',
              section: [
                '在现代社会，如果闷着头什么都不说，要互相理解是很难的。不断增加夫妻之间、父母和孩子之间的沟通与对话，是建立幸福与快乐家庭的基础。无论是夫妻之间还是父母和孩子之间，最好的选择应该是不管什么事都可以交谈。 全家人一起吃饭，一起交流分享生活工作中的美好，早晨的拥抱再见，下午或傍晚的进门问候，让孩子帮忙做家务，和孩子一起玩，一起参加社区义务活动，等等，对增加感情和体验幸福快乐都很重要。',
              ]
            },
            {
              title: '能全家人一起吃饭真的很重要',
              section: [
                '饮食生活不仅对孩子身体健康重要，也对他们的心理与人格发展有深远影响。父母费心费力做出的可口饭菜，全家人一起吃饭的温馨与愉悦，等等，自然地能将父母的爱传达给孩子。由此得到的满足感、信赖感和归属感，能使孩子开朗豁达与阳光向上，从而促使孩子健康与坚强地成长。',
                '请注意饮食的营养均衡，设法保证有一定时间在一起吃饭的日子，养成全家人一起吃饭的习惯，并形成传统。'
              ]
            },
            {
              title: '父母生活态度积极向上，孩子一定能感受到',
              section: [
                '由于家长要一边工作一边养育孩子，致使家长与孩子接触的时间变少。但父母可以放心，只要每次与孩子在一起时候<b>互动质量</b>高，那么，疼爱孩子，为了更美好的未来而努力奋斗的家长形象，一定会深深地铭刻在孩子的心中。',
                '另外，各种各样烦恼是难免的。别一个人撑着，应请亲戚、朋友们协助，或积极利用幼儿园资源求得帮助。无论何时，都要充满自信地养育孩子，表现出积极向上的生活态度。在家里，如果父母脸上总挂着幸福的笑容，会使孩子一直感受幸福，形成阳光的人格。'
              ]
            }
          ]
        },
        {
          title: '二、家教',
          infos: [
            {
              title: '有一件事很奇怪，那就是孩子的缺点跟父母总是很相似',
              section: [
                '那些"只要自己方便，别的我不管"不守公德的人，令人讨厌与唾弃。如果你的孩子这样做时，如果公众不制止，孩子会误认为自己做得对。这样就可能慢慢变成一个不讨人喜欢的人。',
                '应抛弃"只要自己孩子好就行，别的我不管"的想法。孩子做错事时，要<b>以父母之爱</b>严正斥责、严加管教。如果父母在孩子小的时候不管教，那么，将来长大后让社会去管教，那就会付出高代价。',
                '同时，大人自己也要注意尽量不做出轨的事，做能一直让孩子依赖、尊敬的父母。榜样力量是无穷的。这是家庭教育真理。'
              ]
            },
            {
              title: '"规矩"不只是为孩子所定，父母要率先做出典范',
              section: [
                '为了让孩子懂规矩并一直遵守规矩，父母要经过认真讨论，定出明确的家规。父母和孩子一起遵守这个家规。另外，倾听孩子的意见、和孩子共同定家规也很重要。',
                '家规不仅包括日常问候、就寝时间、餐桌礼仪、梳洗清洁、关灯及起床时间等，还包括不给别人添麻烦、诚实守信不撒谎等社会规范。在家里，孩子们有时候守规矩，有时候"犯规"，直到养成习惯。也由此逐渐学会处理人与人之间的关系，了解社会规则的重要性。',
                '如果你不太清楚应该给孩子在家里制定什么样的规则，请到本一幼儿园里问园长或孩子的老师。他们会通过告诉你在幼儿园班级中所树立的各种规则，来提示你怎样家园同步地制定与遵守家庭“规矩”。',
              ]
            },
            {
              title: '如果想让孩子长大不争气，那就他要什么你就给他买什么',
              section: [
                '如果父母不加考虑，孩子要什么就给孩子买什么，那就无法让孩子获得为了得到自己想要的东西而努力、忍耐以及坚持的精神，从而使孩子变得什么都想要，而且无法形成自控力，导致随心所欲，为所欲为、六亲不认、畜生霸道的坏习惯与不良人格。',
                '不管孩子怎么哭闹、缠人，甚至在地上打滚不起来，不合理和不必要的东西也坚决不给买。不要给太多的零花钱。有计划地每周给多少，然后教会孩中自己安排，计划怎样花。予取予求的方式，只能培养出大手大脚、霸道不孝，而且从不明白“钱难挣”道理的败家子。如果真为孩子着想，比起在孩子身上花很多钱，更应在孩子身上花费时间、精力及心血，倾注父母之爱。精神大餐的给予永远比物质欲望的满足更靠谱。',
              ]
            },
            {
              title: '如果让孩子帮着做家务，他就会变得很能干 ',
              section: [
                '孩子们有自我中心与缺乏自主性与自立等倾向。主要是因为自我责任感没有形成。今天我们的父母太宠爱孩子，好多人没有受过"自己的事儿自己做"的家教。在家里定出规矩，让孩子分担家务，对培养孩子的责任感、自主性、自立精神，以及感受到自己是有用的人等方面很重要。',
                '让孩子从"把玩过的玩具或用过的东西整理好"等小事做起，养成和父母一起做家务的习惯。',
              ]
            },
            {
              title: '如果恋上手机或电视，那孩子就太寂寞啦',
              section: [
                '如果孩子整天呆在屋里玩手机、看电视或在电脑上玩电子游戏的话，容易造成与他人、与大自然接触的体验不够，与他人不能很好地交流，缺乏同情心，对生活冷淡，生与死的现实感薄弱，不能区别现实与假想世界，会给孩子身心的健康成长留下不可逆转的阴影。',
                '要给孩子创设与其他小朋友一起玩、体验自然与环境的机会。定出不多玩手机，不多看电视及不多玩电子游戏的“规矩”，并使孩子养成遵守这些规矩的习惯。',
                '请父母与孩子共处时，不要总是低着头看自己的手机。那样，会让孩子觉得父母的手机比他们自己更重要。陪伴不只是人在一起，更重要的是心灵互动和情感交流。'
              ]
            },
            {
              title: '孩子并不能正确发出"危险"信号',
              section: [
                '父母情绪焦躁，对孩子的爱抚、疼爱不够，以及对孩子过分娇生惯养和过多干预等等，都会影响到孩子的心理健康。并常常会在孩子身上表现出来。例如孩子出现肚子疼、恶心、拉肚子、食欲不振、头晕眼花、发烧等症状，以及吃得过多、失眠、吮指头、咬指甲等行为。当察觉到这些症状及行为后，不要只担心孩子是不是生病了，还要好好想想这些症状与行为是不是由于<b>心理原因</b>造成的。',
                '不要仅仅给孩子贴上"神经过敏"、"任性"、"懒惰"之类的标签了事。要好好观察孩子，认真听听孩子说什么，秉持努力理解孩子的态度很重要。',
              ]
            },
            {
              title: '孩子愿意跟怎样的父母谈心？',
              section: [
                '人，是一种喜欢被别人爱、被别人理解的生物。如果得不到别人的理解而产生的不满积累太多的话，有时会一下子爆发出来。孩子突然大发脾气时，连孩子本人和家长都搞不清是怎么回事。',
                '其实，孩子发火、苦恼是有原因的。如父母平时做到仔细听孩子说话、与孩子站在同一角度考虑问题、对孩子的事非常关心等等，孩子便能深刻感受到自己被父母爱着。孩子只有在感到自己被爱着时，才能心平气和地对待问题。这样能避免不必要的冲突，使孩子能接纳别人、正视问题，茁壮成长。',
                '尊重和爱的发生过程是单向的。孩子在小时候只有被人尊重和爱着，他们长大后才会去尊重和爱别人。'
              ]
            }
          ]
        },
        {
          title: '三、同情心',
          infos: [
            {
              title: '1.幸福，不仅有从别人那儿得到的幸福，还有为别人造就的幸福',
              section: [
                '为培养同情弱者，有勇气帮助、爱护弱者的孩子，家长能做些什么呢？父母可以从孩子很小的时候开始，通过日常生活的实践培养孩子的同情心。父母应率先给孩子做出榜样，逐渐培养孩子给孕妇、老年人等让座，当残疾人遇到困难时能主动上前询问等等的行为习惯。',
              ]
            },
            {
              title: '2.别让孩子成为歧视别人的人',
              section: [
                '当发觉孩子有欺负、伤害、污辱别人的行为时，父母有责任教导自己的孩子：对正直的人来说，这是可耻的行为！在这种时候，与其给孩子讲大道理，不如讲讲父母是如何爱孩子，并希望孩子成为杰出的人；讲讲看到自己孩子欺负弱者时是多么吃惊；看到自己孩子对别人受到伤害时反而高兴是多么气愤；等等。父母要尽量把自己的真实想法传达给孩子。',
                '还有，要向孩子表明：父母自己不持偏见，不欺负别人，也不容许孩子欺负别人。这对孩子的教育很重要。'
              ]
            },
            {
              title: '人和人不一样，生命只有一次',
              section: [
                '在现代生活中，孩子亲眼所见亲人死去的情况越来越少。如果只是习惯电视节目、电子游戏中不断重复杀人虚构而死的话，将很难体会到"生命是宝贵的，以及生命只有一次"的事实。应该让孩子在大自然中游戏探索。比如：精心培育花草虫鱼等，有意识地给孩子提供各种生物生活以及看到生物死亡的机会，让孩子切实感受到生命的尊严和珍贵。',
                '还有，让孩子去尽力想像失去亲人的家属，以及有心灵创伤人的心情与感受，使孩子去感受和理解那种极度悲伤是多么强烈。'
              ]
            },
            {
              title: '最精彩的书，是父母自己读给孩子听的书',
              section: [
                '在孩子感受到父母之爱与温馨的同时，也要让他们接触优秀的图书。和家长一起阅读并产生共鸣的时候，正是丰富孩子的感情和心灵的宝贵时刻。就像人必须有吃饭时间一样，父母也应费心设定"读书时间"，哪怕只有一点点时间也没关系，坚持每天讲故事对孩子来说是最好的精神大餐：<b>在感受父母关爱与呵护的同时，充分满足无尽的好奇心与认知需求。</b>',
                '但是，如果在孩子还很小的时候就一下子给孩子读很难的故事和讲很深的道理，那会搞得孩子心理紧张和焦虑，反而可能使孩子对书产生厌恶情绪。所以，最好是由浅入深慢慢来。'
              ]
            }
          ]
        },
        {
          title: '四、个性与理想',
          infos: [
            {
              title: '把孩子培养成一个能自己思考、自己行动的人',
              section: [
                '如果父母把孩子要跨越的障碍物先全部清除掉，从而通过"过度保护"让孩子在生活中顺风顺水，即孩子每走一步都要告诫和警示一下，那么，孩子到任何时候也不会一个人走路。父母这样做也剥夺了孩子接受挑战、从失败中学习以及获得各种体验的机会，等于是剥夺了体验生活的机会，从而降低了生命质量。',
                '父母们容易拿自己的孩子跟别人家的孩子比较，或把自己的希望强加给孩子，让孩子与自己的步调一致，等等。但是，没有一个孩子是跟别的孩子完全一样的。父母应重视孩子的个性，去爱眼前<b>"就是这个样子"</b>的现实中的孩子。'
              ]
            },
            {
              title: '不要总是指责缺点，应去发现和指出优点并使其不断增多',
              section: [
                '对孩子来说，重要的是具有自信、自爱的精神。这种精神就像植物的根一样，扎得越深越广，结出的果实也就越大。父母不要只看表面情况，对孩子的成长与发展要充满信心。给孩子心田浇灌丰富的营养和水分。那么，成为营养和水分的东西是什么呢？那就是：<b>找出孩子的优点并加以表扬。</b>该批评的时候就批评，该表扬的时候应好好表扬。在心里定下批评一次、表扬三次的"一比三"标准。受到表扬的孩子心情愉快，自信及自尊心也会随之得到发展。'
              ]
            },
            {
              title: '有了理想人会变得坚强',
              section: [
                '有这样一种说法，现在的孩子没有热情，对将来不抱幻想和希望，对艰难的目标还未挑战就先放弃，等等。但是，孩子有孩子自己的梦想与希望，不管多么微不足道，和多么滑稽可笑，作为父母，都要静心倾听孩子讲述他的梦想和希望。还有，父母要抓住时机给孩子讲讲自己的经验，讲讲那些经过长期艰苦奋斗而最后实现了自己理想的人们的生活经验。教育孩子人生的目标是在出大力与流大汗，以及不断重复失败过程中达到的。父母要密切关注孩子的成长，永远作为孩子的“母港”与"大后方"。 '
              ]
            },
            {
              title: '没有一个孩子跟别的孩子完全一样',
              section: [
                '孩子记住了多少经典或诗词，已经学会了多少东西，等等。有的父母只将眼光落在自己的孩子跟人家的孩子相比，并以自己的期望值来评价孩子。这对孩子的个性及孩子的成长与发展不仅没有任何好处，相反会造成极大的心理伤害。在这样的父母教育下，孩子会变得容易失去自信。把自己的孩子跟其他孩子比较以后，不要坐立不安。不管什么时候都要相信自己孩子的个性、相信孩子的成长。耐心地、慢慢地、有效地培养自己的孩子。'
              ]
            },
            {
              title: '"拔苗助长"要不得',
              section: [
                '对孩子进行早期智力教育无可非议。但是，让孩子从很小的时候起就学这学那的，等到上了小学、中学，不少孩子就会因感到疲惫不堪而“厌学”。在那种老把自己的孩子跟别人家孩子相比，并想早出成果而焦躁不安的家庭气氛中，孩子的心理与人格发展容易被扭曲。而且，过早地让孩子学习，也减少了对幼儿的发展来说很重要的游戏及各种各样的体验机会，不利于孩子的发展。',
                '应正确地看待孩子，充分认识根据孩子个性、以充足的时间踏踏实实地培养孩子的重要性。'
              ]
            },
            {
              title: '没有人是完美无缺的',
              section: [
                '完美主义的父母总希望自己的孩子能做到完美无缺。一旦孩子出现小小的失败或一丁点儿过错，父母马上变得神经过敏、心理紧张、焦虑不安。长期下去可能导致身心虐待孩子等。孩子没有完全照着父母期望的那样去做，这是很自然的事。大方向对了就行，不必太拘小节，这一点很重要。自然地养育孩子更能使孩子茁壮成长。',
              ]
            }
          ]
        },
        {
          title: '五、游戏',
          infos: [
            {
              title: '游戏就是孩子的“正事儿”',
              section: [
                '游戏对孩子心理发展非常重要。特别是在幼儿期，孩子通过游戏运用感知进行活动、想像、动手制作东西。孩子的学习特点就是<b>直接感知，实际操作和亲身体验</b>。',
                '现在，你到处都可以见到一些喜欢玩手机、看电视而不爱玩玩具，喜欢粘着妈妈而不会和其他孩子玩、在户外不知道怎么玩的孩子。 应该认识到，游戏对孩子来说是多么重要，应该让孩子自由自在地、轻松愉快地玩。'
              ]
            },
            {
              title: '常说真累啊的孩子多起来了',
              section: [
                '现在的孩子从很小的时候起就被时间赶着跑，游戏的时间也被削减，不能悠闲地玩的孩子里，有不少人动不动就觉得累，没有什麽事也会觉得烦和不开心。只有给予孩子充分自由的时间，孩子才会从心里感到游戏的乐趣，去创造、发展游戏，从而使孩子的个性及创造能力得到发展。父母应该鼓起勇气，给孩子充足的时间和充分的自由。',
              ]
            },
            {
              title: '人生需要的东西是从自然中学得的',
              section: [
                '看电视、玩手机等室内游戏在不断增加，而在大自然中进行的游戏正在不断减少。鼓励孩子去野外游戏，并带领孩子到大自然中去，让孩子感受到接触动植物、接触自然的乐趣。 在大自然中玩，通过体验惊异、感动，不仅能培养孩子丰富的情感，也能使孩子学会爱护自然、保护自然，理解忍耐的重要性。',
              ]
            },
            {
              title: '跟其他大孩子、小孩子一起玩，远比我们想象的重要',
              section: [
                '在不同年龄层次构成的集体中，孩子能学到不少有关人际关系的知识。对小的孩子来说，通过集体生活，能理解遵守纪律、自制的重要性；对大的孩子来说，通过集体生活，能培养他们关心别人、在集体中起带头作用等责任感。 在你周围有社区义务活动。体育活动、文化活动、青少年团体活动等等。具有不同年龄层次孩子的集体，能给孩子们提供相互切磋的宝贵机会。父母要重新认识这些活动的重要性，鼓励孩子走出去积极参加。',
              ]
            },
            {
              title: '在家庭里每年进行的例行活动，意义也很重大',
              section: [
                '春节、清明节、端午节、中秋节、国庆节以及生日会等等这些家庭里的例行活动，孩子们不仅能与老年人等不同年龄层次的人交流、联络，加深亲情，也是他们关心社区、亲近传统文化的一个好机会。',
              ]
            },
            {
              title: '你的生活态度是对孩子的最好教育',
              section: [
                '迄今为止，社会要求勤劳与遵纪守法的公民。但是，从现在开始，社会要求自己思考、想方设法解决问题、自己行动、勇气与创造力十足的社会成员。为培养那种不仅考虑自己家与所在单位的利益，还放眼于社区以及整个社会，并积极参与各种活动的人。重要的是父母自身应该先理解独立思考、自我行动、刻意创新、充满勇气的重要性，并积极地去努力、去挑战。',
              ]
            }
          ]
        }
      ],
      countList: []
    };
  },
  created() {
    this.getTreeselect();
  },
  mounted() {
    // this.$refs.list.addEventListener('scroll', this.scrollHandle);
  },
  methods: {
    onScroll(e) {
      let scrollItems = document.querySelectorAll('.list .item');
      let target = document.querySelector('.list');
      for (let i = scrollItems.length - 1; i >= 0; i--) {
        // 判断滚动条滚动距离是否大于当前滚动项可滚动距离
        let judge =
          e.target.scrollTop >=
          scrollItems[i].offsetTop - scrollItems[0].offsetTop - 24;
        const aa = target.scrollHeight - e.target.scrollTop;
        if (judge) {
          this.currentIndex = i + 1;
          return;
        }
      }
    },
    // tab click
    changeNav(index) {
      let target = document.querySelector('.list');
      
      let scrollItems = document.querySelectorAll('.list .item');
      // 判断滚动条是否滚动到底部
      if (target.scrollHeight <= target.scrollTop + target.clientHeight) {
        this.currentIndex = index;
      }
      let totalY = scrollItems[index - 1].offsetTop - scrollItems[0].offsetTop; // 锚点元素距离其offsetParent(这里是body)顶部的距离(待滚动的距离)
      let distance = document.querySelector('.list').scrollTop; // 滚动条距离滚动区域顶部的距离
      // let distance = document.body.scrollTop || document.documentElement.scrollTop || window.pageYOffset // 滚动条距离滚动区域顶部的距离(滚动区域为窗口)
      // 滚动动画实现, 使用setTimeout的递归实现平滑滚动，将距离细分为50小段，10ms滚动一次
      // 计算每一小段的距离
      let step = totalY / 50;
      if (totalY > distance) {
        smoothDown(document.querySelector('.list'));
      } else {
        let newTotal = distance - totalY;
        step = newTotal / 50;
        smoothUp(document.querySelector('.list'));
      }

      // 参数element为滚动区域
      function smoothDown(element) {
        if (distance < totalY) {
          distance += step;
          element.scrollTop = distance;
          setTimeout(smoothDown.bind(this, element), 10);
        } else {
          element.scrollTop = totalY;
        }
      }

      // 参数element为滚动区域
      function smoothUp(element) {
        if (distance > totalY) {
          distance -= step;
          element.scrollTop = distance;
          setTimeout(smoothUp.bind(this, element), 10);
        } else {
          element.scrollTop = totalY;
        }
      }
    },
    // changeNav(item) {
    //   let target = document.querySelector('.list');
    //   // 判断滚动条是否滚动到底部
    //   if (target.scrollHeight <= target.scrollTop + target.clientHeight) {
    //     this.currentIndex = item;
    //   }

    //   this.$refs['item' + item][0].scrollIntoView();
    // },
    /** 查询部门下拉树结构 */
    getTreeselect() {
      treeselect().then(response => {
        this.treeOptions = response.data;
        //console.log(response.data);
      });
    },
  }
};
</script>
<style lang="scss" scoped>
.app-container,
.main {
  height: 100%;
}
.swiper-container ::v-deep.swiper-slide {
  width: auto;
}
.list-btn {
  padding-bottom: 20px;
  .doctor-nav .swiper-slider {
    width: 100px;
  }
  .content {
    background: #eee;
    border-radius: 5px;
    margin: 0 5px;
    padding: 8px 20px;
    font-size: 14px;
    cursor: pointer;
    &.active {
      background: #409eff;
      color: #fff;
    }
  }
}
.main-list {
  height: calc(100% - 52px);
  overflow: hidden;
  .list {
    overflow-y: auto;
    height: 100%;
    .item {
      margin-bottom: 45px;
    }
    .list-title {
      font-size: 18px;
      margin: 16px 0;
      text-align: center;
    }
    .title {
      margin: 10px 0 15px;
      .line {
        position: relative;
        &::before {
          content: '';
          position: absolute;
          left: 16px;
          right: 50%;
          bottom: 0;
          width: 100%;
          border-bottom: 2px solid #409eff;
        }
      }

      .num {
        width: 26px;
        height: 26px;
        font-style: normal;
        text-align: center;
        line-height: 22px;
        font-size: 14px;
        border-radius: 50%;
        font-size: 14px;
        color: #409eff;
        border: 2px solid #409eff;
      }
      h3 {
        padding: 0 8px;
        font-size: 14px;
        margin: 0;
        color: #304156;
      }
    }
    .content {
      margin: 0 20px;
      background: #ccc;
      border-radius: 10px;
      padding: 10px 15px;
      background: #fdfdfd;
      box-shadow: 0 0 0 1px hsla(0, 0%, 100%, 0.3) inset,
        0 3px 10px rgba(0, 0, 0, 0.2);
      .articles {
        text-indent: 2em;
        font-size: 14px;
        line-height: 22px;
        color: #666;
        margin: 7px 0;
      }
    }
  }
}
</style>
