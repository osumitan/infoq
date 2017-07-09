package jp.gr.java_conf.osumitan.infoq;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.google.common.base.Function;

import jp.gr.java_conf.osumitan.infoq.host.ECNaviHost;
import jp.gr.java_conf.osumitan.infoq.host.Host;
import jp.gr.java_conf.osumitan.infoq.host.InfoQHost;
import jp.gr.java_conf.osumitan.infoq.host.QzooHost;

/**
 * infoQメイン
 */
public class Main {

	/** ホストリスト */
	private List<Function<RemoteWebDriver, Host>> hostList;

	/**
	 * メイン
	 * @param args 引数
	 */
	public static void main(String[] args) {
		try {
			new Main().start(args != null && args.length >= 1 ? args[0] : null);
		} catch(Throwable e) {
			e.printStackTrace();
		}
	}

	/**
	 * コンストラクタ
	 */
	public Main() {
		// ホストリスト
		this.hostList = Arrays.asList(ECNaviHost::new, QzooHost::new, InfoQHost::new);
	}

	/**
	 * スタート
	 * @param prm パラメータ
	 */
	private void start(String prm) {
		// ドライバ初期化
		RemoteWebDriver driver = initDriver();
		// ホスト分ループ
		for(Function<RemoteWebDriver, Host> constructor : this.hostList) {
			Host host = constructor.apply(driver);
			int index = this.hostList.indexOf(constructor);
			if(prm == null || index < prm.length() && prm.charAt(index) == '1') {
				System.out.println(host.getClass().getName() + ":start");
				host.start();
				System.out.println(host.getClass().getName() + ":end");
			} else {
				System.out.println(host.getClass().getName() + ":skip");
			}
		}
		// ウィンドウを閉じる
		driver.quit();
	}

	/**
	 * ドライバ初期化
	 * @return ドライバ
	 */
	private RemoteWebDriver initDriver() {
		System.setProperty("webdriver.chrome.driver", "./driver/chromedriver");
		RemoteWebDriver driver = new ChromeDriver();
		driver.manage().window().setSize(new Dimension(800, 600));
		driver.manage().window().setPosition(new Point(520, 120));
		driver.manage().timeouts().pageLoadTimeout(20L, TimeUnit.SECONDS);
		return driver;
	}
}
