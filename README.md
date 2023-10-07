[![](https://jitpack.io/v/hundun000/umamusume-simulation.svg)](https://jitpack.io/#hundun000/umamusume-simulation)

## 开发中

## 用法1：单场比赛模拟

游戏 《赛马娘 Pretty Derby》 比赛模拟。

公式和数值参考原作游戏的解包研究。以模拟的游戏帧进行一场比赛，即模拟赛马每一帧时的位移量，速度，加速度。

### 用法1.1：console输出文字

进一步的，输出的语言可以选择中文/英文。

UmamusumeAppTest.testCharImageDisplayerChinese() 或 UmamusumeAppTest.testCharImageDisplayerEnglish()。
```
[tick 0] 樱花赏 1600米
赛道1: 无声铃鹿A 逃  速800，耐400，力600，根600，智200
赛道2: 黄金船A 追  速800，耐400，力600，根600，智200
赛道3: 草上飞A 差  速800，耐400，力600，根600，智200
赛道4: 特别周A 先  速800，耐400，力600，根600，智200

[tick 59] 00:00.59 无声铃鹿A率先进入初期巡航阶段
[o          ] 6m
馬无声铃鹿A -----------> 
馬黄金船A  -> 
馬草上飞A  -> 
馬特别周A  -> 

[tick 1408] 00:14.08 无声铃鹿A率先进入中期巡航阶段
[=o         ] 257m
馬无声铃鹿A -----------> 
馬黄金船A  -> 
馬草上飞A  --> 
馬特别周A  ---------> 

[tick 3413] 00:34.13 无声铃鹿A率先进入中期巡航(过半)阶段
[====o      ] 661m
馬无声铃鹿A -----------> 
馬黄金船A  -> 
馬草上飞A  -> 
馬特别周A  ----> 

[tick 5405] 00:54.05 黄金船A率先进入末期巡航阶段
[======o    ] 1058m
馬无声铃鹿A -------> 
馬黄金船A  -----------> 
馬草上飞A  -------> 
馬特别周A  -> 

[tick 6832] 01:08.32 无声铃鹿A率先进入末期冲刺阶段
[tick 7281] 01:12.81 草上飞A进入末期冲刺阶段
[tick 7367] 01:13.67 特别周A进入末期冲刺阶段
[tick 7460] 01:14.60 黄金船A最晚进入末期冲刺阶段
[=========o ] 1492m
馬无声铃鹿A ---> 
馬黄金船A  -----------> 
馬草上飞A  --------> 
馬特别周A  -> 

[tick 7871] 01:18.71 黄金船A率先冲线
[=========o ] 1584m
馬无声铃鹿A ------> 
馬黄金船A  冲线时间：01:18.71
馬草上飞A  ---------> 
馬特别周A  -> 

[tick 7882] 01:18.82 草上飞A冲线
[tick 7902] 01:19.02 无声铃鹿A冲线
[tick 7940] 01:19.40 特别周A最晚冲线
[==========o] 1600m
馬无声铃鹿A 冲线时间：01:19.02
馬黄金船A  冲线时间：01:18.71
馬草上飞A  冲线时间：01:18.82
馬特别周A  冲线时间：01:19.40
```

### 用法1.2：swing-GUI输出赛场

UmamusumeAppTest.testGuiDisplayerChinese()。

![](./docs/gui演示_0.jpg)  
![](./docs/gui演示_1.jpg)  

## 用法2：养成游戏

玩法参考原作游戏的养成系统。在训练日时选择一种训练方式，增加自身属性；在比赛日时和其他马比赛，获得奖金。

### 用法2.1：console输出文字

仅供演示。在训练日中选择了某种锻炼，属性得到提升。

DemoGameplayFrontendTest.test()。

```
playerHorse 速700, 耐500, 力500, 根400, 智200
[ConsoleGameplayApp]notifiedModifiedResourceNum
[ConsoleGameplayApp]notifiedModifiedResourceNum
[UmaGameplayManager]train done, gain = [GameResourcePair(type=HORSE_SPEED, amount=5), GameResourcePair(type=HORSE_STAMINA, amount=5), GameResourcePair(type=HORSE_POWER, amount=5)]
[ConsoleGameplayApp]notifiedModifiedResourceNum
[UmaGameplayManager]state change to: TRAIN_DAY
playerHorse 速705, 耐505, 力505, 根400, 智200
```

### 用法2.2：libgdx游戏

[libgdx-TextUma项目](https://github.com/hundun000/libgdx-TextUma)

![](./docs/TextUma.png)  

