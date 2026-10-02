package com.example.core.i18n

import androidx.compose.runtime.compositionLocalOf
import com.example.core.config.AppConfig

val LocalAppStrings = compositionLocalOf<AppStrings.Strings> {
    AppStrings.get(Language.FA)
}

object AppStrings {
    fun get(lang: Language): Strings {
        return when (lang) {
            Language.FA -> PersianStrings
            Language.EN -> EnglishStrings
            Language.AR -> ArabicStrings
            Language.TR -> TurkishStrings
            Language.ES -> SpanishStrings
            Language.FR -> FrenchStrings
            Language.DE -> GermanStrings
            Language.RU -> RussianStrings
        }
    }

    interface Strings {
        val appName: String get() = AppConfig.APP_NAME
        val authorName: String get() = AppConfig.AUTHOR_NAME
        val versionText: String
        val tagline: String
        val authorTitle: String
        val copyrightText: String

        // Navigation & General
        val searchPlaceholder: String
        val allNotes: String
        val allCategories: String
        val favorites: String
        val archived: String
        val trash: String
        val categories: String
        val tags: String
        val newNote: String
        val quickNote: String
        val dailyNote: String
        val calendar: String
        val settings: String
        val about: String
        val dashboard: String
        val backBtn: String
        val moreOptions: String
        val todayBtn: String
        val prevMonth: String
        val nextMonth: String
        val daysOfWeek: List<String>
        val notesForDateLabel: String get() = when (this) {
            is PersianStrings -> "یادداشت‌های تاریخ"
            is ArabicStrings -> "ملاحظات تاريخ"
            is TurkishStrings -> "Tarihli notlar"
            is SpanishStrings -> "Notas para la fecha"
            is FrenchStrings -> "Notes pour la date"
            is GermanStrings -> "Notizen für das Datum"
            is RussianStrings -> "Заметки на дату"
            else -> "Notes for date"
        }
        val notesFoundCountLabel: String get() = when (this) {
            is PersianStrings -> "یادداشت ثبت شده"
            is ArabicStrings -> "ملاحظات مسجلة"
            is TurkishStrings -> "kayıtlı not"
            is SpanishStrings -> "notas registradas"
            is FrenchStrings -> "notes enregistrées"
            is GermanStrings -> "erfasste Notizen"
            is RussianStrings -> "заметок"
            else -> "notes recorded"
        }
        val noNotesInSelectedDay: String get() = when (this) {
            is PersianStrings -> "هیچ یادداشتی برای این تاریخ ثبت نشده است"
            is ArabicStrings -> "لم يتم تسجيل أي ملاحظات لهذا التاريخ"
            is TurkishStrings -> "Bu tarih için kayıtlı not yok"
            is SpanishStrings -> "No hay notas registradas para esta fecha"
            is FrenchStrings -> "Aucune note enregistrée pour cette date"
            is GermanStrings -> "Keine Notizen für dieses Datum erfasst"
            is RussianStrings -> "Нет заметок на эту дату"
            else -> "No notes recorded for this date"
        }

        // Dashboard & Stats
        val totalNotes: String
        val completedChecklists: String
        val pinnedNotes: String
        val recentNotes: String
        val noNotesFound: String
        val noFavoritesYet: String
        val noArchivedNotes: String
        val trashIsEmpty: String
        val createFirstNotePrompt: String

        // Actions & Confirmations
        val save: String
        val cancel: String
        val delete: String
        val restore: String
        val deletePermanently: String
        val emptyTrash: String
        val emptyTrashConfirm: String
        val deletePermanentConfirm: String
        val pin: String
        val unpin: String
        val favorite: String
        val unfavorite: String
        val archive: String
        val unarchive: String
        val reminder: String
        val setReminder: String
        val clearReminder: String
        val selectedCount: String
        val selectAll: String
        val clearSelection: String
        val batchDeleteConfirmTitle: String
        val batchDeleteConfirmMsg: String

        // Categories
        val category: String
        val selectCategory: String
        val noCategory: String
        val newCategory: String
        val editCategory: String
        val categoryName: String
        val categoryColor: String
        val categoryConfirmDelete: String
        val catPersonal: String
        val catWork: String
        val catShopping: String
        val catStudy: String
        val catIdeas: String
        val catMemories: String
        val catTravel: String

        fun localizeCategory(name: String): String {
            return when (name) {
                "شخصی", "Personal", "شخصي", "Kişisel", "Personnel", "Persönlich", "Личное" -> catPersonal
                "کاری", "Work", "عمل", "İş", "Trabajo", "Travail", "Arbeit", "Работа" -> catWork
                "خرید", "Shopping", "تسوق", "Alışveriş", "Compras", "Achats", "Einkaufen", "Покупки" -> catShopping
                "درس", "Study", "دراسة", "Ders", "Estudio", "Études", "Lernen", "Учёба" -> catStudy
                "ایده‌ها", "Ideas", "أفكار", "Fikirler", "Idées", "Ideen", "Идеи" -> catIdeas
                "خاطرات", "Memories", "ذكريات", "Anılar", "Recuerdos", "Souvenirs", "Erinnerungen", "Воспоминания" -> catMemories
                "سفر", "Travel", "سفر", "Seyahat", "Viajes", "Voyage", "Reisen", "Путешествия" -> catTravel
                else -> name
            }
        }

        // Editor & Attachments
        val titlePlaceholder: String
        val contentPlaceholder: String
        val color: String
        val checklist: String
        val addItem: String
        val itemsCompleted: String
        val attachments: String
        val addImage: String
        val recordVoice: String
        val stopVoice: String
        val drawing: String
        val drawingTitle: String
        val drawingUndo: String
        val drawingClear: String
        val drawingSave: String
        val strokeThin: String
        val strokeMedium: String
        val strokeThick: String
        val share: String
        val copy: String
        val textCopied: String
        val addTagPlaceholder: String
        val addTagBtn: String

        // Editor Toolbar & Actions
        val undo: String
        val redo: String
        val bold: String
        val italic: String
        val underline: String
        val strikethrough: String
        val heading: String
        val quote: String
        val bulletList: String
        val numberedList: String
        val code: String
        val link: String

        // Editor Modes & History
        val focusMode: String
        val focusModeDesc: String
        val readingMode: String
        val versionHistory: String
        val restoreVersion: String
        val noHistoryYet: String
        val wordsCount: String
        val charsCount: String
        val linesCount: String
        val noteSaved: String

        // Sort Options
        val sortNewest: String
        val sortOldest: String
        val sortTitle: String
        val sortModified: String

        // Appearance & Settings
        val appearance: String
        val theme: String
        val dayNightMode: String
        val darkMode: String
        val lightMode: String
        val systemMode: String
        val textSize: String
        val small: String
        val medium: String
        val large: String
        val extraLarge: String
        val language: String
        val currentLanguageLabel: String

        // Theme Preset Names
        val themeMetallicBlack: String
        val themeBlue: String
        val themeGreen: String
        val themePurple: String
        val themePink: String
        val themeOrange: String
        val themeRed: String
        val themeTeal: String
        val themeMinimal: String
        val themeGray: String get() = when (this) {
            is PersianStrings -> "خاکستری گرافیتی"
            is ArabicStrings -> "رمادي جرافيت"
            is TurkishStrings -> "Grafit Gri"
            is SpanishStrings -> "Gris Grafito"
            is FrenchStrings -> "Gris Graphite"
            is GermanStrings -> "Graphitgrau"
            is RussianStrings -> "Графитовый серый"
            else -> "Graphite Gray"
        }

        // Settings Details
        val notesSettingsTitle: String
        val autoSaveTitle: String
        val autoSaveDesc: String
        val wordCounterTitle: String
        val wordCounterDesc: String
        val dashboardToggleTitle: String
        val dashboardToggleDesc: String

        // Floating Mode
        val floatingMode: String
        val floatingBubble: String
        val floatingBubbleDesc: String
        val floatingActiveNotice: String
        val floatingQuickNoteTitle: String
        val floatingWritePrompt: String
        val floatingSaveBtn: String
        val floatingSavedToast: String
        val floatingEmptyWarning: String
        val overlayPermissionTitle: String
        val overlayPermissionDesc: String
        val grantPermission: String

        // Security
        val security: String
        val appLock: String
        val appLockDesc: String
        val appLockEnabledStatus: String
        val appLockDisabledStatus: String
        val setPin: String
        val enterPin: String
        val pinPrompt: String
        val wrongPin: String
        val pinLengthError: String
        val pinSavedSuccess: String

        // Data & Backup
        val dataAndBackupTitle: String
        val exportTxt: String
        val exportJson: String
        val exportJsonDesc: String
        val importBackup: String
        val importJsonDesc: String
        val importJsonPrompt: String
        val pasteJsonHint: String
        val restoreDataBtn: String
        val backupSuccessMsg: String
        val backupErrorMsg: String

        // About & Guarantees
        val offlinePledgeTitle: String
        val offlineNotice: String
        val privacyPledgeTitle: String
        val privacyPledge: String

        // Toast & Message Event Notifications
        val batchDeleteSuccess: String
        val batchArchiveSuccess: String
        val batchUnarchiveSuccess: String
        val noteMovedToTrash: String
        val noteRestoredMsg: String
        val notePermanentlyDeletedMsg: String
        val trashEmptiedMsg: String
        val revisionRestoredMsg: String
        val categorySavedMsg: String
        val categoryDeletedMsg: String
        val appLockEnabledMsg: String
        val appLockDisabledMsg: String
        val reminderNotificationTitle: String
        val reminderNotificationBody: String
    }

    // ==========================================
    // 1. PERSIAN (فارسی) - DEFAULT
    // ==========================================
    private object PersianStrings : Strings {
        override val versionText = "نسخه"
        override val tagline = "دفترچه یادداشت و مدیریت حرفه‌ای نوشته‌ها"
        override val authorTitle = "نام سازنده و طراح:"
        override val copyrightText = "کلیه حقوق برای سازنده محفوظ است."

        override val searchPlaceholder = "جستجو در یادداشت‌ها، متن و برچسب‌ها..."
        override val allNotes = "همه یادداشت‌ها"
        override val allCategories = "همه"
        override val favorites = "علاقه‌مندی‌ها"
        override val archived = "بایگانی"
        override val trash = "سطل زباله"
        override val categories = "دسته‌بندی‌ها"
        override val tags = "برچسب‌ها"
        override val newNote = "یادداشت جدید"
        override val quickNote = "یادداشت سریع"
        override val dailyNote = "یادداشت روزانه"
        override val calendar = "تقویم"
        override val settings = "تنظیمات"
        override val about = "درباره متنویس"
        override val dashboard = "داشبورد خلاصه"
        override val backBtn = "بازگشت"
        override val moreOptions = "گزینه‌های بیشتر"
        override val todayBtn = "امروز"
        override val prevMonth = "ماه قبل"
        override val nextMonth = "ماه بعد"
        override val daysOfWeek = listOf("ش", "ی", "د", "س", "چ", "پ", "ج")

        override val totalNotes = "کل یادداشت‌ها"
        override val completedChecklists = "موارد تکمیل‌شده"
        override val pinnedNotes = "سنجاق‌شده‌ها"
        override val recentNotes = "آخرین یادداشت‌ها"
        override val noNotesFound = "هیچ یادداشتی یافت نشد"
        override val noFavoritesYet = "هنوز یادداشتی به علاقه‌مندی‌ها اضافه نشده است"
        override val noArchivedNotes = "هیچ یادداشتی در بایگانی وجود ندارد"
        override val trashIsEmpty = "سطل زباله خالی است"
        override val createFirstNotePrompt = "برای نوشتن اولین یادداشت، دکمه + را لمس کنید"

        override val save = "ذخیره"
        override val cancel = "انصراف"
        override val delete = "حذف"
        override val restore = "بازیابی"
        override val deletePermanently = "حذف دائمی"
        override val emptyTrash = "خالی کردن سطل زباله"
        override val emptyTrashConfirm = "آیا از حذف دائمی تمام یادداشت‌های موجود در سطل زباله اطمینان دارید؟"
        override val deletePermanentConfirm = "آیا از حذف دائمی این یادداشت اطمینان دارید؟ این عمل غیرقابل بازگشت است."
        override val pin = "سنجاق کردن"
        override val unpin = "برداشتن سنجاق"
        override val favorite = "افزودن به علاقه‌مندی‌ها"
        override val unfavorite = "حذف از علاقه‌مندی‌ها"
        override val archive = "بایگانی"
        override val unarchive = "خروج از بایگانی"
        override val reminder = "یادآور"
        override val setReminder = "تنظیم زمان یادآور"
        override val clearReminder = "حذف یادآور"
        override val selectedCount = "مورد انتخاب شد"
        override val selectAll = "انتخاب همه"
        override val clearSelection = "لغو انتخاب"
        override val batchDeleteConfirmTitle = "حذف گروهی"
        override val batchDeleteConfirmMsg = "آیا از انتقال یادداشت‌های انتخاب‌شده به سطل زباله اطمینان دارید؟"

