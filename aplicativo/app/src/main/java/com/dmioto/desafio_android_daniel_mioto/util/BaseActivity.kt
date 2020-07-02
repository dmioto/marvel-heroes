package com.dmioto.desafio_android_daniel_mioto.util

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory

typealias intentListenerType = (intent: Intent) -> Unit

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    companion object {
        const val TAG_BUNDLE = "bundle"
    }

    private val moduleManager: SplitInstallManager by lazy {
        SplitInstallManagerFactory.create(this)
    }

    var intentListener: intentListenerType = { }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // receive return from previous called activity running the method in global intent listener
        data?.run {
            intentListener(this)
        }
    }


    fun openModule(module: ModuleHelper, bundle: Bundle? = null){
        if(moduleManager.installedModules.contains(module.moduleName)){
            startModule(module, bundle)
        }
    }


    private fun startModule(module: ModuleHelper, bundle: Bundle?){
        Intent().setClassName(packageName, module.packageName).also { intent ->
            if(bundle != null) intent.putExtra(TAG_BUNDLE, bundle)
            startActivity(intent)
        }
    }

    fun getBundle(): Bundle {
        return intent.extras?.get(TAG_BUNDLE) as Bundle
    }

    fun openModuleForResult(module : ModuleHelper, bundle: Bundle?, intentListenerResult: intentListenerType){
        intentListener = intentListenerResult

        Intent().setClassName(packageName, module.packageName).also { intent ->
            if(bundle != null) intent.putExtra(TAG_BUNDLE, bundle)
            startActivityForResult(intent, 4242)
        }
    }

}