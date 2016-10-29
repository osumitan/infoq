package jp.gr.java_conf.osumitan.infoq.site;

import java.util.regex.Pattern;

import org.openqa.selenium.By;

/**
 * アンケートサイト：n-research.jp
 */
public class NResearchSite extends EnqueteSite {

	/**
	 * コンストラクタ
	 */
	public NResearchSite() {
		super();
		// ドメイン
		super.domain = "photo-enquete.com";
		// ドメインパターン
		super.domainPattern = Pattern.compile("^.*n-research.jp\\.*$");
		// 次へボタンセレクタ
		super.nextButtonSelector = By.xpath("//input[@type='button' and (@value='回答する' or @value='次へ')]");
		//TODO 最終テキストパス
		super.finalTextPath = By.xpath("//img[contains(@src,'pointget')]");
		//TODO 最終ボタンセレクタ
		super.finalButtonSelector = By.xpath("//input[@type='image' and contains(@src,'btn_finish')]");;
		// ラジオボタンセレクタ
		super.radioButtonSelector = By.xpath("//input[@type='radio']");
		//TODO チェックボックスセレクタ
		super.checkBoxSelector = By.xpath("//input[@type='checkbox']/ancestor::li/label[contains(@for,'que')]");
		//TODO オプションセレクタ
		super.optionSelector = By.cssSelector("select option");
		//TODO 性別質問パス
		super.genderQuestionPath = By.xpath("//*[contains(text(),'あなたの性別をお知らせ下さい')]");
		//TODO 性別回答パス
		super.genderAnswerPath = By.xpath("//input[@type='radio' and @value='1']/ancestor::li/label[contains(@for,'que')]");
		//TODO 年齢質問パス
		super.ageQuestionPath = By.xpath("//*[contains(text(),'あなたの年齢をお知らせ下さい')]");
		//TODO 年齢回答パス
		super.ageAnswerPath = By.xpath("//input[@type='radio' and @value='4']/ancestor::li/label[contains(@for,'que')]");
		//TODO フロート広告クローズボタンセレクタ
		super.floatAdCloseButtonSelector = By.cssSelector("#inter-close");
		//TODO フロート広告クローズスクリプト
		super.floatAdCloseScript = "document.getElementById('inter-close').click();";
		//TODO アクションのたびに待つ
		this.toWaitBeforeEveryAction = true;
		//TODO ドメインが変わるかチェック
		super.checkDomainChanged = true;
		//TODO ドメインが変わったときのスクリプト
		super.domainChangedScript = "window.history.back();";
	}
}