        override val category = "دسته‌بندی"
        override val selectCategory = "انتخاب دسته‌بندی"
        override val noCategory = "بدون دسته‌بندی"
        override val newCategory = "دسته‌بندی جدید"
        override val editCategory = "ویرایش دسته‌بندی"
        override val categoryName = "نام دسته‌بندی"
        override val categoryColor = "رنگ نماد:"
        override val categoryConfirmDelete = "آیا از حذف این دسته‌بندی اطمینان دارید؟"
        override val catPersonal = "شخصی"
        override val catWork = "کاری"
        override val catShopping = "خرید"
        override val catStudy = "درس"
        override val catIdeas = "ایده‌ها"
        override val catMemories = "خاطرات"
        override val catTravel = "سفر"

        override val titlePlaceholder = "عنوان یادداشت..."
        override val contentPlaceholder = "نوشتن را شروع کنید..."
        override val color = "رنگ یادداشت"
        override val checklist = "چک‌لیست"
        override val addItem = "مورد جدید..."
        override val itemsCompleted = "مورد انجام شده"
        override val attachments = "پیوست‌ها"
        override val addImage = "تصویر"
        override val recordVoice = "ضبط صدا"
        override val stopVoice = "توقف ضبط"
        override val drawing = "نقاشی"
        override val drawingTitle = "دست‌نویس و نقاشی"
        override val drawingUndo = "واگردانی"
        override val drawingClear = "پاک کردن"
        override val drawingSave = "ذخیره در یادداشت"
        override val strokeThin = "نازک"
        override val strokeMedium = "متوسط"
        override val strokeThick = "ضخیم"
        override val share = "اشتراک‌گذاری"
        override val copy = "کپی متن"
        override val textCopied = "متن در حافظه کپی شد"
        override val addTagPlaceholder = "برچسب جدید..."
        override val addTagBtn = "افزودن برچسب"

        override val undo = "واگردانی"
        override val redo = "از نو"
        override val bold = "پررنگ (Bold)"
        override val italic = "مورب (Italic)"
        override val underline = "زیرخط (Underline)"
        override val strikethrough = "خط‌خورده (Strikethrough)"
        override val heading = "سرتیتر (Heading)"
        override val quote = "نقل قول (Quote)"
        override val bulletList = "فهرست نقطه‌ای"
        override val numberedList = "فهرست شماره‌دار"
        override val code = "کد برنامه (Code)"
        override val link = "پیوند اینترنتی"

        override val focusMode = "حالت تمرکز (Focus)"
        override val focusModeDesc = "محیط نویسندگی بدون حواس‌پرتی"
        override val readingMode = "حالت مطالعه"
        override val versionHistory = "تاریخچه ویرایش‌ها"
        override val restoreVersion = "بازیابی این نسخه"
        override val noHistoryYet = "هنوز تاریخچه‌ای برای این یادداشت ثبت نشده است"
        override val wordsCount = "کلمه"
        override val charsCount = "حرف"
        override val linesCount = "سطر"
        override val noteSaved = "یادداشت ذخیره شد"

        override val sortNewest = "جدیدترین‌ها"
        override val sortOldest = "قدیمی‌ترین‌ها"
        override val sortTitle = "بر اساس عنوان"
        override val sortModified = "آخرین تغییرات"

        override val appearance = "ظاهر و شخصی‌سازی"
        override val theme = "رنگ‌بندی قالب"
        override val dayNightMode = "حالت شب و روز"
        override val darkMode = "شب (تیره)"
        override val lightMode = "روز (روشن)"
        override val systemMode = "پیرو سیستم"
        override val textSize = "اندازه قلم و متون"
        override val small = "کوچک"
        override val medium = "متوسط (استاندارد)"
        override val large = "بزرگ"
        override val extraLarge = "خیلی بزرگ"
        override val language = "زبان برنامه"
        override val currentLanguageLabel = "زبان فعلی"

        override val themeMetallicBlack = "مشکی متالیک لوکس (اصلی)"
        override val themeBlue = "آبی لاجوردی"
        override val themeGreen = "سبز زمردی"
        override val themePurple = "بنفش سلطنتی"
        override val themePink = "صورتی مرجانی"
        override val themeOrange = "نارنجی کهربایی"
        override val themeRed = "یاقوتی سرخ"
        override val themeTeal = "فیروزه‌ای پارسی"
        override val themeMinimal = "مینیمال مونوسیاه"

        override val notesSettingsTitle = "تنظیمات یادداشت‌ها"
        override val autoSaveTitle = "ذخیره خودکار (Auto-Save)"
        override val autoSaveDesc = "ذخیره آنی یادداشت هنگام نوشتن و خروج"
        override val wordCounterTitle = "شمارنده کلمات و حروف"
        override val wordCounterDesc = "نمایش زنده تعداد کلمات، حروف و خطوط"
        override val dashboardToggleTitle = "داشبورد خلاصه در صفحه اصلی"
        override val dashboardToggleDesc = "نمایش آمار یادداشت‌ها در بالای صفحه اصلی"

        override val floatingMode = "حالت شناور (Floating Mode)"
        override val floatingBubble = "حباب شناور یادداشت سریع"
        override val floatingBubbleDesc = "دسترسی فوری به یادداشت سریع روی سایر برنامه‌ها"
        override val floatingActiveNotice = "حباب یادداشت سریع روی صفحه فعال است"
        override val floatingQuickNoteTitle = "متنویس — یادداشت سریع"
        override val floatingWritePrompt = "متن یادداشت سریع را اینجا بنویسید..."
        override val floatingSaveBtn = "ذخیره یادداشت"
        override val floatingSavedToast = "یادداشت در متنویس ذخیره شد ✓"
        override val floatingEmptyWarning = "لطفاً عنوانی یا متنی بنویسید"
        override val overlayPermissionTitle = "مجوز پنجره شناور (Overlay)"
        override val overlayPermissionDesc = "برای نمایش حباب شناور یادداشت، برنامه نیاز به مجوز نمایش روی سایر برنامه‌ها دارد."
        override val grantPermission = "اعطای مجوز"

        override val security = "امنیت و قفل برنامه"
        override val appLock = "قفل با پین ۴ رقمی"
        override val appLockDesc = "محافظت از حریم شخصی با رمز عبور"
        override val appLockEnabledStatus = "قفل با رمز ۴ رقمی فعال است"
        override val appLockDisabledStatus = "ورود بدون رمز"
        override val setPin = "تنظیم رمز عبور"
        override val enterPin = "رمز عبور را وارد کنید"
        override val pinPrompt = "لطفاً یک رمز عبور ۴ رقمی عددی وارد کنید:"
        override val wrongPin = "رمز عبور اشتباه است، دوباره تلاش کنید"
        override val pinLengthError = "رمز باید دقیقاً ۴ رقم باشد"
        override val pinSavedSuccess = "رمز عبور با موفقیت ذخیره شد"

        override val dataAndBackupTitle = "پشتیبان‌گیری و داده‌ها"
        override val exportTxt = "خروجی متنی (TXT)"
        override val exportJson = "خروجی فایل پشتیبان (JSON)"
        override val exportJsonDesc = "پشتیبان‌گیری کامل از یادداشت‌ها و دسته‌بندی‌ها"
        override val importBackup = "بازیابی از فایل پشتیبان"
        override val importJsonDesc = "بازیابی اطلاعات از فایل پشتیبان JSON قبلی"
        override val importJsonPrompt = "محتوای فایل پشتیبان JSON را در کادر زیر وارد کنید:"
        override val pasteJsonHint = "{\"appName\": \"متنویس\", ...}"
        override val restoreDataBtn = "بازیابی اطلاعات"
        override val backupSuccessMsg = "اطلاعات پشتیبان با موفقیت بازیابی شد"
        override val backupErrorMsg = "خطا در خواندن فایل پشتیبان"

        override val offlinePledgeTitle = "مبتنی بر آفلاین (Offline-First)"
        override val offlineNotice = "تمام یادداشت‌ها و داده‌های شما روی این دستگاه ذخیره می‌شوند و برنامه برای عملکرد به اینترنت وابسته نیست."
        override val privacyPledgeTitle = "حریم خصوصی و امنیت اطلاعات"
        override val privacyPledge = "نوشته‌های شما کاملاً امن، محرمانه و تحت کنترل شماست و هیچ داده‌ای بدون اجازه شما به سروری ارسال نمی‌شود."

        override val batchDeleteSuccess = "یادداشت‌های انتخاب‌شده به سطل زباله منتقل شدند"
        override val batchArchiveSuccess = "یادداشت‌ها بایگانی شدند"
        override val batchUnarchiveSuccess = "از بایگانی خارج شدند"
        override val noteMovedToTrash = "یادداشت به سطل زباله منتقل شد"
        override val noteRestoredMsg = "یادداشت بازیابی شد"
        override val notePermanentlyDeletedMsg = "یادداشت برای همیشه حذف شد"
        override val trashEmptiedMsg = "سطل زباله خالی شد"
        override val revisionRestoredMsg = "نسخه قبلی بازیابی شد"
        override val categorySavedMsg = "دسته‌بندی ذخیره شد"
        override val categoryDeletedMsg = "دسته‌بندی حذف شد"
        override val appLockEnabledMsg = "قفل برنامه فعال شد"
        override val appLockDisabledMsg = "قفل برنامه غیرفعال شد"
        override val reminderNotificationTitle = "یادآور متنویس"
        override val reminderNotificationBody = "زمان یادآوری این یادداشت فرا رسیده است."
    }

    // ==========================================
    // 2. ENGLISH (English)
    // ==========================================
    private object EnglishStrings : Strings {
        override val versionText = "Version"
        override val tagline = "Professional Notes & Personal Writing Suite"
        override val authorTitle = "Creator & Designer:"
        override val copyrightText = "All rights reserved for the creator."

        override val searchPlaceholder = "Search notes, content and tags..."
        override val allNotes = "All Notes"
        override val allCategories = "All"
        override val favorites = "Favorites"
        override val archived = "Archive"
        override val trash = "Trash"
        override val categories = "Categories"
        override val tags = "Tags"
        override val newNote = "New Note"
        override val quickNote = "Quick Note"
        override val dailyNote = "Daily Note"
        override val calendar = "Calendar"
        override val settings = "Settings"
        override val about = "About Matnnevis"
        override val dashboard = "Dashboard"
        override val backBtn = "Back"
        override val moreOptions = "More Options"
        override val todayBtn = "Today"
        override val prevMonth = "Previous Month"
        override val nextMonth = "Next Month"
        override val daysOfWeek = listOf("Sat", "Sun", "Mon", "Tue", "Wed", "Thu", "Fri")

        override val totalNotes = "Total Notes"
        override val completedChecklists = "Completed Items"
        override val pinnedNotes = "Pinned Notes"
        override val recentNotes = "Recent Notes"
        override val noNotesFound = "No notes found"
        override val noFavoritesYet = "No favorite notes yet"
        override val noArchivedNotes = "No archived notes"
        override val trashIsEmpty = "Trash is empty"
        override val createFirstNotePrompt = "Tap + to write your first note"

        override val save = "Save"
        override val cancel = "Cancel"
        override val delete = "Delete"
        override val restore = "Restore"
        override val deletePermanently = "Delete Permanently"
        override val emptyTrash = "Empty Trash"
        override val emptyTrashConfirm = "Are you sure you want to permanently delete all notes in the trash?"
        override val deletePermanentConfirm = "Are you sure you want to permanently delete this note? This action cannot be undone."
        override val pin = "Pin"
        override val unpin = "Unpin"
        override val favorite = "Add to Favorites"
        override val unfavorite = "Remove from Favorites"
        override val archive = "Archive"
        override val unarchive = "Unarchive"
        override val reminder = "Reminder"
        override val setReminder = "Set Reminder"
        override val clearReminder = "Clear Reminder"
        override val selectedCount = "items selected"
        override val selectAll = "Select All"
        override val clearSelection = "Clear Selection"
        override val batchDeleteConfirmTitle = "Batch Delete"
        override val batchDeleteConfirmMsg = "Are you sure you want to move selected notes to trash?"

