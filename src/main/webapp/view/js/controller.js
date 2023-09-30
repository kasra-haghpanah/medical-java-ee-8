/**
 * Created by kasra.haghpanah on 03/04/2016.
 */

String.prototype.toUnicode = function () {
    var result = "";
    for (var i = 0; i < this.length; i++) {
        result += "\\u" + ("000" + this[i].charCodeAt(0).toString(16)).substr(-4);
    }
    return result;
};

String.prototype.escapeHtml = function () {

    var text = document.createTextNode(this);
    var div = document.createElement('div');
    div.appendChild(text);
    var result = div.innerHTML.toString();
    delete div;
    return result;
}


demoApp.run(['$rootScope', '$translate', function ($rootScope, $translate) {


    $rootScope.dir = "ltr";
    $rootScope.changeLanguage = function (key) {
        $translate.use(key);
        $rootScope.dir = "ltr";
        if (key == "fa") {
            $rootScope.dir = "rtl";
        }
    };


    $rootScope.htmlToPDF = function (id) {

        var html = document.getElementById(id).outerHTML.toString();
        ajax.download("POST", "/kasra/htmltopdf.html?format=pdf&html=" + html.toUnicode(), true,
            function (digit) {
                var percent = document.getElementById("exportbar").getElementsByTagName("div").item(0);
                var progress = document.getElementById("exportbar").getElementsByTagName("div").item(1);

                percent.innerHTML = digit * 100 + "%";
                progress.style.width = digit * 100 + "%";

            },
            function (xhr) {

            }
        );
        // var form = document.getElementById('form');
        // form.getElementsByTagName('input').item(0).setAttribute('value', html.toUnicode());
        // form.getElementsByTagName('input').item(1).setAttribute('value', "pdf");
        // form.getElementsByTagName('input').item(2).click();
    }

    $rootScope.htmlToImage = function (id) {


        var html = document.getElementById(id).outerHTML.toString();
        ajax.download("POST", "/kasra/htmltopdf.html?format=png&html=" + html.toUnicode(), true,
            function (digit) {
                var percent = document.getElementById("exportbar").getElementsByTagName("div").item(0);
                var progress = document.getElementById("exportbar").getElementsByTagName("div").item(1);

                percent.innerHTML = digit * 100 + "%";
                progress.style.width = digit * 100 + "%";

            },
            function (xhr) {

            }
        );
        // var form = document.getElementById('form');
        // form.getElementsByTagName('input').item(0).setAttribute('value', html.toUnicode());
        // form.getElementsByTagName('input').item(1).setAttribute('value', "png");
        // form.getElementsByTagName('input').item(2).click();
    }

    $rootScope.startup = function () {

        if (typeof $rootScope.websocket != "undefined") {
            if ($rootScope.websocket != null) {
                $rootScope.websocket.close();
            }
        }

    }


    $rootScope.$on("$routeChangeStart", function (event, next, current) {
        if ((typeof next.$$route != "undefined") && (typeof next.$$route.originalPath != "undefined") && next.$$route.originalPath != "/chatroom") {
            $rootScope.startup();
        }
    });


}]);


demoApp.controller("SickController", ['$scope', '$http', function ($scope, $http) {

    $scope.jsons = [];
    $scope.updateItem = {};
    $scope.addItem = {};
    $scope.addItem.sex = "1";

    $scope.updateItem = {};
    $scope.updateItem.sex = "1";


    $scope.updateSick = function (item2) {

        $scope.updateItem.id = item2.id;
        $scope.updateItem.firstName = item2.firstName;
        $scope.updateItem.lastName = item2.lastName;
        $scope.updateItem.sex = item2.sex;

    }


    $http.post('/kasra/sick/getAll').success(
        function (data) {
            $scope.sickList = data;
        });


    $scope.add = function () {
        $http.put('/kasra/sick/add', [$scope.addItem]).success(
            function (data) {
                $scope.sickList = data;
            });
    }


    $scope.update = function () {
        $http.put('/kasra/sick/update', $scope.updateItem).success(
            function (data) {
                $scope.sickList = data;
            });
    }


    $scope.delete = function (item) {

        var d = {
            id: item.id,
            name: item.name,
            shortName: item.shortName
        }
        $http.post('/kasra/sick/delete', d).success(
            function (data) {
                $scope.sickList = data;
            });
    }


}]);


