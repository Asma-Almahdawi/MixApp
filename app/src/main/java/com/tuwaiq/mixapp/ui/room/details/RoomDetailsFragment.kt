package com.tuwaiq.mixapp.ui.room.details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.tuwaiq.mixapp.R
import com.tuwaiq.mixapp.database.Name
import com.tuwaiq.mixapp.databinding.RoomDetailsFragmentBinding

private const val TAG = "RoomDetailsFragment"
class RoomDetailsFragment : Fragment() {

    private val roomDetailsViewModel: RoomDetailsViewModel by lazy { ViewModelProvider(this)[RoomDetailsViewModel::class.java] }

    private lateinit var binding:RoomDetailsFragmentBinding

    private val navArgs:RoomDetailsFragmentArgs by navArgs()

    private lateinit var name:Name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e(TAG,"the ${navArgs.id}")
        if (navArgs.id != -1){
            roomDetailsViewModel.loadName(navArgs.id)
        }
    }

    override fun onStart() {
        super.onStart()
        val textWatcher = object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                name.name = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
            }

        }
        binding.detailsNameTv.addTextChangedListener(textWatcher)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RoomDetailsFragmentBinding.inflate(layoutInflater)


        if (navArgs.id == -1){
            binding.button.text = "Add Name"
        }else{
            binding.button.text = "Update Name"
        }

        binding.button.setOnClickListener {
            roomDetailsViewModel.updateName(name)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        roomDetailsViewModel.nameLiveData.observe(viewLifecycleOwner){
            if (it != null){
                name = it

                binding.detailsNameTv.setText(it.name)
            }

        }
    }




}