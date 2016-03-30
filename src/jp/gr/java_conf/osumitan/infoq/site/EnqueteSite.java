package jp.gr.java_conf.osumitan.infoq.site;

import org.openqa.selenium.By;

/**
 * アンケートサイト
 */
public abstract class EnqueteSite {

	/** ドメイン */
	protected String domain;
	/** IFRAMEセレクタ */
	protected By iframeSelector;
	/** 次へボタンセレクタ */
	protected By nextButtonSelector;
	/** 最終テキストパス */
	protected By finalTextPath;
	/** 最終ボタンセレクタ */
	protected By finalButtonSelector;
	/** ラジオボタンセレクタ */
	protected By radioButtonSelector;
	/** チェックボックスセレクタ */
	protected By checkBoxSelector;
	/** オプションセレクタ */
	protected By optionSelector;
	/** 性別質問パス */
	protected By genderQuestionPath;
	/** 性別回答パス */
	protected By genderAnswerPath;
	/** 年齢質問パス */
	protected By ageQuestionPath;
	/** 年齢回答パス */
	protected By ageAnswerPath;
	/** 居住地質問パス */
	protected By residenceQuestionPath;
	/** 居住地回答パス */
	protected By residenceAnswerPath;
	/** 職業質問パス */
	protected By jobQuestionPath;
	/** 職業回答パス */
	protected By jobAnswerPath;
	/** ブラックアンケートパス */
	protected By blackEnquetePath;

	/**
	 * @return domain
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * @return iframeSelector
	 */
	public By getIframeSelector() {
		return iframeSelector;
	}

	/**
	 * @return nextButtonSelector
	 */
	public By getNextButtonSelector() {
		return nextButtonSelector;
	}

	/**
	 * @return finalTextPath
	 */
	public By getFinalTextPath() {
		return finalTextPath;
	}

	/**
	 * @return finalButtonSelector
	 */
	public By getFinalButtonSelector() {
		return finalButtonSelector;
	}

	/**
	 * @return radioButtonSelector
	 */
	public By getRadioButtonSelector() {
		return radioButtonSelector;
	}
	/**
	 * @return checkBoxSelector
	 */
	public By getCheckBoxSelector() {
		return checkBoxSelector;
	}

	/**
	 * @return optionSelector
	 */
	public By getOptionSelector() {
		return optionSelector;
	}

	/**
	 * @return genderQuestionPath
	 */
	public By getGenderQuestionPath() {
		return genderQuestionPath;
	}

	/**
	 * @return genderAnswerPath
	 */
	public By getGenderAnswerPath() {
		return genderAnswerPath;
	}

	/**
	 * @return ageQuestionPath
	 */
	public By getAgeQuestionPath() {
		return ageQuestionPath;
	}

	/**
	 * @return ageAnswerPath
	 */
	public By getAgeAnswerPath() {
		return ageAnswerPath;
	}

	/**
	 * @return residenceQuestionPath
	 */
	public By getResidenceQuestionPath() {
		return residenceQuestionPath;
	}

	/**
	 * @return residenceAnswerPath
	 */
	public By getResidenceAnswerPath() {
		return residenceAnswerPath;
	}

	/**
	 * @return jobQuestionPath
	 */
	public By getJobQuestionPath() {
		return jobQuestionPath;
	}

	/**
	 * @return jobAnswerPath
	 */
	public By getJobAnswerPath() {
		return jobAnswerPath;
	}

	/**
	 * @return blackEnquetePath
	 */
	public By getBlackEnquetePath() {
		return blackEnquetePath;
	}
}