demoApp.controller("UploadController", ['$scope', '$http', function ($scope, $http) {

    $scope.dir = "/";

    $scope.upload = function (id) {

        var form = document.getElementById(id);
        var percent = document.getElementById("percent");


        var progressive = function (percents) {
            percent.innerHTML = percents + "%";
            percent.style.width = percents + "%";
        }
        var complete = function (str) {
            percent.innerHTML = "100%";
            alert(str);
        }
        var failed = function () {
            alert("upload failed");
        }
        var cancel = function () {
            alert("upload cancel");
        }

        ajax.upload(form, progressive, complete, failed, cancel, true);


    }


    var inputs = document.getElementById("myupload").getElementsByTagName("input");

    $scope.change = function () {

        for (var i = 0; i < inputs.length; i++) {

            if (inputs.item(i).type.toLowerCase() == "file") {
                var file = inputs.item(i);

                file.onchange = null;

                file.onchange = function () {


                    percent.innerHTML = "0%";
                    percent.style.width = "0%";
                    if (typeof FileReader == "function") {
                        var divs = this.nextSibling.nextSibling.getElementsByTagName("div");
                        var x = ajax.mimeContentType(this);
                        var str = "";

                        for (var i = 0; i < x.length; i++) {
                            str = str + x[i] + " ";
                        }


                        divs.item(0).innerHTML = str;
                        var video = divs.item(1).getElementsByTagName("video");
                        if (video != null && video.length == 1) {
                            video.item(0).src = "";
                        }
                        divs.item(1).innerHTML = null;
                        ajax.fileReader(this, divs.item(1), 500, 200, 45);

                    }

                }
            }

        }

    }


    $scope.change();


    $scope.getListFile = function (url) {

        //$scope.dir = $scope.dir + "/" + url;
        //$scope.dir = $scope.dir.replace("//", "/");
        url = "/resources/download/file/" + url;
        url.replace("//", "/");

        $http.get(url).success(
            function (data) {
                $scope.downloadList = data;
            });

    }

    $scope.disabledLink = function (event) {
        event.preventDefault ? event.preventDefault(event) : (event.returnValue = false);
    }

    $scope.getListFile("");

    $scope.forwardDir = function (filename) {
        $scope.dir = $scope.dir + "/" + filename;
        $scope.dir = $scope.dir.replace("//", "/");
        return $scope.dir;


    }

    $scope.backDir = function (url) {

        if (url == null || url == "" || url.lastIndexOf("/") < 1) {
            $scope.dir = "/";
            return "/";
        }

        var path = url.split("/");
        var path2 = [];
        url = "";

        for (var i = 0; i < path.length; i++) {
            if (path[i] != "") {
                path2.push(path[i]);
            }
        }

        for (var i = 0; i < path2.length - 1; i++) {
            url = "/" + path2[i];
        }

        $scope.dir = url;

        return url;

    }


    $scope.removeElement = function (td) {

        //var lastNode = td.lastElementChild;
        var firstChild = td.firstChild;
        if (firstChild != null) {
            var tagName = firstChild.tagName.toLowerCase();
            if (tagName.indexOf("video") > -1 || tagName.indexOf("audio") > -1 || tagName.indexOf("img") > -1 || tagName.indexOf("iframe") > -1) {
                firstChild.setAttribute("src", "");
            }
            td.removeChild(firstChild);
        }

    }

    $scope.download = function (item, isDownload, isOpen) {

        var filename = item.filename;
        var url = "/resources/download/" + $scope.dir + "/" + filename;
        url = url.replace("//", "/");
        ajax.download("POST", url, isDownload,

            function (digit) {
                var percent = document.getElementById(filename).getElementsByTagName("div").item(0).getElementsByTagName("div").item(0);
                var progress = document.getElementById(filename).getElementsByTagName("div").item(0).getElementsByTagName("div").item(1);
                percent.innerHTML = digit * 100 + "%";
                progress.style.width = digit * 100 + "%";
            },
            function (xhr) {

                if (!isOpen) {
                    return;
                }

                var td = document.getElementById(filename + "-open");
                $scope.removeElement(td);


                if (item.mime.toLowerCase().indexOf("pdf") > -1) {
                    var embed = document.createElement("iframe");
                    //var u =  "localhost:8084/" + url;
                    embed.setAttribute("src", window.URL.createObjectURL(xhr.response));
                    embed.setAttribute("type", "application/pdf");
                    embed.setAttribute("alt", "pdf");
                    embed.setAttribute("width", "200");
                    embed.setAttribute("height", "200");
                    embed.setAttribute("pluginspage", "http://www.adobe.com/products/acrobat/readstep2.html");
                    td.appendChild(embed);
                }
                else if (item.mime.toLowerCase().indexOf("text") > -1) {
                    var textarea = document.createElement("textarea");
                    textarea.value = ajax.blobToString(xhr.response);
                    textarea.width = "150";
                    textarea.height = "150";
                    td.appendChild(textarea);
                }
                else if (item.mime.toLowerCase().indexOf("video") > -1 || item.mime.toLowerCase().indexOf("audio") > -1) {
                    var type = "video";
                    if (item.mime.toLowerCase().indexOf("audio") > -1) {
                        var type = "audio";
                    }

                    var video = document.createElement(type);
                    video.src = "";
                    video.src = window.URL.createObjectURL(xhr.response);
                    video.play();
                    video.preload = "metadata";
                    video.controls = "controls";
                    video.type = item.mime;
                    video.size = "150";
                    video.height = "150";


                    td.appendChild(video);
                }


                else if (item.mime.toLowerCase().indexOf("image/") > -1) {
                    var img = document.createElement("img");
                    img.src = "";
                    img.src = window.URL.createObjectURL(xhr.response);

                    img.size = "150";
                    img.height = "150";
                    img.alt = "alt";

                    td.appendChild(img);
                }


            }
        );

    }

}]);


