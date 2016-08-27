package jp.gr.java_conf.osumitan.infoq.site;

import org.openqa.selenium.By;

/**
 * アンケートサイト：hikikagami.com
 */
public class HikikagamiSite extends InfoPanelSite {

	/**
	 * コンストラクタ
	 */
	public HikikagamiSite() {
		super();
		// ドメイン
		super.domain = "hikikagami.com";
		// 次へボタンセレクタ
		super.nextButtonSelector = By.xpath("//input[@value='次　へ']");
	}
}