        override val category = "Category"
        override val selectCategory = "Select Category"
        override val noCategory = "No Category"
        override val newCategory = "New Category"
        override val editCategory = "Edit Category"
        override val categoryName = "Category Name"
        override val categoryColor = "Category Color:"
        override val categoryConfirmDelete = "Are you sure you want to delete this category?"
        override val catPersonal = "Personal"
        override val catWork = "Work"
        override val catShopping = "Shopping"
        override val catStudy = "Study"
        override val catIdeas = "Ideas"
        override val catMemories = "Memories"
        override val catTravel = "Travel"

        override val titlePlaceholder = "Note Title..."
        override val contentPlaceholder = "Start writing here..."
        override val color = "Note Color"
        override val checklist = "Checklist"
        override val addItem = "New item..."
        override val itemsCompleted = "items completed"
        override val attachments = "Attachments"
        override val addImage = "Image"
        override val recordVoice = "Voice Note"
        override val stopVoice = "Stop Recording"
        override val drawing = "Drawing"
        override val drawingTitle = "Sketch & Handwriting"
        override val drawingUndo = "Undo"
        override val drawingClear = "Clear"
        override val drawingSave = "Save Drawing"
        override val strokeThin = "Thin"
        override val strokeMedium = "Medium"
        override val strokeThick = "Thick"
        override val share = "Share"
        override val copy = "Copy Text"
        override val textCopied = "Text copied to clipboard"
        override val addTagPlaceholder = "New tag..."
        override val addTagBtn = "Add Tag"

        override val undo = "Undo"
        override val redo = "Redo"
        override val bold = "Bold"
        override val italic = "Italic"
        override val underline = "Underline"
        override val strikethrough = "Strikethrough"
        override val heading = "Heading"
        override val quote = "Quote"
        override val bulletList = "Bullet List"
        override val numberedList = "Numbered List"
        override val code = "Code"
        override val link = "Link"

        override val focusMode = "Focus Mode"
        override val focusModeDesc = "Distraction-free writing experience"
        override val readingMode = "Reading Mode"
        override val versionHistory = "Version History"
        override val restoreVersion = "Restore Version"
        override val noHistoryYet = "No revisions saved yet"
        override val wordsCount = "words"
        override val charsCount = "chars"
        override val linesCount = "lines"
        override val noteSaved = "Note saved"

        override val sortNewest = "Newest First"
        override val sortOldest = "Oldest First"
        override val sortTitle = "By Title"
        override val sortModified = "Recently Modified"

        override val appearance = "Appearance & Style"
        override val theme = "Theme Palette"
        override val dayNightMode = "Theme Mode"
        override val darkMode = "Dark"
        override val lightMode = "Light"
        override val systemMode = "System Default"
        override val textSize = "Text Font Size"
        override val small = "Small"
        override val medium = "Medium (Standard)"
        override val large = "Large"
        override val extraLarge = "Extra Large"
        override val language = "App Language"
        override val currentLanguageLabel = "Current Language"

        override val themeMetallicBlack = "Metallic Luxury Black (Primary)"
        override val themeBlue = "Azure Blue"
        override val themeGreen = "Emerald Green"
        override val themePurple = "Royal Purple"
        override val themePink = "Coral Pink"
        override val themeOrange = "Amber Orange"
        override val themeRed = "Ruby Red"
        override val themeTeal = "Persian Teal"
        override val themeMinimal = "Minimal Mono"

        override val notesSettingsTitle = "Notes Preferences"
        override val autoSaveTitle = "Auto-Save"
        override val autoSaveDesc = "Instantly save changes while writing"
        override val wordCounterTitle = "Live Word Counter"
        override val wordCounterDesc = "Show words, characters and lines in editor"
        override val dashboardToggleTitle = "Home Dashboard Overview"
        override val dashboardToggleDesc = "Show quick stats card at the top of home"

        override val floatingMode = "Floating Window Mode"
        override val floatingBubble = "Floating Quick Note Bubble"
        override val floatingBubbleDesc = "Quick access bubble overlaying other apps"
        override val floatingActiveNotice = "Quick note floating bubble is active"
        override val floatingQuickNoteTitle = "Matnnevis — Quick Note"
        override val floatingWritePrompt = "Type your quick thoughts here..."
        override val floatingSaveBtn = "Save Note"
        override val floatingSavedToast = "Note saved to Matnnevis ✓"
        override val floatingEmptyWarning = "Please enter a title or text"
        override val overlayPermissionTitle = "Overlay Permission Required"
        override val overlayPermissionDesc = "To display the quick note bubble, Matnnevis requires permission to draw over other apps."
        override val grantPermission = "Grant Permission"

        override val security = "Security & PIN Lock"
        override val appLock = "4-Digit PIN Lock"
        override val appLockDesc = "Protect your notes with a security code"
        override val appLockEnabledStatus = "App lock with 4-digit PIN is active"
        override val appLockDisabledStatus = "App lock is turned off"
        override val setPin = "Set PIN Code"
        override val enterPin = "Enter PIN Code"
        override val pinPrompt = "Please enter a 4-digit numerical PIN:"
        override val wrongPin = "Incorrect PIN, please try again"
        override val pinLengthError = "PIN must be exactly 4 digits"
        override val pinSavedSuccess = "PIN code successfully saved"

        override val dataAndBackupTitle = "Data & Backup"
        override val exportTxt = "Export as Text (TXT)"
        override val exportJson = "Export Backup (JSON)"
        override val exportJsonDesc = "Full backup of all notes, categories and checklists"
        override val importBackup = "Import Backup"
        override val importJsonDesc = "Restore notes and categories from backup JSON"
        override val importJsonPrompt = "Paste the backup JSON content below:"
        override val pasteJsonHint = "{\"appName\": \"متنویس\", ...}"
        override val restoreDataBtn = "Restore Data"
        override val backupSuccessMsg = "Backup data restored successfully"
        override val backupErrorMsg = "Failed to parse backup JSON file"

        override val offlinePledgeTitle = "Offline-First Guarantee"
        override val offlineNotice = "All your notes and data are stored locally on this device. The app requires no internet access for its core functions."
        override val privacyPledgeTitle = "Privacy & Data Protection"
        override val privacyPledge = "Your thoughts remain private, encrypted in local storage, and are never shared or uploaded to any external server."

        override val batchDeleteSuccess = "Selected notes moved to trash"
        override val batchArchiveSuccess = "Notes archived"
        override val batchUnarchiveSuccess = "Notes unarchived"
        override val noteMovedToTrash = "Note moved to trash"
        override val noteRestoredMsg = "Note restored"
        override val notePermanentlyDeletedMsg = "Note permanently deleted"
        override val trashEmptiedMsg = "Trash emptied"
        override val revisionRestoredMsg = "Previous revision restored"
        override val categorySavedMsg = "Category saved"
        override val categoryDeletedMsg = "Category deleted"
        override val appLockEnabledMsg = "App lock enabled"
        override val appLockDisabledMsg = "App lock disabled"
        override val reminderNotificationTitle = "Matnnevis Reminder"
        override val reminderNotificationBody = "Reminder for your note."
    }

    // ==========================================
    // 3. ARABIC (العربية)
    // ==========================================
    private object ArabicStrings : Strings {
        override val versionText = "الإصدار"
        override val tagline = "دفتر ملاحظات وإدارة نصوص شخصية احترافية"
        override val authorTitle = "المطور والمصمم:"
        override val copyrightText = "جميع الحقوق محفوظة للمطور."

        override val searchPlaceholder = "البحث في الملاحظات، النصوص والوسوم..."
        override val allNotes = "كل الملاحظات"
        override val allCategories = "الكل"
        override val favorites = "المفضلة"
        override val archived = "الأرشيف"
        override val trash = "سلة المحذوفات"
        override val categories = "التصنيفات"
        override val tags = "الوسوم"
        override val newNote = "ملاحظة جديدة"
        override val quickNote = "ملاحظة سريعة"
        override val dailyNote = "ملاحظة يومية"
        override val calendar = "التقويم"
        override val settings = "الإعدادات"
        override val about = "حول متنویس"
        override val dashboard = "لوحة المعلومات"
        override val backBtn = "رجوع"
        override val moreOptions = "خيارات إضافية"
        override val todayBtn = "اليوم"
        override val prevMonth = "الشهر السابق"
        override val nextMonth = "الشهر التالي"
        override val daysOfWeek = listOf("سبت", "أحد", "إثن", "ثلا", "أرب", "خمي", "جمع")

        override val totalNotes = "إجمالي الملاحظات"
        override val completedChecklists = "العناصر المكتملة"
        override val pinnedNotes = "الملاحظات المثبتة"
        override val recentNotes = "أحدث الملاحظات"
        override val noNotesFound = "لم يتم العثور على ملاحظات"
        override val noFavoritesYet = "لا توجد ملاحظات مفضلة بعد"
        override val noArchivedNotes = "لا توجد ملاحظات مؤرشفة"
        override val trashIsEmpty = "سلة المحذوفات فارغة"
        override val createFirstNotePrompt = "اضغط + لكتابة أول ملاحظة لك"

        override val save = "حفظ"
        override val cancel = "إلغاء"
        override val delete = "حذف"
        override val restore = "استعادة"
        override val deletePermanently = "حذف نهائي"
        override val emptyTrash = "إفراغ سلة المحذوفات"
        override val emptyTrashConfirm = "هل أنت متأكد من حذف جميع الملاحظات في السلة نهائياً؟"
        override val deletePermanentConfirm = "هل أنت متأكد من الحذف النهائي لهذه الملاحظة؟ لا يمكن التراجع عن هذا الإجراء."
        override val pin = "تثبيت"
        override val unpin = "إلغاء التثبيت"
        override val favorite = "إضافة للمفضلة"
        override val unfavorite = "إزالة من المفضلة"
        override val archive = "أرشفة"
        override val unarchive = "إلغاء الأرشفة"
        override val reminder = "تذكير"
        override val setReminder = "ضبط التذكير"
        override val clearReminder = "إلغاء التذكير"
        override val selectedCount = "عناصر محددة"
        override val selectAll = "تحديد الكل"
        override val clearSelection = "إلغاء التحديد"
        override val batchDeleteConfirmTitle = "حذف متعدد"
        override val batchDeleteConfirmMsg = "هل أنت متأكد من نقل الملاحظات المحددة إلى السلة؟"

        override val category = "التصنيف"
        override val selectCategory = "اختر التصنيف"
        override val noCategory = "بدون تصنيف"
        override val newCategory = "تصنيف جديد"
        override val editCategory = "تعديل التصنيف"
        override val categoryName = "اسم التصنيف"
        override val categoryColor = "لون الرمز:"
        override val categoryConfirmDelete = "هل أنت متأكد من حذف هذا التصنيف؟"
        override val catPersonal = "شخصي"
        override val catWork = "عمل"
        override val catShopping = "تسوق"
        override val catStudy = "دراسة"
        override val catIdeas = "أفكار"
        override val catMemories = "ذكريات"
        override val catTravel = "سفر"

        override val titlePlaceholder = "عنوان الملاحظة..."
        override val contentPlaceholder = "ابدأ الكتابة هنا..."
        override val color = "لون الملاحظة"
        override val checklist = "قائمة مهام"
        override val addItem = "عنصر جديد..."
        override val itemsCompleted = "عنصر مكتمل"
        override val attachments = "المرفقات"
        override val addImage = "صورة"
        override val recordVoice = "تسجيل صوتي"
        override val stopVoice = "إيقاف التسجيل"
        override val drawing = "رسم"
        override val drawingTitle = "مخطط ورسم يدوي"
        override val drawingUndo = "تراجع"
        override val drawingClear = "مسح"
        override val drawingSave = "حفظ الرسم"
        override val strokeThin = "رفيع"
        override val strokeMedium = "متوسط"
        override val strokeThick = "عريض"
        override val share = "مشاركة"
        override val copy = "نسخ النص"
        override val textCopied = "تم نسخ النص للحافظة"
        override val addTagPlaceholder = "وسم جديد..."
        override val addTagBtn = "إضافة وسم"

        override val undo = "تراجع"
        override val redo = "إعادة"
        override val bold = "عريض"
        override val italic = "مائل"
        override val underline = "تسطير"
        override val strikethrough = "يتوسطه خط"
        override val heading = "عنوان"
        override val quote = "اقتباس"
        override val bulletList = "قائمة نقطية"
        override val numberedList = "قائمة رقمية"
        override val code = "رمز برمجيات"
        override val link = "رابط"

