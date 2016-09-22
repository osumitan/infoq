package jp.gr.java_conf.osumitan.infoq.site;

import java.util.regex.Pattern;

import org.openqa.selenium.By;

/**
 * アンケートサイト：r8.hikikagami.com
 */
public class HikikagamiR8Site extends InfoPanelSite {

	/**
	 * コンストラクタ
	 */
	public HikikagamiR8Site() {
		super();
		// ドメイン
		super.domain = "r8.hikikagami.com";
		// ドメインパターン
		super.domainPattern = Pattern.compile("^.*r[0-9]\\.hikikagami\\.com.*$");
		// 次へボタンセレクタ
		super.nextButtonSelector = By.xpath("//input[@value='次　へ']");
	}
}
