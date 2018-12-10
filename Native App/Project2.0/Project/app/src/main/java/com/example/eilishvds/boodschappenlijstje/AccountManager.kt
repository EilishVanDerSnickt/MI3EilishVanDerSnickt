package com.example.eilishvds.boodschappenlijstje



    import android.content.Context
    import com.google.android.gms.tasks.Task
    import com.google.firebase.auth.AuthResult
    import com.google.firebase.auth.FirebaseAuth
    import com.google.firebase.database.DataSnapshot
    import com.google.firebase.database.DatabaseError
    import com.google.firebase.database.FirebaseDatabase
    import com.google.firebase.database.ValueEventListener
    import java.io.Serializable
    import java.lang.Exception
    import kotlin.collections.Map


    class AccountManager {
        private val mAuth = FirebaseAuth.getInstance()
        //    private var listener: AccountManagerListener? = null


        init {
            captureAccountStateChange()
        }


        fun register(email: String, password: String) {
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task: Task<AuthResult> ->
                    if (task.isSuccessful) {
                        //Registration OK

                    } else {
                        println(task.exception)
                    }
                }
        }



        private fun captureAccountStateChange() {
            mAuth.addAuthStateListener { auth ->

                if (mAuth.currentUser == null)
                {

                }
                else {

                }
            }
        }


}