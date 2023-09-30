/**
 * Created by kasra.haghpanah on 01/09/2016.
 */
(function () {

    if (typeof window.dec2hex === "undefined") {
        window.dec2hex = function (textString) {
            return (textString + 0).toString(16).toUpperCase();
        }
    }

    if (typeof window.hex2char === "undefined") {
        window.hex2char = function (hex) {
            // converts a single hex number to a character
            // note that no checking is performed to ensure that this is just a hex number, eg. no spaces etc
            // hex: string, the hex codepoint to be converted
            var result = '';
            var n = parseInt(hex, 16);
            if (n <= 0xFFFF) {
                result += String.fromCharCode(n);
            }
            else if (n <= 0x10FFFF) {
                n -= 0x10000
                result += String.fromCharCode(0xD800 | (n >> 10)) + String.fromCharCode(0xDC00 | (n & 0x3FF));
            }
            else {
                result += 'hex2Char error: Code point out of range: ' + window.dec2hex(n);
            }
            return result;
        }
    }

    if (typeof String.convertUnicode2Char === "undefined") {

        String.prototype.convertUnicode2Char = function () {
            // converts a string containing U+... escapes to a string of characters
            // str: string, the input

            var str = this;
            // first convert the 6 digit escapes to characters
            //   /[Uu]\+10([A-Fa-f0-9]{4})/g
            str = str.replace(/\\[U|u][0-9a-fA-F]{4}/g,
                function (matchstr, parens) {
                    //return hex2char('10' + parens);
                    matchstr = matchstr.replace("\\u", "");
                    matchstr = matchstr.replace("\\U", "");
                    return hex2char(matchstr);
                }
            );
            // next convert up to 5 digit escapes to characters
            str = str.replace(/[\\U|\\u]\+([A-Fa-f0-9]{1,5})/g,
                function (matchstr, parens) {
                    //return  hex2char(parens);
                    matchstr = matchstr.replace("\\u", "");
                    matchstr = matchstr.replace("\\U", "");
                    return hex2char(matchstr);
                }
            );
            return str;
        }
    }

    if (typeof String.toUnicode === "undefined") {
        String.prototype.toUnicode = function () {
            var result = "";
            for (var i = 0; i < this.length; i++) {
                result += "\\u" + ("000" + this[i].charCodeAt(0).toString(16)).substr(-4);
            }
            return result;
        };
    }

    if (typeof window.ajax === "undefined") {

        window.ajax = {
            xhr: null,
            mimeContentType: function (fileElement) {

                if (!fileElement.files)return false;//if browser does not support
                var file = fileElement.files[0];

                var output = new Array();
                var size = file.size;
                if (size >= 1024 * 1024)size = (Math.round(size / (1024 * 1024))).toString() + ' MB';
                else if (size >= 1024 && size < 1024 * 1024)size = (Math.round(size / 1024)).toString() + ' KB';
                else size = size + " bytes";
                output[0] = size;
                output[1] = file.type;
                output[2] = file.name;
                return output;
            },

            uploadSupport: function (form) {
                var inputs = document.getElementsByTagName("input");
                if (typeof FormData == "function") {
                    for (var i = 0; i < inputs.length; i++)
                        if (inputs.item(i).type == "submit")inputs.item(i).type = "button";
                }

            },

            upload: function (formElement, funcProgress, funcComplete, funcFailed, funcCancel, isUnicode) {

                if (typeof FormData != "function")return false;//if browser does not support

                var fd = new FormData();


                var inputs = formElement.getElementsByTagName("input");
                for (var i = 0; i < inputs.length; i++) {

                    if (inputs.item(i).name != "") {

                        if (inputs.item(i).type == "file") {
                            if (typeof inputs.item(i).files[0] != "undefined") {
                                var nameParameter = inputs.item(i).name;
                                if (isUnicode) {
                                    nameParameter = nameParameter.toUnicode();
                                    if (inputs.item(i).files[0] != null) {
                                        nameParameter = inputs.item(i).files[0].name.toUnicode();
                                    }
                                }
                                fd.append(nameParameter, inputs.item(i).files[0]);
                            }

                        }

                        else if (inputs.item(i).type.toLowerCase() != "checkbox") {

                            if (inputs.item(i).value == null) {
                                inputs.item(i).value = "";
                            }

                            if (inputs.item(i).value != "") {
                                fd.append(
                                    (isUnicode) ? inputs.item(i).getAttribute("name").toUnicode() : inputs.item(i).getAttribute("name"),
                                    inputs.item(i).value
                                );
                            }

                        }
                        else if (inputs.item(i).checked) {
                            fd.append(
                                (isUnicode) ? inputs.item(i).getAttribute("name").toUnicode() : inputs.item(i).getAttribute("name"),
                                inputs.item(i).value
                            );
                        }

                    }
                }

                var selects = formElement.getElementsByTagName("select");
                for (var i = 0; i < selects.length; i++) {
                    var name = selects[i].getAttribute("name");
                    if (isUnicode) {
                        name = name.toUnicode();
                    }
                    fd.append(name, selects[i].value);
                }


                if (typeof XMLHttpRequest == 'function')this.xhr = new XMLHttpRequest();
                else this.xhr = new ActiveXObject("Microsoft.XMLHTTP");

                this.xhr.upload.onprogress = function (evt) {

                    if (evt.lengthComputable) {
                        var percentComplete = Math.round(evt.loaded * 100 / evt.total);
                        funcProgress(percentComplete);
                    }

                    else funcProgress("error");

                }


                this.xhr.onload = function (evt) {
                    funcComplete(evt.target.responseText);
                }

                this.xhr.onerror = function () {
                    funcFailed();//alert("There was an error attempting to upload the file.");
                }
                this.xhr.onabort = function (evt) {
                    funcCancel();//alert("The upload has been canceled by the user or the browser dropped the connection.");
                }

                this.xhr.open(formElement.getAttribute("method").toUpperCase(), formElement.getAttribute("action"));
                this.xhr.send(fd);


            },

            fileReader: function (file, element, width, height, bigSizeByMB) {

                if (typeof FileReader != "function")return false;

                file = file.files[0];
                var size = Math.round(file.size / (1024 * 1024));
                var show = true;
                if (bigSizeByMB != "" && size > bigSizeByMB)show = false;

                var reader = new FileReader();
                var type = file.type;
                //alert(type);

                reader.onload = function (event) {

                    var dataUri = event.target.result;//mohtaviat file dar dataUri zakhire mishavad


                    if (type.indexOf("text") == 0 && show) {
                        //var x=reader.readAsText(file);
                        var text = document.createElement("textarea");
                        text.innerHTML = dataUri;
                        if (width != "" || height != "")
                            text.setAttribute("style", "width:" + width + "px;height:" + height + "px;");
                        else text.setAttribute("style", "width:150px;height:150px;");
                        element.appendChild(text);
                    }
                    else if (type.indexOf("image") == 0 && show) {
                        var img = document.createElement("img");
                        if (width != "" || height != "")
                            img.setAttribute("style", "width:" + width + "px;height:" + height + "px;");
                        img.src = dataUri;
                        element.appendChild(img);
                    }

                    else if ((type.indexOf("audio") > -1 || type.indexOf("video") > -1 ) && show) {
                        var format = (type.indexOf("video") > -1) ? "video" : "audio";
                        var video = document.createElement(format);
                        video.preload = "metadata";
                        video.controls = "controls";
                        video.type = type;
                        if (width != "" || height != "")
                            video.setAttribute("style", "width:" + width + "px;height:" + height + "px;");
                        var source = document.createElement("source");
                        source.type = type;
                        source.src = dataUri;
                        video.appendChild(source);

                        element.appendChild(video);
                    }
                    else if (type.indexOf("pdf") > -1) {
                        if (typeof window.URL.createObjectURL !== "undefined") {

                            var embed = document.createElement("iframe");
                            embed.setAttribute("src", window.URL.createObjectURL(file));
                            embed.setAttribute("type", "application/pdf");
                            embed.setAttribute("width", width);
                            embed.setAttribute("height", height);
                            element.appendChild(embed);
                        }

                    }

                }

                reader.onerror = function (event) {
                    alert("File could not be read! Code " + event.target.error.code);
                }

                if (type.indexOf("text") > -1) {
                    reader.readAsText(file);
                }
                else reader.readAsDataURL(file);

                //reader.readAsText(file);// baraye khandan besourat text .mohtaviat matn dar dataUri zakhire mishavad

            },

            blobToString: function (b) {
                var u, x;
                u = URL.createObjectURL(b);
                x = new XMLHttpRequest();
                x.open('GET', u, false); // although sync, you're not fetching over internet
                x.send();
                URL.revokeObjectURL(u);
                return x.responseText;
            },

            convertToBinary: function (dataURI) {
                // convert base64 to raw binary data held in a string
                // doesn't handle URLEncoded DataURIs
                var byteString = atob(dataURI.split(',')[1]);

                // separate out the mime component
                var mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0]

                // write the bytes of the string to an ArrayBuffer
                var ab = new ArrayBuffer(byteString.length);
                var ia = new Uint8Array(ab);
                for (var i = 0; i < byteString.length; i++) {
                    ia[i] = byteString.charCodeAt(i);
                }

                // write the ArrayBuffer to a blob, and you're done
                var bb = new Blob([ab]);
                return bb;
            },


            download: function (method, url, isDownload, callbackPercent, callbackXHR) {

                var query = null;
                var address = url;
                var index = url.indexOf("?");
                if (index > -1) {
                    address = url.substring(0, index);
                    query = url.substring(index + 1, url.length);
                }

                if (typeof window.URL.createObjectURL !== "undefined" || typeof window.navigator.msSaveBlob !== 'undefined') {


                    var that = this;

                    var args = arguments;


                    var xhr = new XMLHttpRequest();
                    xhr.responseType = "blob";
                    //xhr.responseType = "arraybuffer";
                    xhr.onreadystatechange = function () {
                        if (xhr.readyState === 4 && xhr.status === 200) {
                            var filename = xhr.getResponseHeader("Content-Disposition").split(";")[1].split("=")[1];
                            filename = filename.convertUnicode2Char();
                            if (args.length > 4) {
                                callbackXHR(xhr);
                            }


                            if (isDownload == true) {
                                if (typeof window.chrome !== 'undefined') {
                                    // Chrome version
                                    var link = document.createElement('a');
                                    link.href = window.URL.createObjectURL(xhr.response);
                                    link.download = filename;
                                    link.click();
                                } else if (typeof window.navigator.msSaveBlob !== 'undefined') {
                                    // IE version
                                    var blob = new Blob([xhr.response], {type: 'application/force-download'});
                                    window.navigator.msSaveBlob(blob, filename);
                                } else if (typeof window.URL.createObjectURL !== 'undefined') {
                                    // Firefox version
                                    var file = new File([xhr.response], filename, {type: 'application/force-download'});
                                    window.open(URL.createObjectURL(file));
                                }


                            }


                        }
                    };

                    xhr.open(method.toUpperCase(), address, true);
                    xhr.addEventListener("progress", function (evt) {
                        if (evt.lengthComputable) {
                            var percentComplete = evt.loaded / evt.total;
                            //console.log(percentComplete);
                            var digit = parseFloat(percentComplete.toFixed(2));
                            if (args.length > 3) {
                                callbackPercent(digit);
                            }

                        }
                    }, false);
                    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

                    //xhr.setRequestHeader("OPENED", "bytes=0-" + xhr.getResponseHeader("Content-Length"));
                    //xhr.setRequestHeader("OPENED", "bytes=0-" + 1024*1024*3);
                    if (query == null) {
                        xhr.send();
                    }
                    else {
                        xhr.send(query);
                    }


                }/////???****
                else {
                    var from = document.createElement("from");
                    from.setAttribute("action", url);
                    from.setAttribute("method", method.toUpperCase());
                    var input = document.createElement("input");
                    input.setAttribute("type", "submit");
                    input.setAttribute("value", "send");
                    from.appendChild(input);
                    input.click();
                }

            },

            sendRequest: function (method, url, data, contentType, success) {
                var xhr;
                if (typeof XMLHttpRequest !== 'undefined') {
                    xhr = new XMLHttpRequest();
                }
                else {
                    var versions = [
                        "MSXML2.XmlHttp.6.0",
                        "MSXML2.XmlHttp.5.0",
                        "MSXML2.XmlHttp.4.0",
                        "MSXML2.XmlHttp.3.0",
                        "MSXML2.XmlHttp.2.0",
                        "Microsoft.XmlHttp"
                    ];

                    for (var i = 0; i < versions.length; i++) {

                        try {
                            xhr = new ActiveXObject(versions[i]);
                            break;
                        } catch (e) {
                        }

                    }
                }

                xhr.open(method.toUpperCase(), url, true);
                xhr.setRequestHeader('Content-Type', contentType);
                if (contentType.indexOf('application/json') > -1) {
                    xhr.send(JSON.stringify(data));
                }
                else {
                    xhr.send(data);
                }
                //xhr.onloadend
                xhr.onreadystatechange = function (event) {
                    if (xhr.readyState == 4 && xhr.status == 200) {
                        //var target = event.target;
                        var contentTypeResponse = xhr.getResponseHeader("Content-Type");
                        var data = null;
                        if (contentTypeResponse.indexOf('application/json') > -1) {
                            data = JSON.parse(xhr.responseText);
                        }
                        else if (contentTypeResponse.indexOf('application/xml') > -1) {
                            data = xhr.responseXML;
                        }
                        else {
                            data = xhr.responseText;
                        }
                        success(data);
                    }
                };
            },
            sendAsJSON: function (method, url, data, success) {
                this.sendRequest(method, url, data, 'application/json; charset=UTF-8', success);
            },
            sendAsXML: function (method, url, data, success) {
                this.sendRequest(method, url, data, 'application/xml; charset=UTF-8', success);
            },
            send: function (method, url, query, success) {
                this.sendRequest(method, url, query, 'application/x-www-form-urlencoded; charset=UTF-8', success);
            },

            clone: function () {
                var obj = {};
                for (var attr in this) {
                    obj[attr] = this[attr];
                }
                return obj;
            }


        }
    }

})();
