package jp.gr.java_conf.osumitan.infoq.site;

import java.util.regex.Pattern;

import org.openqa.selenium.By;

/**
 * アンケートサイト：photo-enquete.com
 */
public class PhotoEnqueteSite extends EnqueteSite {

	/**
	 * コンストラクタ
	 */
	public PhotoEnqueteSite() {
		super();
		// ドメイン
		super.domain = "photo-enquete.com";
		// ドメインパターン
		super.domainPattern = Pattern.compile("^.*(photo-enquete\\.com|cosme-beaute\\.com).*$");
		// 次へボタンセレクタ
		super.nextButtonSelector = By.xpath("//input[(@type='image' and contains(@src,'btn_next')) or (@type='submit' and contains(@class,'enquete_nextbt'))]");
		// 最終テキストパス
		super.finalTextPath = By.xpath("//img[contains(@src,'pointget')]");
		// 最終ボタンセレクタ
		super.finalButtonSelector = By.xpath("//input[@type='image' and contains(@src,'btn_finish')]");;
		// ラジオボタンセレクタ
		super.radioButtonSelector = By.xpath("//input[@type='radio']/ancestor::li/label[contains(@for,'que')]");
		// チェックボックスセレクタ
		super.checkBoxSelector = By.xpath("//input[@type='checkbox']/ancestor::li/label[contains(@for,'que')]");
		// オプションセレクタ
		super.optionSelector = By.cssSelector("select option");
		// 性別質問パス
		super.genderQuestionPath = By.xpath("//*[contains(text(),'あなたの性別をお知らせ下さい')]");
		// 性別回答パス
		super.genderAnswerPath = By.xpath("//input[@type='radio' and @value='1']/ancestor::li/label[contains(@for,'que')]");
		// 年齢質問パス
		super.ageQuestionPath = By.xpath("//*[contains(text(),'あなたの年齢をお知らせ下さい')]");
		// 年齢回答パス
		super.ageAnswerPath = By.xpath("//input[@type='radio' and @value='4']/ancestor::li/label[contains(@for,'que')]");
		// フロート広告クローズボタンセレクタ
		super.floatAdCloseButtonSelector = By.cssSelector("#inter-close");
		// フロート広告クローズスクリプト
		super.floatAdCloseScript = "document.getElementById('inter-close').click();";
		// アクションのたびに待つ
		this.toWaitBeforeEveryAction = true;
	}
}
