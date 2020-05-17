package io.nikvn.chat_shifr


import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.Window
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var database: FirebaseDatabase = FirebaseDatabase.getInstance()//сылка на базу данных
    val LENGTH_MSG=248
    var myRef: DatabaseReference = database.getReference("messages")//ссылка на блок базы данных где хранятся сообщения
    //lateinit var mEditTextMessage: EditText
    // lateinit var mMessagesRecycler : RecyclerView
    //lateinit var dataAdapter : AdapterDate

    var messages = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main)
        var mEditTextMessage = findViewById<EditText>(R.id.message_input)
        var mMessagesRecycler = findViewById<RecyclerView>(R.id.messages_recycle)
        mMessagesRecycler.layoutManager = LinearLayoutManager(this)
        var dataAdapter = AdapterDate(this, messages)
        mMessagesRecycler.adapter = dataAdapter
        button.setOnClickListener {
            var msg = mEditTextMessage?.text.toString()
            //val toast = Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()

            if (msg.isBlank()){
                val toast = Toast.makeText(applicationContext, "Enter a message", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(msg.length>LENGTH_MSG){
                val toast = Toast.makeText(applicationContext, "Your message is longer than 246 characters.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val encrypted = AES256.encrypt(msg)
            //myRef.push().setValue(encrypted)
            // mEditTextMessage.setText("")
            myRef.push().setValue(encrypted)
            var Str : String =""
            mEditTextMessage.text = Editable.Factory.getInstance().newEditable(Str)

        }
        var childEventListener = object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                Log.d("TAG", "onChildAdded:" + dataSnapshot.key!!)

                val msg =
                    dataSnapshot.getValue(String::class.java)
                var decrypted: String? = null
                Log.d("TAG", msg + dataSnapshot.key!!)

                assert(msg != null)
                decrypted = AES256.decrypt(msg)
                if (decrypted != null) {
                    messages.add("Decrypt: "+ decrypted)
                }
                dataAdapter.notifyDataSetChanged()
                mMessagesRecycler.smoothScrollToPosition(messages.size)
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
                Log.d("TAG", "onChildChanged: ${dataSnapshot.key}")
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                Log.d("TAG", "onChildRemoved:" + dataSnapshot.key!!)
            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {
                Log.d("TAG", "onChildMoved:" + dataSnapshot.key!!)
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        }

        myRef.addChildEventListener(childEventListener)
    }

}
