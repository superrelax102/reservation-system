# 画面イメージ
- ユーザー
  - ログイン画面
    - ＩＤ＆パスワード
  - トップ画面
    - 新規予約ボタン、予約履歴一覧ボタン
  - 新規予約画面
    - メニュー選択→スタッフ別orスタッフ全員の空き状況の確認カレンダー→予約日時選択→予約最終確定画面
  - 予約履歴一覧画面
    - 過去の予約一覧（日付とその時のメニューと担当スタッフ）→同じメニューを選択できるボタン→予約日時選択→予約最終確定画面

- スタッフ
  - ログイン画面
    - ＩＤ＆パスワード
  - トップ画面
    - 予約一覧カレンダー確認ボタン、お客様一覧、メニュー一覧
  - 予約一覧カレンダー確認画面
    - 一覧から特定の予約をクリックで予約詳細編集画面へ
  - お客様一覧確認画面
    - 一覧から特定のお客様クリックでお客様詳細編集画面へ
  - メニュー一覧
    - 一覧から特定のメニューでメニューの詳細編集画面へ


## E-R図
```mermaid
erDiagram
    ユーザー ||--o{ 予約 : "作成"
    スタッフ ||--o{ 予約 : "担当"
    メニュー ||--o{ 予約 : "適用"

    ユーザー {
        long user_id PK
        string user_name
        string user_email
        string user_tel
        boolean is_deleted "論理削除フラグ"
    }
    予約 {
        long reserve_id PK
        datetime start_at
        datetime end_at
        string status "確定/完了/キャンセル"
        long user_id FK
        long staff_id FK
        long menu_id FK
        int billing_fee"予約時の金額を記録（スナップショット）"
        datetime created_at
        datetime update_at
        boolean is_deleted "論理削除フラグ"
    }
    メニュー {
        long menu_id PK
        string menu_name
        string menu_detail
        int duration"30, 60, 90...（分）"
        int current_fee"現在のマスター価格"
    }

    スタッフ {
        long staff_id PK
        string staff_name
    }

```
