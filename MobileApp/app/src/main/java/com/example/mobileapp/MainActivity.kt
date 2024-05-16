package com.example.mobileapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentManager
import com.example.mobileapp.databinding.ActivityMainBinding
import com.genesys.cloud.integration.messenger.MessengerAccount
import com.genesys.cloud.ui.structure.controller.ChatController
import com.genesys.cloud.ui.structure.controller.ChatLoadResponse
import com.genesys.cloud.ui.structure.controller.ChatLoadedListener

class MainActivity : AppCompatActivity() {
    private lateinit var fragmentManager: FragmentManager
    private lateinit var binding: ActivityMainBinding

    private val messengerAccount = MessengerAccount("ENTER_YOUR_DEPLOYMENT_ID", "ENTER_YOUR_REGION").apply {
        logging = true
    }

    private val chatController = ChatController.Builder(context = this).build(messengerAccount, object :
        ChatLoadedListener {
        override fun onComplete(result: ChatLoadResponse) {
            result.error?.let {
                // report Chat loading failed
            } ?: let {
                // add result.fragment to your activity.
                supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, result.fragment!!).commit()
            }
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button1.setOnClickListener {
            println("button1 clicked")
            chatController.startChat(messengerAccount)
        }

        binding.button2.setOnClickListener {
            println("button2 clicked")
            chatController.endChat()
            fragmentManager = supportFragmentManager
            fragmentManager.beginTransaction().replace(R.id.fragmentContainer, messaging_closed()).commit()
        }
    }
}