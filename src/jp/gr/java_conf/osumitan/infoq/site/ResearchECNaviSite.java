package jp.gr.java_conf.osumitan.infoq.site;

import java.util.regex.Pattern;

import org.openqa.selenium.By;

/**
 * アンケートサイト：research.ecnavi.jp
 */
public class ResearchECNaviSite extends EnqueteSite {

	/**
	 * コンストラクタ
	 */
	public ResearchECNaviSite() {
		super();
		// ドメイン
		super.domain = "research.ecnavi.jp";
		// ドメインパターン
		super.domainPattern = Pattern.compile("^.*research\\.ecnavi\\.jp.*$");
		// スタートボタンセレクタ
		super.startButtonSelector = By.xpath("//input[@alt='アンケートに参加']");
		// 次へボタンセレクタ
		super.nextButtonSelector = By.cssSelector("input[value='　送信　']");
		// 最終テキストパス
		super.finalTextPath = By.cssSelector("input[value=' アンケート完了 ']");
		// 最終ボタンセレクタ
		super.finalButtonSelector = super.finalTextPath;
		// 最終質問あり
		super.hasFinalQuestion = true;
		// ラジオボタンセレクタ
		super.radioButtonSelector = By.xpath("//input[@type='radio']");
		// チェックボックスセレクタ
		super.checkBoxSelector = By.xpath("//input[@type='checkbox']");
		// テキストエリアセレクタ
		super.textAreaSelector = By.cssSelector("textarea");
		// クリック広告リンクセレクタ
		super.clickAdLinkSelector = By.xpath("//a[contains(@onclick,'doAccess')]");
		// 質問グループ属性
		this.questionGroupAttribute = "name";
		// 質問グループ識別子
		this.questionGroupIdentifier = "(\\w+)";
	}
}
