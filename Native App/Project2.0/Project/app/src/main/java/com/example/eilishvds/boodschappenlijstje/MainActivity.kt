package com.example.eilishvds.boodschappenlijstje

import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley.newRequestQueue
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.login.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_password_fragment.*
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.FirebaseException
import android.util.Log
import android.widget.Toast
import android.text.TextUtils
import android.text.TextUtils.*
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.fragment_change_email_fragment.*
import kotlinx.android.synthetic.main.fragment_change_password_fragment.*
import kotlinx.android.synthetic.main.fragment_main_fragment.*
import kotlinx.android.synthetic.main.fragment_registration_fragment.*

class MainActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener,
    main_fragment.OnFragmentInteractionListener,
    settings_fragment.OnFragmentInteractionListener,
    home_fragment.OnFragmentInteractionListener,
    password_fragment.OnFragmentInteractionListener,
    registration_fragment.OnFragmentInteractionListener,
    changeEmail_fragment.OnFragmentInteractionListener,
    changePassword_fragment.OnFragmentInteractionListener,
    create_fragment.OnFragmentInteractionListener{

    // [START declare_auth]
    private lateinit var auth: FirebaseAuth
    // [END declare_auth]


    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        // [START initialize_auth]
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        // [END initialize_auth]

        onStart()


        /**
        val queue = newRequestQueue(this);
        val url = "https://bramvandenabbeele.000webhostapp.com/mi3/project/api.php?";
        */

        /**
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        */

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_boodschappenlijstje -> { Navigation.findNavController(findViewById(R.id.fragment_boodschappenlijstje)).navigate(R.id.home_fragment)}
            R.id.action_settings -> { Navigation.findNavController(findViewById(R.id.fragment_boodschappenlijstje)).navigate(R.id.settings_fragment)}
            R.id.action_logout -> {signOut(); Navigation.findNavController(findViewById(R.id.fragment_boodschappenlijstje)).navigate(R.id.main_fragment)}
        }
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_boodschappenlijstje -> {
                Navigation.findNavController(findViewById(R.id.fragment_boodschappenlijstje)).navigate(R.id.home_fragment)
            }
            R.id.nav_instellingen -> {
                Navigation.findNavController(findViewById(R.id.fragment_boodschappenlijstje)).navigate(R.id.settings_fragment)
            }
            R.id.nav_afmelden -> {
                signOut()
                Navigation.findNavController(findViewById(R.id.fragment_boodschappenlijstje)).navigate(R.id.main_fragment)
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
    //van loginscherm naar de boodschappenlijstjes
     fun activityMain(v: View) {
        signIn(login_emailadres.text.toString(), login_wachtwoord.text.toString())
        getUserProfile()
    }

    //van het loginscherm naar wachtwoordvergeten
    fun wachtwoord(v: View) {
        val action = main_fragmentDirections.ActionMainFragmentToPasswordFragment();

        v.findNavController().navigate(action)
    }

    //van het loginscherm naar registreren
    fun registreerNu(v: View) {
        val action = main_fragmentDirections.ActionMainFragmentToRegistrationFragment();

        v.findNavController().navigate(action)
    }

    //van het wachtwoordvergeten naar loginscherm
    fun wachtwoordVergeten(v: View) {
            val action = password_fragmentDirections.ActionPasswordFragmentToMainFragment();

            v.findNavController().navigate(action)
    }

    //wachtwoordvergeten annuleren
    fun annuleerWachtwoordVergeten(v: View) {
        val action = password_fragmentDirections.ActionPasswordFragmentToMainFragment();

        v.findNavController().navigate(action)
    }

    //van het registreren naar loginscherm
    fun registreer(v: View) {
        createAccount(registratie_voornaam.text.toString(), registratie_emailadres.text.toString(), registratie_wachtwoord.text.toString(), registratie_wachtwoord2.text.toString(), text_licentievoorwaarden.isChecked);
        sendEmailVerification()
        /**
        val action = registration_fragmentDirections.ActionRegistrationFragmentToMainFragment();

        v.findNavController().navigate(action)
        */
    }

    //registreren annuleren
    fun aanmelden(v: View) {
        val action = registration_fragmentDirections.ActionRegistrationFragmentToMainFragment();

        v.findNavController().navigate(action)
    }


    //van instellingen naar wachtwoordwijzigen
    fun WachtwoordWijzigen(v: View) {
        val action = settings_fragmentDirections.ActionSettingsFragmentToChangePasswordFragment();

        v.findNavController().navigate(action)
    }

    //van instellingen naar emailwijzigen
    fun EmailWijzigen(v: View) {
        val action = settings_fragmentDirections.ActionSettingsFragmentToChangeEmailFragment();

        v.findNavController().navigate(action)
    }

    private fun VulHuidigeEmailIn() {

        val user = auth.currentUser
        val huidigEmail = user?.email
        Toast.makeText(
            baseContext, "email: $huidigEmail",
            Toast.LENGTH_SHORT
        ).show()
        edit_emailWijzigen_huidigEmail.setText(huidigEmail)
    }

    //account verwijderen
    fun AccountVerwijderen(v: View) {
        deleteUser()
        signOut()

        val action = settings_fragmentDirections.ActionSettingsFragmentToMainFragment();

        v.findNavController().navigate(action)
    }

    //wachtwoord is gewijzigd
    fun wachtwoordIsGewijzigd(v: View) {
        updatePassword(edit_wachtwoordWijzigen_nieuwWachtwoord.text.toString(), edit_wachtwoordWijzigen_nieuwWachtwoord2.text.toString())
        /**
        val action = changePassword_fragmentDirections.ActionChangePasswordFragmentToSettingsFragment();

        v.findNavController().navigate(action)
        */
    }


    //wachtwoordwijzigen annuleren
    fun annuleerChangePassword(v: View) {
        val action = changePassword_fragmentDirections.ActionChangePasswordFragmentToSettingsFragment();

        v.findNavController().navigate(action)
    }

    //emailadres is gewijzigd
    fun emailIsGewijzigd(v: View) {
        updateEmail(edit_emailWijzigen_nieuwEmail.toString())

        /**
        val action = changeEmail_fragmentDirections.ActionChangeEmailFragmentToSettingsFragment();

        v.findNavController().navigate(action)
        */
    }

    //emailadres wijzigen annuleren
    fun annuleerChangeEmail(v: View) {
        val action = changeEmail_fragmentDirections.ActionChangeEmailFragmentToSettingsFragment();

        v.findNavController().navigate(action)
    }

    //annuleer het maken van een boodschappenlijstje
    fun annuleerBoodschappenlijst(v: View) {
        val action = create_fragmentDirections.ActionCreateFragmentToHomeFragment();

        v.findNavController().navigate(action)
    }

    //toe voegen van een boodschappenlijstje
    fun voegBoodschappenlijstToe(v: View) {
        val action = create_fragmentDirections.ActionCreateFragmentToHomeFragment();

        v.findNavController().navigate(action)
    }

    // [START on_start_check_user]
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }
    // [END on_start_check_user]

    private fun createAccount(naam: String, email: String, password: String, password2: String, licentievoorwaarden: Boolean) {
        Log.d(TAG, "createAccount:$email")
        if (!validateFormRegistratie()) {
            return
        }

        // [START create_user_with_email]
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                    Navigation.findNavController(findViewById(R.id.fragment)).navigate(R.id.main_fragment)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.d(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }

        // [END create_user_with_email]
    }

    private fun signIn(email: String, password: String) {
        Log.d(TAG, "signIn:$email")
        if (!validateFormLogin()) {
            return
        }
        // [START sign_in_with_email]
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                    setContentView(R.layout.activity_main)
                    setSupportActionBar(toolbar)

                    fab.setOnClickListener { view ->
                        Navigation.findNavController(findViewById(R.id.fragment_boodschappenlijstje)).navigate(R.id.create_fragment)
                    }

                    val toggle = ActionBarDrawerToggle(
                        this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
                    )

                    drawer_layout.addDrawerListener(toggle)
                    toggle.syncState()

                    nav_view.setNavigationItemSelectedListener(this)

                    Navigation.findNavController(findViewById(R.id.fragment_boodschappenlijstje)).navigate(R.id.home_fragment)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }

                // [START_EXCLUDE]
                if (!task.isSuccessful) {
                    val status = "authentication failed"
                }
                // [END_EXCLUDE]
            }
        // [END sign_in_with_email]
    }

    private fun signOut() {
        auth.signOut()
        updateUI(null)
    }

    private fun sendEmailVerification() {
        // Disable button
        btn_registreer.isEnabled = false

        // Send verification email
        // [START send_email_verification]
        val user = auth.currentUser
        user?.sendEmailVerification()
            ?.addOnCompleteListener(this) { task ->
                // [START_EXCLUDE]
                // Re-enable button
                btn_registreer.isEnabled = true

                if (task.isSuccessful) {
                    Toast.makeText(baseContext,
                        "Verification email sent to ${user.email} ",
                        Toast.LENGTH_SHORT).show()
                } else {
                    Log.e(TAG, "sendEmailVerification", task.exception)
                    Toast.makeText(baseContext,
                        "Failed to send verification email.",
                        Toast.LENGTH_SHORT).show()
                }
                // [END_EXCLUDE]
            }
        // [END send_email_verification]
    }

    private fun validateFormRegistratie(): Boolean {
        var valid = true

        val naam = registratie_voornaam.text.toString()
        if (isEmpty(naam)) {
            registratie_voornaam.error = "Verplicht."
            valid = false
        } else {
            registratie_voornaam.error = null
        }

        val email = registratie_emailadres.text.toString()
        if (isEmpty(email)) {
            registratie_emailadres.error = "Verplicht."
            valid = false
        } else {
            registratie_emailadres.error = null
        }

        val password = registratie_wachtwoord.text.toString()
        if (isEmpty(password)) {
            registratie_wachtwoord.error = "Verplicht."
            valid = false
        } else {
            registratie_wachtwoord.error = null
        }

        val password2 = registratie_wachtwoord2.text.toString()
        if (password != password2)
        {
            registratie_wachtwoord2.error = "Wachtwoord moet hetzelfde zijn"
            valid = false
        } else {
            registratie_wachtwoord2.error = null
        }

        if (!text_licentievoorwaarden.isChecked)
        {
            text_licentievoorwaarden.error = "Ga akkoord met de licentievoorwaarden"
            valid = false
        } else {
            text_licentievoorwaarden.error = null
        }

        return valid
    }

    private fun validateFormLogin(): Boolean {
        var valid = true

        val email = login_emailadres.text.toString()
        if (isEmpty(email)) {
            login_emailadres.error = "Verplicht."
            valid = false
        } else {
            login_emailadres.error = null
        }

        val password = login_wachtwoord.text.toString()
        if (isEmpty(password)) {
            login_wachtwoord.error = "Verplicht."
            valid = false
        } else {
            login_wachtwoord.error = null
        }

        return valid
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            val status = user.email + user.isEmailVerified
            val detail =  user.uid

        } else {
            val status = "signed out"
            val detail = null
        }
    }

    companion object {
        private const val TAG = "EmailPassword"
    }

    private fun getUserProfile() {
        // [START get_user_profile]
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            // Name, email address, and profile photo Url
            val name = user.displayName
            val email = user.email

            // Check if user's email is verified
            val emailVerified = user.isEmailVerified

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            val uid = user.uid
        }
        // [END get_user_profile]
    }

    private fun updateEmail(nieuw: String) {
        // [START update_email]
        val user = auth.currentUser

        if (!validateFormUpdateEmail()) {
            return
        }

        user?.updateEmail(nieuw)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User email address updated.")
                    Navigation.findNavController(findViewById(R.id.fragment_boodschappenlijstje)).navigate(R.id.settings_fragment)
                }
                else{
                    Toast.makeText(
                        baseContext, "Kan email niet updaten",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        // [END update_email]
    }

    private fun validateFormUpdateEmail(): Boolean {
        var valid = true

        val nieuw = edit_emailWijzigen_nieuwEmail.text.toString()
        if (isEmpty(nieuw)) {
            edit_emailWijzigen_nieuwEmail.error = "Verplicht."
            valid = false
        } else {
            edit_emailWijzigen_nieuwEmail.error = null
        }

        return valid
    }

    private fun deleteUser() {
        // [START delete_user]
        val user = auth.currentUser

        user?.delete()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User account deleted.")
                }
            }
        // [END delete_user]
    }

    private fun updatePassword(nieuw1: String, nieuw2: String) {
        // [START update_password]
        val user = auth.currentUser


        if (!validateFormUpdatePassword()) {
            return
        }

        val newPassword = nieuw1

        user?.updatePassword(newPassword)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User password updated.")
                    Navigation.findNavController(findViewById(R.id.fragment_boodschappenlijstje)).navigate(R.id.settings_fragment)
                }
                else{
                    Toast.makeText(
                        baseContext, "Kan wachtwoord niet updaten",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        // [END update_password]
    }

    private fun validateFormUpdatePassword(): Boolean {
        var valid = true

        val nieuw1 = edit_wachtwoordWijzigen_nieuwWachtwoord.text.toString()
        if (isEmpty(nieuw1)) {
            edit_wachtwoordWijzigen_nieuwWachtwoord.error = "Verplicht."
            valid = false
        } else {
            edit_wachtwoordWijzigen_nieuwWachtwoord.error = null
        }

        val nieuw2 = edit_wachtwoordWijzigen_nieuwWachtwoord2.text.toString()
        if (isEmpty(nieuw2)) {
            edit_wachtwoordWijzigen_nieuwWachtwoord2.error = "Verplicht."
            valid = false
        } else {
            edit_wachtwoordWijzigen_nieuwWachtwoord2.error = null
        }

        if (nieuw1 != nieuw2){
            edit_wachtwoordWijzigen_nieuwWachtwoord2.error = "De wachtwoorden moeten overeen komen."
            valid = false
        } else {
            edit_wachtwoordWijzigen_nieuwWachtwoord2.error = null
        }

        return valid
    }
}
