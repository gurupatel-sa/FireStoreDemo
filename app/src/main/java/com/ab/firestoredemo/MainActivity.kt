package com.ab.firestoredemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var TAG : String = this.javaClass.getSimpleName()
    val db = FirebaseFirestore.getInstance()
    var freeRef : DocumentReference?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        freeRef= db.collection("Account").document("Free")

        var map =HashMap<String,String>()
        map.put("User" ,"AB")

        btn_send.setOnClickListener {
            db.collection("Account").document("Premiums")
                .collection("Test").document("Try")
                .set(map).addOnSuccessListener {
                    Log.d(TAG ,"onSuccess : send")
                }
        }

        btn_read.setOnClickListener {
            db.collection("Account").document("Free")
                .get()
                .addOnSuccessListener {
                    Log.d(TAG ,"onSuccess : read" + it.getString("User"))
                }
        }

        btn_observe.setOnClickListener {
            
        }

        db.collection("Account").addSnapshotListener(this , EventListener { snapshot, firebaseFirestoreException -> 
            Log.d(TAG ,"SnapshotListner :")
        })

    }

    // update fields
    fun updateFieldOnly(){
        freeRef?.update("User" ,18)         //or use map object

    }

    fun deleteField(){
        freeRef?.update("User", FieldValue.delete())
    }

    fun delete(){
        freeRef?.delete()
    }

    fun getModel(){
        freeRef?.addSnapshotListener(this) { documentSnapshot, firebaseFirestoreException ->
            if(documentSnapshot?.exists()!!){
                // Create user model
//                documentSnapshot.toObject(classname)
                //user.getAge()
            }

        }
    }
}
