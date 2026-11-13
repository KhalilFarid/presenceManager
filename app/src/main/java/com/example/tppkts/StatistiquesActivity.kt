package com.example.tppkts

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class StatistiquesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_statistiques)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private fun afficherInfosDeveloppeur() {
        val dialogue = AlertDialog.Builder(this)
            .setTitle("Informations du développeur")
            .setMessage(
                "Application : PrésenceManager\n" +
                        "Version : 1.0\n" +
                        "Développeur : NOM_PRENOM\n" +
                        "GitHub : https://github.com/VOTRE_IDENTIFIANT/PresenceManager"
            )
            .setPositiveButton("OK", null)
            .show()

        dialogue.findViewById<TextView>(android.R.id.message)?.let { texte ->
            Linkify.addLinks(texte, Linkify.WEB_URLS)
            texte.movementMethod = LinkMovementMethod.getInstance()
        }
    }
}