define(['dojo/i18n!app/nls/constants', 'durandal/composition', 'global/services/session', 'global/services/security'],
        function(constants, composition, session, security) {
            var ctor = function() {
            };

            ctor.prototype.activate = function(settings) {
                this.settings = settings;
                this.userId = this.settings.panelId;


                this.user = this.getUser(this.userId);
            };


            ctor.prototype.getUser = function(userId) {

                var user = {},
                        control,
                        allUser = session.userAllSearch(),
                        oneUser = session.userSearch();

                $.each(oneUser, function(id, value) {
                    if (userId.toString() === value.userId.toString()) {//convert, so that === will work well.
                        //  alert(userId.toString() + ' === ' + value.userId.toString());
                        user.userId = value.userId;
                        user.name = value.name;
                        user.usersName = value.usersName;
                        user.usersFirstname = value.usersFirstname;
                        user.usersLastname = value.usersLastname;
                        user.usersFullname = user.usersLastname + " " + user.usersFirstname;
                        user.usersStatus = value.usersStatus;
                        user.img = constants.userImgFolder + userId + '/' + userId + constants.extJPG;
                        control = 1;
                    }
                });

                if (control === undefined) {
                    $.each(allUser, function(id, value) {
                        if (userId.toString() === value.userId.toString()) {//convert, so that === will work well.
                            // alert(userId.toString() + ' =2=2= ' + value.userId.toString());
                            user.userId = value.userId;
                            user.name = value.name;
                            user.usersName = value.usersName;
                            user.usersFirstname = value.usersFirstname;
                            user.usersLastname = value.usersLastname;
                            user.usersFullname = user.usersLastname + " " + user.usersFirstname;
                            user.usersStatus = value.usersStatus;
                            user.img = constants.userImgFolder + userId + '/' + userId + constants.extJPG;

                        }
                    });
                }

                return user;
            };


            ctor.prototype.addFriend = function() {
                alert('userId : ' + this.userId);
                var jData = {
                    userId: this.userId
                };
                var url = '/tuFriendServlet';
                security.addFriend(url, jData);
            };
//ctor.prototype.thatt = 'this.settings.headerProperty';
            /*
             ctor.prototype.afterRenderItem = function(elements, item) {
             var parts = composition.getParts(elements);
             var $itemContainer = $(parts.itemContainer);
             
             $itemContainer.hide();
             
             $(parts.headerContainer).bind('click', function() {
             $itemContainer.toggle('fast');
             });
             };
             */
            return ctor;
        });