        override val focusMode = "وضع التركيز"
        override val focusModeDesc = "بيئة كتابة خالية من المشتتات"
        override val readingMode = "وضع القراءة"
        override val versionHistory = "سجل التعديلات"
        override val restoreVersion = "استعادة هذا الإصدار"
        override val noHistoryYet = "لا توجد تعديلات سابقة بعد"
        override val wordsCount = "كلمة"
        override val charsCount = "حرف"
        override val linesCount = "سطر"
        override val noteSaved = "تم حفظ الملاحظة"

        override val sortNewest = "الأحدث أولاً"
        override val sortOldest = "الأقدم أولاً"
        override val sortTitle = "حسب العنوان"
        override val sortModified = "تاريخ التعديل"

        override val appearance = "المظهر والتخصيص"
        override val theme = "نمط الألوان"
        override val dayNightMode = "وضع الليل والنهار"
        override val darkMode = "داكن"
        override val lightMode = "فاتح"
        override val systemMode = "تلقائي حسب النظام"
        override val textSize = "حجم الخط والنصوص"
        override val small = "صغير"
        override val medium = "متوسط (قياسي)"
        override val large = "كبير"
        override val extraLarge = "كبير جداً"
        override val language = "لغة التطبيق"
        override val currentLanguageLabel = "اللغة الحالية"

        override val themeMetallicBlack = "أسود معدني فاخر (رئيسي)"
        override val themeBlue = "أزرق لازوردي"
        override val themeGreen = "أخضر زمردي"
        override val themePurple = "بنفسجي ملكي"
        override val themePink = "وردي مرجاني"
        override val themeOrange = "برتقالي عنبري"
        override val themeRed = "أحمر ياقوتي"
        override val themeTeal = "فيروزي فارسي"
        override val themeMinimal = "أحادي بسيط"

        override val notesSettingsTitle = "إعدادات الملاحظات"
        override val autoSaveTitle = "الحفظ التلقائي"
        override val autoSaveDesc = "حفظ التغييرات فوراً أثناء الكتابة"
        override val wordCounterTitle = "عداد الكلمات والأحرف"
        override val wordCounterDesc = "عرض مباشر لعدد الكلمات والأحرف والأسطر"
        override val dashboardToggleTitle = "لوحة المعلومات في الصفحة الرئيسية"
        override val dashboardToggleDesc = "عرض بطاقة الإحصائيات في الأعلى"

        override val floatingMode = "الوضع العائم"
        override val floatingBubble = "فقاعة الملاحظة السريعة"
        override val floatingBubbleDesc = "الوصول السريع للملاحظات فوق التطبيقات الأخرى"
        override val floatingActiveNotice = "فقاعة الملاحظة السريعة قيد التشغيل"
        override val floatingQuickNoteTitle = "متنویس — ملاحظة سريعة"
        override val floatingWritePrompt = "اكتب ملاحظتك السريعة هنا..."
        override val floatingSaveBtn = "حفظ الملاحظة"
        override val floatingSavedToast = "تم حفظ الملاحظة في متنویس ✓"
        override val floatingEmptyWarning = "يرجى إدخال عنوان أو نص"
        override val overlayPermissionTitle = "إذن الظهور فوق التطبيقات"
        override val overlayPermissionDesc = "لعرض الفقاعة العائمة، يتطلب التطبيق إذن العرض فوق التطبيقات الأخرى."
        override val grantPermission = "منح الإذن"

        override val security = "الأمان والقفل"
        override val appLock = "قفل برمز PIN من 4 أرقام"
        override val appLockDesc = "حماية ملاحظاتك برمز سري"
        override val appLockEnabledStatus = "القفل برمز 4 أرقام مفعّل"
        override val appLockDisabledStatus = "القفل معطل"
        override val setPin = "تعيين الرمز السري"
        override val enterPin = "أدخل الرمز السري"
        override val pinPrompt = "يرجى إدخال رمز سري مكون من 4 أرقام:"
        override val wrongPin = "رمز المرور غير صحيح، يرجى المحاولة ثانية"
        override val pinLengthError = "يجب أن يتكون الرمز من 4 أرقام بالضبط"
        override val pinSavedSuccess = "تم حفظ رمز المرور بنجاح"

        override val dataAndBackupTitle = "البيانات والنسخ الاحتياطي"
        override val exportTxt = "تصدير كنص (TXT)"
        override val exportJson = "تصدير نسخة احتياطية (JSON)"
        override val exportJsonDesc = "نسخ احتياطي كامل لكافة الملاحظات والتصنيفات"
        override val importBackup = "استيراد نسخة احتياطية"
        override val importJsonDesc = "استعادة الملاحظات من ملف JSON سابق"
        override val importJsonPrompt = "الصق محتوى النسخة الاحتياطية أدناه:"
        override val pasteJsonHint = "{\"appName\": \"متنویس\", ...}"
        override val restoreDataBtn = "استعادة البيانات"
        override val backupSuccessMsg = "تمت استعادة البيانات بنجاح"
        override val backupErrorMsg = "فشل في قراءة ملف النسخة الاحتياطية"

        override val offlinePledgeTitle = "مبدأ العمل بدون إنترنت (Offline-First)"
        override val offlineNotice = "جميع بياناتك وملاحظاتك مخزنة محلياً على جهازك ولا يتطلب التطبيق اتصالاً بالإنترنت."
        override val privacyPledgeTitle = "الخصوصية وحماية البيانات"
        override val privacyPledge = "كتاباتك محمية ومخزنة بأمان على جهازك ولا يتم إرسالها إلى أي خوادم خارجية إطلاقاً."

        override val batchDeleteSuccess = "تم نقل الملاحظات المحددة إلى السلة"
        override val batchArchiveSuccess = "تمت أرشفة الملاحظات"
        override val batchUnarchiveSuccess = "تمت إزالة الملاحظات من الأرشيف"
        override val noteMovedToTrash = "تم نقل الملاحظة إلى السلة"
        override val noteRestoredMsg = "تمت استعادة الملاحظة"
        override val notePermanentlyDeletedMsg = "تم حذف الملاحظة نهائياً"
        override val trashEmptiedMsg = "تم إفراغ سلة المحذوفات"
        override val revisionRestoredMsg = "تمت استعادة الإصدار السابق"
        override val categorySavedMsg = "تم حفظ التصنيف"
        override val categoryDeletedMsg = "تم حذف التصنيف"
        override val appLockEnabledMsg = "تم تفعيل قفل التطبيق"
        override val appLockDisabledMsg = "تم تعطيل قفل التطبيق"
        override val reminderNotificationTitle = "تذكير متنویس"
        override val reminderNotificationBody = "حان موعد تذكير ملاحظتك."
    }

    // ==========================================
    // 4. TURKISH (Türkçe)
    // ==========================================
    private object TurkishStrings : Strings {
        override val versionText = "Sürüm"
        override val tagline = "Profesyonel Not Defteri ve Kişisel Yazı Yönetimi"
        override val authorTitle = "Geliştirici ve Tasarımcı:"
        override val copyrightText = "Tüm hakları saklıdır."

        override val searchPlaceholder = "Notlarda, içerikte ve etiketlerde ara..."
        override val allNotes = "Tüm Notlar"
        override val allCategories = "Tümü"
        override val favorites = "Favoriler"
        override val archived = "Arşiv"
        override val trash = "Çöp Kutusu"
        override val categories = "Kategoriler"
        override val tags = "Etiketler"
        override val newNote = "Yeni Not"
        override val quickNote = "Hızlı Not"
        override val dailyNote = "Günlük Not"
        override val calendar = "Takvim"
        override val settings = "Ayarlar"
        override val about = "Matnnevis Hakkında"
        override val dashboard = "Gösterge Paneli"
        override val backBtn = "Geri"
        override val moreOptions = "Daha Fazla Seçenek"
        override val todayBtn = "Bugün"
        override val prevMonth = "Önceki Ay"
        override val nextMonth = "Sonraki Ay"
        override val daysOfWeek = listOf("Cts", "Paz", "Pzt", "Sal", "Çar", "Per", "Cum")

        override val totalNotes = "Toplam Not"
        override val completedChecklists = "Tamamlanan Ögeler"
        override val pinnedNotes = "Sabitlenen Notlar"
        override val recentNotes = "Son Notlar"
        override val noNotesFound = "Not bulunamadı"
        override val noFavoritesYet = "Henüz favori not eklenmedi"
        override val noArchivedNotes = "Arşivde not yok"
        override val trashIsEmpty = "Çöp kutusu boş"
        override val createFirstNotePrompt = "İlk notunuzu yazmak için + butonuna dokunun"

        override val save = "Kaydet"
        override val cancel = "İptal"
        override val delete = "Sil"
        override val restore = "Geri Yükle"
        override val deletePermanently = "Kalıcı Olarak Sil"
        override val emptyTrash = "Çöp Kutusunu Boşalt"
        override val emptyTrashConfirm = "Çöp kutusundaki tüm notları kalıcı olarak silmek istediğinizden emin misiniz?"
        override val deletePermanentConfirm = "Bu notu kalıcı olarak silmek istediğinizden emin misiniz? Bu işlem geri alınamaz."
        override val pin = "Sabitle"
        override val unpin = "Sabitlemeyi Kaldır"
        override val favorite = "Favorilere Ekle"
        override val unfavorite = "Favorilerden Çıkar"
        override val archive = "Arşivle"
        override val unarchive = "Arşivden Çıkar"
        override val reminder = "Hatırlatıcı"
        override val setReminder = "Hatırlatıcı Ayarla"
        override val clearReminder = "Hatırlatıcıyı Kaldır"
        override val selectedCount = "öge seçildi"
        override val selectAll = "Tümünü Seç"
        override val clearSelection = "Seçimi Temizle"
        override val batchDeleteConfirmTitle = "Toplu Silme"
        override val batchDeleteConfirmMsg = "Seçili notları çöp kutusuna taşımak istediğinizden emin misiniz?"

        override val category = "Kategori"
        override val selectCategory = "Kategori Seç"
        override val noCategory = "Kategorisiz"
        override val newCategory = "Yeni Kategori"
        override val editCategory = "Kategoriyi Düzenle"
        override val categoryName = "Kategori Adı"
        override val categoryColor = "Kategori Rengi:"
        override val categoryConfirmDelete = "Bu kategoriyi silmek istediğinizden emin misiniz?"
        override val catPersonal = "Kişisel"
        override val catWork = "İş"
        override val catShopping = "Alışveriş"
        override val catStudy = "Ders"
        override val catIdeas = "Fikirler"
        override val catMemories = "Anılar"
        override val catTravel = "Seyahat"

        override val titlePlaceholder = "Not Başlığı..."
        override val contentPlaceholder = "Yazmaya buradan başlayın..."
        override val color = "Not Rengi"
        override val checklist = "Kontrol Listesi"
        override val addItem = "Yeni öge..."
        override val itemsCompleted = "öge tamamlandı"
        override val attachments = "Ekler"
        override val addImage = "Görsel"
        override val recordVoice = "Ses Kaydı"
        override val stopVoice = "Kaydı Durdur"
        override val drawing = "Çizim"
        override val drawingTitle = "El Yazısı ve Çizim"
        override val drawingUndo = "Geri Al"
        override val drawingClear = "Temizle"
        override val drawingSave = "Çizimi Kaydet"
        override val strokeThin = "İnce"
        override val strokeMedium = "Orta"
        override val strokeThick = "Kalın"
        override val share = "Paylaş"
        override val copy = "Metni Kopyala"
        override val textCopied = "Metin panoya kopyalandı"
        override val addTagPlaceholder = "Yeni etiket..."
        override val addTagBtn = "Etiket Ekle"

        override val undo = "Geri Al"
        override val redo = "Yinele"
        override val bold = "Kalın"
        override val italic = "İtalik"
        override val underline = "Altı Çizili"
        override val strikethrough = "Üstü Çizili"
        override val heading = "Başlık"
        override val quote = "Alıntı"
        override val bulletList = "Madde İşaretli Liste"
        override val numberedList = "Numaralı Liste"
        override val code = "Kod"
        override val link = "Bağlantı"

        override val focusMode = "Odaklanma Modu"
        override val focusModeDesc = "Dikkat dağıtıcı unsurlardan uzak yazma ortamı"
        override val readingMode = "Okuma Modu"
        override val versionHistory = "Sürüm Geçmişi"
        override val restoreVersion = "Bu Sürümü Geri Yükle"
        override val noHistoryYet = "Henüz sürüm geçmişi kaydedilmedi"
        override val wordsCount = "kelime"
        override val charsCount = "karakter"
        override val linesCount = "satır"
        override val noteSaved = "Not kaydedildi"

        override val sortNewest = "En Yeni"
        override val sortOldest = "En Eski"
        override val sortTitle = "Başlığa Göre"
        override val sortModified = "Son Değiştirilen"

