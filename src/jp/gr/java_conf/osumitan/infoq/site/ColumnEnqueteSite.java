package jp.gr.java_conf.osumitan.infoq.site;

import java.util.regex.Pattern;

import org.openqa.selenium.By;

/**
 * アンケートサイト：column-enquete.com
 */
public class ColumnEnqueteSite extends EnqueteSite {

	/**
	 * コンストラクタ
	 */
	public ColumnEnqueteSite() {
		super();
		// ドメイン
		super.domain = "column-enquete.com";
		// ドメインパターン
		super.domainPattern = Pattern.compile("^.*column-enquete\\.com.*$");
		// 次へボタンセレクタ
		super.nextButtonSelector = By.xpath("//input[(@type='image' and (contains(@src,'next_bt') or contains(@src,'read_column')) ) or (@type='submit' and @class='enquete_nextbt')]");
		// 最終テキストパス
		super.finalTextPath = By.cssSelector("a[href^='endlink.php']");
		// 最終ボタンセレクタ
		super.finalButtonSelector = super.finalTextPath;
		// ラジオボタンセレクタ
		super.radioButtonSelector = By.cssSelector("input[type='radio']");
		// チェックボックスセレクタ
		super.checkBoxSelector = By.cssSelector("input[type='checkbox']");
		// オプションセレクタ
		super.optionSelector = By.cssSelector("select option");
		// 性別質問パス
		super.genderQuestionPath = By.xpath("//*[contains(text(),'あなたの性別をお知らせ下さい')]");
		// 性別回答パス
		super.genderAnswerPath = By.xpath("//input[@type='radio' and @value='1']");
		// 年齢質問パス
		super.ageQuestionPath = By.xpath("//*[contains(text(),'あなたの年齢をお知らせ下さい')]");
		// 年齢回答パス
		super.ageAnswerPath = By.xpath("//input[@type='radio' and @value='4']");
		// アクションのたびに待つ
		this.toWaitBeforeEveryAction = true;
		// フロート広告クローズボタンセレクタ
		super.floatAdCloseButtonSelector = By.cssSelector("#inter-close");
		// フロート広告クローズスクリプト
		super.floatAdCloseScript = "document.getElementById('inter-close').click();";
	}
}