demoApp.controller("MedicalController", ['$scope', '$http', '$routeParams', function ($scope, $http, $routeParams) {

    $scope.addMedicalRecord = {};
    $scope.addMedicalRecord.category = "heart";
    $scope.addMedicalRecord.sick = {};
    $scope.addMedicalRecord.sick.id = $routeParams.id;


    $scope.updateMedicalRecord = {};
    $scope.updateMedicalRecord.sick = {};
    $scope.updateMedicalRecord.sick.id = $routeParams.id;


    $scope.updateMedical = function (item) {

        $scope.updateMedicalRecord.id = item.id;
        $scope.updateMedicalRecord.sick = {};
        $scope.updateMedicalRecord.sick.id = item.sick.id;
        $scope.updateMedicalRecord.category = item.category;
        $scope.updateMedicalRecord.description = item.description;


    }

    $http.post("/kasra/medical/record/getBySickId", $scope.addMedicalRecord.sick).success(
        function (data) {
            $scope.medicalRecordList = data;
        });


    $scope.add = function () {
        $http.put('/kasra/medical/record/add', $scope.addMedicalRecord).success(
            function (data) {
                $scope.medicalRecordList = data;
            });
    }


    $scope.update = function () {
        $http.put('/kasra/medical/record/update', $scope.updateMedicalRecord).success(
            function (data) {
                $scope.medicalRecordList = data;
            });
    }


    $scope.delete = function (item) {

        var d = {
            id: item.id,
            sick: {id: $routeParams.id},
            category: item.category,
            description: item.description
        }
        $http.post('/kasra/medical/record/delete', d).success(
            function (data) {
                $scope.medicalRecordList = data;
            });
    }

}]);


demoApp.controller("RecourseController", ['$scope', '$http', '$routeParams', function ($scope, $http, $routeParams) {

    $scope.addRecourse = {};
    $scope.addRecourse.sick = {};
    $scope.addRecourse.sick.id = $routeParams.id;


    $scope.updateRecourse = {};
    $scope.updateRecourse.sick = {};
    $scope.updateRecourse.sick.id = $routeParams.id;


    $scope.updateRecourseFunc = function (item) {

        $scope.updateRecourse.id = item.id;
        $scope.updateRecourse.sick = {};
        $scope.updateRecourse.sick.id = item.sick.id;
        $scope.updateRecourse.diseaseName = item.diseaseName;


    }

    $http.post("/kasra/recourse/getBySickId", $scope.addRecourse.sick).success(
        function (data) {
            $scope.recourseList = data;
        });


    $scope.add = function () {
        $http.put('/kasra/recourse/add', $scope.addRecourse).success(
            function (data) {
                $scope.recourseList = data;
            });
    }


    $scope.update = function () {
        $http.put('/kasra/recourse/update', $scope.updateRecourse).success(
            function (data) {
                $scope.recourseList = data;
            });
    }


    $scope.delete = function (item) {

        var d = {
            id: item.id,
            sick: {id: $routeParams.id},
            diseaseName: item.diseaseName
        }
        $http.post('/kasra/recourse/delete', d).success(
            function (data) {
                $scope.recourseList = data;
            });
    }

}]);


demoApp.controller("PrescriptionController", ['$scope', '$http', '$routeParams', function ($scope, $http, $routeParams) {

    $scope.addPrescription = {};
    $scope.addPrescription.recourse = {};
    $scope.addPrescription.recourse.id = $routeParams.id;


    $scope.updatePrescription = {};
    $scope.updatePrescription.recourse = {};
    $scope.updatePrescription.recourse.id = $routeParams.id;


    $scope.updatePrescriptionFunc = function (item) {

        $scope.updatePrescription.id = item.id;
        $scope.updatePrescription.recourse = {};
        $scope.updatePrescription.recourse.id = item.recourse.id;
        $scope.updatePrescription.description = item.description;


    }

    $http.post("/kasra/prescription/getByRecourseId", $scope.addPrescription.recourse).success(
        function (data) {
            $scope.prescriptionList = data;
        });


    $scope.add = function () {
        $http.put('/kasra/prescription/add', $scope.addPrescription).success(
            function (data) {
                $scope.prescriptionList = data;
            });
    }


    $scope.update = function () {
        $http.put('/kasra/prescription/update', $scope.updatePrescription).success(
            function (data) {
                $scope.prescriptionList = data;
            });
    }


    $scope.delete = function (item) {

        var d = {
            id: item.id,
            recourse: {id: $routeParams.id},
            description: item.description
        };
        $http.post('/kasra/prescription/delete', d).success(
            function (data) {
                $scope.prescriptionList = data;
            });
    }

}]);