        override val appearance = "Görünüm ve Tema"
        override val theme = "Renk Teması"
        override val dayNightMode = "Gündüz ve Gece Modu"
        override val darkMode = "Karanlık"
        override val lightMode = "Aydınlık"
        override val systemMode = "Sistem Varsayılanı"
        override val textSize = "Yazı Boyutu"
        override val small = "Küçük"
        override val medium = "Orta (Standart)"
        override val large = "Büyük"
        override val extraLarge = "Çok Büyük"
        override val language = "Uygulama Dili"
        override val currentLanguageLabel = "Mevcut Dil"

        override val themeMetallicBlack = "Metalik Lüks Siyah (Ana)"
        override val themeBlue = "Gök Mavisi"
        override val themeGreen = "Zümrüt Yeşili"
        override val themePurple = "Kraliyet Moru"
        override val themePink = "Mercan Pembesi"
        override val themeOrange = "Kehribar Turuncusu"
        override val themeRed = "Yakut Kırmızısı"
        override val themeTeal = "Fars Turkuazı"
        override val themeMinimal = "Minimal Tek Renk"

        override val notesSettingsTitle = "Not Tercihleri"
        override val autoSaveTitle = "Otomatik Kaydetme"
        override val autoSaveDesc = "Yazarken değişiklikleri anında kaydet"
        override val wordCounterTitle = "Canlı Kelime Sayacı"
        override val wordCounterDesc = "Editörde kelime, karakter ve satır sayısını göster"
        override val dashboardToggleTitle = "Ana Sayfa Gösterge Paneli"
        override val dashboardToggleDesc = "Üst kısımda özet istatistik kartını göster"

        override val floatingMode = "Kayan Pencere Modu"
        override val floatingBubble = "Kayan Hızlı Not Baloncuğu"
        override val floatingBubbleDesc = "Diğer uygulamaların üzerinde hızlı not erişimi"
        override val floatingActiveNotice = "Hızlı not baloncuğu aktif"
        override val floatingQuickNoteTitle = "Matnnevis — Hızlı Not"
        override val floatingWritePrompt = "Hızlı notunuzu buraya yazın..."
        override val floatingSaveBtn = "Notu Kaydet"
        override val floatingSavedToast = "Not Matnnevis'e kaydedildi ✓"
        override val floatingEmptyWarning = "Lütfen başlık veya metin girin"
        override val overlayPermissionTitle = "Üstte Gösterme İzni Gerekli"
        override val overlayPermissionDesc = "Kayan not baloncuğunu gösterebilmek için uygulamanın diğer uygulamaların üzerinde görüntüleme iznine ihtiyacı vardır."
        override val grantPermission = "İzin Ver"

        override val security = "Güvenlik ve PIN Kilidi"
        override val appLock = "4 Haneli PIN Kilidi"
        override val appLockDesc = "Notlarınızı bir güvenlik koduyla koruyun"
        override val appLockEnabledStatus = "4 haneli PIN kilidi devrede"
        override val appLockDisabledStatus = "PIN kilidi kapalı"
        override val setPin = "PIN Belirle"
        override val enterPin = "PIN Girin"
        override val pinPrompt = "Lütfen 4 haneli sayısal bir PIN girin:"
        override val wrongPin = "Hatalı PIN, lütfen tekrar deneyin"
        override val pinLengthError = "PIN tam olarak 4 basamaklı olmalıdır"
        override val pinSavedSuccess = "PIN kodu başarıyla kaydedildi"

        override val dataAndBackupTitle = "Veri ve Yedekleme"
        override val exportTxt = "Metin Olarak Dışa Aktar (TXT)"
        override val exportJson = "Yedeği Dışa Aktar (JSON)"
        override val exportJsonDesc = "Tüm notların ve kategorilerin tam yedeklemesi"
        override val importBackup = "Yedeği İçe Aktar"
        override val importJsonDesc = "Önceki JSON yedek dosyasından geri yükle"
        override val importJsonPrompt = "Yedek JSON içeriğini aşağıya yapıştırın:"
        override val pasteJsonHint = "{\"appName\": \"متنویس\", ...}"
        override val restoreDataBtn = "Verileri Geri Yükle"
        override val backupSuccessMsg = "Yedek veriler başarıyla geri yüklendi"
        override val backupErrorMsg = "Yedek dosyası okunamadı"

        override val offlinePledgeTitle = "Çevrimdışı Öncelikli İlke (Offline-First)"
        override val offlineNotice = "Tüm notlarınız ve verileriniz bu cihazda yerel olarak saklanır. Uygulama temel işlevler için internete ihtiyaç duymaz."
        override val privacyPledgeTitle = "Gizlilik ve Veri Güvenliği"
        override val privacyPledge = "Düşünceleriniz gizlidir, cihazınızda güvenle saklanır ve hiçbir zaman harici bir sunucuya yüklenmez."

        override val batchDeleteSuccess = "Seçilen notlar çöp kutusuna taşındı"
        override val batchArchiveSuccess = "Notlar arşivlendi"
        override val batchUnarchiveSuccess = "Notlar arşivden çıkarıldı"
        override val noteMovedToTrash = "Not çöp kutusuna taşındı"
        override val noteRestoredMsg = "Not geri yüklendi"
        override val notePermanentlyDeletedMsg = "Not kalıcı olarak silindi"
        override val trashEmptiedMsg = "Çöp kutusu boşaltıldı"
        override val revisionRestoredMsg = "Önceki sürüm geri yüklendi"
        override val categorySavedMsg = "Kategori kaydedildi"
        override val categoryDeletedMsg = "Kategori silindi"
        override val appLockEnabledMsg = "Uygulama kilidi aktif"
        override val appLockDisabledMsg = "Uygulama kilidi kapatıldı"
        override val reminderNotificationTitle = "Matnnevis Hatırlatıcı"
        override val reminderNotificationBody = "Notunuz için hatırlatma zamanı geldi."
    }

    // ==========================================
    // 5. SPANISH (Español)
    // ==========================================
    private object SpanishStrings : Strings {
        override val versionText = "Versión"
        override val tagline = "Suite profesional de notas y redacción personal"
        override val authorTitle = "Creador y Diseñador:"
        override val copyrightText = "Todos los derechos reservados."

        override val searchPlaceholder = "Buscar en notas, contenido y etiquetas..."
        override val allNotes = "Todas las notas"
        override val allCategories = "Todas"
        override val favorites = "Favoritos"
        override val archived = "Archivo"
        override val trash = "Papelera"
        override val categories = "Categorías"
        override val tags = "Etiquetas"
        override val newNote = "Nueva nota"
        override val quickNote = "Nota rápida"
        override val dailyNote = "Nota diaria"
        override val calendar = "Calendario"
        override val settings = "Ajustes"
        override val about = "Acerca de Matnnevis"
        override val dashboard = "Panel de control"
        override val backBtn = "Atrás"
        override val moreOptions = "Más opciones"
        override val todayBtn = "Hoy"
        override val prevMonth = "Mes anterior"
        override val nextMonth = "Mes siguiente"
        override val daysOfWeek = listOf("Sáb", "Dom", "Lun", "Mar", "Mié", "Jue", "Vie")

        override val totalNotes = "Total de notas"
        override val completedChecklists = "Tareas completadas"
        override val pinnedNotes = "Notas fijadas"
        override val recentNotes = "Notas recientes"
        override val noNotesFound = "No se encontraron notas"
        override val noFavoritesYet = "Aún no hay notas favoritas"
        override val noArchivedNotes = "No hay notas archivadas"
        override val trashIsEmpty = "La papelera está vacía"
        override val createFirstNotePrompt = "Pulsa + para redactar tu primera nota"

        override val save = "Guardar"
        override val cancel = "Cancelar"
        override val delete = "Eliminar"
        override val restore = "Restaurar"
        override val deletePermanently = "Eliminar permanentemente"
        override val emptyTrash = "Vaciar papelera"
        override val emptyTrashConfirm = "¿Estás seguro de eliminar permanentemente todas las notas de la papelera?"
        override val deletePermanentConfirm = "¿Estás seguro de eliminar permanentemente esta nota? Esta acción es irreversible."
        override val pin = "Fijar"
        override val unpin = "Desfijar"
        override val favorite = "Añadir a favoritos"
        override val unfavorite = "Quitar de favoritos"
        override val archive = "Archivar"
        override val unarchive = "Desarchivar"
        override val reminder = "Recordatorio"
        override val setReminder = "Fijar recordatorio"
        override val clearReminder = "Borrar recordatorio"
        override val selectedCount = "elementos seleccionados"
        override val selectAll = "Seleccionar todo"
        override val clearSelection = "Deseleccionar"
        override val batchDeleteConfirmTitle = "Eliminación múltiple"
        override val batchDeleteConfirmMsg = "¿Mover las notas seleccionadas a la papelera?"

        override val category = "Categoría"
        override val selectCategory = "Seleccionar categoría"
        override val noCategory = "Sin categoría"
        override val newCategory = "Nueva categoría"
        override val editCategory = "Editar categoría"
        override val categoryName = "Nombre de categoría"
        override val categoryColor = "Color del icono:"
        override val categoryConfirmDelete = "¿Estás seguro de eliminar esta categoría?"
        override val catPersonal = "Personal"
        override val catWork = "Trabajo"
        override val catShopping = "Compras"
        override val catStudy = "Estudio"
        override val catIdeas = "Ideas"
        override val catMemories = "Recuerdos"
        override val catTravel = "Viajes"

        override val titlePlaceholder = "Título de la nota..."
        override val contentPlaceholder = "Empieza a escribir aquí..."
        override val color = "Color de la nota"
        override val checklist = "Lista de tareas"
        override val addItem = "Nuevo elemento..."
        override val itemsCompleted = "tareas completadas"
        override val attachments = "Archivos adjuntos"
        override val addImage = "Imagen"
        override val recordVoice = "Nota de voz"
        override val stopVoice = "Detener grabación"
        override val drawing = "Dibujo"
        override val drawingTitle = "Boceto y manuscrito"
        override val drawingUndo = "Deshacer"
        override val drawingClear = "Borrar"
        override val drawingSave = "Guardar dibujo"
        override val strokeThin = "Fino"
        override val strokeMedium = "Medio"
        override val strokeThick = "Grueso"
        override val share = "Compartir"
        override val copy = "Copiar texto"
        override val textCopied = "Texto copiado al portapapeles"
        override val addTagPlaceholder = "Nueva etiqueta..."
        override val addTagBtn = "Añadir etiqueta"

        override val undo = "Deshacer"
        override val redo = "Rehacer"
        override val bold = "Negrita"
        override val italic = "Cursiva"
        override val underline = "Subrayado"
        override val strikethrough = "Tachado"
        override val heading = "Encabezado"
        override val quote = "Cita"
        override val bulletList = "Lista con viñetas"
        override val numberedList = "Lista numerada"
        override val code = "Código"
        override val link = "Enlace"

        override val focusMode = "Modo Concentración"
        override val focusModeDesc = "Experiencia de escritura libre de distracciones"
        override val readingMode = "Modo Lectura"
        override val versionHistory = "Historial de versiones"
        override val restoreVersion = "Restaurar esta versión"
        override val noHistoryYet = "Aún no hay versiones guardadas"
        override val wordsCount = "palabras"
        override val charsCount = "caracteres"
        override val linesCount = "líneas"
        override val noteSaved = "Nota guardada"

        override val sortNewest = "Más recientes"
        override val sortOldest = "Más antiguos"
        override val sortTitle = "Por título"
        override val sortModified = "Modificados recientemente"

        override val appearance = "Apariencia y tema"
        override val theme = "Paleta de colores"
        override val dayNightMode = "Modo Día / Noche"
        override val darkMode = "Oscuro"
        override val lightMode = "Claro"
        override val systemMode = "Predeterminado del sistema"
        override val textSize = "Tamaño de fuente"
        override val small = "Pequeño"
        override val medium = "Mediano (Estándar)"
        override val large = "Grande"
        override val extraLarge = "Muy grande"
        override val language = "Idioma de la app"
        override val currentLanguageLabel = "Idioma actual"

        override val themeMetallicBlack = "Negro Metálico de Lujo (Principal)"
        override val themeBlue = "Azul Zafiro"
        override val themeGreen = "Verde Esmeralda"
        override val themePurple = "Púrpura Real"
        override val themePink = "Rosa Coral"
        override val themeOrange = "Ámbar Naranja"
        override val themeRed = "Rojo Rubí"
        override val themeTeal = "Turquesa Persa"
        override val themeMinimal = "Monocromo Minimalista"

