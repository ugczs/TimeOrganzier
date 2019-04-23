package com.saradruid.yuguanzhao.timeorganizier

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.saradruid.yuguanzhao.timeorganzier.R
import java.util.*




class MainActivity : AppCompatActivity(), OnDataPass  {
    lateinit var mDrawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setDefaultView()

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }
        mDrawerLayout = findViewById(R.id.drawer_layout)

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
                // set item as selected to persist highlight
            menuItem.isChecked = false
                // close drawer when item is tapped
            mDrawerLayout.closeDrawers()

                // update  UI based on the item selected
                 when (menuItem.itemId) {
                    R.id.add -> {
                        Log.i("event menu add ","is clicked!")
                        TimeSettingView()
                    }
                     R.id.unit-> {
                         Log.i("event menu unit","is clicked!")
                     }
                     R.id.about -> {
                         Log.i("event menu about","is clicked!")
                         deleteAll()
                     }
                }
                true
        }

            mDrawerLayout.addDrawerListener(
                    object : DrawerLayout.DrawerListener {
                        override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                            Log.i("Drawer ","position changed!")
                        }

                        override fun onDrawerOpened(drawerView: View) {
                            Log.i("Drawer ","is opened!")
                        }

                        override fun onDrawerClosed(drawerView: View) {
                            Log.i("Drawer ","is closed!")
                        }

                        override fun onDrawerStateChanged(newState: Int) {
                            Log.i("Drawer ","motion state changed!")
                        }
                    }
            )
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                Log.i("menu ","is clicked!")
                mDrawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /*private fun openTimeSelector() {
        val fragment = TimePicker()
        val fragmentManager = fragmentManager
        fragmentManager.beginTransaction()
                .add(R.id.content_frame, fragment)
                .commit()
    }

    private fun openCountDownList() {
        val fragment = ListFragment()
        val fragmentManager = fragmentManager
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit()
    }
*/
    /*private fun openDateSelector() {
        val fragment = DatePicker()
        val fragmentManager = fragmentManager
        fragmentManager.beginTransaction()
                .add(R.id.content_frame, fragment)
                .commit()
    }*/

    private fun setDefaultView() {
        val defaultView = fragmentManager.findFragmentByTag("defaultView")
        if (defaultView == null) {
            val listFragment = ListFragment()
            val ft = fragmentManager.beginTransaction()
            ft.replace(R.id.content_frame, listFragment, "defaultView").commit()
        }
        else {
            Log.i("defaultView", "can not be duplicated!")
        }
    }

    private fun deleteAll() {
        val newFragment = ListFragment()
        newFragment.cleanSharedPreferences(PreferenceManager.getDefaultSharedPreferences(baseContext))
    }

    private fun TimeSettingView() {
        val schedule = fragmentManager.findFragmentByTag("schedule")
        if (schedule == null) {
            val scheduleFragment = ScheduleFragment()
            val ft = fragmentManager.beginTransaction()
            ft.replace(R.id.content_frame, scheduleFragment, "schedule").addToBackStack("startScreen").commit()
        }
        else {
            Log.i("schedule", "can not be duplicated!")
        }

    }

    override fun onDataPass(data: Any?) {
        Log.i("onDataPass", "try to pass data")
        if(data != null) {
            val fragment = fragmentManager.findFragmentByTag("schedule") as ScheduleFragment
            if( data is Time) {
                fragment.upDateTime(data)
            }
            else if (data is Date) {
                fragment.upDateDate(data)
            }
            else {
                Log.i("onDataPass", "unknown data type")
            }
        }
        else {
            Log.i("onDataPass", "data is null")
        }
    }

    public override fun onResume() {
        Log.e("resumed", "onResume of main activity")
        super.onResume()
    }

    public override fun onPause() {
        Log.e("paused", "OnPause of  main activity")
        super.onPause()
    }

    /*override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false
        }
        return super.onKeyDown(keyCode, event)
    }*/

}
