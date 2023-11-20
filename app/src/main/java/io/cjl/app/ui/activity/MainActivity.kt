package io.cjl.app.ui.activity

import dagger.hilt.android.AndroidEntryPoint
import io.cjl.app.R
import io.cjl.app.ui.base.BaseActivity
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val logger: Logger = LoggerFactory.getLogger(MainActivity::class.java)

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        logger.info("MainActivity Created {}", "success");
    }

    override fun initData() {

    }

}