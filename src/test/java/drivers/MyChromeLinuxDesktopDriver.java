package drivers;

import dto.MainDto;
import enums.PlatformTypes;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.netty.handler.codec.http.HttpResponse;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.filters.ResponseFilter;
import net.lightbody.bmp.filters.ResponseFilterAdapter;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.SystemUtils;

import java.io.File;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.HashMap;

public class MyChromeLinuxDesktopDriver extends MyDriverRemote {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyChromeLinuxDesktopDriver.class);
    private MainDto mainDto;
    private ChromeDriver chromeDriver;
    private DevTools devTools;
    private WebDriverManager wdm;

    public MyChromeLinuxDesktopDriver(MainDto mainDto) {
        this.mainDto = mainDto;
        LOGGER.debug("creating new linux chrome browser");
        wdm = WebDriverManager.chromedriver();
        wdm.setup();
        try{
            chromeDriver = new ChromeDriver(chromeOptions());
            this.mainDto.setDriver(chromeDriver);
        }
        catch (SessionNotCreatedException e){
            LOGGER.debug("SessionNotCreatedException occurred! will retry to create session!");
            chromeDriver = new ChromeDriver(chromeOptions());
            this.mainDto.setDriver(chromeDriver);
            LOGGER.debug("new session success!");
        }
//        System.setProperty("webdriver.chrome.silentOutput", "true");
//        System.setProperty("webdriver.chrome.whitelistedIps", ""); //for docker runs
//        devTools = chromeDriver.getDevTools();
//        devTools.createSession(); //this for tracing
//        mainDto.setDevTools(devTools);

    }

    private ChromeOptions chromeOptions(){
        LOGGER.debug("setting chrome options");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"}); //for chrome not to detect automation
        if(Boolean.parseBoolean(SystemUtils.getConfig("is.headless"))) {
            options.addArguments("--headless");
        }
        options.addArguments("--disable-dev-shm-usage");
        options.setExperimentalOption("detach", true);
        options.addArguments("disable-infobars","--window-size=1920,1080");
        options.setAcceptInsecureCerts(true);
        //TODO please allow me to leave these configs here as comment as we need them later\
//        String driverPath = WebDriverManager.chromedriver().getDownloadedDriverPath();
//        LOGGER.debug("driverPath = {}", driverPath);
//        options.setBinary(driverPath);
//        Proxy proxy = new Proxy();
//        proxy.setProxyType(Proxy.ProxyType.DIRECT);
//        proxy.setHttpProxy("telkomsel-mc5e32iz.billfazz.com");
//        proxy.setSslProxy("yoururl:portno");
//        options.setCapability("proxy", proxy);
//        options.addArguments("--incognito");
//        options.addArguments("--disable-gpu");
//        options.addArguments("--mute-audio");
//        options.addArguments("--ignore-certificate-errors");
//        options.addArguments("--ignore-ssl-errors");
//        options.addArguments("--disable-infobars");
//        options.addArguments("--ignore-certificate-errors-spki-list");
//        options.addArguments("--no-sandbox");
//        options.addArguments("--no-zygote");
//        options.addArguments("--log-level=3");
//        options.addArguments("--allow-running-insecure-content");
//        options.addArguments("--disable-web-security");
//        options.addArguments("--disable-features=VizDisplayCompositor");
//        options.addArguments("--disable-breakpad");
//        Map<String, Object> localState = new HashMap<String, Object>();
//        localState.put("dns_over_https.mode", "off");
//        localState.put("dns_over_https.templates", "");
//        options.setExperimentalOption("localState", localState) ;
//        options.setCapability(CapabilityType.PLATFORM_NAME, Platform.LINUX);
//        options.addArguments("--disable-notifications");
//        options.addArguments("--headless");
//        options.addArguments("--whitelisted-ips");
//        options.addArguments("--no-sandbox");
//        options.addArguments("--disable-extensions");
//        Proxy seleniumProxy = getSeleniumProxy(getProxyServer());
//        options.setCapability(CapabilityType.PROXY, seleniumProxy);
//        String extraOptions = System.getProperty("chromeOptions");
//        if (extraOptions != null) {
//            options.addArguments(extraOptions);
//        }
//
//        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
//        options.setExperimentalOption("useAutomationExtension", false);
        // set download path
        String downloadFilepath = System.getProperty("user.dir") + File.separator + "downloads";
        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", downloadFilepath);
        options.setExperimentalOption("prefs", chromePrefs);
        mainDto.setPlatformType(PlatformTypes.UNIX);
        setOption(options);
        return options;
    }

    private BrowserMobProxy getProxyServer() {
        BrowserMobProxy proxy = new BrowserMobProxyServer();
        proxy.setTrustAllServers(true);
// above line is needed for application with invalid certificates
        proxy.start(0);
        proxy.addFirstHttpFilterFactory(new ResponseFilterAdapter.FilterSource(new ResponseFilter() {
            @Override
            public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
            }
        }, Integer.MAX_VALUE));
        return proxy;
    }

    private Proxy getSeleniumProxy(BrowserMobProxy proxyServer) {
        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxyServer);
        try {
            String hostIp = Inet4Address.getLocalHost().getHostAddress();
            seleniumProxy.setHttpProxy(hostIp+":" + proxyServer.getPort());
            seleniumProxy.setSslProxy(hostIp+":" + proxyServer.getPort());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return seleniumProxy;
    }
}
