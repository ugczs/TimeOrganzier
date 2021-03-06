package com.saradruid.yuguanzhao.timeorganizier

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.saradruid.yuguanzhao.timeorganzier.R


class MainActivity : AppCompatActivity() {

    private lateinit var mDrawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

                // Add code here to update the UI based on the item selected
                // For example, swap UI fragments here

                 when (menuItem.itemId) {
                    R.id.add -> {
                        Log.i("event menu add ","is clicked!")
                        intent = Intent(this, DatePicker::class.java)
                        startActivity(intent)
                        true
                    }
                     R.id.about -> {
                         Log.i("event menu about","is clicked!")
                         intent = Intent(this, TimePicker::class.java)
                         startActivity(intent)
                         true
                     }
                }
                true
            }

            mDrawerLayout.addDrawerListener(
                    object : DrawerLayout.DrawerListener {
                        override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                            Log.i("Drawer ","position changed!");
                        }

                        override fun onDrawerOpened(drawerView: View) {
                            Log.i("Drawer ","is opened!");
                        }

                        override fun onDrawerClosed(drawerView: View) {
                            Log.i("Drawer ","is closed!");
                        }

                        override fun onDrawerStateChanged(newState: Int) {
                            Log.i("Drawer ","motion state changed!");
                        }
                    }
            )

       /* listView = findViewById(R.id.listView)*/
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

   /* override fun onCreateContextMenu(menu: ContextMenu, v: View,
                                     menuInfo: ContextMenuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menu.setHeaderTitle("Item Operations")
        menu.add(0, v.id, 0, "Edit Item")
        menu.add(0, v.id, 0, "Delete Item")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterContextMenuInfo
        if (item.title === "Edit Item") {

        }
        else if (item.title === "Delete Item") {

        }
        return super.onContextItemSelected(item)
    }*/

}
