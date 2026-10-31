# خروجی‌های انتشار فروشگاهی متنویس

## خروجی‌های کافه‌بازار
Workflow نسخهٔ امضاشده را می‌سازد و در کنار AAB، فایل BIN موردنیاز فرایند انتشار App Bundle کافه‌بازار را با ابزار رسمی `cafebazaar/bundle-signer` تولید می‌کند.

فایل‌های خروجی:
- `app/build/outputs/bundle/release/app-release.aab`
- `app/build/outputs/cafebazaar/app-release.bin`

در GitHub Actions این خروجی‌ها به صورت Artifactهای جداگانه با نام‌های زیر در دسترس‌اند:
- `matnnevis-release-aab`
- `matnnevis-cafebazaar-bin`

فایل BIN از AAB امضاشده و همان keystore انتشار تولید می‌شود؛ تغییر پسوند APK/AAB به BIN معتبر نیست. در پنل توسعه‌دهندگان بازار، AAB و BIN متناظر با همان نسخه را طبق فرایند پنل بارگذاری کنید.

## سایر قالب‌های اندروید
- APK: بستهٔ نصب اندروید برای نصب و تست مستقیم؛ Workflow نسخهٔ Release امضاشده را نیز تولید می‌کند.
- AAB: قالب Android App Bundle برای انتشار فروشگاهی؛ در بازار برای این مسیر، فایل BIN متناظر نیز تولید می‌شود.

## امضای نسخه انتشار
برای امضای Release و تولید BIN این GitHub Secrets را تعریف کنید:
- `ANDROID_KEYSTORE_BASE64`: محتوای فایل keystore به صورت Base64
- `ANDROID_KEYSTORE_PASSWORD`
- `ANDROID_KEY_ALIAS` (اختیاری؛ در صورت تنظیم، alias داخل keystore؛ مقدار پیش‌فرض پروژه `upload` است.)
- `ANDROID_KEY_PASSWORD`

فایل keystore و رمزها نباید commit شوند یا در کد قرار بگیرند. تا وقتی سه Secret اصلی keystore/password تنظیم نشده‌اند، خروجی‌های Release امضاشده و BIN قابل تولید نیستند. کلید امضا باید همان کلیدی باشد که برای به‌روزرسانی نسخهٔ قبلی برنامه استفاده شده است.

## خروجی تست
Workflow خروجی Debug APK و گزارش تست را به صورت GitHub Actions Artifact منتشر می‌کند. خروجی Debug برای تست داخلی است و جایگزین نسخهٔ Release امضاشده فروشگاهی نیست.