demoApp.controller("LogController", ['$scope', '$http', '$routeParams', function ($scope, $http, $routeParams) {


    $scope.addPrescription = {};
    $scope.addPrescription.recourse = {};
    $scope.addPrescription.recourse.id = $routeParams.id;


    $scope.updatePrescription = {};
    $scope.updatePrescription.recourse = {};
    $scope.updatePrescription.recourse.id = $routeParams.id;


    $scope.updatePrescriptionFunc = function (item) {

        $scope.updatePrescription.id = item.id;
        $scope.updatePrescription.recourse = {};
        $scope.updatePrescription.recourse.id = item.recourse.id;
        $scope.updatePrescription.description = item.description;


    }

    $http.post("/resources/log/getAll", $scope.addPrescription.recourse).success(
        function (data) {
            $scope.logList = data;
        });


    $scope.delete = function (item) {

        $http.put("/resources/log/delete", item).success(
            function (data) {
                $scope.logList = data;
            });

    }


}]);


demoApp.controller("XMLController", ['$scope', '$http', '$routeParams', function ($scope, $http, $routeParams) {

    $scope.change = function () {

        var inputs = document.getElementById("xml").getElementsByTagName("input");
        for (var i = 0; i < inputs.length; i++) {

            if (inputs.item(i).type.toLowerCase() == "file") {
                var file = inputs.item(i);

                var button = document.getElementById("xml").getElementsByTagName("input")[1];
                button.disabled = true;
                file.onchange = null;

                file.onchange = function () {


                    if (typeof FileReader == "function") {
                        var divs = this.nextSibling.nextSibling.getElementsByTagName("div");
                        var x = ajax.mimeContentType(this);
                        var str = "";

                        for (var i = 0; i < x.length; i++) {
                            str = str + x[i] + " ";
                        }


                        if (str.indexOf(".xml") > -1) {

                            button.disabled = false;
                        }
                        else {
                            button.disabled = true;
                        }


                        divs.item(0).innerHTML = str;
                        var video = divs.item(1).getElementsByTagName("video");
                        if (video != null && video.length == 1) {
                            video.item(0).src = "";
                        }
                        divs.item(1).innerHTML = null;
                        ajax.fileReader(this, divs.item(1), 500, 200, 45);

                    }

                }
            }

        }

    }


    $scope.change();

    $scope.upload = function (id) {

        var xml = document.getElementById(id).getElementsByTagName("textarea")[0].value.toString();

        $http({
            method: 'POST',
            url: '/kasra/xml/read',
            data: xml,
            headers: {"Content-Type": 'application/xml'}
        });

    }


}]);


demoApp.controller("UploadAnotherHostController", ['$scope', '$http', function ($scope, $http) {

    $scope.upload = function (id) {

        var form = document.getElementById(id);
        var percent = document.getElementById("percent");


        var progressive = function (percents) {
            percent.innerHTML = percents + "%";
            percent.style.width = percents + "%";
        }
        var complete = function (str) {
            percent.innerHTML = "100%";
            alert(str);
        }
        var failed = function () {
            alert("upload failed");
        }
        var cancel = function () {
            alert("upload cancel");
        }

        ajax.upload(form, progressive, complete, failed, cancel, true);

    }


    var inputs = document.getElementById("myAnotherHostupload").getElementsByTagName("input");

    $scope.change = function () {

        for (var i = 0; i < inputs.length; i++) {

            if (inputs.item(i).type.toLowerCase() == "file") {
                var file = inputs.item(i);

                file.onchange = null;

                file.onchange = function () {


                    percent.innerHTML = "0%";
                    percent.style.width = "0%";
                    if (typeof FileReader == "function") {
                        var divs = this.nextSibling.nextSibling.getElementsByTagName("div");
                        var x = ajax.mimeContentType(this);
                        var str = "";

                        for (var i = 0; i < x.length; i++) {
                            str = str + x[i] + " ";
                        }


                        divs.item(0).innerHTML = str;
                        var video = divs.item(1).getElementsByTagName("video");
                        if (video != null && video.length == 1) {
                            video.item(0).src = "";
                        }
                        divs.item(1).innerHTML = null;
                        ajax.fileReader(this, divs.item(1), 500, 200, 45);

                    }

                }
            }

        }

    }


    $scope.change();


}]);


