/**
 * 利用者のライト・ダーク設定を、一覧と登録モーダルの共通テーマに反映する。
 * jQueryや画面の要素を待たずに実行できるため、head内でdeferを付けずに読み込む。
 */
(function() {
    // ブラウザが認識している、利用者のダークモード設定を取得する。
    const colorScheme = window.matchMedia("(prefers-color-scheme: dark)");

    function applyTheme() {
        // htmlに設定すると、その中の一覧・モーダル両方にBootstrapのテーマが適用される。
        document.documentElement.setAttribute(
            "data-bs-theme", colorScheme.matches ? "dark" : "light"
        );
    }

    // 初回表示のテーマを、画面が描画される前に設定する。
    applyTheme();

    // ページを開いたまま利用者が明暗設定を変更した場合も、再読み込みなしで反映する。
    colorScheme.addEventListener("change", applyTheme);
})();