        override val notesSettingsTitle = "Preferencias de notas"
        override val autoSaveTitle = "Guardado automático"
        override val autoSaveDesc = "Guarda cambios al instante mientras escribes"
        override val wordCounterTitle = "Contador de palabras en vivo"
        override val wordCounterDesc = "Muestra palabras, caracteres y líneas en el editor"
        override val dashboardToggleTitle = "Panel de control en inicio"
        override val dashboardToggleDesc = "Muestra tarjeta de estadísticas en la parte superior"

        override val floatingMode = "Modo Ventana Flotante"
        override val floatingBubble = "Burbuja de nota rápida"
        override val floatingBubbleDesc = "Acceso rápido a notas sobre otras aplicaciones"
        override val floatingActiveNotice = "Burbuja flotante activa"
        override val floatingQuickNoteTitle = "Matnnevis — Nota Rápida"
        override val floatingWritePrompt = "Escribe tus notas rápidas aquí..."
        override val floatingSaveBtn = "Guardar nota"
        override val floatingSavedToast = "Nota guardada en Matnnevis ✓"
        override val floatingEmptyWarning = "Por favor ingresa un título o texto"
        override val overlayPermissionTitle = "Permiso de superposición necesario"
        override val overlayPermissionDesc = "Para mostrar la burbuja flotante, la app necesita permiso para mostrarse sobre otras aplicaciones."
        override val grantPermission = "Conceder permiso"

        override val security = "Seguridad y Bloqueo PIN"
        override val appLock = "Bloqueo por PIN de 4 dígitos"
        override val appLockDesc = "Protege tus notas con un código de seguridad"
        override val appLockEnabledStatus = "Bloqueo por PIN de 4 dígitos activado"
        override val appLockDisabledStatus = "Bloqueo desactivado"
        override val setPin = "Definir código PIN"
        override val enterPin = "Introduce el PIN"
        override val pinPrompt = "Introduce un PIN numérico de 4 dígitos:"
        override val wrongPin = "PIN incorrecto, inténtalo de nuevo"
        override val pinLengthError = "El PIN debe tener exactamente 4 dígitos"
        override val pinSavedSuccess = "Código PIN guardado exitosamente"

        override val dataAndBackupTitle = "Datos y Copias de Seguridad"
        override val exportTxt = "Exportar como Texto (TXT)"
        override val exportJson = "Exportar Copia de Seguridad (JSON)"
        override val exportJsonDesc = "Copia completa de notas, categorías y listas"
        override val importBackup = "Importar Copia de Seguridad"
        override val importJsonDesc = "Restaurar notas desde un archivo JSON previo"
        override val importJsonPrompt = "Pega el contenido JSON de respaldo a continuación:"
        override val pasteJsonHint = "{\"appName\": \"متنویس\", ...}"
        override val restoreDataBtn = "Restaurar datos"
        override val backupSuccessMsg = "Datos de respaldo restaurados con éxito"
        override val backupErrorMsg = "Error al leer el archivo de respaldo"

        override val offlinePledgeTitle = "Garantía Offline-First"
        override val offlineNotice = "Todas tus notas y datos se guardan localmente en este dispositivo. No se requiere conexión a internet para las funciones principales."
        override val privacyPledgeTitle = "Privacidad y Protección de Datos"
        override val privacyPledge = "Tus escritos son estrictamente confidenciales y jamás se envían a ningún servidor externo."

        override val batchDeleteSuccess = "Notas seleccionadas enviadas a la papelera"
        override val batchArchiveSuccess = "Notas archivadas"
        override val batchUnarchiveSuccess = "Notas desarchivadas"
        override val noteMovedToTrash = "Nota enviada a la papelera"
        override val noteRestoredMsg = "Nota restaurada"
        override val notePermanentlyDeletedMsg = "Nota eliminada permanentemente"
        override val trashEmptiedMsg = "Papelera vaciada"
        override val revisionRestoredMsg = "Versión anterior restaurada"
        override val categorySavedMsg = "Categoría guardada"
        override val categoryDeletedMsg = "Categoría eliminada"
        override val appLockEnabledMsg = "Bloqueo de aplicación activado"
        override val appLockDisabledMsg = "Bloqueo de aplicación desactivado"
        override val reminderNotificationTitle = "Recordatorio de Matnnevis"
        override val reminderNotificationBody = "Ha llegado el momento de revisar tu nota."
    }

    // ==========================================
    // 6. FRENCH (Français)
    // ==========================================
    private object FrenchStrings : Strings {
        override val versionText = "Version"
        override val tagline = "Suite professionnelle de prise de notes et d'écriture personnelle"
        override val authorTitle = "Créateur et Designer :"
        override val copyrightText = "Tous droits réservés au créateur."

        override val searchPlaceholder = "Rechercher dans les notes, textes et tags..."
        override val allNotes = "Toutes les notes"
        override val allCategories = "Tout"
        override val favorites = "Favoris"
        override val archived = "Archives"
        override val trash = "Corbeille"
        override val categories = "Catégories"
        override val tags = "Tags"
        override val newNote = "Nouvelle note"
        override val quickNote = "Note rapide"
        override val dailyNote = "Note quotidienne"
        override val calendar = "Calendrier"
        override val settings = "Paramètres"
        override val about = "À propos de Matnnevis"
        override val dashboard = "Tableau de bord"
        override val backBtn = "Retour"
        override val moreOptions = "Plus d'options"
        override val todayBtn = "Aujourd'hui"
        override val prevMonth = "Mois précédent"
        override val nextMonth = "Mois suivant"
        override val daysOfWeek = listOf("Sam", "Dim", "Lun", "Mar", "Mer", "Jeu", "Ven")

        override val totalNotes = "Total des notes"
        override val completedChecklists = "Éléments terminés"
        override val pinnedNotes = "Notes épinglées"
        override val recentNotes = "Notes récentes"
        override val noNotesFound = "Aucune note trouvée"
        override val noFavoritesYet = "Aucune note favorite pour le moment"
        override val noArchivedNotes = "Aucune note archivée"
        override val trashIsEmpty = "La corbeille est vide"
        override val createFirstNotePrompt = "Appuyez sur + pour créer votre première note"

        override val save = "Enregistrer"
        override val cancel = "Annuler"
        override val delete = "Supprimer"
        override val restore = "Restaurer"
        override val deletePermanently = "Supprimer définitivement"
        override val emptyTrash = "Vider la corbeille"
        override val emptyTrashConfirm = "Êtes-vous sûr de vouloir supprimer définitivement toutes les notes de la corbeille ?"
        override val deletePermanentConfirm = "Êtes-vous sûr de vouloir supprimer définitivement cette note ? Cette action est irréversible."
        override val pin = "Épingler"
        override val unpin = "Détacher"
        override val favorite = "Ajouter aux favoris"
        override val unfavorite = "Retirer des favoris"
        override val archive = "Archiver"
        override val unarchive = "Désarchiver"
        override val reminder = "Rappel"
        override val setReminder = "Définir un rappel"
        override val clearReminder = "Supprimer le rappel"
        override val selectedCount = "éléments sélectionnés"
        override val selectAll = "Tout sélectionner"
        override val clearSelection = "Désélectionner"
        override val batchDeleteConfirmTitle = "Suppression groupée"
        override val batchDeleteConfirmMsg = "Déplacer les notes sélectionnées vers la corbeille ?"

        override val category = "Catégorie"
        override val selectCategory = "Choisir une catégorie"
        override val noCategory = "Sans catégorie"
        override val newCategory = "Nouvelle catégorie"
        override val editCategory = "Modifier la catégorie"
        override val categoryName = "Nom de la catégorie"
        override val categoryColor = "Couleur de l'icône :"
        override val categoryConfirmDelete = "Voulez-vous vraiment supprimer cette catégorie ?"
        override val catPersonal = "Personnel"
        override val catWork = "Travail"
        override val catShopping = "Achats"
        override val catStudy = "Études"
        override val catIdeas = "Idées"
        override val catMemories = "Souvenirs"
        override val catTravel = "Voyage"

        override val titlePlaceholder = "Titre de la note..."
        override val contentPlaceholder = "Commencez à écrire ici..."
        override val color = "Couleur de la note"
        override val checklist = "Liste de tâches"
        override val addItem = "Nouvel élément..."
        override val itemsCompleted = "éléments terminés"
        override val attachments = "Pièces jointes"
        override val addImage = "Image"
        override val recordVoice = "Note vocale"
        override val stopVoice = "Arrêter l'enregistrement"
        override val drawing = "Dessin"
        override val drawingTitle = "Croquis et écriture manuscrite"
        override val drawingUndo = "Annuler"
        override val drawingClear = "Effacer"
        override val drawingSave = "Enregistrer le dessin"
        override val strokeThin = "Fin"
        override val strokeMedium = "Moyen"
        override val strokeThick = "Épais"
        override val share = "Partager"
        override val copy = "Copier le texte"
        override val textCopied = "Texte copié dans le presse-papiers"
        override val addTagPlaceholder = "Nouveau tag..."
        override val addTagBtn = "Ajouter un tag"

        override val undo = "Annuler"
        override val redo = "Rétablir"
        override val bold = "Gras"
        override val italic = "Italique"
        override val underline = "Souligné"
        override val strikethrough = "Barré"
        override val heading = "Titre"
        override val quote = "Citation"
        override val bulletList = "Liste à puces"
        override val numberedList = "Liste numérotée"
        override val code = "Code"
        override val link = "Lien"

        override val focusMode = "Mode Concentration"
        override val focusModeDesc = "Environnement d'écriture sans distraction"
        override val readingMode = "Mode Lecture"
        override val versionHistory = "Historique des versions"
        override val restoreVersion = "Restaurer cette version"
        override val noHistoryYet = "Aucune version enregistrée"
        override val wordsCount = "mots"
        override val charsCount = "caractères"
        override val linesCount = "lignes"
        override val noteSaved = "Note enregistrée"

        override val sortNewest = "Plus récents"
        override val sortOldest = "Plus anciens"
        override val sortTitle = "Par titre"
        override val sortModified = "Dernière modification"

        override val appearance = "Apparence et thème"
        override val theme = "Palette de couleurs"
        override val dayNightMode = "Mode Jour / Nuit"
        override val darkMode = "Sombre"
        override val lightMode = "Clair"
        override val systemMode = "Défaut système"
        override val textSize = "Taille du texte"
        override val small = "Petit"
        override val medium = "Moyen (Standard)"
        override val large = "Grand"
        override val extraLarge = "Très grand"
        override val language = "Langue de l'application"
        override val currentLanguageLabel = "Langue actuelle"

        override val themeMetallicBlack = "Noir Métallique de Luxe (Principal)"
        override val themeBlue = "Bleu Azur"
        override val themeGreen = "Vert Émeraude"
        override val themePurple = "Violet Royal"
        override val themePink = "Rose Corail"
        override val themeOrange = "Orange Ambré"
        override val themeRed = "Rouge Rubis"
        override val themeTeal = "Turquoise Persan"
        override val themeMinimal = "Minimaliste Monochrome"

        override val notesSettingsTitle = "Préférences des notes"
        override val autoSaveTitle = "Enregistrement automatique"
        override val autoSaveDesc = "Sauvegarde instantanée pendant la saisie"
        override val wordCounterTitle = "Compteur de mots en direct"
        override val wordCounterDesc = "Affiche mots, caractères et lignes"
        override val dashboardToggleTitle = "Tableau de bord à l'accueil"
        override val dashboardToggleDesc = "Affiche la carte récapitulative en haut"

        override val floatingMode = "Mode Fenêtre Flottante"
        override val floatingBubble = "Bulle de note rapide"
        override val floatingBubbleDesc = "Accès rapide aux notes par-dessus d'autres applications"
        override val floatingActiveNotice = "Bulle flottante de note active"
        override val floatingQuickNoteTitle = "Matnnevis — Note Rapide"
        override val floatingWritePrompt = "Écrivez votre note rapide ici..."
        override val floatingSaveBtn = "Enregistrer la note"
        override val floatingSavedToast = "Note enregistrée dans Matnnevis ✓"
        override val floatingEmptyWarning = "Veuillez saisir un titre ou du texte"
        override val overlayPermissionTitle = "Autorisation de superposition requise"
        override val overlayPermissionDesc = "Pour afficher la bulle flottante, l'application a besoin de l'autorisation de s'afficher par-dessus les autres applications."
        override val grantPermission = "Accorder l'autorisation"