demoApp.controller("JmsController", ['$scope', '$http', function ($scope, $http) {

    $scope.jsons = [];
    $scope.addItem = {};
    $scope.addItem.sex = "1";
    $scope.personList = [];


    $http.post('/kasra/sick/getAll').success(
        function (data) {
            $scope.sickList = data;
        });


    $scope.add = function () {
        $http.put('/resources/jms/queue/add', $scope.addItem).success(
            function (data) {


                setTimeout(function () {

                    $http.post('/kasra/sick/getAll').success(
                        function (data) {
                            $scope.sickList = data;
                        });

                }, 2000);


            });
    }


    $scope.delete = function (item) {

        var d = {
            id: item.id,
            name: item.name,
            shortName: item.shortName
        }
        $http.post('/kasra/sick/delete', d).success(
            function (data) {
                $scope.sickList = data;
            });
    }


    $scope.sendJmsMessages = function () {

        $http.put("/resources/jms/topic/durable/share", $scope.personList).success(
            function (data) {
                $scope.personList = data;
            });

    }

    $scope.deleteMessage = function (item) {

        var index = $scope.personList.indexOf(item);
        $scope.personList.splice(index, 1);
    }

    $scope.addMessage = function () {

        var person = {
            id: parseInt($scope.person.id),
            firstName: $scope.person.firstName,
            lastName: $scope.person.lastName
        };

        $scope.personList.splice($scope.personList.length - 1, 0, person);

        $scope.personList.sort(function (a, b) {
            if (a.id > b.id) {
                return true;
            }
            return false;
        });
    }

}]);


demoApp.controller("JtaController", ['$scope', '$http', function ($scope, $http) {


    $http.get("/resources/jta/native/member/getAll").success(
        function (data) {
            $scope.memberList = data;
        });


    $scope.change = function () {

        if ($scope.search != null && $scope.search != "") {

            $http.get("/resources/jta/native/member/lastname/" + $scope.search).success(
                function (data) {
                    $scope.memberNativeList = data;
                });
        }

    }


    $scope.save = function () {

        var member = {};
        //member.id = $scope.member.id;
        member.firstName = $scope.member.firstName;
        member.lastName = $scope.member.lastName;
        member.memberBiography = {};
        //member.memberBiography.id = $scope.member.memberBiography.id;////////
        member.memberBiography.university = $scope.member.memberBiography.university;
        member.memberBiography.age = $scope.member.memberBiography.age;


        $http.put("/resources/jta/save/member", member).success(
            function (data) {
                $scope.memberList = data;
            });

    }


    $scope.update = function () {

        var member = {};
        member.id = $scope.memberUpdate.id;
        member.firstName = $scope.memberUpdate.firstName;
        member.lastName = $scope.memberUpdate.lastName;
        member.memberBiography = {};
        member.memberBiography.id = $scope.memberUpdate.memberBiography.id;////////
        member.memberBiography.university = $scope.memberUpdate.memberBiography.university;
        member.memberBiography.age = $scope.memberUpdate.memberBiography.age;


        $http.put("/resources/jta/update/member", member).success(
            function (data) {
                $scope.memberList = data;
            });

    }

    $scope.updateRecord = function (item) {

        if (typeof $scope.memberUpdate === 'undefined') {
            $scope.memberUpdate = {};
        }

        if (typeof $scope.memberUpdate.memberBiography === 'undefined') {
            $scope.memberUpdate.memberBiography = {};
        }

        $scope.memberUpdate.id = item.id;
        $scope.memberUpdate.firstName = item.firstName;
        $scope.memberUpdate.lastName = item.lastName;
        $scope.memberUpdate.memberBiography.id = item.memberBiography.id;
        $scope.memberUpdate.memberBiography.university = item.memberBiography.university;
        $scope.memberUpdate.memberBiography.age = item.memberBiography.age;

    }


    $scope.delete = function (item) {

        $http.put("/resources/jta/delete/member", item).success(
            function (data) {
                $scope.memberList = data;
            });

    }


    $scope.saveContainerMember = function () {

        var member = {};
        member.id = $scope.memberUpdate.id;
        member.firstName = $scope.memberUpdate.firstName;
        member.lastName = $scope.memberUpdate.lastName;
        member.memberBiography = {};
        member.memberBiography.id = $scope.memberUpdate.memberBiography.id;////////
        member.memberBiography.university = $scope.memberUpdate.memberBiography.university;
        member.memberBiography.age = $scope.memberUpdate.memberBiography.age;


        $http.put("/resources/jta/save/container/member", member).success(
            function (data) {
                $scope.memberList = data;
            });

    }

    $scope.saveContainerMemberWithException = function () {

        var member = {};
        member.id = $scope.memberUpdate.id;
        member.firstName = $scope.memberUpdate.firstName;
        member.lastName = $scope.memberUpdate.lastName;
        member.memberBiography = {};
        member.memberBiography.id = $scope.memberUpdate.memberBiography.id;////////
        member.memberBiography.university = $scope.memberUpdate.memberBiography.university;
        member.memberBiography.age = $scope.memberUpdate.memberBiography.age;


        $http.put("/resources/jta/save/container/exception/member", member).success(
            function (data) {
                $scope.memberList = data;
            });

    }

    $scope.saveBeanMember = function () {

        var member = {};
        member.id = $scope.memberUpdate.id;
        member.firstName = $scope.memberUpdate.firstName;
        member.lastName = $scope.memberUpdate.lastName;
        member.memberBiography = {};
        member.memberBiography.id = $scope.memberUpdate.memberBiography.id;////////
        member.memberBiography.university = $scope.memberUpdate.memberBiography.university;
        member.memberBiography.age = $scope.memberUpdate.memberBiography.age;


        $http.put("/resources/jta/save/bean/member", member).success(
            function (data) {
                $scope.memberList = data;
            });

    }


}]);


