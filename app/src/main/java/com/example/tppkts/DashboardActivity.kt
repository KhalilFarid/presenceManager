package com.example.tppkts
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ActionMode
import android.os.Bundle
import android.content.Intent
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DashboardActivity : AppCompatActivity() {
    private var actionMode: ActionMode? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tp2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.DashboardActivity)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        findViewById<android.view.View>(R.id.cardGroupes).setOnClickListener{
            startActivity(Intent(this, GroupesActivity::class.java))}
        val cardGroupes = findViewById<View>(R.id.cardGroupes)
        val cardAppel = findViewById<View>(R.id.cardAppel)
        val cardHistorique = findViewById<View>(R.id.cardHistorique)
        val cardStatistiques = findViewById<View>(R.id.cardStatistiques)
        cardGroupes.setOnClickListener {
            startActivity(Intent(this, GroupesActivity::class.java))
        }
        cardAppel.setOnClickListener {
            startActivity(Intent(this, AppelActivity::class.java))
        }
        cardHistorique.setOnClickListener {
            startActivity(Intent(this, HistoriqueActivity::class.java))
        }
        cardStatistiques.setOnClickListener {
            startActivity(Intent(this, StatistiquesActivity::class.java))
        }
        cardGroupes.setOnLongClickListener {
            Toast.makeText(
                this,
                "Groupes : sélectionner ou consulter les groupes",
                Toast.LENGTH_SHORT
            ).show()
            true
        }
        cardAppel.setOnLongClickListener {
            Toast.makeText(
                this,
                "Faire l'appel : démarrer une séance",
                Toast.LENGTH_SHORT
            ).show()
            true
        }
        val imgProfil = findViewById<ImageView>(R.id.imgProfil)
        val detecteur = GestureDetector(
            this,
            object : GestureDetector.SimpleOnGestureListener() {
                override fun onDown(e: MotionEvent): Boolean = true

                override fun onDoubleTap(e: MotionEvent): Boolean {
                    AlertDialog.Builder(this@DashboardActivity)
                        .setTitle("À propos de PrésenceManager")
                        .setMessage(
                            "Application de gestion des présences — Version 1.0"
                        )
                        .setPositiveButton("OK", null)
                        .show()
                    return true
                }
            }
        )
        imgProfil.setOnTouchListener { _, evenement ->
            detecteur.onTouchEvent(evenement)
            true
        }
        findViewById<TextView>(R.id.txtFooterDashboard).setOnClickListener {
            afficherInfosDeveloppeur()
        }
        val btnCab = findViewById<Button>(R.id.btnCab)
        btnCab.setOnClickListener {
            if (actionMode == null) {
                actionMode = startSupportActionMode(cabCallback)
            }
        }
        val btnPopup = findViewById<Button>(R.id.btnPopup)
        btnPopup.setOnClickListener {
            val popupMenu = PopupMenu(this, btnPopup)
            popupMenu.menuInflater.inflate(
                R.menu.popup_dashboard, popupMenu.menu
            )
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_ouvrir_groupes -> {
                        startActivity(
                            Intent(this, GroupesActivity::class.java)
                        )
                        true
                    }

                    R.id.action_informations -> {
                        AlertDialog.Builder(this)
                            .setTitle("PrésenceManager")
                            .setMessage(
                                "Menu contextuel flottant du tableau de bord."
                            )
                            .setPositiveButton("OK", null)
                            .show()
                        true
                    }

                    else -> false
                }
            }
            popupMenu.show()
        }

    }

    private val cabCallback = object : ActionMode.Callback {
        override fun onCreateActionMode(
            mode: ActionMode, menu: Menu
        ): Boolean {
            mode.menuInflater.inflate(R.menu.cab_dashboard, menu)
            mode.title = "Actions contextuelles"
            return true
        }

        override fun onPrepareActionMode(
            mode: ActionMode, menu: Menu
        ): Boolean = false

        override fun onActionItemClicked(
            mode: ActionMode, item: MenuItem
        ): Boolean = when (item.itemId) {
            R.id.action_afficher -> {
                AlertDialog.Builder(this@DashboardActivity)
                    .setTitle("CAB")
                    .setMessage("Action Afficher exécutée.")
                    .setPositiveButton("OK", null)
                    .show()
                true
            }

            R.id.action_terminer -> {
                mode.finish()
                true
            }

            else -> false
        }

        override fun onDestroyActionMode(mode: ActionMode) {
            actionMode = null
        }
    }
    private fun afficherInfosDeveloppeur() {
        val dialogue = AlertDialog.Builder(this@DashboardActivity)
            .setTitle("Informations du développeur")
            .setMessage(
                "Application : PrésenceManager\n" +
                        "Version : 1.0\n" +
                        "Développeur : FARIDKhalil\n" +
                        "GitHub : https://github.com/KhalilFarid/PresenceManager"
            )
            .setPositiveButton("OK", null)
            .show()

        dialogue.findViewById<TextView>(android.R.id.message)?.let { texte ->
            Linkify.addLinks(texte, Linkify.WEB_URLS)
            texte.movementMethod = LinkMovementMethod.getInstance()
        }
    }
}