        override val security = "Sécurité et Verrouillage PIN"
        override val appLock = "Verrouillage par code PIN à 4 chiffres"
        override val appLockDesc = "Protégez vos notes avec un code de sécurité"
        override val appLockEnabledStatus = "Verrouillage PIN à 4 chiffres activé"
        override val appLockDisabledStatus = "Verrouillage désactivé"
        override val setPin = "Définir le code PIN"
        override val enterPin = "Entrez le code PIN"
        override val pinPrompt = "Veuillez entrer un code PIN à 4 chiffres :"
        override val wrongPin = "Code PIN incorrect, veuillez réessayer"
        override val pinLengthError = "Le code PIN doit comporter exactement 4 chiffres"
        override val pinSavedSuccess = "Code PIN enregistré avec succès"

        override val dataAndBackupTitle = "Données et Sauvegardes"
        override val exportTxt = "Exporter en Texte (TXT)"
        override val exportJson = "Exporter Sauvegarde (JSON)"
        override val exportJsonDesc = "Sauvegarde intégrale des notes et catégories"
        override val importBackup = "Importer une sauvegarde"
        override val importJsonDesc = "Restaurer à partir d'un fichier JSON précédent"
        override val importJsonPrompt = "Collez le contenu JSON de la sauvegarde ci-dessous :"
        override val pasteJsonHint = "{\"appName\": \"متنویس\", ...}"
        override val restoreDataBtn = "Restaurer les données"
        override val backupSuccessMsg = "Données restaurées avec succès"
        override val backupErrorMsg = "Erreur lors de la lecture du fichier de sauvegarde"

        override val offlinePledgeTitle = "Principe Offline-First"
        override val offlineNotice = "Toutes vos notes et données sont stockées localement sur cet appareil. Aucune connexion internet n'est requise."
        override val privacyPledgeTitle = "Confidentialité et Protection"
        override val privacyPledge = "Vos écrits restent strictement confidentiels et ne sont jamais envoyés vers des serveurs distants."

        override val batchDeleteSuccess = "Notes sélectionnées déplacées vers la corbeille"
        override val batchArchiveSuccess = "Notes archivées"
        override val batchUnarchiveSuccess = "Notes désarchivées"
        override val noteMovedToTrash = "Note déplacée vers la corbeille"
        override val noteRestoredMsg = "Note restaurée"
        override val notePermanentlyDeletedMsg = "Note supprimée définitivement"
        override val trashEmptiedMsg = "Corbeille vidée"
        override val revisionRestoredMsg = "Version précédente restaurée"
        override val categorySavedMsg = "Catégorie enregistrée"
        override val categoryDeletedMsg = "Catégorie supprimée"
        override val appLockEnabledMsg = "Verrouillage activé"
        override val appLockDisabledMsg = "Verrouillage désactivé"
        override val reminderNotificationTitle = "Rappel Matnnevis"
        override val reminderNotificationBody = "Il est l'heure de consulter votre note."
    }

    // ==========================================
    // 7. GERMAN (Deutsch)
    // ==========================================
    private object GermanStrings : Strings {
        override val versionText = "Version"
        override val tagline = "Professionelles Notizbuch und persönliches Schreibstudio"
        override val authorTitle = "Entwickler & Designer:"
        override val copyrightText = "Alle Rechte vorbehalten."

        override val searchPlaceholder = "Notizen, Inhalte und Tags durchsuchen..."
        override val allNotes = "Alle Notizen"
        override val allCategories = "Alle"
        override val favorites = "Favoriten"
        override val archived = "Archiv"
        override val trash = "Papierkorb"
        override val categories = "Kategorien"
        override val tags = "Tags"
        override val newNote = "Neue Notiz"
        override val quickNote = "Schnellnotiz"
        override val dailyNote = "Tagesnotiz"
        override val calendar = "Kalender"
        override val settings = "Einstellungen"
        override val about = "Über Matnnevis"
        override val dashboard = "Übersicht"
        override val backBtn = "Zurück"
        override val moreOptions = "Weitere Optionen"
        override val todayBtn = "Heute"
        override val prevMonth = "Vorheriger Monat"
        override val nextMonth = "Nächster Monat"
        override val daysOfWeek = listOf("Sa", "So", "Mo", "Di", "Mi", "Do", "Fr")

        override val totalNotes = "Gesamte Notizen"
        override val completedChecklists = "Erledigte Aufgaben"
        override val pinnedNotes = "Angeheftete Notizen"
        override val recentNotes = "Aktuelle Notizen"
        override val noNotesFound = "Keine Notizen gefunden"
        override val noFavoritesYet = "Noch keine Favoriten vorhanden"
        override val noArchivedNotes = "Keine archivierten Notizen"
        override val trashIsEmpty = "Papierkorb ist leer"
        override val createFirstNotePrompt = "Tippen Sie auf +, um Ihre erste Notiz zu schreiben"

        override val save = "Speichern"
        override val cancel = "Abbrechen"
        override val delete = "Löschen"
        override val restore = "Wiederherstellen"
        override val deletePermanently = "Dauerhaft löschen"
        override val emptyTrash = "Papierkorb leeren"
        override val emptyTrashConfirm = "Möchten Sie wirklich alle Notizen im Papierkorb dauerhaft löschen?"
        override val deletePermanentConfirm = "Möchten Sie diese Notiz wirklich dauerhaft löschen? Dies kann nicht rückgängig gemacht werden."
        override val pin = "Anheften"
        override val unpin = "Lösen"
        override val favorite = "Zu Favoriten hinzufügen"
        override val unfavorite = "Aus Favoriten entfernen"
        override val archive = "Archivieren"
        override val unarchive = "Dearchivieren"
        override val reminder = "Erinnerung"
        override val setReminder = "Erinnerung einstellen"
        override val clearReminder = "Erinnerung entfernen"
        override val selectedCount = "Elemente ausgewählt"
        override val selectAll = "Alle auswählen"
        override val clearSelection = "Auswahl aufheben"
        override val batchDeleteConfirmTitle = "Stapellöschung"
        override val batchDeleteConfirmMsg = "Ausgewählte Notizen in den Papierkorb verschieben?"

        override val category = "Kategorie"
        override val selectCategory = "Kategorie wählen"
        override val noCategory = "Ohne Kategorie"
        override val newCategory = "Neue Kategorie"
        override val editCategory = "Kategorie bearbeiten"
        override val categoryName = "Kategoriename"
        override val categoryColor = "Symbolfarbe:"
        override val categoryConfirmDelete = "Sind Sie sicher, dass Sie diese Kategorie löschen möchten?"
        override val catPersonal = "Persönlich"
        override val catWork = "Arbeit"
        override val catShopping = "Einkaufen"
        override val catStudy = "Lernen"
        override val catIdeas = "Ideen"
        override val catMemories = "Erinnerungen"
        override val catTravel = "Reisen"

        override val titlePlaceholder = "Notiztitel..."
        override val contentPlaceholder = "Hier mit dem Schreiben beginnen..."
        override val color = "Notizfarbe"
        override val checklist = "Checkliste"
        override val addItem = "Neuer Eintrag..."
        override val itemsCompleted = "Einträge erledigt"
        override val attachments = "Anhänge"
        override val addImage = "Bild"
        override val recordVoice = "Sprachaufnahme"
        override val stopVoice = "Aufnahme stoppen"
        override val drawing = "Zeichnung"
        override val drawingTitle = "Skizze & Handschrift"
        override val drawingUndo = "Rückgängig"
        override val drawingClear = "Löschen"
        override val drawingSave = "Zeichnung speichern"
        override val strokeThin = "Dünn"
        override val strokeMedium = "Mittel"
        override val strokeThick = "Dick"
        override val share = "Teilen"
        override val copy = "Text kopieren"
        override val textCopied = "Text in die Zwischenablage kopiert"
        override val addTagPlaceholder = "Neuer Tag..."
        override val addTagBtn = "Tag hinzufügen"

        override val undo = "Rückgängig"
        override val redo = "Wiederholen"
        override val bold = "Fett"
        override val italic = "Kursiv"
        override val underline = "Unterstrichen"
        override val strikethrough = "Durchgestrichen"
        override val heading = "Überschrift"
        override val quote = "Zitat"
        override val bulletList = "Aufzählung"
        override val numberedList = "Nummerierte Liste"
        override val code = "Code"
        override val link = "Link"

        override val focusMode = "Fokus-Modus"
        override val focusModeDesc = "Ablenkungsfreie Schreibumgebung"
        override val readingMode = "Lesemodus"
        override val versionHistory = "Versionsverlauf"
        override val restoreVersion = "Diese Version wiederherstellen"
        override val noHistoryYet = "Bisher kein Verlauf vorhanden"
        override val wordsCount = "Wörter"
        override val charsCount = "Zeichen"
        override val linesCount = "Zeilen"
        override val noteSaved = "Notiz gespeichert"

        override val sortNewest = "Neueste zuerst"
        override val sortOldest = "Älteste zuerst"
        override val sortTitle = "Nach Titel"
        override val sortModified = "Zuletzt geändert"

        override val appearance = "Erscheinungsbild"
        override val theme = "Farbschema"
        override val dayNightMode = "Tag- & Nachtmodus"
        override val darkMode = "Dunkel"
        override val lightMode = "Hell"
        override val systemMode = "Systemstandard"
        override val textSize = "Schriftgröße"
        override val small = "Klein"
        override val medium = "Mittel (Standard)"
        override val large = "Groß"
        override val extraLarge = "Sehr groß"
        override val language = "App-Sprache"
        override val currentLanguageLabel = "Aktuelle Sprache"

        override val themeMetallicBlack = "Metallisches Luxusschwarz (Haupt)"
        override val themeBlue = "Azurblau"
        override val themeGreen = "Smaragdgrün"
        override val themePurple = "Königspurpur"
        override val themePink = "Korallenrosa"
        override val themeOrange = "Bernsteinorange"
        override val themeRed = "Rubinrot"
        override val themeTeal = "Persisches Türkis"
        override val themeMinimal = "Minimalistisches Monochrom"

        override val notesSettingsTitle = "Notizeinstellungen"
        override val autoSaveTitle = "Automatische Speicherung"
        override val autoSaveDesc = "Änderungen beim Schreiben sofort speichern"
        override val wordCounterTitle = "Echtzeit-Wortzähler"
        override val wordCounterDesc = "Wörter, Zeichen und Zeilen im Editor anzeigen"
        override val dashboardToggleTitle = "Übersicht auf Startseite"
        override val dashboardToggleDesc = "Statistik-Karte oben auf der Startseite einblenden"

        override val floatingMode = "Schwebender Fenstermodus"
        override val floatingBubble = "Schwebende Schnellnotiz-Blase"
        override val floatingBubbleDesc = "Schneller Notizzugriff über anderen Apps"
        override val floatingActiveNotice = "Schwebende Notizblase ist aktiv"
        override val floatingQuickNoteTitle = "Matnnevis — Schnellnotiz"
        override val floatingWritePrompt = "Schreiben Sie Ihre Notiz hier..."
        override val floatingSaveBtn = "Notiz speichern"
        override val floatingSavedToast = "Notiz in Matnnevis gespeichert ✓"
        override val floatingEmptyWarning = "Bitte geben Sie einen Titel oder Text ein"
        override val overlayPermissionTitle = "Überlagerungsberechtigung erforderlich"
        override val overlayPermissionDesc = "Um die schwebende Blase anzuzeigen, benötigt die App die Berechtigung, sich über anderen Apps einzublenden."
        override val grantPermission = "Berechtigung erteilen"

        override val security = "Sicherheit & PIN-Sperre"
        override val appLock = "4-stellige PIN-Sperre"
        override val appLockDesc = "Schützen Sie Ihre Notizen mit einem Sicherheitscode"
        override val appLockEnabledStatus = "PIN-Sperre ist aktiv"
        override val appLockDisabledStatus = "PIN-Sperre ist deaktiviert"
        override val setPin = "PIN festlegen"
        override val enterPin = "PIN eingeben"
        override val pinPrompt = "Bitte geben Sie eine 4-stellige Zahl ein:"
        override val wrongPin = "Falsche PIN, bitte versuchen Sie es erneut"
        override val pinLengthError = "Die PIN muss genau 4 Ziffern lang sein"
        override val pinSavedSuccess = "PIN-Code erfolgreich gespeichert"

        override val dataAndBackupTitle = "Daten & Sicherung"
        override val exportTxt = "Als Text exportieren (TXT)"
        override val exportJson = "Sicherung exportieren (JSON)"
        override val exportJsonDesc = "Vollständige Sicherung aller Notizen und Kategorien"
        override val importBackup = "Sicherung importieren"
        override val importJsonDesc = "Notizen aus einer JSON-Datei wiederherstellen"
        override val importJsonPrompt = "Fügen Sie den JSON-Sicherungsinhalt unten ein:"
        override val pasteJsonHint = "{\"appName\": \"متنویس\", ...}"
        override val restoreDataBtn = "Daten wiederherstellen"
        override val backupSuccessMsg = "Sicherungsdaten erfolgreich wiederhergestellt"
        override val backupErrorMsg = "Fehler beim Lesen der Sicherungsdatei"

