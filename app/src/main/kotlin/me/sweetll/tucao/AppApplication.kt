package me.sweetll.tucao

import android.app.Application
import com.squareup.leakcanary.LeakCanary
import com.umeng.analytics.MobclickAgent
import me.drakeet.library.CrashWoodpecker
import me.drakeet.library.PatchMode
import me.sweetll.tucao.di.component.ApiComponent
import me.sweetll.tucao.di.component.BaseComponent
import me.sweetll.tucao.di.component.DaggerBaseComponent
import me.sweetll.tucao.di.module.ApiModule
import me.sweetll.tucao.di.module.BaseModule
import me.sweetll.tucao.di.service.ApiConfig

class AppApplication : Application() {
    companion object {
        private lateinit var INSTANCE: AppApplication

        fun get(): AppApplication {
            return INSTANCE
        }
    }

    private lateinit var baseComponent: BaseComponent
    private lateinit var apiComponent: ApiComponent

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL)
        CrashWoodpecker.instance()
                .setPatchMode(PatchMode.SHOW_LOG_PAGE)
                .flyTo(this)
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)
        initApiComponent()
    }

    private fun initApiComponent() {
        baseComponent = DaggerBaseComponent.builder()
                .baseModule(BaseModule(ApiConfig.API_KEY))
                .build()
        apiComponent = baseComponent.plus(
                ApiModule()
        )
    }

    fun getApiComponent(): ApiComponent = apiComponent

}