demoApp.controller("CdiController", ['$scope', '$http', function ($scope, $http) {

    $scope.disposes = function () {

        $http.put("/resources/cdi/disposes").success(
            function (data) {
                $scope.disposes = data;
            });

    }

    $scope.event = function () {

        $http.put("/resources/cdi/event").success(
            function (data) {
                $scope.event = data;
            });
    }


    $scope.alternative = function () {

        $http.put("/resources/cdi/alternative").success(
            function (data) {
                $scope.alternative = data;
            });
    }


    $scope.decorator = function () {

        $http.put("/resources/cdi/decorator").success(
            function (data) {
                $scope.decorator = data;
            });
    }

}]);


demoApp.controller("ChatroomController", ['$rootScope', '$scope', '$http', '$location', '$timeout', function ($rootScope, $scope, $http, $location, $timeout) {

    //$scope.send = null;
    //$scope.close = null;
    $scope.country = "US";
    $scope.color = "#000000";
    $scope.prevColor = "#000000";
    $scope.size = 5;
    $scope.sketchFlag = false;
    $rootScope.websocket;
    $rootScope.websocketCanvas;
    $scope.room = $rootScope.selectRoom;

    if ("WebSocket" in window) {

        var text = document.getElementsByTagName("textarea").item(0);
        var usersBox = document.getElementsByTagName("textarea").item(1);
        var textMessage = document.getElementById("text-chatMessage");

        if ($rootScope.websocket == null) {
            $rootScope.websocket = new WebSocket("ws://" + $location.host() + ":" + $location.port() + "/chatrooms/" + $rootScope.selectRoom);
            $rootScope.websocket.onopen = function (message) {
                text.value += "open\n";
            };

            $rootScope.websocket.onclose = function (message) {
                console.log("close");
                $scope.send = null;
                $rootScope.websocket = null;
                $timeout(function () {
                    var path = $location.path();
                    if (path == "/chatroom") {
                        path = "/login";
                    }
                    $location.path(path);
                }, 1000);

            };

            $rootScope.websocket.onerror = function (message) {
                //text.value += "error ...\n";
                console.log("error");
            };

            $rootScope.websocket.onmessage = function (message) {

                var json = JSON.parse(message.data);

                if (!(typeof json.message == "undefined")) {

                    if (json.name == "null") {
                        $scope.close();

                        $timeout(function () {
                            $location.path("/login");
                        }, 1000);
                    }

                    if (json.message != "null") {
                        text.value += json.name + "(" + json.location + ")" + ":" + json.message + "\n";
                        $scope.room = json.room;
                    }
                }
                else if (!(typeof json.users == "undefined")) {
                    usersBox.value = "";
                    for (i in json.users) {
                        usersBox.value += json.users[i] + "\n";
                    }
                }


            };


            $scope.send = function () {
                $scope.websocket.send(JSON.stringify({'message': textMessage.value, 'location': $scope.country}));
                textMessage.value = "";
            };

            $scope.close = function () {
                if ($scope.websocket != null) {
                    $scope.websocket.close();
                    $timeout(function () {
                        $location.path("/login");
                    }, 1000);
                    $scope.websocket = null;
                }
            };

        }

        //if ($rootScope.websocketCanvas == null) {
        $rootScope.websocketCanvas = new WebSocket("ws://" + $location.host() + ":" + $location.port() + "/sketchServerEndpoint");

        var sketchCanvas = document.getElementById("sketchCanvas");
        var context = sketchCanvas.getContext("2d");

        $scope.sketch = function (x, y, size, color) {

            context.beginPath();
            context.fillStyle = color;
            context.fillRect(x, y, size, size);
            context.closePath();
        }

        $rootScope.websocketCanvas.onmessage = function (sketchMessage) {
            var sketchData = JSON.parse(sketchMessage.data);
            $scope.sketch(sketchData.x, sketchData.y, sketchData.size, sketchData.color);

        }


        sketchCanvas.style.border = "solid";


        context.canvas.onmousemove = function (event) {
            var positionX = event.clientX - this.offsetLeft;
            var positionY = event.clientY - this.offsetTop;

            if ($scope.sketchFlag == true) {
                $scope.sketch(positionX, positionY, $scope.size, $scope.color);
                $rootScope.websocketCanvas.send(JSON.stringify({
                    x: positionX,
                    y: positionY,
                    size: $scope.size,
                    color: $scope.color
                }));
            }

        }

        context.canvas.onmousedown = function (event) {
            $scope.sketchFlag = true;
        }

        context.canvas.onmouseup = function (event) {
            $scope.sketchFlag = false;
        }

        context.canvas.onmouseleave = function (event) {
            $scope.sketchFlag = false;
        }


        $scope.chooseColor = function () {
            $scope.prevColor = $scope.color;
            $scope.color = $scope.eventColor;

        }

        $scope.toggleState = function () {
            var event = document.getElementById("erase");
            if (event.value == "Erase") {
                event.value = "Sketch";
                $scope.prevColor = $scope.color;
                $scope.color = "#FFFFFF";
                $scope.size = 5;

                document.getElementById("colorPicker").style.visibility = "hidden";
                document.getElementById("sketchCanvas").style.cursor = "text";

            }
            else {

                event.value = "Erase";
                $scope.color = $scope.prevColor;
                $scope.size = 5;

                event.style.visibility = "visible";

                document.getElementById("colorPicker").style.visibility = "visible";
                document.getElementById("sketchCanvas").style.cursor = "auto";

            }
        }

        //}

    }


}]);