        override val offlinePledgeTitle = "Offline-First Prinzip"
        override val offlineNotice = "Alle Ihre Notizen und Daten werden lokal auf diesem Gerät gespeichert. Für Kernfunktionen ist kein Internet erforderlich."
        override val privacyPledgeTitle = "Datenschutz & Privatsphäre"
        override val privacyPledge = "Ihre Notizen bleiben vertraulich, sind lokal geschützt und werden niemals an externe Server gesendet."

        override val batchDeleteSuccess = "Ausgewählte Notizen in den Papierkorb verschoben"
        override val batchArchiveSuccess = "Notizen archiviert"
        override val batchUnarchiveSuccess = "Notizen dearchiviert"
        override val noteMovedToTrash = "Notiz in den Papierkorb verschoben"
        override val noteRestoredMsg = "Notiz wiederhergestellt"
        override val notePermanentlyDeletedMsg = "Notiz dauerhaft gelöscht"
        override val trashEmptiedMsg = "Papierkorb geleert"
        override val revisionRestoredMsg = "Vorherige Version wiederhergestellt"
        override val categorySavedMsg = "Kategorie gespeichert"
        override val categoryDeletedMsg = "Kategorie gelöscht"
        override val appLockEnabledMsg = "App-Sperre aktiviert"
        override val appLockDisabledMsg = "App-Sperre deaktiviert"
        override val reminderNotificationTitle = "Matnnevis Erinnerung"
        override val reminderNotificationBody = "Zeit für Ihre Notiz-Erinnerung."
    }

    // ==========================================
    // 8. RUSSIAN (Русский)
    // ==========================================
    private object RussianStrings : Strings {
        override val versionText = "Версия"
        override val tagline = "Профессиональный блокнот и личное пространство для заметок"
        override val authorTitle = "Создатель и разработчик:"
        override val copyrightText = "Все права защищены автором."

        override val searchPlaceholder = "Поиск по заметкам, тексту и тегам..."
        override val allNotes = "Все заметки"
        override val allCategories = "Все"
        override val favorites = "Избранное"
        override val archived = "Архив"
        override val trash = "Корзина"
        override val categories = "Категории"
        override val tags = "Теги"
        override val newNote = "Новая заметка"
        override val quickNote = "Быстрая заметка"
        override val dailyNote = "Заметка дня"
        override val calendar = "Календарь"
        override val settings = "Настройки"
        override val about = "О программе Matnnevis"
        override val dashboard = "Панель управления"
        override val backBtn = "Назад"
        override val moreOptions = "Дополнительные параметры"
        override val todayBtn = "Сегодня"
        override val prevMonth = "Предыдущий месяц"
        override val nextMonth = "Следующий месяц"
        override val daysOfWeek = listOf("Сб", "Вс", "Пн", "Вт", "Ср", "Чт", "Пт")

        override val totalNotes = "Всего заметок"
        override val completedChecklists = "Выполненные задачи"
        override val pinnedNotes = "Закрепленные"
        override val recentNotes = "Недавние заметки"
        override val noNotesFound = "Заметки не найдены"
        override val noFavoritesYet = "Нет избранных заметок"
        override val noArchivedNotes = "В архиве нет заметок"
        override val trashIsEmpty = "Корзина пуста"
        override val createFirstNotePrompt = "Нажмите +, чтобы создать первую заметку"

        override val save = "Сохранить"
        override val cancel = "Отмена"
        override val delete = "Удалить"
        override val restore = "Восстановить"
        override val deletePermanently = "Удалить навсегда"
        override val emptyTrash = "Очистить корзину"
        override val emptyTrashConfirm = "Вы уверены, что хотите навсегда удалить все заметки из корзины?"
        override val deletePermanentConfirm = "Удалить эту заметку навсегда? Это действие необратимо."
        override val pin = "Закрепить"
        override val unpin = "Открепить"
        override val favorite = "В избранное"
        override val unfavorite = "Из избранного"
        override val archive = "В архив"
        override val unarchive = "Из архива"
        override val reminder = "Напоминание"
        override val setReminder = "Установить напоминание"
        override val clearReminder = "Удалить напоминание"
        override val selectedCount = "элементов выбрано"
        override val selectAll = "Выбрать все"
        override val clearSelection = "Снять выбор"
        override val batchDeleteConfirmTitle = "Групповое удаление"
        override val batchDeleteConfirmMsg = "Переместить выбранные заметки в корзину?"

        override val category = "Категория"
        override val selectCategory = "Выбрать категорию"
        override val noCategory = "Без категории"
        override val newCategory = "Новая категория"
        override val editCategory = "Редактировать категорию"
        override val categoryName = "Название категории"
        override val categoryColor = "Цвет значка:"
        override val categoryConfirmDelete = "Вы уверены, что хотите удалить эту категорию?"
        override val catPersonal = "Личное"
        override val catWork = "Работа"
        override val catShopping = "Покупки"
        override val catStudy = "Учёба"
        override val catIdeas = "Идеи"
        override val catMemories = "Воспоминания"
        override val catTravel = "Путешествия"

        override val titlePlaceholder = "Заголовок заметки..."
        override val contentPlaceholder = "Начните писать здесь..."
        override val color = "Цвет заметки"
        override val checklist = "Чек-лист"
        override val addItem = "Новый пункт..."
        override val itemsCompleted = "пунктов выполнено"
        override val attachments = "Вложения"
        override val addImage = "Изображение"
        override val recordVoice = "Аудиозапись"
        override val stopVoice = "Остановить запись"
        override val drawing = "Рисунок"
        override val drawingTitle = "Рукописный рисунок"
        override val drawingUndo = "Отменить"
        override val drawingClear = "Очистить"
        override val drawingSave = "Сохранить рисунок"
        override val strokeThin = "Тонкий"
        override val strokeMedium = "Средний"
        override val strokeThick = "Толстый"
        override val share = "Поделиться"
        override val copy = "Копировать текст"
        override val textCopied = "Текст скопирован в буфер обмена"
        override val addTagPlaceholder = "Новый тег..."
        override val addTagBtn = "Добавить тег"

        override val undo = "Отменить"
        override val redo = "Повторить"
        override val bold = "Полужирный"
        override val italic = "Курсив"
        override val underline = "Подчеркнутый"
        override val strikethrough = "Зачеркнутый"
        override val heading = "Заголовок"
        override val quote = "Цитата"
        override val bulletList = "Маркированный список"
        override val numberedList = "Нумерованный список"
        override val code = "Код"
        override val link = "Ссылка"

        override val focusMode = "Режим концентрации"
        override val focusModeDesc = "Среда для письма без отвлекающих факторов"
        override val readingMode = "Режим чтения"
        override val versionHistory = "История версий"
        override val restoreVersion = "Восстановить версию"
        override val noHistoryYet = "История версий пока отсутствует"
        override val wordsCount = "слов"
        override val charsCount = "симв."
        override val linesCount = "строк"
        override val noteSaved = "Заметка сохранена"

        override val sortNewest = "Сначала новые"
        override val sortOldest = "Сначала старые"
        override val sortTitle = "По названию"
        override val sortModified = "По дате изменения"

        override val appearance = "Внешний вид и тема"
        override val theme = "Цветовая палитра"
        override val dayNightMode = "День и Ночь"
        override val darkMode = "Тёмная"
        override val lightMode = "Светлая"
        override val systemMode = "Как в системе"
        override val textSize = "Размер шрифта"
        override val small = "Мелкий"
        override val medium = "Средний (Стандарт)"
        override val large = "Крупный"
        override val extraLarge = "Очень крупный"
        override val language = "Язык приложения"
        override val currentLanguageLabel = "Текущий язык"

        override val themeMetallicBlack = "Металлический роскошный чёрный (Основной)"
        override val themeBlue = "Лазурный синий"
        override val themeGreen = "Изумрудный зелёный"
        override val themePurple = "Королевский фиолетовый"
        override val themePink = "Коралловый розовый"
        override val themeOrange = "Янтарный оранжевый"
        override val themeRed = "Рубиновый красный"
        override val themeTeal = "Персидский бирюзовый"
        override val themeMinimal = "Минималистичный моно"

        override val notesSettingsTitle = "Настройки заметок"
        override val autoSaveTitle = "Автосохранение"
        override val autoSaveDesc = "Мгновенное сохранение при вводе текста"
        override val wordCounterTitle = "Счётчик слов и символов"
        override val wordCounterDesc = "Отображение количества слов и строк в редакторе"
        override val dashboardToggleTitle = "Панель на главном экране"
        override val dashboardToggleDesc = "Показывать карточку статистики вверху"

        override val floatingMode = "Режим плавающего окна"
        override val floatingBubble = "Плавающая кнопка быстрой заметки"
        override val floatingBubbleDesc = "Быстрый доступ к заметкам поверх других приложений"
        override val floatingActiveNotice = "Плавающая кнопка активна"
        override val floatingQuickNoteTitle = "Matnnevis — Быстрая заметка"
        override val floatingWritePrompt = "Напишите быструю заметку здесь..."
        override val floatingSaveBtn = "Сохранить заметку"
        override val floatingSavedToast = "Заметка сохранена в Matnnevis ✓"
        override val floatingEmptyWarning = "Пожалуйста, введите заголовок или текст"
        override val overlayPermissionTitle = "Требуется разрешение на отображение поверх"
        override val overlayPermissionDesc = "Для работы плавающего окна требуется разрешение на отображение поверх других приложений."
        override val grantPermission = "Предоставить разрешение"

        override val security = "Безопасность и PIN-код"
        override val appLock = "Блокировка 4-значным PIN-кодом"
        override val appLockDesc = "Защитите ваши записи кодом безопасности"
        override val appLockEnabledStatus = "Блокировка 4-значным PIN включена"
        override val appLockDisabledStatus = "Блокировка отключена"
        override val setPin = "Установить PIN-код"
        override val enterPin = "Введите PIN-код"
        override val pinPrompt = "Введите 4-значный числовой PIN-код:"
        override val wrongPin = "Неверный PIN-код, попробуйте снова"
        override val pinLengthError = "PIN-код должен состоять ровно из 4 цифр"
        override val pinSavedSuccess = "PIN-код успешно сохранен"

        override val dataAndBackupTitle = "Данные и Резервное копирование"
        override val exportTxt = "Экспорт в Текст (TXT)"
        override val exportJson = "Экспорт резервной копии (JSON)"
        override val exportJsonDesc = "Полная резервная копия всех заметок и категорий"
        override val importBackup = "Импорт резервной копии"
        override val importJsonDesc = "Восстановление заметок из файла JSON"
        override val importJsonPrompt = "Вставьте содержимое JSON резервной копии ниже:"
        override val pasteJsonHint = "{\"appName\": \"متنویس\", ...}"
        override val restoreDataBtn = "Восстановить данные"
        override val backupSuccessMsg = "Данные успешно восстановлены"
        override val backupErrorMsg = "Ошибка при чтении файла резервной копии"

        override val offlinePledgeTitle = "Принцип Offline-First"
        override val offlineNotice = "Все ваши заметки сохраняются локально на этом устройстве. Приложению не требуется интернет для основных функций."
        override val privacyPledgeTitle = "Конфиденциальность и Безопасность"
        override val privacyPledge = "Ваши мысли строго конфиденциальны, защищены на устройстве и никогда не передаются на внешние серверы."

        override val batchDeleteSuccess = "Выбранные заметки перемещены в корзину"
        override val batchArchiveSuccess = "Заметки архивированы"
        override val batchUnarchiveSuccess = "Заметки разархивированы"
        override val noteMovedToTrash = "Заметка перемещена в корзину"
        override val noteRestoredMsg = "Заметка восстановлена"
        override val notePermanentlyDeletedMsg = "Заметка удалена навсегда"
        override val trashEmptiedMsg = "Корзина очищена"
        override val revisionRestoredMsg = "Предыдущая версия восстановлена"
        override val categorySavedMsg = "Категория сохранена"
        override val categoryDeletedMsg = "Категория удалена"
        override val appLockEnabledMsg = "Блокировка приложения включена"
        override val appLockDisabledMsg = "Блокировка приложения отключена"
        override val reminderNotificationTitle = "Напоминание Matnnevis"
        override val reminderNotificationBody = "Время просмотреть вашу заметку."
    }
}
