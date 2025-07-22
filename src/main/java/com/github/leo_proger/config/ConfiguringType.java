package com.github.leo_proger.config;


public enum ConfiguringType {

	LOT_SIZE(
			2,
			"Нужно будет сначала нажать на левый верхний угол 1го лота, а затем на правый нижний угол также 1го лота"
	),
	BUY_BUTTON_X(1, "Нужно будет нажать на любую кнопку покупки лота"),
	CONFIRM_PURCHASE_BUTTON(
			1,
			"Сначала нужно в эмуляторе нажать на любую кнопку покупки лота, затем вернуться сюда, нажать Enter, а потом нажать на кнопку подтверждения покупки"
	),
	REFRESH_BUTTON(1, "Нужно будет нажать на чекбокс \"Только мои запросы\"");

	private final int clicks;
	private final String message;

	ConfiguringType(int clicks, String message) {
		this.clicks = clicks;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public int getClicks() {
		return clicks;
	}
}
