package jp.gr.java_conf.osumitan.infoq.site;

import java.util.regex.Pattern;

import org.openqa.selenium.By;

/**
 * アンケートサイト：vote.media-ad.jp
 */
public class VoteMediaSite extends EnqueteSite {

	/**
	 * コンストラクタ
	 */
	public VoteMediaSite() {
		super();
		// ドメイン
		super.domain = "vote.media-ad.jp";
		// ドメインパターン
		super.domainPattern = Pattern.compile("^.*vote\\.media-ad\\.jp.*$");
		// スタートボタンセレクタ
		super.startButtonSelector = By.cssSelector("a.start__button");
		// 次へボタンセレクタ
		super.nextButtonSelector = By.cssSelector("input.button--answer");
		// 最終テキストパス
		super.finalTextPath = By.xpath("//*[contains(@class,'point__text') and contains(.,'お疲れ様') or contains(text(),'以上で、アンケートは終了です。')]");
		// 最終ボタンセレクタ
		super.finalButtonSelector = By.xpath("//*[(contains(@class,'button--answer') and @value='回答を終了する')]");
		// ラジオボタンセレクタ
		super.radioButtonSelector = By.cssSelector("label.radio");
		// アクションのたびに待つ
		this.toWaitBeforeEveryAction = true;
		// 完了クローズボタンを待つ
		this.toWaitCompleteCloseButton = true;
	}
}
