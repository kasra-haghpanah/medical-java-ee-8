/**
 * Created by kasra.haghpanah on 03/04/2016.
 */
var demoApp = angular.module("demoApp", ['ngResource', 'ngRoute', 'pascalprecht.translate', 'ngLocale']);

demoApp.config(function ($routeProvider) {

    $routeProvider
        .when('/home', {
            controller: 'SickController',
            templateUrl: '/view/view/home.htm'
        })
        .when('/upload', {
            controller: 'UploadController',
            templateUrl: '/view/view/upload.htm'
        })
        .when('/medical/record/sick/:id', {
            controller: 'MedicalController',
            templateUrl: '/view/view/medical.htm'
        })
        .when('/recourse/sick/:id', {
            controller: 'RecourseController',
            templateUrl: '/view/view/recourse.htm'
        })
        .when('/prescription/recourse/:id', {
            controller: 'PrescriptionController',
            templateUrl: '/view/view/prescription.htm'
        })
        .when('/log', {
            controller: 'LogController',
            templateUrl: '/view/view/log.htm'
        })
        .when('/asynch/xml', {
            controller: 'XMLController',
            templateUrl: '/view/view/xml.htm'
        })
        .when('/host/upload', {
            controller: 'UploadAnotherHostController',
            templateUrl: '/view/view/uploadAnotherHost.htm'
        })
        .when('/jms/queue', {
            controller: 'JmsController',
            templateUrl: '/view/view/jms.htm'
        })
        .when('/chatroom', {
            controller: 'ChatroomController',
            templateUrl: '/view/view/chatroom.htm'
        })
        .when('/login', {
            controller: 'LoginController',
            templateUrl: '/view/view/login.htm'
        })
        .when('/mail', {
            controller: 'MailController',
            templateUrl: '/view/view/mail.htm'
        })
        .when('/user', {
            controller: 'UserController',
            templateUrl: '/view/view/user.htm'
        })
        .when('/group', {
            controller: 'GroupController',
            templateUrl: '/view/view/group.htm'
        })
        .when('/batch', {
            controller: 'BatchController',
            templateUrl: '/view/view/batch.htm'
        })
        .when('/cdi', {
            controller: 'CdiController',
            templateUrl: '/view/view/cdi.htm'
        })
        .when('/jta', {
            controller: 'JtaController',
            templateUrl: '/view/view/jta.htm'
        })
        .when('/live/video', {
            controller: 'LiveVideoController',
            templateUrl: '/view/view/live.video.htm'
        })
        .otherwise({
            redirectTo: '/home'
        });
});

demoApp.config(function ($translateProvider) {

    $translateProvider.translations('en', {
        View_1: 'View 1',
        View_2: 'View 2',
        BUTTON_LANG_EN: 'english',
        BUTTON_LANG_FA: 'فارسی',
        Run: 'Run',
        Bike: 'Bike',
        Swim: 'Swim',
        Minutes: "Minutes",
        Activity: "Activity",
        Add: "Add",
        Delete: "Delete",
        Update: "Update",
        Send: "Send",
        From_Client: "From Client",
        From_Server: "From Server",
        Exercise: "Exercise",
        Goals_Report: "Goals Report",
        ID: "id",
        Exercises: "Exercises",
        Filter: "Filter",
        Exercises_Minutes: "Exercises Minutes",
        Export: "Export",
        Person: "Person",
        PersonsList: "Persons List",
        Name: "name",
        ShortName: "short name",
        FirstName: "firstname",
        LastName: "lastname",
        Sex: "sex",
        Male: "male",
        Female: "female",
        SickId: "sick id",
        Category: "category",
        MedicalRecord: "medical record",
        Date: "date",
        Heart: "heart",
        Brain: "brain",
        DiseaseName: "disease name",
        Recourse: "recourse",
        Prescription: "prescription",
        RecourseId: "recourse id",
        Description: "description",
        Class: "class",
        Method: "method",
        PDF_Export: "PDF Export",
        Image_Export: "Image Export",
        Chat: "Chat",
        Close: "Close",
        US: "United States",
        CA: "Canada",
        IR: "Iran",
        Erase: "Erase",
        Enter_Name: "Please enter name",
        Login: "Login",
        Signout: "Signout",
        Room: "Room",
        Room1: "Room1",
        Room2: "Room2",
        Room3: "Room3",
        New_Room: "New Room",
        Plz_enter_room: "Please enter your room name",
        Username: "Username",
        Password: "Password",
        Role: "Role",
        User: "User",
        Admin: "Admin",
        Batch_Processing: "Batch Processing",
        Show: "Show",
        University: "University",
        Age: "Age",
        Search: "Search",
        FileName: "filename",
        Size: "size",
        Mime: "mime",
        Download: "download",
        Back: "Back",
        Open: "Open",
        Message:"message"


    });


    $translateProvider.translations('fa', {
        View_1: 'نمایش اول',
        View_2: 'نمایش دوم',
        BUTTON_LANG_EN: 'english',
        BUTTON_LANG_FA: 'فارسی',
        Run: "دویدن",
        Bike: 'دوچرخه سواری',
        Swim: 'شنا کردن',
        Minutes: "دقایق",
        Activity: "فعالیت",
        Add: "افزودن",
        Delete: "حذف",
        Update: "بروز رسانی",
        Send: "ارسال",
        From_Client: "از سمت کاربر",
        From_Server: "از سمت سرور",
        Exercise: "تمرین",
        Goals_Report: "گزارش گل ها",
        ID: "شماره کاربری",
        Exercises: "تکالیف",
        Filter: "فیلتر",
        Exercises_Minutes: "دقایق تمرین",
        Export: "خروجی",
        Person: "شخص",
        PersonsList: "لیست اشخاص",
        Name: "نام",
        ShortName: "نام اختصاری",
        FirstName: "نام",
        LastName: "نام خانوادگی",
        Sex: "جنسیت",
        Male: "مرد",
        Female: "زن",
        SickId: "شناسه کاربری بیمار",
        Category: "گروه",
        MedicalRecord: "پرونده پزشکی",
        Date: "تاریخ",
        Heart: "قلب",
        Brain: "مغز",
        DiseaseName: "نام بیماری",
        Recourse: "مراجعه",
        Prescription: "نسخه",
        RecourseId: "شناسه مراجعه",
        Description: "توضیح",
        Class: "کلاس",
        Method: "تابع",
        PDF_Export: "خروجی PDF",
        Image_Export: "خروجی عکس",
        Chat: "گفتگو",
        Close: "بستن",
        US: "ایالات متحده آمریکا",
        CA: "کانادا",
        IR: "ایران",
        Erase: "پاک کن",
        Enter_Name: "لطفا نام خود را وارد کنید",
        Login: "ورود",
        Signout: "خروج",
        Room: "اتاق",
        Room1: "اتاق اول",
        Room2: "اتاق دوم",
        Room3: "اتاق سوم",
        New_Room: "اتاق جدید",
        Plz_enter_room: "لطفا نام اتاق چت خود را وارد کنید",
        Username: "شناسه کاربری",
        Password: "پسورد",
        Role: "نقش",
        User: "کاربر",
        Admin: "مدیر",
        Batch_Processing: "پردازش بسته ای",
        Show: "نمایش",
        University: "دانشگاه",
        Age: "سن",
        Search: "جستجو",
        FileName: "نام فایل",
        Size: "حجم",
        Mime: "فرمت",
        Download: "دانلود",
        Back: "بازگشت",
        Open: "نمایش",
        Message:"محتوا"

    });

    $translateProvider.preferredLanguage('en');
    //$translateProvider.useLocalStorage();


});