demoApp.controller("LiveVideoController", ['$rootScope', '$scope', '$http', '$location', '$timeout', function ($rootScope, $scope, $http, $location, $timeout) {

    var video = document.getElementById("live");
    var canvas = document.getElementById("canvas");
    var image = document.getElementById("image");
    var stream = document.getElementById("stream");

    var ctx = canvas.getContext('2d');
    var options = {
        "video": true
        // , "audio": true,
        // "desktop": true
    };


    // Put video listeners into place
    window.URL = window.URL || window.webkitURL;
    navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia || navigator.msGetUserMedia;

    window.URL = window.URL || window.webkitURL;
    // use the chrome specific GetUserMedia function
    $scope.myStream = null;
    var timer = null;
    $scope.ws = null;


    $scope.capture = function () {

        if (navigator.getUserMedia) {
            navigator.getUserMedia

            (options, function (stream) {

                $scope.myStream = stream;
                video.src = window.URL.createObjectURL(stream);

            }, function (err) {
                console.log("Unable to get video stream!")

            });

        }

    }

    $scope.videoStream = "ws://" + $location.host() + ":" + $location.port() + "/livevideo";
    stream.src = "blob:" + $scope.videoStream;


    $scope.open = function () {


        //image.src = $scope.videoStream;
        $scope.ws = new WebSocket($scope.videoStream);
        $scope.ws.binaryType = 'arrayBuffer';


        $scope.ws.onopen = function () {
            console.log("Openened connection to websocket");
        }


        $scope.message = null;


        $scope.ws.onmessage = function (message) {

            var target = document.getElementById("target");
            url = window.URL.createObjectURL(message.data);
            target.onload = function () {
                window.URL.revokeObjectURL(url);
            };
            target.src = url;


        }


        timer = setInterval(
            function () {

                if ($scope.ws) {

                    if ($scope.myStream) {
                        ctx.drawImage(video, 0, 0, 320, 240);
                        //var data = canvas.toDataURL('image/jpeg', 1.0);
                        var data = canvas.toDataURL('video/mp4', 1.0);
                        var newblob = ajax.convertToBinary(data);
                        $scope.ws.send(newblob);
                    }


                }

            }, 250);


    }


    $scope.close = function () {

        if ($scope.myStream) {
            $scope.myStream.getTracks()[0].stop();
        }

        if ($scope.ws != null && typeof $scope.ws.close !== 'undefined') {
            $scope.ws.close();
        }

        clearInterval(timer);


    }


}]);


demoApp.controller("LoginController", ['$rootScope', '$scope', '$http', '$location', '$timeout', function ($rootScope, $scope, $http, $location, $timeout) {


    $http.post("/resources/MyRestService/json/getSick", [{
        id: 1,
        firstName: "kasra",
        lastName: "hagh",
        "sex": 1
    }]).success(
        function (data) {
            $scope.sick = data;
        });


    var xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><sicks><sick id=\"0\"> <firstName>kasra1</firstName> <lastName>haghpanah1</lastName> <sex>1</sex> </sick> <sick id=\"0\"><firstName>farah1</firstName><lastName>diba1</lastName> <sex>0</sex></sick></sicks>";

    $http({
        method: 'POST',
        url: '/resources/MyRestService/xml/getSicks',
        data: xml,
        headers: {"Content-Type": 'application/xml'}
    }).success(
        function (data) {
            $scope.sickList = data;
        });


    $scope.name = "";
    $scope.country = "US";
    $scope.room = "room1";
    $scope.newRoom = "";
    $scope.rooms = ["Room1", "Room2", "Room3", "New_Room"];

    $rootScope.selectRoom = $scope.room;

    $scope.add = function () {
        if ($scope.newRoom != "") {
            var length = $scope.rooms.length;
            $scope.rooms.splice(length - 1, 0, $scope.newRoom);
            $scope.room = $scope.newRoom.toLowerCase();
        }
        $rootScope.selectRoom = $scope.room;
    }


    $scope.login = function () {

        $rootScope.selectRoom = $scope.room;

        $http.put("/kasra/login/signin", {name: $scope.name, location: $scope.country}).success(
            function (data) {
                $timeout(function () {
                    $location.path("/chatroom");
                }, 1000);
            });
    }

    $scope.signout = function () {

        $http.put("/kasra/login/signout").success(
            function (data) {

            });
    }

}]);


