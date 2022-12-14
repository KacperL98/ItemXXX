package com.kacper.itemxxx.chat.chatsFragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kacper.itemxxx.chat.adapters.UserAdapter
import com.kacper.itemxxx.chat.model.Users
import com.kacper.itemxxx.databinding.FragmentSearchBinding
import java.util.*
import kotlin.collections.ArrayList

class SearchFragment : Fragment() {

    private var userAdapter: UserAdapter? = null
     lateinit var users: List<Users>

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerSearchList.setHasFixedSize(true)
        binding.recyclerSearchList.layoutManager = LinearLayoutManager(context)


        users = ArrayList()
        retrieveAllUsers()
        binding.searchUsersEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(cs: CharSequence?, start: Int, before: Int, count: Int) {
                searchForUsers(cs.toString().toLowerCase(Locale.ROOT))

            }
            override fun afterTextChanged(s: Editable?) {
            }
        }) }
    private fun retrieveAllUsers() {
        val firebaseUserID = FirebaseAuth.getInstance().currentUser!!.uid

        val refUsers = FirebaseDatabase.getInstance().reference.child("Users")
        refUsers.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(pO: DataSnapshot) {
                (users as ArrayList<Users>).clear()
                if (binding.searchUsersEt.text.toString() == ""){

                    for (snapshot in pO.children ){

                        val user: Users? = snapshot.getValue(Users::class.java)
                        if ((user!!.uid) != firebaseUserID){
                            (users as ArrayList<Users>).add(user)

                        }
                    }
                    userAdapter = UserAdapter(context, users as ArrayList<Users>, false)
                    binding.recyclerSearchList.adapter = userAdapter
                }
            }
            override fun onCancelled(pO: DatabaseError) {
            }
        })
    }
    private fun searchForUsers (str: String){
        val firebaseUserID = FirebaseAuth.getInstance().currentUser!!.uid

        val queryUsers = FirebaseDatabase.getInstance().reference.child("Users").orderByChild("search")
            .startAt(str)
            .endAt(str + "\uf8ff")
        queryUsers.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(pO: DataSnapshot) {
                (users as ArrayList<Users>).clear()

                for (snapshot in pO.children ){
                    val user: Users? = snapshot.getValue(Users::class.java)
                    if ((user!!.uid) != firebaseUserID){
                        (users as ArrayList<Users>).add(user)
                    }
                }
                userAdapter = UserAdapter(context, users as ArrayList<Users>, false)
                binding.recyclerSearchList.adapter = userAdapter
            }
            override fun onCancelled(pO: DatabaseError) {
            }
        })
    }
}