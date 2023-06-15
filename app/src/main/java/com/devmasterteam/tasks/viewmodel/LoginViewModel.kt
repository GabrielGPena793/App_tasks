package com.devmasterteam.tasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.devmasterteam.tasks.service.listener.APIListener
import com.devmasterteam.tasks.service.model.PersonModel
import com.devmasterteam.tasks.service.repository.PersonRepository

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = PersonRepository(application.applicationContext)

    private val _item = MutableLiveData<String>()
    val item: LiveData<String> = _item

    /**
     * Faz login usando API
     */
    fun doLogin(email: String, password: String) {
        repository.login(email, password, object : APIListener<PersonModel> {
            override fun onSucess(result: PersonModel) {

                _item.value = "Bom"
            }

            override fun onFailure(message: String) {
                TODO("Not yet implemented")
            }

        })
    }

    /**
     * Verifica se usuário está logado
     */
    fun verifyLoggedUser() {
    }

}