demoApp.controller("MailController", ['$scope', '$http', '$routeParams', function ($scope, $http, $routeParams) {

    $scope.send = function () {

        $http.put("/kasra/mail/send?message=" + $scope.message.toUnicode() + "&email=" + $scope.email.toUnicode() + "&subject=" + $scope.subject.toUnicode() + "&filename=" + $scope.filename.toUnicode()).success(
            function (data) {
                alert(data.data);
                console.log(data.property);
            });

    }

}]);


demoApp.controller("UserController", ['$scope', '$http', '$routeParams', function ($scope, $http, $routeParams) {

    $http.post("/kasra/user/getAll").success(
        function (data) {
            $scope.userList = data;
        }
    );

    $scope.add = function () {

        $http.put("/kasra/user/add", [{username: $scope.addItem.username, password: $scope.addItem.password}]).success(
            function (data) {
                $scope.userList = data;
            }
        );
    }

    $scope.update = function () {

        $http.put("/kasra/user/update", {
            id: $scope.updateItem.id,
            username: $scope.updateItem.username,
            password: $scope.updateItem.password
        }).success(
            function (data) {
                $scope.userList = data;
            }
        );
    }


    $scope.updateUser = function (item) {
        $scope.updateItem = {};
        $scope.updateItem.id = item.id;
        $scope.updateItem.username = item.username;

        $scope.updateItem.password = "";//item.password;
    }


    $scope.delete = function (item) {
        $http.post("/kasra/user/delete", {id: item.id, username: item.username, password: item.password}).success(
            function (data) {
                $scope.userList = data;
            }
        );
    }

}]);

demoApp.controller("GroupController", ['$scope', '$http', function ($scope, $http) {


    $scope.roles = ["Admin", "User"];
    $scope.role = $scope.roles[0].toLowerCase();

    $http.post("/kasra/user/getAll").success(
        function (data) {
            $scope.userList = data;
            if (data.length > 0) {
                $scope.user = JSON.stringify(data[0]);
            }
        }
    );

    $http.post("/kasra/group/getAll").success(
        function (data) {
            $scope.groupList = data;
        }
    );


    $scope.add = function () {


        $http.put("/kasra/group/add", {user: JSON.parse($scope.user), role: $scope.role}).success(
            function (data) {
                $scope.groupList = data;
            }
        );
    }

    $scope.update = function () {

        $http.put("/kasra/group/update", {
            id: $scope.updateItem.id,
            user: $scope.updateItem.user,
            role: $scope.updateItem.role
        }).success(
            function (data) {
                $scope.groupList = data;
            }
        );
    }


    $scope.updateGroup = function (item) {
        $scope.updateItem = {};
        $scope.updateItem.id = item.id;
        $scope.updateItem.user = item.user;
        $scope.updateItem.role = item.role;
    }


    $scope.delete = function (item) {
        $http.post("/kasra/group/delete", {id: item.id, user: item.user, role: item.role}).success(
            function (data) {
                $scope.groupList = data;
            }
        );
    }


}]);


demoApp.controller("BatchController", ['$scope', '$http', function ($scope, $http) {

    $scope.getAll = function () {

        $http.get("/resources/batch/getAll").success(
            function (data) {
                $scope.personList = data;
            }
        );

    }

    $scope.getAll();

    $scope.firstJobChunkSimple = function () {

        $http.post("/resources/batch/firstJobChunkSimple").success(
            function (data) {
                $scope.personList = data;
            }
        );

    }


    $scope.mapperPartitionBatch = function () {

        $http.post("/resources/batch/mapperPartitionBatch").success(
            function (data) {
                $scope.personList = data;
            }
        );

    }

    $scope.planPartitionBatch = function () {

        $http.post("/resources/batch/planPartitionBatch").success(
            function (data) {
                $scope.personList = data;
            }
        );

    }


    $scope.secondJobWithBatchlet = function () {

        $http.post("/resources/batch/secondJobWithBatchlet").success(
            function (data) {
                $scope.personList = data;
            }
        );

    }


    $scope.delete = function (item) {

        $http.post("/resources/batch/delete", item).success(
            function (data) {
                $scope.personList = data;
            }
        );
    }

}]);
