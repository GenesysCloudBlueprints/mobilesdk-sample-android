package com.example.basicmobileapp

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.FragmentManager
import com.example.basicmobileapp.databinding.ActivityMainBinding
import com.genesys.cloud.integration.messenger.MessengerAccount
import com.genesys.cloud.ui.structure.controller.ChatController
import com.genesys.cloud.ui.structure.controller.ChatLoadResponse
import com.genesys.cloud.ui.structure.controller.ChatLoadedListener

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private lateinit var fragmentManager: FragmentManager

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
                supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,
                    result.fragment!!
                ).commit()
            }
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener {
            println("button1 clicked")
            chatController.startChat(messengerAccount)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}