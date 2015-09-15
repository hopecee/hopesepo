
define(['plugins/router', 'global/session', 'global/services/logger', 'global/services/imageSelectService'
], function(router, session, logger, imageSelectService
        ) {
    "use strict";
    // $(document).ready(function() {

    // });

    var attached = function(view, paren) {
// userPhotoHandler();

//var userPhotoHandler = function() {
//var  self = view;
        var imgPath;
        if (session.isUserImg()) {
        } else {
            imgPath = '/faces/javax.faces.resource/uploads/user_blank.jpg';
        }


        var user = {
            userphotoImg: ko.observable(imgPath)
        };

        $('.success').hide();
        var userNeo4jId = session.userNeo4jIdStr();
        var userPhotoFile = userNeo4jId + '.jpg';
        // var userphotoImg = '';
        var userTempFile = '/faces/javax.faces.resource/uploads/' + userNeo4jId + '_temp.jpg';
        // var userDefaultImg = '/faces/javax.faces.resource/uploads/user_blank.jpg';
        var userImg = '/faces/javax.faces.resource/uploads/' + session.userNeo4jIdStr() + '.jpg';

        var userphotoImg;
        var img = new Image();
        var checkUserImg = function(userPhotoFile) {
            $.ajax({
                url: '/faces/javax.faces.resource/uploads/' + userPhotoFile,
                cache: false,
                async: false,
                success: function() {
                    userphotoImg = userImg;
                },
                error: function() {
                    userphotoImg = user.userphotoImg();
                    // alert(user.userphotoImg);
                }
            });
        };
        // var getUserImg = function() {
        var getUserImg = function() {
            $.ajax({
                url: userTempFile,
                cache: false,
                async: false,
                success: function() {
                    checkUserImg();
                    // alert('checkUserImg b');
                },
                error: function() {
                    userphotoImg = user.userphotoImg();

                }
            });
        };
        getUserImg();
        var imgWidth, imgHeight;


        $('#userphotoDIV').hide();
        img.onload = function() {
            if (img.height > img.width) {
                $('#userphoto').css({'height': '200px'});
                $('#photoPreview').css({'height': '100px'});
            } else {
                $('#userphoto').css({"height": "", "width": "160px"});
                $('#photoPreview').css({"height": "", "width": "80px"});
            }
            //show the image.
            $('#userphotoDIV').show('fast');
            $('#userphoto').show('fast');
        };
        img.src = userphotoImg + '?' + (new Date()).getTime();



        var userphotoDIV,
                userphotoPreview, userphotoPreview2;
        userphotoDIV = $('#userphotoDIV').html();
        userphotoPreview = $('#userphotoPreview').html();
        userphotoPreview2 = $('#userphotoPreview2').html();

        $("#userphoto").attr('src', userphotoImg + '?' + (new Date()).getTime());



        $('#userphotoPreview').empty();
        $('#userphotoPreview2').empty();



        var uploader = new plupload.Uploader({
            runtimes: 'html5,flash,silverlight,html4', // html5,browserplus,silverlight,flash,html4' Set runtimes, here it will use HTML5, if not supported will use flash, etc.
            browse_button: 'userphotoform:pickfiles',
            url: '/pluploadServlet',
            container: 'uploader', // The id of the upload form container
            // filters: {title: "Image files", extensions: "jpg,gif,png,jpeg"}  // Filter the files that will be showed on the select files window
            filters: {
                mime_types: [
                    {title: "Image files", extensions: "jpg,gif,png,jpeg"}//,
//{ title : "Zip files", extensions : "zip" }
                ]
            },
            max_file_size: '10mb',
            multi_selection: false, // Allow  to select one file each time
            rename: true,
            //unique_names : '#{usersPhotoFile}',

            flash_swf_url: '../lib/plupload_2_1_2/js/Moxie.swf',
            silverlight_xap_url: '../lib/plupload_2_1_2/js/Moxie.xap'//,
                    //flash_swf_url : '#{request.contextPath}/resources/plupload_1_ 5_4/js/plupload.flash.swf', 
                    //silverlight_xap_url : '#{request.contextPath}/resources/plupload_1_5_4/js/plupload.silverlight.xap', 


        });
        // RUNTIME
        uploader.bind('Init', function(up, params) {
            $('#runtime').text(params.runtime);
        });
        uploader.init(); // Initializes the Uploader instance and adds internal event listeners.

        document.getElementById('userphotoform:uploadfiles').classList.add("ui-state-disabled");
        document.getElementById('userphotoform:savefiles').classList.add("ui-state-disabled");
        document.getElementById('userphotoform:uploadfiles').disabled = true;
        document.getElementById('userphotoform:savefiles').disabled = true;
        // Start Upload ////////////////////////////////////////////
        // When the button with the id "#uploadfiles" is clicked the upload will start
        document.getElementById('userphotoform:uploadfiles').onclick = function(e) {

            uploader.start();
            e.preventDefault();
        };

        uploader.bind('FilesAdded', function(up, files) {
            $.each(files, function(i, file) {
                $('#filelist').append('<div class="addedFile" id="' + file.id + '">' + file.name + '<a href="#" id="' + file.id + '" class="removeFile"></a>' + '</div>');
            });
            up.refresh(); // Reposition Flash/Silverlight
            document.getElementById('userphotoform:uploadfiles').classList.remove("ui-state-disabled");
            document.getElementById('userphotoform:uploadfiles').disabled = false;
            document.getElementById('userphotoform:savefiles').classList.add("ui-state-disabled");
            document.getElementById('userphotoform:savefiles').disabled = true;
        });
        // Progress bar ////////////////////////////////////////////
        // Add the progress bar when the upload starts
        // Append the tooltip with the current percentage
        uploader.bind('UploadProgress', function(up, file) {
            var progressBarValue = up.total.percent;
            $('#progressbar').fadeIn().progressbar({
                value: progressBarValue
            });
            $('#progressbar .ui-progressbar-value').html('<span class="progressTooltip">' + up.total.percent + '%</span>');
        });
        // Error Alert /////////////////////////////////////////////
        // If an error occurs an alert window will popup with the error code and error message.
        // Ex: when a user adds a file with now allowed extension

        uploader.bind('Error', function(up, err) {
            alert("Error: " + err.code + ", Message: " + err.message + (err.file ? ", File: " + err.file.name : "") + "");
            up.refresh(); // Reposition Flash/Silverlight
        });


        var busy;
        uploader.bind('BeforeUpload', function(up, file) {
            file.name = userPhotoFile;
            $('#userphotoDIV').empty();
            busy = getBusyOverlay(document.getElementById('userphotoPreviewBusy'), {color: 'white', opacity: 0.05, text: '', style: 'text-shadow: 0 0 3px black;font-weight:bold;font-size:16px;color:white'}, {color: 'black', size: 50, type: 'o'});


        });



        // Close window after upload ///////////////////////////////
        // If checkbox is checked when the upload is complete it will close the window
        uploader.bind('FileUploaded', function(up, file) {
            $('#' + file.id + " b").html("Successfuly loaded   100%");
        });



        uploader.bind('UploadComplete', function() {
            var userphotoImg1;
            var getUserImg1 = function() {
                $.ajax({
                    url: userTempFile,
                    cache: false,
                    async: false,
                    success: function() {
                        userphotoImg1 = userImg;
                    },
                    error: function() {
                        userphotoImg1 = user.userphotoImg();

                    }
                });
            };


            getUserImg1();


            var img2 = new Image();


            //hide the image before loading.
            $('#userphotoDIV').hide();
            img2.onload = function() {

                if (img2.height > img2.width) {
                    $('#userphoto').css({'height': '200px'});
                    $('#photoPreview').css({'height': '100px'});

                } else {
                    $('#userphoto').css({"height": "", "width": "160px"});
                    $('#photoPreview').css({"height": "", "width": "80px"});
                }
                //show the image.
                $('#userphotoDIV').show('fast');
                $('#userphoto').show('fast');
                // busy.remove();
                $("#userphoto").trigger("click");
                $('.filelistclass').fadeOut('fast');
                $('.success').show('fast');

                document.getElementById('userphotoform:uploadfiles').classList.add("ui-state-disabled");
                document.getElementById('userphotoform:uploadfiles').disabled = true;
                document.getElementById('userphotoform:pickfiles').classList.add("ui-state-disabled");


                document.getElementById('userphotoform:savefiles').classList.remove("ui-state-disabled");
                document.getElementById('userphotoform:savefiles').disabled = false;

            };

            img2.src = userphotoImg1 + '?' + (new Date()).getTime();




            function preview(img, selection) {
                var imageWidth = img2.width;
                var imageHeight = img2.height;

                if (!selection.width || !selection.height) {
                    return;
                }

                if (imageHeight > imageWidth) {
//alert(200 * imageWidth / imageHeight);
                    var scaledImageWidth = 200 * imageWidth / imageHeight;
                    var scaledImageHeight = 200;
                } else {

                    var scaledImageWidth = 160;
                    var scaledImageHeight = 160 * imageHeight / imageWidth;
                }

                var outSizeWidth = 80;
                var outSizeHeight = 100;
                var scaleX = outSizeWidth / (selection.width);
                var scaleY = outSizeHeight / (selection.height);
                $('#userphotoPreview  img').css({
                    width: Math.round(scaleX * scaledImageWidth) + 'px',
                    height: Math.round(scaleY * scaledImageHeight) + 'px',
                    marginLeft: '-' + Math.round(scaleX * selection.x1) + 'px',
                    marginTop: '-' + Math.round(scaleY * selection.y1) + 'px'

                });
                $('#userphotoPreview2  img').css({
                    width: Math.round(scaleX * scaledImageWidth) + 'px',
                    height: Math.round(scaleY * scaledImageHeight) + 'px',
                    marginLeft: '-' + Math.round(scaleX * selection.x1) + 'px',
                    marginTop: '-' + Math.round(scaleY * selection.y1) + 'px'

                });
                $('#x1').val(selection.x1);
                $('#y1').val(selection.y1);
                $('#x2').val(selection.x2);
                $('#y2').val(selection.y2);
                $('#scaledImgWidth').val(scaledImageWidth);
                $('#scaledImgHeight').val(scaledImageHeight);
                $('#outSizeWidth').val(outSizeWidth);
                $('#outSizeHeight').val(outSizeHeight);
            }

            $('#userphotoDIV').append(userphotoDIV);
            //  $('#userphoto').attr("src", "");
            $("#userphoto").attr('src', userphotoImg1 + '?' + (new Date()).getTime()).bind("click", function() {
                imageSelectService.userPhoto('#userphoto', preview);
                busy.remove();
            });
            $('#userphotoPreview').append(userphotoPreview);

            $("#photoPreview").attr('src', userphotoImg1 + '?' + (new Date()).getTime());

            $('#userphotoPreview2').append(userphotoPreview2);
            $("#photoPreview2").attr('src', userphotoImg1 + '?' + (new Date()).getTime());


        });


        document.getElementById('userphotoform:savefiles').onclick = function() {
           
            var x1 = $('#x1').val();
            var x2 = $('#x2').val();
            var y1 = $('#y1').val();
            var y2 = $('#y2').val();
            var scaledImgWidth = $('#scaledImgWidth').val();
            var scaledImgHeight = $('#scaledImgHeight').val();
            var outSizeWidth = $('#outSizeWidth').val();
            var outSizeHeight = $('#outSizeHeight').val();


alert("Data Saved: " );

            if (x1 == "" || y1 == "" || x2 == "" || y2 == "" || scaledImgWidth == "" || scaledImgHeight == "") {
                alert("You must make a selection first:" + x1 + "//" + y2 + "//" + y1 + "//" + scaledImgHeight + "//");
                return false;
            } else {
                alert( x1 +" / "+ x2 +" / "+ y1 +" / "+ y2 +" / "+ scaledImgWidth +" / "+ scaledImgHeight  +" / "+outSizeWidth  +" / "+outSizeHeight  +" / "+userNeo4jId);
                //Send ajax with data.
                var dataToSend = {x1: x1, x2: x2, y1: y1, y2: y2, scaledImgWidth: scaledImgWidth, scaledImgHeight: scaledImgHeight, outSizeWidth: outSizeWidth, outSizeHeight: outSizeHeight, userNeo4jId: userNeo4jId};
                $.ajax({ 
                    type: "POST",
                    url: "/usersPhotoSaverServlet",
                    data: dataToSend
                }).done(function(msg) {
                    alert("Data Saved: " + msg);
                });
            }



        };





    };
    return {
        attached: attached
                //image: ko.observable().subscribeTo('USER_PHOTO_IMG')
    };
});