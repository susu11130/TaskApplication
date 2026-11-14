const registerModal = document.getElementById("register-modal");

// Bootstrapの表示アニメーション完了後、最初の入力エラーへフォーカスを移す
registerModal.addEventListener("shown.bs.modal", function() {
    $("#register-form .input-error").first().trigger("focus");
});

// 検証エラー時は、Bootstrapのモーダルを開き直す
if ($("#register-modal").attr("data-error") === "true") {
    bootstrap.Modal.getOrCreateInstance(registerModal).show();
}

/**
 * ID入力値を半角数値のみに制限
 */
$("#id-modal").on("input", function() {
    this.value = this.value.replace(/[^0-9]/g, "");
});

/**
 * 期限
 */
document.getElementById("dueDate").addEventListener("click", function() {
    // ブラウザがshoPickerに対応している場合はshowPickerを開く
    if (typeof this.showPicker === "function") {
        this.showPicker();
    }
});

/**
 * 登録ボタン　クリック
 */
$("#register").on("click", function() {
    const form = $("#register-form");   // JQuery idを取得

    form.attr("action", "/task/register");
    form.attr("method", "post");

    form[0].requestSubmit();
});
