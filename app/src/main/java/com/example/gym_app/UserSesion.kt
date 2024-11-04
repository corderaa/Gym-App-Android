package com.example.gym_app

import java.util.Date

class UserSesion private constructor() {

    companion object {

        @Volatile
        private var instance: UserSesion? = null
        private var userName: String? = null
        private var userAuthority: String? = null
        private var birthDate: Long? = null
        private var userLastName: String? = null
        private var userLevel: Long? = null
        private var userLogin: String? = null
        private var userMail: String? = null
        private var userPassword: String? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: UserSesion().also { instance = it }
            }

        fun getUserName(): String? {
            return userName
        }

        fun setUserName(userName: String?) {
            this.userName = userName
        }

        fun getUserAuthority(): String? {
            return userAuthority
        }

        fun setUserAuthority(userAuthority: String?) {
            this.userAuthority = userAuthority
        }

        fun getBirthDate(): Long? {
            return birthDate
        }

        fun setBirthDate(birthDate: Long?) {
            this.birthDate = birthDate
        }

        fun getUserLastName(): String? {
            return userLastName
        }

        fun setUserLastName(userLastName: String?) {
            this.userLastName = userLastName
        }

        fun getUserLevel(): Long? {
            return userLevel
        }

        fun setUserLevel(userLevel: Long?) {
            this.userLevel = userLevel
        }

        fun getUserLogin(): String? {
            return userLogin
        }

        fun setUserLogin(userLogin: String?) {
            this.userLogin = userLogin
        }

        fun getUserMail(): String? {
            return userMail
        }

        fun setUserMail(userMail: String?) {
            this.userMail = userMail
        }

        fun getUserPassword(): String? {
            return userPassword
        }

        fun setUserPassword(userPassword: String?) {
            this.userPassword = userPassword
        }
    }


}