/**
 * 編集ボタン　クリック
 */
$("#task-table-body").on("click", ".edit-task-button", function() {
    const row = $(this).closest("tr");

    // 編集中の場合は処理中断
    if (row.hasClass("isEditing")) {
        resetEdit(row);
        return;
    }

    // 表示用文字を隠し、入力欄を表示
    row.find(".title-display").prop("hidden", true);
    row.find(".title-input").prop("hidden", false);

    row.find(".category-display").prop("hidden", true);
    row.find(".category-input").prop("hidden", false);

    row.find(".dueDate-display").prop("hidden", true);
    row.find(".dueDate-input").prop("hidden", false);

    // 編集中フラグをONにする
    row.addClass("isEditing");
    // タイトル欄へフォーカスを当てる
    row.find(".title-input").trigger("focus");
});

/**
 * 編集モード解除
 */
function resetEdit(row) {
    // 更新中は処理中断
    if (row.data("isUpdating")) {
        return;
    }

    row.find(".title-input").val(row.find(".title-display").text());
    row.find(".category-input").val(row.find(".category-display").text());
    row.find(".dueDate-input").val(row.find(".dueDate-display").text());
    // 入力エラーを消し、編集モードを解除
    row.find(".title-error, .category-error, .dueDate-error").text("");
    row.removeClass("isEditing");

    // 入力欄を隠し、表示用の文字を再表示
    row.find(".title-input, .category-input, .dueDate-input").prop("hidden", true);
    row.find(".title-display, .category-display, .dueDate-display").prop("hidden", false);
}

/**
 * 編集中（isEditing）の行のフォーカスアウトが外れた場合に更新の通信をする
 */
$("#task-table-body").on("focusout", "tr.isEditing", function(event) {
    //　同じ行の中へ移動した場合は、更新しない（タイトル欄からカテゴリ欄など） 
    if (this.contains(event.relatedTarget)) {
        return;
    }

    const row = $(this)
    // 既に更新中なら更新しない
    if (row.data("isUpdating")) {
        return;
    }
    row.data("isUpdating", true);

    // 前回のエラーメッセージをクリア
    row.find(".title-error, .category-error, .dueDate-error").text("");

    const task = {
        id: Number(row.find(".task-id").text()),
        title: row.find(".title-input").val(),
        category: row.find(".category-input").val(),
        dueDate: row.find(".dueDate-input").val() || null,
        version: Number(row.attr("data-version"))
    };

    $.ajax({
        url: "/task/update",
        type: "PUT",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify(task)
    })
        .done(function(updatedTask) {
            row.find(".title-display").text(updatedTask.title);
            row.find(".category-display").text(updatedTask.category);
            row.find(".dueDate-display").text(updatedTask.dueDate || "");
            row.attr("data-version", updatedTask.version);

            row.find(".title-input, .category-input, .dueDate-input").prop("hidden", true);
            row.find(".title-display, .category-display, .dueDate-display").prop("hidden", false);
            // トースト表示
            $(".toast-message.toast-success").remove();
            const toast = $("<div>")
                .addClass("toast-message toast-success")
                .attr("role", "status")
                .text("更新しました。");

            $("body").append(toast);
            removeToast(toast);

            row.data("isUpdating");
            row.removeClass("isEditing");
        })
        .fail(function(response) {
            // 入力に検証エラーがある場合は、エラーメッセージを表示
            if (response.status === 400 && response.responseJSON) {
                const errors = response.responseJSON;
                row.find(".title-error").text(errors.titleErrorMessage || "");
                row.find(".category-error").text(errors.categoryErrorMessage || "");
                row.find(".dueDate-error").text(errors.dueDateErrorMessage || "");
                return;
            }
            // 更新に失敗した場合は、エラー表示
            if (response.status === 409) {
                row.find(".title-error").text("他の利用者によって変更または削除されています。");
                return;
            }
            // 上記以外のエラー
            row.find(".title-error").text("更新に失敗しました。");
        })
        .always(function() {
            row.data("isUpdating", false);
        })
});

/**
 * 削除ボタン
 */
$("#task-table-body").on("click", ".delete-task-button", function() {
    const row = $(this).closest("tr");
    // 削除中であれば削除しない
    if (row.data("isDeleting")) {
        return;
    }
    
    row.data("isDeleting", true);

    const data = {
        id: Number(row.find(".task-id").text()),
        version: Number(row.attr("data-version"))
    };

    $.ajax({
        url: "/task/delete",
        type: "DELETE",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify(data)
    })
        .done(function() {
            row.remove();
        })
        .fail(function(response) {
            // 既に削除済みであればエラー
            if (response.status === 409) {
                row.find(".title-error").text("他の利用者によって変更または削除されています。");
                return;
            }
            row.find(".title-error").text("削除に失敗しました。");
        })
        .always(function() {
            row.data("isDeleting", false);
        });
});

/**
 * トーストを3秒後に削除する
 */
function removeToast(toast) {
    setTimeout(function() {
        toast.remove();
    }, 3000);
}
// 新規登録の成功時のトーストを削除
removeToast($(".toast-message"));
